package ninja.seppli.learngym.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jfr and sebi
 *
 */
public class Subject implements Averagable {
	private String subjectName;
	private Map<Student, Float> grades = new HashMap<>();
	private Teacher teacher;

	/**
	 * constructor
	 *
	 * @param subjectName
	 */
	public Subject(String subjectName, Teacher teacher) {
		this.subjectName = subjectName;
		this.teacher = teacher;
	}

	/**
	 *
	 * @return
	 */
	public String getSubjectName() {
		return subjectName;
	}

	public String getShortname() {
		if (subjectName.length() < 2) {
			return subjectName;
		}
		return subjectName.substring(0, 2);
	}

	public Set<Student> getStudents() {
		return grades.keySet();
	}

	public boolean containsStudent(Student student) {
		return grades.containsKey(student);
	}

	public void addGrade(Student student, float grade) {
		grades.put(student, grade);
	}

	public Teacher getTeacher() {
		return teacher;
	}

	@Override
	public float getAverage() {
		return 0;
	}

}
