package ninja.seppli.learngym.model;

public class Student extends Person {
	private Course course;

	public Student(String firstname, String lastname, Course course) {
		super(firstname, lastname);
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	protected void setCourse(Course course) {
		this.course = course;
	}

}
