package controllers;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static utilities.Priority.HIGH;
import static utilities.Priority.LOW;
import static utilities.Priority.MEDIUM;
import static utilities.Status.DONE;
import static utilities.Utilities.getDialog;
import static utilities.Utilities.getFXMLLoader;

/**
 * Controller for the app's main view.
 *
 * <p>Renders the left navigation bar and handles switching of views.
 */
public class MainController implements Initializable {
  private static final Logger LOGGER =
      Logger.getLogger(MainController.class.getName());

  private static final String ALL_TASKS_FXML_NAME = "AllTasks";
  private static final String DONE_TASKS_FXML_NAME = "DoneTasks";
  private static final String TASK_FORM_FXML_NAME = "TaskForm";
  private static final String VIEW_TASK_FXML_NAME = "ViewTask";

  @FXML BorderPane pane;

  private TaskRegistry taskRegistry;
  private Parent taskFormParent;
  private TaskDetailController taskFormController;
  private Parent taskViewParent;
  private TaskDetailController taskViewController;
  private Parent taskListParent;
  private TaskListController taskListController;
  private Parent doneTasksParent;
  private TrashController doneTasksController;

  /**
   * {@inheritDoc}
   */
  @FXML
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      taskRegistry = new TaskRegistry();
      FXMLLoader taskFormLoader = getFXMLLoader(TASK_FORM_FXML_NAME);
      taskFormParent = taskFormLoader.load();
      taskFormController = taskFormLoader.getController();
      FXMLLoader displayTaskLoader = getFXMLLoader(VIEW_TASK_FXML_NAME);
      taskViewParent = displayTaskLoader.load();
      taskViewController = displayTaskLoader.getController();
      FXMLLoader taskListLoader = getFXMLLoader(ALL_TASKS_FXML_NAME);
      taskListParent = taskListLoader.load();
      taskListController = taskListLoader.getController();
      FXMLLoader doneTasksLoader = getFXMLLoader(DONE_TASKS_FXML_NAME);
      doneTasksParent = doneTasksLoader.load();
      doneTasksController = doneTasksLoader.getController();
      doneTasksController.initData(this);
      loadTaskListView(t -> true, "All Tasks", true);
    } catch (IOException e) {
      LOGGER.log(SEVERE,
          () -> "Caught exception while initializing: " + e.toString());
      exceptionHandler(e, "Exception caught in MainController::initialize");
    }
  }

  /**
   * Called when allTasksButton is clicked.
   *
   * @param event MouseEvent from view
   * @throws IOException upon IO failure
   */
  @FXML
  void displayAllTasks(MouseEvent event) throws IOException {
    loadTaskListView(t -> true, "All Tasks", true);
  }

  /**
   * Called when highPriorityButton is clicked.
   *
   * @param event MouseEvent from view
   */
  @FXML
  void displayHighPriorityTasks(MouseEvent event) {
    loadTaskListView(task -> task.getPriority().equals(HIGH),
        "High Priority Tasks", false);
  }

  /**
   * Called when mediumPriorityButton is clicked.
   *
   * @param event MouseEvent from view
   */
  @FXML
  void displayMediumPriorityTasks(MouseEvent event) {
    loadTaskListView(task -> task.getPriority().equals(MEDIUM),
        "Medium Priority Tasks", false);
  }

  /**
   * Called when lowPriorityButton is clicked.
   *
   * @param event MouseEvent from view
   */
  @FXML
  void displayLowPriorityTasks(MouseEvent event) {
    loadTaskListView(task -> task.getPriority().equals(LOW),
        "Low Priority Tasks", false);
  }

  /**
   * Called when trashButton is clicked.
   *
   * @param event MouseEvent from view
   * @throws IOException upon IO failure
   */
  @FXML
  void displayDoneTasks(MouseEvent event) throws IOException {
    doneTasksController.refreshData();
    pane.setCenter(doneTasksParent);
  }

  /**
   * Loads the view allTasks. This includes displaying the view as center and
   * initializing allTasks variable in allTasksController.
   *
   * @param filter function for filtering tasks (boolean test)
   * @param title  title for view
   */
  public void loadTaskListView(Function<Task, Boolean> filter,
                               String title, boolean dragAndDroppable) {
    try {
      taskListController.initData(this, filter, title, dragAndDroppable);
      pane.setCenter(taskListParent);
    } catch (IOException e) {
      exceptionHandler(e, "Failed to load task list");
    }
  }

  public void loadTaskDetailView(Task task, Parent parent,
                                 TaskDetailController controller) {
    controller.initData(this, task);
    pane.setCenter(parent);
  }

  public void loadTaskFormView(Task task) {
    loadTaskDetailView(task, taskFormParent, taskFormController);
  }

  public void loadDisplayTaskView(Task task) {
    loadTaskDetailView(task, taskViewParent, taskViewController);
  }

  public void exceptionHandler(Throwable e, String message) {
    LOGGER.log(SEVERE, () -> ("""
        %s caught
        Message from caller: %s
        Exception message: %s%s""").formatted(e.getClass().getName(), message
        , e.getMessage(), e.toString()));
    StackPane container = new StackPane();
    Node center = pane.getCenter();
    pane.setCenter(container);
    JFXDialog dialog = getDialog(container, new Pane(), message);
    dialog.setOnDialogClosed(event -> pane.setCenter(center));
  }

  public TaskRegistry getTaskRegistry() {
    return taskRegistry;
  }
}
