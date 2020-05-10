package service.exceptions;

//used when an element is not found in a list
public class ElementNotFoundException extends RuntimeException {
	public ElementNotFoundException(String message) {
		super(message);
	}
}
