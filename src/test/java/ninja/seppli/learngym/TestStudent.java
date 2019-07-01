package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Subject;
import ninja.seppli.learngym.model.Teacher;

/**
 * Tests the student class
 *
 * @author sebi
 *
 */
public class TestStudent {
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
		assertEquals(5f, student.getAverage());
	}

	/**
	 * tests rounding of grades
	 */
	@Test
	public void testRounding2() {
		math.addGrade(student, 5);
		math.addGrade(student, 6);
		assertEquals(5.5f, student.getAverage());
	}

	/**
	 * tests rounding of grades
	 */
	@Test
	public void testRounding3() {
		math.addGrade(student, 5.5f);
		math.addGrade(student, 6);
		assertEquals(6f, student.getAverage());
	}

	/**
	 * tests adding grades
	 */
	@Test
	public void testAddGrade() {
		math.addGrade(student, 5.2f);
		assertEquals(5f, student.getAverage());
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision1() {
		math.addGrade(student, 3);
		math.addGrade(student, 5);
		math.addGrade(student, 5);
		assertFalse(student.isProv());
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision2() {
		math.addGrade(student, 3);
		math.addGrade(student, 5);
		assertTrue(student.isProv());
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision3() {
		math.addGrade(student, 5);
		assertTrue(student.isProv());
	}
}
