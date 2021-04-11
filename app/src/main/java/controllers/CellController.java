package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Task;
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

  public CellController(ListController listController) {
    this.listController = listController;
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

