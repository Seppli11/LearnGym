package ninja.seppli.learngym.model;

import java.util.List;

public class Subject {
	private String subjectName;
	private List<Grade> grades;
	private  List<Teacher> teachers;
	
	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}
	
	
}
