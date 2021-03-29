package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import models.Task;

import java.time.LocalDate;

public class AllTasksController {

    ObservableList<Task> tasks;

    @FXML
    private JFXListView<Task> allTasksList;

    @FXML
    private JFXButton addTaskButton;

    @FXML
    void initialize() {

        //make 3 Task objects for test
        Task t1 = new Task();
        t1.setDescription("Do the addTask method");
        t1.setDeadline(LocalDate.of(2021, 4, 2));
        Task t2 = new Task();
        t2.setDescription("Do the markAsDone method");
        t2.setDeadline(LocalDate.of(2021, 4, 3));
        Task t3 = new Task();
        t3.setDescription("Make the code");
        t3.setDeadline(LocalDate.of(2021, 4, 8));

        tasks = FXCollections.observableArrayList();//we can pass the items(tasks) here too.
        tasks.addAll(t1, t2, t3);

        //pass the observable list to the listView
        allTasksList.setItems(tasks);

        //fetch the customized cell
        allTasksList.setCellFactory(cellController -> new CellController());

    }


}
