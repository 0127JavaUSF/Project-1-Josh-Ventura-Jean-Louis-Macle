package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() {
		String url = System.getenv("PROJECT1_URL");
		String user = System.getenv("PROJECT1_ROLE");
		String password =  System.getenv("PROJECT1_PASSWORD");
		
		// jdbc:postgresql://host:port/postgres
		try {
			return DriverManager.getConnection(url, user, password);			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
