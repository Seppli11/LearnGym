package ninja.seppli.learngym.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ninja.seppli.learngym.exception.NoGradeYetException;

/**
 * A class which holds all student-course related resources. These aren't on the
 * {@link Student} itself, because there could be multiple courses per student
 *
 * @author sebi
 *
 */
public class StudentCourse {

	/**
	 * the course
	 */
	private Course course;

	/**
	 * the student
	 */
	private Student student;

	private ObservableList<Double> grades = FXCollections.observableArrayList();
	private ObservableList<Double> finalGrades = FXCollections.unmodifiableObservableList(grades);

	private ObservableList<Subject> subjects;
	private ObservableList<Subject> finalSubjects;
	private DoubleBinding averageBinding = Bindings.createDoubleBinding(() -> {
		double avg = course.streamOfGrades(getStudent()).average().orElseThrow(NoGradeYetException::new);
		return Math.round(avg * 2) / 2d;
	}, grades);

	public StudentCourse(Course course, Student student) {
		this.course = course;
		this.student = student;

		subjects = course.getSubjects().filtered(s -> s.containsStudent(student));
		finalSubjects = FXCollections.unmodifiableObservableList(subjects);
		this.course.getSubjects().addListener((ListChangeListener<Subject>) c -> {
			while (c.next()) {
				c.getAddedSubList().forEach(subject -> {
					subject.getGrades().addListener((ListChangeListener<Double>) c1 -> {
						if (subject.containsStudent(student)) {

						}
					});
				});
			}
		});
	}

	public Course getCourse() {
		return course;
	}

	public Student getStudent() {
		return student;
	}
}
