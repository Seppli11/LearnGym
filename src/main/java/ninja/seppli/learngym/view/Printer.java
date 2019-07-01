package ninja.seppli.learngym.view;

import ninja.seppli.learngym.model.Course;

/**
 * An interface which prints a course
 * 
 * @author sebi
 *
 */
public interface Printer {
	/**
	 * prints the given course
	 * 
	 * @param course the course to print
	 */
	public void print(Course course);
}
