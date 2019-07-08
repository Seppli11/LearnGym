package ninja.seppli.learngym.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;

/**
 * A controller for the new subject dialog view
 *
 * @author sebi
 *
 */
public class SubjectDialogController implements Initializable, ObjectDialog<Subject> {
	/**
	 * the name text field
	 */
	@FXML
	private TextField nameTF;

	/**
	 * the teacher combobox
	 */
	@FXML
	private ComboBox<Teacher> teacherCB;

	/**
	 * the ok button
	 */
	@FXML
	private Button okBtn;

	/**
	 * the subject which is edited/created
	 */
	private Subject subject;

	/**
	 * the teacher manager
	 */
	private TeacherManager teacherManager;

	/**
	 * the stage
	 */
	private Stage stage;

	/**
	 * Constructor
	 *
	 * @param teacherManager the course
	 * @param stage          the stage
	 */
	private SubjectDialogController(TeacherManager teacherManager, Stage stage) {
		this.teacherManager = teacherManager;
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		okBtn.disableProperty().bind(
				nameTF.textProperty().isEmpty().or(teacherCB.getSelectionModel().selectedItemProperty().isNull()));
		teacherCB.getItems().addAll(teacherManager.getAll());
		teacherCB.setConverter(new StringConverter<Teacher>() {

			@Override
			public String toString(Teacher object) {
				return object.getFullname();
			}

			@Override
			public Teacher fromString(String string) {
				return null;
			}
		});
	}

	/**
	 * Cancels the dialog
	 *
	 * @param e the event
	 */
	@FXML
	private void cancel(ActionEvent e) {
		stage.close();
	}

	/**
	 * creates the subject
	 *
	 * @param e
	 */
	@FXML
	private void ok(ActionEvent e) {
		String name = nameTF.getText();
		Teacher teacher = teacherCB.getValue();
		if (subject == null) {
			subject = new Subject(name, teacher);
		} else {
			subject.setSubjectName(name);
			subject.setTeacher(teacher);
		}
		stage.close();
	}

	@Override
	public Optional<Subject> getObject() {
		return Optional.ofNullable(subject);
	}

	@Override
	public void setObject(Subject subject) {
		this.subject = subject;
		if (subject != null) {
			nameTF.setText(subject.getSubjectName());
			teacherCB.getSelectionModel().select(subject.getTeacher());
		}
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param teacherManager
	 * @param subject        the subject if it should be edited or null if a new
	 *                       subject should be created
	 * @return the controller
	 */
	public static SubjectDialogController show(TeacherManager teacherManager, Subject subject) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		SubjectDialogController controller = new SubjectDialogController(teacherManager, stage);
		loader.setController(controller);

		try {
			loader.setLocation(SubjectDialogController.class.getResource("../view/subject_dialog.fxml"));
			Parent p = loader.load();
			controller.setObject(subject);
			Scene scene = new Scene(p);
			stage.setScene(scene);
			stage.showAndWait();
			return controller;
		} catch (IOException e) {
			throw new IllegalStateException("subject_dialog.fxml wasn't found", e);
		}
	}
}
