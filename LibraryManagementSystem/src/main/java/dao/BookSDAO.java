package dao;
import model.Book;
import model.Loan;
import model.User;
import util.DBConnection;
import java.sql.*;
import java.util.*;
public class BookSDAO {
	public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Books";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPublisher(rs.getString("publisher"));
                b.setIsbn(rs.getString("isbn"));
                b.setTotalCopies(rs.getInt("total_copies"));
                b.setAvailableCopies(rs.getInt("available_copies"));
                books.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
	public List<Loan> getBorrowedBooks(User user) {
		System.out.println(user.getUserId());
	    List<Loan> loans = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "SELECT l.loan_id, l.book_id, l.borrow_date, l.due_date, l.renewed, " +
	                     "b.title, b.author, b.publisher, b.isbn, b.total_copies, b.available_copies ,l.return_date,l.requested " +
	                     "FROM loans l LEFT JOIN books b ON l.book_id = b.book_id WHERE l.user_id = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, user.getUserId());
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Loan loan = new Loan();
	            loan.setLoanId(rs.getInt("loan_id"));
	            loan.setBookId(rs.getInt("book_id"));
	            loan.setBorrowDate(rs.getDate("borrow_date"));
	            loan.setDueDate(rs.getDate("due_date"));
	            loan.setRenewed(rs.getBoolean("renewed"));
	            loan.setReturnDate(rs.getDate("return_date"));
	            loan.setRequested(rs.getBoolean("requested"));

	            Book book = new Book();
	            book.setBookId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthor(rs.getString("author"));
	            book.setPublisher(rs.getString("publisher"));
	            book.setIsbn(rs.getString("isbn"));
	            book.setTotalCopies(rs.getInt("total_copies"));
	            book.setAvailableCopies(rs.getInt("available_copies"));
	            //System.out.println(rs.getInt("book_id"));

	            loan.setBook(book); // Set Book in Loan
	            loans.add(loan);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return loans;
    }
	public boolean addBook(Book book) {
        // Corrected SQL for available_copies based on your AddBookServlet logic
        String sql = "INSERT INTO Books (book_id, title, author, publisher, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPublisher());
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getTotalCopies());
            stmt.setInt(7, book.getTotalCopies()); // available_copies are same as total_copies on add
 
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
 
        } catch (SQLException e) { // Catch SQLException specifically for DB issues
            e.printStackTrace();
            System.err.println("SQL Error adding book: " + e.getMessage());
            return false;
        } catch (Exception e) { // Catch other potential exceptions (e.g., from DBConnection.getConnection)
            e.printStackTrace();
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }
	//public void 
	public BookSDAO() {
		// TODO Auto-generated constructor stub
	}
	public boolean incrementAvailableCopies(int bookId) {
		boolean incrementedBook=false;
		Connection conn;
		try {
			
			conn = DBConnection.getConnection();
			System.out.println("Book ID"+bookId);
			PreparedStatement stmt = conn
					.prepareStatement("UPDATE books SET available_copies=available_copies+1 WHERE book_id=?");
//			System.out.println("Available copies incremented");
			stmt.setInt(1, bookId);
			stmt.executeUpdate();
			incrementedBook=true;
 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return incrementedBook;
		
}
}
