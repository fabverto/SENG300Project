package service.task;

import service.domain.Faculty;
import service.exceptions.ElementNotFoundException;
import service.exceptions.HasElementsException;
import service.exceptions.NotUniqueException;
import service.repository.FacultyRepository;

//This is the entry point to the service layer of the system. The UI will use this class to manage (add, edit, delete) the list of faculties
public class FacultyTask {	
	
	public static FacultyRepository repository;
	
	static {
		repository = new FacultyRepository();
	}
	
	//constructor for facultyTask
	public FacultyTask() {}
	
	//UI will call this method to create a new faculty
	public Faculty create(String name) {
		//If a faculty with this name already exists, throw exception
		if (repository.findByName(name) != null) {
			throw new NotUniqueException(String.format("faculty %s already exists", name));
		}
		return repository.save(new Faculty(name));
	}
	
	//delete a faculty
	public void delete(String name) {
		Faculty toDelete = repository.findByName(name);
		if (toDelete == null) {
			throw new ElementNotFoundException("Faculty not found");
		}
		//ensures that a faculty can only be deleted if it has not scholarships
		if (toDelete.hasScholarships()) {
			throw new HasElementsException("The faculty cannot be deleted if it has scholarships still available");
		}
		repository.delete(toDelete);
	}

	//edit a Faculty (after the changes have been made in the UI using getters/setters)
	public Faculty edit(Faculty toEdit) {
		if(repository.findById(toEdit.getId()) == null) {
			throw new ElementNotFoundException("An Item with this ID does not exist in the database");
		}
		return repository.save(toEdit);
	}
	
}
