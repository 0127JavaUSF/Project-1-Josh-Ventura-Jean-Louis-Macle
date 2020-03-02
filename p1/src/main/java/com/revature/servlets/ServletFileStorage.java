package com.revature.servlets;

import java.io.File;
import java.io.IOException;
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
		doGet(request, response);
		
		ReimbursementDAO rdao = new ReimbursementDAO();
		//ASSUMPTION: the file is a jpg file
		String fileId = request.getParameter("userId")+"_"+request.getParameter("reimbId")+".jpg";
		response.getWriter().append(LocalDateTime.now()+"\n");
		response.getWriter().append("File Id is:"+fileId+"\n");
		response.getWriter().append(request.getParameter("file")+"\n");
		//http://commons.apache.org/proper/commons-fileupload/using.html
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		ServletFileUpload fileUpload = new ServletFileUpload();
		//Iterator on the items uploaded
		
		FileItemIterator items;
		try {
			items = fileUpload.getItemIterator(request);
			// iterate items
			String uploadFolder = System.getProperty("user.dir")+"/src/main/resources/receipts_upload/";
		    while (items.hasNext()) {
		    	System.out.println("1 element found in items.");
		        FileItem item = (FileItem)items.next();
		        item.write(new File(uploadFolder,"uploaded.jpg"));
		    }
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		
		
	    
	    /*
	    List fileItems = upload.parseRequest(req);
	    // assume we know there are two files. The first file is a small
	    // text file, the second is unknown and is written to a file on
	    // the server
	    Iterator i = fileItems.iterator();
	    String comment = ((FileItem)i.next()).getString();
	    FileItem fi = (FileItem)i.next();
	    // file name on the client
	    String fileName = fi.getName();
	    // save comment and file name to database
	    ...
	    // write the file
	    fi.write(new File("/www/uploads/", fileName));
		*/
		//rdao.setReceipt(fileId);
	}

}
