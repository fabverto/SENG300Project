package service.exceptions;

//used when a field is not unique when it should be
public class NotUniqueException extends RuntimeException {
	public NotUniqueException(String message) {
		super(message);
	}
}
