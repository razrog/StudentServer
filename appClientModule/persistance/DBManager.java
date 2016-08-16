package persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import exceptions.DBFullException;
import exceptions.StudentAlreadyExsitsException;
import exceptions.StudentNotFoundException;

public class DBManager {

	
	final String PATH_TO_DB = "/source/students.csv";
	private HashMap<String,StudentBean> map = null; 
	
	
	
	public DBManager() throws IOException{
		this.map = getAllStudents();
	}

	
	public StudentBean getStudentByID(String id) throws StudentNotFoundException{
		if (!map.containsKey(id))
			throw new StudentNotFoundException("Sorry - Unable to retrive Student");
		
		return map.get(id);
	}
	
	
	public void addNewStudent(StudentBean student) throws StudentAlreadyExsitsException, DBFullException, IOException{
		if (map.containsKey(student.getID()))
			throw new StudentAlreadyExsitsException("Student Already Exists In The System");
		if(map.size()>=1000)
			throw new DBFullException("Cannot add new Student (MAX 1000)"); 
		
		map.put(student.getID(), student);		

		writeToDB(map);
	}
	
	public void deleteStudent(String id) throws StudentNotFoundException, IOException{
		if (!map.containsKey(id))
			throw new StudentNotFoundException("Sorry - We don't have Student with that ID");
		map.remove(id);
		writeToDB(map);
	}
	
	public void writeToDB(HashMap<String, StudentBean> newMap) throws IOException {
		File file = open();
		StudentBean student;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,false)); 
		Set<String> keys = map.keySet();
		for(String key : keys)
		{
			 student = map.get(key);
			 bw.write(student.getID() + "\t" + student.getName() + "\t" + student.getGender() + "\t" + student.getGP());
			 bw.newLine();
		 
		}
		bw.close();
	}

	@SuppressWarnings("resource")
	public HashMap<String, StudentBean> getAllStudents() throws IOException {
		File file = open();
		
		HashMap<String,StudentBean> map = new HashMap<>();
		
		String id,name,gender;
		double gpa;
		String line = null;
		String splitBy = "\t"; 
		
		BufferedReader rd = new BufferedReader(new FileReader(file)); 
		while((line = rd.readLine())!=null){
			if(line.equals(""))
				break;
			String [] data = line.split(splitBy);
			 id = data[0];
			 name = data[1];
			 gender = data[2];
			 gpa = Double.parseDouble(data[3]);
			 
			 map.put(id, new StudentBean(id, name, gender, gpa));
		}
		return map;
	}

	private File open() throws IOException {
		return new File(new java.io.File(".").getCanonicalPath()+ PATH_TO_DB);
	}
	
	
	public void fillDB () throws StudentAlreadyExsitsException, DBFullException, IOException{
		int db_size = map.size();
		int id = 99999993;
		while(db_size<1000){
			addNewStudent(new StudentBean(id+"", "Israel Israeli", "F", 52));
			id-=1;
			db_size+=1;
		}
	}	
		
	
	public void clearDB() throws IOException{
		map.clear();
		writeToDB(map);
	}
	
	
	
	
	
}
