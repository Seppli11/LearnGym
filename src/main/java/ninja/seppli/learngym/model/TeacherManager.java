package ninja.seppli.learngym.model;

import java.util.function.IntFunction;

/**
 * A manager which manages all teachers
 *
 * @author sebi
 *
 */
public class TeacherManager extends Manager<Teacher> {

	/**
	 * Constructor
	 */
	public TeacherManager() {
	}

	/**
	 * Creates a new teacher and adds it to the manager
	 * 
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 * @return the teacher which was created
	 */
	public Teacher add(String firstname, String lastname) {
		Teacher t = new Teacher(getNextInt(), firstname, lastname);
		objects.add(t);
		return t;
	}

	@Override
	protected IntFunction<Teacher[]> getArrayIntFunction() {
		return Teacher[]::new;
	}

}
