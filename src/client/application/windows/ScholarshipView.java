package client.application.windows;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import client.application.controls.JGpaTextField;
import client.graphics.SwingElementsCreator;
import service.domain.Faculty;
import service.domain.Scholarship;
import service.task.FacultyTask;
import service.task.ScholarshipTask;

/*
 * this is the main view for managing scholarships.  you can create, edit and delete scholarships here
 */
public class ScholarshipView extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private Scholarship scholarshipToLoad;
	private boolean isNew;

	private final JLabel nameLabel = new JLabel("Name");
	private final JTextField nameTextField = new JTextField();
	
	private final JLabel facultyLabel = new JLabel("Faculty");
	private final JComboBox<String> facultyComboBox = new JComboBox<String>();
	private final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

	private final JLabel dueDateLabel = new JLabel("Due Date");
	private final JXDatePicker picker = SwingElementsCreator.createDatePicker();

	private final JLabel numberAvailableLabel = new JLabel("Number Available");
	private final JTextField numberAvailableTextField = new JTextField();

	private final JButton saveButton = new JButton("Save");
	private final JButton deleteButton = new JButton("Delete");
	private final JButton cancelButton = new JButton("Cancel");
	
	private final JLabel lblMinimumGpa = new JLabel("Minimum GPA");
	private final JGpaTextField minimumGPATextField = new JGpaTextField();
	private final JLabel lblDescription = new JLabel("Description");
	private final JTextField descriptionTextField = new JTextField();

	private final JLabel amountAwardedLabel = new JLabel("Monetary Amount Awarded $");
	private final JTextField amountAwardedTextField = new JTextField();
	
	//constructor which takes in the Scholarship to work with
	public ScholarshipView(Scholarship scholarship, boolean isNew) {
		this.scholarshipToLoad = scholarship;
		this.isNew = isNew;
		setModal(true);
		initializeComponents();	
		loadValues();
	}

	//method to initialize the UI components
	private void initializeComponents() {
		amountAwardedTextField.setToolTipText("EXCLUDE $");
		amountAwardedTextField.setColumns(10);
		descriptionTextField.setColumns(10);
		
		minimumGPATextField.setMaxLength(5);
		minimumGPATextField.setPrecision(4);
		minimumGPATextField.setAllowNegative(false);
	
		setResizable(false);
		setSize(500, 400);
    	setLocationRelativeTo(null);
		setTitle(scholarshipToLoad.getName());

		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		getContentPane().add(nameLabel);
		getContentPane().add(nameTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));

		getContentPane().add(lblDescription);

		getContentPane().add(descriptionTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(facultyLabel);
		getContentPane().add(facultyComboBox);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(dueDateLabel);
		getContentPane().add(picker);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(numberAvailableLabel);
		getContentPane().add(numberAvailableTextField);

		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));

		getContentPane().add(lblMinimumGpa);
		getContentPane().add(minimumGPATextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(new JLabel(""));
		
		getContentPane().add(amountAwardedLabel);
		getContentPane().add(amountAwardedTextField);
		getContentPane().add(new JSeparator());
		getContentPane().add(new JSeparator());
		
		getContentPane().add(saveButton);
		getContentPane().add(deleteButton);
		getContentPane().add(cancelButton);

		nameTextField.setColumns(10);
		model.removeAllElements();
		for (Faculty fac : FacultyTask.repository.data)
			model.addElement(fac.getName());
		facultyComboBox.setModel(model);

		//when adding a new Scholarship update the list of faculties to choose from
		if (!isNew)
			setFaculty(scholarshipToLoad.getFaculty());


		//ability to add a new scholarship to the system provided all required fields have been provided.
		saveButton.addActionListener(e -> {
			try {
				//if add was selected add the new scholarship
				if (isNew) {
					addScholarship();					
				}
				else {
					//otherwise edit the existing scholarship
					editScholarship();
				}
				closeThisWindow();	
			}
			catch (java.lang.IllegalArgumentException e1) { 
				System.out.println(e1);
			}
		});

		//ability to remove a scholarship from the system.
		deleteButton.addActionListener(e -> {
			if (!isNew)
				ScholarshipTask.repository.delete(scholarshipToLoad);
			closeThisWindow();
		});
		
		cancelButton.addActionListener(e -> closeThisWindow());
	}
	
	//function to add a new scholarship to the system
	public void addScholarship() {
		//validate all required fields
		if (check((String) facultyComboBox.getSelectedItem(),Double.parseDouble(minimumGPATextField.getText()),
				Double.parseDouble(amountAwardedTextField.getText()),Integer.parseInt(numberAvailableTextField.getText()),
				nameTextField.getText()) == true) {			
		}
		else {
			//ensure they have chosen an expiry date for the scholarship
			if(picker.getDate() != null)
			{
				ScholarshipTask.repository.save(new Scholarship(
						nameTextField.getText(),
						Double.parseDouble(minimumGPATextField.getText()),
						FacultyTask.repository.findByName((String) facultyComboBox.getSelectedItem()),
						Double.parseDouble(amountAwardedTextField.getText()), descriptionTextField.getText(),
						Integer.parseInt(numberAvailableTextField.getText()), picker.getDate()));
				
				closeThisWindow();
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid Due Date", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//function to edit an existing scholarship
	public void editScholarship() {
		try 
		{
			//validate all required fields
			if(check((String) facultyComboBox.getSelectedItem(),Double.parseDouble(minimumGPATextField.getText()),
					Double.parseDouble(amountAwardedTextField.getText()),Integer.parseInt(numberAvailableTextField.getText()),
					nameTextField.getText()) == true ) {
				
			}
			else {
				//Apply the changes to each field.
				scholarshipToLoad.setName(nameTextField.getText());
				scholarshipToLoad.setDescription(descriptionTextField.getText());
				scholarshipToLoad.setFaculty(
						FacultyTask.repository.findByName((String) facultyComboBox.getSelectedItem()));
				scholarshipToLoad.setDueDate(picker.getDate());
				scholarshipToLoad.setMaxAwarded(Integer.parseInt(numberAvailableTextField.getText()));
				scholarshipToLoad.setMinimumGpa(Double.parseDouble(minimumGPATextField.getText()));
				scholarshipToLoad.setAmountAwarded(Double.parseDouble(amountAwardedTextField.getText()));
				
				closeThisWindow();
			} 
		}
		
		catch (NullPointerException e1) {} 
	}
	
	//function to populate the UI elements with the scholarship
	public void loadValues() {
		nameTextField.setText(scholarshipToLoad.getName());
		descriptionTextField.setText(scholarshipToLoad.getDescription());
		picker.setDate(scholarshipToLoad.getDueDate());
		numberAvailableTextField.setText(Integer.toString(scholarshipToLoad.getMaxAwarded()));
		minimumGPATextField.setText(String.valueOf(scholarshipToLoad.getMinimumGpa()));
		amountAwardedTextField.setText(Double.toString(scholarshipToLoad.getAmountAwarded()));
	}

	//finds the faculty set on the scholarship and 'selects' that on the UI dropdown
	public  void setFaculty(Faculty faculty) {
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) facultyComboBox.getModel();
		int i = 0;
		while (!model.getElementAt(i).equals(faculty.getName())) {
			i++;
		}
		
		facultyComboBox.setSelectedIndex(i);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void launch(Scholarship toLoad, boolean isNew) {
		ScholarshipView view = new ScholarshipView(toLoad, isNew);
		view.setVisible(true);
	}

	//function to close this window

	public void closeThisWindow() {		
		removeActionListeners(saveButton);
		removeActionListeners(deleteButton);
		removeActionListeners(cancelButton);
		
		this.setVisible(false);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	//remove action listeners from the JButtons
	public  void removeActionListeners(JButton button) {
		for (ActionListener al : button.getActionListeners()) {
			button.removeActionListener(al);
		}
	}

	//function to verify that all requires fields have been provided
	private boolean check (String fac, Double gpa, Double amount, Integer available, String name) {
		boolean flag = false;
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Need to provide Name of Scholarship", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			flag=true;
		} else if (gpa <= 0 || gpa > 4) {
			JOptionPane.showMessageDialog(null, "Invalid GPA", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			flag=true;
		}

		else if (amount <= 0) {
			JOptionPane.showMessageDialog(null, "Invalid Amount Awarded", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			flag=true;
		}

		else if (available <= 0) {
			JOptionPane.showMessageDialog(null, "Invalid Number of Available Scholarship", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			flag=true;
		}
		
		else if (picker.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Invalid Due Date", "Error - Invalid Input",
					JOptionPane.ERROR_MESSAGE);
			flag=true;
		}

		return flag;

	}
}


