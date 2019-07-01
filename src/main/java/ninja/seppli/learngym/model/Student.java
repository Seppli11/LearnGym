package ninja.seppli.learngym.model;

/**
 *
 * @author jfr and sebi
 *
 */
public class Student extends Person {
	private Course course;

	/**
	 * Constructor
	 *
	 * @param firstname
	 * @param lastname
	 */
	public Student(String firstname, String lastname) {
		super(firstname, lastname);
		this.course = null; // is later set by Course#addStudent(Student)
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
