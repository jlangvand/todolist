package models;

import dao.PersistentRegistry;
import utilities.Priority;
import utilities.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRegistry {
  private List<Task> tasks;
  PersistentRegistry fileHandle;
  private ArrayList<Task> taskList;
  private String fileName;


  /*
   *
   *
   * */
  public TaskRegistry(String fileName) throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    this.tasks = fileHandle.read();
    this.fileName = fileName;
  }

  /*
   *
   * */
  public List<Task> getTasks() {
    return tasks;

  }

  /*
   *
   *
   * */
  public List<Task> getTasksByStatus(Status status) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStatus().equals(status)) {
        foundTasks.add(task);
      }
    });
    return foundTasks;
  }

  /**
   * @param priority
   * @return
   */
  public List<Task> getTasksByPriority(Priority priority) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals(priority)) {
        foundTasks.add(task);
      }
    });
    return foundTasks;
  }

  public List<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {
    List<Task> foundTasksByDate = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStartedDate().isAfter(fromDate) && task.getFinishedDate().isBefore(toDate))
        foundTasksByDate.add(task);
    });

    return foundTasksByDate;
  }

  /*
   *
   * */
  public List<Task> getTasksByDate(LocalDate date) {
    List<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getDeadline().equals(date))
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
