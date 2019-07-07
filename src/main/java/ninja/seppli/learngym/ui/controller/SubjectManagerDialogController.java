package ninja.seppli.learngym.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.TeacherManager;
import ninja.seppli.learngym.utils.FxHelper;

/**
 * @author sebi
 *
 */
public class SubjectManagerDialogController extends ManagerDialogController<Subject> {
	/**
	 * the course
	 */
	private Course course;
	/**
	 * the teacher manager used to create new subjects
	 */
	private TeacherManager teacherManager;

	/**
	 * Constructor
	 *
	 * @param course         the course
	 * @param teacherManager the teacher manager
	 * @param stage          the stage
	 */
	public SubjectManagerDialogController(Course course, TeacherManager teacherManager, Stage stage) {
		super(null, stage);
		this.course = course;
		this.teacherManager = teacherManager;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.setItems(course.getSubjects());
		setupListCellFactory();
	}

	@Override
	protected String convertToString(Subject obj) {
		if (obj == null) {
			return "";
		}
		return obj.getSubjectName();
	}

	@Override
	protected void create(ActionEvent e) {
		SubjectDialogController controller = SubjectDialogController.show(teacherManager, null);
		Subject subject = controller.getObject().orElse(null);
		if (subject != null) {
			getCourse().getSubjects().add(subject);
		}
	}

	@Override
	protected void edit(ActionEvent e) {
		Subject selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			SubjectDialogController.show(teacherManager, selected);
		}
	}

	@Override
	protected void delete(ActionEvent e) {
		Subject selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			SubjectDialogController.show(teacherManager, selected);
		}
		getCourse().getSubjects().remove(selected);
	}

	/**
	 * Returns the course
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param course         the course
	 * @param teacherManager
	 * @return the controller
	 */
	public static SubjectManagerDialogController show(Course course, TeacherManager teacherManager) {
		return FxHelper.show(stage -> new SubjectManagerDialogController(course, teacherManager, stage), getFxmlURL());
	}

}
