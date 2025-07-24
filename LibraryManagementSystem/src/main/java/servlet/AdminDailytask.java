package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dao.BookSDAO;
import model.Book;
import model.Loan;

/**
 * Servlet implementation class AdminDailytask
 */
@WebServlet("/AdminDailytask")
public class AdminDailytask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDailytask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		AdminDAO admin = new AdminDAO();
		List<Loan> borrowedBooks = admin.getBookRequestedForReturnToday();
		BookSDAO dao = new BookSDAO();
		List<Book> availableBooks = dao.getAvailableBooks();
		request.setAttribute("books", availableBooks);
		//request.setAttribute("BookRequestedForReturnToday", borrowedBooks);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		AdminDAO admin = new AdminDAO();
 
		if (userId != null || !userId.isEmpty()) {
			List<Loan> loanList = admin.getBookRequestedForUserId(Integer.parseInt(userId));
			System.out.println(loanList+"ll");
			request.setAttribute("loanList", loanList);
			request.setAttribute("user_id", userId);
		}
		
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("AdminDashBoard.jsp");
		requestDispatcher.forward(request, response);
	}

}
