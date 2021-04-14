package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Task;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ResourceBundle;

import static utilities.Utilities.getDialog;

public class ViewTaskController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private Label taskTitle;

  @FXML
  private Label taskDescription;

  @FXML
  private Label taskDeadline;

  @FXML
  private Label taskPriority;

  @FXML
  private Label taskCategory;

  @FXML
  private Label taskStartedDate;

  @FXML
  private JFXButton editButton;

  @FXML
  private Pane viewPane;

  @FXML
  private StackPane stackPane;

  @FXML private BorderPane mainPane;

  @FXML
  private ImageView statusImage;

  private Task task;
  private MainController mainController;

  /**
   * Method called when user clicks edit button.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void displayEdit(ActionEvent event) throws IOException {
    mainController.loadEditTaskView(task);
  }

  /**
   * Method called right after object is initialized
   *
   * @param task
   */
  public void initData(Task task, MainController mainController) {
    this.task = task;
    taskTitle.setText(task.getTitle());
    taskPriority.setText(task.getPriorityString());
    taskCategory.setText(task.getCategory());
    taskDescription.setText(task.getDescription());
    taskStartedDate.setText(task.getDateAdded().toString());
    taskDeadline.setText(this.getDeadlineString(task));
    this.mainController = mainController;
  }

  @FXML
  public void refreshData() {
    initData(task, mainController);
  }

  @FXML
  public void backEvent() throws IOException {
    mainController.displayAllTasks(null);
  }

  @FXML
  public void deleteAction() {
    BoxBlur blur = new BoxBlur(3, 3, 3);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXButton delete = new JFXButton("Delete");
    JFXButton cancel = new JFXButton("Cancel");
    String styleSheetPath = ViewTaskController.class.getResource("/css" +
        "/dialogJFX.css").toString();
    delete.getStylesheets().add(styleSheetPath);
    cancel.getStylesheets().add(styleSheetPath);

    JFXDialog dialog = new JFXDialog(stackPane, dialogLayout,
        JFXDialog.DialogTransition.TOP);

    delete.setOnAction(event1 -> {
      try {
        mainController.getTaskRegistry().removeTask(task);

        JFXDialog deletedDialog = getDialog(stackPane, mainPane, "The " +
            "task has been deleted successfully");
        deletedDialog.setOnDialogClosed(event2 -> {
          try {
            mainController.displayAllTasks(null);
          } catch (IOException e) {
            e.printStackTrace();
          }
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

  /**
   * @return deadline in form: x days, x hours, x minutes.
   */
  public String getDeadlineString(Task task) {
    final long SECONDS_PER_HOUR = 3600;
    final long SECONDS_PER_MINUTE = 60;
    String deadlineString;

    //if deadline date is today and the time passed  OR  the deadline date
    // passed(before today)
    if ((task.getDeadline().isEqual(LocalDate.now()) && task.getDeadLineTime().isBefore(LocalTime.now()))
        || (task.getDeadline().isBefore(LocalDate.now()))) {
      deadlineString =
          "The deadline has passed" + "   (" + task.getDeadline() + " " + task.getDeadLineTime() + ")";
    } else {
      Period period = Period.between(LocalDate.now(), task.getDeadline());
      Duration duration = Duration.between(LocalTime.now(),
          task.getDeadLineTime());

      //fix the bug that returns negative number of days.
      if (duration.isNegative()) {
        period = period.minusDays(1);
        duration = duration.plusDays(1);
      }

      //get total seconds from now to finished time, and calculate how many
      // hours and minutes.
      long seconds = duration.getSeconds();

      long hours = seconds / SECONDS_PER_HOUR;
      long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);

      //return days,h,m, with deadline date and time
      deadlineString = period.getDays() + " days, " +
          hours + " hours, " +
          minutes + " min until deadline." +
          " (" + task.getDeadline() + ", " + task.getDeadLineTime().getHour() + ":" + task.getDeadLineTime().getMinute() + ")";
    }
    return deadlineString;
  }
}