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
  private final File file;

  public PersistentRegistry() throws IOException {
    this("default");
  }

  public PersistentRegistry(String fileName) throws IOException {
    this.file = new File(fileName);
    if (file.createNewFile()) {
      System.err.printf("Created file %s%n", file.getAbsolutePath());
    } else {
      System.err.printf("Opened file %s%n", file.getAbsolutePath());
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

  /**
   *
   * @throws IOException
   */
  public void save(ArrayList<Task> tasks) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
         ObjectOutputStream oos = new ObjectOutputStream(fos);) {
      oos.writeObject(tasks);
    }
  }
}
