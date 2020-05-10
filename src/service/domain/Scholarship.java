package service.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import service.exceptions.ApplicationException;
import service.exceptions.NotUniqueException;

public class Scholarship extends Entity {

	private static final long serialVersionUID = 1L;
	private String description;
	private double amountAwarded;
	private double minimumGpa;
	private int maxAwarded;
	private Date dueDate; 

	private Faculty faculty;
	private ArrayList<Student> awardedTo;
	private ArrayList<Student> applicants;
	
	public Scholarship() {  } //used for persistence Object-Relational Mappers (ORMs)

	public Scholarship(String name) {
		super();
		this.setName(name);
	}
	//constructor
	public Scholarship(String name, double minimumGpa, Faculty faculty, double amountAwarded, String description, int maxAwarded, Date dueDate) {
		super();
		this.setName(name);
		this.minimumGpa = minimumGpa;
		this.faculty = faculty;
		this.awardedTo = new ArrayList<>();
		this.applicants = new ArrayList<>();
		this.amountAwarded = amountAwarded;
		this.description = description;
		this.maxAwarded = maxAwarded;
		this.dueDate = dueDate;
	}
	
	  @Override
    public String toString() {
		  String name,description, fac, amount,quantity,gpa;
		  name = "N: " + getName();
		  name = name + spaces(name);
		  description = "D: " + getDescription();
		  description = description + spaces(description);
		  amount = "$" + Double.toString(getAmountAwarded());
		  amount = amount + spaces(amount);
		  quantity = "Q: " + Double.toString(getMaxAwarded());
		  quantity = quantity + spaces(quantity);
		  gpa = "GPA: " + Double.toString(getMinimumGpa());
		  gpa = gpa + spaces(gpa);
		  fac = "F: " + faculty.getName();
		  fac = fac + spaces(fac);
		  return(name + description + fac + quantity + gpa + amount);
    }

    //getter for due date
    public Date getDueDate() {
        return dueDate;
    }

    //getter for due date
  public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
  }
    
	//getter for minimum gpa
	public double getMinimumGpa() {
		return minimumGpa;
	}

	//setter for minimum gpa
	public void setMinimumGpa(double minimumGpa) {
		if(this.minimumGpa < 0 && this.minimumGpa > 4)
			throw new NotUniqueException(String.format("Invalid GPA", minimumGpa));
			
		else	
			this.minimumGpa = minimumGpa;
	}

	//getter for faculty
	public Faculty getFaculty() {
		return faculty;
	}

	//setter for faculty
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	//getter for awarded to list
	public List<Student> getAwardedTo() {
		return awardedTo;
	}

	//getter for application list
	public List<Student> getApplicants() {
		return applicants;
	}

	//getter for description
	public String getDescription() {
		return description;
	}

	//setter for description
	public void setDescription(String description) {
		this.description = description;
	}

	//getter for amount awarded
	public double getAmountAwarded() {
		return amountAwarded;
	}

	//setter for amount awarded
	public void setAmountAwarded(double amountAwarded) {
		this.amountAwarded = amountAwarded;
	}
	
	//getter for max number of students awarded
	public int getMaxAwarded() {
		return maxAwarded;
	}

	//setter for max number of students awarded
	public void setMaxAwarded(int maxAwarded) {
		this.maxAwarded = maxAwarded;
	}
	
	//remove the relationship to faculty
	public void delete() {
		this.faculty.remove(this);
		this.faculty = null;
	}
	
	public boolean hasVacancy() {
		return this.maxAwarded > this.awardedTo.size();
	}

	public boolean isBefore(Date today) {
		if (dueDate == null)
			return false;
		return today.compareTo(dueDate) > 0;
	}

	public void apply(Student applicant) {
		if (this.hasStudentApplied(applicant))
			throw new ApplicationException("Student has already applied for this award!");
		assertHasNotBeenAwarded(applicant);
		
		this.applicants.add(applicant);
	}
	
	public void approve(Student toApprove) {
		if (!this.hasStudentApplied(toApprove))
			throw new ApplicationException("Student has not applied for this award!");
		assertHasNotBeenAwarded(toApprove);
		assertHasRoom();
		
		toApprove.grant(this);
		this.awardedTo.add(toApprove);
		this.applicants.remove(toApprove);
	}
	
	private void assertHasRoom() {
		if (!hasVacancy()) 
			throw new ApplicationException("All awards for this scholarship have been given out");
	}

	private void assertHasNotBeenAwarded(Student student) {

		if (this.awardedTo.contains(student))
			throw new ApplicationException("Student has already been awarded this scholarship!");
	}
	
	//extremely random & dumb function I'm using to see why list of scholarships is not being left indented
	public String spaces(String x) {
		int tot = 25 - x.length();
		//System.out.println(tot);
		String space = "";
		
		if (tot<0)
		{
			return "";
		}
		
		while(tot >= 0) 
		{
			space = space + " ";
			tot--;
		}
		return space;
	}

	//checks if a student has applied for a scholarship
	public boolean hasStudentApplied(Student applicant) {
		for(Student student : getApplicants()) {		
			if (student.getEmail().equals(applicant.getEmail()))
				return true;
		}
		return false;
	}

	public static Scholarship makeOne() {
		return new Scholarship("New", 1.0, new Faculty("Business"), 0.0, "", 0, new Date());
	}
}
