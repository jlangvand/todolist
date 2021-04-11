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
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import models.Task;
import models.TaskRegistry;
import utilities.Status;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;

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

  public CellController(ListController listController, TaskRegistry allTasks) {
    this.listController = listController;

    JFXListCell<Task> thisCell = this;

    setOnDragDetected(event -> {
      if (getItem() == null) return;

      ObservableList<Task> items = getListView().getItems();
      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(String.valueOf(items.indexOf(getItem())));
      dragboard.setContent(content);
      dragboard.setDragView(new Image("file:src/main/resources/images/Task_icon.png"));
      event.consume();

    });

    setOnDragOver(event -> {
      if (event.getGestureSource() != thisCell && event.getDragboard().hasString()) {
        event.acceptTransferModes(TransferMode.MOVE);
      }

      event.consume();
    });

    setOnDragExited(event -> {
      if (event.getGestureSource() != thisCell &&
              event.getDragboard().hasString()) {
        setOpacity(1);
      }
    });

    setOnDragDropped(event -> {
      if (getItem() == null) return;

      Dragboard db = event.getDragboard();
      boolean success = false;

      if (db.hasString()) {
        ObservableList<Task> items = getListView().getItems();
        int draggedIdx = Integer.parseInt(db.getString());
        int thisIdx = items.indexOf(getItem());
        try {
          if (thisIdx > draggedIdx) {
            for (int i = draggedIdx; i<thisIdx; i++) {
              allTasks.swapTasksByIndex(i, i+1);
            }

          } else if (draggedIdx > thisIdx){
            for (int i = draggedIdx; i > thisIdx; i--) {
              allTasks.swapTasksByIndex(i, i-1);
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        success = true;
      }

      event.setDropCompleted(success);

      event.consume();

    });

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

      statusButton.setOnMousePressed(event -> {
        Status newStatus = (task.getStatus().equals(Status.ACTIVE)) ? Status.DONE : Status.ACTIVE;
        task.setStatus(newStatus);
        updateStatusImage(newStatus);

        LOGGER.log(Level.INFO, task.getStatus().toString()); //To test the actual status


        try {
          listController.refreshData();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      setText(null);
      setGraphic(taskCellPane); // Inserting all graphics (Description, date, etc.) in root Pane (taskCellPane)
    }
  }

  private void updateStatusImage(Status status){
    if (status.equals(Status.DONE)) {
      cellStatusImage.setImage(new Image("file:src/main/resources" +
              "/images/Done2.png", 48, 48, true, true));
    } else {
      cellStatusImage.setImage(new Image("file:src/main/resources" +
              "/images/not_Done.png", 48, 48, true, true));
    }
  }
}

