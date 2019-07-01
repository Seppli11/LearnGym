package ninja.seppli.learngym;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.view.console.PrintStreamPrinter;

/**
 *
 * @author jfr and sebi
 *
 */
public class LearnGymMain {
	private static Logger logger = LogManager.getLogger(LearnGymMain.class);

	/**
	 * Main
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		PrintStreamPrinter printer = new PrintStreamPrinter(System.out);
		Course course = createCourse();
		printer.print(course);
	}

	private static Course createCourse() {
		Teacher teacher = new Teacher("Irene", "Heller");
		Course course = new Course("5LG18a", teacher);

		Student s1 = new Student("Simona", "Aschwand");
		Student s2 = new Student("Nicola", "Berscher");
		Student s3 = new Student("Jasmine", "Kern");
		Student s4 = new Student("Eric", "Lindenbaum");

		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addStudent(s4);

		Subject german = new Subject("Deutsch", teacher);
		Subject french = new Subject("Französisch", teacher);
		course.getSubjects().add(german);
		course.getSubjects().add(french);

		german.addGrade(s1, 4);
		german.addGrade(s2, 5.5f);
		german.addGrade(s3, 5.0f);
		german.addGrade(s4, 4f);

		french.addGrade(s1, 4);
		french.addGrade(s2, 6);
		french.addGrade(s3, 5);
		french.addGrade(s4, 2);
		return course;

	}

}
