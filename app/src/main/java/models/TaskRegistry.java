package models;

import dao.PersistentRegistry;
import utilities.Priority;
import utilities.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRegistry {
  private final List<Task> tasks;
  PersistentRegistry fileHandle;

  /**
   * Create a new TaskRegistry with a custom save file.
   *
   * @param fileName file path to use for saving
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry(String fileName) throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    this.tasks = fileHandle.read();
  }

  /**
   * Create a TaskRegistry using a default save file.
   *
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry() throws IOException {
    this.fileHandle = new PersistentRegistry();
    this.tasks = fileHandle.read();
  }

  /**
   * Get all tasks.
   *
   * @return all tasks as an ArrayList
   */
  public List<Task> getTasks() {
    return tasks;

  }

  /**
   * Get tasks by status.
   *
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
   * Get tasks by priority.
   *
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

  /*
   *
   * */
  public List<Task> getHighPriorityTasks() {
    return getTasksByPriority(Priority.HIGH);
  }

  /*
   *
   *
   *
   * */
  public List<Task> getMediumPriorityTasks() {
    return getTasksByPriority(Priority.MEDIUM);
  }

  /*
   *
   *
   * */
  public List<Task> getLowPriorityTasks() {
    return getTasksByPriority(Priority.LOW);
  }

  /*
   *
   *
   * */
  public List<Task> getDoneTasks() {
    return getTasksByStatus(Status.DONE);
  }

  /*
   *
   *
   * */
  public List<Task> getActiveTasks() {
    return getTasksByStatus(Status.ACTIVE);
  }

  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + tasks +
        '}';
  }

  void addTask(Task task) throws IOException {
    tasks.add(task);
    fileHandle.save(tasks);
  }

  void removeTask(Task task) throws IOException {
    tasks.remove(task);
    fileHandle.save(tasks);
  }
}
