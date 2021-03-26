package dao;

import models.Task;
import models.TaskRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistentRegistry {
 // Task task=new Task();
  public PersistentRegistry(String fileName) {
  }

  public PersistentRegistry() {

  }

  public TaskRegistry read() {
    return null;
  }

  public void save() {
  }
  /*
  * It is just for helping to write the functions in taskRegistry class
  * We can delete or changing after that.
  * */

  public void saveData(String fileName, Task task) throws IOException {
    FileOutputStream fileOut=new FileOutputStream("fileName");
    ObjectOutputStream out=new ObjectOutputStream(fileOut);
    out.writeObject(task);
    out.close();
    fileOut.close();
  }

  public List<Task> loadDate(String fileName)  {
    List<Task> deserialized = new ArrayList<>();
    try{
      FileInputStream fileIn = new FileInputStream("fileName");
      ObjectInputStream in=new ObjectInputStream(fileIn);
      deserialized=(ArrayList<Task>) in.readObject();
    }catch (IOException ex){
      System.out.println("Error: "+ex.getMessage());
    } catch(Exception ex){
      ex.getMessage();
    }

    return  deserialized;

  }

}
