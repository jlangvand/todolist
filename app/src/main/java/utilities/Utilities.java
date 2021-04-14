package utilities;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class Utilities {
  private Utilities() {

  }

  /**
   * Get an FXMLLoaader by filename.
   *
   * @param fxml Filename of fxml, without the file extension (.fxml)
   * @return FXMLLoader object representing the view
   */
  public static FXMLLoader getFXMLLoader(String fxml) {
    return new FXMLLoader(Utilities.class.getResource("/view/" + fxml +
        ".fxml"));
  }

  public static JFXDialog getDialog(StackPane dialogContainer,
                                    Region blurredRegion, String dialogText) {
    BoxBlur blur = new BoxBlur(3, 3, 3);
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXButton okButton = new JFXButton("Ok!");

    okButton.getStylesheets().add(getCSSPath("dialogJFX.css"));

    JFXDialog dialog = new JFXDialog(dialogContainer, dialogLayout,
        JFXDialog.DialogTransition.TOP);

    okButton.setOnAction(event -> dialog.close());
    dialog.setOnDialogClosed(event1 -> blurredRegion.setEffect(null));

    Label label = new Label(dialogText);
    label.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 17pt");
    dialogLayout.setBody(label);
    dialogLayout.setActions(okButton);

    blurredRegion.setEffect(blur);
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
