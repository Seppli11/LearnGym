package ninja.seppli.learngym.view.console;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
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
		List<Subject> subjects = course.getSubjects();
		String formatStr = "%d\t%s\t";
		for (int i = 0; i < subjects.size(); i++) {
			formatStr += "\t%s";
		}
		formatStr += "\t\t%.1f\t%.1f\t%.1f\t%s\n";
		int i = 1;
		for (Student student : course.getStudents()) {
			printTableLine(i, student, formatStr);
			i++;
		}
	}

	/**
	 * prints a line of the table
	 * 
	 * @param nr        the student number
	 * @param student   the student of the line
	 * @param formatStr the format string which should be used
	 */
	private void printTableLine(int nr, Student student, String formatStr) {
		int maxSubjects = student.getCourse().getSubjects().size();
		Subject[] subjects = student.getCourse().getSubjects().stream().filter(s -> s.containsStudent(student))
				.toArray(Subject[]::new);
		Object[] formatArgs = new Object[6 + maxSubjects];
		Arrays.fill(formatArgs, "");
		formatArgs[0] = nr;
		formatArgs[1] = student.getLastname() + " " + student.getFirstname();
		for (int i = 0; i < subjects.length; i++) {
			formatArgs[2 + i] = subjects[i].getGrade(student);
		}
		formatArgs[2 + maxSubjects + 0] = 0f; // TODO get student's average with Student.getAverage()
		formatArgs[2 + maxSubjects + 1] = 0f; // TODO get student's too low points
		formatArgs[2 + maxSubjects + 2] = 0f; // TODO get student's count of too low marks
		formatArgs[2 + maxSubjects + 3] = "DEF_PR"; // TODO get if the student is prom or prov

		out.printf(formatStr, formatArgs);
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
			formatStr += "\t%.1f";
			formatArgs[i] = subjects.get(i).getAverage();
		}
		formatArgs[subjects.size()] = 0f;
		formatStr += "\t\t%.1f\n";
		out.printf(formatStr, formatArgs);
	}

}
