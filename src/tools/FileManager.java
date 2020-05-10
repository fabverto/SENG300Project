package tools;

import java.io.*;

//Save and load data from .ser's
public class FileManager 
{
    public static boolean save(java.io.Serializable thingToSave, String locationFromProjectFolder, String fileName) 
    {
        try 
        {
            String outputLocation = locationFromProjectFolder + "/" + fileName + ".ser";
            FileOutputStream fileOut = new FileOutputStream(outputLocation);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(thingToSave);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + outputLocation);
            return true;
        } 
        catch (IOException i)
        {
            i.printStackTrace();
            return false;
        }
    }
    
    public static Object load(String fileToLoad) 
    {
//        Object o = null;
        try 
        {
           FileInputStream fileIn = new FileInputStream(fileToLoad);
           ObjectInputStream in = new ObjectInputStream(fileIn);
           Object o = in.readObject();
           in.close();
           fileIn.close();
           return o;
        } 
		catch (IOException i) {
//			System.out.println("shit: " + i.getMessage() + ": endshit");
			i.printStackTrace();
//			System.exit(-1);
			return i;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return c;
        }
    }
    
    public static boolean checkIfFileExists(String pathAndNameFromProjectFolder) 
    {
        return new File(pathAndNameFromProjectFolder).exists();
    }
    
    public static boolean createFile(String pathAndNameFromProjectFolder) 
    {
        PrintWriter writer;
        try 
        {
            writer = new PrintWriter(pathAndNameFromProjectFolder, "UTF-8");
            writer.close();
            return true;
        } 
        catch (FileNotFoundException e) { e.printStackTrace(); return false;}
        catch (UnsupportedEncodingException e) {e.printStackTrace(); return false;}
    }
}
