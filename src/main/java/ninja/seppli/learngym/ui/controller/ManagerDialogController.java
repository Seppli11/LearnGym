package ninja.seppli.learngym.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ninja.seppli.learngym.model.Manager;

/**
 * an general controller for a person manager
 *
 * @author sebi
 * @param <T> the type of the manager
 *
 */
public abstract class ManagerDialogController<T> implements Initializable {
	/**
	 * the person list
	 */
	@FXML
	protected ListView<T> list;

	/**
	 * the manager
	 */
	private Manager<T> manager;

	/**
	 * the stage
	 */
	private Stage stage;

	/**
	 * Constructor
	 *
	 * @param manager the manager
	 * @param stage   the stage
	 */
	public ManagerDialogController(Manager<T> manager, Stage stage) {
		this.manager = manager;
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupListCellFactory();
		list.getItems().setAll(getManager().getAll());
		getManager().getAll().addListener((ListChangeListener<T>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					list.getItems().addAll(c.getAddedSubList());
				}
				if (c.wasRemoved()) {
					list.getItems().removeAll(c.getRemoved());
				}
			}
		});
	}

	/**
	 * Sets up the list cell factory to use {@link #convertToString(Object)}<br>
	 * Source from:
	 * <a>https://stackoverflow.com/questions/36657299/how-can-i-populate-a-listview-in-javafx-using-custom-objects</a>
	 */
	protected void setupListCellFactory() {
		list.setCellFactory(param -> new ListCell<T>() {
			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				setText(convertToString(item));
			}
		});
	}

	/**
	 * An T object to string mapper for the list cells
	 *
	 * @param obj the object
	 * @return the string from the object
	 */
	protected String convertToString(T obj) {
		return obj.toString();
	}

	/**
	 * Create a new person<br>
	 * called by the view
	 *
	 * @param e the event
	 */
	@FXML
	protected abstract void create(ActionEvent e);

	/**
	 * Edits the selected person<br>
	 * called by the view
	 *
	 * @param e the event
	 */
	@FXML
	protected abstract void edit(ActionEvent e);

	/**
	 * Deletes the selected person<br>
	 * called by the view
	 *
	 * @param e the event
	 */
	@FXML
	protected abstract void delete(ActionEvent e);

	/**
	 * Closes the stage<br>
	 * called by the view
	 *
	 * @param e the event
	 */
	@FXML
	protected void close(ActionEvent e) {
		stage.close();
	}

	/**
	 * the person manager
	 *
	 * @return the manager
	 */
	public Manager<T> getManager() {
		return manager;
	}

	/**
	 * the stage of the controller
	 *
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Returns the fxml url of the fxml
	 *
	 * @return the url
	 */
	public static URL getFxmlURL() {
		return ManagerDialogController.class.getResource("../view/manager.fxml");
	}
}
