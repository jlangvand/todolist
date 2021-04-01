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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Task;
import utilities.Status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

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
        t1.setStartedDate(LocalDate.of(2021, 4, 2));
        //t1.setDeadline(t1.getDeadline()); need to write this method to calculate the deadline..
        t1.setDescription("finish all methods");
        t1.setStatus(Status.DONE);
        //t1.setStatus2("DONE");


        Task t2 = new Task();
        t2.setTitle("Shopping..");
        t2.setStartedDate(LocalDate.of(2021, 4, 3));
        t2.setDescription("Cheese, milk, bread");
        t2.setStatus(Status.DONE);
        //t2.setStatus2("DONE");

        Task t3 = new Task();
        t3.setTitle("3rd task");
        t3.setStartedDate(LocalDate.of(2021, 4, 8));
        t3.setDescription("don't do anything... ");
        t3.setStatus(Status.ACTIVE);
        //t3.setStatus2("ACTIVE");

        tasks = FXCollections.observableArrayList();//we can pass the items(tasks) here too.
        tasks.addAll(t1, t2, t3);

        //pass the observable list to the listView
        allTasksList.setItems(tasks);

        //fetch the customized cell
        allTasksList.setCellFactory(cellController -> new CellController());

        //select a task from the list (the action)
        allTasksList.setOnMouseClicked(event -> {
            Task selectedTask = allTasksList.getSelectionModel().getSelectedItem();
            System.out.print(selectedTask.getDescription());

//            //here we need probably to use threads, because if we changed the
//            //order of the lines, so not all of lines will implemented.
//            allTasksList.setVisible(false);
//            taskViewPane.setVisible(true);
//
//            taskTitle.setText(selectedTask.getTitle());
//            taskStartedDate.setText(selectedTask.getStartedDate().toString());
//            taskDescription.setText(selectedTask.getDescription());
//            taskPriority.setText(selectedTask.getPriority().toString());


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/ViewTask.fxml").toURI().toURL());
                Parent root1 = fxmlLoader.load();
                ViewTaskController viewTaskController = fxmlLoader.getController();

                //fill ViewTask.fxml with the data from the task that the user pressed on.
                viewTaskController.setTaskInView(selectedTask);

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        //press on BACK button to get back to tasks list
        //this action will be used if we choose to call the TaskView as inner pane in allTasks.fxml
        backToTasks.setOnAction(event -> {
            taskViewPane.setVisible(false);
            allTasksList.setVisible(true);

        });

//        addTaskButton.setOnMousePressed(event -> {
//            System.out.println("Button clicked");
//            //add more...
//
//            try {
//                FXMLLoader fxmlLoader2 = new FXMLLoader(new File("src/main/resources/view/NewTask.fxml").toURI().toURL());
//                //fxmlLoader2.setController(this);
//                Parent root12 = fxmlLoader2.load();
//
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root12));
//                stage.show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        });

    }

    @FXML
    void addNewTask(ActionEvent event) {

        System.out.println("Button clicked");
        //add more...

        try {
            //just ViewTask.fxml works!!!! when I try to call/load NewTask.fxml I get nullpointerexception !!!!
            FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/ViewTask.fxml").toURI().toURL());
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
