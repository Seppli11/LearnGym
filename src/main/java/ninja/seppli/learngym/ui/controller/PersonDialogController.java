package ninja.seppli.learngym.ui.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ninja.seppli.learngym.model.Person;
import ninja.seppli.learngym.model.Teacher;

/**
 * A controller for an edit/create dialog for people
 *
 * @author sebi
 *
 * @param <T> the type of person
 */
public class PersonDialogController<T extends Person> implements Initializable, ObjectDialog<T> {

	/**
	 * the first name text field
	 */
	@FXML
	private TextField firstnameTF;

	/**
	 * the last name text field
	 */
	@FXML
	private TextField lastnameTF;

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
	private T person;

	/**
	 * the stage
	 */
	private Stage stage;

	/**
	 * the person factory used to create new persons
	 */
	private BiFunction<String, String, T> personFactory;

	/**
	 * Constructor
	 *
	 * @param stage         the stage
	 * @param personFactory the person factory
	 * @param person        the person which is edited or null
	 */
	public PersonDialogController(Stage stage, BiFunction<String, String, T> personFactory, T person) {
		this.stage = stage;
		this.personFactory = personFactory;
		this.person = person;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		okBtn.disableProperty().bind(firstnameTF.textProperty().isEmpty().or(lastnameTF.textProperty().isEmpty()));
		if (person != null) {
			firstnameTF.setText(person.getFirstname());
			lastnameTF.setText(person.getLastname());
		}
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
		String firstname = firstnameTF.getText();
		String lastname = lastnameTF.getText();
		if (person == null) {
			person = personFactory.apply(firstname, lastname);
		} else {
			person.setFirstname(firstname);
			person.setLastname(lastname);
		}
		stage.close();
	}

	@Override
	public Optional<T> getObject() {
		return Optional.ofNullable(person);
	}

	@Override
	public void setObject(T subject) {
		this.person = subject;
	}

	/**
	 * Returns the person factory
	 *
	 * @return the factory
	 */
	public BiFunction<String, String, T> getPersonFactory() {
		return personFactory;
	}

	/**
	 * Returns the fxml url of the fxml
	 *
	 * @return the url
	 */
	public static URL getFxmlURL() {
		return ManagerDialogController.class.getResource("../view/person_dialog.fxml");
	}
}
