package dao;

import java.sql.Connection;
import exception.BookLimitExceededException;
import exception.BookAlreadyBorrowedException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Fines;
import model.Loan;
import model.User;
import util.DBConnection;

public class LoanDAO {

    public LoanDAO() {
        // Constructor
    }

    public boolean addBorrow(User user, Book book) throws BookLimitExceededException, BookAlreadyBorrowedException {
        boolean isAdded = false;

        try (Connection conn = DBConnection.getConnection()) {

            // Step 1: Check if user already has 5 active loans
            String checkSql = "SELECT COUNT(*) FROM loans WHERE user_id = ? AND return_date IS NULL";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, user.getUserId());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) >4) {
                    throw new BookLimitExceededException("You have already borrowed 4 books.");
                }
            }

            // Step 2: Check if the same book is already borrowed and not returned
            String duplicateSql = "SELECT COUNT(*) FROM loans WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
            try (PreparedStatement dupStmt = conn.prepareStatement(duplicateSql)) {
                dupStmt.setInt(1, user.getUserId());
                dupStmt.setInt(2, book.getBookId());
                ResultSet rs = dupStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new BookAlreadyBorrowedException("You have already borrowed this book and not returned it.");
                }
            }

            // Step 3: Attempt to insert loan if copies are available
            String insertSql =
                    "INSERT INTO loans (user_id, book_id, borrow_date, due_date, renewed, return_date) " +
                    "SELECT ?, ?, ?, ?, ?, ? FROM books " +
                    "WHERE book_id = ? AND available_copies >= 1";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                LocalDate today = LocalDate.now();
                LocalDate dueDate = today.plusDays(5);

                insertStmt.setInt(1, user.getUserId());
                insertStmt.setInt(2, book.getBookId());
                insertStmt.setDate(3, Date.valueOf(today));
                insertStmt.setDate(4, Date.valueOf(dueDate));
                insertStmt.setBoolean(5, false);
                insertStmt.setNull(6, java.sql.Types.DATE);
                insertStmt.setInt(7, book.getBookId());

                int rows = insertStmt.executeUpdate();

                if (rows > 0) {
                    // Step 4: Decrement available copies
                    String updateSql = "UPDATE books SET available_copies = available_copies - 1 " +
                                       "WHERE book_id = ? AND available_copies >= 1";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, book.getBookId());
                        updateStmt.executeUpdate();
                    }

                    isAdded = true;
                } else {
                    System.out.println("Book is not available to borrow (0 copies left).");
                }
            }

        } catch (BookLimitExceededException | BookAlreadyBorrowedException e) {
            throw e; // re-throw custom exception
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAdded;
    }
    public boolean updateReturnDate(int loanId, LocalDate returnDate) { // updates return date in loans table
		boolean bookReturned = false;
 
		Connection conn;
		try {
			conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE loans SET return_date=?, requested=0 WHERE loan_id=?");
			stmt.setDate(1, Date.valueOf(returnDate));
			stmt.setInt(2, loanId);
			bookReturned = stmt.executeUpdate() > 0; // executeUpdate() returns no. of rows updated
 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return bookReturned;
 
	}
 

    public Loan getActiveLoanByUserAndBook(int userId, int bookId) {
        Loan loan = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM loans WHERE user_id = ? AND book_id = ? AND return_date IS NULL"
             )) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setBookId(rs.getInt("book_id"));
                loan.setBorrowDate(rs.getDate("borrow_date"));
                loan.setDueDate(rs.getDate("due_date"));
                loan.setRenewed(rs.getBoolean("renewed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loan;
    }

    public void renewLoan(int loanId, Date newDueDate) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE loans SET due_date = ?, renewed = true WHERE loan_id = ?"
             )) {
            stmt.setDate(1, newDueDate);
            stmt.setInt(2, loanId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getActiveLoanByUserId(int userId) {
		List<Loan> loanList = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT l.loan_id,u.name,b.title, l.borrow_date, l.due_date, f.amount "
								+ "FROM books b "
								+ "JOIN Loans l ON b.book_id=l.book_id "
								+ "JOIN users u ON u.user_id=l.user_id "
								+ "JOIN fines f ON f.loan_id=l.loan_id "
								+ "WHERE u.user_id=? ")) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Loan loan = new Loan();
				Book book=new Book();
				User user=new User();
				Fines fine=new Fines();
				
				loan.setLoanId(rs.getInt("loan_id"));
				
				user.setName(rs.getString("name"));
				loan.setUser(user);
				
				book.setTitle(rs.getString("title"));
				loan.setBook(book);
				
				loan.setBorrowDate(rs.getDate("borrow_date"));
				loan.setDueDate(rs.getDate("due_date"));
				
				fine.setAmount(rs.getDouble("amount"));
				loan.setFine(fine);
				
				loanList.add(loan);
			}
 
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
 
		return loanList;
 
	}
}
