package ninja.seppli.learngym.model;

/**
 * 
 * @author jfr
 *
 */
public class Person {
	private String firstname;
	private String lastname;

	/**
	 * Constructor
	 * 
	 * @param firstname
	 * @param lastname
	 */
	public Person(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * 
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * 
	 * @return
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * 
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}
