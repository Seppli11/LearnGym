package ninja.seppli.learngym;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;
import ninja.seppli.learngym.saveload.CourseModel;
import ninja.seppli.learngym.saveload.JaxbLoader;
import ninja.seppli.learngym.ui.controller.UIApplication;
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
	 * @param args the sytem args
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			if (args.length != 1) {
				logger.error("Usage: learngym <filepath>");
			}
			File file = new File(args[0]);
			try {
				CourseModel model = new JaxbLoader().load(file);
				PrintStreamPrinter printer = new PrintStreamPrinter(System.out);
				printer.print(model.getCourse());
			} catch (FileNotFoundException | JAXBException e) {
				logger.error("Cannot load file \"{}\"", file.getAbsoluteFile());
			}
		} else {
			UIApplication.openGui(args);
		}
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

		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addStudent(s4);
		course.addStudent(s5);

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
