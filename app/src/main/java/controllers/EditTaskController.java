package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Task;

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
  private JFXComboBox<?> priorityComboBox;

  @FXML
  private JFXTextField categoryField;


  @FXML
  private JFXButton saveEdit;

  @FXML
  private JFXButton cancelEdit;

  private Task task;
  private NavBarController navBarController;

  @FXML
  void displayTrashDialog(ActionEvent event) {

  }

  @FXML
  void displayEdit(ActionEvent event) {

  }

  public void initData(Task task, NavBarController navBarController) {
    this.task = task;
    nameField.setText(task.getTitle());
    descriptionField.setText(task.getDescription());
    deadlineDateField.setValue(task.getDeadline()); //must add variable for time too in task class.
    categoryField.setText(task.getCategory());
    this.navBarController = navBarController;
  }

  // TODO(joakilan): Validate input
  @FXML
  public void saveAction() throws IOException {
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setDeadline(deadlineDateField.getValue());
    task.setCategory(categoryField.getText());
    navBarController.loadDisplayTaskView(task);
  }

  @FXML
  public void cancelAction() throws IOException {
    navBarController.loadDisplayTaskView(task);
  }
}