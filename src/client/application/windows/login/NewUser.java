package client.application.windows.login;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import client.application.ApplicationContext;
import client.application.windows.WindowHelper;
import service.domain.User;
import service.task.AdminTask;
import service.task.StudentTask;
import service.task.UserTask;
import service.userLogin.Hashing;
import service.userLogin.Login;

import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

/* 
 * View for registering a new user.
 */
public class NewUser extends JFrame {
	private User userLoggedIn;
		
	private final JLabel nameLabel = new JLabel("Name");
	private final JTextField nameTextField = new JTextField();
	
	private final JLabel lblSchoolId = new JLabel("School ID");
	private final JTextField schoolIdTextField = new JTextField();
	
	private final JLabel emailLabel = new JLabel("Email Address");
	private final JTextField emailTextField = new JTextField();

	private final JLabel passwordLabel = new JLabel("Password");
	private final JPasswordField passwordField = new JPasswordField();
	
	private final JCheckBox adminCheck = new JCheckBox("Signup as Administrator");
	private final JCheckBox studentCheck = new JCheckBox("Signup as Student");
	
	private final JButton saveButton = new JButton("Save");
	private final JButton cancelButton = new JButton("Cancel");

	private UserTask userTask = new UserTask();
	private AdminTask adminTask = new AdminTask();
	private StudentTask studentTask = new StudentTask();

	public NewUser(User activeUser) {
		this.userLoggedIn = activeUser;
		initializeComponent();
	}

	private void initializeComponent() {
		nameTextField.setText("");
		schoolIdTextField.setText("");
		emailTextField.setToolTipText("Must contain \"@\"");
		emailTextField.setText("");
		
		loadValues();

		setResizable(false);
		setSize(500, 275);
		setLocationRelativeTo(null);
		setTitle("New User");

		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		getContentPane().add(nameLabel);
		getContentPane().add(nameTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));

		getContentPane().add(lblSchoolId);
		getContentPane().add(schoolIdTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(emailLabel);
		getContentPane().add(emailTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(passwordLabel);
		passwordField.setToolTipText("Must be longer than \r\n4 characters");
		getContentPane().add(passwordField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));

		studentCheck.setToolTipText("Register as a student");
		getContentPane().add(studentCheck);
		adminCheck.setToolTipText("You must be promoted by \r\nan existing Administrator");		
		adminCheck.setVisible(isCurrentUserAnAdmin());
		getContentPane().add(adminCheck);
		getContentPane().add(new JSeparator());
		getContentPane().add(new JSeparator());

		getContentPane().add(saveButton);
		getContentPane().add(cancelButton);
		
		adminCheck.addActionListener(e -> {
			if (adminCheck.isSelected())
				studentCheck.setSelected(false);
		});
		
		studentCheck.addActionListener(e -> {
			if (studentCheck.isSelected())
				adminCheck.setSelected(false);
		});

		cancelButton.addActionListener(e -> {
			closeThisWindow();
		});

		saveButton.addActionListener(e -> {
			
			List<String> response = new ArrayList<>();
			response.add(0, "1");
			response.add(1,"");
						
			String name = nameTextField.getText();
			String email = emailTextField.getText();
			String schoolId = schoolIdTextField.getText();
			String password = String.valueOf(passwordField.getPassword());
			String hashedPassword = Hashing.hash(password);
			boolean wantsAdmin = adminCheck.isSelected();
			boolean wantsStudent = studentCheck.isSelected();
					
			response = new Login().verify(email, password);
			System.out.print(response);								
					
			if (response.get(0) == "1")
				JOptionPane.showMessageDialog(null, response.get(1), "Error - Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			else {
				JOptionPane.showMessageDialog(null, "Sign Up Successful!", "Registration Complete",
						JOptionPane.INFORMATION_MESSAGE);

				if (wantsStudent)
					studentTask.create(name, schoolId, email, hashedPassword);
				else if (wantsAdmin)
					adminTask.create(name, schoolId, email, hashedPassword);				 
				else
					userTask.create(name, schoolId, email, hashedPassword);
				
				closeThisWindow();
			}
				
		});
	}
	
	private boolean isCurrentUserAnAdmin() {
		if (this.userLoggedIn == null)
			return false;
		return this.userLoggedIn.isAdmin();
	}

	//load values
	public  void loadValues() {
		setValues("", "", "", "");
	}

	//update the UI elements with the values provided.
	public void setValues(String name, String schoolID, String email, String password) {
		nameTextField.setText(name);
		schoolIdTextField.setText(schoolID);
		emailTextField.setText(email);
		passwordField.setText(password);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void launch() {		
		NewUser view = new NewUser(getCurrentUser());
		view.setVisible(true);
	}

	private static User getCurrentUser() {
		return ApplicationContext.getInstance().getCurrentUser();
	}

	//function to close this window
	public  void closeThisWindow() {
		WindowHelper.removeActionListeners(saveButton);
		WindowHelper.removeActionListeners(cancelButton);
		
		this.setVisible(false);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
