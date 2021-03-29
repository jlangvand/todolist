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

import static javafx.collections.FXCollections.observableArrayList;

public class TaskRegistry {
  private List<Task> tasks;
  PersistentRegistry fileHandle;
  private final String fileName = "TasksData.obj";

  /*
   *
   *
   * */
  public TaskRegistry() throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    this.tasks = fileHandle.read();
  }

  /*
   *
   * */
  public ObservableList<Task> getTasks() {
    return observableArrayList(tasks);
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getTasksByStatus(Status status) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStatus().equals(status)) {
        foundTasks.add(task);
      }
    });
    return FXCollections.observableArrayList(foundTasks);
  }

  /**
   * @param priority
   * @return
   */
  public ObservableList<Task> getTasksByPriority(Priority priority) {
    ArrayList<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals(priority)) {
        foundTasks.add(task);
      }
    });
    return FXCollections.observableArrayList(foundTasks);
  }

  public ObservableList<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {
    List<Task> foundTasksByDate = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStartedDate().isAfter(fromDate) && task.getFinishedDate().isBefore(toDate))
        foundTasksByDate.add(task);
    });
    ObservableList<Task> allFoundedTasks = FXCollections.observableArrayList(foundTasksByDate);
    return allFoundedTasks;
  }

  /*
   *
   * */
  public ObservableList<Task> getTasksByDate(LocalDate date) {
    List<Task> foundTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getDeadline().equals(date))
        foundTasks.add(task);
    });
    return FXCollections.observableArrayList(foundTasks);
  }

  /*
   *
   * */
  public ObservableList<Task> getHighPriorityTasks() {
    return getTasksByPriority(Priority.HIGH);
  }

  /*
   *
   *
   *
   * */
  public ObservableList<Task> getMediumPriorityTasks() {
    return getTasksByPriority(Priority.MEDIUM);
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getLowPriorityTasks() {
    return getTasksByPriority(Priority.LOW);
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getDoneTasks() {
    return getTasksByStatus(Status.DONE);
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getActiveTasks() {
    return getTasksByStatus(Status.ACTIVE);
  }

  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + tasks +
        '}';
  }

  void addTask(Task task) {
    tasks.add(task);
  }

  void removeTask(Task task) {
    tasks.remove(task);
  }
}
