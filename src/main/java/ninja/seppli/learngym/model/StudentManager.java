package ninja.seppli.learngym.model;

import java.util.function.IntFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import ninja.seppli.learngym.saveload.JaxbSaverLoader;

/**
 * A class which manages all student. This is mainly used for the
 * {@link JaxbSaverLoader}
 *
 * @author sebi
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentManager extends Manager<Student> {

	/**
	 * Constructor
	 */
	public StudentManager() {
	}

	/**
	 * Creates a new student and add it to the manager
	 *
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 * @return the created student
	 */
	public Student add(String firstname, String lastname) {
		Student student = new Student(getNextInt(), firstname, lastname);
		objects.add(student);
		return student;
	}

	@Override
	protected IntFunction<Student[]> getArrayIntFunction() {
		return Student[]::new;
	}

}
