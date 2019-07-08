package ninja.seppli.learngym.ui.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ninja.seppli.learngym.model.Manager;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;

/**
 * A controller for a teacher manager dialog
 *
 * @author sebi
 *
 */
public class TeacherManagerDialogController extends ManagerDialogController<Teacher> {

	/**
	 * Constructor
	 *
	 * @param manager the manger
	 * @param stage   the stage
	 */
	public TeacherManagerDialogController(Manager<Teacher> manager, Stage stage) {
		super(manager, stage);
	}

	@Override
	protected String convertToString(Teacher obj) {
		if (obj == null) {
			return "";
		}
		return obj.getFullname();
	}

	@Override
	protected void create(ActionEvent e) {
		TeacherDialogController.show((TeacherManager) getManager(), null);
	}

	@Override
	protected void edit(ActionEvent e) {
		Teacher selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			TeacherDialogController.show((TeacherManager) getManager(), selected);
		}
	}

	@Override
	protected void delete(ActionEvent e) {
		Teacher selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			getManager().remove(selected);
		}
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param teacherManager the manager
	 * @return the controller
	 */
	public static TeacherManagerDialogController show(TeacherManager teacherManager) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		TeacherManagerDialogController controller = new TeacherManagerDialogController(teacherManager, stage);
		loader.setController(controller);

		try {
			loader.setLocation(ManagerDialogController.getFxmlURL());
			Parent p = loader.load();
			Scene scene = new Scene(p);
			stage.setScene(scene);
			stage.showAndWait();
			return controller;
		} catch (IOException e) {
			throw new IllegalStateException("person_manager.fxml wasn't found", e);
		}
	}

}
