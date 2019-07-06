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
	 * Constructor
	 *
	 * @param firstname
	 * @param lastname
	 */
	public Student(String firstname, String lastname) {
		super(firstname, lastname);
	}

}
