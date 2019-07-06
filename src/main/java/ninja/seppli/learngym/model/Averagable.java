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
}
