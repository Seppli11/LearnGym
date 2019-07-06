package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
 * Tests the student class
 *
 * @author sebi
 *
 */
public class TestStudent {
	private Course course;
	private Subject math;
	private Subject french;
	private Student student;

	/**
	 * sets up the environement
	 */
	@BeforeEach
	public void setup() {
		Teacher teacher = new TeacherManager().add("Franz", "Meier");
		course = new Course("7abj", teacher);

		math = new Subject("Math", teacher);
		course.getSubjects().add(math);

		french = new Subject("French", teacher);
		course.getSubjects().add(french);

		student = new StudentManager().add("Kim", "Müller");
		course.getStudents().add(student);
	}

	/**
	 * Tests if the stream works
	 */
	@Test
	public void testStreamOfGrades() {
		math.setGrade(student, 4);
		french.setGrade(student, 3);
		double[] grades = course.streamOfGrades(student).sorted().toArray();
		assertArrayEquals(new double[] { 3, 4 }, grades);

	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding1() throws NoGradeYetException {
		math.setGrade(student, 5);
		french.setGrade(student, 5.5f);
		assertEquals(5.5f, course.getAverageOfStudent(student));
	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding2() throws NoGradeYetException {
		math.setGrade(student, 5);
		french.setGrade(student, 6);
		assertEquals(5.5f, course.getAverageOfStudent(student));
	}

	/**
	 * tests rounding of grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testRounding3() throws NoGradeYetException {
		math.setGrade(student, 5.5f);
		french.setGrade(student, 6);
		assertEquals(6d, course.getAverageOfStudent(student));
	}

	/**
	 * tests adding grades
	 *
	 * @throws NoGradeYetException
	 */
	@Test
	public void testAddGrade() throws NoGradeYetException {
		math.setGrade(student, 5.2f);
		assertEquals(5f, course.getAverageOfStudent(student));
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision1() {
		math.setGrade(student, 3);
		french.setGrade(student, 5);
		math.setGrade(student, 5);
		assertFalse(course.isStudentProv(student));
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision2() {
		math.setGrade(student, 3);
		french.setGrade(student, 5);
		assertTrue(course.isStudentProv(student));
	}

	/**
	 * tests prom/prov decision making
	 */
	@Test
	public void testPromDecision3() {
		math.setGrade(student, 5);
		assertFalse(course.isStudentProv(student));
	}
}
