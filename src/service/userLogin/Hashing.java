package service.userLogin;

import java.security.MessageDigest;

public class Hashing {
	
	//This class is used for hasing a string, used specifically for passwords
	public Hashing( ) {}
	
		//use this function to get a bytePass SHA-256 of a string in hex
	public static String hash(String pass) {
		try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] bytePass = digest.digest(pass.getBytes("UTF-8"));
	        
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < bytePass.length; i++) {
	        	
	            String hex = Integer.toHexString(0xff & bytePass[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
		        return hexString.toString();
		        
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

}