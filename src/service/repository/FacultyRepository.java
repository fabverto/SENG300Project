package service.repository;

import service.domain.Faculty;
import service.exceptions.ElementNotFoundException;

//This is a list of all of the existing faculties, Extends the generic "AbstractRepository" class 
public class FacultyRepository  extends AbstractRepository<Faculty>{

	//Constructor for faculty repository
	public FacultyRepository() {
	}

	//deletes a faculty from the repository
	public void delete(Faculty toDelete) {
		if(this.findByName(toDelete.getName()) == null) {
			throw new ElementNotFoundException("Faculty does not exist in the repository");
		}
		this.getData().remove(toDelete);
	}
}
