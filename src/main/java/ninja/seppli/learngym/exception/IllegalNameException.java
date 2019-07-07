package ninja.seppli.learngym.exception;

/**
 * This exception is thrown if an name is not legal
 *
 * @author sebi
 *
 */
public class IllegalNameException extends RuntimeException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 8545894827276084321L;

	/**
	 * Constructor
	 *
	 * @param message the message
	 */
	public IllegalNameException(String message) {
		super(message);
	}

}
