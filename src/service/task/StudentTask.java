package service.task;

import service.domain.Student;
import service.exceptions.ElementNotFoundException;
import service.exceptions.NotUniqueException;
import service.repository.UserRepository;

public class StudentTask {
	public static UserRepository repository;
	
	//static method that takes the instance of UserRepository
	//"static block", repository is initialized when it is referenced, and before any methods are called
	static {
		repository = UserRepository.getInstance();
	}
	
	//constructor for Student
	public StudentTask() {
	}
	
	//UI will call this method to create a new Student
	public Student create(String name, String schoolId, String email, String password) {
		//WE MUST CREATE A USER FIRST, DELETE IT THEN BUILD AN STUDENT WITH THEIR DATA
		//If a STUDENT with this name already exists, throw exception
		if (repository.findBySchoolId(schoolId) != null) {
			throw new NotUniqueException(String.format("Student %s already with id %s already exists", name, schoolId));
		}
		return (Student)repository.save(new Student(name, schoolId, email, password));
	}
	
	//deletes by SchoolID
	public void delete(String schoolId) {
		Student toDelete = repository.findBySchoolId(schoolId);
		if(toDelete == null) {
			throw new ElementNotFoundException("This Student is not found");
		}
		repository.delete(toDelete);	
	}
	
	//edit a student (after the changes have been made in the UI using getters/setters)
	public Student edit(Student toEdit) {
		if(repository.findById(toEdit.getId()) == null) {
			throw new ElementNotFoundException("A student with this ID does not exist in the database");
		}
		return (Student)repository.save(toEdit);
	}

	//deletes by Student object
	public void delete(Student student) {
		Student toDelete = repository.findBySchoolId(student.getSchoolId());
		if(toDelete == null) {
			throw new ElementNotFoundException("This student is not found");
		}
		repository.delete(toDelete);
	}

}
