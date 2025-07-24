<%@ page import="java.util.List" %>
<%@ page import="model.Loan" %>
<%@ page import="model.User" %>
<%@ page import="model.Book" %>
<%@ page import="model.Fines" %>
<%@ page import="dao.AdminDAO" %>
<%@ page import="dao.BookSDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%User user = (User) session.getAttribute("user");
//List<Book> availableBooks = (List<Book>) request.getAttribute("books");
BookSDAO b = new BookSDAO();
List<Book> availableBooks = b.getAvailableBooks();
%>
<html>
<head>
    <title>Books Returned Today</title>
    <style>
body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #f5f7fa;
  color: #333;
  transition: all 0.3s ease;
}
 
h1, h2 {
  margin-bottom:15px;
  font-weight: 600;
  font-family: 'Segoe UI';
  text-align:center;
}
 
table {
  width: 90%;
  margin: 20px auto;
  border-collapse: collapse;
  background: #fff;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  border-radius: 10px;
  overflow: hidden;
}
 
th, td {
  border-bottom: 1px solid #ddd;
  padding: 14px 18px;
  text-align: left;
}
 
th {
  background-color: #eaeaea;
  font-weight: 600;
}
 
button, .action-button {
  padding: 8px 16px;
  border: none;
  cursor: pointer;
  border-radius: 6px;
  font-size: 14px;
  transition: background-color 0.3s ease;
}
 
.green-button {
  background-color: #28a745;
  color: white;
}
.green-button:hover {
  background-color: #218838;
}
 
.red-button {
  background-color: #dc3545;
  color: white;
}
.red-button:hover {
  background-color: #c82333;
}
 
.yellow-button {
  background-color: #ffc107;
  color: black;
}
.yellow-button:hover {
  background-color: #e0a800;
}
 
.centered {
  text-align: right;
  margin-right: 60px;
  margin-bottom: 30px;
}
 
.top-bar {
  width: 90%;
  margin: 20px auto 10px auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}
 
.search-box input[type="text"] {
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 6px;
  transition: border 0.2s ease;
}
.search-box input[type="text"]:focus {
  border-color: #ffc107;
  outline: none;
}
.search-box input[type="submit"] {
  padding: 8px 14px;
  margin-left: 8px;
  background-color: #ffc107;
  color: black;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.search-box input[type="submit"]:hover {
  background-color: #e0a800;
}
 
.button-link button {
  background-color: #ffc107;
  color: black;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 6px;
  transition: background-color 0.3s ease;
}
.button-link button:hover {
  background-color: #e0a800;
}
 
.toast {
  visibility: hidden;
  min-width: 250px;
  background-color: #4CAF50;
  color: white;
  text-align: center;
  border-radius: 6px;
  padding: 14px 24px;
  position: fixed;
  z-index: 1;
  left: 50%;
  transform: translateX(-50%);
  bottom: 30px;
  font-size: 16px;
  box-shadow: 0px 2px 12px rgba(0, 0, 0, 0.15);
  opacity: 0;
  transition: opacity 0.3s ease, bottom 0.3s ease;
}
 
.toast.show {
  visibility: visible;
  opacity: 1;
  bottom: 40px;
}
 
.header-bar {
  position: fixed;
  top: 0;
  left: 0;
  height: 80px;
  width: 100%;
  background-color: #ffffff;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 24px;
  z-index: 1001;
  box-shadow: 0 2px 6px rgba(0,0,0,0.04);
}
 
.header-bar h2 {
  font-size: 30px;
  margin: 0;
  font-weight: 600;
  color: #333;
  justify-content:center;
  
}
 
.side-menu {
  position: fixed;
  top: 60px;
  left: 0;
  width: 220px;
  height: calc(100% - 60px);
  background-color: #ffffff;
  border-right: 1px solid #ddd;
  padding-top: 20px;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.03);
  z-index: 1000;
}
 
.side-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
 
.side-menu ul li {
  padding: 12px 24px;
  border-bottom: 1px solid #f1f1f1;
}
 
.side-menu ul li a {
  text-decoration: none;
  color: #333;
  display: block;
  font-weight: 500;
  transition: all 0.2s ease;
}
 
.side-menu ul li:hover {
  background-color: #f8f9fa;
  border-radius: 6px;
}
 
.main-content {
  margin-left: 220px;
  padding: 80px 20px 20px 20px;
  transition: margin-left 0.3s ease;
}
 
@keyframes fadein {
  from { bottom: 0; opacity: 0; }
  to { bottom: 40px; opacity: 1; }
}
 
@keyframes fadeout {
  from { bottom: 40px; opacity: 1; }
  to { bottom: 0; opacity: 0; }
}
* {
  transition: background-color 0.3s ease, color 0.3s ease, border 0.2s ease;
}
 
