package service.repository;

import java.util.List;

import service.domain.Admin;
import service.domain.Student;
import service.domain.User;
import service.exceptions.PermissionException;

//Repository for Users (Not this includes students, admins and generic users
public class UserRepository extends AbstractRepository<User> {
	
	private static UserRepository instance;
	//static "block method", which creates an instance of user repository
	static {
		instance = new UserRepository();
	}
	
	//this is a singleton pattern.  only one instance exists ever.
	public static UserRepository getInstance() { return instance; }
	
	//constructor for student repository
	public UserRepository() {
	}

	//reduces compiler warnings :)
	@SuppressWarnings("unchecked")
	public <T> T findBySchoolId(String schoolId) {
		try {
			for (User each : getData()) {
				if (each.getSchoolId().equals(schoolId))				
					return (T)each;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	//Find by Email Method, finds the Email of a User in the list
	public User findByEmail(String Email) {
		for (User each : getData()) {
			if (each.getEmail().equals(Email))
				return each;
		}
		return null;
	}

	//deletes an User from the repository
	public void delete(User toDelete) {
		//check and ensure they do not delete the root admin user
		if(toDelete.getSchoolId().equals(Admin.ROOT_ID) && toDelete instanceof Admin) {
			throw new PermissionException("You cannot delete the root Admin");
		}
		getData().remove(toDelete);
	}

	//used to collapse several instances of a single student who are no longer attached to a scholarship
	//used when reading from the serialized file.
	public void reconcileStudents(List<Student> registeredStudents) {
		for(Student registered : registeredStudents) {
			delete(findByEmail(registered.getEmail()));
		}
		data.addAll(registeredStudents);
	}
}

