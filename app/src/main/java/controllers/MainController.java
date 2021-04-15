package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import static utilities.Utilities.getFXMLLoader;

public class MainController implements Initializable {
  private static final String ALL_TASKS_FXML_NAME = "AllTasks";
  private static final String DONE_TASKS_FXML_NAME = "DoneTasks";
  private static final String TASK_FORM_FXML_NAME = "TaskForm";
  private static final String VIEW_TASK_FXML_NAME = "ViewTask";

  @FXML
  BorderPane pane;

  private TaskRegistry taskRegistry;
  private FXMLLoader taskFormLoader;
  private FXMLLoader displayTaskLoader;

  private static final Function<Task, Boolean> filterAllTasks = t -> true;
  private static final Function<Task, Boolean> filterHighPriorityTasks =
      t -> t.getPriority() == Priority.HIGH;

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
      taskRegistry = new TaskRegistry();
      loadAllTasksView(filterAllTasks);
      taskFormLoader = getFXMLLoader(TASK_FORM_FXML_NAME);
      displayTaskLoader = getFXMLLoader(VIEW_TASK_FXML_NAME);
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
    loadAllTasksView(filterAllTasks);
  }

  /**
   * Called when highPriorityButton is clicked.
   *
   * @param event
   */
  @FXML
  void displayHighPriorityTasks(MouseEvent event) throws IOException {
    loadAllTasksView(filterHighPriorityTasks);
  }

  /**
   * Called when mediumPriorityButton is clicked.
   *
   * @param event
   */
  @FXML
  void displayMediumPriorityTasks(MouseEvent event) {
    // To be implemented
  }

  /**
   * Called when lowPriorityButton is clicked.
   *
   * @param event
   */
  @FXML
  void displayLowPriorityTasks(MouseEvent event) {
    // To be implemented
  }

  /**
   * Called when trashButton is clicked.
   *
   * @param event
   */
  @FXML
  void displayDoneTasks(MouseEvent event) throws IOException {
    loadDoneTasksView();
  }

  /**
   * Loads the view allTasks. This includes displaying the view as center and
   * initializing allTasks variable in allTasksController.
   *
   * @throws IOException
   */
  public void loadAllTasksView(Function<Task, Boolean> filter) throws IOException {
    FXMLLoader fxmlLoader = getFXMLLoader(ALL_TASKS_FXML_NAME);
    Parent root = fxmlLoader.load();
    AllTasksController allTasksController = fxmlLoader.getController();
    allTasksController.initData(taskRegistry, filter, this);
    pane.setCenter(root);
  }

  public void loadNewTaskView() throws IOException {
    taskFormLoader = getFXMLLoader(TASK_FORM_FXML_NAME);
    Parent root = taskFormLoader.load();
    TaskFormController taskFormController = taskFormLoader.getController();
    taskFormController.initData(this, new Task());
    pane.setCenter(root);
  }

  public void loadDisplayTaskView(Task task) throws IOException {
    displayTaskLoader = getFXMLLoader(VIEW_TASK_FXML_NAME);
    Parent root = displayTaskLoader.load();
    ViewTaskController controller = displayTaskLoader.getController();
    controller.initData(task, this);
    pane.setCenter(root);
  }

  public void loadEditTaskView(Task task) throws IOException {
    taskFormLoader = getFXMLLoader(TASK_FORM_FXML_NAME);
    Parent root = taskFormLoader.load();
    TaskFormController controller = taskFormLoader.getController();
    controller.initData(this, task);
    pane.setCenter(root);
  }

  public void loadDoneTasksView() throws IOException {
    FXMLLoader fxmlLoader = getFXMLLoader(DONE_TASKS_FXML_NAME);
    Parent root = fxmlLoader.load();
    TrashController trashController = fxmlLoader.getController();
    trashController.initData(taskRegistry, this);
    pane.setCenter(root);
  }

  public TaskRegistry getTaskRegistry() {
    return taskRegistry;
  }
}
