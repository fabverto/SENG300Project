package service.task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import service.domain.Faculty;
import service.domain.Scholarship;
import service.domain.Student;
import service.exceptions.ApplicationException;
import service.exceptions.ElementNotFoundException;
import service.exceptions.NotUniqueException;
import service.model.ApproveStudentResult;
import service.repository.ScholarshipRepository;

public class ScholarshipTask {
	public static ScholarshipRepository repository;

	static {
		 repository = new ScholarshipRepository();
	}
	
	public ScholarshipTask() {
	}
	
	//UI will call this method to create a new faculty
	public Scholarship create(String name, double minimumGpa, Faculty faculty, double amountAwarded, String description, int maxAwarded, Date date) {
		//If a scholarship with this name already exists, throw exception
		if (repository.findByName(name) != null) {
			throw new NotUniqueException(String.format("Scholarship %s already exists", name));
		}
		//creates the scholarship
		Scholarship scholarship = new Scholarship(name,minimumGpa,faculty,amountAwarded,description,maxAwarded, date);
		//adds scholarship to the list in faculty
		faculty.addScholarship(scholarship);
		return repository.save(scholarship);
	}

	
	//delete a scholarship
	public void delete(String name) {
		Scholarship toDelete = repository.findByName(name);
		if(toDelete == null) {
			throw new ElementNotFoundException("There does not exist a scholarship with this name to delete");
		}
	
		toDelete.delete();
		repository.delete(toDelete);
	}

	//edit a scholarship (after the changes have been made in the UI using getters/setters)
	public Scholarship edit(Scholarship toEdit) {
		if(repository.findById(toEdit.getId()) == null) {
			throw new ElementNotFoundException("An Item with this ID does not exist in the database");
		}
		return repository.save(toEdit);
	}

	//loads the data from the repository
	public void load(List<Scholarship> data) {
		repository.data.addAll(data);		
	}
	
	public List<Scholarship> findAppliedForStudent(Student student) {
		return repository.getData().stream()
									.filter(each -> each.getApplicants().contains(student))
									.collect(Collectors.toList());
	}

	public ApproveStudentResult approve(Scholarship scholarship, Student toApprove) {
		try {
			scholarship.approve(toApprove);
		} catch (ApplicationException ex) {
			return ApproveStudentResult.fail(ex.getMessage());
		}		
		return ApproveStudentResult.success();
	}
}