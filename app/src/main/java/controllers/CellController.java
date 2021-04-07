package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Task;
import utilities.Status;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CellController extends JFXListCell<Task> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane taskCellPane;

    @FXML
    private Label cellTitle;

    @FXML
    private ImageView cellStatusImage;

    @FXML
    private Label cellDate;

    @FXML
    private JFXButton statusButton;


    private FXMLLoader fxmlLoader;


    @FXML
    void initialize() {
    }


    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {

                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/taskCell.fxml"));


                //This class is the controller for the taskCell.fxml
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Fill the cell with info from the task

            cellTitle.setText(task.getTitle());
            cellDate.setText(task.getDateAdded().toString());


            try {
                //cellStatusImage = new ImageView();
                statusButton.setOnMousePressed(event -> {
                  if (task.getStatus().equals(Status.ACTIVE)) {
                    cellStatusImage.setImage(new Image("file:src/main/resources/images/Done2.png", 48, 48, true, true));
                    task.setStatus(Status.DONE);
                  } else if (task.getStatus().equals(Status.DONE)) {
                    cellStatusImage.setImage(new Image("file:src/main/resources/images/not_Done.png", 48, 48, true, true));
                    task.setStatus(Status.ACTIVE);
                  }
                  System.out.println(task.getStatus().toString());//to test the actual status!
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


            setText(null);

            //Everything is setup well now and need to insert all these graphics(cellDescription,cellDate...) in the root pane(taskCellPane)
            setGraphic(taskCellPane);
        }

    }
}

