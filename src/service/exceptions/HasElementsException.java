package service.exceptions;

//used when an element already exists inside of a list
public class HasElementsException extends RuntimeException {
	public HasElementsException(String message) {
		super(message);
	}

}
