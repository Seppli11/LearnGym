package ninja.seppli.learngym.model;

import java.util.function.IntFunction;

import javax.xml.bind.annotation.XmlElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ninja.seppli.learngym.saveload.JaxbLoader;

/**
 * A class which manages all student. This is mainly used for the
 * {@link JaxbLoader}
 *
 * @author sebi
 * @param <T> the type of the manager
 *
 */
public abstract class Manager<T> {
	/**
	 * the next id
	 */
	@XmlElement
	private long nextId = Long.MIN_VALUE;
	/**
	 * the list with all objects
	 */
	@XmlElement
	protected ObservableList<T> objects = FXCollections.observableArrayList();

	/**
	 * An unmodifiable list of objects
	 */
	private ObservableList<T> finalObjects = FXCollections.unmodifiableObservableList(objects);

	/**
	 * Constructor
	 */
	public Manager() {
	}

	/**
	 * Removes a student
	 *
	 * @param obj the student to remove
	 * @return if the operation was successsful
	 */
	public boolean remove(T obj) {
		return objects.remove(obj);
	}

	/**
	 * A list of all students
	 *
	 * @return the students
	 */
	public ObservableList<T> getAll() {
		return finalObjects;
	}

	/**
	 * Returns the {@link IntFunction} needd to create a new array in
	 * {@link #getAll()}
	 *
	 * @return the {@link IntFunction}
	 */
	protected abstract IntFunction<T[]> getArrayIntFunction();

	/**
	 * Returns a unique id
	 *
	 * @return the id
	 */
	protected String getNextInt() {
		return "ID#" + nextId++;
	}

}
