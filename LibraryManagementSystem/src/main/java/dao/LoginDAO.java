package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.DBConnection;

public class LoginDAO {
	public User loginUser(User user,String role) {
        boolean isUser = false;
        User auth_user = null;
       System.out.println("in login DAO");
        try (Connection conn = DBConnection.getConnection()) {
        	 String sql;
        	 if ("user".equals(role)) {
         	    sql = "SELECT user_id, name, email FROM users WHERE email = ? AND password = ?";
         	} else {
         	    sql = "SELECT admin_id, name, email FROM admins WHERE email = ? AND password = ?";
         	}
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();
           
                        // If a record exists, login is successful
            if (rs.next()) {
            	auth_user = new User();
            	if("user".equals(role)) {
            	auth_user.setUserId(rs.getInt("user_id"));
            	}
            	else {
            		auth_user.setUserId(rs.getInt("admin_id"));
            	}
            	auth_user.setName(rs.getString("name"));
            	auth_user.setEmail(rs.getString("email"));
            	//auth_user.setName(rs.getString("phone"));
            	
                isUser = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth_user;
    }

	public LoginDAO() {
		// TODO Auto-generated constructor stub
	}

}
