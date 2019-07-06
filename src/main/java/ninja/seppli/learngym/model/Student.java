package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.VariableElement;

/**
 *
 * @author jfr and sebi
 *
 */
public class Student extends Person implements Averagable {
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

	/**
	 * 
	 * @return
	 */
	public List<Float> getAllGrades() {
		List<Float> listOfGrades = new ArrayList<>();

		List<Subject> listOfSubject = course.getSubjects();
		
		for (int i = 0; i < listOfSubject.size(); i++) {
			Subject subject = listOfSubject.get(i);
			
		}

		return null;

	}

	@Override
	public float getAverage() {

		return 0;
	}

}
