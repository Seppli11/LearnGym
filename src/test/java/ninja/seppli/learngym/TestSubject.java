package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;

/**
 * Tests the subject class
 * 
 * @author sebi
 *
 */
public class TestSubject {
	private Course course;
	private Subject math;
	private Student student;

	/**
	 * sets up the environement
	 */
	@BeforeEach
	public void setup() {
		Teacher teacher = new Teacher("Franz", "Meier");
		course = new Course("Math", teacher);

		math = new Subject("Math", teacher);

		student = new Student("Kim", "Müller");
		course.addStudent(student);
	}

	/**
	 * tests rounding of grades
	 */
	@Test
	public void testRounding1() {
		math.addGrade(student, 5);
		math.addGrade(student, 5.5f);
		assertEquals(5f, math.getAverage());
	}

	/**
	 * tests rounding of grades
	 */
	@Test
	public void testRounding2() {
		math.addGrade(student, 5);
		math.addGrade(student, 6);
		assertEquals(5.5f, math.getAverage());
	}

	/**
	 * tests rounding of grades
	 */
	@Test
	public void testRounding3() {
		math.addGrade(student, 5.5f);
		math.addGrade(student, 6);
		assertEquals(6f, math.getAverage());
	}

	/**
	 * tests adding grades
	 */
	@Test
	public void testAddGrade() {
		math.addGrade(student, 5.2f);
		assertEquals(5f, math.getAverage());
	}
}
