package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Fines;
import model.Loan;
import model.User;
import util.DBConnection;

public class FineDAO {
	public void addOrUpdateFine(int loanId, double amount , int a) {
	    try (Connection conn = DBConnection.getConnection()) {
	        // Check if fine exists
	        PreparedStatement checkStmt = conn.prepareStatement(
	            "SELECT fine_id FROM fines WHERE loan_id = ?"
	        );
	        checkStmt.setInt(1, loanId);
	        ResultSet rs = checkStmt.executeQuery();
	        
	        if (rs.next()) {
	            // Update existing fine
	            PreparedStatement updateStmt = conn.prepareStatement(
	                "UPDATE fines SET amount = ?, paid = 0 WHERE loan_id = ?"
	            );
	            updateStmt.setDouble(1, amount);
	            updateStmt.setInt(2, loanId);
	            updateStmt.executeUpdate();
	            
	            
	        } else {
	            // Insert new fine
	            PreparedStatement insertStmt = conn.prepareStatement(
	                "INSERT INTO fines (loan_id, amount, paid,requested) VALUES (?, ?, 0,True)"
	            );
	            insertStmt.setInt(1, loanId);
	            insertStmt.setDouble(2, amount);
	            insertStmt.executeUpdate();
	        }
	        
	       
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	}
	public void addOrUpdateReturnFine(int loanId, double amount) {
	    try (Connection conn = DBConnection.getConnection()) {
	    	System.out.println("yes in dao");
	        // Check if fine exists
	        PreparedStatement checkStmt = conn.prepareStatement(
	            "SELECT fine_id FROM fines WHERE loan_id = ?"
	        );
	        checkStmt.setInt(1, loanId);
	        ResultSet rs = checkStmt.executeQuery();

	        if (rs.next()) {
	            // Update existing fine
	            PreparedStatement updateStmt = conn.prepareStatement(
	                "UPDATE fines SET amount = ?, paid = 0 WHERE loan_id = ?"
	            );
	            updateStmt.setDouble(1, amount);
	            updateStmt.setInt(2, loanId);
	            updateStmt.executeUpdate();

//	            PreparedStatement updateLoanDate = conn.prepareStatement(
//	                "UPDATE loans SET Return_date = CURRENT_DATE WHERE loan_id = ?"
//	            );
//	            updateLoanDate.setInt(1, loanId); // ✅ FIXED
//	            updateLoanDate.executeUpdate();

	        } else {
	            // Insert new fine
	            PreparedStatement insertStmt = conn.prepareStatement(
	                "INSERT INTO fines (loan_id, amount, paid) VALUES (?, ?, 0)"
	            );
	            insertStmt.setInt(1, loanId);
	            insertStmt.setDouble(2, amount);
	            insertStmt.executeUpdate();

//	            PreparedStatement updateLoanDate = conn.prepareStatement(
//	                "UPDATE loans SET Return_date = CURRENT_DATE WHERE loan_id = ?"
//	            );
//	            updateLoanDate.setInt(1, loanId); // ✅ FIXED
//	            updateLoanDate.executeUpdate();
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public List<Loan> getLoansWithFinesByUserId(int userId) throws ClassNotFoundException {
        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT f.loan_id, l.user_id, b.title, f.amount,l.requested, l.return_date " +
                     "FROM fines f " +
                     "LEFT JOIN loans l ON l.loan_id = f.loan_id " +
                     "LEFT JOIN books b ON b.book_id = l.book_id " +
                     "WHERE l.user_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
        	PreparedStatement checkStmt = conn.prepareStatement(
    	           sql
    	        );
        	checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();

            while (rs.next()) {
                Loan loan = new Loan();

                loan.setLoanId(rs.getInt("loan_id"));
                loan.setUserId(rs.getInt("user_id"));
                loan.setReturnDate(rs.getDate("return_date"));
                //loan.setRenewed(rs.getBoolean("renewed"));

                // Set Book
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                loan.setBook(book);

                // Set Fine
                Fines fine = new Fines();
                fine.setAmount(rs.getDouble("amount"));
                loan.setFine(fine);

                // Set User (optional, if needed)
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                loan.setUser(user);

                loans.add(loan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }
	public void RequestedForReturn(int loanId) {
		  try (Connection conn = DBConnection.getConnection()) {
			  System.out.println("RequestedForReturn");
			  PreparedStatement updateStmtLoan = conn.prepareStatement(
		                "UPDATE Loans SET requested= True WHERE loan_id = ?"
		            );
		        updateStmtLoan.setInt(1, loanId);
		        updateStmtLoan.executeUpdate();
			  
		  }  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public FineDAO() {
		// TODO Auto-generated constructor stub
	}

}