.centered-text {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
.centered {
	text-align: center;
	margin-top: 20px;
}
.navbar {
            background-color: #333;
            overflow: hidden;
            margin-bottom: 20px;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar .right {
            float: right;
        }
 	
</style>
</head>
<body>
<div class="navbar">
    <a href="#">Welcome, Admin <%= user.getName() %>!</a>
    <a href="userStatus.jsp">Check User Status</a>

    <div class="top-bar">
        <div class="button-link">
            <!-- Optional Button content -->
        </div>

        <div class="search-box">
            <form method="POST" action="AdminDailytask">
                <input type="text" name="user_id" placeholder="Search by User ID">
                <input type="submit" value="Search">
            </form>
        </div>
    </div>

    <div class="remove-filter-form">
        <form action="AdminDashBoard.jsp" method="POST">
            <button type="submit" class="red-button">Remove Filter</button>
        </form>
    </div>

    <a href="LogoutServlet">Logout</a>
</div>

 
    <h2>Books Return Requests</h2>

    <%
    List<Loan> loanList;
	
	if(request.getAttribute("user_id")!=null){
		System.out.println("if");
		AdminDAO admin = new AdminDAO();
		loanList=(List<Loan>)request.getAttribute("loanList");
		
	}
	else{
		System.out.println("else");
		AdminDAO admin = new AdminDAO();
		loanList = admin.getBookRequestedForReturnToday();
	}
        /* AdminDAO adminDAO = new AdminDAO();
        List<Loan> loanList = adminDAO.getBookRequestedForReturnToday();
         */
        if (loanList == null || loanList.isEmpty()) {
    %>
        <p>No books have been returned today.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>Loan ID</th>
                <th>User Name</th>
                <th>Book Title</th>
                <th>Fine Amount (₹)</th>
                 <th>Verification</th>
                 
                 
            </tr>
            <%
                for (Loan loan : loanList) {
                	System.out.println(loan.isRequested()+ " "+loan.getReturnDate()+"for");
                if(loan.getReturnDate()==null){
                	//System.out.println("here"+" "+loan.getLoanId()+loan.getUser().getName()+);
            %>
                <tr>
                    <td><%= loan.getLoanId() %></td>
                    <td><%= loan.getUser().getName() %></td>
                    <td><%= loan.getBook().getTitle() %></td>
                    <td><%= loan.getFine().getAmount() %></td>
                  <td><form action="confirmReturn" method="POST"
						style="display: inline;">
						<input type="hidden" name="bookId" value="<%=loan.getLoanId()%>">
						<input type="hidden" name="loan_id" value="<%=loan.getLoanId()%>">
						<input type="hidden" name="book_id" value="<%=loan.getBook().getBookId()%>">
						<button type="submit" class="green-button">Confirm Return</button>
					</form></td>
                </tr>
            <%
                }
                	}
            %>
        </table>
    <%
        }
    %>
    <a href="addbooks.jsp" class="centered-text" style="display: block; margin-top: 20px;">Add more books?</a>
    <% if (!availableBooks.isEmpty()) { %>
	<table>
		<thead>
			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Publisher</th>
				<th>ISBN</th>
				<th>Total Copies</th>
				<th>Available Copies</th>
				<th>Borrow</th>
			</tr>
		</thead>
		<tbody>
			<% for (Book book : availableBooks) { %>
			<tr>
				<td><%= book.getBookId() %></td>
				<td><%= book.getTitle() %></td>
				<td><%= book.getAuthor() %></td>
				<td><%= book.getPublisher() %></td>
				<td><%= book.getIsbn() %></td>
				<td><%= book.getTotalCopies() %></td>
				<td><%= book.getAvailableCopies() %></td>
				<td>
					<form action="BorrowBookServlet" method="post">
						<input type="hidden" name="bookId" value="<%= book.getBookId() %>">
						<button type="submit" class="action-button">Borrow</button>
					</form>
				</td>
			</tr>
			<% } %>
		</tbody>
	</table>
<% } else { %>
	<p class="centered">No available books at the moment.</p>
<% } %>
<%
	
	Boolean returnSuccess = (Boolean) session.getAttribute("returnSuccess");
	if (returnSuccess != null && returnSuccess) {
		
	%>
	<div id="successToast" class="toast">Book return successfully confirmed!</div>
	<%
	}
	session.removeAttribute("returnSuccess");
	%>
	
	<script>
	window.onload = () => {
	  const toast = document.getElementById("successToast");
	  if (toast) {
	    toast.classList.add("show");
	    setTimeout(() => toast.classList.remove("show"), 3000);
	  }
	};
</script>
</body>
</html>
