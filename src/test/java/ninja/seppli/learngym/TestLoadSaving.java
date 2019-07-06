package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;
import ninja.seppli.learngym.saveload.CourseModel;
import ninja.seppli.learngym.saveload.JaxbLoader;
import ninja.seppli.learngym.saveload.JaxbSaver;

/**
 * Tests loading and saving
 * 
 * @author sebi
 *
 */
public class TestLoadSaving {
	/**
	 * the course model
	 */
	private CourseModel model;
	/**
	 * a loader
	 */
	private JaxbLoader loader = new JaxbLoader();
	/**
	 * a saver
	 */
	private JaxbSaver saver = new JaxbSaver();

	/**
	 * Initializes the environement
	 */
	@BeforeEach
	public void setup() {
		this.model = createCourseModel();
	}

	/**
	 * Tests if the unmarshaled output still has refrences from
	 * {@link TeacherManager} to {@link Course}
	 *
	 * @throws JAXBException
	 */
	@Test
	public void testBasicReferences() throws JAXBException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		saver.save(out, model);

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		CourseModel model = loader.load(in);

		TeacherManager teachers = model.getTeacherManager();
		Course course = model.getCourse();
		assertEquals(teachers.getAll()[0].getId(), course.getMainTeacher().getId());
		assertTrue(teachers.getAll()[0] == course.getMainTeacher());
	}

	/**
	 * Creates a course model to test
	 *
	 * @return the coursemodel
	 */
	private static CourseModel createCourseModel() {
		TeacherManager teachers = new TeacherManager();
		StudentManager students = new StudentManager();

		Teacher teacher = teachers.add("Irene", "Heller");
		Course course = new Course("5LG18a", teacher);

		Student s1 = students.add("Simona", "Aschwand");
		Student s2 = students.add("Nicola", "Berscher");

		course.getStudents().addAll(Arrays.asList(s1, s2));

		Subject german = new Subject("Deutsch", teacher);
		Subject french = new Subject("Französisch", teacher);
		course.getSubjects().add(german);
		course.getSubjects().add(french);

		german.setGrade(s1, 4);
		german.setGrade(s2, 5.5f);

		french.setGrade(s1, 4);
		french.setGrade(s2, 6);
		return new CourseModel(course, students, teachers);

	}
}
