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
import com.jfoenix.controls.JFXListCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import models.Task;
import models.TaskRegistry;
import utilities.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.Status.ACTIVE;
import static utilities.Status.DONE;
import static utilities.Utilities.getImagePath;

/**
 * This class represents a task cell. A task cell is responsible for interaction
 * between a user and a task, and to display a task with basic information.
 */
public class CellController extends JFXListCell<Task> {

  @FXML private ResourceBundle resources;
  @FXML private URL location;
  @FXML private AnchorPane taskCellPane;
  @FXML private Label cellTitle;
  @FXML private ImageView cellStatusImage;
  @FXML private Label cellDate;
  @FXML private JFXButton statusButton;

  private FXMLLoader fxmlLoader;
  private final ListController listController;
  private final TaskRegistry tasks;

  /**
   * Creates a CellController object.
   *
   * @param mainController   main controller for callbacks
   * @param listController   controller of the display where this cell should be
   *                         displayed
   * @param dragAndDroppable true if list can be rearranged by user, else false
   */
  public CellController(MainController mainController,
                        ListController listController,
                        boolean dragAndDroppable) {
    this.listController = listController;
    this.tasks = mainController.getTaskRegistry();

    if (dragAndDroppable) {
      setOnDragDetected(this::onDragDetected);
      setOnDragOver(this::onDragOver);
      setOnDragExited(this::onDragExited);
      setOnDragDropped(this::onDragDropped);
    }
  }

  /**
   * Updates this task cell.
   *
   * @param task  Task belonging to this cell
   * @param empty {@inheritDoc}
   */
  @Override
  protected void updateItem(Task task, boolean empty) {
    super.updateItem(task, empty);

    if (empty || task == null) {
      setText(null);
      setGraphic(null);
    } else {
      if (fxmlLoader == null) {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/TaskCell.fxml"));
        fxmlLoader.setController(this);

        try {
          fxmlLoader.load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      // Filling cell with info from task
      cellTitle.setText(task.getTitle());
      cellDate.setText(task.getDateAdded().toString());
      updateStatusImage(task.getStatus());

      /*
       Inserting all graphics (Description, date, etc.) in root Pane
       (taskCellPane)
      */
      setText(null);
      setGraphic(taskCellPane);

      // Event handling
      statusButton.setOnMouseReleased(event -> {
        Status status = task.getStatus() == ACTIVE ? DONE : ACTIVE;
        task.setStatus(status);
        updateStatusImage(status);
        listController.refreshData();
      });
    }
  }

  /**
   * Updates the status image of this task cell.
   *
   * @param status current status of a task to be updated
   */
  private void updateStatusImage(Status status) {
    if (status.equals(Status.DONE)) {
      cellStatusImage.setImage(new Image(getImagePath("Done2.png"),
          48, 48, true, true));
    } else {
      cellStatusImage.setImage(new Image(getImagePath("not_Done.png")));
    }
  }

  /**
   * Handle mouse drag events for reordering tasks.
   *
   * @param event MouseEvent from view
   */
  private void onDragDetected(MouseEvent event) {
    if (getItem() == null) return;
    ObservableList<Task> items = getListView().getItems();
    // No reordering in trash list
    if (getItem().getStatus() == ACTIVE) {
      var dragboard = startDragAndDrop(TransferMode.MOVE);
      var content = new ClipboardContent();
      content.putString(String.valueOf(items.indexOf(getItem())));
      dragboard.setContent(content);
      dragboard.setDragView(new Image(getImagePath("Task_icon" +
          ".png")));
    }
  }

  private void onDragOver(DragEvent event) {
    if (event.getGestureSource() != this && event.getDragboard().hasString()) {
      event.acceptTransferModes(TransferMode.MOVE);
    }
    event.consume();
  }

  private void onDragExited(DragEvent event) {
    if (event.getGestureSource() != this &&
        event.getDragboard().hasString()) {
      setOpacity(1);
    }
  }

  /**
   * Update index in task registry when dropped.
   *
   * @param event DragEvent from view
   */
  private void onDragDropped(DragEvent event) {
    if (getItem() == null) return;

    var db = event.getDragboard();
    var success = false;

    if (db.hasString()) {
      ObservableList<Task> items = getListView().getItems();
      var draggedIdx = Integer.parseInt(db.getString());
      var thisIdx = items.indexOf(getItem());

      try {
        if (thisIdx > draggedIdx) {
          for (var i = draggedIdx; i < thisIdx; i++) {
            tasks.swapTasksByIndex(tasks.getActiveTasksIndex()[i],
                tasks.getActiveTasksIndex()[i + 1]);
          }
        } else if (draggedIdx > thisIdx) {
          for (var i = draggedIdx; i > thisIdx; i--) {
            tasks.swapTasksByIndex(tasks.getActiveTasksIndex()[i],
                tasks.getActiveTasksIndex()[i - 1]);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      success = true;
    }

    listController.refreshData();
    event.setDropCompleted(success);
    event.consume();
  }
}

