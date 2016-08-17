package persistance;

import exceptions.GradeNotValidException;
import exceptions.IdNotValidException;

public class Student {
	
	
	final String DEFAULT_NAME = "Israel Israeli";
	final String DEFAULT_GPA = "90"; 
	final String DEFAULT_GENDER = "female";
	
	private String id;
	private String name;
	private String gender;
	private String gpa ;
	
	
	
	public Student(String id,String name,String gender ,String gpa) throws IdNotValidException, GradeNotValidException{
		if(!isIdValid(id)){
			this.id = id;	
		}else{
			throw new IdNotValidException("You must pass Valid ID - Not Empty & Only Numbers ");
		}
		if(!isEmptyString(name)){
			this.name = name;
		}else{
			this.name = DEFAULT_NAME;
		}
		if (!isEmptyString(gpa))
		 {
			if(!isValidGPA(gpa))
				throw new GradeNotValidException("You Must Pass A valid Grade - only numbers (Example - 98.5)");
			else
				this.gpa=gpa;
			 
		 }
		else{
			this.gpa = DEFAULT_GPA;
		}
		if(!isEmptyString(gender)){
			this.gender = gender;
		}else{
			this.gender = DEFAULT_GENDER;
		}
	}
	
	private boolean isValidGPA(String gpa) {
		return gpa.matches("[0-9]+");
	}

	private boolean isEmptyString(String value) {
		return value == null || value.trim().equals("");
	}

	private boolean isIdValid(String id) {
		return id==null || id.equals("") || !id.matches("[0-9]+");
	}

	public Student(){}
	
	public String getID() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void sestName(String name) {
		if(name.equals(""))
			this.name = "Israel Israeli";
		else
			this.name = name;
	}
	
	public String getGP() {
		return gpa;
	}
	
	public String getGender() {
		return gender;
	}
}
