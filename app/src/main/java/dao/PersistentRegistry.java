package dao;

import models.Task;
import models.TaskRegistry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistentRegistry {
  File file;
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

  public void saveData(String fileName, Task task) throws Exception {
      file=new File(fileName);
      try {
          FileOutputStream fileOut = new FileOutputStream(file);
          ObjectOutputStream out = new ObjectOutputStream(fileOut);
          out.writeObject(task);
          out.close();
          fileOut.close();
      }catch(IOException ex){
          ex.printStackTrace();
      }catch(Exception ex){
          ex.printStackTrace();
      }
  }

  public List<Task> loadDate(String fileName)  {
    List<Task> deserialized = new ArrayList<>();
    file=new File(fileName);
    try{
      FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in=new ObjectInputStream(fileIn);
      deserialized=(ArrayList<Task>) in.readObject();
    }catch (IOException ex){
      System.out.println("Error: "+ex.getMessage());
    } catch(ClassNotFoundException ex){
        System.out.println("Error: "+ex.getMessage());
    } catch(Exception ex){
        System.out.println("Error: " +ex.getMessage());
    }

    return  deserialized;

  }

}
