package todolist.app;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Application main class.
 */
public class App extends Application {
  private static final int DEFAULT_STAGE_WIDTH = 1000;
  private static final int DEFAULT_STAGE_HEIGHT = 650;

  /**
   * Main method launches JavaFX application.
   *
   * @param args launch arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Called when the javaFX application is started.
   *
   * @param stage Primary stage of the application
   * @throws IOException exception is thrown if file I/O fails
   */
  @Override
  public void start(Stage stage) throws IOException {
    JFXDecorator decorator = new JFXDecorator(stage, loadFXML("navigationBar"));
    decorator.setCustomMaximize(true);
    Scene scene = new Scene(decorator, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Loads an fxml file from the path app/src/main/resources/view
   *
   * @param fxml Filename of fxml, without the file extension (.fxml)
   * @return Parent object representing the view
   * @throws IOException exception is thrown if file I/O fails
   */
  private static Parent loadFXML(String fxml) throws IOException {
    URL url = new File("src/main/resources/view/" + fxml + ".fxml").toURI().toURL();
    return FXMLLoader.load(url);
  }
}
