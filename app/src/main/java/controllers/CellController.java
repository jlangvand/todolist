package controllers;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Task;

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
    private Label cellDescription;

    @FXML
    private ImageView cellStatus;

    @FXML
    private Label cellDate;


    private FXMLLoader fxmlLoader;

    @FXML
    void initialize() {
    }

    //just test
    @FXML
    void changeStatus(MouseEvent event) {
        Image done = new Image("src/main/resources/images/Done.png", 56, 60, false, true);

        cellStatus.setImage(done);

    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                //fxmlLoader = new FXMLLoader(getClass().getResource("/view/taskCell.fxml"));
                //fxmlLoader = new FXMLLoader();
                //fxmlLoader.setLocation(getClass().getResource("app/src/main/resources/view/taskCell.fxml"));
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
            cellDescription.setText(task.getDescription());
            cellDate.setText(task.getDeadline().toString());

            setText(null);

            //Everything is setup well now and need to insert all these graphics(cellDescription,cellDate...) in the root pane(taskCellPane)
            setGraphic(taskCellPane);
        }

    }
}

