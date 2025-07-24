<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Book, model.User, model.Loan, model.Fines"%>

<%
User user = (User) session.getAttribute("user");

if (user == null) {
	response.sendRedirect("Login.jsp");
	return;
}

List<Book> availableBooks = (List<Book>) request.getAttribute("books");
List<Loan> fineList = (List<Loan>) request.getAttribute("fineList");
double totalFine = 0;

if (availableBooks == null) {
	response.sendRedirect("CheckAvailabilityServlet");
	return;
}

List<Loan> loans = (List<Loan>) request.getAttribute("borrowedBooks");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library - Book Availability</title>
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f9f9f9;
	color: #333;
}
 
h1, h2 {
	text-align: center;
	color: #2c3e50;
	margin-top: 20px;
}
 
table {
	width: 90%;
	margin: 20px auto;
	border-collapse: collapse;
	background-color: #ffffff;
	box-shadow: 0 0 8px rgba(0, 0, 0, 0.05);
	border-radius: 6px;
	overflow: hidden;
}
 
th, td {
	padding: 12px 15px;
	text-align: left;
	border-bottom: 1px solid #e0e0e0;
}
 
th {
	background-color: #f0f0f0;
	font-weight: 600;
}
 
tr:hover {
	background-color: #f9f9f9;
}
 
.action-button {
	padding: 6px 12px;
	background-color: #28a745;
	color: #fff;
	border: none;
	cursor: pointer;
	border-radius: 4px;
	font-size: 14px;
	transition: background-color 0.2s ease;
}
 
.action-button:hover {
	background-color: #218838;
}
 
.action-button[style*="background-color: #dc3545;"] {
	background-color: #dc3545 !important;
}
 
.action-button[style*="background-color: #dc3545;"]:hover {
	background-color: #c82333 !important;
}
 
.action-button[style*="background-color: #ffc107;"] {
	background-color: #ffc107 !important;
	color: #000 !important;
}
 
.action-button[style*="background-color: #ffc107;"]:hover {
	background-color: #e0a800 !important;
}
 
.centered {
	text-align: center;
	padding: 15px;
	color: #666;
}
 
.top-right {
	position: absolute;
	top: 20px;
	right: 20px;
}
 
.red-button {
	background-color: #dc3545;
	color: white;
	border: none;
	padding: 10px 16px;
	border-radius: 6px;
	font-size: 14px;
	cursor: pointer;
	transition: background-color 0.2s ease;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}
 
.red-button:hover {
	background-color: #c82333;
}
.centered-text {
            text-align: center;
            margin-top: 20px;
            color: red;
        }
 
 
</style>
 
</head>
<body>

<div class="top-right">
	<form action="Login.jsp" method="POST">
		<button type="submit" class="red-button">Log Out</button>
	</form>
</div>
 
<h1 style="padding-top:50px;">Welcome, <%= user.getName() %>!</h1>
<h2>Available Books</h2>

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
	<p class="centered ">No available books at the moment.</p>
<% } %>
<%
String error = (String) session.getAttribute("error");
if (error != null) {
%>
    <div class="alert alert-danger centered-text"><%= error %></div>
<%
    session.removeAttribute("error"); // So it doesn't persist
}
%>

<%
String message = (String) session.getAttribute("message");
if (message != null) {
%>
    <div class="alert alert-success"><%= message %></div>
<%
    session.removeAttribute("message");
}
%>


<h2>Borrowed Books</h2>
<table>
	<tr>
		<th>Loan ID</th>
		<th>Book ID</th>
		<th>Title</th>
		<th>Borrow Date</th>
		<th>Due Date</th>
		<th>Renewed</th>
		<th>Actions</th>
	</tr>
	<% if (loans != null && !loans.isEmpty()) {
		for (Loan loan : loans) {
		if(loan.getReturnDate()==null){
			Book book = loan.getBook();
	%>
	<tr>
		<td><%= loan.getLoanId() %></td>
		<td><%= book.getBookId() %></td>
		<td><%= book.getTitle() %></td>
		<td><%= loan.getBorrowDate() %></td>
		<td><%= loan.getDueDate() %></td>
		<td><%= loan.isRenewed() ? "Renewed" : "Not Renewed" %></td>
		<td>
			<% if (!loan.isRequested()) { %>
				<!-- Return Button -->
				<form action="ReturnBookServlet" method="post" style="display:inline;">
					<input type="hidden" name="bookId" value="<%= book.getBookId() %>">
					<button type="submit" class="action-button" style="background-color: #dc3545;">Return</button>
				</form>

				<!-- Renew Button (only if not already renewed) -->
				<% if (!loan.isRenewed()) { %>
				<form action="RenewBookServlet" method="post" style="display:inline;">
					<input type="hidden" name="bookId" value="<%= book.getBookId() %>">
					<button type="submit" class="action-button" style="background-color: #ffc107; color: black;">Renew</button>
				</form>
				<% } %>
			<% } 
			else { %>
				<span style="color: gray;">Requested</span>
			<% } %>
		</td>
	</tr>
	<% } else { %>
	<tr>
		<td colspan="7" class="centered">No borrowed books found.</td>
	</tr>
	<% }}} %>
</table>

<h2>User Fine Details</h2>
<% if (fineList != null && !fineList.isEmpty()) { %>
	<table>
		<tr>
			<th>Loan ID</th>
			<th>User ID</th>
			<th>Book Title</th>
			<th>Fine Amount (₹)</th>
		</tr>
		<% for (Loan loan : fineList) {
			Fines fine = loan.getFine();
			if(fine.getAmount()>0){
			Book book = loan.getBook();
			//Fines fine = loan.getFine();
			double fineAmount = (fine != null) ? fine.getAmount() : 0.0;
			totalFine += fineAmount;
			
		%>
		<tr>
			<td><%= loan.getLoanId() %></td>
			<td><%= loan.getUserId() %></td>
			<td><%= (book != null ? book.getTitle() : "N/A") %></td>
			<td>₹ <%= fineAmount %></td>
		</tr>
		<% }} %>
		<tr>
			<td colspan="3"><strong>Total Fine</strong></td>
			<td><strong>₹ <%= totalFine %></strong></td>
		</tr>
	</table>
<% } else { %>
	<p class="centered">No fines for this user yet.</p>
<% } %>

</body>
</html>
