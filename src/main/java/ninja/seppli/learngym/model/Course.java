package ninja.seppli.learngym.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ninja.seppli.learngym.exception.NoGradeYetException;

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
	@XmlElement
	private ObservableList<StudentCourse> students = FXCollections.observableArrayList();

	/**
	 * Constructor for jaxb
	 */
	protected Course() {
		students.addListener((ListChangeListener<StudentCourse>) c -> {
			while (c.next()) {
				c.getAddedSubList().forEach(obj -> obj.setupCourse(this));
			}

		});
	}

	/**
	 * Constructor
	 *
	 * @param name
	 * @param mainTeacher
	 */
	public Course(String name, Teacher mainTeacher) {
		this();
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
	public ObservableList<StudentCourse> getStudents() {
		return students;
	}

	/**
	 * Adds a new student
	 *
	 * @param student the student
	 * @return the created student course
	 */
	public StudentCourse addStudent(Student student) {
		StudentCourse studentCourse = new StudentCourse(student);
		students.add(studentCourse);
		return studentCourse;
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

}
