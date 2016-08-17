package exceptions;

@SuppressWarnings("serial")
public class GradeNotValidException extends Exception{
	public GradeNotValidException(String message){
		super(message);
	}

}
