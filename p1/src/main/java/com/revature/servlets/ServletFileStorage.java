package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.revature.daos.ReimbursementDAO;

/**
 * Servlet implementation class ServletFileStorage
 */
public class ServletFileStorage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFileStorage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReimbursementDAO rdao = new ReimbursementDAO();
		
		for (String key :request.getParameterMap().keySet()) System.out.println(request.getParameterMap().get(key));
		
		//http://commons.apache.org/proper/commons-fileupload/using.html
		// Check that we have a file upload request
		ServletFileUpload fileUpload = new ServletFileUpload();
		//Iterator on the items uploaded		
		FileItemIterator items;
		try {
			items = fileUpload.getItemIterator(request);
			// iterate items
			String uploadFolder ="C:/_Revature/Projects/Project1/p1/src/main/resources/receipts_upload/";
			String fieldName = "";
			String userId = "";
			String reimbId = "";
		    while (items.hasNext()) {
		    	System.out.println("1 element found in items.");		    	
		    	FileItemStream itemStream = items.next();
		    	fieldName = itemStream.getFieldName();		    	
		    	System.out.println("Field name: "+fieldName);
		    	switch (fieldName) {
		    	case "userId": userId = fieldName; break;
		    	case "reimbId": 
		    		reimbId = fieldName;  
		    		String name = itemStream.getName();
		    		System.out.println("name:"+name);
		    		break;
		    	case "file": 
		    		 InputStream is = itemStream.openStream();
		    		 String file_Path = uploadFolder+userId+"_"+reimbId+".jpg";
		    		 System.out.println(file_Path);
				     FileUtils.copyInputStreamToFile(is, new File(uploadFolder,"file_stored.jpg"));
				     break;		    	
		    	}		      
		    }
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		
	}

}
