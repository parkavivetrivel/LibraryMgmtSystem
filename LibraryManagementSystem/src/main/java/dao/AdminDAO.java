package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Fines;
import model.Loan;
import model.User;
import util.DBConnection;

public class AdminDAO {
	public boolean deleteBookDetails(int bookId) {
        String sql2 = "DELETE FROM books WHERE book_id=?;";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
 
            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }	
	public List<Loan> getBookRequestedForUserId(int userId) {
		System.out.println("in dao");
		//System.out.println(user.getUserId());
	    List<Loan> loans = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "select l.loan_id,u.name,b.book_id,b.title,f.amount from loans l \r\n"
	        		+ "left join users u on u.user_id = l.user_id\r\n"
	        		+ "left join books b on b.book_id = l.book_id \r\n"
	        		+ "left join fines f on l.loan_id =  f.loan_id\r\n"
	        		+ " where l.requested=1 AND u.user_id=?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Loan loan = new Loan();
	            loan.setLoanId(rs.getInt("loan_id"));
	            
	           
	            User user = new User();
	            user.setName(rs.getString("name"));
	            
	            Book book = new Book();
	            book.setBookId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            
	            Fines fine = new Fines();
	            fine.setAmount(rs.getDouble("amount"));
	            
	            //System.out.println(rs.getInt("book_id"));
 
	            loan.setBook(book); // Set Book in Loan
	            loan.setFine(fine);
	            loan.setUser(user);
	        
	            loans.add(loan);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return loans;
    }
//	public boolean updateBookDetails(Book book) {
//        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, " +
//                     "isbn = ?, total_copies = ?, available_copies = ? WHERE book_id = ?;";
// 
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
// 
//            // Set parameters for the prepared statement
//            pstmt.setString(1, book.getTitle());
//            pstmt.setString(2, book.getAuthor());
//            pstmt.setString(3, book.getPublisher());
//            pstmt.setString(4, book.getIsbn());
//            pstmt.setInt(5, book.getTotalCopies());
//            pstmt.setInt(6, book.getAvailableCopies());
//            pstmt.setInt(7, book.getBookId()); // for the WHERE clause
// 
//            int rowsAffected = pstmt.executeUpdate();
//            return rowsAffected > 0;
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
	public List<Loan> getBookRequestedForReturnToday() {
		//System.out.println(user.getUserId());
	    List<Loan> loans = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection()) {
	        String sql = "select l.loan_id,u.name,b.title,f.amount,l.requested,l.return_date from loans l \r\n"
	        		+ "left join users u on u.user_id = l.user_id\r\n"
	        		+ "left join books b on b.book_id = l.book_id \r\n"
	        		+ "left join fines f on l.loan_id =  f.loan_id where l.requested = 1 \r\n";;
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        //stmt.setInt(1, user.getUserId());
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Loan loan = new Loan();
	            loan.setLoanId(rs.getInt("loan_id"));
	            loan.setRequested(rs.getBoolean("requested"));
	            loan.setReturnDate(rs.getDate("return_date"));

	            

	           
	            User user = new User();
	            user.setName(rs.getString("name"));
	            
	            Book book = new Book();
	            book.setTitle(rs.getString("title"));
	            
	            Fines fine = new Fines();
	            fine.setAmount(rs.getDouble("amount"));
	            //fine.re
	            
	            //System.out.println(rs.getInt("book_id"));

	            loan.setBook(book); // Set Book in Loan
	            loan.setFine(fine);
	            loan.setUser(user);
	        
	            loans.add(loan);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return loans;
    }

	public AdminDAO() {
		// TODO Auto-generated constructor stub
	}

}
