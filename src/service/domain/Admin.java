package service.domain;

public class Admin extends User {
	
	private static final long serialVersionUID = 1L;
	public static final String ROOT_ID = "001";
	
	public Admin() { } //used for persistence Object-Relational Mappers (ORMs)

	//constructor for admin
	public Admin(String name, String schoolId, String email, String password) {
		super(name, schoolId, email, password);
		this.setSchoolId(schoolId);
		setIsAdmin();
	}

	//overrides method from user, returns true when asked if admin
	@Override
	public boolean isAdmin() { return true; }
	
	//This creates an Admin from a user's data (static factory method)
	public static Admin create(User user) {
		return new Admin(user.getName(),User.uniqueId(), user.getEmail(), user.getPassword());
	}

	public boolean isRootAdmin() {
		return ROOT_ID.equals(getSchoolId());
	}
	
} 
