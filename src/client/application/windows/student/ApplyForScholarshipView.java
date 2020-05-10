package client.application.windows.student;

import java.awt.Font;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import client.application.ApplicationContext;
import client.application.controls.JGpaTextField;
import client.application.windows.WindowHelper;
import service.domain.Scholarship;
import service.domain.Student;
import service.model.ScholarshipApplicationResult;
import service.task.ScholorshipApplicationTask;

/*
 * View that allows a student to apply for a scholarship
 */
public class ApplyForScholarshipView extends JDialog {

	private static final long serialVersionUID = 1L;
	private static ScholorshipApplicationTask applicationTask;

	private static JFrame view;
	
	private static Scholarship scholarship = null;
	private static JGpaTextField gpaEdit;
	private static final JButton applyButton = new JButton("Apply");
	private static final JButton cancelButton = new JButton("Cancel");
	
	//initialize the task (business logic)
	static {
		applicationTask = new ScholorshipApplicationTask();		
	}
	
	//ensure noone can create an instance of this on their own.
	private ApplyForScholarshipView( ) {	
	}
	
	//function used to initialize the UI
	private static void initialize() {		

		view = new JFrame();
		
		view.setBounds(100, 100, 400, 500);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setTitle("Apply for Scholarship");
		view.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Apply for:" + scholarship.getName());
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitle.setBounds(49, 20, 250, 32);
		view.getContentPane().add(lblTitle);
		
		JLabel lblGpa = new JLabel("Please enter in your current Gpa:");
		lblGpa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGpa.setBounds(49, 90, 250, 32);
		view.getContentPane().add(lblGpa);

		gpaEdit = new JGpaTextField();
		gpaEdit.setMaxLength(3);
		gpaEdit.setPrecision(4);
		gpaEdit.setAllowNegative(false);
		gpaEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		gpaEdit.setBounds(300, 90, 60, 32);
		view.getContentPane().add(gpaEdit);
		
		applyButton.setBounds(20, 160, 80, 32);
		applyButton.addActionListener(e -> { applyForScholarship(); });
		view.getContentPane().add(applyButton);

		cancelButton.setBounds(90, 160, 80, 32);
		cancelButton.addActionListener(e -> { closeThisWindow(); });
		view.getContentPane().add(cancelButton);

        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	//event listener method hooked to the 'apply' button 
	private static void applyForScholarship() {
		//ensure the requirements have been met.
		if (!canApply())
			return;
		double gpaVal = Double.valueOf(gpaEdit.getText());
		Student student = (Student)ApplicationContext.getInstance().getCurrentUser();
		
		//Attempt to apply
		ScholarshipApplicationResult result = applicationTask.apply(student,  gpaVal, scholarship);
			
		//if accepted report that to the user.
		if (result.isAccepted()) {			
			JOptionPane.showMessageDialog(null, String.format("You have applied for %s", scholarship.getName()),
						"Success", JOptionPane.OK_OPTION);			
		}
		else {
			//show the error why the application was rejected
			JOptionPane.showMessageDialog(null, String.format("Error: %s", result.getErrorMessage()),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		closeThisWindow();
	}

	//function to determine if the requirements for the scholarship application have been met.
	private static boolean canApply() {
		String gpa = gpaEdit.getText();
		if (gpa.isEmpty()) {
			showError("please enter your gpa");
			return false;
		}
		double gpaVal = Double.valueOf(gpa);
		if (gpaVal <= 0.0 || gpaVal > 4.0) {
			showError("invalid gpa.  must be between 0.01 & 4.00");
			return false;
		}
		
		return true;
	}
	
	//display the error to teh user.
	private static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error - Invalid Input", JOptionPane.ERROR_MESSAGE);
	}
	
	//close this view.
	public static void closeThisWindow() {
		WindowHelper.removeActionListeners(cancelButton);
		WindowHelper.removeActionListeners(applyButton);
		view.setVisible(false);
		view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
	}
	
	//entry point to create and launch this View
	public static void Launch(Scholarship award) {
		scholarship = award;
		initialize();
		view.setVisible(true);
	}
}
