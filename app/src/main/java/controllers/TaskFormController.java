package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static utilities.Utilities.getDialog;

public class TaskFormController {
  private static final Logger LOGGER =
      Logger.getLogger(TaskFormController.class.getName());

  @FXML
  private StackPane stackPane;

  @FXML
  private BorderPane mainPane;

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
  private JFXComboBox<Priority> priorityField;

  @FXML
  private JFXButton saveButton;

  @FXML
  private JFXButton cancelButton;

  @FXML
  private Label titleText;

  private TaskRegistry tasks;
  private Task task;
  private MainController mainController;
  private boolean editing;

  @FXML
  void cancelTask(ActionEvent event) throws IOException {
    if (editing) mainController.loadDisplayTaskView(task);
    else mainController.displayAllTasks(null);
  }

  @FXML
  void saveTask(ActionEvent event) throws IOException {
    if (validateInput()) {
      String category = categoryField.getText();
      if (category.isEmpty()) category = "Default";
      task.setTitle(nameField.getText());
      task.setDescription(descriptionField.getText());
      task.setCategory(category);
      task.setDeadLineTime(deadlineTimeField.getValue());
      task.setDeadline(deadlineDateField.getValue());
      task.setPriority(priorityField.getValue());
      if (!editing) tasks.addTask(task);
      JFXDialog dialog = getDialog(stackPane, mainPane, "Task saved");
      dialog.setOnDialogClosed(event1 -> {
        try {
          mainController.displayAllTasks(null);
        } catch (IOException e) {
          LOGGER.log(SEVERE, () -> "Failed to load view: " + e.toString());
        }
      });
    }
  }

  private boolean validateInput() {
    LocalDate deadlineDate = deadlineDateField.getValue();
    LocalTime deadlineTime = deadlineTimeField.getValue();
    boolean ok;
    ok = !nameField.getText().isEmpty();
    ok = ok && deadlineDate.isAfter(LocalDate.now().minusDays(1));
    if (deadlineDate.isEqual(LocalDate.now())) {
      ok = ok && deadlineTime.isAfter(LocalTime.now());
    }
    return ok;
  }

  public void initData(MainController mainController, Task task) {
    this.tasks = mainController.getTaskRegistry();
    this.task = task;
    this.mainController = mainController;
    this.editing = tasks.contains(task);
    titleText.setText(editing ? "Edit task" : "New task");
    nameField.setText(task.getTitle());
    descriptionField.setText((task.getDescription()));
    deadlineDateField.setValue(task.getDeadline());
    deadlineTimeField.setValue(task.getDeadLineTime());
    categoryField.setText(task.getCategory());
    priorityField.setItems(FXCollections.observableArrayList(Priority.values()));
    priorityField.setValue(task.getPriority());
  }

}