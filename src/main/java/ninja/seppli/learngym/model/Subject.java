package ninja.seppli.learngym.model;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.binding.ListBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.saveload.adapter.SubjectGradeMapAdapter;

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
	@XmlJavaTypeAdapter(SubjectGradeMapAdapter.class)
	private ObservableList<SubjectEntry> grades = FXCollections.observableArrayList();

	/**
	 * the teacher
	 */
	private ObjectProperty<Teacher> teacher = new SimpleObjectProperty<>();

	/**
	 * a shorthand binding
	 */
	private StringBinding shorthandBinding = new StringBinding() {
		{
			super.bind(subjectNameProperty());
		}

		@Override
		protected String computeValue() {
			if (getSubjectName().length() < 2) {
				return getSubjectName();
			}
			return getSubjectName().substring(0, 2);
		}
	};

	private ListBinding<Double> gradesBinding = new ListBinding<Double>() {
		{
			super.bind(grades);
		}

		@Override
		protected ObservableList<Double> computeValue() {
			return FXCollections.observableList((List<Double>) grades.values());
		}
	};

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
	 * Returns an unmodifiable version of {@link #grades}
	 *
	 * @return the grades map
	 */
	public ObservableMap<Student, Double> getGradeMap() {
		return finalGrades;
	}

	/**
	 * Returns the students which participate in this subject
	 *
	 * @return the students
	 */
	public Set<Student> getStudents() {
		return grades.stream().map(SubjectEntry::getStudent).
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
	 * Returns all grades in this subject
	 *
	 * @return the grades
	 */
	public double[] getGrades() {
		return grades.values().stream().mapToDouble(Double::doubleValue).toArray();
	}

	/**
	 * Returns a binding to {@link #getGrades()}
	 *
	 * @return the binding
	 */
	public ListBinding<Double> gradesBinding() {
		return gradesBinding;
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
		double avg = grades.values().stream().mapToDouble(Double::doubleValue).average()
				.orElseThrow(NoGradeYetException::new);
		return Math.round(avg * 2) / 2f;
	}

	private static class SubjectEntry {
		public Student student;
		public double grade;

		/**
		 * Jaxb constructor
		 */
		public SubjectEntry() {
		}

		/**
		 * constructor
		 *
		 * @param student the student
		 * @param grade   the grade
		 */
		public SubjectEntry(Student student, double grade) {
			this.student = student;
			this.grade = grade;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public double getGrade() {
			return grade;
		}

		public void setGrade(double grade) {
			this.grade = grade;
		}

	}
}
