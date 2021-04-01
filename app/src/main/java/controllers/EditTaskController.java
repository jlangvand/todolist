package controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import models.Task;

public class EditTaskController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextArea descriptionField;

    @FXML
    private JFXDatePicker deadlineDateField;

    @FXML
    private JFXTimePicker deadlineTimeField;


    @FXML
    private JFXTextField categoryField;

    @FXML
    private JFXComboBox<?> priorityComboBox;

    @FXML
    private JFXButton saveEdit;

    @FXML
    private JFXButton cancelEdit;

    private Task task;

    @FXML
    void displayTrashDialog(ActionEvent event) {

    }

    @FXML
    void displayEdit(ActionEvent event) {

    }

    public void initData(Task task) {
        this.task = task;

    }

}