package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BookSDAO;
import model.Book;
/**
 * Servlet implementation class AddBookServlet
 */
@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookServlet() {
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
		// ✅ Retrieve form input values
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String isbn = request.getParameter("isbn");
            int totalCopies = Integer.parseInt(request.getParameter("totalCopies"));
 
            // ✅ Available copies same as total at start
            int availableCopies = totalCopies;
 
            // ✅ Create a Book object and populate it
            Book book = new Book();
            book.setBookId(bookId);
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setIsbn(isbn);
            book.setTotalCopies(totalCopies);
            book.setAvailableCopies(availableCopies);
 
            // ✅ Use DAO to save the book to DB
            BookSDAO bookDAO = new BookSDAO();
            boolean success = bookDAO.addBook(book);
 
            if (success) {
                // Redirect or forward with success message
                request.setAttribute("message", "Book added successfully!");
                request.getRequestDispatcher("bookAdded.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Failed to add book. It may already exist.");
                request.getRequestDispatcher("addbooks.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle parsing errors or DB issues
            request.setAttribute("message", "Error adding book: " + e.getMessage());
            request.getRequestDispatcher("addbooks.jsp").forward(request, response);
        }
		
	}

}
