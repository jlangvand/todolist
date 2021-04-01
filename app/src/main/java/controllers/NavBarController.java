package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {
    @FXML
    BorderPane pane;

    @FXML
    private JFXButton allTasksButton;

    @FXML
    private JFXButton highPriorityButton;

    @FXML
    private JFXButton mediumPriorityButton;

    @FXML
    private JFXButton lowPriorityButton;

    @FXML
    private JFXButton trashButton;

    /**
     * This button is called when the navBarController is initialized.
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateCenter("allTasks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is called when allTasksButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayAllTasks(MouseEvent event) throws IOException {
        updateCenter("allTasks");

    }


    /**
     * This method is called when highPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayHighPriorityTasks(MouseEvent event) {

    }


    /**
     * This method is called when mediumPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayMediumPriorityTasks(MouseEvent event) {

    }


    /**
     * This method is called when lowPriorityButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayLowPriorityTasks(MouseEvent event) {

    }


    /**
     * This method is called when trashButton is clicked.
     *
     * @param event
     */
    @FXML
    void displayTrash(MouseEvent event) {

    }

    private void updateCenter(String fxml) throws IOException {
        URL url = new File("src/main/resources/view/" + fxml + ".fxml").toURI().toURL();
        pane.setCenter(FXMLLoader.load(url));
    }

}
