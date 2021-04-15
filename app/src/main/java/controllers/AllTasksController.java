package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AllTasksController implements ListController {

  @FXML
  private JFXListView<Task> allTasksList;

  private TaskRegistry tasks;
  private MainController mainController;
  private Function<Task, Boolean> filter;

  /**
   * Method called when user clicks on add button.
   *
   * @param event
   * @throws IOException, the exception will be handled in the method.
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

  void initData(TaskRegistry tasks,
                Function<Task, Boolean> filter,
                MainController mainController) throws IOException {
    this.tasks = tasks;
    this.filter = filter;
    refreshData();
    allTasksList.setCellFactory(cellController -> new CellController(this,
        tasks));
    this.mainController = mainController;
  }

  @FXML
  @Override
  public void refreshData() throws IOException {
    allTasksList.setItems(
        FXCollections.observableArrayList(
            tasks.getActiveTasks().stream().filter(filter::apply)
                .collect(Collectors.toList())));
  }
}
