package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ninja.seppli.learngym.exception.NoGradeYetException;
import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;
import ninja.seppli.learngym.model.TeacherManager;

/**
 * Tests the subject class
 *
 * @author sebi
 *
 */
public class TestSubject {
	private Course course;
	private Subject math;
	private Student student1;
	private Student student2;

	/**
	 * sets up the environement
	 */
	@BeforeEach
	public void setup() {
		Teacher teacher = new TeacherManager().add("Franz", "Meier");
		course = new Course("Math", teacher);

		math = new Subject("Math", teacher);

		StudentManager students = new StudentManager();
		student1 = students.add("Kim", "Müller");
		course.getStudents().add(student1);

		student2 = students.add("Jan", "Haus");
		course.getStudents().add(student2);
	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding1() throws NoGradeYetException {
		math.setGrade(student1, 5);
		math.setGrade(student2, 5.5f);
		assertEquals(5.5f, math.getAverage());
	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding2() throws NoGradeYetException {
		math.setGrade(student1, 5);
		math.setGrade(student2, 6);
		assertEquals(5.5f, math.getAverage());
	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding3() throws NoGradeYetException {
		math.setGrade(student1, 5.5f);
		math.setGrade(student2, 6);
		assertEquals(6f, math.getAverage());
	}

	/**
	 * tests adding grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testAddGrade() throws NoGradeYetException {
		math.setGrade(student1, 5.2f);
		assertEquals(5f, math.getAverage());
	}
}
