
package service.userLogin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import service.task.UserTask;

import java.util.ArrayList;
import java.util.List;
//use this class to test login. code 0 in return means successful, code 1 means unsuccessful.
public class LoginTest {

	//declaration of variables
	private Login login;
	private UserTask userTask;
	private List<String> response;
	
	//tests (this used to be a single test, but I divided it into several different tests)
	@Before
	public void before() {
		response = new ArrayList<String>();
		userTask = new UserTask();
		login = new Login();
		login.wipeUserInfo(300);//used to wipe user info for testing
	}
	
	@Test
	public void passesVerify() {
		response = login.verify("test1@gmail.com", "test1");
		Assert.assertEquals("Test 1: Return code test.","0", response.get(0));
		Assert.assertEquals("Test 2: Return message test.", "Successful", response.get(1));
	}	
	
	@Test
	public void passesVerify2() {
		response = login.verify("test2@gmail.com", "test2");
		Assert.assertEquals("Test 1: Return code test.","0", response.get(0));
		Assert.assertEquals("Test 2: Return message test.", "Successful", response.get(1));
	}

	@Test
	public void failsWhenUserAlreadyExists() {
		userTask.create("exists", "002", "exists@gmail.com", "password");
		
		response = login.verify("exists@gmail.com", "password");
		Assert.assertEquals("Test 3: Return code test.", "1", response.get(0));
		Assert.assertEquals("Test 4: Return code test.", "User already exist.", response.get(1));
	}
	
	@Test
	public void failsWhenPasswordIsWeak() {
		//password has only 3 characters
		response =login.verify("p@gmail.com", "pas");
		Assert.assertEquals("Test 5: Return code test.", "1", response.get(0));
		Assert.assertEquals("Test 6: Return message test.", 
				"Unsuccessfull, email or password does not meet minimum criteria", response.get(1));
	}
	
}
