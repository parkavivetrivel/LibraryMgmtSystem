package servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookSDAO;
import dao.LoanDAO;
import exception.BookAlreadyBorrowedException;
import exception.BookLimitExceededException;
import model.User;
import model.Book;
import model.Loan;

@WebServlet("/BorrowBookServlet")
public class BorrowBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBookServlet() {
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
		//HttpSession session = request.getSession(false);
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

		// ✅ Check if session or user is null
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// ✅ Retrieve user from session
		User user = (User) session.getAttribute("user");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		User addbookUser = new User();
		Book addbookBook = new Book();
		addbookUser.setUserId(user.getUserId());
		addbookBook.setBookId(bookId);
		
		
		// ✅ Get bookId from form input
		

		//System.out.println("User: " + user.getName() + " wants to borrow Book ID: " + bookId);
		
		LoanDAO loanDAO = new LoanDAO();
		
		
		// TODO: Now call DAO to check borrow count and allow borrowing if < 4
		// e.g., BookSDAO.borrowBook(user.getUserId(), bookId);
		try {
			boolean b =loanDAO.addBorrow( addbookUser,addbookBook);
			System.out.println(b);
		} catch (BookLimitExceededException e) {
		    session.setAttribute("error", e.getMessage());
		    System.out.println(e.getMessage());
		} catch (BookAlreadyBorrowedException e) {
		    session.setAttribute("error", e.getMessage());
		    System.out.println(e.getMessage());
		}
		
		//System.out.println("borrowedBooks"+borrowedBooks);
		// After borrowing
		//request.setAttribute("borrowedBooks", borrowedBooks);
		request.getRequestDispatcher("availableBooks.jsp").forward(request, response);

		//response.sendRedirect("CheckAvailabilityServlet");		
	}

}
