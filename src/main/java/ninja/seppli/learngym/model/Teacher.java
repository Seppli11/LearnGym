package ninja.seppli.learngym.model;

import ninja.seppli.learngym.exception.IllegalNameException;

/**
 * Represents a teacher which of course is also a person
 *
 * @author jfr and sebi
 *
 */
public class Teacher extends Person {
	/**
	 * Constructor for JAXB
	 */
	protected Teacher() {
	}

	/**
	 * Constructor
	 *
	 * @param id        the id
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 */
	protected Teacher(String id, String firstname, String lastname) {
		super(id, firstname, lastname);
	}

	/**
	 * Returns the shortname of the teacher. It is generated from the first letter
	 * of the last and first name
	 *
	 * @return the shortname
	 */
	public String getShortname() {
		if (getFirstname().length() == 0 || getLastname().length() == 0) {
			throw new IllegalNameException("The teacher \"" + this + "\" has either no first or no lastname");
		}
		return "" + getFirstname().charAt(0) + getLastname().charAt(0);
	}

}
