# StudentServer


#Deploy Server

1. git clone https://github.com/razrog/StudentServer.git
2. cd StudentServer/ 
3. java -jar startServer.jar 
3. open http://localhost:8080/ 

## Introduction
Server that manage students

The Server will Add / Remove / Show  Student from file. 

## Main Requests : 
	• /students/add?studentId=1234&studentName=Israel Israeli&studentGPA=90 
		○ Adds New Student to the system - Only 'studentID ' is mandatory
	• /students/remove?studentId=1234
		○ Removes student from System by given id
	• /students/showStudent?studentId=1234
		○ Get Student details by given id
	• /students/fill 
		○ Fill All the data file with random values
	• /students/clear
		○ Empty the File

## Logic 
In order to load fast response to each request. The system was build in such way: 

	Caching Students into Map Object - {Key : ID , Value : Student (Object)}
	Thread - write the map to Disk every 10 second (Should be 1 minute , but for the testing purpose it was set into 10 seconds)

# Components
## Server 
 Jetty Server was used in order to deploy a quick java application on server. 
 
	1.loads file resources (home.html)
	2.loads servlets to the server (controller.java) 
	3.Starts Server 
	
##Student - basic Student Object
Used to write/return a student from the System
	· StudentID
	· StudentName
	· Student Gender
	· StudentGPA

##ResourceHandler (interface ) 
Has all the server methods 

##FileResourceHandler  
Created to deal with the File writing/deleting methods

Contains : idToStudentMap - Cunncurent Map Object (String id , Student student)

saveToFileEveryMinute  Method - creates new Thread which writes the Map to the File every 10 seconds

**All Methods** (AddNewStudent , deleteStudent , getStudent) - Are being handle with the Map methods.  


##Controller (Servlet) 
###Server's Main & Only Servlet
Handle every request that the Server gets 

Handle Server responses
