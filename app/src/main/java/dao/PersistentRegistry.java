package dao;

import models.Task;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 * Abstraction for file handling.
 *
 * <p>Provides methods for saving a list of tasks to, and reading it from, a
 * file in the file system.
 */
public class PersistentRegistry {
  private static final Logger LOGGER =
      Logger.getLogger(PersistentRegistry.class.getName());
  private File file;

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
    createOrOpenFile();
  }

  private void createOrOpenFile() throws IOException {
    if (file.createNewFile()) {
      LOGGER.log(INFO, () -> "Created new file " + file.getAbsolutePath());
      save(new ArrayList<>());
    } else {
      LOGGER.log(INFO, () -> "Opened file " + file.getAbsolutePath());
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
         ObjectInputStream ois = new ObjectInputStream(fis)) {
      return (ArrayList<Task>) ois.readObject();
    } catch (ClassNotFoundException | InvalidClassException
        | ClassCastException e) {
      LOGGER.log(SEVERE, () -> "File is corrupt! Deleting file.");
      Files.delete(Path.of(file.getAbsolutePath()));
      createOrOpenFile();
    } catch (EOFException e) {
      LOGGER.log(INFO, "File is empty, returning new ArrayList.");
    }
    return new ArrayList<>();
  }

  /**
   * Save list of tasks to file.
   *
   * @param tasks list of tasks
   * @throws IOException exception is thrown if, for some reason, file I/O fails
   */
  public void save(List<Task> tasks) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(tasks);
    }
  }
}
