package ninja.seppli.learngym.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.utils.FxHelper;

/**
 * a class which represents a subject
 *
 * @author jfr and sebi
 *
 */
public class Subject implements Averagable {
	/**
	 * the subject name
	 */
	private StringProperty subjectName = new SimpleStringProperty();

	/**
	 * the grades
	 */
	@XmlElement
	private ObservableList<StudentGradeEntry> studentGradeEntries = FXCollections.observableArrayList();

	/**
	 * an unmodifiable version from {@link #studentGradeEntries}
	 */
	private ObservableList<StudentGradeEntry> finalStudentGradeEntries = FXCollections
			.unmodifiableObservableList(studentGradeEntries);

	/**
	 * the teacher
	 */
	private ObjectProperty<Teacher> teacher = new SimpleObjectProperty<>();

	/**
	 * a shorthand binding
	 */
	private StringBinding shorthandBinding = Bindings.createStringBinding(() -> {
		if (getSubjectName().length() < 2) {
			return getSubjectName();
		}
		return getSubjectName().substring(0, 2);
	}, subjectNameProperty());

	/**
	 * A list of all grades
	 */
	private ObservableList<Double> grades = FxHelper.mapList(StudentGradeEntry::getGrade, studentGradeEntries);

	/**
	 * An binding for the average
	 */
	private DoubleBinding averageBinding = Bindings.createDoubleBinding(() -> {
		return getGrades().stream().mapToDouble(Double::doubleValue).average().orElse(-1);
	}, studentGradeEntries);

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
		setSubjectName(subjectName);
		setTeacher(teacher);
	}

	/**
	 * the name
	 *
	 * @return returns the name of the subject
	 */
	@XmlElement
	public String getSubjectName() {
		return subjectName.get();
	}

	/**
	 * returns the subjectname property
	 *
	 * @return the prop
	 */
	public StringProperty subjectNameProperty() {
		return subjectName;
	}

	/**
	 * sets the subject name
	 *
	 * @param subjectName the name
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName.set(subjectName);
	}

	/**
	 * Returns the shortname.<br>
	 * it is generated from the first to character of the {@link #subjectName}
	 *
	 * @return the shortname
	 */
	public String getShortname() {
		return shorthandBinding.get();
	}

	/**
	 * returns the shorthand binding
	 *
	 * @return the shorthand binding
	 */
	public StringBinding shortnameBinding() {
		return shorthandBinding;
	}

	/**
	 * Returns the students which participate in this subject
	 *
	 * @return the students
	 */
	public List<Student> getStudents() {
		return studentGradeEntries.stream().map(StudentGradeEntry::getStudent).collect(Collectors.toList());
	}

	/**
	 * Checks if the given student participates
	 *
	 * @param student the student to check
	 * @return if the student is in this subjecet
	 */
	public boolean containsStudent(Student student) {
		return getStudents().contains(student);
	}

	/**
	 * Adds a grade to this subject associated with the given student
	 *
	 * @param student the student who wrote the mark
	 * @param grade   the grade. The grade is rounded to 0.5 steps
	 */
	public void setGrade(Student student, double grade) {
		if (containsStudent(student)) {
			getStudentGradeEntry(student).setGrade(grade);
		} else {
			studentGradeEntries.add(new StudentGradeEntry(student, grade));
		}
	}

	/**
	 * Returns the student grade entry of the student
	 *
	 * @param student the student
	 * @return the grade entry or null if the student doesn't have a grade yet
	 */
	public StudentGradeEntry getStudentGradeEntry(Student student) {
		return studentGradeEntries.stream().filter(se -> student.equals(se.getStudent())).findFirst().orElse(null);
	}

	/**
	 * Returns all grades in this subject
	 *
	 * @return the grades
	 */
	public ObservableList<Double> getGrades() {
		return grades;
	}

	/**
	 * Returns a list with all student grade entries
	 *
	 * @return the list
	 */
	public ObservableList<StudentGradeEntry> getStudentGradeEntries() {
		return finalStudentGradeEntries;
	}

	/**
	 * Returns if there is at least one grade. If this function returns false, then
	 * {@link #getAverage()} will throw a {@link NoGradeYetException}.
	 *
	 * @return if there is at least one grade.
	 */
	@Override
	public boolean hasGrades() {
		return !getGrades().isEmpty();
	}

	/**
	 * Returns the teacher
	 *
	 * @return the teacher
	 */
	@XmlElement
	@XmlIDREF
	public Teacher getTeacher() {
		return teacher.get();
	}

	/**
	 * Returns the teacher property
	 *
	 * @return the prop
	 */
	public ObjectProperty<Teacher> teacherProperty() {
		return teacher;
	}

	/**
	 * Sets the teacher
	 *
	 * @param teacher the teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher.set(teacher);
	}

	@Override
	public double getAverage() throws NoGradeYetException {
		if (!hasGrades()) {
			throw new NoGradeYetException("No grades yet");
		}
		return averageBinding.get();
	}

	/**
	 * Returns a binding for the average of this subject
	 *
	 * @return the binding
	 */
	public DoubleBinding averageBinding() {
		return averageBinding;
	}

	/**
	 * The student grade entry.
	 *
	 * @author sebi
	 *
	 */
	public static class StudentGradeEntry {
		/**
		 * the student of this entry
		 */
		private Student student;
		/**
		 * the grade property
		 */
		private DoubleProperty grade = new SimpleDoubleProperty();

		/**
		 * Jaxb constructor
		 */
		protected StudentGradeEntry() {
		}

		/**
		 * constructor
		 *
		 * @param student the student
		 * @param grade   the grade
		 */
		public StudentGradeEntry(Student student, double grade) {
			this.student = student;
			setGrade(grade);
		}

		/**
		 * Returns the student
		 *
		 * @return the student
		 */
		@XmlElement
		@XmlIDREF
		public Student getStudent() {
			return student;
		}

		/**
		 * Sets the student.<br>
		 * This function is only intendet for jaxb!!!
		 *
		 * @param student the new student
		 */
		protected void setStudent(Student student) {
			this.student = student;
		}

		/**
		 * Returns the grade of the student
		 *
		 * @return the grade
		 */
		@XmlElement
		public double getGrade() {
			return grade.get();
		}

		/**
		 * returns the grade property
		 *
		 * @return the prop
		 */
		public DoubleProperty gradeProperty() {
			return grade;
		}

		/**
		 * Sets the grade and rounds it to 0.5 steps
		 *
		 * @param grade the new grade
		 */
		public void setGrade(double grade) {
			this.grade.set(Math.round(grade * 2) / 2d);
		}
	}

}
