package utilities;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;

public class Utilities {
  private Utilities() {

  }

  /**
   * Loads an fxml file from the path app/src/main/resources/view
   *
   * @param fxml Filename of fxml, without the file extension (.fxml)
   * @return Parent object representing the view
   * @throws IOException exception is thrown if file I/O fails
   */
  public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
    return new FXMLLoader(
        new File("src/main/resources/view/" + fxml + ".fxml")
            .toURI().toURL());
  }

}
