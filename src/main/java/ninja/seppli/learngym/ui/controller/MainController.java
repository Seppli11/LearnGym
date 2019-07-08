package ninja.seppli.learngym.ui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentCourse;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.saveload.CourseModel;
import ninja.seppli.learngym.saveload.JaxbLoader;
import ninja.seppli.learngym.saveload.JaxbSaver;
import ninja.seppli.learngym.ui.view.SubjectInformationGrid;

/**
 * The main controller
 *
 * @author sebi
 *
 */
public class MainController implements Initializable {
	/**
	 * logger
	 */
	private Logger logger = LogManager.getLogger();
	/**
	 * the main grid with the student's marks
	 */
	@FXML
	private TableView<Student> mainGrid;

	/**
	 * the pane where the {@link #subjectInformationGrid} is added
	 */
	@FXML
	private BorderPane subjectInfoPane;

	/**
	 * The average table
	 */
	@FXML
	private TableView<StudentCourse> avgTable;

	/**
	 * The average column
	 */
	@FXML
	private TableColumn<StudentCourse, Double> avgColumn;

	/**
	 * The negative sum column
	 */
	@FXML
	private TableColumn<StudentCourse, Double> sumNegativeColumn;

	/*
	 * The negative column
	 */
	@FXML
	private TableColumn<StudentCourse, Integer> negativeCounterColumn;

	/*
	 * The prom column
	 */
	@FXML
	private TableColumn<StudentCourse, String> promColumn;

	/**
	 * the toolbar
	 */
	@FXML
	private ToolBar toolbar;

	/**
	 * the subject infromation grid
	 */
	private SubjectInformationGrid subjectInformationGrid;

	/**
	 * the course model
	 */
	private ObjectProperty<CourseModel> courseModel = new SimpleObjectProperty<>();

	/**
	 * A binding to the course of the course model
	 */
	private ObjectBinding<Course> courseBinding = Bindings
			.createObjectBinding(() -> getCourseModel() == null ? null : getCourseModel().getCourse(), courseModel);

	/**
	 * From where the current file is loaded
	 */
	private File currentLoadedFile;

	/**
	 * Constructor
	 */
	public MainController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		courseModel.addListener(
				(ChangeListener<CourseModel>) (observable, oldValue, newValue) -> reloadCourseModel(newValue));

