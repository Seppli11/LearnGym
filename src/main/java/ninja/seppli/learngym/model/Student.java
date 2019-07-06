package ninja.seppli.learngym.model;

/**
 * This class represents a student. The only reason for this class is to
 * differentiate between students and teacher
 *
 * @author jfr and sebi
 *
 */
public class Student extends Person {
	/**
	 * Constructor for JAXB
	 */
	protected Student() {
	}

	/**
	 * Constructor
	 *
	 * @param id        the id
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 */
	protected Student(String id, String firstname, String lastname) {
		super(id, firstname, lastname);
	}

}
