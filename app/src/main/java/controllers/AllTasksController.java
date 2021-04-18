package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Controller for views that are displaying tasks from a task registry.
 * (Currently All Task Page and Priority Pages)
 */
public class AllTasksController implements ListController {

  @FXML
  private JFXListView<Task> allTasksList;

  @FXML
  private Text title;

  private TaskRegistry tasks;
  private MainController mainController;
  private Function<Task, Boolean> filter;

  /**
   * Method called when user clicks on add button.
   *
   * @param event
   */
  @FXML
  void displayNewTask(ActionEvent event) {
    try {
      mainController.loadNewTaskView();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method called when user clicks on a task.
   * The method displays information about the task that was clicked on.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void displayTask(MouseEvent event) throws IOException {
    if (!allTasksList.getSelectionModel().isEmpty()) {
      Task selectedTask = allTasksList.getSelectionModel()
          .getSelectedItem();
      mainController.loadDisplayTaskView(selectedTask);
    }
  }

  /**
   * Initializes data needed for this controller.
   *
   * @param tasks TaskRegistry to display tasks from
   * @param filter
   * @param mainController MainController
   * @param title Title of the task display
   * @param dragAndDroppable True: drag and drop enabled, False: drag and drop disabled
   * @throws IOException
   */
  void initData(TaskRegistry tasks, Function<Task, Boolean> filter,
                MainController mainController, String title,
                boolean dragAndDroppable) throws IOException {
    this.tasks = tasks;
    this.filter = filter;
    refreshData();
    allTasksList.setCellFactory(cellController ->
            new CellController (this, tasks, dragAndDroppable));
    this.mainController = mainController;
    this.title.setText(title);
  }

  /**
   * Refreshes the view.
   *
   * @throws IOException
   */
  @FXML
  @Override
  public void refreshData() {
    allTasksList.setItems(
        FXCollections.observableArrayList(
            tasks.getActiveTasks().stream().filter(filter::apply)
                .collect(Collectors.toList())));
  }
}
