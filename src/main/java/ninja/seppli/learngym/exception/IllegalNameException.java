package ninja.seppli.learngym.exception;

/**
 * This exception is thrown if an name is not legal
 * 
 * @author sebi
 *
 */
public class IllegalNameException extends RuntimeException {

	/**
	 * Constructor
	 * 
	 * @param message the message
	 */
	public IllegalNameException(String message) {
		super(message);
	}

}
