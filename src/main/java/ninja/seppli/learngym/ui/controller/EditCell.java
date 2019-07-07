package ninja.seppli.learngym.ui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.Event;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 *
 * source copied from here: https://gist.github.com/james-d/be5bbd6255a4640a5357
 * with some minor modifications
 *
 * @author james-d
 * @param <S> The type of the TableView generic type (i.e. S == TableView<S>).
 *            This should also match with the first generic type in TableColumn.
 * @param <T> The type of the item contained within the Cell
 *
 */
public class EditCell<S, T> extends TableCell<S, T> {
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger();

	// TODO: allow this to be a plugable control.
	/**
	 * Text field for editing
	 */
	private final TextField textField = new TextField();

	/**
	 * Converter for converting the text in the text field to the user type, and
	 * vice-versa:
	 */
	private final StringConverter<T> converter;

	/**
	 * constructor
	 *
	 * @param converter the string converter
	 */
	public EditCell(StringConverter<T> converter) {
		this.converter = converter;

		itemProperty().addListener((obx, oldItem, newItem) -> {
			if (newItem == null) {
				setText(null);
			} else {
				setText(converter.toString(newItem));
			}
		});
		setGraphic(textField);
		setContentDisplay(ContentDisplay.TEXT_ONLY);

		textField.setOnAction(evt -> {
			try {
				commitEdit(this.converter.fromString(textField.getText()));
			} catch (Exception e) {
				logger.warn("Couldn't convert to string", e);
			}
		});
		textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				commitEdit(this.converter.fromString(textField.getText()));
			}
		});
		textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				textField.setText(converter.toString(getItem()));
				cancelEdit();
				event.consume();
			} else if (event.getCode() == KeyCode.RIGHT) {
				getTableView().getSelectionModel().selectRightCell();
				event.consume();
			} else if (event.getCode() == KeyCode.LEFT) {
				getTableView().getSelectionModel().selectLeftCell();
				event.consume();
			} else if (event.getCode() == KeyCode.UP) {
				getTableView().getSelectionModel().selectAboveCell();
				event.consume();
			} else if (event.getCode() == KeyCode.DOWN) {
				getTableView().getSelectionModel().selectBelowCell();
				event.consume();
			}
		});
	}

	/**
	 * Convenience converter that does nothing (converts Strings to themselves and
	 * vice-versa...).
	 */
	public static final StringConverter<String> IDENTITY_CONVERTER = new StringConverter<String>() {

		@Override
		public String toString(String object) {
			return object;
		}

		@Override
		public String fromString(String string) {
			return string;
		}

	};

	/**
	 * Convenience method for creating an EditCell for a String value.
	 *
	 * @return returns the edit cell
	 */
	public static <S> EditCell<S, String> createStringEditCell() {
		return new EditCell<S, String>(IDENTITY_CONVERTER);
	}

	// set the text of the text field and display the graphic
	@Override
	public void startEdit() {
		super.startEdit();
		textField.setText(converter.toString(getItem()));
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		textField.requestFocus();
	}

	// revert to text display
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}

	// commits the edit. Update property if possible and revert to text display
	@Override
	public void commitEdit(T item) {

		// This block is necessary to support commit on losing focus, because the
		// baked-in mechanism
		// sets our editing state to false before we can intercept the loss of focus.
		// The default commitEdit(...) method simply bails if we are not editing...
		if (!isEditing() && !item.equals(getItem())) {
			TableView<S> table = getTableView();
			if (table != null) {
				TableColumn<S, T> column = getTableColumn();
				CellEditEvent<S, T> event = new CellEditEvent<>(table,
						new TablePosition<S, T>(table, getIndex(), column), TableColumn.editCommitEvent(), item);
				Event.fireEvent(column, event);
			}
		}

		super.commitEdit(item);

		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}

}
