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

/**
 * Abstraction for file handling.
 *
 * <p>Provides methods for saving a list of tasks to, and reading it from, a
 * file in the file system.
 */
public class PersistentRegistry {
  private final File file;

  /**
   * Create an instance using a default file name.
   *
   * @throws IOException exception is thrown if, for some reason, file I/O fails
   */
  public PersistentRegistry() throws IOException {
    this("default");
  }

  /**
   * Create an instance provided a file name.
   *
   * <p>Creates a new file if the file does not exist.
   *
   * @param fileName file name/path
   * @throws IOException exception is thrown if, for some reason, file I/O fails
   */
  public PersistentRegistry(String fileName) throws IOException {
    this.file = new File(fileName);
    if (file.createNewFile()) {
      System.err.printf("Created file %s%n", file.getAbsolutePath());
    } else {
      System.err.printf("Opened file %s%n", file.getAbsolutePath());
    }
  }

  /**
   * Get tasks stored in file.
   *
   * @return list of tasks as an ArrayList
   * @throws IOException exception is thrown if, for some reason, file I/O fails
   */
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
   * Save list of tasks to file.
   *
   * @param tasks list of tasks
   * @throws IOException exception is thrown if, for some reason, file I/O fails
   */
  public void save(List<Task> tasks) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
         ObjectOutputStream oos = new ObjectOutputStream(fos);) {
      oos.writeObject(tasks);
    }
  }
}
