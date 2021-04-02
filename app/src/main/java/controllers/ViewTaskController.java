package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label taskTitle;

    @FXML
    private JFXTextArea taskDescription;

//    @FXML
//    private Label taskDescription;

    @FXML
    private Label taskDeadline;

    @FXML
    private Label taskPriority;

    @FXML
    private Label taskStartedDate;

    @FXML
    private JFXButton editButton;

  @FXML
  private Pane viewPane;

  @FXML
  private ImageView statusImage;

  private Task task;


  /**
   * Method called when user clicks edit button.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void displayEdit(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/EditTask.fxml").toURI().toURL());
    Parent root = fxmlLoader.load();

    EditTaskController editTaskController = fxmlLoader.getController();
    editTaskController.initData(task);

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();

  }

  /**
   * Method called right after object is initialized
   *
   * @param task
   */
  public void initData(Task task) {
    this.task = task;
    taskTitle.setText(task.getTitle());
    taskPriority.setText(task.getPriorityString());
    taskDescription.setText(task.getDescription());
    taskStartedDate.setText(task.getDateAdded().toString());

    //if (task.getStatus().toString().equals("DONE")) {
    //  this.statusImage.setImage(new Image("file:src/main/resources/images/Done2.png", 27, 27, true, true));
    //} else if (task.getStatus().toString().equals("ACTIVE")) {
    //this.statusImage.setImage(new Image("file:src/main/resources/images/not_Done.png", 30, 30, true, true));
    //}

  }
}
