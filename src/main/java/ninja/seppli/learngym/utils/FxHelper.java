package ninja.seppli.learngym.utils;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javafx.beans.Observable;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Useful helper function to ease the life with javafx bindings/properties
 *
 * @author sebi
 *
 */
public class FxHelper {
	/**
	 * Creates a new list binding and binds the binding to the given dependencies
	 *
	 * @param <T>                   the type of the list
	 * @param computedValueSupplier the supplier which supplies the computed value
	 * @param dependencies          the dependencies of the list
	 * @return the created ListBinding
	 */
	public static <T> ListBinding<T> createListBinding(Supplier<ObservableList<T>> computedValueSupplier,
			Observable... dependencies) {
		return new ListBinding<T>() {
			{
				super.bind(dependencies);
			}

			@Override
			protected ObservableList<T> computeValue() {
				return computedValueSupplier.get();
			}
		};
	}

	/**
	 * This helper function creates a list which is bound to the given source
	 * list.<br>
	 * If the source list changes, the changes are mapped and applied to the created
	 * list.<br>
	 *
	 * The mapper has to be pure, because else when removing an object, it won't
	 * match
	 *
	 * @param <T>    the type of the source list
	 * @param <R>    the type of the mapped list
	 * @param mapper the mapper
	 * @param source the source list
	 * @return the newly created, mapped list
	 */
	public static <T, R> ObservableList<R> mapList(Function<T, R> mapper, ObservableList<T> source) {
		ObservableList<R> returnList = FXCollections
				.observableArrayList(source.stream().map(mapper).collect(Collectors.toList()));
		source.addListener((ListChangeListener<T>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach(obj -> {
						returnList.add(mapper.apply(obj));
					});
				}
				if (c.wasRemoved()) {
					c.getRemoved().forEach(obj -> returnList.remove(mapper.apply(obj)));
				}
			}
		});
		return returnList;
	}

	/**
	 * Shows the dialog and waits until its done
	 *
	 * @param controllerFactory the controller factory
	 * @param fxml              the fxml url
	 * @param <T>               the type of the controller
	 * @return the created controller the created controller
	 *
	 */
	public static <T> T show(Function<Stage, T> controllerFactory, URL fxml) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		T controller = controllerFactory.apply(stage);
		loader.setController(controller);

		try {
			loader.setLocation(fxml);
			Parent p = loader.load();
			Scene scene = new Scene(p);
			stage.setScene(scene);
			stage.showAndWait();
			return controller;
		} catch (IOException e) {
			throw new IllegalStateException("\"" + fxml.getPath() + "\" wasn't found", e);
		}
	}
}
