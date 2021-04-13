package utilities;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class Utilities {
  private Utilities() {

  }

  /**
   * Loads an fxml file from the path app/src/main/resources/view
   *
   * @param fxml Filename of fxml, without the file extension (.fxml)
   * @return Parent object representing the view
   */
  public static FXMLLoader getFXMLLoader(String fxml) {
    return new FXMLLoader(Utilities.class.getResource("/view/" + fxml +
        ".fxml"));
  }

  public static JFXDialog getDialog(StackPane stackPane, Pane mainPane,
                                    String dialogText) {
    BoxBlur blur = new BoxBlur(3, 3, 3);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXButton okButton = new JFXButton("Ok!");

    okButton.getStylesheets().add(Utilities.class.getResource("/css/dialogJFX" +
        ".css").toString());

    JFXDialog dialog = new JFXDialog(stackPane, dialogLayout,
        JFXDialog.DialogTransition.TOP);

    okButton.setOnAction(event -> dialog.close());
    dialog.setOnDialogClosed(event1 -> mainPane.setEffect(null));

    Label label = new Label(dialogText);
    label.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 17pt");
    dialogLayout.setBody(label);
    dialogLayout.setActions(okButton);

    mainPane.setEffect(blur);
    dialog.show();
    return dialog;
  }

  private static String getResourcePath(String path) {
    return Utilities.class.getResource(path).toString();
  }

  public static String getImagePath(String file) {
    return getResourcePath("/images/" + file);
  }

  public static String getCSSPath(String file) {
    return getResourcePath("/css/" + file);
  }

  public static boolean dateIsInRange(LocalDate date, LocalDate from,
                                      LocalDate to) {
    return date.isBefore(to.plusDays(1)) && date.isAfter(from.minusDays(1));
  }
}