		subjectInformationGrid = new SubjectInformationGrid();
		subjectInfoPane.setCenter(subjectInformationGrid);
		initAvgTable();
		reloadCourseModel(getCourseModel());
	}

	/**
	 * Inits the avg table
	 */
	private void initAvgTable() {
		avgColumn.setCellValueFactory(cellData -> {
			return cellData.getValue().averageBinding().asObject();
		});

		sumNegativeColumn.setCellValueFactory(cellData -> {
			return cellData.getValue().negativeSumBinding().asObject();
		});

		negativeCounterColumn.setCellValueFactory(cellData -> {
			return cellData.getValue().negativeGradesCounterBinding().asObject();
		});

		promColumn.setCellValueFactory(cellData -> {
			StudentCourse studentCourse = cellData.getValue();
			return Bindings.createStringBinding(() -> {
				return studentCourse.isProv() ? "N_PROM" : "DEF_PR";
			}, studentCourse.provBinding());
		});
	}

	/**
	 * Reloads the course model
	 *
	 * @param model the model
	 */
	private void reloadCourseModel(CourseModel model) {
		mainGrid.getColumns().clear();
		subjectInformationGrid.setCourse(getCourse());
		toolbar.setDisable(model == null);
		setupAvgTable(model);
		if (model == null) {
			logger.warn("CourseModel is null");
			return;
		}

		avgTable.setItems(model.getCourse().getStudents());

		// setup grade grid
		TableColumn<Student, String> studentNameColumn = new TableColumn<>("Students");
		studentNameColumn.setCellValueFactory(cellData -> {
			return cellData.getValue().fullnameBinding();
		});
		studentNameColumn.setSortable(false);
		mainGrid.getColumns().add(studentNameColumn);

		Course course = model.getCourse();
		for (Subject subject : course.getSubjects()) {
			setupSubjectColumn(subject);
		}
		course.getSubjects().addListener((ListChangeListener<Subject>) c -> {
			boolean reload = false;
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach(s -> setupSubjectColumn(s));
				}
				if (c.wasRemoved()) {
					// reload because the column can't be identified
					reload = true;
				}
			}
			if (reload) {
				reloadCourseModel(getCourseModel());
			}
		});
		course.getStudents().addListener((ListChangeListener<StudentCourse>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					mainGrid.getItems().addAll(
							c.getAddedSubList().stream().map(StudentCourse::getStudent).collect(Collectors.toList()));
				}
				if (c.wasRemoved()) {
					mainGrid.getItems().removeAll(c.getRemoved());
				}
			}
		});
		mainGrid.getItems()
				.setAll(course.getStudents().stream().map(StudentCourse::getStudent).collect(Collectors.toList()));

		// setup subject/average grid
		Iterator<TableColumn<Student, ?>> tableColumnIt = mainGrid.getColumns().iterator();
		Iterator<ColumnConstraints> gridColumnIt = subjectInformationGrid.getColumnConstraints().iterator();
		while (tableColumnIt.hasNext() && gridColumnIt.hasNext()) {
			TableColumn<Student, ?> tableColumn = tableColumnIt.next();
			ColumnConstraints gridColumn = gridColumnIt.next();
			gridColumn.prefWidthProperty().bind(tableColumn.widthProperty());
		}

	}

	/**
	 * Adds a subject column to the {@link #mainGrid}
	 *
	 * @param subject the subject
	 */
	private void setupSubjectColumn(Subject subject) {

		TableColumn<Student, Number> subjectColumn = new TableColumn<>();
		subjectColumn.textProperty().bind(subject.subjectNameProperty());
		subjectColumn.setCellValueFactory(cellData -> {
			Student student = cellData.getValue();
			if (!subject.containsStudent(student)) {
				return null;
			}
			return subject.getStudentGradeEntry(student).gradeProperty();
		});
		subjectColumn.setSortable(false);
		subjectColumn.setOnEditCommit(e -> {
			Number num = e.getOldValue();
			if (e.getNewValue() != null) {
				num = e.getNewValue();
			}
			Student student = e.getRowValue();
			if (num == null) {
			} else {
				subject.setGrade(student, num.doubleValue());
				subject.setGrade(student, num.doubleValue());
			}
			mainGrid.refresh();

		});
		subjectColumn.setEditable(true);
		subjectColumn.setCellFactory(param -> new EditCell<Student, Number>(new NumberStringConverter()));
		mainGrid.getColumns().add(subjectColumn);
	}

	/**
	 * Sets up the avg table to the new model
	 *
	 * @param model the model
	 */
	private void setupAvgTable(CourseModel model) {
		avgTable.getItems().clear();
		if (model == null) {
			logger.warn("CourseModel is null");
			return;
		}

	}

	/**
	 * Returns the course model
	 *
	 * @return the model
	 */
	public CourseModel getCourseModel() {
		return courseModel.get();
	}

	/**
	 * Returns the course model property
	 *
	 * @return the prop
	 */
	public ObjectProperty<CourseModel> courseModelProperty() {
		return courseModel;
	}

	/**
	 * Sets the current course model of the controller
	 *
	 * @param courseModel the model
	 */
	public void setCourseModel(CourseModel courseModel) {
		this.courseModel.set(courseModel);
	}

	/**
	 * Returns the course from the {@link #courseModel}
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return courseBinding.get();
	}

	/**
	 * Returns the current loaded file
	 *
	 * @return the file or null if there isn't a file loaded
	 */
	public File getCurrentLoadedFile() {
		return currentLoadedFile;
	}

	/**
	 * Returns the course binding from the coursemodel
	 *
	 * @return the course binding
	 */
	public ObjectBinding<Course> courseBinding() {
		return courseBinding;
	}

	/**
	 * Creates a filechooser to choose/save *.lgdb files
	 *
	 * @return the file chooser
	 */
	private FileChooser createFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("LearnGym DB", "*.lgdb"));
		return fileChooser;
	}

	/**
	 * Opens a file chooser and opens the chosen file<br>
	 * Called from the view
	 *
	 * @param e the event
	 */
	@FXML
	private void openFile(ActionEvent e) {
		FileChooser fileChooser = createFileChooser();
		File chosenFile = fileChooser.showOpenDialog(mainGrid.getScene().getWindow());
		if (chosenFile == null) {
			return;
		}

		try {
			currentLoadedFile = chosenFile;
			CourseModel model = new JaxbLoader().load(chosenFile);
			setCourseModel(model);
		} catch (FileNotFoundException | JAXBException e1) {
			logger.error("Cannot open file \"" + chosenFile.getAbsolutePath() + "\"", e1);
			showError("Cannot open file \"" + chosenFile.getAbsolutePath() + "\"", e1);
		}
	}

	/**
	 * Saves the file to where it was loaded from or if it was never saved asks for
	 * a location
	 *
	 * @param e the event
	 */
	@FXML
	private void saveFile(ActionEvent e) {
		File chosenFile = currentLoadedFile;
		if (chosenFile == null) {
			FileChooser fileChooser = createFileChooser();
			fileChooser.setSelectedExtensionFilter(new ExtensionFilter("LearnGym DB", "*.lgdb"));
			chosenFile = fileChooser.showSaveDialog(mainGrid.getScene().getWindow());
			if (chosenFile == null) {
				return;
			}
		}

		try {
			new JaxbSaver().save(chosenFile, getCourseModel());
		} catch (FileNotFoundException e1) {
			showError("Cannot save", e1);
		}
	}

	/**
	 * Always saves the current loaded file in a new file
	 *
	 * @param e the event
	 */
	@FXML
	private void saveAsFile(ActionEvent e) {
		FileChooser fileChooser = createFileChooser();
		File chosenFile = fileChooser.showSaveDialog(mainGrid.getScene().getWindow());
		if (chosenFile == null) {
			return;
		}

		try {
			new JaxbSaver().save(chosenFile, getCourseModel());
			currentLoadedFile = chosenFile;
		} catch (FileNotFoundException e1) {
			showError("Cannot save", e1);
		}
	}

	/**
	 * Exits the application. Called from the view
	 *
	 * @param e the action event
	 */
	@FXML
	private void exitApplication(ActionEvent e) {
		Platform.exit();
	}

	/**
	 * Opens the subject manager<br>
	 * Called from the view
	 *
	 * @param e the event
	 */
	@FXML
	private void openSubjectManager(ActionEvent e) {
		SubjectManagerDialogController.show(getCourse(), getCourseModel().getTeacherManager());
	}

	/**
	 * Opens the teacher manager<br>
	 * Called from the view
	 *
	 * @param e the event
	 */
	@FXML
	private void openTeacherManager(ActionEvent e) {
		TeacherManagerDialogController.show(getCourseModel().getTeacherManager());
	}

	/**
	 * Opens the student manager<br>
	 * Called from the view
	 *
	 * @param e the event
	 */
	@FXML
	private void openStudentManager(ActionEvent e) {
		StudentManagerDialogController.show(getCourseModel().getStudentManager());
	}

	/**
	 * Opens the about alert<br>
	 * Called from view
	 *
	 * @param e event
	 */
	@FXML
	private void openAbout(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("About");
		alert.setContentText("Die LearnGym Software wurde \nvon Jeriel Frei und Sebastian Zumbrunn entwickelt.");
		alert.showAndWait();
	}

	/**
	 * Shows an error dialog with the stacktrace of the given exception
	 *
	 * @param msg the message
	 * @param e   the exception
	 */
	public static void showError(String msg, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("An error occured");
		alert.setHeaderText(msg);
		VBox dialogPaneContent = new VBox();

		StringWriter stacktraceWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stacktraceWriter));

		dialogPaneContent.getChildren().addAll(new Label("Stack Trace"), new TextArea(stacktraceWriter.toString()));
		alert.getDialogPane().setContent(dialogPaneContent);

		alert.showAndWait();
	}

}
