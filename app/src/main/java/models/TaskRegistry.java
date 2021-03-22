package models;

import javafx.collections.ObservableList;
import utilities.Priority;
import utilities.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRegistry {
  private List<Task> tasks;

  public TaskRegistry() {
    this.tasks = new ArrayList<Task>();
  }

  public ObservableList<Task> getTasks() {
    return null;
  }

  public ObservableList<Task> getTasksByStatus(Status status) {
    return null;
  }

  public ObservableList<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {
    return null;
  }

  public ObservableList<Task> getTasksByDate(LocalDate date) {
    return null;
  }

  public ObservableList<Task> getTasksByPriority(Priority priority) {
    return null;
  }

  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + tasks +
        '}';
  }

  void addTask(Task task) {
  }

  void removeTask(Task task) {
  }
}
