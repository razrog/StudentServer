package persistance;

import java.io.IOException;

import exceptions.DBFullException;
import exceptions.GradeNotValidException;
import exceptions.IdNotValidException;
import exceptions.StudentAlreadyExsitsException;
import exceptions.StudentNotFoundException;

public interface ResourceHandler {
	public Student getStudentByID(String id) throws StudentNotFoundException;
	public void addNewStudent(Student student) throws StudentAlreadyExsitsException, DBFullException, IOException;
	public void deleteStudent(String id) throws StudentNotFoundException, IOException;
	public void fillFileWithRandumValues() throws StudentAlreadyExsitsException, DBFullException, IOException, IdNotValidException, GradeNotValidException;
	public void emptyFile() throws IOException;
	
}
