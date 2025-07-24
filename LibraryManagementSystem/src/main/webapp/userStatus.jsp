<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="model.Loan, java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check User Status</title>
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	margin: 0;
	padding: 40px 20px;
	display: flex;
	flex-direction: column;
	align-items: center;
    background-color: #f5f7fa;
    color: #333;
    transition: all 0.3s ease;
}
}
 
.container {
	width: 100%;
	max-width: 900px;
	background-color: #ffffff;
	padding: 40px;
	border-radius: 12px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}
 
form {
	margin-bottom: 30px;
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 12px;
	flex-wrap: wrap;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
}
 
form input[type="text"] {
	padding: 10px 14px;
	border: 2px solid #aaa;
	border-radius: 8px;
	font-size: 16px;
	width: 250px;
}
 
form input[type="submit"] {
	background-color: #28a745;
	color: white;
	padding: 10px 20px;
	border: none;
	border-radius: 8px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s ease;
}
 
form input[type="submit"]:hover {
	background-color: #1f7a35;
}
 
h2 {
	color: #333;
	margin-bottom: 20px;
}
 
.user-info {
  font-size: 18px;
  font-weight: 600;
  margin: 20px auto;
  color: #333333;             
  background-color: #f0f0f0;  
  padding: 12px 20px;
  border-left: 5px solid #555555;
  border-radius: 8px;
  max-width: 400px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  user-select: none;
  text-align: center;
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
  padding: 14px 18px;
  border-bottom: 1px solid #ddd;
  text-align: left;
}
 
th {
  background-color: #eaeaea;
  color: #333;
  font-weight: 600;
}
 
tr:hover {
  background-color: #f4f9ff;
}
 
 
p {
	font-size: 16px;
	color: #555;
	margin-top: 20px;
}
</style>
</head>
 
<body>
 
	<h2>Check User Status</h2>
	
	<form method="POST" action="CheckUserStatusServlet">
		Enter User ID:<input type="text" name="user_id" />
		 <input type="submit" value="Check Status" />
	</form>
	
	<%
	List<Loan> activeLoans = (List<Loan>) request.getAttribute("active_loans");
 
	boolean formSubmitted = (activeLoans != null);
	if (formSubmitted) {
		
		String username = null;
		if (request.getAttribute("username") != null) {
			username = request.getAttribute("username").toString();
		}
		%>
		<div class="user-info">User Name: <%=username%></div>
	<%
		if (request.getAttribute("username") != null && !activeLoans.isEmpty()) {
	%>
	<table border="1">
		<tr>
			<th>Loan Id</th>
			<th>Book Title</th>
			<th>Borrow Date</th>
			<th>Due Date</th>
			<th>Fine</th>
		</tr>
		<%
		int totalFine=0;
		for (Loan loan : activeLoans) {
			totalFine+=(int)loan.getFine().getAmount();
			boolean isOverdue = loan.getDueDate().before(new java.util.Date()); //if due date is before current date, then book is overdue
		%>
		<tr>
			<td><%=loan.getLoanId()%></td>
			<td><%=loan.getBook().getTitle()%></td>
			<td><%=loan.getBorrowDate()%></td>
			<td ><%=loan.getDueDate()%><%
			    if (isOverdue) {
			  %>
			    <span style="color:crimson; font-weight:bold;">(Overdue)</span>
			  <%
			    }
			  %>
			</td>
			<td><%=loan.getFine().getAmount()%></td>
			
		</tr>
		
		<%
		}
		%>
		<tr>
			<td colspan="4"><b>Total Fines</b>
			<td><span style="color:red; font-weight:bold;">₹<%=totalFine %></span>
		</tr>
	</table>
	<%
	} else {
	%>
	<p>No active loans for this user.</p>
	<%
	}
	}
	%>
</body>
</html>
