package client.application;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.application.windows.ScholarshipListView;
import client.application.windows.login.LoginUI;
import service.domain.Faculty;
import service.domain.Student;
import service.task.FacultyTask;
import service.task.ScholarshipTask;
import service.task.UserTask;
import tools.*;

//this is the main entry point for the application. 
public class Main 
{	
	//where we start.
	public static void main(String[] args)
	{		
		setup();
		LoginUI.launch();
	}
	
	//This launches the ScholarsipListView page
    public static void startMainApplication() 
    {   	    
		ScholarshipListView.launch();
    }

    //This method is the start of the application.
    //1) reads in the data files to repopulate the in-memory repositories.
    //2) sets up the function to save the in-memory repositories to the file system
    public static void setup() 
    {
    	loadData();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
        {
        	//Save data when exiting application
            public void run() 
            {
            	saveRepositoryState(FacultyTask.repository.findAll(), "ProjectData", "faculties");
            	saveRepositoryState(ScholarshipTask.repository.findAll(), "ProjectData", "scholarships");
            	saveRepositoryState(UserTask.repository.findAll(), "ProjectData", "users");
                saveRepositoryState(FacultyTask.repository.findAll(), "ProjectData", "faculties");
            }
        }));
    }
   
    //This function will save the data from a given repository to the file system
    public static <T> void saveRepositoryState(List<T> data, String folder, String file ) {
    	FileManager.save((Serializable)data, folder, file);
    }
    
    //This function will load all the data from the file system into the in-memory repositories
    public static void loadData()
    {    	
    	FacultyTask.repository.data.addAll(loadData("ProjectData/faculties.ser"));
    	addDefaultFaculties(FacultyTask.repository.data);
    	
    	ScholarshipTask scholarshipTask = new ScholarshipTask();
    	scholarshipTask.load(loadData("ProjectData/scholarships.ser"));
    	
    	UserTask userTask = new UserTask();
    	userTask.load(loadData("ProjectData/users.ser"));
    	
    	List<Student> registeredStudents = ScholarshipTask.repository.findAllStudentsRegistered();
    	UserTask.repository.reconcileStudents(registeredStudents);
    	        
    }

    private static void addDefaultFaculties(ArrayList<Faculty> data) {
    	if (!data.isEmpty())
    		return;
    	
		data.add(new Faculty("Arts"));
		data.add(new Faculty("Science"));
		data.add(new Faculty("Medicine"));
		data.add(new Faculty("Business"));
		data.add(new Faculty("Engineering"));
		data.add(new Faculty("Kinesiology"));
		
	}

	//This function will load a single file and cast it to the type the repository is needing
	@SuppressWarnings("unchecked")
	private static <T> List<T> loadData(String dataFile) {
		if (FileManager.checkIfFileExists(dataFile))
		{
			return (ArrayList<T>) FileManager.load(dataFile);
		}
		System.out.println("There is no scholarships to be loaded!");
		return Collections.emptyList(); 
	} 
        
}
