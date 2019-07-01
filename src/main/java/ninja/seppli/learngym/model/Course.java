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

	public Student[] getStudents() {
		return students.toArray(new Student[students.size()]);
	}

	public void addStudent(Student student) {
		if (students.contains(student)) {
			return;
		}
		student.setCourse(this);
		students.add(student);
	}

}
