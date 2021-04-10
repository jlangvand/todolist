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

  public CellController() {}
  public CellController(TaskRegistry allTasks) {

    JFXListCell<Task> thisCell = this;

    setOnDragDetected(event -> {
      if (getItem() == null) return;

      ObservableList<Task> items = getListView().getItems();
      Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(String.valueOf(items.indexOf(getItem())));
      dragboard.setContent(content);
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
          allTasks.swapTasksByIndex(draggedIdx,thisIdx);
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

        //This class is the controller for the TaskCell.fxml
        fxmlLoader.setController(this);
        try {
          fxmlLoader.load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      //Fill the cell with info from the task
      cellTitle.setText(task.getTitle());
      cellDate.setText(task.getDateAdded().toString());

      try {
        statusButton.setOnMousePressed(event -> {
          if (task.getStatus().equals(Status.ACTIVE)) {
            cellStatusImage.setImage(new Image("file:src/main/resources" +
                "/images/Done2.png", 48, 48, true, true));
            task.setStatus(Status.DONE);
          } else if (task.getStatus().equals(Status.DONE)) {
            cellStatusImage.setImage(new Image("file:src/main/resources" +
                "/images/not_Done.png", 48, 48, true, true));
            task.setStatus(Status.ACTIVE);
          }
          //to test the actual status!
          LOGGER.log(Level.INFO, task.getStatus().toString());
        });

      } catch (Exception e) {
        e.printStackTrace();
      }


      setText(null);

      /*
      Everything is setup well now and need to insert all these graphics
      (cellDescription,cellDate...) in the root pane(taskCellPane)
      */
      setGraphic(taskCellPane);
    }
  }
}

