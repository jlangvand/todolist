package controllers;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;

public class TrashController implements ListController {

  @FXML private JFXListView<Task> allTasksList;

  private TaskRegistry tasks;
  private MainController mainController;

  void initData(MainController mainController) {
    this.tasks = mainController.getTaskRegistry();
    refreshData();
    allTasksList.setCellFactory(c -> new CellController(mainController, this,
        false));
    this.mainController = mainController;
    allTasksList.setOnMouseReleased(event -> {
      if (!allTasksList.getSelectionModel().isEmpty()) {
        mainController.loadDisplayTaskView(
            allTasksList.getSelectionModel().getSelectedItem());
      }
    });
  }

  @FXML
  @Override
  public void refreshData() {
    allTasksList.setItems(FXCollections.observableArrayList(tasks.getDoneTasks()));
    try {
      tasks.save();
    } catch (IOException e) {
      mainController.exceptionHandler(e, "Failed to save task");
    }
  }
}
