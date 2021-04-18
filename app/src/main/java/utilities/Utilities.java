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
    // Hiding the implicit constructor
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

  /**
   * Create and display a dialog.
   *
   * <p>Display a dialog and blur the background while the dialog is visible.
   * Returns a reference to the dialog.
   *
   * @param dialogContainer StackPane inside which the dialog will be rendered
   * @param blurredRegion   region to blur
   * @param dialogText      message to display
   * @return a reference to the dialog
   */
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

  /**
   * Get absolute path to a generic resource.
   *
   * @param path relative path
   * @return absolute path
   */
  private static String getResourcePath(String path) {
    return Utilities.class.getResource(path).toString();
  }

  /**
   * Get path to a image resource.
   *
   * @param file name of image
   * @return absolute path
   */
  public static String getImagePath(String file) {
    return getResourcePath("/images/%s".formatted(file));
  }

  /**
   * Get path to a CSS resource
   * @param file name of stylesheet
   * @return absolute path
   */
  public static String getCSSPath(String file) {
    return getResourcePath("/css/%s".formatted(file));
  }

  /**
   * Human readable representation of remaining time.
   *
   * @param date date to calculate
   * @param time time of day
   * @return human readable string
   */
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

  /**
   * Pluralise a word.
   *
   * <p>Takes a String and an int. Returns a String consisting of the
   * provided int, a space, the provided String with an "s" appended if the int
   * is not equal to 1, and a space.
   *
   * <p>Example: plural("apple", 1) will return "1 apple ",
   * plural("green onion", 2) will return "2 green onions ". It does not handle
   * irregular words; plural("sheep", 3) returns "3 sheeps" (sic).
   *
   * @param str word or sentence to pluralise
   * @param n   number of occurrences
   * @return pluralised string ending with a space
   */
  public static String plural(String str, int n) {
    return "%d %s%s ".formatted(n, str, n == 1 ? "" : "s");
  }

  /**
   * Check if a date is within range of two dates, inclusive.
   *
   * @param date date to test
   * @param from range starting from (inclusive)
   * @param to   range ending at (inclusive)
   * @return true if date is within the range
   */
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
