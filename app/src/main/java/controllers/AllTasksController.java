package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Task;

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
//        t1.setDescription("finish all methods");
//        t1.setStatus(Status.ACTIVE);


        Task t2 = new Task();
        t2.setTitle("Shopping..");
        t2.setStartedDate(LocalDate.of(2021, 4, 3));
//        t2.setDescription("Cheese, milk, bread");
//        t2.setStatus(Status.ACTIVE);

        Task t3 = new Task();
        t3.setTitle("3rd task");
        t3.setStartedDate(LocalDate.of(2021, 4, 8));
//        t3.setDescription("don't do anything... ");
//        t3.setStatus(Status.ACTIVE);

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
                ViewTaskController viewTaskController = (ViewTaskController) fxmlLoader.getController();

                viewTaskController.setTaskTitle(selectedTask.getTitle());
                //viewTaskController.setTaskPriority(selectedTask.getPriority().toString());
                viewTaskController.setTaskDescription(selectedTask.getDescription());
                viewTaskController.setTaskStartedDate(selectedTask.getStartedDate().toString());


                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        //press on BACK button to get back to tasks list
        backToTasks.setOnAction(event -> {
            taskViewPane.setVisible(false);
            allTasksList.setVisible(true);

        });

//        addTaskButton.setOnMouseClicked(event -> {
//            System.out.println("Button clicked");
//            //add more...
//
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/view/NewTask.fxml").toURI().toURL());
//                Parent root1 = fxmlLoader.load();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root1));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });

    }


}
