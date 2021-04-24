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

package todolist.app;

import com.jfoenix.controls.JFXDecorator;
import controllers.MainController;
import javafx.application.Application;
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
    stage.setTitle(TITLE);
    var mainLoader = getFXMLLoader("Main");
    Thread.setDefaultUncaughtExceptionHandler((thread, throwable) ->
        ((MainController) mainLoader.getController())
            .exceptionHandler(throwable, """
                An unknown error occurred!
                                
                Error type: %s""".formatted(throwable.getMessage())));
    try {
      var decorator = new JFXDecorator(stage, mainLoader.load());
      decorator.setCustomMaximize(true);
      decorator.setTitle(TITLE);
      var scene =
          new Scene(decorator, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);
      scene.getStylesheets().add(Utilities.getCSSPath("main.css"));
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      ((MainController) mainLoader.getController()).exceptionHandler(e,
          "IOException in start method when loading JFXDecorator");
      e.printStackTrace();
    }
  }
}
