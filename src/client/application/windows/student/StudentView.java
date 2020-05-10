package client.application.windows.student;

import service.domain.Scholarship;

/*
 * Student Tasks used in ScholarshipsView are defined here
 */
public class StudentView{
		
	//FOR A FUTURE USE
	public static void makeStudent() {
		
	}

	//FOR A FUTURE USE
	static void viewAppliedScholarships() {
		//WIP
	}

	//FOR A FUTURE USE
	static void acceptScholarships() {
		//WIP
	}
	
	//Function to add the Student buttons on the ScholarshipListView page
	static void addButtons() {
	}

	public static void launchAppliedScholarshipsView() {
		ScholarshipsAppliedView.launch();
	}

	public static void launchApplyScholarshipView(Scholarship scholarship) {
		ApplyForScholarshipView.Launch(scholarship);
	}

}
