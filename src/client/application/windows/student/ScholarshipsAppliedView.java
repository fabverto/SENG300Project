package client.application.windows.student;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.application.ApplicationContext;
import client.application.windows.WindowHelper;
import service.domain.Scholarship;
import service.domain.Student;
import service.task.ScholarshipTask;

/*
 * View page to visualIze all the scholarship a student has applied to
 * THIS WILL BE DONE IN A FUTURE ITERATION 
 */
public class ScholarshipsAppliedView extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JPanel scholarshipPanel = new JPanel();
	private final JList<Scholarship> list = new JList<Scholarship>();
	private final DefaultListModel<Scholarship> model = new DefaultListModel<>();
    private final JPanel scholarshipListPanel = new JPanel();
    protected static final JScrollPane scrollPane = new JScrollPane();
    
	private final JButton cancelButton = new JButton("Cancel");
	
	private ScholarshipTask scholarshipTask;
	
	public ScholarshipsAppliedView() {
		scholarshipTask = new ScholarshipTask();
		fetchData(ApplicationContext.getInstance().getCurrentStudent());
		initializeComponent();
	}
	
	private void fetchData(Student student) {
		int i = 0;
		for(Scholarship each :  scholarshipTask.findAppliedForStudent(student)) {
			model.add(i++, each);
		}
	}

	private void initializeComponent() {
		setBounds(20, 20, 800, 600);
		setTitle("Scholarshipws I Applied for:");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(model); 
        scrollPane.setViewportView(list);
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        getContentPane().add(scholarshipPanel);
        scholarshipPanel.setLayout(new GridLayout(1, 1, 0, 0));
        scholarshipPanel.add(scholarshipListPanel);
        
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(model);        
        scrollPane.setViewportView(list);
        
        scholarshipListPanel.setLayout(new GridLayout(0, 1, 0, 0)); //0100
        scholarshipListPanel.add(scrollPane);
		
		cancelButton.setBounds(90, 160, 80, 32);
		cancelButton.addActionListener(e -> { closeThisWindow(); });
		getContentPane().add(cancelButton);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
	}
	
	//close this view.
	public void closeThisWindow() {
		WindowHelper.removeActionListeners(cancelButton);
		this.setVisible(false);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}	
	
	//function to launch the page for the user.
	public static void launch() {
		ScholarshipsAppliedView view = new ScholarshipsAppliedView();
		view.setVisible(true);
		view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
	}
}
