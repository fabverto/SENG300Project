package service.model;

public class ApproveStudentResult extends Result {

	public ApproveStudentResult(boolean accepted, String errorMessage) {
		super(accepted, errorMessage);
	}

	public static ApproveStudentResult success() {
		return new ApproveStudentResult(true, "success");
	}

	public static ApproveStudentResult fail(String message) {
		return new ApproveStudentResult(false, message);
	}

}
