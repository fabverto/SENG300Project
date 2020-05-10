package service.domain;

import java.util.UUID;

public class User extends Entity {

	private static final long serialVersionUID = 1L;
	private String email;
	private String schoolId;
	private String password;
	private boolean isAdmin;
	private boolean isStudent;
	
	public User() { } //used for persistence Object-Relational Mappers (ORMs)
	
	public User(String name, String schoolId, String email, String password) {
		this.setName(name);
		this.schoolId = schoolId;
		this.email = email;
		this.password = password;
	}

	//set "isadmin" field to true, make sure isstudent is false
	protected void setIsAdmin() {
		this.isAdmin = true;
		this.isStudent = false;
	}
	
	//set "isstudent" field to be true, make sure isadmin is false
	protected void setIsStudent() {
		this.isStudent = true;
		this.isAdmin = false;
	}
	
	//returns true if User is an Admin
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	//returns true if User is a Student
	public boolean isStudent() { 
		return this.isStudent;
	}
	
	//getter for email
	public String getEmail() {
		return email;
	}
	
	//setter for email
	public void setEmail(String email) {
		this.email = email;
	}
	
	//getter for SchoolId
	public String getSchoolId() {
		return schoolId;
	}

	//Setter for SchoolId
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;		
	}
	
	//this generates a unique UUID which can be used as an Id for students and Admin
	//this is used in the promotion and registration tasks
	public static String uniqueId() {
		return UUID.randomUUID().toString();
	}
}
