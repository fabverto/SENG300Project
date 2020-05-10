package client.application.windows.admin;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import client.application.windows.WindowHelper;
import service.domain.Scholarship;
import service.domain.Student;
import service.model.ApproveStudentResult;
import service.task.ScholarshipTask;

public class ApproveStudentView extends JDialog  {

	private static final long serialVersionUID = 1L;
	private static ScholarshipTask scholarshipTask;
		
	private final JPanel scholarshipPanel = new JPanel();
	
	private final JPanel taskPanel = new JPanel();
    
    private final JPanel studentListPanel = new JPanel();
    final JList<Student> list = new JList<Student>();
    private final DefaultListModel<Student> model = new DefaultListModel<>();
    protected final JScrollPane scrollPane = new JScrollPane();
    
    private final JButton btnApprove = new JButton("Approve");
    private final JButton btnClose = new JButton("Close");
	
	static {
		scholarshipTask = new ScholarshipTask();
	}
	
	private final Scholarship scholarship;
	
	public ApproveStudentView(Scholarship scholarship) {
		this.scholarship = scholarship;
		setModal(true);
		initializeComponents();
	}
	
	public void initializeComponents() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		setResizable(true);
		setSize(600,400);
		setLocationRelativeTo(null);
		setTitle("Scholarship Applicant Approvals");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		getContentPane().add(scholarshipPanel);		
		scholarshipPanel.add(new JLabel("Scholarship:  "));
		scholarshipPanel.add(new JLabel(this.scholarship.getName()));

        scholarshipPanel.add(new JLabel(" :  "));
        scholarshipPanel.add(new JLabel("Description:  "));
        scholarshipPanel.add(new JLabel(this.scholarship.getDescription()));

        scholarshipPanel.add(new JLabel(" :  "));
        scholarshipPanel.add(new JLabel("Gpa:  "));
        scholarshipPanel.add(new JLabel(Double.toString(this.scholarship.getMinimumGpa())));

        getContentPane().add(studentListPanel);
        studentListPanel.setLayout(new GridLayout(0, 1, 0, 0)); //0100
        studentListPanel.add(scrollPane);
        scrollPane.setViewportView(list);
          
        list.setModel(getApplicants());
          
        getContentPane().add(taskPanel);
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.X_AXIS));
          
        btnApprove.addActionListener(e -> approveStudent(list.getSelectedValue()));
        taskPanel.add(btnApprove);
        btnClose.addActionListener((e) -> closeThisWindow());
        taskPanel.add(btnClose);   
	}
	
	private void approveStudent(Student toApprove) {		
		if (toApprove == null) 
			return;
		ApproveStudentResult result = scholarshipTask.approve(scholarship, toApprove);
		if (result.isAccepted()) {
			JOptionPane.showMessageDialog(null, "Scholarship Awarded", "Awarded", JOptionPane.OK_OPTION);
			list.setModel(getApplicants());
		}
		else {
			JOptionPane.showMessageDialog(null ,result.getErrorMessage(), "Error", 
					JOptionPane.ERROR_MESSAGE);			
		}		
	}

	private ListModel<Student> getApplicants() {
		model.clear();
		
		for(Student each : this.scholarship.getApplicants()) {			
			model.addElement(each);
		}
		return model;
	}

	//close this view.
	public void closeThisWindow() {
		WindowHelper.removeActionListeners(btnClose);
		setVisible(false);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	public static void launch(Scholarship scholarship) {
		ApproveStudentView view = new ApproveStudentView(scholarship);
		view.setVisible(true);			
	}

}
