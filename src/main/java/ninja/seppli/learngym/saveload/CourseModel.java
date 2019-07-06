package ninja.seppli.learngym.saveload;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import ninja.seppli.learngym.model.Course;
import ninja.seppli.learngym.model.StudentManager;
import ninja.seppli.learngym.model.TeacherManager;

/**
 * A class which holds a field to every course, student and teacher so it can be
 * stored in one xml file
 *
 * @author sebi
 *
 */
@XmlRootElement(name = "learngym")
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseModel {
	private Course course;
	private StudentManager studentManager;
	private TeacherManager teacherManager;

	private CourseModel() {
	}

	/**
	 * Constructor
	 *
	 * @param course         the course
	 * @param studentManager the student manger
	 * @param teacherManager the teacher manager
	 */
	public CourseModel(Course course, StudentManager studentManager, TeacherManager teacherManager) {
		this.course = course;
		this.studentManager = studentManager;
		this.teacherManager = teacherManager;
	}

	/**
	 * Returns the course
	 * 
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Returns the student manager
	 * 
	 * @return the student manager
	 */
	public StudentManager getStudentManager() {
		return studentManager;
	}

	/**
	 * Returns the teacher manager
	 * 
	 * @return the teacher manager
	 */
	public TeacherManager getTeacherManager() {
		return teacherManager;
	}
}
