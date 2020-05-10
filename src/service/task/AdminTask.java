package service.task;

import service.domain.Admin;
import service.domain.User;
import service.exceptions.ElementNotFoundException;
import service.exceptions.NotUniqueException;
import service.repository.UserRepository;

//This is the entry point to the service layer of the system. The UI will use this 
// class to manage (add, edit, delete) the list of Admins
public class AdminTask {
	public static UserRepository repository;
	
	static {
		repository = UserRepository.getInstance();
	}
	
	//constructor for AdminTask
	public AdminTask() {
	}
	
	//UI will call this method to create a new Admin
	public Admin create(String name, String schoolId,String email, String password) {
		//WE MUST CREATE A USER FIRST, DELETE IT THEN BUILD AN ADMIN WITH THEIR DATA
		//If a ADMIN with this name already exists, throw exception
		if (repository.findBySchoolId(schoolId) != null) {
			throw new NotUniqueException(String.format("Admin %s already with id %s already exists", name, schoolId));
		}
		return (Admin)repository.save(new Admin(name, schoolId, email, password));
	}
	
	public void delete(String schoolId) {
		Admin toDelete = repository.findBySchoolId(schoolId);
		if(toDelete == null) {
			throw new ElementNotFoundException("This admin is not found");
		}
		repository.delete(toDelete);	
	}
	//edit a admin (after the changes have been made in the UI using getters/setters)
	public Admin edit(Admin toEdit) {
		if(repository.findById(toEdit.getId()) == null) {
			throw new ElementNotFoundException("An Item with this ID does not exist in the database");
		}
		return (Admin)repository.save(toEdit);
	}

	//this method is used in the AdminRoleTask to promote a User to an Admin
	public Admin promote(User toPromote) {
		Admin promoted = Admin.create(toPromote);
		repository.save(promoted);
		return promoted;
	}

	//finds an admin by their school Id in the repository
	public Admin findBySchoolId(String Schoolid) {
		Admin found =  repository.findBySchoolId(Schoolid);
		if (found != null) {
			return found;
		}
		throw new ElementNotFoundException("This user was not found in the repository");
	}

}

