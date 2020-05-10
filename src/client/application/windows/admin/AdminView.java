package client.application.windows.admin;

import javax.swing.JOptionPane;

import client.application.windows.ScholarshipListView;
import client.application.windows.ScholarshipView;
import service.domain.Scholarship;
import service.task.ScholarshipTask;

/*
 * Administrator Tasks used in ScholarshipsView are defined here
 */
public class AdminView{
	
	public static void ApproveApplicants(Scholarship scholarship) {
		 if(scholarship != null)
	            ApproveStudentView.launch(scholarship);
	        else{
	        	JOptionPane.showMessageDialog(null, "No scholarship has been selected", "Error - Empty list",
						JOptionPane.ERROR_MESSAGE);
	        } 
	}
    
	//This function set6ups which scholarship to view/edit or displays a message if there aren't in the system.
    public static void EditScholarship(Scholarship scholarship) {
        if(scholarship != null)
            ScholarshipView.launch(scholarship, false);
        else{
        	JOptionPane.showMessageDialog(null, "No scholarship has been selected", "Error - Empty list",
					JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    //function that will the system of all scholarships.
    public static void ClearScholarships(ScholarshipListView view ) {
    	ScholarshipTask.repository.delete();
    	for(int i = ScholarshipTask.repository.data.size() - 1; i >= 0; i--) 
        {
    		view.getModel().remove(i);
        }
    	view.updateList();
    }

	public static void AddAdmin() {
	}
    
}
