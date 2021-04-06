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
    Task t4 = new Task();
    t4.setTitle("3rd323 task");
    //t3.setStartedDate(LocalDate.of(2021, 4, 8));
    t4.setDescription("don't do anything... ");
    t4.setStatus(Status.ACTIVE);
    t4.setDeadLineTime(LocalTime.of(23,00));
    t4.setDeadline(LocalDate.of(2021, 4, 9));
    tasks.addTask(t4);

  }

  public void initData(TaskRegistry tasks) {
    this.tasks = tasks;
  }

}