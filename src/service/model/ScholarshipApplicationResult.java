package service.model;

public class ScholarshipApplicationResult extends Result {

	private ScholarshipApplicationResult(boolean accepted, String errorMessage) {
		super(accepted, errorMessage);
	}
	
	public static ScholarshipApplicationResult accepted() {
		return new ScholarshipApplicationResult(true, "You have succesfully applied!");
	}
	
	public static ScholarshipApplicationResult rejected(String errorMessage) {
		return new ScholarshipApplicationResult(false, errorMessage);
	}
}
