package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import models.Task;
import models.TaskRegistry;
import utilities.Status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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
  void saveTask(ActionEvent event) throws IOException {
    Task task = new Task();
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setCategory(categoryField.getText());
    task.setStatus(Status.ACTIVE);
    task.setDeadLineTime(LocalTime.of(0,0)); //Change !!
    task.setDeadline(LocalDate.of(0, 1, 1)); //Change!!
    tasks.addTask(task);

  }

  public void initData(TaskRegistry tasks) {
    this.tasks = tasks;
  }

}