package persistance;

import exceptions.IdNotValidException;

public class StudentBean {
	
	private String id;
	private String name;
	private String gender;
	private double gpa ;
	
	
	public StudentBean(String id,String name,String gender ,double gpa){
		
		this.id = id; 
		this.name = name; 
		this.gender = gender; 
		this.gpa = gpa; 
	}
	public StudentBean(){}
	
	
	
	public String getID() {
		return id;
	}
	public void setID(String iD) throws IdNotValidException {
		if (iD==null || iD.equals(""))
			throw new IdNotValidException("You must pass Valid ID");
		id = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name.equals(""))
			this.name = "Israel Israeli";
		else
			this.name = name;
	}
	public double getGP() {
		return gpa;
	}
	public void setGP(String gP) {
		if(gP.equals(""))
			gpa = 90;
		else
			gpa = Double.parseDouble(gP);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	} 
	
	
	
	
	

}
