package ninja.seppli.learngym.ui.controller;

import javafx.stage.Stage;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;
import ninja.seppli.learngym.utils.FxHelper;

/**
 * The teacher dialog controller
 *
 * @author sebi
 *
 */
public class TeacherDialogController extends PersonDialogController<Teacher> {
	/**
	 * Constructor
	 *
	 * @param stage   the stage
	 * @param manager the teacher manager
	 * @param teacher a teacher which is edited or null
	 */
	public TeacherDialogController(Stage stage, TeacherManager manager, Teacher teacher) {
		super(stage, manager::add, teacher);
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param teacherManager
	 * @param teacher        the teacher that should be edited or null if none
	 *                       should be edited
	 * @return the controller
	 */
	public static TeacherDialogController show(TeacherManager teacherManager, Teacher teacher) {
		return FxHelper.show(stage -> new TeacherDialogController(stage, teacherManager, teacher), getFxmlURL());
	}
}
