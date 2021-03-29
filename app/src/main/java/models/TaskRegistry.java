package models;

import dao.PersistentRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

  public ObservableList<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {

    tasks=fileHandle.loadDate(fileName);
    List<Task>foundTasksByDate=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getStartedDate().isAfter(fromDate)&&task.getFinishedDate().isBefore(toDate))
        foundTasksByDate.add(task);
    });
    ObservableList<Task> allFoundedTasks=FXCollections.observableArrayList(foundTasksByDate);
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
    ArrayList<Task> highPriorityTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals("HIGH"))
        highPriorityTasks.add(task);
    });
    return FXCollections.observableArrayList(highPriorityTasks);
  }

  /*
   *
   *
   *
   * */
  public ObservableList<Task> getMediumPriorityTasks() {
    ArrayList<Task> mediumPriorityTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals("MEDIUM"))
        mediumPriorityTasks.add(task);
    });
    ObservableList<Task> mediumPriorities = FXCollections.observableArrayList(mediumPriorityTasks);
    return mediumPriorities;
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getLowPriorityTasks() {
    ArrayList<Task> lowPriorityTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals("LOW"))
        lowPriorityTasks.add(task);
    });
    ObservableList<Task> lowPriorities = FXCollections.observableArrayList(lowPriorityTasks);
    return lowPriorities;
  }
  /*public ObservableList<Task> getTodayTasks(LocalDate today){
    tasks=fileHandle.loadDate(fileName);
    tasks.forEach(task->{

    });

  }*/

  /*
   *
   *
   * */
  public ObservableList<Task> getDoneTasks() {
    ArrayList<Task> doneTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getPriority().equals("DONE")) {
        doneTasks.add(task);
      }
    });
    return FXCollections.observableArrayList(doneTasks);
  }

  /*
   *
   *
   * */
  public ObservableList<Task> getActiveTasks() {
    ArrayList<Task> activeTasks = new ArrayList<>();
    tasks.forEach(task -> {
      if (task.getStatus().equals("ACTIVE"))
        activeTasks.add(task);
    });
    return FXCollections.observableArrayList(activeTasks);
  }

  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + tasks +
        '}';
  }

  void addTask(Task task) throws Exception {
    if(task.getTitle()!=null){
      fileHandle.saveData(fileName,task);
    }
  }

  void removeTask(Task task) {
    Iterator itr = tasks.iterator();
    while (itr.hasNext()) {
      Task element = (Task) itr.next();
      if (element.getTitle().equalsIgnoreCase(task.getTitle()) && !(element.getTitle().equals(null))) {
        itr.remove();
        break;
      }
    }
  }
}
