package client.graphics;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jdesktop.swingx.JXDatePicker;

//Default objects builder
public class SwingElementsCreator 
{
	public static JButton createButton(String text, ActionListener a) 
    {
        JButton newButton = new JButton();
        newButton.setVisible(true);
        newButton.setText(text);
        newButton.addActionListener(a);
        return newButton;
    }
    
    public static JFrame createFrame(String title) 
    {
        Display newDisplay = new Display(title, 1, 1);
        newDisplay.getFrame().setLayout(new FlowLayout());
        return newDisplay.getFrame();
    }
    
    public static JXDatePicker createDatePicker() 
    {
    	JXDatePicker picker = new JXDatePicker();
    	Calendar calendar = picker.getMonthView().getCalendar();
    	calendar.setTime(new Date());
    	picker.getMonthView().setLowerBound(calendar.getTime());
    	return (picker);
    }
    
}
