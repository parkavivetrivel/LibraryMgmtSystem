<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
</head>
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
    input[type="password"], input[type="text"] {
        width: 92%;
        padding: 12px;
        margin-top: 8px;
        margin-bottom: 16px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
    }
    
    h3{
    text-align:center;
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
<body>
 
<div class="container">
    <form action="register" method="post">
    	 <h3>Register</h3>
        <input type="text" name="name" placeholder="Full Name" required />
        <input type="email" name="email" placeholder="Email Address" required />
        <input type="text" name="phone" placeholder="Phone Number" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Register</button>
        <div  style="padding-top:15px; text-align:center;">
        	<a href="Login.jsp" style="font-size:14px">Already have an account? Log in!</a>
        </div>
    </form>
</div>
 
</body>
</html>
 
 