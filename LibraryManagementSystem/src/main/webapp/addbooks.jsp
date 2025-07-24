<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Entry Form</title>
    <style>
     body {
        font-family: "Segoe UI", sans-serif;
        background-color: #f0f2f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        flex-direction: column;
    }
 
    form {
        background-color: #ffffff;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        width: 350px;
    }
 
    input[type="email"],
    input[type="password"], input[type="text"], input[type="number"] {
        width: 92%;
        padding: 12px;
        margin-top: 8px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
    }
    
    h2 {
        text-align: center;
        margin-bottom: 20px;
        margin-top:50px;
    }
 
    label {
        font-weight: 500;
        color: #333;
        margin-bottom: 6px;
        display: block;
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

        * {
            transition: background-color 0.3s ease, color 0.3s ease, border 0.2s ease;
        }
    </style>
</head>
<body>

    <h2>Enter Book Details</h2>

    <form action="AddBookServlet" method="post">
        <input type="text" name="bookId" placeholder="Book ID" required />
        <input type="text" name="title" placeholder="Title" required />
        <input type="text" name="author" placeholder="Author" required />
        <input type="text" name="publisher" placeholder="Publisher" required />
        <input type="text" name="isbn" placeholder="ISBN" required />
        <input type="number" name="totalCopies" placeholder="Total Copies" min="1" required />
        <button type="submit">Submit Book</button>
    </form>

    <%-- Toast message rendering --%>
    <% 
        String message = (String) request.getAttribute("message");
        if (message != null && !message.trim().isEmpty()) { 
    %>
        <div id="successToast" class="toast"><%= message %></div>
    <% } %>

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
