package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionUtil;

public class UserDao {
	
	
	public ArrayList<String> getUserRoles() {
		ArrayList<String> roles = new ArrayList<String>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();
			String sql = "SELECT user_role FROM ERS_USER_ROLES;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				String role = result.getString("user_role");
				roles.add(role);
			}
			result.close();
			statement.close();
			connection.close();	
			return roles;			
		}
		catch(SQLException e) {
			e.printStackTrace();		
		}		
		catch (NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");
		}
		return null;
	}
	
	public String getUserRole(int userId) {
		String role = "init";
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();
			String sql = "SELECT ERS_USER_ROLES.user_role FROM ERS_USER_ROLES JOIN ers_users ON ers_users.user_role_id = ERS_USER_ROLES.ers_user_role_id WHERE ers_users.ers_users_id =?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				role = result.getString("user_role");
				return role;
			}
			result.close();
			statement.close();
			connection.close();	
			return role;			
		}
		catch(SQLException e) {
			e.printStackTrace();		
		}		
		catch (NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");
		}
		return null;
	}

}
