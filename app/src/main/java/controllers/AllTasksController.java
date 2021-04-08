package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;

public class AllTasksController {

    @FXML
    private JFXListView<Task> allTasksList;

    @FXML
    private JFXButton addTaskButton;

    @FXML
    private BorderPane taskViewPane;

    @FXML
    private JFXTextArea taskDescription;

    @FXML
    private JFXButton backToTasks;

    @FXML
    private Label taskTitle;

    @FXML
    private Label taskDeadline;

    @FXML
    private Label taskPriority;

    @FXML
    private Label taskStartedDate;

    @FXML
    private JFXButton editButton;

    private TaskRegistry tasks;
    private NavBarController navBarController;

    @FXML
    void initialize() {
    }

  /**
   * Method called when user clicks on add button.
   *
   * @param event
   * @throws IOException, the exception will be handled in the method.
   */
  @FXML
  void displayNewTask(ActionEvent event) {
    try {
      navBarController.loadNewTaskView(tasks);
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
          navBarController.loadDisplayTaskView(selectedTask);
      }
  }

  void initData(TaskRegistry tasks, NavBarController navBarController) {
    this.tasks = tasks;
    refreshData();
    allTasksList.setCellFactory(cellController -> new CellController());
    this.navBarController = navBarController;
  }

  @FXML
  void refreshData() {
    allTasksList.setItems(FXCollections.observableArrayList(tasks.getTasks()));
  }


}
