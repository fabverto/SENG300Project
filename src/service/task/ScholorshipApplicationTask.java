package service.task;

import java.util.Date;

import service.domain.Scholarship;
import service.domain.Student;
import service.exceptions.ApplicationException;
import service.model.ScholarshipApplicationResult;

//This class contains all of the methods that the UI would need for the application process
public class ScholorshipApplicationTask {
	private final StudentTask studentTask;
	private final ScholarshipTask scholarshipTask;
	
	//contructor
	public ScholorshipApplicationTask(StudentTask studentTask, ScholarshipTask scholarshipTask) {
		this.studentTask = studentTask;
		this.scholarshipTask = scholarshipTask;
	}
	
	//CURRENTLY NOT USED, MAY BE USED IN THE FUTURE
	public ScholorshipApplicationTask() {
		this(new StudentTask(), new ScholarshipTask());
	}
	
	//method for a student to apply for a scholarship
	public ScholarshipApplicationResult apply(Student applicant, double gpa, Scholarship award) {
		try {
			applicant.setGpa(gpa);
			validate(applicant, gpa, award);
			award.apply(applicant);
			return ScholarshipApplicationResult.accepted();
		} catch (Exception ex) {
			return ScholarshipApplicationResult.rejected(ex.getMessage());
		}
	}
	
	//validates if the student meets the criteria for a scholarship
	private void validate(Student applicant, double gpa, Scholarship award) {
		if (applicant.hasScholarship())
			throw new ApplicationException("Student has already been awarded a scholarship");
		if (award.getMinimumGpa() > gpa)
			throw new ApplicationException("Student gpa does not meet the minimum requirements");
		if (!award.hasVacancy())
			throw new ApplicationException("This scholarship has been fully awarded already!");
		if (award.isBefore(new Date()))
			throw new ApplicationException("This scholarship has expired!");
	}
}
