package servlet;
import dao.BookSDAO;
import dao.FineDAO;
import model.Book;
import model.Loan;
import model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

@WebServlet("/CheckAvailabilityServlet")
public class CheckAvailabilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckAvailabilityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession(false);
		//User user = (User) session.getAttribute("user");

		HttpSession session = request.getSession(false);

		// Check for null session
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");
			return;
		}

		User user = (User) session.getAttribute("user");
		User addborrowerUser = new User();
		addborrowerUser.setUserId(user.getUserId());

		BookSDAO dao = new BookSDAO();
		List<Book> availableBooks = dao.getAvailableBooks();
		List<Loan> borrowedBooks = dao.getBorrowedBooks(addborrowerUser);

		// ✅ Fine auto-update logic
		FineDAO fineDAO = new FineDAO();
		LocalDate today = LocalDate.now();
		for (Loan loan : borrowedBooks) {
			LocalDate dueDate = loan.getDueDate().toLocalDate();
			if (today.isAfter(dueDate)) {
				long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
				double fineAmount = daysOverdue * 10.0;
				fineDAO.addOrUpdateFine(loan.getLoanId(), fineAmount, 0);
			} else {
				// Optional: remove/reset fine if not overdue
				fineDAO.addOrUpdateReturnFine(loan.getLoanId(), 0);
			}
		}

		// ✅ Pass data to JSP
		request.setAttribute("books", availableBooks);
		request.setAttribute("borrowedBooks", borrowedBooks);

		try {
			List<Loan> fineList = fineDAO.getLoansWithFinesByUserId(user.getUserId());
			request.setAttribute("fineList", fineList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("availableBooks.jsp");
		dispatcher.forward(request, response);
	}

	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("post do ");	
		}

}
