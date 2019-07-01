package ninja.seppli.learngym.model;

import java.util.List;

/**
 * 
 * @author jfr
 *
 */
public class Student extends Person {

	private List<Grade> grades;
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
	public void setCourse(Course course) {
		this.course = course;
	}

}
