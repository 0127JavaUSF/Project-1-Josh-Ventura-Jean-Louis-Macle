package com.revature.daos.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.daos.ReimbursementDAO;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementPending;

public class ReimbursementDAOTest {
	ReimbursementDAO dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		 dao = new ReimbursementDAO();
	}

	@After
	public void tearDown() throws Exception {
	}

		/* Getting the reimbursement types */
	  @Test public void getReimbursementTypeTest() { ArrayList<String> roles =
	  dao.getReimbursementType(); boolean isArrayListNotNull=false; boolean
	  isFirstTypeLodging=false; boolean isSecondTypeTravel=false; boolean
	  isThirdTypeFood=false; boolean isFourthTypeOther=false;
	  
	  if (roles == null) fail(); else isArrayListNotNull = true;
	  
	  try { System.out.println(roles); isFirstTypeLodging =
	  (roles.get(0)).equals("Lodging"); isSecondTypeTravel =
	  (roles.get(1)).equals("Travel"); isThirdTypeFood =
	  (roles.get(2)).equals("Food"); isFourthTypeOther =
	  (roles.get(3)).equals("Other");
	  
	  } catch (Exception e) { e.printStackTrace(); }
	  assertTrue(isArrayListNotNull&isFirstTypeLodging&isSecondTypeTravel&
	  isThirdTypeFood&isFourthTypeOther); }
	  
	  /*Getting the reimbursement statuses*/
	
	  @Test public void getReimbursementsStatusTest() { ArrayList<String> statuses
	  = dao.getReimbursementStatus(); boolean isArrayListNotNull=false; boolean
	  isFirstStatusPending=false; boolean isSecondStatusApproved=false; boolean
	  isThirdStatusDenied=false;
	  
	  if (statuses == null) fail(); else isArrayListNotNull = true;
	  
	  try { System.out.println(statuses); isFirstStatusPending =
	  (statuses.get(0)).equals("Pending"); isSecondStatusApproved =
	  (statuses.get(1)).equals("Approved"); isThirdStatusDenied =
	  (statuses.get(2)).equals("Denied"); } catch (Exception e) {
	  e.printStackTrace(); }
	  assertTrue(isArrayListNotNull&isFirstStatusPending&isSecondStatusApproved&
	  isThirdStatusDenied); }
	 
	 
	
	  @Test public void setNewReimbursementTest() { 
		  int userId = 9; 
		  int status = -1; 
		  ReimbursementPending newReimbursementPending = new ReimbursementPending(9,50,80.02, "Hotel night", userId, "Lodging"); 
		  status =  dao.setNewReimbursement(userId, newReimbursementPending); 
		  //executeUpdate returns either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing 
	      // https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#
	      assertTrue(status != -1); }
	 
	  @Test
	  public void getPendingReimbursementsTest() {
		  int userId = 10;
		  Optional<ArrayList<ReimbursementPending>> optionalList = Optional.ofNullable(dao.getPendingReimbursements(userId));
		  
		  if (optionalList.isPresent()) {
			  for (ReimbursementPending e :optionalList.get()) {
				  System.out.println(e.toString());
			  }
			  assertTrue(true);			  
		  }
		  else fail();		  
	  }
	  
	  @Test
	  public void getReimbursementListTest() {
		  ArrayList<Reimbursement> list = dao.getReimbursementList(9,"Pending");
		  System.out.println("\n Test list of reimbursement with pending status.");
		  if (list.size() != 0)
		  {
			  for (Reimbursement e: list) {
				  System.out.println(e.toString());
			  }
			  
			  
		  }
		  System.out.println("\n");
		  assertTrue(list.size() != 0);
		  
	  }
	  
//	  @Test
//	  public void changeReimbursementStatusTest() {
//		  int status = dao.changeReimbursementStatus(25, 8, "Denied");
//		  assertTrue(status != 0);
//	  }
	  
	  
	   //@Test 
/*	  public void getPendingReimbursementListTest() {
		  dao.getReimbursementList(9, "Pending");
		}  
	  */
	  
	  
	 @Test 
	 public void setReceiptTest () { assertTrue(dao.setReceipt("reimbId_UserId.jpg")==1); }
	 
	@Test
	public void getFileFromBlobTest() {assertTrue(dao.getReceipt("reimbId_UserId.jpg")==1);}	
	  
	  /*Test*/
	  @Test 
	  public void show_tablesTest() {
		  dao.show_tables();
		  assertTrue(true);
	  }
	  
		
}
