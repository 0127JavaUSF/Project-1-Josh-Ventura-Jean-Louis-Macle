package com.revature.daos.junit;



import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.daos.UserDao;



public class UserDaoTest {
	UserDao dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao  = new UserDao();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getUserRolesTest() {
		ArrayList<String> roles = dao.getUserRoles();
		boolean isArrayListNotNull=false;
		boolean isFirstRoleEMPLOYEE=false;
		boolean isSecondRoleMANAGER=false;
		 if (roles == null) fail();
		 else isArrayListNotNull = true;
		
		try {
			System.out.println(roles);
			isFirstRoleEMPLOYEE = (roles.get(0)).equals("EMPLOYEE");
			isSecondRoleMANAGER = (roles.get(1)).equals("MANAGER");
		}
		catch (Exception e) {
			e.printStackTrace();		
		}
		assertTrue(isArrayListNotNull&isFirstRoleEMPLOYEE&isSecondRoleMANAGER);
	}
	
	@Test
	public void getUserRoleTest() {
		String role = dao.getUserRole(9);
		System.out.println(role);
		assertTrue(role.equals("EMPLOYEE"));
	
	}

}
