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

  public List<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {
    List<Task> foundTasksByDate = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getDateAdded().isAfter(fromDate) && task.getFinishedDate().isBefore(toDate))
        foundTasksByDate.add(task);
    });

    return foundTasksByDate;
  }

  public List<Task> getTasksByDateAdded(LocalDate from, LocalDate to) {
    return tasks.stream().filter(t ->
        t.getDateAdded().isAfter(from.minusDays(1))
            && t.getDateAdded().isBefore(to.plusDays(1)))
        .collect(Collectors.toList());
  }

  /*
   *
   * */
  public List<Task> getTasksByDate(LocalDate date) {
    List<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getDeadline().isEqual(date))
        foundTasks.add(task);
    });
    return foundTasks;
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
