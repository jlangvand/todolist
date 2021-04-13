package models;

import dao.PersistentRegistry;
import utilities.Priority;
import utilities.Status;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static utilities.Utilities.dateIsInRange;

/**
 * Persistent, ordered list of Task objects.
 *
 * <p>Provides methods for filtering and reordering tasks. Upon creation, it
 * loads tasks from a file if it exists, else it creates a new file. Tasks
 * are written to or removed from the file as they are added to or removed
 * from the list.
 */
public class TaskRegistry extends ArrayList<Task> implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
  private final transient PersistentRegistry fileHandle;

  /**
   * Create a new TaskRegistry with a custom save file.
   *
   * @param fileName file path to use for saving
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry(String fileName) throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    addAll(fileHandle.read());
  }

  /**
   * Create a TaskRegistry using a default save file.
   *
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry() throws IOException {
    this.fileHandle = new PersistentRegistry();
    addAll(fileHandle.read());
  }

  /**
   * Save the current list to file.
   *
   * @throws IOException if file IO fails
   */
  public void save() throws IOException {
    fileHandle.save(this);
  }

  /**
   * @param status enum Status
   * @return matching tasks as an ArrayList
   */
  public List<Task> getTasksByStatus(Status status) {
    return stream().filter(t -> t.getStatus().equals(status))
        .collect(Collectors.toList());
  }

  /**
   * @param priority enum Priority
   * @return matching tasks as an ArrayList
   */
  public List<Task> getTasksByPriority(Priority priority) {
    return stream().filter(t -> t.getPriority().equals(priority))
        .collect(Collectors.toList());
  }

  /**
   * Get tasks added within an inclusive range of dates.
   *
   * @param from filter tasks added on of after this date
   * @param to   filter tasks added on or before this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDateAdded(LocalDate from, LocalDate to) {
    return stream().filter(t -> dateIsInRange(t.getDateAdded(), from, to))
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
   * @param from filter tasks with deadline on of after this date
   * @param to   filter tasks with deadline on or before this date
   * @return a list of tasks matching the query
   */
  public List<Task> getTasksByDeadline(LocalDate from, LocalDate to) {
    return stream().filter(t -> dateIsInRange(t.getDeadline(), from, to))
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
        "tasks=" + this +
        '}';
  }

  /**
   * Adds a task to the registry, and saves the changes using fileHandler.
   *
   * @param task the task to be added
   * @throws IOException throws exception if file IO fails
   */
  public void addTask(Task task) throws IOException {
    add(0, task);
    save();
  }

  /**
   * Removes a task from the registry, and saves the changes using fileHandler.
   *
   * @param task the task to be removed
   * @throws IOException throws exception if file IO fails
   */
  public void removeTask(Task task) throws IOException {
    remove(task);
    save();
  }

  /**
   * Remove task by index, saves new list to file.
   *
   * @param index task to remove
   * @throws IOException if file IO fails
   */
  public void removeTask(int index) throws IOException {
    removeTask(get(index));
  }

  /**
   * Swaps the index of the task of index a with the task of index b.
   *
   * @param a Index of task A
   * @param b Index of task B
   * @throws IOException throws exception if file IO fails
   */
  public void swapTasksByIndex(int a, int b) throws IOException {
    Task taskA = get(a);
    Task taskB = get(b);
    set(a, taskB);
    set(b, taskA);
    save();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    TaskRegistry registry = (TaskRegistry) o;
    return fileHandle.equals(registry.fileHandle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fileHandle);
  }
}
