package client.application.windows.login;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.JTextField;

import client.application.ApplicationContext;
import client.application.Main;
import client.application.windows.WindowHelper;
import service.domain.User;
import service.task.UserTask;
import service.userLogin.Hashing;

import javax.swing.JButton;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

/*
 * LoginUI view.  this if the first page brought up so a user can register or sign in
 */
@SuppressWarnings("serial")
public class LoginUI extends JFrame{

	private final JButton btnLogin = new JButton("Sign In");
	private final JButton btnSignup = new JButton("New User");
	
	private JTextField username;
	private JPasswordField password;
	
	private static boolean loginSuccess;
	private static UserTask userTask = new UserTask();

	public LoginUI() {
		initializeComponent();
	}
	
	public void initializeComponent() {
		getContentPane().setBackground(SystemColor.menu);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Scholarship Application Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		getContentPane().setLayout(null);
				
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEmail.setBounds(49, 54, 104, 32);
		getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(49, 127, 89, 16);
		getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(175, 60, 156, 22);
		getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(175, 125, 156, 22);
		getContentPane().add(password);
		password.setColumns(10);
				
		//add a listener to the add button
		btnLogin.addActionListener(e -> {
				
				String user = username.getText();
				String pass = String.copyValueOf(password.getPassword());
				
				try {
					setValues(user, pass);
					
					if(user.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "must provide username", "Error - Invalid Input",
								JOptionPane.ERROR_MESSAGE);
					}
					else if (pass.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Must provide password", "Error - Invalid Input",
								JOptionPane.ERROR_MESSAGE);	
					}
					else
					{
						
						User loggedIn = userTask.login(user, Hashing.hash(pass));
						ApplicationContext.getInstance().setCurrentUser(loggedIn);
						
						if (loggedIn != null) {
							loginSuccess = true;
							System.out.println("Username is : " + user);
							System.out.println("Password is : " + pass);
							System.out.println("Was login Successful? : " + loginSuccess);
						}
						
						if (!loginSuccess)
							JOptionPane.showMessageDialog(null, "Login credentials were incorrect", "Error - Invalid Input",
									JOptionPane.ERROR_MESSAGE);
						else {
							Main.startMainApplication();
							closeThisWindow();						
						}

					}
										
				} catch (Exception e1) {
					System.out.println("Exception was caught in LoginUI login function");
					e1.printStackTrace();
				}	
			});

		btnLogin.setBounds(200, 180, 100, 38);
		getContentPane().add(btnLogin);
		
		JLabel lblAuthenticationService = new JLabel("Authentication Service");
		lblAuthenticationService.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblAuthenticationService.setBounds(49, 13, 311, 16);
		getContentPane().add(lblAuthenticationService);

		btnSignup.addActionListener(e -> {
			NewUser.launch();
		});			
		btnSignup.setBounds(49, 180, 100, 38);
		getContentPane().add(btnSignup);			
	}
	
	//Updates the UI with the values passed in
	public void setValues(String u, String p) {
		username.setText(u);
		password.setText(p);
	}	
	
	public void closeThisWindow() {
		WindowHelper.removeActionListeners(btnLogin);
		WindowHelper.removeActionListeners(btnSignup);
		setVisible(false);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSED));
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public static void launch() 
	{
		LoginUI view = new LoginUI();	
		view.setVisible(true);
	}
	
	//function to return if the user succesfully logged on.
	public static boolean isLoginSuccess() {
		return loginSuccess;
	}
}
