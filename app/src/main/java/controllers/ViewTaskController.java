package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.Task;

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

    //It takes a task object as argument and fill the file ViewTask.fxml with info from the task,
    //ViewTask.fxml will be called in AllTaskController class.
    void setTaskInView(Task task) {
        setTaskTitle(task.getTitle());
        setTaskDescription(task.getDescription());
        setTaskStartedDate(task.getStartedDate().toString());
        String p = task.getStatus().toString();
        setTaskPriority(p);

        try {
            if (task.getStatus().toString().equals("DONE")) {
                this.statusImage.setImage(new Image("file:src/main/resources/images/Done2.png", 27, 27, true, true));
            } else if (task.getStatus().toString().equals("ACTIVE")) {
                this.statusImage.setImage(new Image("file:src/main/resources/images/not_Done.png", 30, 30, true, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
