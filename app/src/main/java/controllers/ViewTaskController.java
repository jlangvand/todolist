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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
    taskDeadline.setText(this.getDeadlineString(task));

    //if (task.getStatus().toString().equals("DONE")) {
    //  this.statusImage.setImage(new Image("file:src/main/resources/images/Done2.png", 27, 27, true, true));
    //} else if (task.getStatus().toString().equals("ACTIVE")) {
    //this.statusImage.setImage(new Image("file:src/main/resources/images/not_Done.png", 30, 30, true, true));
    //}

  }

  /**
   *
   * @return deadline in form: x days, x hours, x minutes.
   */
  public String getDeadlineString(Task task){
    final long SECONDS_PER_HOUR=3600;
    final long SECONDS_PER_MINUTE=60;

    Period period = Period.between(LocalDate.now(), task.getDeadline());
    Duration duration = Duration.between(LocalTime.now(), task.getDeadLineTime());

    //fix the bug that returns negative number of days.
    if (duration.isNegative()) {
      period = period.minusDays(1);
      duration = duration.plusDays(1);
    }

    //get total seconds from now to finished time, and calculate how many hours and minutes.
    long seconds = duration.getSeconds();

    long hours = seconds / SECONDS_PER_HOUR;
    long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);

    return period.getDays()+" days, "+
        hours+" hours, "+
        minutes+" minutes";
  }
}
