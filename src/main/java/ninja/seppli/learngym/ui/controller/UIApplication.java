package ninja.seppli.learngym.ui.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The gui application start point
 * 
 * @author sebi
 *
 */
public class UIApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent parent = FXMLLoader.load(MainController.class.getResource("../view/main.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setTitle("LearnGym");
			primaryStage.show();
		} catch (IOException e) {
			throw new IllegalStateException("Cannot laod main.fxml", e);
		}
	}

	/**
	 * Launches the gui
	 * 
	 * @param args the command line args
	 */
	public static void openGui(String[] args) {
		launch(args);
	}
}
