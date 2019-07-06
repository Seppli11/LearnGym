package ninja.seppli.learngym.model;

/**
 *
 * @author jfr and sebi
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

	/**
	 * Returns the full name concated from the first and last name
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return getFirstname() + " " + getLastname();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Person other = (Person) obj;
		if (firstname == null) {
			if (other.firstname != null) {
				return false;
			}
		} else if (!firstname.equals(other.firstname)) {
			return false;
		}
		if (lastname == null) {
			if (other.lastname != null) {
				return false;
			}
		} else if (!lastname.equals(other.lastname)) {
			return false;
		}
		return true;
	}

}
