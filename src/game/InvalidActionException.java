package game;

public class InvalidActionException extends RuntimeException {

	public static final long serialVersionUID = 1L;
	
	public InvalidActionException(String msg) {
		super(msg);
	}
	
}
