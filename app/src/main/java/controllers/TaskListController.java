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

public class TaskListController implements ListController {

  @FXML private JFXListView<Task> allTasksList;
  @FXML private Text title;

  private TaskRegistry tasks;
  private MainController mainController;
  private Function<Task, Boolean> filter;

  /**
   * Method called when user clicks on add button.
   *
   * @param event ActionEvent from view
   * @throws IOException if IO fails
   */
  @FXML
  void displayNewTask(ActionEvent event) throws IOException {
    mainController.loadTaskFormView(new Task());
  }

  /**
   * Method called when user clicks on a task.
   *
   * @param event MouseEvent from view
   * @throws IOException if IO fails
   */
  @FXML
  void displayTask(MouseEvent event) throws IOException {
    if (!allTasksList.getSelectionModel().isEmpty()) {
      mainController.loadDisplayTaskView(allTasksList.getSelectionModel()
          .getSelectedItem());
    }
  }

  /**
   * @param mainController
   * @param filter
   * @param title
   * @throws IOException if IO fails
   */
  void initData(MainController mainController, Function<Task, Boolean> filter,
                String title)
      throws IOException {
    this.tasks = mainController.getTaskRegistry();
    this.filter = filter;
    refreshData();
    allTasksList.setCellFactory(c -> new CellController(mainController, this));
    this.mainController = mainController;
    this.title.setText(title);
  }

  @FXML
  @Override
  public void refreshData() throws IOException {
    allTasksList.setItems(FXCollections.observableArrayList(
        tasks.getActiveTasks().stream().filter(filter::apply)
            .collect(Collectors.toList())));
    tasks.save();
  }
}
