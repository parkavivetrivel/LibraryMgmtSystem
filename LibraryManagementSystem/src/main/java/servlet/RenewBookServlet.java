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
 * Servlet implementation class RenewBookServlet
 */
@WebServlet("/RenewBookServlet")
public class RenewBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenewBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		int bookId = Integer.parseInt(request.getParameter("bookId"));
        HttpSession session = request.getSession(false);
        int userId = ((model.User) session.getAttribute("user")).getUserId();
        LoanDAO loanDAO = new LoanDAO();
        FineDAO fineDAO = new FineDAO();
        Loan loan = loanDAO.getActiveLoanByUserAndBook(userId, bookId);
        if (loan == null) {
            response.sendRedirect("CheckAvailabilityServlet");
            return;
        }

        // 1. Check if already renewed
        if (loan.isRenewed()) {
            request.setAttribute("message", "This book has already been renewed once.");
            request.getRequestDispatcher("CheckAvailabilityServlet").forward(request, response);
            return;
        }

        // 2. Calculate fine if current date > borrowDate + 5
        LocalDate today = LocalDate.now();
        LocalDate borrowDate = loan.getBorrowDate().toLocalDate();
        //boolean renewed = loan.isRenewed();
        
        LocalDate dueDate=loan.getDueDate().toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(dueDate, today);
        if(dueDate.isAfter(today)) {
        	double fineAmount = (daysBetween) * 10.0;
            fineDAO.addOrUpdateFine(loan.getLoanId(), fineAmount,0);
        	
        }
        
        //long daysBetween = ChronoUnit.DAYS.between(borrowDate, today);
//        if (daysBetween > 5) {
//        	int a ;
//        	if(renewed) {
//        		a= 5+3;
//        	}
//        	else {
//        		a = 5;
//        	}
//            double fineAmount = (daysBetween - a) * 10.0;
//            fineDAO.addOrUpdateFine(loan.getLoanId(), fineAmount,0); // insert/update logic inside DAO
//        }

        // 3. Renew the loan
        LocalDate newDueDate = borrowDate.plusDays(8);
        loanDAO.renewLoan(loan.getLoanId(), java.sql.Date.valueOf(newDueDate));
        response.sendRedirect("CheckAvailabilityServlet");
	}

}
