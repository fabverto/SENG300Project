package service.repository;

import java.util.ArrayList;
import java.util.List;

import service.domain.Entity;

//This is a general repository, every other repository will extend this
//Holds a list of data and 
public abstract class AbstractRepository<T extends Entity> {
	public ArrayList<T> data;

	//creates a basic abstract repository
	protected AbstractRepository() {
		data = new ArrayList<>();
	}

	//returns all of the data in the arraylist
	public ArrayList<T> getData() {
		return data;
	}

	//finds an item by it's unique ID
	public T findById(int id) {
		for(T each : getData()) {
			if (each.getId() == id)
				return each;
		}
		return null;
	}
	
	//Find by Name Method, finds the name of a faculty in the list
	public T findByName(String name) {
		for (T each : getData()) {
			if (each.getName().equals(name))
				return each;
		}
		return null;
	}
	
	//this method is to add or update elements of the repository
	public T save(T toSave) {
		//if the object with this ID is not found, then it is a new element and must be saved
		if (findById(toSave.getId()) == null) {
			toSave.setId(data.size()+1);
			data.add(toSave);
		}	
		return toSave;
	}
	
	public List<T> findAll() {
		return data;
	}
	
	//deletes the data in the list
	public T delete() {
		data.clear();
		return null;
	}
	
}
