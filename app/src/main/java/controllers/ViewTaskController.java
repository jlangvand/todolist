package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    void setTaskTitle(String title) {
        this.taskTitle.setText(title);
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription.setText(taskDescription);
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline.setText(taskDeadline);
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority.setText(taskPriority);
    }

    public void setTaskStartedDate(String taskStartedDate) {
        this.taskStartedDate.setText(taskStartedDate);
    }
}
