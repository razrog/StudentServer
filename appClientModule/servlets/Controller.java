package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DBFullException;
import exceptions.GradeNotValidException;
import exceptions.IdNotValidException;
import exceptions.StudentAlreadyExsitsException;
import exceptions.StudentNotFoundException;
import persistance.DBManager;
import persistance.StudentBean;


@SuppressWarnings("serial")
@WebServlet("/students/*")
public class Controller extends HttpServlet {
	final String HOME = "http://localhost:8080";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		DBManager manager = new DBManager();

		String stringURL = "";
		if (request.getQueryString()!=null)
			stringURL = request.getRequestURL() + "?" +request.getQueryString();
		else
			stringURL = request.getRequestURL().toString();
		
		System.out.println(stringURL);
		
		
		String requstUrl = request.getRequestURI().toString();

		if (requstUrl.equals("/students/add"))
			addNewStudentRequest(request, response,manager);
		else if (requstUrl.equals("/students/remove"))
			deleteStudentRequest(request, response, manager);
		else if (requstUrl.equals("/students/showStudent"))
			getStudentDetails(request,response,manager);
		else if(requstUrl.equals("/students/fill"))
			fillStudentDB(request,response,manager);
		else if(requstUrl.equals("/students/clear"))
			clearStudentDB(request,response,manager);
		else
			response.sendRedirect(HOME);
	}

	private void fillStudentDB(HttpServletRequest request, HttpServletResponse response, DBManager manager) throws ServletException, IOException {

		try {
			manager.fillDB();
			request.setAttribute("message","DB Was succesfully been Full");
			response.sendRedirect(HOME);
			
			
		} catch (StudentAlreadyExsitsException | DBFullException | IOException e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
	}

	private void clearStudentDB(HttpServletRequest request, HttpServletResponse response, DBManager manager) throws ServletException, IOException {
		try {
			manager.clearDB();
			response.sendRedirect(HOME);
			
		} catch (IOException e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
	}
	
	
	public void getStudentDetails(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {			
			String studentId = request.getParameter("studentId");			
			StudentBean student = manager.getStudentByID(studentId);
				
			
			response.getWriter().write("<h1>" +student.getName()+ " Details</h1>" +
					"<table>"
					+ " <tr><th>ID</th><td>" + student.getID() + "</td></tr>"
					+ "<tr><th>Full Name</th><td>" + student.getName() + "</td></tr>"
					+ "<tr><th>Gender</th><td>" + student.getGender() + "</td></tr>"
					+ "<tr><th>Grade</th><td>" + student.getGP() + "</td></tr>"
					+ "</table>"
					+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
			
		
			} catch (StudentNotFoundException e) {
				response.getWriter().write("<h1>Internal Server Error</h1>" +
											"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
											+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			}
	}



	public void deleteStudentRequest(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {			
			String studentId = request.getParameter("studentId");			
			manager.deleteStudent(studentId);
				
			alert(response,"Student Was Succesfully Removed");
			
			response.sendRedirect(HOME);
		
			} catch (StudentNotFoundException e) {
				response.getWriter().write("<h1>Internal Server Error</h1>" +
											"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			}
	}

	public void addNewStudentRequest(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {
	
		String studentName = request.getParameter("studentName");
		String studentId = request.getParameter("studentId");
		String studentGPA = request.getParameter("studentGPA");
		String gender = request.getParameter("studentGender");
		 
		if(gender==null || gender.equals("M"))
			gender = "male";
		else if (gender.equals("F"))
			gender = "female";
			
		StudentBean student = new StudentBean();
		student.setID(studentId);
		student.setGender(gender);
		student.setName(studentName);
		student.setGP(studentGPA);
		
		manager.addNewStudent(student);
			
		alert(response,"Student Was Succesfully Added");
		
		response.sendRedirect(HOME);

			
			
		} catch (StudentAlreadyExsitsException | DBFullException | IdNotValidException | GradeNotValidException e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
		
	}

public void alert(HttpServletResponse response,String message) throws IOException{
	PrintWriter out = response.getWriter();  
	response.setContentType("text/html");  
	out.println("<script type=\"text/javascript\">");  
	out.println("alert('"+message +"');");  
	out.println("</script>");
	
}





		

	
	
}

