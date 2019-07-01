package ninja.seppli.learngym.model;

/**
 *
 * @author jfr
 *
 */
public class Student extends Person {
	private Course course;

	/**
	 * Constructor
	 *
	 * @param firstname
	 * @param lastname
	 * @param course
	 */
	public Student(String firstname, String lastname, Course course) {
		super(firstname, lastname);
		this.course = course;
	}

	/**
	 *
	 * @return
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 *
	 * @param course
	 */
	protected void setCourse(Course course) {
		this.course = course;
	}

}
