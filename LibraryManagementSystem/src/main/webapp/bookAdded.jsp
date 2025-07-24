<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Added Successfully</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px;
        background-color: #f4f4f4;
        text-align: center;
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        background: #fff;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
        color: #4CAF50; /* Green for success */
        margin-bottom: 20px;
    }
    p {
        font-size: 1.1em;
        line-height: 1.6;
        color: #333;
    }
    .button-group {
        margin-top: 30px;
    }
    .button-group a {
        display: inline-block;
        padding: 10px 20px;
        margin: 0 10px;
        text-decoration: none;
        background-color: #007bff;
        color: white;
        border-radius: 5px;
        transition: background-color 0.3s ease;
    }
    .button-group a:hover {
        background-color: #0056b3;
    }
    .back-link {
        background-color: #6c757d; /* Gray for "back" */
    }
    .back-link:hover {
        background-color: #5a6268;
    }
</style>
</head>
<body>
    <div class="container">
        <h2>Book Added Successfully!</h2>
 
        <%-- Display the success message if available --%>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p><%= message %></p>
        <%
            } else {
        %>
            <p>The book details have been successfully saved to the database.</p>
        <%
            }
        %>
 
        <div class="button-group">
            <a href="addbooks.jsp" class="back-link">Add Another Book</a>
            <%-- Assuming you have an Admin Dashboard --%>
            <a href="AdminDashBoard.jsp">Go to Dashboard</a>
        </div>
    </div>
</body>
</html>