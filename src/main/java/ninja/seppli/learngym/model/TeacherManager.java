package ninja.seppli.learngym.model;

import java.util.function.IntFunction;

/**
 * A manager which manages all teachers
 * 
 * @author sebi
 *
 */
public class TeacherManager extends Manager<Teacher> {

	public TeacherManager() {
	}

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
