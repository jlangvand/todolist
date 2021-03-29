package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;

public class AllTasksController2 {

    ObservableList<JFXButton> tasks;

    @FXML
    private JFXListView<JFXButton> allTasksList;


    @FXML
    private JFXButton addTaskButton;

    @FXML
    void initialize() {

        //make 3 JFXButton objects for test
        JFXButton b1 = new JFXButton("TEST 1");
        JFXButton b2 = new JFXButton("TEST 2");
        JFXButton b3 = new JFXButton("TEST 3");


        tasks = FXCollections.observableArrayList();//we can pass the items(tasks) here too.
        tasks.addAll(b1, b2, b3);
        tasks.forEach(b -> {
            b.setStyle("-fx-background-color: #34495e");
            b.setTextFill(Paint.valueOf("#FFFFFF"));
            //b.setRipplerFill(Paint.valueOf("#FFFFFF"));
            b.setButtonType(JFXButton.ButtonType.RAISED);
        });

        //pass the observable list to the listView
        allTasksList.setItems(tasks);


    }
}
