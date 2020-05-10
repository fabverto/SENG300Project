package service.model;

public class Result {
	private final boolean accepted;
	private final String errorMessage;
	
	public Result(boolean accepted, String errorMessage) {
		this.accepted = accepted;
		this.errorMessage = errorMessage;
	}
	
	public boolean isAccepted() { return this.accepted; }
	public String getErrorMessage() { return this.errorMessage; }
}
