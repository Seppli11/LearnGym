package ninja.seppli.learngym.model;

import ninja.seppli.learngym.exception.NoGradeYetException;

/**
 * An interface for all class which can average a grade
 *
 * @author jfr and sebi
 *
 */
public interface Averagable {

	/**
	 * Returns an average
	 *
	 * @return the average
	 * @throws NoGradeYetException if there is no grade yet and thus no average can
	 *                             be calculated
	 */
	public double getAverage() throws NoGradeYetException;

	/**
	 * Checks if the averagable object has at least one grade. This can be used to
	 * check if the {@link #getAverage()} will throw a {@link NoGradeYetException}
	 *
	 * @return if the object has a grade
	 */
	public boolean hasGrades();
}
