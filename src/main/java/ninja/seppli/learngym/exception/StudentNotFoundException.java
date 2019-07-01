package ninja.seppli.learngym.exception;

/**
 * This exception is thrown if a student couldn't be found
 * 
 * @author sebi
 *
 */
public class StudentNotFoundException extends Exception {

	/**
	 * Constructor
	 * 
	 * @param message the message
	 * @param cause   the cause
	 */
	public StudentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * 
	 * @param message the message
	 */
	public StudentNotFoundException(String message) {
		super(message);
	}

}
