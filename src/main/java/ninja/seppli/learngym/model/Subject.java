package ninja.seppli.learngym.model;

import java.util.List;

/**
 * 
 * @author jfr
 *
 */
public class Subject {
	private String subjectName;
	private List<Grade> grades;
	private List<Teacher> teachers;

	/**
	 * constructor
	 * 
	 * @param subjectName
	 */
	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
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
	public List<Grade> getGrades() {
		return grades;
	}

	/**
	 * 
	 * @return
	 */
	public List<Teacher> getTeachers() {
		return teachers;
	}

}
