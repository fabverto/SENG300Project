package service.domain;

public class Student extends User {

	private static final long serialVersionUID = 1L;
	private boolean hasScholarship = false;
	private double amountReceived = 0;
	private double gpa;
	
	public Student() { } //used for persistence Object-Relational Mappers (ORMs)

	//constructor for student
	public Student(String name, String schoolId, String email, String password) {
		super(name, schoolId, email, password);
		this.setSchoolId(schoolId);
		this.gpa = 0.0;
		setIsStudent();
	}
	
	public double getGpa( ) { return this.gpa; }
	public void setGpa(double gpa) {this.gpa = gpa; }
	
	//setter for amount received
	public void setAmountRecieved(double amount_received) {
		this.amountReceived = amount_received;
	}
	
	//getter for amount received
	public double getAmountRecieved() {
		return this.amountReceived;
	}
	
	//getter for hasScholarship
	public boolean hasScholarship () {
		return hasScholarship;
	}
	
	//Setter for hasScholarship
	public void setHasScholarship(Boolean hasScholarship) {
		this.hasScholarship = hasScholarship;
	}
	
	//overrides parent method, returns true for is student
	@Override
	public boolean isStudent() { return true; }
	
	@Override 
	public String toString() {
		return "Name: " + getName() +
			   "             ID: " + getSchoolId() +
			   "             Gpa: " + Double.toString(getGpa())	;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null)
        	return false;
        if (!(obj instanceof Student))
        	return false;
        
        Student other = (Student)obj;       
        return this.getId() == other.getId();
    }
	public void grant(Scholarship scholarship) {
		this.hasScholarship = true;
		this.setAmountRecieved(scholarship.getAmountAwarded());		
	}
	
}
