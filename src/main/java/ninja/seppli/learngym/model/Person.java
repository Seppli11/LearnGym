package ninja.seppli.learngym.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

import ninja.seppli.learngym.saveload.JaxbSaverLoader;

/**
 *
 * @author jfr and sebi
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
	@XmlID
	private String id;
	private String firstname;
	private String lastname;

	protected Person() {
	}

	/**
	 * Constructor
	 *
	 * @param id        the id
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 */
	protected Person(String id, String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * Used in {@link JaxbSaverLoader} to identify persons uniquly
	 *
	 * @return the id of this person
	 */
	public String getId() {
		return id;
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
