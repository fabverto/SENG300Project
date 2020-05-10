package client.graphics;
import javax.swing.*;

/**
* A class representing the window in the gui game.
* @version 2.0
*/
public class Display
{
    //Instance Variables
    private JFrame frame;
    
    private String title;
    private int width;
    private int height;

    //Constructors
    /**
    * Constructor to create a graphical window for the game
    * @param title  The text on the top of the window
    * @param width  The windows width in pixels
    * @param height The windows height in pixels
    */
    public Display(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        createWindow();
    }

    //Getters Setters
    /**
    * @return   height  The windows height in pixels.
    */
    public int getHeight()
    {
        return height;
    }
    /**
    * @return   width   The windows width in pixels
    */
    public int getWidth()
    {
        return width;
    }
    /**
    * @return   frame   The window.
    */
    public JFrame getFrame()
    {
        return frame;
    }
 

    //Methods
    /**
    * Creates a window with a canvas object we can draw on.
    */
    private void createWindow()
    {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame = frame;
    }
}
