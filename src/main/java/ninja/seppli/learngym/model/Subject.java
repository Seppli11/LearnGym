package ninja.seppli.learngym.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ninja.seppli.learngym.exception.StudentNotFoundException;

/**
 *
 * @author jfr
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

	public Set<Student> getStudents() {
		return grades.keySet();
	}

	public boolean containsStudent(Student student) {
		return grades.containsKey(student);
	}

	public void addGrade(Student student, float grade) throws StudentNotFoundException {
		if (!containsStudent(student)) {
			throw new StudentNotFoundException("The student \"" + student + "\" wasn't found");
		}
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
