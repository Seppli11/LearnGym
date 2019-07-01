package ninja.seppli.learngym.model;

public class Grade {
	private String subjectName;
	private Student student;
	
	public Grade(String subjectName, Student student) {
		super();
		this.subjectName = subjectName;
		this.student = student;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	
}
