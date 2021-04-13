package todolist.app;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static utilities.Utilities.getFXMLLoader;

/**
 * Application main class.
 */
public class App extends Application {
  private static final int DEFAULT_STAGE_WIDTH = 1000;
  private static final int DEFAULT_STAGE_HEIGHT = 650;
  private static final String TITLE = "Todolist";

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
    JFXDecorator decorator =
        new JFXDecorator(stage, getFXMLLoader("Main_MVP").load());
    decorator.setCustomMaximize(true);
    stage.setTitle(TITLE);
    decorator.setTitle(TITLE);
    URI uri = new File("src/main/resources/css/main.css").toURI();
    Scene scene =
        new Scene(decorator, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);
    scene.getStylesheets().add(uri.toString());
    stage.setScene(scene);
    stage.show();
  }
}
