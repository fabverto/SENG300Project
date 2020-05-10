package service.userLogin;

import java.util.ArrayList;
import java.util.List;

import service.domain.User;
import service.exceptions.ElementNotFoundException;
import service.task.UserTask;

/* 
 * This class handles login to your application 
 * It can handle sign in, sign up and change password request
 * User credential should meet minimum acceptable criteria
 * CAUTION: Make sure to display acceptable criteria to users
 * Features: uses Cryptography to store users password
*/
public class Login {
	
	private UserTask userTask;
	
	//Constructor
	public Login (){
		this.userTask = new UserTask();
	}
	
	/**
	 * use this function to check user credentials
	 * 
	 * @param   email       user email address
	 * @param   password    user password
	 * @return              result of checking provided credentials against stored user database
	*/
	public boolean checkUserCredentials (String email, String password) throws Exception { 
		
		User found = userTask.findByEmail(email);
		String expectedHash = Hashing.hash(password);
		if (found != null ) {
			String actualHash = Hashing.hash(found.getPassword());

			return expectedHash.equals(actualHash);

		} else {
			return false;
		}
	}

	
	/**
	 * Use this function to change the password
	 * 
	 * @param   email           user email address
	 * @param   currentPass     user current password
	 * @param   newPass         user new password
	 * 
	 * @return  response    list of two strings, the first element is a 0/1 identifying if the sign up was successful (0) or not (1)
	 *                      the second element is a custom message based on different situation
	*/
	public List<String> changePassword (String email, String currentPass, String newPass) throws Exception {
		List<String> response = new ArrayList<>();
			
		if (checkUserCredentials(email, currentPass)==true) {
				if (acceptableCredentials(email, newPass) == true) {
					
					User found = userTask.findByEmail(email);
					found.setPassword(Hashing.hash(newPass));

					//set the response
					response.add("0");
					response.add("Successful");
					return response;
				}
				else {
					response.add("1");
					response.add("Unsuccessfull, password dose not meet minimum criteria");
					return response;
				}
			
		} else {
			response.add("1");
			response.add("Unsuccessfull, email or password is WRONG!");
			return response;
		}
				
	}
	
		
	//Use this function to check if email and password meet minimum acceptable criteria
	private static Boolean acceptableCredentials (String email, String password) {
		if ((password.length() >= 4) & (email.contains("@"))) {
			return true;
		}else {
			return false;
		}
	}	
	//Use this function to show acceptable criteria for the password
	public String displayPassCriteria() {
		return "Password should contains at least 4 charecters";
	
	}
	//Used to clear a users information
	protected void wipeUserInfo(int key) {
		if (key == 300) {
			UserTask.repository.data.clear();
		}
	}

	//used to verify if a student already exists in the list
	public List<String> verify(String email, String password) {
		List<String> response = new ArrayList<>();
		
		try {
			userTask.findByEmail(email);
			response.add("1");
			response.add("User already exist.");
			return response;
		} catch(ElementNotFoundException notFound) {
			//if this is thrown that means the user does not exist
			//which is good.  
		}
		
		//verifies that the user has valid credentials
		if (acceptableCredentials(email, password) == true) {
			response.add("0");
			response.add("Successful");			
			return response;
		}

		response.add("1");
		response.add("Unsuccessfull, email or password does not meet minimum criteria");
		return response;
	}

}