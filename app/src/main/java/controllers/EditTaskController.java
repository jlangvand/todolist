package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;

import java.io.IOException;

public class EditTaskController {

  @FXML
  private BorderPane pane;

  @FXML
  private JFXTextField nameField;

  @FXML
  private JFXTextArea descriptionField;

  @FXML
  private JFXDatePicker deadlineDateField;

  @FXML
  private JFXTimePicker deadlineTimeField;

  @FXML
  private JFXComboBox<Priority> priorityField;

  @FXML
  private JFXTextField categoryField;

  @FXML
  private JFXButton saveEdit;

  @FXML
  private JFXButton cancelEdit;

  private TaskRegistry allTasks;
  private Task task;
  private MainController mainController;

  @FXML
  void displayTrashDialog(ActionEvent event) {
    // To be implemented
  }

  @FXML
  void displayEdit(ActionEvent event) {
    // To be implemented
  }

  public void initData(Task task, TaskRegistry allTasks, MainController mainController) {
    this.task = task;
    this.allTasks = allTasks;
    nameField.setText(task.getTitle());
    descriptionField.setText(task.getDescription());
    deadlineDateField.setValue(task.getDeadline()); //must add variable for time too in task class.
    categoryField.setText(task.getCategory());
    priorityField.setItems(FXCollections.observableArrayList(Priority.values()));
    priorityField.setValue(task.getPriority());
    this.mainController = mainController;
  }

  // TODO(joakilan): Validate input
  @FXML
  public void saveAction() throws IOException {
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setDeadline(deadlineDateField.getValue());
    task.setCategory(categoryField.getText());
    task.setPriority(priorityField.getValue());
    allTasks.save(); //Needed in order to save task changes locally.
    mainController.loadDisplayTaskView(task);
  }

  @FXML
  public void cancelAction() throws IOException {
    mainController.loadDisplayTaskView(task);
  }
}