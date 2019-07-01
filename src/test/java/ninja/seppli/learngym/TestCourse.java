package ninja.seppli.learngym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Teacher;

public class TestCourse {
	private Course course;
	private Subject math;
	private Student student;

	@BeforeEach
	public void setup() {
		Teacher teacher = new Teacher("Franz", "Meier");
		course = TestHelper.createCourse();

		math = new Subject("Math");

		student = new Student("Kim", "Müller");
		course.addStudent(student);
		math.addStudent(student);

	}

	@Test
	public void testRounding() {
		subject.addGrade(student, 5);
		subject.addGrade(student, 5.5);
		assertEquals(5, course.getAverage());
	}

}
