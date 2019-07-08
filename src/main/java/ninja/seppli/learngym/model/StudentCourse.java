package ninja.seppli.learngym.model;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.exception.StudentNotFoundException;
import ninja.seppli.learngym.model.Subject.StudentGradeEntry;

/**
 * A class which holds all student-course related resources. These aren't on the
 * {@link Student} itself, because there could be multiple courses per student
 *
 * @author sebi
 *
 */
public class StudentCourse implements Averagable {

	/**
	 * the student
	 */
	private Student student;

	/**
	 * the course
	 */
	private Course course;

	/**
	 * the grades
	 */
	private ObservableList<Double> grades = FXCollections.observableArrayList();
	/**
	 * the final grades
	 */
	private ObservableList<Double> finalGrades = FXCollections.unmodifiableObservableList(grades);

	/**
	 * the subjects in which the student is participating
	 */
	private ObservableList<Subject> subjects = FXCollections.observableArrayList();
	/**
	 * an umodifiable list of the subjects
	 */
	private ObservableList<Subject> finalSubjects = FXCollections.unmodifiableObservableList(subjects);
	/**
	 * an binding to the average of the student
	 */
	private DoubleBinding averageBinding = Bindings.createDoubleBinding(() -> {
		return grades.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
	}, grades);

	/**
	 * all positive grades
	 */
	private FilteredList<Double> positiveGradeList = grades.filtered(grade -> grade >= 4);

	/**
	 * all negative grades
	 */
	private FilteredList<Double> negativeGradeList = grades.filtered(grade -> grade < 4);

	/**
	 * A bindings to how many grades are positive
	 */
	private IntegerBinding positiveGradesCounterBinding = Bindings.createIntegerBinding(positiveGradeList::size,
			grades);
	/**
	 * A bindings to how many grades are negative
	 */
	private IntegerBinding negativeGradesCounterBinding = Bindings.createIntegerBinding(negativeGradeList::size,
			grades);

	/**
	 * The sum of all positive points
	 */
	private DoubleBinding positiveSumBinding = Bindings
			.createDoubleBinding(() -> positiveGradeList.stream().mapToDouble(grade -> grade - 4).sum(), grades);
	/**
	 * The sum of all negative points
	 */
	private DoubleBinding negativeGradeSumBinding = Bindings
			.createDoubleBinding(() -> negativeGradeList.stream().mapToDouble(grade -> 4 - grade).sum(), grades);

	/**
	 * the listener to replace and track grades
	 */
	private ChangeListener<Number> gradeChangeListener = (ob, oldV, newV) -> {
		// removes one value of the old grade and adds the new grade
		grades.remove(oldV.doubleValue());
		grades.add(newV.doubleValue());
	};

	/**
	 * a binding if the student is prov or not
	 */
	private BooleanBinding provBinding = Bindings.createBooleanBinding(() -> {
		double positive = getPositiveSum();
		double negative = getNegativeSum();
		return positive < negative * 2;
	}, positiveSumBinding, negativeGradeSumBinding);

	/**
	 * jaxb constructor
	 */
	private StudentCourse() {
	}

	/**
	 * Fixes the bindings
	 */
	protected void fix() {
		setupCourse(course);
	}

	/**
	 * Constructor<br>
	 * Used by {@link Course}
	 *
	 * @param course  the course of this object
	 * @param student the student
	 */
	protected StudentCourse(Course course, Student student) {
		this.course = course;
		this.student = student;
		setCourse(course);
	}

	/**
	 * Sets up the object
	 *
	 * @param course the course to use
	 */
	protected void setupCourse(Course course) {
		subjects.addListener((ListChangeListener<Subject>) c -> {
			boolean redo = false;
			while (c.next()) {
				// add the grades from the subject to the student
				c.getAddedSubList().stream().map(subject -> subject.getStudentGradeEntry(student).getGrade())
						.forEach(grades::add);

				// add listener to track grades
				c.getAddedSubList().forEach(subject -> {
					subject.getStudentGradeEntries()
							.forEach(gradeEntry -> gradeEntry.gradeProperty().addListener(gradeChangeListener));
				});
				if (c.wasRemoved()) {
					c.getRemoved().forEach(subject -> {
						// remove listener to track grades
						subject.getStudentGradeEntries()
								.forEach(gradeEntry -> gradeEntry.gradeProperty().removeListener(gradeChangeListener));
					});
					redo = true;
				}
			}
			if (redo) {
				grades.setAll(subjects.stream().map(subject -> subject.getStudentGradeEntry(student).getGrade())
						.collect(Collectors.toList()));
			}
		});

		setupSubjectListener(course);

	}

