package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import servlets.Controller;

public class StudentsServer {
    
	
	//Main method will configure the server with the home page file,Servlet 
	 public static void main(String[] args) throws Exception
	    {

	        Server server = new Server(8080);
	        
	        String HOME_PATH = "appClientModule/META-INF/home.html";
	        //Resource Handler will handle the deployment of the homePage file. 
	        ResourceHandler resource_handler = new ResourceHandler();
	 
	        resource_handler.setWelcomeFiles(new String[]{ HOME_PATH });
	        resource_handler.setResourceBase(".");
	        
	        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);	
	    	  
	        context.setContextPath("/");
	    	context.addServlet(new ServletHolder(new Controller()),"/students/*");

	        
	        HandlerList handlers = new HandlerList();
	        handlers.setHandlers(new Handler[] { resource_handler,context, new DefaultHandler() });
	        	        
	        server.setHandler(handlers);

	   
	        server.start();
	        server.join();
		 
	    }
	 
}
