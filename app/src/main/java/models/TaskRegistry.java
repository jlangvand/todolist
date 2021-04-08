package models;

import dao.PersistentRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Priority;
import utilities.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a register of tasks.
 */
public class TaskRegistry {
  private final List<Task> tasks;
  PersistentRegistry fileHandle;
  ObservableList<Task> observableTasks; //Used to display all tasks.

  /**
   * Create a new TaskRegistry with a custom save file.
   *
   * @param fileName file path to use for saving
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry(String fileName) throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    this.tasks = fileHandle.read();
    observableTasks = FXCollections.observableArrayList();
  }

  /**
   * Create a TaskRegistry using a default save file.
   *
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry() throws IOException {
    this.fileHandle = new PersistentRegistry();
    this.tasks = fileHandle.read();
    observableTasks = FXCollections.observableArrayList();
  }

  public PersistentRegistry getFileHandle() {
    return fileHandle;
  }

  /**
   * @return All tasks as an ArrayList
   */
  public List<Task> getTasks() {
    return tasks;

  }

  /**
   * @return The observableList corresponding to the tasks of this TaskRegister.
   */
  public ObservableList<Task> getObservableTasks() {
    return observableTasks;
  }

  /**
   * @param status enum Status
   * @return matching tasks as an ArrayList
   */
  public List<Task> getTasksByStatus(Status status) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStatus() == status) {
        foundTasks.add(task);
      }
    });
    return foundTasks;
  }

  /**
   * @param priority enum Priority
   * @return matching tasks as an ArrayList
   */
  public List<Task> getTasksByPriority(Priority priority) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority() == priority)
        foundTasks.add(task);
    });
    return foundTasks;
  }

  /**
   * Get tasks added within an inclusive range of dates.
   *
   * @param fromDate filter tasks added on of after this date
   * @param toDate filter tasks added on or before this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDateAdded(LocalDate fromDate, LocalDate toDate) {
    return tasks.stream().filter(t ->
        t.getDateAdded().isAfter(fromDate.minusDays(1))
            && t.getDateAdded().isBefore(toDate.plusDays(1)))
        .collect(Collectors.toList());
  }

  /**
   * Get tasks added on a given date.
   *
   * @param date filter tasks added on this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDateAdded(LocalDate date) {
    return getTasksByDateAdded(date, date);
  }

  /**
   * Get tasks with deadline within an inclusive range of dates.
   *
   * @param fromDate filter tasks with deadline on of after this date
   * @param toDate filter tasks with deadline on or before this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDeadline(LocalDate fromDate, LocalDate toDate) {
    return tasks.stream().filter(t ->
        t.getDeadline().isAfter(fromDate.minusDays(1))
            && t.getDeadline().isBefore(toDate.plusDays(1)))
        .collect(Collectors.toList());
  }

  /**
   * Get tasks with deadline on a given date.
   *
   * @param date filter tasks with deadline on this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDeadline(LocalDate date) {
    return getTasksByDeadline(date, date);
  }

  /**
   * @return List of high priority tasks
   */
  public List<Task> getHighPriorityTasks() {
    return getTasksByPriority(Priority.HIGH);
  }

  /**
   * @return List of medium priority tasks
   */
  public List<Task> getMediumPriorityTasks() {
    return getTasksByPriority(Priority.MEDIUM);
  }

  /**
   * @return List of low priority tasks
   */
  public List<Task> getLowPriorityTasks() {
    return getTasksByPriority(Priority.LOW);
  }

  /**
   * @return List of done tasks
   */
  public List<Task> getDoneTasks() {
    return getTasksByStatus(Status.DONE);
  }

  /**
   * @return List of active tasks
   */
  public List<Task> getActiveTasks() {
    return getTasksByStatus(Status.ACTIVE);
  }

  /**
   * @return String representation of TaskRegistry object.
   */
  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + tasks +
        '}';
  }

  /**
   * Adds a task to the registry, and saves the changes using fileHandler.
   * @param task the task to be added
   * @throws IOException throws exception if file IO fails
   */
  public void addTask(Task task) throws IOException {
    tasks.add(task);
    observableTasks.add(task);
    fileHandle.save(tasks);
  }

  /**
   * Removes a task from the registry, and saves the changes using fileHandler.
   * @param task the task to be removed
   * @throws IOException throws exception if file IO fails
   */
  public void removeTask(Task task) throws IOException {
    tasks.remove(task);
    observableTasks.remove(task);
    fileHandle.save(tasks);
  }
}
