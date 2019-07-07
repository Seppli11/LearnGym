package ninja.seppli.learngym.ui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.saveload.CourseModel;
import ninja.seppli.learngym.saveload.JaxbLoader;

public class MainController implements Initializable {
	private Logger logger = LogManager.getLogger();
	@FXML
	private TableView<Student> mainGrid;

	private ObjectProperty<CourseModel> courseModel = new SimpleObjectProperty<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		courseModel.addListener(
				(ChangeListener<CourseModel>) (observable, oldValue, newValue) -> reloadCourseModel(newValue));
		reloadCourseModel(getCourseModel());
	}

	private void reloadCourseModel(CourseModel model) {
		mainGrid.getColumns().clear();
		if (model == null) {
			logger.warn("CourseModel is null");
			return;
		}

		Course course = model.getCourse();
		for (Subject subject : course.getSubjects()) {
			TableColumn<Student, Double> subjectCol = new TableColumn<>(subject.getSubjectName());
			ObservableMap<Student, Double> gradeMap = subject.getGradeMap();
			subjectCol.setCellValueFactory(param -> Bindings.createObjectBinding(() -> {
				return gradeMap.get(param.getValue());
			}, gradeMap));
			mainGrid.getColumns().add(subjectCol);
		}
		course.getStudents().addListener((ListChangeListener<Student>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					mainGrid.getItems().addAll(c.getAddedSubList());
				}
				if (c.wasRemoved()) {
					mainGrid.getItems().removeAll(c.getRemoved());
				}
			}
		});
		mainGrid.getItems().setAll(course.getStudents());
	}

	public CourseModel getCourseModel() {
		return courseModel.get();
	}

	public ObjectProperty<CourseModel> courseModelProperty() {
		return courseModel;
	}

	public void setCourseModel(CourseModel courseModel) {
		this.courseModel.set(courseModel);
	}

	@FXML
	private void openFile(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		File chosenFile = fileChooser.showOpenDialog(mainGrid.getScene().getWindow());
		if (chosenFile == null) {
			return;
		}

		try {
			CourseModel model = new JaxbLoader().load(chosenFile);
			setCourseModel(model);
		} catch (FileNotFoundException | JAXBException e1) {
			logger.error("Cannot open file \"" + chosenFile.getAbsolutePath() + "\"", e1);
			showError("Cannot open file \"" + chosenFile.getAbsolutePath() + "\"", e1);
		}
	}

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
