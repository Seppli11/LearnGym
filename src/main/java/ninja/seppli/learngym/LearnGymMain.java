package ninja.seppli.learngym;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;
import ninja.seppli.learngym.saveload.CourseModel;
import ninja.seppli.learngym.saveload.JaxbSaverLoader;
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
		CourseModel model = createCourseModel();
		printer.print(model.getCourse());
		JaxbSaverLoader saver = new JaxbSaverLoader();
		saver.save(new File("test.xml"), model);
	}

	private static CourseModel createCourseModel() {
		TeacherManager teachers = new TeacherManager();
		StudentManager students = new StudentManager();

		Teacher teacher = teachers.add("Irene", "Heller");
		Course course = new Course("5LG18a", teacher);

		Student s1 = students.add("Simona", "Aschwand");
		Student s2 = students.add("Nicola", "Berscher");
		Student s3 = students.add("Jasmine", "Kern");
		Student s4 = students.add("Eric", "Lindenbaum");
		Student s5 = students.add("Tim", "Lindenbaum");

		course.getStudents().addAll(Arrays.asList(s1, s2, s3, s4, s5));

		Subject german = new Subject("Deutsch", teacher);
		Subject french = new Subject("Französisch", teacher);
		Subject english = new Subject("Englisch", teacher);
		course.getSubjects().add(german);
		course.getSubjects().add(french);
		course.getSubjects().add(english);

		german.setGrade(s1, 4);
		german.setGrade(s2, 5.5f);
		german.setGrade(s3, 5.0f);
		german.setGrade(s4, 4f);

		french.setGrade(s1, 4);
		french.setGrade(s2, 6);
		french.setGrade(s3, 5);
		french.setGrade(s4, 2);
		return new CourseModel(course, students, teachers);

	}

}
