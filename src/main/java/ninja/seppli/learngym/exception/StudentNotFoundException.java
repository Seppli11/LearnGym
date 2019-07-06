package ninja.seppli.learngym.exception;

/**
 * This exception is thrown if a student couldn't be found. It is a runtime
 * exception because you should check if an student exists in a
 * course/subject/... thus it doesn't need to be handled by the caller.<br>
 * Because a few function deal with primitives, returning null doens't work
 * either
 *
 * @author sebi
 *
 */
public class StudentNotFoundException extends RuntimeException {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

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
