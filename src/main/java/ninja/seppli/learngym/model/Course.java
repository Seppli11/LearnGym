package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private String name;
	private Teacher mainTeacher;
	private List<Subject> subjects = new ArrayList<Subject>();
	private List<Student> students = new ArrayList<Student>();
	
	public Course(String name, Teacher mainTeacher) {
		super();
		this.name = name;
		this.mainTeacher = mainTeacher;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getMainTeacher() {
		return mainTeacher;
	}

	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public List<Student> getStudents() {
		return students;
	}
	
	

	
}
