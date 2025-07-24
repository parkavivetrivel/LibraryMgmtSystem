package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.FineDAO;
import dao.LoanDAO;
import model.Loan;

/**
 * Servlet implementation class ReturnBookServlet
 */
@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("is it oke");
		
		LoanDAO loanDAO = new LoanDAO();
		FineDAO fineDAO = new FineDAO();
		int bookId = Integer.parseInt(request.getParameter("bookId"));
        HttpSession session = request.getSession(false);
        int userId = ((model.User) session.getAttribute("user")).getUserId();
		 Loan loan = loanDAO.getActiveLoanByUserAndBook(userId, bookId);
		 //System.out.println("loan choosen for return"+loan.getLoanId());
		 if (loan == null) {
			 
	            response.sendRedirect("CheckAvailabilityServlet");
	            return;
	        }
		 System.out.println("loan choosen for return"+loan.getLoanId());
		 LocalDate today = LocalDate.now();
		 
		 LocalDate dueDate=loan.getDueDate().toLocalDate();
	        long daysBetween = ChronoUnit.DAYS.between(dueDate, today);
	        if(dueDate.isAfter(today)) {
	        	double fineAmount = (daysBetween) * 10.0;
	            fineDAO.addOrUpdateFine(loan.getLoanId(), fineAmount,0);
	            fineDAO.RequestedForReturn(loan.getLoanId());
	        	
	        }
	        else {
	        	fineDAO.addOrUpdateReturnFine(loan.getLoanId(), 0);
	        	fineDAO.RequestedForReturn(loan.getLoanId()); 
	        }
	        response.sendRedirect("CheckAvailabilityServlet");
	}
}
