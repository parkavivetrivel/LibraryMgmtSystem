package servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Get form data
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");

		// 2. Create User object
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(password);

		// 3. Save to DB using DAO
		UserDAO dao = new UserDAO();
		boolean result = dao.registerUser(user);

		// 4. Redirect based on result
		if (result) {
			response.sendRedirect("Login.jsp");
		} else {
			//response.sendRedirect("jsp/error.jsp");
		}
	}
}
