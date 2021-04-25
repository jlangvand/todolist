/*
 *     Copyright © 2021 Mona Mahmoud Mousa
 *
 *      Authors (in alphabetical order):
 *      Ask Brandsnes Røsand
 *      Joakim Skogø Langvand
 *      Leonard Sandløkk Schiller
 *      Moaaz Bassam Yanes
 *      Mona Mahmoud Mousa
 *
 *     This file is part of Todolist.
 *
 *     Todolist is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Todolist is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Todolist.  If not, see <https://www.gnu.org/licenses/>.
 */

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

/**
 * Utility class providing project wide convenience methods.
 */
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
    var blur = new BoxBlur(3, 3, 3);
    var dialogLayout = new JFXDialogLayout();
    var okButton = new JFXButton("Ok!");

    okButton.getStylesheets().add(getCSSPath("dialogJFX.css"));

    var dialog = new JFXDialog(dialogContainer, dialogLayout,
        JFXDialog.DialogTransition.TOP);

    okButton.setOnAction(event -> {
      dialog.close();
      blurredRegion.setEffect(null);
    });

    var label = new Label(dialogText);
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
   *
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
    final var HIDE_HOURS_IF_DAYS_MORE_THAN = 1;
    final var HIDE_MINUTES_IF_HOURS_MORE_THAN = 10;
    if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now()))
      return "Deadline had passed";
    int days = (int) ChronoUnit.DAYS.between(LocalDate.now(), date);
    int hours = (int) ChronoUnit.HOURS.between(LocalTime.now(), time) % 24;
    int minutes = (int) ChronoUnit.MINUTES.between(LocalTime.now(), time) % 60;
    var sb = new StringBuilder();
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
      var temp = to;
      to = from;
      from = temp;
    }
    return date.isBefore(to.plusDays(1)) && date.isAfter(from.minusDays(1));
  }
}
