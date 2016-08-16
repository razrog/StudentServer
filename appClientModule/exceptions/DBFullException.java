package exceptions;

@SuppressWarnings("serial")
public class DBFullException extends Exception{

	public DBFullException(String message){
		super(message);
	}
	
}
