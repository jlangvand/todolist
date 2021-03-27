package models;

import dao.PersistentRegistry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.Priority;
import utilities.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class TaskRegistry {
  private List<Task> tasks;
  PersistentRegistry fileHandle;
  private final String fileName="TasksData.obj";
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
    ArrayList<Task> foundedTaskByStatus = new ArrayList<>();
    tasks =  fileHandle.loadDate(fileName);
    tasks.forEach(task -> {
      if(task.getStatus().equals(status)){
        foundedTaskByStatus.add(task);
      }
    });
    ObservableList<Task>AllFoundedTasks= FXCollections.observableArrayList(foundedTaskByStatus);
    return AllFoundedTasks;
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
  /*
  *
  * */
  public ObservableList<Task> getHighPriorityTasks(){

    tasks=fileHandle.loadDate(fileName);
    ArrayList<Task> highPriorityTasks=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getPriority().equals("HIGH"))
        highPriorityTasks.add(task);
    });
    ObservableList<Task>highPriorities=FXCollections.observableArrayList(highPriorityTasks);
    return highPriorities;
  }
  /*
  *
  *
  *
  * */
  public ObservableList<Task> getMediumPriorityTasks(){

    tasks=fileHandle.loadDate(fileName);
    ArrayList<Task> mediumPriorityTasks=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getPriority().equals("MEDIUM"))
        mediumPriorityTasks.add(task);
    });
    ObservableList<Task>mediumPriorities=FXCollections.observableArrayList(mediumPriorityTasks);
    return mediumPriorities;
  }
  /*
  *
  *
  * */
  public ObservableList<Task> getLowPriorityTasks(){

    tasks=fileHandle.loadDate(fileName);
    ArrayList<Task>lowPriorityTasks=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getPriority().equals("LOW"))
        lowPriorityTasks.add(task);
    });
    ObservableList<Task> lowPriorities=FXCollections.observableArrayList(lowPriorityTasks);
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
  public ObservableList<Task> getDoneTasks(){
    tasks=fileHandle.loadDate(fileName);
    ArrayList<Task> doneTasks=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getPriority().equals("DONE")){
        doneTasks.add(task);
      }
    });
    ObservableList<Task> allDoneTasks=FXCollections.observableArrayList(doneTasks);
    return allDoneTasks;
  }
  /*
  *
  *
  * */
  public ObservableList<Task> getActiveTasks(){
    tasks=fileHandle.loadDate(fileName);
    ArrayList<Task> activeTasks=new ArrayList<>();
    tasks.forEach(task -> {
      if(task.getStatus().equals("ACTIVE"))
        activeTasks.add(task);
    });
    ObservableList<Task> allActiveTasks=FXCollections.observableArrayList(activeTasks);
    return allActiveTasks;
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

    tasks=fileHandle.loadDate(fileName);
    Iterator itr=tasks.iterator();
      while (itr.hasNext()) {
        Task element = (Task) itr.next();
        if (element.getTitle().equalsIgnoreCase(task.getTitle())&& !(element.getTitle().equals(null))) {
          itr.remove();
          break;
        }
      }
  }
}
