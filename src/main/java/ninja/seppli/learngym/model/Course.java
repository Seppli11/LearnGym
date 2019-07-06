package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.exception.StudentNotFoundException;

/**
 *
 * @author jfr and sebi
 *
 */
public class Course implements Averagable {
	private Logger logger = LogManager.getLogger();
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
	 * Returns the name of the course. Basicly name of the class
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the course
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the main teacher of the course. Every subject can have its own
	 * teacher
	 *
	 * @return the main teacher
	 */
	public Teacher getMainTeacher() {
		return mainTeacher;
	}

	/**
	 * Sets the main teacher
	 *
	 * @param mainTeacher the teacher
	 */
	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	/**
	 * Returns all subjects in this course. The returned list is live, so changes to
	 * the list are also in the course
	 *
	 * @return the subjects
	 */
	public List<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * Returns a live list of the students.<br>
	 * All changes to this list are also in the course.
	 *
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	@Override
	public double getAverage() throws NoGradeYetException {
		double avg = subjects.stream().filter(Subject::hasGrades).mapToDouble(value -> {
			try {
				return value.getAverage();
			} catch (NoGradeYetException e) {
				throw new RuntimeException("This shoulden't have happend", e);
			}
		}).average().orElseThrow(NoGradeYetException::new);
		return Math.round(avg * 2) / 2f;
	}

	/**
	 * Returns a stream of the grades of the students
	 *
	 * @param s the student
	 * @return the stream of grades
	 * @throws StudentNotFoundException if the student isn't partipating in this
	 *                                  course
	 */
	public DoubleStream streamOfGrades(Student s) {
		if (!getStudents().contains(s)) {
			throw new StudentNotFoundException(
					"The student \"" + s.getFullName() + "\" cannot be found on the course \"" + getName() + "\"");
		}
		return getSubjects().stream().filter(subject -> subject.containsStudent(s))
				.mapToDouble(subject -> subject.getGrade(s));
	}

	/**
	 * Returns all grades of a student
	 *
	 * @param s the student
	 * @return the grades in an array
	 * @throws StudentNotFoundException if the student isn't partipating in this
	 *                                  course
	 */
	public double[] getGrades(Student s) {
		return streamOfGrades(s).toArray();
	}

	/**
	 * Gets the average of a student.<br>
	 * This method is here and not in the student class, because if there are
	 * multiple courses, this method has to be on the course and not on the student,
	 * because the course has the reference to the student and not reversed.
	 *
	 * @param s the student
	 * @return the average
	 * @throws NoGradeYetException
	 */
	public double getAverageOfStudent(Student s) throws NoGradeYetException {
		double avg = streamOfGrades(s).average().orElseThrow(NoGradeYetException::new);
		return Math.round(avg * 2) / 2d;
	}

	/**
	 * Returns a sum of the student's positive grades
	 *
	 * @param s the student
	 * @return the sum of all the student's positive grades
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public double getPositiveGrade(Student s) {
		return streamOfGrades(s).filter(grade -> grade >= 4).sum();
	}

	/**
	 * Returns a sum of the student's negative grades
	 *
	 * @param s the student
	 * @return the sum of all the student's negative grades
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public double getNegativeGrade(Student s) {
		return streamOfGrades(s).filter(grade -> grade < 4).sum();
	}

	/**
	 * Checks if the student is prov or not
	 *
	 * @param s the student
	 * @return if the student is prov or not
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public boolean isStudentProv(Student s) {
		double positive = getPositiveGrade(s);
		double negative = getNegativeGrade(s);
		return positive < negative * 2;
	}
}
