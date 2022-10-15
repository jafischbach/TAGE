package game;

public class InvalidDirectionException extends RuntimeException {

	public static final long serialVersionUID = 1L;
	
	public InvalidDirectionException(String error) {
		super(error);
	}
	
}
