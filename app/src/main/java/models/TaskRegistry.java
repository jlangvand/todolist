package models;

import dao.PersistentRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Priority;
import utilities.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class TaskRegistry {
  private List<Task> tasks;
  PersistentRegistry fileHandle;
  private final String fileName="TasksData.dat";
  private Status status;
  private Priority priority;
/*
*
*
* */
  public TaskRegistry() {
    this.tasks = new ArrayList<Task>();
    fileHandle=new PersistentRegistry();
  }
/*
*
* */
  public ObservableList<Task> getTasks() {
    //Object tasks=new ArrayList<>();
    tasks=fileHandle.loadDate(fileName);
    ObservableList<Task> allTasks= observableArrayList(tasks);
    return allTasks;
  }
  /*
  *
  *
  * */

  public ObservableList<Task> getTasksByStatus(String status) {
    ArrayList<Task> foundTaskByStatus = new ArrayList<>();
    tasks =  fileHandle.loadDate(fileName);
    for ( Task task :  tasks)
      if (task.getStatus().equals(status)) {
        foundTaskByStatus.add(task);
      }
    ObservableList<Task>AllFoundTasks= FXCollections.observableArrayList(foundTaskByStatus);
    return AllFoundTasks;
  }

  public ObservableList<Task> getTasksByDate(LocalDate fromDate, LocalDate toDate) {
    return null;
  }
/*
*
* */
  public ObservableList<Task> getTasksByDate(LocalDate date) {

    tasks=fileHandle.loadDate(fileName);
    List<Task>foundTasksByDate=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getDeadline().equals(date))
        foundTasksByDate.add(task);
    });
    ObservableList<Task> allFoundedTasks=FXCollections.observableArrayList(foundTasksByDate);
    return allFoundedTasks;
  }
  /*public ObservableList<Task> getTodayTasks(LocalDate today){
    tasks=fileHandle.loadDate(fileName);
    tasks.forEach(task->{

    });

  }*/

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
