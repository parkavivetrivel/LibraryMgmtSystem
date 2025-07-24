package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoanDAO;
import model.Loan;
import model.User;

/**
 * Servlet implementation class CheckUserStatusServlet
 */
@WebServlet("/CheckUserStatusServlet")
public class CheckUserStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUserStatusServlet() {
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
		LoanDAO loans=new LoanDAO();
		List<Loan> activeLoanList = new ArrayList<>();
		int userId=Integer.parseInt(request.getParameter("user_id"));
		activeLoanList=loans.getActiveLoanByUserId(userId); //gets all borrowed books for user
		
		if(activeLoanList != null && !activeLoanList.isEmpty()) {
			Loan firstLoan=activeLoanList.get(0); //retrieves first obj in loanList
			User user=firstLoan.getUser(); //retrieves user obj from activeLoan
			String username=user.getName(); //retrieves username
			request.setAttribute("username", username);
			
		}else {
			System.out.println("No active loans found");
		}
		
		request.setAttribute("active_loans",activeLoanList);
		
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("userStatus.jsp");
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 

 
	}

}
