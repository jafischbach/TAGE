package game;

public class InvalidLabelException extends RuntimeException {

	public static final long serialVersionUID = 1L;
	
	public InvalidLabelException(String error) {
		super(error);
	}
	
}
