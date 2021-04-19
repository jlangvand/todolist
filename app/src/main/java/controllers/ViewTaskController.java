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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Task;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static utilities.Utilities.deadlineRemainingTimeString;
import static utilities.Utilities.getDialog;

public class ViewTaskController implements TaskDetailController, Initializable {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private Label taskTitle;
  @FXML private Label taskDescription;
  @FXML private Label deadlineTimeLeft;
  @FXML private Label deadlineDateTime;
  @FXML private Label taskPriority;
  @FXML private Label taskCategory;
  @FXML private Label taskStartedDate;
  @FXML private JFXButton editButton;
  @FXML private JFXButton backButton;
  @FXML private Pane viewPane;
  @FXML private StackPane stackPane;
  @FXML private BorderPane mainPane;
  @FXML private ImageView statusImage;

  private Task task;
  private MainController mainController;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    editButton.setOnAction(event -> mainController.loadTaskFormView(task));
    backButton.setOnAction(event -> mainController.loadTaskListView());
  }

  /**
   * Method called right after object is initialized
   *
   * @param task
   */
  public void initData(MainController mainController, Task task) {
    this.task = task;
    taskTitle.setText(task.getTitle());
    taskPriority.setText(task.getPriority().toString());
    taskCategory.setText(task.getCategory());
    taskDescription.setText(task.getDescription());
    taskStartedDate.setText(task.getDateAdded().toString());
    deadlineDateTime.setText("Deadline: %s %s".formatted(
        task.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE),
        task.getDeadLineTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
            .substring(0, 5)));
    deadlineTimeLeft.setText(deadlineRemainingTimeString(task.getDeadline(),
        task.getDeadLineTime()));
    this.mainController = mainController;
  }

  @FXML
  public void deleteAction() {
    BoxBlur blur = new BoxBlur(3, 3, 3);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXButton delete = new JFXButton("Delete");
    JFXButton cancel = new JFXButton("Cancel");
    String styleSheetPath = ViewTaskController.class.
        getResource("/css/dialogJFX.css").toString();
    delete.getStylesheets().add(styleSheetPath);
    cancel.getStylesheets().add(styleSheetPath);

    JFXDialog dialog = new JFXDialog(stackPane, dialogLayout,
        JFXDialog.DialogTransition.TOP);

    delete.setOnAction(event1 -> {
      try {
        mainController.getTaskRegistry().removeTask(task);

        JFXDialog deletedDialog = getDialog(stackPane, mainPane,
            "The task has been deleted successfully");
        deletedDialog.setOnDialogClosed(
            event2 -> {
              mainController.loadTaskListView();
              dialog.close();
            });
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    cancel.setOnAction(event1 -> dialog.close());
    dialog.setOnDialogClosed(event1 -> mainPane.setEffect(null));

    Label label = new Label("Delete this task?");
    label.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 17pt");
    dialogLayout.setBody(label);
    dialogLayout.setActions(delete, cancel);
    mainPane.setEffect(blur);
    dialog.show();
    dialog.setEffect(null);
  }
}