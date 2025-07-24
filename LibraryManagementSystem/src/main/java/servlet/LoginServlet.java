package servlet;
import dao.LoginDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		// 1. Get form data
		//System.out.println("hello world");
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        LoginDAO dao = new LoginDAO();
        User result = dao.loginUser(user, role);
        System.out.println("result"+result);
        if (result != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", result);

            if ("user".equalsIgnoreCase(role)) {
            	session.setAttribute("user", result);
                response.sendRedirect("availableBooks.jsp");
            } else if ("admin".equalsIgnoreCase(role)) {
            	session.setAttribute("user", result);
                response.sendRedirect("AdminDashBoard.jsp");
            } else {
                // fallback if unknown role
                request.setAttribute("error", "Invalid role selected.");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

}
