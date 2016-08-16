package exceptions;

@SuppressWarnings("serial")
public class IdNotValidException extends Exception{

	public IdNotValidException(String message){
		super(message);
	}
}
