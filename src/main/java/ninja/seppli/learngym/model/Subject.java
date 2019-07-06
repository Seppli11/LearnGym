package ninja.seppli.learngym.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.exception.StudentNotFoundException;
import ninja.seppli.learngym.saveload.adapter.SubjectGradeMapAdapter;

/**
 * a class which represents a subject
 *
 * @author jfr and sebi
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Subject implements Averagable {
	private String subjectName;
	@XmlJavaTypeAdapter(SubjectGradeMapAdapter.class)
	private Map<Student, Double> grades = new HashMap<>();
	@XmlIDREF
	private Teacher teacher;

	/**
	 * Constructor for jaxb
	 */
	protected Subject() {
	}

	/**
	 * constructor
	 *
	 * @param subjectName the name of the subject
	 * @param teacher     the teacher of the subject
	 */
	public Subject(String subjectName, Teacher teacher) {
		this.subjectName = subjectName;
		this.teacher = teacher;
	}

	/**
	 * the name
	 *
	 * @return returns the name of the subject
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Returns the shortname.<br>
	 * it is generated from the first to character of the {@link #subjectName}
	 *
	 * @return the shortname
	 */
	public String getShortname() {
		if (subjectName.length() < 2) {
			return subjectName;
		}
		return subjectName.substring(0, 2);
	}

	/**
	 * Returns the students which participate in this subject
	 *
	 * @return the students
	 */
	public Set<Student> getStudents() {
		return grades.keySet();
	}

	/**
	 * Checks if the given student participates
	 *
	 * @param student the student to check
	 * @return if the student is in this subjecet
	 */
	public boolean containsStudent(Student student) {
		return grades.containsKey(student);
	}

	/**
	 * Adds a grade to this subject associated with the given student
	 *
	 * @param student the student who wrote the mark
	 * @param grade   the grade. The grade is rounded to 0.5 steps
	 */
	public void setGrade(Student student, double grade) {
		grades.put(student, Math.round(grade * 2) / 2d);
	}

	/**
	 * Returns the grade of the student or if the student doesn't participate in the
	 * subject then an {@link StudentNotFoundException} is thrown.
	 *
	 * @param student the student
	 * @return the grade of the given student
	 * @throws StudentNotFoundException
	 */
	public double getGrade(Student student) throws StudentNotFoundException {
		if (!containsStudent(student)) {
			throw new StudentNotFoundException("The student \"" + student.getFirstname() + " " + student.getLastname()
					+ "\" doesnt' participate in the subject \"" + getSubjectName() + "\"");
		}
		return grades.get(student);
	}

	/**
	 * Returns all grades in this subject
	 *
	 * @return the grades
	 */
	public double[] getGrades() {
		return grades.values().stream().mapToDouble(Double::doubleValue).toArray();
	}

	/**
	 * Returns if there is at least one grade. If this function returns false, then
	 * {@link #getAverage()} will throw a {@link NoGradeYetException}.
	 *
	 * @return if there is at least one grade.
	 */
	@Override
	public boolean hasGrades() {
		return getGrades().length != 0;
	}

	/**
	 * Returns the teacher
	 *
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	@Override
	public double getAverage() throws NoGradeYetException {
		double avg = grades.values().stream().mapToDouble(Double::doubleValue).average()
				.orElseThrow(NoGradeYetException::new);
		return Math.round(avg * 2) / 2f;
	}

}
