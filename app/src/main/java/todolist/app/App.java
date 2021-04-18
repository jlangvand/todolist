package todolist.app;

import com.jfoenix.controls.JFXDecorator;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Utilities;

import java.io.IOException;

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
   * @param stage primary stage of the application
   */
  @Override
  public void start(Stage stage) {
    FXMLLoader mainLoader = getFXMLLoader("Main");
    Thread.setDefaultUncaughtExceptionHandler((thread, throwable) ->
        // TODO(joakilan): Find a way to print the original Exception's message
        ((MainController) mainLoader.getController())
            .exceptionHandler(throwable, """
                An unknown error occurred!
                                
                Error type: %s""".formatted(throwable.getMessage())));
    JFXDecorator decorator = null;
    try {
      decorator = new JFXDecorator(stage, mainLoader.load());
    } catch (IOException e) {
      ((MainController) mainLoader.getController()).exceptionHandler(e,
          "IOException in start method when loading JFXDecorator");
      e.printStackTrace();
    }
    decorator.setCustomMaximize(true);
    stage.setTitle(TITLE);
    decorator.setTitle(TITLE);
    Scene scene =
        new Scene(decorator, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);
    scene.getStylesheets().add(Utilities.getCSSPath("main.css"));
    stage.setScene(scene);
    stage.show();
  }
}
