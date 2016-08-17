package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.IdNotValidException;
import exceptions.StudentNotFoundException;
import persistance.FileResourceHandler;
import persistance.ResourceHandler;
import persistance.Student;



@SuppressWarnings("serial")
@WebServlet("/students/*")
public class Controller extends HttpServlet {
	
	//CONST PARAMETERS  
	
	final String HOME = "http://localhost:8080";
	final String STUDENT_ID_PARAM = "studentId";
	final String STUDENT_NAME_PARAM = "studentName";
	final String STUDENT_GENDER_PARAM = "studentGender";
	final String STUDENT_GRADE_PARAM = "studentGPA";
	
	
	ResourceHandler resourceHandler = new FileResourceHandler();

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try 
		{
			handleRequest(request, response);
		} 
		catch (Exception e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
					"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
					+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
	}
	
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IdNotValidException{
		String stringURL = "";
		if (request.getQueryString()!=null)
			stringURL = request.getRequestURL() + "?" +request.getQueryString();
		else
			stringURL = request.getRequestURL().toString();
		
		System.out.println(stringURL);
		
		
		String requstUrl = request.getRequestURI().toString();

		if (requstUrl.equals("/students/add"))
			addNewStudentRequest(request, response);
		else if (requstUrl.equals("/students/remove"))
			deleteStudentRequest(request, response);
		else if (requstUrl.equals("/students/showStudent"))
			getStudentDetails(request,response);
		else if(requstUrl.equals("/students/fill"))
			fillStudentDB(request,response);
		else if(requstUrl.equals("/students/clear"))
			clearStudentDB(request,response);
		else
			response.sendRedirect(HOME);
	}

	private void fillStudentDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			resourceHandler.fillFileWithRandumValues();
	
			response.getWriter().write("<h1>File is Now full! " +"</h1>" 
					+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			
		} catch (Exception e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
	}

	private void clearStudentDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			resourceHandler.emptyFile();
	
			response.getWriter().write("<h1>File is Now Empty! " +"</h1>" 
					+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			
		} catch (IOException e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}
	}
	
	
	public void getStudentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {			
			String studentId = request.getParameter(STUDENT_ID_PARAM);			
			Student student = resourceHandler.getStudentByID(studentId);
				
			
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



	public void deleteStudentRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {			
			String studentId = request.getParameter(STUDENT_ID_PARAM);			
			resourceHandler.deleteStudent(studentId);
							
			response.getWriter().write("<h1>Student " + studentId + " Has been Removed!" +"</h1>" 
					+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			
		
			} catch (StudentNotFoundException e) {
				response.getWriter().write("<h1>Internal Server Error</h1>" +
											"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
			}
	}

	public void addNewStudentRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
	
		String studentName = request.getParameter(STUDENT_NAME_PARAM);
		String studentId = request.getParameter(STUDENT_ID_PARAM);
		String studentGPA = request.getParameter(STUDENT_GRADE_PARAM);
		String gender = request.getParameter(STUDENT_GENDER_PARAM);
		 
		if(gender==null || gender.equals("M"))
			gender = "male";
		else if (gender.equals("F"))
			gender = "female";
			
		Student student = new Student(studentId, studentName, gender, studentGPA);
		
		resourceHandler.addNewStudent(student);

		response.getWriter().write("<h1>Student " + studentId + " Has been Added!" +"</h1>" 
			+"<h1><a href='http://localhost:8080/'>Home</a></h1>");	
	
		} catch (Exception e) {
			response.getWriter().write("<h1>Internal Server Error</h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Home</a></h1>");
		}		
	}
}

