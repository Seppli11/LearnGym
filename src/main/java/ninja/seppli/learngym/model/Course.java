package ninja.seppli.learngym.model;

import java.util.stream.DoubleStream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.exception.StudentNotFoundException;

/**
 *
 * @author jfr and sebi
 *
 */
public class Course implements Averagable {
	/**
	 * the name of the course
	 */
	private StringProperty name = new SimpleStringProperty();
	/**
	 * the main teacher prop
	 */
	private ObjectProperty<Teacher> mainTeacher = new SimpleObjectProperty<>();
	/**
	 * the subjects in this course
	 */
	@XmlElement
	private ObservableList<Subject> subjects = FXCollections.observableArrayList();

	/**
	 * the students in this course
	 */
	@XmlIDREF
	@XmlElement(name = "students")
	private ObservableList<Student> students = FXCollections.observableArrayList();

	/**
	 * Constructor for jaxb
	 */
	protected Course() {
	}

	/**
	 * Constructor
	 *
	 * @param name
	 * @param mainTeacher
	 */
	public Course(String name, Teacher mainTeacher) {
		setName(name);
		setMainTeacher(mainTeacher);
	}

	/**
	 * Returns the name of the course. Basicly name of the class
	 *
	 * @return the name
	 */
	@XmlElement
	public String getName() {
		return name.get();
	}

	/**
	 * Returns the name property
	 *
	 * @return the property
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * Sets the name of the course
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * Returns the main teacher of the course. Every subject can have its own
	 * teacher
	 *
	 * @return the main teacher
	 */
	@XmlElement
	@XmlIDREF
	public Teacher getMainTeacher() {
		return mainTeacher.get();
	}

	/**
	 * Returns the main teacher property
	 *
	 * @return the property
	 */
	public ObjectProperty<Teacher> mainTeacherProperty() {
		return mainTeacher;
	}

	/**
	 * Sets the main teacher
	 *
	 * @param mainTeacher the teacher
	 */
	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher.set(mainTeacher);
	}

	/**
	 * Returns all subjects in this course. The returned list is live, so changes to
	 * the list are also in the course
	 *
	 * @return the subjects
	 */
	public ObservableList<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * Returns a live list of the students.<br>
	 * All changes to this list are also in the course.
	 *
	 * @return the students
	 */
	public ObservableList<Student> getStudents() {
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

	@Override
	public boolean hasGrades() {
		return subjects.stream().filter(Subject::hasGrades).count() != 0;
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
					"The student \"" + s.getFullname() + "\" cannot be found on the course \"" + getName() + "\"");
		}
		return getSubjects().stream().filter(subject -> subject.containsStudent(s))
				.mapToDouble(subject -> subject.getStudentGradeEntry(s).getGrade());
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
	 * Checks whether a student has grades. This can be used to check if
	 * {@link #getAverageOfStudent(Student)} will throw a
	 * {@link NoGradeYetException}
	 *
	 * @param s the student
	 * @return if the student has a grade
	 */
	public boolean hasStudentGrades(Student s) {
		return getGrades(s).length != 0;
	}

	/**
	 * Returns an {@link Averagable} object for a student. This method will throw a
	 * {@link NoGradeYetException}
	 *
	 * @param s the student
	 * @return the object
	 */
	public Averagable getAveragableOfStudent(Student s) {
		if (!getStudents().contains(s)) {
			throw new StudentNotFoundException(
					"The student \"" + s.getFullname() + "\" cannot be found on the course \"" + getName() + "\"");
		}
		return new Averagable() {

			@Override
			public boolean hasGrades() {
				return hasStudentGrades(s);
			}

			@Override
			public double getAverage() throws NoGradeYetException {
				return getAverageOfStudent(s);
			}
		};
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
		return streamOfGrades(s).filter(grade -> grade >= 4).map(grade -> grade - 4).sum();
	}

	/**
	 * Returns how many grades are above 4
	 *
	 * @param s the student
	 * @return how many grades are ok
	 */
	public int getPostiveGradeCounter(Student s) {
		return (int) streamOfGrades(s).filter(grade -> grade >= 4).count();
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
		return streamOfGrades(s).filter(grade -> grade < 4).map(grade -> 4 - grade).sum();
	}

	/**
	 * Returns how many grades are below 4
	 *
	 * @param s the student
	 * @return how many grades are not ok
	 */
	public int getNegativeGradeCounter(Student s) {
		return (int) streamOfGrades(s).filter(grade -> grade < 4).count();
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
