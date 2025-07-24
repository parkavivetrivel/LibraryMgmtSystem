<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    body {
        font-family: "Segoe UI", sans-serif;
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
 
    form {
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        width: 350px;
    }
 
    input[type="email"],
    input[type="password"] {
        width: 90%;
        padding: 12px;
        margin-top: 8px;
        margin-bottom: 16px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
    }
 
    label {
        font-weight: 500;
        color: #333;
        margin-bottom: 6px;
        display: block;
    }
 
    input[type="radio"] {
        margin-right: 6px;
        margin-bottom: 10px;
    }
 
    button {
        width: 100%;
        background-color: #28a745;
        color: white;
        padding: 12px;
        border: none;
        border-radius: 6px;
        font-size: 15px;
        cursor: pointer;
        transition: background-color 0.2s ease;
    }
 
    button:hover {
        background-color: #218838;
    }
</style>
 
 
</head>
<body>
<div style="display:flex; flex-direction:column; justify-content:center;">
<form action="LoginServlet" method="post">
        <h3 style="display:flex; justify-content:center;">Login</h3>
        <input type="email" name="email" placeholder="Email Address" required />
        <input type="password" name="password" placeholder="Password" required /><br>
        <label>Select Role:</label><br>
    	<input type="radio" name="role" value="user" required /> User
   		<input type="radio" name="role" value="admin" required /> Librarian<br><br>
        <button type="submit">Login</button>
        <div  style="padding-top:15px; text-align:center;">
        <a href="SignIn.jsp" style="font-size:14px">Don't have an account yet? Sign up!</a>
        </div>
    </form>
   </div>
</body>
</html>
 