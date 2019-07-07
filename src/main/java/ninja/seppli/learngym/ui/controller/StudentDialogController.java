package ninja.seppli.learngym.ui.controller;

import javafx.stage.Stage;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.utils.FxHelper;

/**
 * The teacher dialog controller
 *
 * @author sebi
 *
 */
public class StudentDialogController extends PersonDialogController<Student> {
	/**
	 * Constructor
	 *
	 * @param stage   the stage
	 * @param manager the manager
	 * @param student a person which is edited or null
	 */
	public StudentDialogController(Stage stage, StudentManager manager, Student student) {
		super(stage, manager::add, student);
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param manager
	 * @param student the person that should be edited or null if none should be
	 *                edited
	 * @return the controller
	 */
	public static StudentDialogController show(StudentManager manager, Student student) {
		return FxHelper.show(stage -> new StudentDialogController(stage, manager, student), getFxmlURL());
	}
}
