package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookSDAO;
import dao.LoanDAO;

/**
 * Servlet implementation class ConfirmReturnServlet
 */
@WebServlet("/confirmReturn")
public class ConfirmReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmReturnServlet() {
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
		int loanId = Integer.parseInt(request.getParameter("loan_id"));
		int bookId = Integer.parseInt(request.getParameter("book_id"));
 
		LocalDate currentDate = LocalDate.now();
 
		LoanDAO loan = new LoanDAO();
		boolean updatedReturnDate = loan.updateReturnDate(loanId, currentDate); // return date is inserted in loans
																				// table
		BookSDAO book = new BookSDAO();
		boolean incrementedAvailableCopies = book.incrementAvailableCopies(bookId);
 
		if (updatedReturnDate && incrementedAvailableCopies) {
			request.getSession().setAttribute("returnSuccess", true);
		} else {
			request.getSession().setAttribute("returnSuccess", false);
		}
		response.sendRedirect("AdminDashBoard.jsp"); // redirected to another .jsp
 
	}



}