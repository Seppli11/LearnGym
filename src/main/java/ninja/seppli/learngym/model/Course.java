package ninja.seppli.learngym.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private String name;
	private Teacher mainTeacher;
	private List<Subject> subjects = new ArrayList<Subject>();
	private List<Student> students = new ArrayList<Student>();

}
