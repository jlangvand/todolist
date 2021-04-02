package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import models.Task;
import models.TaskRegistry;

public class NewTaskController {

  @FXML
  private JFXTextField nameField;

  @FXML
  private JFXTextArea descriptionField;

  @FXML
  private JFXDatePicker deadlineDateField;

  @FXML
  private JFXTimePicker deadlineTimeField;

  @FXML
  private JFXTextField categoryField;

  @FXML
  private JFXComboBox<?> priorityField;

  @FXML
  private JFXButton saveButton;

  @FXML
  private JFXButton cancelButton;

  private TaskRegistry tasks;

  @FXML
  void cancelTask(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  void saveTask(ActionEvent event) {

  }

  public void initData(TaskRegistry tasks) {
    this.tasks = tasks;
  }

}