package service.task;

import service.domain.Student;
import service.domain.User;

//NOTE THIS IS CURRENTLY NOT USED BUT MAY BE USED IN THE FUTURE, OR DELETED
public class RegistrationTask {
	private final UserTask userTask;
	private final StudentTask studentTask;
	
	public RegistrationTask(UserTask userTask, StudentTask studentTask) {
		this.userTask = userTask;
		this.studentTask = studentTask;
	}
	
	public RegistrationTask() {
		this(new UserTask(), new StudentTask());
	}	

	//this method will register a User as a student
	public Student register(User user) {
		Student created = studentTask.create(user.getName(),  user.getSchoolId(), user.getEmail(), user.getPassword());
		userTask.delete(user.getEmail());
		return created;
	}
	
	public User unregister(Student student) {
		User created = userTask.create(student.getName(),  student.getSchoolId(), student.getEmail(), student.getPassword());
		studentTask.delete(student);
		return created;
	}
}
