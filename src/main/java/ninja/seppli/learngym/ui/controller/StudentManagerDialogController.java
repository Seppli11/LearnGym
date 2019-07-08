package ninja.seppli.learngym.ui.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ninja.seppli.learngym.model.Manager;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;

/**
 * A controller for a teacher manager dialog
 *
 * @author sebi
 *
 */
public class StudentManagerDialogController extends ManagerDialogController<Student> {

	/**
	 * Constructor
	 *
	 * @param manager the manger
	 * @param stage   the stage
	 */
	public StudentManagerDialogController(Manager<Student> manager, Stage stage) {
		super(manager, stage);
	}

	@Override
	protected String convertToString(Student obj) {
		if (obj == null) {
			return "";
		}
		return obj.getFullname();
	}

	@Override
	protected void create(ActionEvent e) {
		StudentDialogController.show((StudentManager) getManager(), null);
	}

	@Override
	protected void edit(ActionEvent e) {
		Student selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			StudentDialogController.show((StudentManager) getManager(), selected);
		}
	}

	@Override
	protected void delete(ActionEvent e) {
		Student selected = list.getSelectionModel().getSelectedItem();
		if (selected != null) {
			getManager().remove(selected);
		}
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param studentManager the manager
	 * @return the controller
	 */
	public static StudentManagerDialogController show(StudentManager studentManager) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		StudentManagerDialogController controller = new StudentManagerDialogController(studentManager, stage);
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
