package client.application.windows;

import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.application.ApplicationContext;
import client.application.windows.admin.AdminView;
import client.application.windows.login.LoginUI;
import client.application.windows.login.NewUser;
import client.application.windows.student.StudentView;

import javax.swing.BoxLayout;
import service.domain.Scholarship;
import service.domain.User;
import service.task.ScholarshipTask;

/*
 * This is the main Scholarship list page.  All scholarships are visible here and here is where you can add, edit or delete scholarships;
 * 
 * Some function here will only be available depending on what type of user has logged on (Admin, Student, User).
 * 
 */
public class ScholarshipListView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final JPanel scholarshipPanel = new JPanel();
    final JPanel taskPanel = new JPanel();
    private final JPanel scholarshipListPanel = new JPanel();
    
    //Admin buttons
    final JButton btnAddScholarship = new JButton("Add Scholarship");
    final JButton btnApproveScholarship = new JButton("Approve Scholarship");
    final JButton btnEditScholarship = new JButton("Edit Scholarship");
    final JButton btnCreateAdminUser = new JButton("Create Admin");
    final JButton btnErase = new JButton("Clear Scholarships");
    final JButton btnAddAdmin = new JButton("Add a new Administrator");
   
    final JButton btnExitApp = new JButton("Log out");
    
    //Student buttons
    final JButton btnApplyToScholarship = new JButton("Apply Scholarship");
    final JButton btnShowScholarshipsApplied = new JButton("Show Applied");
    
    final JList<Scholarship> list = new JList<Scholarship>();
    private final DefaultListModel<Scholarship> model = new DefaultListModel<>();
    protected final JScrollPane scrollPane = new JScrollPane();
   
    private static Scholarship selectedScholarship = null;
    
    public ScholarshipListView() {
    	initializeComponent();
    }
    
    private void initializeComponent() {
    	getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
    	setResizable(true);
    	setSize(800, 600);
    	setLocationRelativeTo(null);
       	setTitle("Scholarship Creation");

       	///Administrator Tasks``
       	btnAddScholarship.addActionListener(e -> {
       		ScholarshipView.launch(Scholarship.makeOne(), true);
       		updateList();
       	});
       	
       	btnEditScholarship.addActionListener(e -> {
       		AdminView.EditScholarship(list.getSelectedValue());
       		updateList();
       	});
          
       	btnApproveScholarship.addActionListener(e -> {
       		AdminView.ApproveApplicants(list.getSelectedValue());
       		updateList();
       	});      
       	
       	btnCreateAdminUser.addActionListener(e -> {
       		NewUser.launch();
       	});
          
        btnErase.addActionListener(e -> {
        	int dialogButton = JOptionPane.YES_NO_OPTION;
        	int response = JOptionPane.showConfirmDialog (null, "Are you sure?","Remove Scholarships",dialogButton);
        	
        	if (response == JOptionPane.YES_OPTION) {
        		AdminView.ClearScholarships(this);
        		}
        });
          
        btnAddAdmin.addActionListener(e -> { AdminView.AddAdmin(); });
          
        //Student Tasks
        btnApplyToScholarship.addActionListener(e -> {
        	if (selectedScholarship == null)
        		return; //no one has selected a scholarship yet.
        	StudentView.launchApplyScholarshipView(selectedScholarship);
        });
          
        btnApplyToScholarship.setEnabled(false);
        btnShowScholarshipsApplied.addActionListener(e -> { StudentView.launchAppliedScholarshipsView(); });
          
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        getContentPane().add(scholarshipPanel);
          scholarshipPanel.setLayout(new GridLayout(1, 1, 0, 0));
          scholarshipPanel.add(scholarshipListPanel);
          
          list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          list.setModel(model);        
          list.addListSelectionListener(e -> {
          	selectedScholarship = list.getSelectedValue();
          	btnApplyToScholarship.setEnabled(true);
          });
          
          scrollPane.setViewportView(list);
          
          scholarshipListPanel.setLayout(new GridLayout(0, 1, 0, 0)); //0100
          scholarshipListPanel.add(scrollPane);
          getContentPane().add(taskPanel);
          taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
          
          //These are the administrator functions available only to Administrators
          if (getCurrentUser().isAdmin()) {
        	  taskPanel.add(btnAddScholarship);
        	  taskPanel.add(btnEditScholarship);
        	  taskPanel.add(btnApproveScholarship);        
        	  taskPanel.add(btnErase); 
        	  taskPanel.add(new JLabel(""));
        	  taskPanel.add(btnCreateAdminUser);
          }       	    

          //Students have limited features here.  they can apply to a scholarship or view the ones they have applied to
          if (getCurrentUser().isStudent()) {
        	  taskPanel.add(btnApplyToScholarship);
        	  taskPanel.add(btnShowScholarshipsApplied);
          }
      	
          btnExitApp.addActionListener((e) -> logOut());
          taskPanel.add(new JLabel(""));
          taskPanel.add(btnExitApp);     
          setVisible(true);

          //fetch all the scholarships from the system and display them
          updateList();
    }
    
    /**
     * @wbp.parser.entryPoint
     */
    // This function build the View page and wires up all the UI components
    public static void launch() 
    {
    	ScholarshipListView view = new ScholarshipListView();
    	view.setVisible(true);
    }
    
    private void logOut() {
    	ApplicationContext.getInstance().setCurrentUser(null);
    	setVisible(false);
    	LoginUI.launch();
    }
   
    //This function will update the UI with the Scholarships in the repository
    public void updateList() 
    {       
        model.clear();
        for(int i = ScholarshipTask.repository.data.size() - 1; i >= 0; i--) 
        {
              model.addElement(ScholarshipTask.repository.data.get(i));
        }
    }
   
    //this is a convenience method to get the user who is currently logged on.
	public static User getCurrentUser() {
		return ApplicationContext.getInstance().getCurrentUser();
	}

	public DefaultListModel<Scholarship> getModel() {
		return model;
	}
}
