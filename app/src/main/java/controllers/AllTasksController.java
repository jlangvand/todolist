package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Task;
import utilities.Priority;
import utilities.Status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AllTasksController {

    ObservableList<Task> tasks;

    @FXML
    private JFXListView<Task> allTasksList;

    @FXML
    private JFXButton addTaskButton;

    @FXML
    private BorderPane taskViewPane;

    @FXML
    private JFXTextArea taskDescription;

    @FXML
    private JFXButton backToTasks;

    @FXML
    private Label taskTitle;


    @FXML
    private Label taskDeadline;

    @FXML
    private Label taskPriority;

    @FXML
    private Label taskStartedDate;

    @FXML
    private JFXButton editButton;


    @FXML
    void initialize() {

        ///make 3 Task objects for test///
        Task t1 = new Task();
        t1.setTitle("Methods");
        //t1.setStartedDate(LocalDate.of(2021, 4, 2));
        //t1.setDeadline(t1.getDeadline()); need to write this method to calculate the deadline..
        t1.setDescription("finish all methods");
        t1.setStatus(Status.DONE);
        t1.setPriority(Priority.HIGH);
        t1.setDeadLineTime(LocalTime.of(17,30));
        t1.setDeadline(LocalDate.of(2021, 4, 2));



        Task t2 = new Task();
        t2.setTitle("Shopping..");
        //t2.setStartedDate(LocalDate.of(2021, 4, 3));
        t2.setDescription("Cheese, milk, bread");
        t2.setStatus(Status.DONE);
        t2.setPriority(Priority.MEDIUM);
        t2.setDeadLineTime(LocalTime.of(17,42));
        t2.setDeadline(LocalDate.of(2021, 4, 3));




      Task t3 = new Task();
        t3.setTitle("3rd task");
        //t3.setStartedDate(LocalDate.of(2021, 4, 8));
        t3.setDescription("don't do anything... ");
        t3.setStatus(Status.ACTIVE);
        t3.setDeadLineTime(LocalTime.of(23,00));
        t3.setDeadline(LocalDate.of(2021, 4, 9));



      tasks = FXCollections.observableArrayList();//we can pass the items(tasks) here too.
        tasks.addAll(t1, t2, t3);

      //pass the observable list to the listView
      allTasksList.setItems(tasks);

      //fetch the customized cell
      allTasksList.setCellFactory(cellController -> new CellController());


      //press on BACK button to get back to tasks list
      //this action will be used if we choose to call the TaskView as inner pane in allTasks.fxml
//        backToTasks.setOnAction(event -> {
//            taskViewPane.setVisible(false);
//            allTasksList.setVisible(true);
//
//        });

    }

  /**
   * Method called when user clicks on add button.
   *
   * @param event
   * @throws IOException, the exception will be handled in the method.
   */
  @FXML
  void displayNewTask(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/NewTask.fxml").toURI().toURL());
      Parent root = fxmlLoader.load();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Method called when user clicks on a task.
   *
   * @param event
   * @throws IOException
   */
  @FXML
  void displayTask(MouseEvent event) throws IOException {
    Task selectedTask = allTasksList.getSelectionModel().getSelectedItem();

    FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/ViewTask.fxml").toURI().toURL());
    Parent root = fxmlLoader.load();
    ViewTaskController viewTaskController = fxmlLoader.getController();
    viewTaskController.initData(selectedTask);

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }


}
