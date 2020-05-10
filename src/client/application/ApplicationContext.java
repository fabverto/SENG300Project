package client.application;


import service.domain.Student;
import service.domain.User;

//this class is used to hold application wide objects.
//currently the user who is logged on is only kept here
public class ApplicationContext {
	public static ApplicationContext instance;
	
	private User currentUser = null;

	//this is a static constructor that you can use to initialize static data 
	//as soon as this class is references and before any of its functions are called.
	static {
		instance = new ApplicationContext();
	}

	//getter to get the lone instance of this class.
	public static ApplicationContext getInstance() {
		return instance;
	}
	
	//private constructor since I want this to be a lone instance
	private ApplicationContext() {
	}
	
	//getter for the user who is logged in
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	//setter for the user who is logged in.  called by the LoginView after a successful login
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Student getCurrentStudent() {
		return (Student) this.currentUser;
	}
}
