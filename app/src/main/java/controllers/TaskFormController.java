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

import static java.util.logging.Level.INFO;
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
  void cancelAction() throws IOException {
    back();
  }

  private void back() throws IOException {
    if (editing) mainController.loadDisplayTaskView(task);
    else mainController.displayAllTasks(null);
  }

  @FXML
  void saveTask(ActionEvent event) throws IOException {
    try {
      saveOrExcept();
      getDialog(stackPane, mainPane, "Task saved").setOnDialogClosed(event1 -> {
        try {
          back();
        } catch (IOException e) {
          LOGGER.log(SEVERE, () -> "Failed to load view: " + e.toString());
        }
      });
      LOGGER.log(INFO, () -> "Task saved! Title: " + task.getTitle());
    } catch (IllegalArgumentException e) {
      getDialog(stackPane, mainPane, e.getMessage());
    } catch (IOException e) {
      getDialog(stackPane, mainPane, "Could not save file: " + e.getMessage());
      LOGGER.log(SEVERE, () -> "Could not save file: " + e.toString());
    }
  }

  private void saveOrExcept() throws IllegalArgumentException, IOException {
    if (nameField.getText().isEmpty()) throw new IllegalArgumentException(
        "Task must have a title");
    LocalDate deadlineDate = deadlineDateField.getValue();
    LocalTime deadlineTime = deadlineTimeField.getValue();
    final String deadlineError = "Deadline must be in the future";
    if (deadlineDate.isBefore(LocalDate.now()))
      throw new IllegalArgumentException(deadlineError);
    if (deadlineDate.equals(LocalDate.now()) && deadlineTime.isBefore(LocalTime.now()))
      throw new IllegalArgumentException(deadlineError);
    String category = categoryField.getText();
    if (category.isEmpty()) category = "Default";
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setCategory(category);
    task.setDeadLineTime(deadlineTimeField.getValue());
    task.setDeadline(deadlineDateField.getValue());
    task.setPriority(priorityField.getValue());
    // Add to registry only if this is a new task (avoid duplicates)
    if (!editing) {
      tasks.addTask(task);
      LOGGER.log(INFO, () -> "Task with title " + task.getTitle() + " added " +
          "to registry");
    } else {
      LOGGER.log(INFO, () -> "Task with title " + task.getTitle() + " already" +
          " in registry");
    }
    tasks.save();
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