	/**
	 * Sets up the necesarry listeners to track subject
	 *
	 * @param course the course
	 */
	private void setupSubjectListener(Course course) {
		subjects.addAll(
				course.getSubjects().stream().filter(s -> s.containsStudent(student)).collect(Collectors.toList()));
		course.getSubjects().forEach(this::addListenersToSubject);

		course.getSubjects().addListener((ListChangeListener<Subject>) c1 -> {
			while (c1.next()) {
				// subject is added
				if (c1.wasAdded()) {
					// add all subjects where this student is already participating
					subjects.addAll(c1.getAddedSubList().stream().filter(subject -> subject.containsStudent(student))
							.collect(Collectors.toList()));

					// setup listener which catch if a student register/unregister itself
					c1.getAddedSubList().forEach(this::addListenersToSubject);
				}

				// a subject is removed
				if (c1.wasRemoved()) {
					// remove also the subject from the list
					c1.getRemoved().forEach(subjects::remove);
				}
			}
		});

	}

	/**
	 * Adds the listener necessary to track the changes inside of {@link Subject}
	 *
	 * @param subject the subject
	 */
	private void addListenersToSubject(Subject subject) {
		subject.getStudentGradeEntries().addListener((ListChangeListener<StudentGradeEntry>) c2 -> {
			while (c2.next()) {
				// grades were added
				if (c2.wasAdded()) {
					c2.getAddedSubList().forEach(entry -> {
						if (!subjects.contains(subject) && subject.containsStudent(student)) {
							subjects.add(subject);
						}
					});
				}
				// grades where removed
				if (c2.wasRemoved()) {
					c2.getRemoved().forEach(entry -> {
						// removes the subject if the subjet doesn't contain the student anymore
						if (subjects.contains(subject) && !subject.containsStudent(student)) {
							subjects.remove(subject);
						}
					});
				}
			}
		});
	}

	/**
	 * returns the studnet
	 *
	 * @return the student
	 */
	@XmlElement
	@XmlIDREF
	public Student getStudent() {
		return student;
	}

	/**
	 * Jaxb setter for the student
	 *
	 * @param student the new student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Returns the course
	 *
	 * @return the course
	 */
	@XmlElement
	@XmlIDREF
	public Course getCourse() {
		return course;
	}

	/**
	 * Setter for the course<br>
	 * Jaxb function
	 *
	 * @param course the course
	 */
	protected void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Returns an unmodifiable list of grades
	 *
	 * @return the grades
	 */
	public ObservableList<Double> getGrades() {
		return finalGrades;
	}

	/**
	 * Returns an unmodifiable list of all subjets
	 *
	 * @return the subjects
	 */
	public ObservableList<Subject> getSubjects() {
		return finalSubjects;
	}

	/**
	 * Gets the average of a student.<br>
	 * This method is here and not in the student class, because if there are
	 * multiple courses, this method has to be on the course and not on the student,
	 * because the course has the reference to the student and not reversed.
	 *
	 * @return the average
	 * @throws NoGradeYetException
	 */
	@Override
	public double getAverage() throws NoGradeYetException {
		return averageBinding.get();
	}

	/**
	 * returns the average binding
	 *
	 * @return the average binding
	 */
	public DoubleBinding averageBinding() {
		return averageBinding;
	}

	/**
	 * Checks whether a student has grades. This can be used to check if
	 * {@link #getAverage()} will throw a {@link NoGradeYetException}
	 *
	 * @return if the student has a grade
	 */
	@Override
	public boolean hasGrades() {
		return !getGrades().isEmpty();
	}

	/**
	 * Returns a sum of the student's positive grades
	 *
	 * @return the sum of all the student's positive grades
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public double getPositiveSum() {
		return positiveSumBinding.get();
	}

	/**
	 * Returns the sum of all positive points
	 *
	 * @return the sum binding
	 */
	public DoubleBinding positiveSumBinding() {
		return positiveSumBinding;
	}

	/**
	 * Returns how many grades are above 4
	 *
	 * @return how many grades are ok
	 */
	public int getPostiveGradeCounter() {
		return positiveGradesCounterBinding.get();
	}

	/**
	 * Returns the positive grade counter
	 *
	 * @return the positive ground
	 */
	public IntegerBinding positiveGradeCounterBinding() {
		return positiveGradesCounterBinding;
	}

	/**
	 * Returns a sum of the student's negative grades
	 *
	 * @return the sum of all the student's negative grades
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public double getNegativeSum() {
		return negativeGradeSumBinding.get();
	}

	/**
	 * Returns the binding to the negative sum of the grades
	 *
	 * @return the binding
	 */
	public DoubleBinding negativeSumBinding() {
		return negativeGradeSumBinding;
	}

	/**
	 * Returns how many grades are below 4
	 *
	 * @return how many grades are not ok
	 */
	public int getNegativeGradeCounter() {
		return negativeGradesCounterBinding.get();
	}

	/**
	 * Returns the number of negative grades in a binding
	 *
	 * @return the binding
	 */
	public IntegerBinding negativeGradesCounterBinding() {
		return negativeGradesCounterBinding;
	}

	/**
	 * Checks if the student is prov or not
	 *
	 * @return if the student is prov or not
	 * @throws StudentNotFoundException if the student isn't participating in this
	 *                                  course
	 */
	public boolean isProv() {
		return provBinding.get();
	}

	/**
	 * Returns the binding to if the student is prov or not
	 *
	 * @return the binding
	 */
	public BooleanBinding provBinding() {
		return provBinding;
	}

}
