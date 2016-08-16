package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DBFullException;
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
		DBManager manager = new DBManager();
		
		String message = (String) request.getAttribute("message");
		if(message!=null || message!=""){
			alert(response, message);
		}
		
		String requstUrl = request.getRequestURL().toString();
		
		
		if (requstUrl.contains("add"))
			addNewStudentRequest(request, response,manager);
		else if (requstUrl.contains("delete"))
			deleteStudentRequest(request, response, manager);
		else if (requstUrl.contains("show"))
			getStudentDetails(request,response,manager);
		else if(requstUrl.contains("fill"))
			fillStudentDB(request,response,manager);
		else if(requstUrl.contains("clear"))
			clearStudentDB(request,response,manager);
		else
			response.sendRedirect(HOME);

	}
	
	

	private void fillStudentDB(HttpServletRequest request, HttpServletResponse response, DBManager manager) throws ServletException, IOException {
		System.out.println(request.getRequestURL());
		try {
			manager.fillDB();
			request.setAttribute("message","DB Was succesfully been Full");
			response.sendRedirect(HOME);
			
			
		} catch (StudentAlreadyExsitsException | DBFullException | IOException e) {
			response.getWriter().write("<h1>Error in Server </h1>" +
					"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
					+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
					e.printStackTrace();
		}
	}

	private void clearStudentDB(HttpServletRequest request, HttpServletResponse response, DBManager manager) throws ServletException, IOException {
		System.out.println(request.getRequestURL());
		try {
			manager.clearDB();
			response.sendRedirect(HOME);
			
		} catch (IOException e) {
			response.getWriter().write("<h1>Error in Server </h1>" +
					"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
					+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
					e.printStackTrace();
		}
	}
	
	

//TODO!!!!!!
	//TODO!!!!!!
	//TODO!!!!!!
	public void getStudentDetails(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {
			System.out.println(request.getRequestURL());
			
			String studentId = request.getParameter("studentId");			
			StudentBean student = manager.getStudentByID(studentId);
				
			String json = new Gson().toJson(student);
			
			response.getWriter().write(json);

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
			
		    response.sendRedirect(HOME);

		
			} catch (StudentNotFoundException e) {
				response.getWriter().write("<h1>Error in Server </h1>" +
											"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
											+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
				e.printStackTrace();
			}
	}

	
	
	public void deleteStudentRequest(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {
			System.out.println(request.getRequestURL());
			
			String studentId = request.getParameter("studentId");			
			manager.deleteStudent(studentId);
				
			alert(response,"Student Was Succesfully Removed");
			
			response.sendRedirect(HOME);
		
			} catch (StudentNotFoundException e) {
				response.getWriter().write("<h1>Error in Server </h1>" +
											"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
											+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
				e.printStackTrace();
			}
	}

	public void addNewStudentRequest(HttpServletRequest request, HttpServletResponse response,DBManager manager) throws ServletException, IOException{
		try {
		System.out.println("[INFO]controller");		
		System.out.println(request.getRequestURL());
			
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

			
			
		} catch (StudentAlreadyExsitsException | DBFullException | IdNotValidException e) {
			response.getWriter().write("<h1>Error in Server </h1>" +
										"<h2>[INFO]\t"+ e.getMessage() +"</h2>"
										+"<h1><a href='http://localhost:8080/'>Go Home</a></h1>");
			e.printStackTrace();
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

