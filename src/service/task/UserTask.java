package service.task;

import java.util.List;

import service.domain.Admin;
import service.domain.User;
import service.exceptions.ElementNotFoundException;
import service.exceptions.NotUniqueException;
import service.repository.UserRepository;

//This class contains all methods that the GUI requires for base User functionality
public class UserTask {

	public static UserRepository repository;
	
	static {
		repository = UserRepository.getInstance();
	}
	
	//constructor for Student
	public UserTask() {
	}
	
	//creates a user
	public User create(String name, String schoolId, String email, String password) {
		//WE MUST CREATE A USER FIRST, DELETE IT THEN BUILD AN STUDENT WITH THEIR DATA
		//If a STUDENT with this name already exists, throw exception
		if (repository.findByEmail(email) != null) {
			throw new NotUniqueException(String.format("User %s already with Email %s already exists", name, email));
		}
		return repository.save(new User(name, schoolId, email, password));
	}	
	
	//deletes a user based on their email (unique)
	public void delete(String email) {
		User toDelete = repository.findByEmail(email);
		if(toDelete == null) {
			throw new ElementNotFoundException("This User is not found");
		}
		repository.delete(toDelete);	
	}
	
	//edit a student (after the changes have been made in the UI using getters/setters)
	public User edit(User toEdit) {
		if(repository.findByEmail(toEdit.getEmail()) == null) {
			throw new ElementNotFoundException("A User with this ID does not exist in the database");
		}
		return repository.save(toEdit);
	}
	
	//Find by Email Method, finds the User in the list based on their email
	public User findByEmail(String email) {
		User found =  repository.findByEmail(email);
		if (found != null)
			return found;
		throw new ElementNotFoundException("This user was not found in the repository");
	}
	
	//this method is used in the AdminRole task to demote an Admin to a user
	public User demote(Admin toDemote) {
		User demoted = Admin.create(toDemote);
		repository.save(demoted);
		return demoted;
	}

	//user login functionality
	public User login(String name, String hash) {
		User user = repository.findByEmail(name);
		if (user == null || !user.getPassword().equals(hash))
			return null;

		return user;		
	}

	//loads user from the repository
	public void load(List<User> data) {
		repository.data.addAll(data);
	}	
}
