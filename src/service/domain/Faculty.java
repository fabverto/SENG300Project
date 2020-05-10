package service.domain;

import java.util.ArrayList;
import java.util.List;

import service.exceptions.NotUniqueException;

public class Faculty  extends Entity {

	private static final long serialVersionUID = 1L;
	private List<Scholarship> scholarships;
	
	public Faculty() { } //used for persistence Object-Relational Mappers (ORMs)
	
	//constructor for faculty
	public Faculty(String name) {
		this.setName(name);
		this.scholarships = new ArrayList<>();
	}

	//setter for the list of scholarships
	public List<Scholarship> getScholarships() {
		return scholarships;
	}

	//adds a scholarship to the faculty
	public void addScholarship(Scholarship scholarship) {
		for(Scholarship each : this.scholarships) {
			if(each.getName() == scholarship.getName()) {
				throw new NotUniqueException("A scholarship with this name already exists in the Faculty");
			}
		}
		scholarship.setFaculty(this);
		scholarships.add(scholarship);
	}

	//removes a scholarship from the list in the faculty
	public void remove(Scholarship scholarship) {
		this.scholarships.remove(scholarship);
	}

	//returns true if the scholarship list is not empty
	public boolean hasScholarships() {
		return !this.scholarships.isEmpty();
	}

}
