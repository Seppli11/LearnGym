package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jfr
 *
 */
public class Course {
	private String name;
	private Teacher mainTeacher;
	private List<Subject> subjects = new ArrayList<Subject>();
	private List<Student> students = new ArrayList<Student>();

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param mainTeacher
	 */
	public Course(String name, Teacher mainTeacher) {
		super();
		this.name = name;
		this.mainTeacher = mainTeacher;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public Teacher getMainTeacher() {
		return mainTeacher;
	}

	/**
	 * 
	 * @param mainTeacher
	 */
	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	/**
	 * 
	 * @return
	 */
	public List<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * 
	 * @return
	 */
	public List<Student> getStudents() {
		return students;
	}

}
