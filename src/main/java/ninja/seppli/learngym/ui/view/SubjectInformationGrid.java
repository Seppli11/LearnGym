package ninja.seppli.learngym.ui.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Subject;

/**
 * The information grid below the main grid
 *
 * @author sebi
 *
 */
public class SubjectInformationGrid extends GridPane {
	/**
	 * the course property
	 */
	private ObjectProperty<Course> course = new SimpleObjectProperty<>();

	/**
	 * Constructor
	 */
	public SubjectInformationGrid() {
		setGridLinesVisible(true);
		setup();
		course.addListener((ChangeListener<Course>) (observable, oldValue, newValue) -> setup());
	}

	/**
	 * Sets up the grid pane
	 */
	private void setup() {
		resetWidthBindings();
		getChildren().clear();
		if (getCourse() == null) {
			return;
		}
		add(new Label("Durchschnitt"), 0, 0);
		add(new Label("Lehrperson"), 0, 1);
		getColumnConstraints().add(new ColumnConstraints());

		for (Subject subject : getCourse().getSubjects()) {
			setupSubject(subject);
		}
		getCourse().getSubjects().addListener((ListChangeListener<Subject>) c -> {
			boolean reload = false;
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach(s -> setupSubject(s));
				}
				if (c.wasRemoved()) {
					reload = true;
				}
			}
			if (reload) {
				setup();
			}
		});

		Label classLbl = new Label("Klasse: " + getCourse().getName());
		add(classLbl, 0, 3);

		Label mainTeacherLbl = new Label("Klasse: " + getCourse().getMainTeacher().getFullname());
		add(mainTeacherLbl, 0, 4);

	}

	/**
	 * Setsup a column for a new subject
	 *
	 * @param subject the subject
	 */
	private void setupSubject(Subject subject) {
		int counter = getColumnConstraints().size();
		Label avgLbl = new Label();
		avgLbl.textProperty().bind(subject.averageBinding().asString("%.1f"));
		add(avgLbl, counter, 0);

		Label teacherLbl = new Label();
		teacherLbl.textProperty().bind(subject.getTeacher().fullnameBinding());
		subject.teacherProperty().addListener((ob, oldV, newV) -> {
			teacherLbl.textProperty().unbind();
			teacherLbl.textProperty().bind(subject.getTeacher().fullnameBinding());
		});
		add(teacherLbl, counter, 1);

		getColumnConstraints().add(new ColumnConstraints());
	}

	/**
	 * Resets all bindings from the previous columns
	 */
	public void resetWidthBindings() {
		getColumnConstraints().forEach(c -> c.prefWidthProperty().unbind());
	}

	/**
	 * Returns the course
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return course.get();
	}

	/**
	 * returns the course property
	 *
	 * @return the prop
	 */
	public ObjectProperty<Course> courseProperty() {
		return course;
	}

	/**
	 * Sets the course
	 *
	 * @param course the course
	 */
	public void setCourse(Course course) {
		this.course.set(course);
	}
}
