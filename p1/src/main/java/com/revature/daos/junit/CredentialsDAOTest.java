package com.revature.daos.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.daos.CredentialsDAO;

public class CredentialsDAOTest {
	CredentialsDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new CredentialsDAO();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getUserRoleIdTest1() {
		ArrayList<Integer> ids  = new  ArrayList<Integer>();
		ids.add(0, new Integer(9));
		ids.add(1, new Integer(1));
		assertTrue(ids==dao.getUserIdAndRoleId("jl1", "pwd1"));
	}
	
	@Test
	public void getUserRoleIdTest2() {
		ArrayList<Integer> ids  = new  ArrayList<Integer>();
		ids.add(0, new Integer(8));
		ids.add(1, new Integer(2));
		assertTrue(ids==dao.getUserIdAndRoleId("jl2", "pwd2"));
	}

}
