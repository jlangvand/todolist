/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todolist.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class App extends Application {
  //Constants
  private static final int defaultStageWidth = 1000;
  private static final int defaultStageHeight = 650;


  public static void main(String[] args) { launch(args); }


  /**
   * This method is called when the javaFX application is started.
   * @param stage Primary stage of the application
   * @throws IOException
   */
  @Override
  public void start(Stage stage) throws IOException{
    Scene scene = new Scene(loadFXML("navigationBar"), defaultStageWidth, defaultStageHeight);
    stage.setScene(scene);
    stage.show();
  }


  /**
   * This method loads an fxml from the path app/src/main/resources/view
   * @param fxml Filename of fxml, without the file extension (.fxml)
   * @return Parent object representing the view
   * @throws IOException
   */
  private static Parent loadFXML(String fxml) throws IOException {
    URL url = new File("app/src/main/resources/view/" + fxml + ".fxml").toURI().toURL();
    return FXMLLoader.load(url);
  }

}
