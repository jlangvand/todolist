/*
 *     Copyright © 2021 Mona Mahmoud Mousa
 *
 *      Authors (in alphabetical order):
 *      Ask Brandsnes Røsand
 *      Joakim Skogø Langvand
 *      Leonard Sandløkk Schiller
 *      Moaaz Bassam Yanes
 *      Mona Mahmoud Mousa
 *
 *     This file is part of Todolist.
 *
 *     Todolist is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Todolist is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Todolist.  If not, see <https://www.gnu.org/licenses/>.
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import static utilities.Constants.ALL_TASKS_FXML_NAME;
import static utilities.Constants.DONE_TASKS_FXML_NAME;
import static utilities.Constants.TASK_FORM_FXML_NAME;
import static utilities.Constants.VIEW_TASK_FXML_NAME;
import static utilities.Priority.HIGH;
import static utilities.Priority.LOW;
import static utilities.Priority.MEDIUM;
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

  @FXML BorderPane pane;
  @FXML JFXButton allTasksButton;
  @FXML JFXButton highPriorityButton;
  @FXML JFXButton mediumPriorityButton;
  @FXML JFXButton lowPriorityButton;
  @FXML JFXButton doneTasksButton;

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
    } catch (IOException e) {
      LOGGER.log(SEVERE, () ->
          "Caught exception while initializing: %s".formatted(e.toString()));
      exceptionHandler(e, "Exception caught in MainController::initialize");
    }
    initViews();
    initButtons();
  }

  private void initViews() {
    try {
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
      loadTaskListView();
    } catch (IOException e) {
      LOGGER.log(SEVERE, () ->
          "Failed to init views: %s".formatted(e.toString()));
      exceptionHandler(e, "Error: Could not load views!");
    }
  }

  private void initButtons() {
    allTasksButton.setOnMouseReleased(event -> loadTaskListView());
    highPriorityButton.setOnMouseReleased(event ->
        loadTaskListView(task -> task.getPriority() == HIGH,
            "High priority tasks"));
    mediumPriorityButton.setOnMouseReleased(event ->
        loadTaskListView(task -> task.getPriority() == MEDIUM,
            "Medium priority tasks"));
    lowPriorityButton.setOnMouseReleased(event ->
        loadTaskListView(task -> task.getPriority() == LOW,
            "Low priority tasks"));
    doneTasksButton.setOnMouseReleased(event -> {
      doneTasksController.refreshData();
      pane.setCenter(doneTasksParent);
    });
  }

  /**
   * Loads the Task List view.
   *
   * <p>This includes displaying the view as center and initializing allTasks
   * variable in allTasksController.
   */
  public void loadTaskListView() {
    loadTaskListView(task -> true, "All Tasks", true);
  }

  /**
   * Loads the Task List view.
   *
   * <p>This includes displaying the view as center and initializing allTasks
   * variable in allTasksController.
   *
   * @param filter function for filtering tasks (boolean test)
   * @param title  title for view
   */
  public void loadTaskListView(Function<Task, Boolean> filter,
                               String title) {
    loadTaskListView(filter, title, false);
  }

  /**
   * Loads the Task List view.
   *
   * <p>This includes displaying the view as center and initializing allTasks
   * variable in allTasksController.
   *
   * @param filter           function for filtering tasks (boolean test)
   * @param title            title for view
   * @param dragAndDroppable true if list can be rearranged
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

  /**
   * Load view for new task.
   */
  public void loadTaskFormView() {
    loadTaskFormView(new Task());
  }

  /**
   * Load view for editing task.
   *
   * @param task task to edit
   */
  public void loadTaskFormView(Task task) {
    loadTaskDetailView(task, taskFormParent, taskFormController);
  }

  /**
   * Load view for displaying task.
   *
   * @param task task to display
   */
  public void loadDisplayTaskView(Task task) {
    loadTaskDetailView(task, taskViewParent, taskViewController);
  }

  public void exceptionHandler(Throwable e, String message) {
    LOGGER.log(SEVERE, () -> ("""
        %s caught
        Message from caller: %s
        Exception message: %s%s""").formatted(e.getClass().getName(), message,
        e.getMessage(), e.toString()));
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
