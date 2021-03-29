package dao;

import models.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PersistentRegistry {
  private final ArrayList<Task> tasks;
  private File file;

  public PersistentRegistry() throws IOException {
    this("default");
  }

  public PersistentRegistry(String fileName) throws IOException {
    this.file = new File(fileName);
    if (file.createNewFile()) {
      System.err.printf("Created file %s%n", file.getAbsolutePath());
      this.tasks = new ArrayList<>();
    } else {
      System.err.printf("Opened file %s%n", file.getAbsolutePath());
      this.tasks = (ArrayList<Task>) read();
    }
  }

  @SuppressWarnings("unchecked")
  public List<Task> read() throws IOException {
    try (FileInputStream fis = new FileInputStream(file.getAbsolutePath());
         ObjectInputStream ois = new ObjectInputStream(fis);) {
      return (ArrayList<Task>) ois.readObject();
    } catch (ClassNotFoundException e) {
      throw new IOException("File may be corrupt. Error: " + e.getMessage());
    }
  }

  public void save() throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
         ObjectOutputStream oos = new ObjectOutputStream(fos);) {
      oos.writeObject(this.tasks);
    }
  }

  /*
   * It is just for helping to write the functions in taskRegistry class
   * We can delete or changing after that.
   * */

  public void saveData(String fileName, Task task) throws Exception {
    file = new File(fileName);
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
