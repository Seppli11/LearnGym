package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import ninja.seppli.learngym.saveload.JaxbSaverLoader;

/**
 * A class which manages all student. This is mainly used for the
 * {@link JaxbSaverLoader}
 *
 * @author sebi
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Manager<T> {
	/**
	 * the next id
	 */
	private long nextId = Long.MIN_VALUE;
	/**
	 * the list with all objects
	 */
	protected List<T> objects = new ArrayList<>();

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
	public T[] getAll() {
		return objects.stream().toArray(getArrayIntFunction());
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
