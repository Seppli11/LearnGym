package ninja.seppli.learngym.view.console;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.model.Averagable;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.StudentCourse;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.view.Printer;

/**
 * Prints a course to a print stream like the {@link System#out}
 *
 * @author sebi
 *
 */
public class PrintStreamPrinter implements Printer {
	/**
	 * the print stream
	 */
	private PrintStream out;

	/**
	 * Constructor
	 *
	 * @param out the print stream to print the course
	 */
	public PrintStreamPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void print(Course course) {
		Teacher mainTeacher = course.getMainTeacher();
		out.printf("\tKlasse %s\t\t\t\t%s %s\n", course.getName(), mainTeacher.getLastname(),
				mainTeacher.getFirstname());
		out.println();
		printTeachers(course);
		out.println();
		printTableHeaders(course);
		printTableContent(course);

		out.println();
		printSummary(course);
	}

	/**
	 * Prints the teachers to the print stream
	 *
	 * @param course the course to print
	 */
	private void printTeachers(Course course) {
		List<Subject> subjects = course.getSubjects();
		String formatStr = "\tLehrpersonen\t";
		String[] formatArgs = new String[subjects.size()];
		for (int i = 0; i < subjects.size(); i++) {
			formatStr += "\t%s";
			formatArgs[i] = subjects.get(i).getTeacher().getShortname();
		}
		formatStr += "\n";
		out.printf(formatStr, formatArgs);
	}

	/**
	 * prints the table headers
	 *
	 * @param course the course to print
	 */
	private void printTableHeaders(Course course) {
		List<Subject> subjects = course.getSubjects();
		String formatStr = "Nr\tSchülerIn\t";
		String[] formatArgs = new String[subjects.size()];
		for (int i = 0; i < subjects.size(); i++) {
			formatStr += "\t%s";
			formatArgs[i] = subjects.get(i).getShortname();
		}
		formatStr += "\t\tAvg\tT.Pkt.\tT.Note\tprom\n";
		out.printf(formatStr, formatArgs);
	}

	/**
	 * prints the actual content of the table
	 *
	 * @param course the course to print
	 */
	private void printTableContent(Course course) {
		int i = 1;
		for (StudentCourse student : course.getStudents()) {
			printTableLine(i, course, student);
			i++;
		}
	}

	/**
	 * prints a line of the table
	 *
	 * @param nr      the student number
	 * @param course  the course
	 * @param studentCourse the student of the line
	 */
	private void printTableLine(int nr, Course course, StudentCourse studentCourse) {
		StringBuffer formatStr = new StringBuffer("%d\t%s\t");
		int maxSubjects = course.getSubjects().size();
		Subject[] subjects = course.getSubjects().stream().toArray(Subject[]::new);
		Object[] formatArgs = new Object[6 + maxSubjects];
		Arrays.fill(formatArgs, "");
		formatArgs[0] = nr;
		formatArgs[1] = studentCourse.getStudent().getLastname() + " " + studentCourse.getStudent().getFirstname();
		for (int i = 0; i < subjects.length; i++) {
			Subject subject = subjects[i];
			if (subject.containsStudent(studentCourse.getStudent())) {
				formatStr.append("\t%.1f");
				formatArgs[2 + i] = subject.getStudentGradeEntry(studentCourse.getStudent()).getGrade();
			} else {
				formatStr.append("\t%s");
				formatArgs[2 + i] = "-";
			}
		}
		formatStr.append("\t");
		formatArgs[2 + maxSubjects + 0] = printAverageable(studentCourse, formatStr);
		formatArgs[2 + maxSubjects + 1] = studentCourse.getNegativeSum();
		formatArgs[2 + maxSubjects + 2] = studentCourse.getNegativeGradeCounter();
		formatArgs[2 + maxSubjects + 3] = studentCourse.isProv() ? "N_PROM" : "DEF_PR";
		formatStr.append("\t%.1f\t%d\t%s\n");

		out.printf(formatStr.toString(), formatArgs);
	}

	/**
	 * Prints the last line with the summary
	 *
	 * @param course the course to print
	 */
	private void printSummary(Course course) {
		List<Subject> subjects = course.getSubjects();
		String formatStr = "\tDurchschnitt:\t";
		Object[] formatArgs = new Object[subjects.size() + 1];
		for (int i = 0; i < subjects.size(); i++) {
			Subject subject = subjects.get(i);
			if (subject.hasGrades()) {
				formatStr += "\t%.1f";
				try {
					formatArgs[i] = subjects.get(i).getAverage();
				} catch (NoGradeYetException e) {
					throw new RuntimeException("This should have happennd (Subject#hasGrade() doesn't work)", e);
				}
			} else {
				// not directly in format str, because formatArgs has to be filled to avoid the
				// null
				formatStr += "\t%s";
				formatArgs[i] = "-";
			}
		}
		formatArgs[subjects.size()] = 0f;
		formatStr += "\t\t%.1f\n";
		out.printf(formatStr, formatArgs);
	}

	/**
	 * This method can be used in conjunction with
	 * {@link Formatter#format(String, Object...)}
	 *
	 * @param avg       the object
	 * @param formatStr the format stringbuffer which is the format string
	 * @return the object to add to the array
	 */
	private Object printAverageable(Averagable avg, StringBuffer formatStr) {
		if (avg.hasGrades()) {
			formatStr.append("\t%.1f");
			try {
				return avg.getAverage();
			} catch (NoGradeYetException e) {
				throw new IllegalStateException("This shouldn't have happend", e);
			}
		} else {
			formatStr.append("\t%s");
			return "-";
		}
	}
}
