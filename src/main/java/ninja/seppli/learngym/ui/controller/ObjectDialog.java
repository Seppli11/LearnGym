package ninja.seppli.learngym.ui.controller;

import java.util.Optional;

/**
 * An interface for edit/create dialogs
 *
 * @author sebi
 *
 * @param <T> the type which is created
 */
public interface ObjectDialog<T> {

	/**
	 * Sets the object which should be edited
	 *
	 * @param obj the object
	 */
	void setObject(T obj);

	/**
	 * Opens the dialog and returns an {@link Optional} with the object
	 *
	 * @return the optional
	 */
	Optional<T> getObject();

}
