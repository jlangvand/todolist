package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;

public class TrashController implements ListController{

  @FXML
  private JFXListView<Task> allTasksList;

  private TaskRegistry tasks;
  private MainController mainController;


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

  void initData(TaskRegistry tasks, MainController mainController) throws IOException {
    this.tasks = tasks;
    refreshData();
    allTasksList.setCellFactory(cellController -> new CellController(this));
    this.mainController = mainController;
  }

  @FXML
  @Override
  public void refreshData() throws IOException {
    allTasksList.setItems(FXCollections.observableArrayList(tasks.getDoneTasks()));
    tasks.save();
  }
}
