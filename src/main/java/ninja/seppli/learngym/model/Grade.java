package ninja.seppli.learngym.model;

/**
 * 
 * @author jfr
 *
 */
public class Grade {
	private String subjectName;
	private Student student;

	/**
	 * Constructor
	 * 
	 * @param subjectName
	 * @param student
	 */
	public Grade(String subjectName, Student student) {
		super();
		this.subjectName = subjectName;
		this.student = student;
	}

	/**
	 * 
	 * @return
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 
	 * @param subjectName
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * 
	 * @return
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * 
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

}
