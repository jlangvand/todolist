package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import models.Task;
import models.TaskRegistry;
import utilities.Priority;

import java.io.File;
import java.io.IOException;

import java.util.Optional;

public class EditTaskController {

  @FXML
  private BorderPane pane;

  @FXML
  private StackPane stackPane;

  @FXML
  private JFXTextField nameField;

  @FXML
  private JFXTextArea descriptionField;

  @FXML
  private JFXDatePicker deadlineDateField;

  @FXML
  private JFXTimePicker deadlineTimeField;

  @FXML
  private JFXComboBox<Priority> priorityField;

  @FXML
  private JFXTextField categoryField;

  @FXML
  private JFXButton saveEdit;

  @FXML
  private JFXButton cancelEdit;

  private TaskRegistry allTasks;
  private Task task;
  private MainController mainController;

//  @FXML
//  void displayTrashDialog(ActionEvent event) throws IOException {
//    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//    alert.setTitle("Confirmation Dialog");
//    alert.setHeaderText("Delete this task?");
//
//    alert.getDialogPane().setStyle("-fx-background-color: #2c3e50");
//
//    Optional<ButtonType> answer = alert.showAndWait();
//    if(answer.get()==ButtonType.OK){
//      allTasks.removeTask(task);
//      mainController.loadAllTasksView();
//    }
//
//  }

  @FXML
  void displayTrashDialog(ActionEvent event) throws IOException {

    BoxBlur blur = new BoxBlur(3,3,3);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXButton delete = new JFXButton("Delete");
    JFXButton cancel = new JFXButton("Cancel");

    delete.getStylesheets().add(new File("src/main/resources/css/dialogJFX.css").toURI().toString());
    cancel.getStylesheets().add(new File("src/main/resources/css/dialogJFX.css").toURI().toString());
    delete.getStyleClass().add("dialog-button");
    cancel.getStyleClass().add("dialog-button");

    JFXDialog dialog = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.TOP);

    delete.setOnAction(event1 -> {
      try{
        allTasks.removeTask(task);
        mainController.loadAllTasksView();
      }catch (IOException e) {
        e.printStackTrace();
      }
    });

    cancel.setOnAction(event1 -> dialog.close());

    dialog.setOnDialogClosed(event1 -> pane.setEffect(null));

    Label label = new Label("Delete this task?");
    label.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 17pt");
    dialogLayout.setBody(label);
    dialogLayout.setActions(delete,cancel);

    dialog.show();
    pane.setEffect(blur);
  }

  @FXML
  void displayEdit(ActionEvent event) {
    // To be implemented
  }

  public void initData(Task task, TaskRegistry allTasks, MainController mainController) {
    this.task = task;
    this.allTasks = allTasks;
    nameField.setText(task.getTitle());
    descriptionField.setText(task.getDescription());
    deadlineDateField.setValue(task.getDeadline()); //must add variable for time too in task class.
    categoryField.setText(task.getCategory());
    priorityField.setItems(FXCollections.observableArrayList(Priority.values()));
    priorityField.setValue(task.getPriority());
    this.mainController = mainController;
  }

  // TODO(joakilan): Validate input
  @FXML
  public void saveAction() throws IOException {
    task.setTitle(nameField.getText());
    task.setDescription(descriptionField.getText());
    task.setDeadline(deadlineDateField.getValue());
    task.setCategory(categoryField.getText());
    task.setPriority(priorityField.getValue());
    allTasks.save(); //Needed in order to save task changes locally.
    mainController.loadDisplayTaskView(task);
  }

  @FXML
  public void cancelAction() throws IOException {
    mainController.loadDisplayTaskView(task);
  }
}