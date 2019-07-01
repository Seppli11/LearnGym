package ninja.seppli.learngym;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.Student;
import ninja.seppli.learngym.model.Subject;

public class TestHelper {
	public static Course createCourse() {
		Course course = new Course();
		Subject s1 = new Subject("S1");

		return course;
	}

	public static Student createStudent(Course c) {
		Student student = new Student("Test", "Test", c);
	}

}
