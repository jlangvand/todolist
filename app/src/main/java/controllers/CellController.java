package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import models.Task;
import models.TaskRegistry;
import utilities.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utilities.Utilities.getImagePath;

public class CellController extends JFXListCell<Task> {
  private static final Logger LOGGER =
      Logger.getLogger(CellController.class.getName());

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private AnchorPane taskCellPane;

  @FXML
  private Label cellTitle;

  @FXML
  private ImageView cellStatusImage;

  @FXML
  private Label cellDate;

  @FXML
  private JFXButton statusButton;

  private FXMLLoader fxmlLoader;
  private ListController listController;
  private TaskRegistry allTasks;

  public CellController(ListController listController, TaskRegistry allTasks, boolean dragAndDroppable) {
    this.listController = listController;
    this.allTasks = allTasks;

    if (dragAndDroppable){
      setOnDragDetected(this::onDragDetected);
      setOnDragOver(this::onDragOver);
      setOnDragExited(this::onDragExited);
      setOnDragDropped(this::onDragDropped);
    }
  }

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
        fxmlLoader.setController(this); //This class is the controller for the TaskCell.fxml

        try {
          fxmlLoader.load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      //Filling cell with info from task
      cellTitle.setText(task.getTitle());
      cellDate.setText(task.getDateAdded().toString());
      updateStatusImage(task.getStatus());


      // Inserting all graphics (Description, date, etc.) in root Pane (taskCellPane)
      setText(null);
      setGraphic(taskCellPane);

      //Event handling
      statusButton.setOnMousePressed(event -> updateTaskStatus(task));
    }

  }

  private void updateStatusImage(Status status) {
    if (status.equals(Status.DONE)) {
      cellStatusImage.setImage(new Image(getImagePath("Done2.png"),
          48, 48, true,
          true));
    } else {
      cellStatusImage.setImage(new Image(getImagePath("not_Done.png")));
    }
  }

  private void onDragDetected(MouseEvent event) {
    if (getItem() == null) return;
    ObservableList<Task> items = getListView().getItems();
    if (getItem().getStatus() == Status.ACTIVE) { //Not allowing reorder in trash page
      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
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

  private void onDragDropped(DragEvent event) {
    if (getItem() == null) return;

    Dragboard db = event.getDragboard();

    boolean success = false;

    if (db.hasString()) {
      ObservableList<Task> items = getListView().getItems();
      int draggedIdx = Integer.parseInt(db.getString());
      int thisIdx = items.indexOf(getItem());

      try {
        if (thisIdx > draggedIdx) {
          for (int i = draggedIdx; i < thisIdx; i++) {
            allTasks.swapTasksByIndex(allTasks.getActiveTasksIndex()[i],
                    allTasks.getActiveTasksIndex()[i + 1]);
          }
        } else if (draggedIdx > thisIdx) {
          for (int i = draggedIdx; i > thisIdx; i--) {
            allTasks.swapTasksByIndex(allTasks.getActiveTasksIndex()[i],
                    allTasks.getActiveTasksIndex()[i - 1]);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      success = true;
    }

    event.setDropCompleted(success);
    event.consume();
  }

  /**
   * Updates the status of a task (both field and image) when user marks the task as done.
   * The method also updates the UI, such that the update is visible.
   *
    * @param task Task to be updated
   */
  private void updateTaskStatus(Task task) {
    Status newStatus = (task.getStatus().equals(Status.ACTIVE)) ?
            Status.DONE : Status.ACTIVE;
    task.setStatus(newStatus);
    updateStatusImage(newStatus);

    LOGGER.log(Level.INFO, task.getStatus().toString()); //To test the actual status

    try {
      listController.refreshData();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}

