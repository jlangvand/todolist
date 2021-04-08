package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;
import utilities.Status;
import utilities.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static utilities.Utilities.getFXMLLoader;

public class NavBarController implements Initializable {
    @FXML
    BorderPane pane;

    @FXML
    private JFXButton allTasksButton;

    @FXML
    private JFXButton highPriorityButton;

    @FXML
    private JFXButton mediumPriorityButton;

    @FXML
    private JFXButton lowPriorityButton;

    @FXML
    private JFXButton trashButton;

    private TaskRegistry allTasks;

    private FXMLLoader allTasksLoader;
    private FXMLLoader newTaskLoader;
    private FXMLLoader displayTaskLoader;
    private Parent displayTasksRoot;
    private FXMLLoader editTaskLoader;

    /**
     * Called when the navBarController is initialized.
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      try {
        allTasks = getTestRegistry(); //Sets allTasks registry as test registry.
        loadAllTasksView("allTasks");
        newTaskLoader = getFXMLLoader("NewTask");
        allTasksLoader = getFXMLLoader("allTasks");
        displayTaskLoader = getFXMLLoader("ViewTask");
//      displayTaskLoader.load();
        editTaskLoader = getFXMLLoader("EditTask");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    /**
     * Called when allTasksButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayAllTasks(MouseEvent event) throws IOException {
      loadAllTasksView("allTasks");
    }


    /**
     * Called when highPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayHighPriorityTasks(MouseEvent event) {

    }


    /**
     * Called when mediumPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayMediumPriorityTasks(MouseEvent event) {

    }


    /**
     * Called when lowPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayLowPriorityTasks(MouseEvent event) {

    }


    /**
     * Called when trashButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayTrash(MouseEvent event) {

    }

  /**
   * Loads the view allTasks. This includes displaying the view as center and
   * initializing allTasks variable in allTasksController.
   *
   * @param fxml fxml file name of all tasks view. (excluding .fxml)
   * @throws IOException
   */
    private void loadAllTasksView(String fxml) throws IOException {
      FXMLLoader fxmlLoader = getFXMLLoader("allTasks");
      Parent root = fxmlLoader.load();
      AllTasksController allTasksController = fxmlLoader.getController();
      allTasksController.initData(allTasks, this);
      pane.setCenter(root);

    }

    public void loadNewTaskView(TaskRegistry tasks) throws IOException {
      newTaskLoader = getFXMLLoader("NewTask");
      Parent root = newTaskLoader.load();
      NewTaskController newTaskController = newTaskLoader.getController();
      newTaskController.initData(tasks);
      pane.setCenter(root);
    }

    public void loadDisplayTaskView(Task task) throws IOException {
      displayTaskLoader = getFXMLLoader("ViewTask");
      Parent root = displayTaskLoader.load();
      ViewTaskController controller = displayTaskLoader.getController();
      controller.initData(task, this);
      pane.setCenter(root);
    }

    public void loadEditTaskView(Task task) throws IOException {
      editTaskLoader = getFXMLLoader("EditTask");
      Parent root = editTaskLoader.load();
      EditTaskController controller = editTaskLoader.getController();
      controller.initData(task, this);
      pane.setCenter(root);
    }

  /**
   * @return Test task registry object with three tasks.
   * @throws IOException
   */
  private TaskRegistry getTestRegistry() throws IOException {
      Task t1 = new Task();
      t1.setTitle("Methods");
      //t1.setStartedDate(LocalDate.of(2021, 4, 2));
      //t1.setDeadline(t1.getDeadline()); need to write this method to calculate the deadline..
      t1.setDescription("finish all methods");
      t1.setStatus(Status.DONE);
      t1.setPriority(Priority.HIGH);
      t1.setDeadLineTime(LocalTime.of(17,30));
      t1.setDeadline(LocalDate.of(2021, 4, 2));



      Task t2 = new Task();
      t2.setTitle("Shopping..");
      //t2.setStartedDate(LocalDate.of(2021, 4, 3));
      t2.setDescription("Cheese, milk, bread");
      t2.setStatus(Status.DONE);
      t2.setPriority(Priority.MEDIUM);
      t2.setDeadLineTime(LocalTime.of(17,42));
      t2.setDeadline(LocalDate.of(2021, 4, 3));




      Task t3 = new Task();
      t3.setTitle("3rd task");
      //t3.setStartedDate(LocalDate.of(2021, 4, 8));
      t3.setDescription("don't do anything... ");
      t3.setStatus(Status.ACTIVE);
      t3.setDeadLineTime(LocalTime.of(23,00));
      t3.setDeadline(LocalDate.of(2021, 4, 9));


      TaskRegistry taskRegistry = new TaskRegistry();
      taskRegistry.addTask(t1);
      taskRegistry.addTask(t2);
      taskRegistry.addTask(t3);

      return taskRegistry;

  }

}
