package service.repository;

import java.util.ArrayList;
import java.util.HashMap;

import service.domain.Scholarship;
import service.domain.Student;
import service.exceptions.ElementNotFoundException;

public class ScholarshipRepository extends AbstractRepository <Scholarship> {
	//constructor for admin repository
	public ScholarshipRepository() {
		
	}

	//this deletes a scholarship from the repository
	public void delete(Scholarship toDelete) {
		if(this.findByName(toDelete.getName()) == null) {
			throw new ElementNotFoundException("Scholarship does not exist in the repository");
		}
		this.getData().remove(toDelete);
	}

	//returns an arraylist of all students registered! this is necessary for
	//the object relationships after reading the data from the serialized files
	//(since the students have the potential to be serialized to 2 files, we must consolidate them).
	public ArrayList<Student> findAllStudentsRegistered() {
		HashMap<String, Student> students = new HashMap<String, Student>();

		for(Scholarship each : data) {
			for(Student eachStudent : each.getApplicants()) {
				students.put(eachStudent.getEmail(), eachStudent);
			}

			for(Student eachStudent : each.getAwardedTo()) {
				students.put(eachStudent.getEmail(), eachStudent);
			}
		}
	
		return new ArrayList<Student>(students.values());
	}
}
