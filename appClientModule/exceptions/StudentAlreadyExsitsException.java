package exceptions;


@SuppressWarnings("serial")
public class StudentAlreadyExsitsException extends Exception{
	public StudentAlreadyExsitsException(String message){
		super(message);
	}

}
