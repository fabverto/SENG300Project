package service.exceptions;

//used when there is a problem with applying to a scholarship
public class ApplicationException  extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String message) {
		super(message);
	}
}
