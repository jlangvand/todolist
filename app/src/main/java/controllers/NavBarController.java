package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {
    @FXML
    BorderPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Temporary, to test loading of allTasks as center of navigationBar.
        try {
            pane.setCenter(FXMLLoader.load(new File("src/main/resources/view/allTasks.fxml").toURI().toURL()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
