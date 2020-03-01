package com.revature.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementPending;
import com.revature.util.ConnectionUtil;

public class ReimbursementDAO {
	/***/
	public ArrayList<String> getReimbursementType(){
		ArrayList<String> types = new ArrayList<String>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();
			String sql = "SELECT REIMB_TYPE FROM ERS_REIMBURSEMENT_TYPE;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				String role = result.getString("REIMB_TYPE");
				types.add(role);
			}
			result.close();
			statement.close();
			connection.close();	
			return types;
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		catch(NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");		
		}
		
		return null;
	}
	/**/
	public ArrayList<String> getReimbursementStatus(){
		ArrayList<String> statuses = new ArrayList<String>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();
			String sql = "SELECT REIMB_STATUS FROM ERS_REIMBURSEMENT_STATUS;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				String role = result.getString("REIMB_STATUS");
				statuses.add(role);
			}
			result.close();
			statement.close();
			connection.close();	
			return statuses;
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		catch(NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");		
		}
		
		return null;
	}
	
	public int setNewReimbursement(int userId, ReimbursementPending newReimbursementPending) {
		int status = 0;
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();	
			//CASTING TO THE MONEY TYPE
			// https://www.postgresql.org/docs/9.1/datatype-money.html
			String sql = "INSERT INTO ERS_REIMBURSEMENT(REIMB_AMOUNT, REIMB_DESCRIPTION, REIMB_SUBMITTED, REIMB_AUTHOR, REIMB_STATUS_ID, REIMB_TYPE_ID) VALUES(?::NUMERIC::MONEY,?,CURRENT_TIMESTAMP,?,1,(SELECT reimb_type_id FROM ers_reimbursement_type WHERE reimb_type = ?));";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDouble(1,newReimbursementPending.getReimbursementAmount());
			statement.setString(2, newReimbursementPending.getReimbursementDescription());
			statement.setInt(3, userId);
			statement.setString(4, newReimbursementPending.getReimbursementType());			
			status = statement.executeUpdate();
			statement.close();
			connection.close();
			return status;
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		catch(NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");		
		}
		return status;	
	}
	
	public ArrayList<ReimbursementPending>  getPendingReimbursements(int userId) {
		ArrayList<ReimbursementPending> list = new ArrayList<ReimbursementPending>();
		try {
			Connection connection = ConnectionUtil.getConnection();
			if (connection == null) throw new NullConnectionException();
			String sql = "SELECT ERS_REIMBURSEMENT.REIMB_ID, ERS_REIMBURSEMENT.REIMB_AMOUNT::NUMERIC, ERS_REIMBURSEMENT.REIMB_DESCRIPTION, ERS_REIMBURSEMENT_TYPE.reimb_type FROM ERS_REIMBURSEMENT JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id WHERE REIMB_AUTHOR=?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet result = statement.executeQuery();
			while (result.next()) {				
				ReimbursementPending pendingReimbursement = new ReimbursementPending(userId, Integer.parseInt(result.getString("REIMB_ID")), new Double(result.getString("REIMB_AMOUNT")), result.getString("REIMB_DESCRIPTION"),userId, result.getString("reimb_type"));
				list.add(pendingReimbursement);
			}
			result.close();
			statement.close();
			connection.close();	
			return list;
		}
		catch(SQLException e) {
			e.printStackTrace();			
		}
		catch(NullConnectionException e) {
			System.err.println("ConnectionUtil.getConnection() returned null;");		
		}
		
		return list;
	}
	
	 //Getting all reimbursements by status	
	  public ArrayList<Reimbursement> getReimbursementList(int userId, String  status){ 
		  ArrayList<Reimbursement> list = new ArrayList<Reimbursement>(); 
		  try { 
			  Connection connection = ConnectionUtil.getConnection(); 
			  if (connection ==  null) throw new NullConnectionException(); 
			  String sql =  "SELECT REIMB_ID, REIMB_AMOUNT::NUMERIC, REIMB_DESCRIPTION, ERS_REIMBURSEMENT_TYPE.reimb_type,ERS_USERS.ers_users_id ,ERS_USERS.user_last_name||' , '||ERS_USERS.user_first_name AS author_name, ERS_REIMBURSEMENT.reimb_submitted, ERS_REIMBURSEMENT.reimb_resolved, (SELECT ERS_Users.ers_users_id AS resolver_id FROM ERS_USERS WHERE ERS_USERS.ers_users_id = ERS_REIMBURSEMENT.reimb_resolver ), (SELECT ERS_USERS.user_last_name||' , '||ERS_USERS.user_first_name AS resolver_name FROM ERS_USERS WHERE ERS_USERS.ers_users_id = ERS_REIMBURSEMENT.reimb_resolver ) FROM ERS_REIMBURSEMENT JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.reimb_type_id = ERS_REIMBURSEMENT_TYPE.reimb_type_id JOIN ERS_USERS ON ERS_USERS.ers_users_id = ERS_REIMBURSEMENT.reimb_author JOIN ers_reimbursement_status ON ers_reimbursement_status.reimb_status_id = ERS_REIMBURSEMENT.reimb_status_id  WHERE ers_reimbursement_status.reimb_status =? AND REIMB_AUTHOR=?;"; 
			  PreparedStatement statement = connection.prepareStatement(sql);
			  statement.setString(1,status); 
			  statement.setInt(2, userId); 
			  ResultSet result = statement.executeQuery(); 
			  while (result.next()) { 
				  Reimbursement reimbursement = new Reimbursement(Integer.parseInt(result.getString("REIMB_ID")), new Double(result.getString("REIMB_AMOUNT")),  result.getString("REIMB_DESCRIPTION"), result.getString("REIMB_TYPE"),result.getInt("ers_users_id"),result.getString("author_name"),result.getString("reimb_submitted"), result.getInt("resolver_id"),result.getString("resolver_name"),result.getString("reimb_resolved"),status );
				  
	  			  list.add(reimbursement); 
	  			}	  
			  result.close(); statement.close(); connection.close(); return list; }
	  
		  catch(SQLException e) { e.printStackTrace(); } 
		  catch(NullConnectionException  e) { System.err.println("ConnectionUtil.getConnection() returned null;"); }
		  return null; }
	
	
	/* TODO get all reimbursement for all employees .*/
	  public HashMap<Integer, ArrayList<ArrayList<Reimbursement>>> getAllUsersAllReimbursements() { 
		  HashMap<Integer, ArrayList<ArrayList<Reimbursement>>> data = new HashMap<Integer, ArrayList<ArrayList<Reimbursement>>>();
		  ArrayList<Integer> userIdList = new ArrayList<Integer>();
		  //for all users, get all reimbursements
		  	//selection of all employees' user ids
		  try {
			  Connection connection = ConnectionUtil.getConnection();
			  if (connection ==  null) throw new NullConnectionException(); 
			  String sql ="SELECT ERS_USERS_ID FROM ERS_USERS WHERE USER_ROLE_ID = '1';";
			  PreparedStatement statement = connection.prepareStatement(sql);
			  ResultSet result = statement.executeQuery(); 		  
			  while (result.next()) {
				  userIdList.add(Integer.parseInt(result.getString("ERS_USERS_ID")));				  
			  }
			  result.close(); statement.close(); connection.close(); 
		  
		  	//selection of all types of reimbursement status and storage in 3 different ArrayList.
			  for (Integer id: userIdList) {
				  ArrayList<Reimbursement> pendingList = getReimbursementList(id.intValue(), "Pending");
				  ArrayList<Reimbursement> approvedList = getReimbursementList(id.intValue(), "Approved");
				  ArrayList<Reimbursement> deniedList = getReimbursementList(id.intValue(), "Denied");
				  ArrayList<ArrayList<Reimbursement>> allLists = new ArrayList<ArrayList<Reimbursement>>();
				  allLists.add(pendingList);
				  allLists.add(approvedList);
				  allLists.add(deniedList);
				  data.put(id,allLists);
			  }
			  return data;
		  }
		  catch(SQLException e) { e.printStackTrace(); } 
		  catch(NullConnectionException  e) { System.err.println("ConnectionUtil.getConnection() returned null;"); }
		  
		  return null;
	  }
	  	  
	 /* Changing reimbursement status */ 
	  public int changeReimbursementStatus(int reimb_id, int resolver_id, String status) {
		  int returnCode = -1;
		  try { 
			  Connection connection = ConnectionUtil.getConnection(); 
			  if (connection ==  null) throw new NullConnectionException(); 
			  String sql =  "UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVED=CURRENT_TIMESTAMP, REIMB_STATUS_ID=(SELECT ERS_REIMBURSEMENT_STATUS.reimb_status_id FROM ERS_REIMBURSEMENT_STATUS WHERE ERS_REIMBURSEMENT_STATUS.reimb_status=? ), REIMB_RESOLVER=?  WHERE reimb_id =?;"; 
			  PreparedStatement statement = connection.prepareStatement(sql);
			  statement.setString(1,status); 
			  statement.setInt(2,resolver_id); 
			  statement.setInt(3,reimb_id); 
				 
			  returnCode = statement.executeUpdate(); 
			   
			  statement.close(); connection.close(); return returnCode; }
	  
		  catch(SQLException e) { e.printStackTrace(); } 
		  catch(NullConnectionException  e) { System.err.println("ConnectionUtil.getConnection() returned null;"); }
		  
		  return returnCode;
	  	}
		  	  
	
	public int setReceipt(String fileId) {
		int status = -1;
		
		try {
			System.setProperty(SDKGlobalConfiguration.ENABLE_S3_SIGV4_SYSTEM_PROPERTY, "true");
			AWSCredentials credentials = new BasicAWSCredentials(System.getenv("S3_KEY_ID"), System.getenv("S3_KEY_ACCESS"));
			AmazonS3 s3client = new AmazonS3Client(credentials);
			String bucketName = "ersfiles";
			String uploadFolder = System.getProperty("user.dir")+"/src/main/resources/receipts_upload/";
			s3client.putObject(new PutObjectRequest(bucketName, fileId, new File(uploadFolder+fileId)));
			status = 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			
		}
		return status;
	}
	
	public int setReceipt(String fileId, String fileURL) {
		int status = -1;
		
		try {
			System.setProperty(SDKGlobalConfiguration.ENABLE_S3_SIGV4_SYSTEM_PROPERTY, "true");
			AWSCredentials credentials = new BasicAWSCredentials(System.getenv("S3_KEY_ID"), System.getenv("S3_KEY_ACCESS"));
			AmazonS3 s3client = new AmazonS3Client(credentials);
			String bucketName = "ersfiles";
			String uploadFolder = System.getProperty("user.dir")+"/src/main/resources/receipts_upload/";
			s3client.putObject(new PutObjectRequest(bucketName, fileId, new File(uploadFolder+fileId)));
			status = 1;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			
		}
		return status;
	}
	
	public int getReceipt(String fileId) {
		/*
		 * https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3.html#getObject-com.amazonaws.services.s3.model.GetObjectRequest-
		 * Use the data from the input stream in Amazon S3 object as soon as possible
		 * Read all data from the stream (use GetObjectRequest.setRange(long, long) to request only the bytes you need) 
		 * Close the input stream in Amazon S3 object as soon as possible
		 */
		int status = -1;
		String uploadFolder = System.getProperty("user.dir")+"/src/main/resources/receipts_download/";
		
		try {
			
			AWSCredentials credentials = new BasicAWSCredentials(System.getenv("S3_KEY_ID"), System.getenv("S3_KEY_ACCESS"));
			AmazonS3 s3client = new AmazonS3Client(credentials);
			String bucketName = "ersfiles";
			s3client.getObject(new GetObjectRequest(bucketName, fileId), new File(uploadFolder+fileId));
			
			status = 1;
				
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return status;
	}
	
	/*test*/
	public void show_tables() {
		Connection connection = ConnectionUtil.getConnection();
		Statement ps;
		try {
			
			String sql = "SELECT table_schema,table_name FROM information_schema.TABLES WHERE table_schema='public' ORDER BY table_schema,table_name;";
			ps = connection.createStatement();
			ResultSet result = ps.executeQuery(sql);
			System.out.println("\nListing of the database tables.");
			while (result.next()) {				
				System.out.println(result.getString("table_name"));
			}
			result.close();
		    ps.close();	
		    connection.close();
		    System.out.println("\n");
		}		
		catch (SQLException e) {
			e.printStackTrace();	
		}
		
	}
	

}
