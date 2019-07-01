package ninja.seppli.learngym.model;

import ninja.seppli.learngym.exception.IllegalNameException;

/**
 *
 * @author jfr and sebi
 *
 */
public class Teacher extends Person {

	/**
	 * Constructor
	 *
	 * @param firstname
	 * @param lastname
	 */
	public Teacher(String firstname, String lastname) {
		super(firstname, lastname);
	}

	public String getShortname() {
		if (getFirstname().length() == 0 || getLastname().length() == 0) {
			throw new IllegalNameException("The teacher \"" + this + "\" has either no first or no lastname");
		}
		return "" + getFirstname().charAt(0) + getLastname().charAt(0);
	}

}
