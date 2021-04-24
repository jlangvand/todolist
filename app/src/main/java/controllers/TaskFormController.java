/*
 *     Copyright © 2021 Mona Mahmoud Mousa
 *
 *      Authors (in alphabetical order):
 *      Ask Brandsnes Røsand
 *      Joakim Skogø Langvand
 *      Leonard Sandløkk Schiller
 *      Moaaz Bassam Yanes
 *      Mona Mahmoud Mousa
 *
 *     This file is part of Todolist.
 *
 *     Todolist is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Todolist is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Todolist.  If not, see <https://www.gnu.org/licenses/>.
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static utilities.Utilities.getDialog;

/**
 * Form controller for adding and editing tasks.
 */
public class TaskFormController implements TaskDetailController, Initializable {
  private static final Logger LOGGER =
      Logger.getLogger(TaskFormController.class.getName());

  @FXML private StackPane stackPane;
  @FXML private BorderPane mainPane;
  @FXML private JFXTextField nameField;
  @FXML private JFXTextArea descriptionField;
  @FXML private JFXDatePicker deadlineDateField;
  @FXML private JFXTimePicker deadlineTimeField;
  @FXML private JFXTextField categoryField;
  @FXML private JFXComboBox<Priority> priorityField;
  @FXML private JFXButton saveButton;
  @FXML private JFXButton cancelButton;
  @FXML private Label titleText;

  private TaskRegistry tasks;
  private Task task;
  private MainController mainController;
  private boolean editing;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cancelButton.setOnMouseReleased(this::back);
    saveButton.setOnMouseReleased(event -> {
      nameField.validate();
      try {
        saveOrExcept();
        getDialog(stackPane, mainPane, "Task saved")
            .setOnDialogClosed(this::back);
        LOGGER.log(INFO, () ->
            "Task saved! Title: %s".formatted(task.getTitle()));
      } catch (IllegalArgumentException e) {
        getDialog(stackPane, mainPane, e.getMessage());
      } catch (IOException e) {
        mainController.exceptionHandler(e,
            "Could not save file: %s".formatted(e.getMessage()));
      }
    });
    nameField.setValidators(new RequiredFieldValidator(
        "Task must have a title"));
  }

  /**
   * Back to task view is editing task, else back to task list.
   *
   * @param e event from view
   */
  private void back(Event e) {
    if (editing) {
      mainController.loadDisplayTaskView(task);
    } else {
      mainController.loadTaskListView();
    }
  }

  /**
   * Save the task or throw an exception if validation fails.
   *
   * @throws IllegalArgumentException if field validation failed
   * @throws IOException              if task save failed
   */
  private void saveOrExcept() throws IllegalArgumentException, IOException {
    if (nameField.getText().isEmpty()) {
      throw new IllegalArgumentException(
          "Task must have a title");
    }
    LocalDate deadlineDate = deadlineDateField.getValue();
    LocalTime deadlineTime = deadlineTimeField.getValue();
    final var deadlineError = "Deadline must be in the future";
    if (deadlineDate.isBefore(LocalDate.now())
        || (deadlineDate.equals(LocalDate.now())
        && deadlineTime.isBefore(LocalTime.now()))) {
      throw new IllegalArgumentException(deadlineError);
    }
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setCategory(categoryField.getText());
    task.setDeadLineTime(deadlineTimeField.getValue());
    task.setDeadline(deadlineDateField.getValue());
    task.setPriority(priorityField.getValue());
    // Add to registry only if this is a new task (avoid duplicates)
    if (!editing) {
      tasks.addTask(task);
      LOGGER.log(INFO, () ->
          "Task with title %s added to registry".formatted(task.getTitle()));
    } else {
      LOGGER.log(INFO, () ->
          "Task with title %s already in registry".formatted(task.getTitle()));
    }
    tasks.save();
  }

  /**
   * {@inheritDoc}
   *
   * @param mainController {@inheritDoc}
   * @param task           {@inheritDoc}
   */
  @Override
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