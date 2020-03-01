package com.revature.daos;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionUtil;

public class CredentialsDAO {

	/* UNIQUE CONSTRAINT sets on the username. Returns the clientid */
	public ArrayList<Integer>  getUserIdAndRoleId(String username, String password) {
		
		Connection connection = ConnectionUtil.getConnection();
		String sql = "SELECT ERS_USERS_ID, USER_ROLE_ID FROM ERS_USERS WHERE ERS_USERNAME = ? AND ERS_PASSWORD_HASH = (SELECT crypt(?, (SELECT ERS_PASSWORD_SALT FROM ERS_USERS WHERE ERS_USERNAME=?)));";
		int userId=0;//not null constraint on the cientId
		int user_role_id=0;
		ArrayList<Integer> userIdAndRoleId = new ArrayList<Integer>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, username);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				userId = result.getInt("ERS_USERS_ID");		
				user_role_id = result.getInt("USER_ROLE_ID");	
				userIdAndRoleId.add(0, new Integer(userId));
				userIdAndRoleId.add(1, new Integer(user_role_id));
				System.out.println("Authentication successful.");
			
				return 	userIdAndRoleId;		
			}	
			result.close();
			statement.close();
			connection.close();	
		}
		catch(SQLException e)
		{		
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			System.err.println("NullPointerException. Is that a connection issue?");
		
		}
		return userIdAndRoleId;
	}
	
}
