package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import models.TaskRegistry;
import utilities.Priority;
import utilities.Status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AllTasksController {

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

    private TaskRegistry allTasks;
    private ObservableList<Task> tasks;
    private NavBarController navBarController;


    @FXML
    void initialize() {
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
      navBarController.loadNewTaskView(allTasks);
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
      if (!allTasksList.getSelectionModel().isEmpty()) {
          Task selectedTask = allTasksList.getSelectionModel().getSelectedItem();
/*          allTasksList.getSelectionModel().clearSelection();

          FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/ViewTask.fxml").toURI().toURL());
          Parent root = fxmlLoader.load();
          ViewTaskController viewTaskController = fxmlLoader.getController();
          viewTaskController.initData(selectedTask);

          Stage stage = new Stage();
          JFXDecorator decorator = new JFXDecorator(stage, root);
          decorator.setCustomMaximize(true);
          stage.setScene(new Scene(decorator));
          stage.initModality(Modality.APPLICATION_MODAL);
          stage.show();*/
          navBarController.loadDisplayTaskView(selectedTask);
      }
  }

  void initData(TaskRegistry taskRegistry, NavBarController navBarController) {
    allTasks = taskRegistry;
    tasks = allTasks.getObservableTasks();
    allTasksList.setItems(tasks);
    allTasksList.setCellFactory(cellController -> new CellController());
    this.navBarController = navBarController;
  }

  @FXML
  void refreshData() {
    allTasksList.refresh();
  }


}
