package ninja.seppli.learngym.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ninja.seppli.learngym.saveload.JaxbLoader;

/**
 *
 * @author jfr and sebi
 *
 */
public class Person {
	/**
	 * the id property
	 */
	private ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();
	/**
	 * the firstname property
	 */
	private StringProperty firstname = new SimpleStringProperty();
	/**
	 * the lastname property
	 */
	private StringProperty lastname = new SimpleStringProperty();

	/**
	 * the fullname binding which automaticly updates if the firstname or lastname
	 * changes
	 */
	private StringBinding fullnameBinding = new StringBinding() {
		{
			super.bind(firstnameProperty(), lastnameProperty());
		}

		@Override
		protected String computeValue() {
			return getFirstname() + " " + getLastname();
		}
	};

	/**
	 * jaxb constructor
	 */
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
		this.id.set(id);
		setFirstname(firstname);
		setLastname(lastname);
	}

	/**
	 * Used in {@link JaxbLoader} to identify persons uniquly
	 *
	 * @return the id of this person
	 */
	@XmlElement
	@XmlID
	public String getId() {
		return id.get();
	}

	/**
	 * Sets the id of the person.<br>
	 * <b>DON'T CALL THIS FUNCTION!!!<b> It is intendet for jaxb
	 *
	 * @param id
	 */
	public void setId(String id) {
		this.id.set(id);
	}

	/**
	 * Returns the read only id property
	 *
	 * @return the property
	 */
	public ReadOnlyStringProperty idProperty() {
		return id.getReadOnlyProperty();
	}

	/**
	 * Returns the first name
	 *
	 * @return the first name
	 */
	@XmlElement
	public String getFirstname() {
		return firstname.get();
	}

	/**
	 * Returns the firstname property
	 *
	 * @return the prop
	 */
	public StringProperty firstnameProperty() {
		return firstname;
	}

	/**
	 * Sets the first name of the person
	 *
	 * @param firstname the firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname.set(firstname);
	}

	/**
	 * Returns the last name
	 *
	 * @return the last name
	 */
	@XmlElement
	public String getLastname() {
		return lastname.get();
	}

	/**
	 * returns the lastname property
	 *
	 * @return the lastname property
	 */
	public StringProperty lastnameProperty() {
		return lastname;
	}

	/**
	 * Sets the last name of the person
	 *
	 * @param lastname the lastname
	 */
	public void setLastname(String lastname) {
		this.lastname.set(lastname);
	}

	/**
	 * Returns the full name concated from the first and last name
	 *
	 * @return the full name
	 */
	public StringBinding fullnameBinding() {
		return fullnameBinding;
	}

	/**
	 * Returns the full name of the person
	 * 
	 * @return the fullname
	 */
	public String getFullname() {
		return fullnameBinding.get();
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
