package ninja.seppli.learngym.exception;

/**
 * An exception which is thrown if a method can only function correctly with at
 * least one grade (like average) and there is no grade yet
 *
 * @author sebi
 *
 */
public class NoGradeYetException extends Exception {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 3706280343295428145L;

	/**
	 * Constructor with default message
	 */
	public NoGradeYetException() {
		super("No grade is saved yet");
	}

	/**
	 * Constructor
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public NoGradeYetException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message the message
	 */
	public NoGradeYetException(String message) {
		super(message);
	}

}
