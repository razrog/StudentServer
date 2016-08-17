package persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.DBFullException;
import exceptions.GradeNotValidException;
import exceptions.IdNotValidException;
import exceptions.StudentAlreadyExsitsException;
import exceptions.StudentNotFoundException;



public class FileResourceHandler implements ResourceHandler{
	final String PATH_TO_FILE = "/source/students.tsv";
	
	private Map<String, Student> idtoStudentMap = new ConcurrentHashMap<>() ; 
	
	public FileResourceHandler() {
		try 
		{
			//Loads all student from file to map
			addAllStudentsToMap();
		} 
		catch (IOException | IdNotValidException | GradeNotValidException e) 
		{
			e.printStackTrace();
		}
		saveToFileEveryMinute();
	}
	//Save to file method will use a Thread which will save the map(Students) to the Disk every 10 second
	private void saveToFileEveryMinute() {
		Thread thread = new Thread()
	    {
	        public void run()
	        {
	        	while(true){
		            try 
		            {
		                Thread.sleep(10 * 1000);
		                writeToFile();
		            }
		            catch (Exception e) 
		            {
		                e.printStackTrace();
		            }
	        	}
	        }
	    };
	    thread.start();	
	}

	public Student getStudentByID(String id) throws StudentNotFoundException{
		if (!idtoStudentMap.containsKey(id))
			throw new StudentNotFoundException("Sorry - Unable to retrive student with the given ID");
		
		return idtoStudentMap.get(id);
	}
	
	public void addNewStudent(Student student) throws StudentAlreadyExsitsException, DBFullException, IOException{
		if (idtoStudentMap.containsKey(student.getID()))
			throw new StudentAlreadyExsitsException("Student Already Exists In The System");
		if(idtoStudentMap.size()>=1000)
			throw new DBFullException("Cannot add new Student (MAX 1000)"); 
	
		idtoStudentMap.put(student.getID(), student);
   	}
	
	public void deleteStudent(String id) throws StudentNotFoundException, IOException{
		if (!idtoStudentMap.containsKey(id))
			throw new StudentNotFoundException("Sorry - Unable to retrive student with the given ID");
		idtoStudentMap.remove(id);
	}
	
	private void writeToFile() throws IOException {
		File file = open();
		Student student;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,false)); 
		Set<String> keys = idtoStudentMap.keySet();
		for(String key : keys)
		{
			 student = idtoStudentMap.get(key);
			 bw.write(student.getID() + "\t" + student.getName() + "\t" + student.getGender() + "\t" + student.getGP());
			 bw.newLine();
		}
		bw.close();
	}

	public void addAllStudentsToMap() throws IOException, IdNotValidException, GradeNotValidException {
		File file = open();
		
		String id,name,gender, gpa;
		String line = null;
		String splitBy = "\t"; 
		
		BufferedReader reader = new BufferedReader(new FileReader(file)); 
		while((line = reader.readLine())!=null){
			if(line.equals(""))
				break;
			String [] data = line.split(splitBy);
			 id = data[0];
			 name = data[1];
			 gender = data[2];
			 gpa = data[3];
			 
			 idtoStudentMap.put(id, new Student(id, name, gender, gpa));
		}
		reader.close();
	}

	private File open() throws IOException {
		return new File(new java.io.File(".").getCanonicalPath()+ PATH_TO_FILE);
	}
	
	public void fillFileWithRandumValues () throws StudentAlreadyExsitsException, DBFullException, IOException, IdNotValidException, GradeNotValidException{
		int db_size = idtoStudentMap.size();
		int id = 99999993;
		while(db_size<1000){
			addNewStudent(new Student(id+"", "Israel Israeli", "Female", "52"));
			id-=1;
			db_size+=1;
		}
	}	
	
	public void emptyFile() throws IOException{
		idtoStudentMap.clear();
		writeToFile();
	}
}
