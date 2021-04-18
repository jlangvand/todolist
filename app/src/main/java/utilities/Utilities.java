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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
    return new FXMLLoader(Utilities.class.getResource(
        "/view/%s.fxml".formatted(fxml)));
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
    return getResourcePath("/images/%s".formatted(file));
  }

  public static String getCSSPath(String file) {
    return getResourcePath("/css/%s".formatted(file));
  }

  public static String deadlineRemainingTimeString(LocalDate date,
                                                   LocalTime time) {
    final int HIDE_HOURS_IF_DAYS_MORE_THAN = 1;
    final int HIDE_MINUTES_IF_HOURS_MORE_THAN = 10;
    if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now()))
      return "Deadline had passed";
    int days = (int) ChronoUnit.DAYS.between(LocalDate.now(), date);
    int hours = (int) ChronoUnit.HOURS.between(LocalTime.now(), time) % 24;
    int minutes = (int) ChronoUnit.MINUTES.between(LocalTime.now(), time) % 60;
    StringBuilder sb = new StringBuilder();
    if (days > HIDE_HOURS_IF_DAYS_MORE_THAN) {
      sb.append(plural("day", days));
    } else if (days > 0) {
      sb.append("%s and %s".formatted(plural("day", days),
          plural("hour", hours)));
    } else if (hours < HIDE_MINUTES_IF_HOURS_MORE_THAN) {
      if (hours < 1) {
        sb.append(plural("minute", minutes));
      } else {
        sb.append("%s and %s".formatted(plural("hour", hours),
            plural("minute", minutes)));
      }
    } else {
      sb.append(plural("hour", hours));
    }
    sb.append("remaining");
    return sb.toString().strip();
  }

  public static String plural(String str, int n) {
    return "%d %s%s ".formatted(n, str, n == 1 ? "" : "s");
  }

  public static boolean dateIsInRange(LocalDate date, LocalDate from,
                                      LocalDate to) {
    if (to.isBefore(from)) {
      LocalDate temp = to;
      to = from;
      from = temp;
    }
    return date.isBefore(to.plusDays(1)) && date.isAfter(from.minusDays(1));
  }
}
