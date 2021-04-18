package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskListController implements ListController, Initializable {

  @FXML private JFXListView<Task> allTasksList;
  @FXML private JFXButton addTaskButton;
  @FXML private Text title;

  private TaskRegistry tasks;
  private MainController mainController;
  private Function<Task, Boolean> filter;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    allTasksList.setOnMouseReleased(event -> {
      if (!allTasksList.getSelectionModel().isEmpty()) {
        mainController.loadDisplayTaskView(allTasksList.getSelectionModel()
            .getSelectedItem());
      }
    });
    addTaskButton.setOnMouseReleased(event -> mainController.loadTaskFormView());
  }

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
   * @param mainController
   * @param filter
   * @param title
   * @throws IOException if IO fails
   */
  void initData(MainController mainController, Function<Task, Boolean> filter,
                String title, boolean dragAndDroppable)
      throws IOException {
    this.tasks = mainController.getTaskRegistry();
    this.filter = filter;
    refreshData();
    allTasksList.setCellFactory(c -> new CellController(mainController, this,
        dragAndDroppable));
    this.mainController = mainController;
    this.title.setText(title);
  }

  @FXML
  @Override
  public void refreshData() {
    allTasksList.setItems(FXCollections.observableArrayList(
        tasks.getActiveTasks().stream().filter(filter::apply)
            .collect(Collectors.toList())));
    try {
      tasks.save();
    } catch (IOException e) {
      mainController.exceptionHandler(e, "Failed to save task");
    }
  }
}
