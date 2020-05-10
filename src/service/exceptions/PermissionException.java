package service.exceptions;

//used when a class with insufficient permissions attempts to access data/functions
public class PermissionException extends RuntimeException {
	public PermissionException(String message) {
		super(message);
	}
}
