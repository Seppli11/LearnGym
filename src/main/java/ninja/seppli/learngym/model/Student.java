package ninja.seppli.learngym.model;

import java.util.List;

public class Student extends Person {
	
	private List<Grade> grades;
	private Course course;
	
	public Student(String firstname, String lastname, List<Grade> grades, Course course) {
		super(firstname, lastname);
		this.grades = grades;
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	



}
