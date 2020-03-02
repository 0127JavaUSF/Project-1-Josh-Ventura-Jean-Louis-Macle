package com.revature.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ReimbursementDAO;
import com.revature.models.Reimbursement;

/**
 * Servlet implementation class ServletChangeStatus
 */
public class ServletChangeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletChangeStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * You must add this to each servlet you create OR you can isolate this code to
		 * a Filter. (See servlet API filters for details)
		 * 
		 * If you have done it correctly and are STILL getting CORS errors, here are two
		 * important things to check:
		 * 
		 * * Are your allow headers allowing the right things? Check the error message
		 * on the browser closely.
		 * 
		 * * Is your URL correct? If you're not getting routed to a servlet then the
		 * headers will not be added.
		 */

		resp.addHeader("Access-Control-Allow-Headers", "authorization");
		resp.addHeader("Access-Control-Allow-Methods", "GET POST PUT DELETE");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

		// TODO Auto-generated method stub
		super.service(req, resp);

		// doPost(req, resp); //test
	}

	@Override
	public void init() throws ServletException {
		System.out.println("Servlet initializing");
		// https://tomcat.apache.org/tomcat-7.0-doc/jndi-datasource-examples-howto.html
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] reimb_id = request.getParameterValues("reimbIds");
		String status = request.getParameter("status");
		HttpSession session = request.getSession();
		int resolver_id= ((Integer)(session.getAttribute("userId"))).intValue();
		

		ReimbursementDAO dao = new ReimbursementDAO();
		List<Reimbursement> list = dao.changeReimbursementStatus(reimb_id, resolver_id, status);
		response.setStatus(200);

		ObjectMapper om = new ObjectMapper();
		om.writeValue(response.getWriter(), list);


	}

}
