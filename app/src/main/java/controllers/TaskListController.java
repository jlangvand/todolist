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

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Controller for displaying a filtered list of tasks.
 */
public class TaskListController implements ListController, Initializable {

  @FXML private JFXListView<Task> allTasksList;
  @FXML private JFXButton addTaskButton;
  @FXML private Text title;

  private TaskRegistry tasks;
  private MainController mainController;
  private Function<Task, Boolean> filter;

  /** {@inheritDoc} */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    allTasksList.setOnMouseReleased(event -> {
      if (!allTasksList.getSelectionModel().isEmpty()) {
        mainController.loadDisplayTaskView(allTasksList.getSelectionModel()
            .getSelectedItem());
      }
    });
    addTaskButton.setOnMouseReleased(event -> mainController.loadTaskFormView());
  }

  /**
   * Init controller.
   *
   * @param mainController application main controller
   * @param filter         function for filtering tasks
   * @param title          of the view
   * @throws IOException if IO fails
   */
  void initData(MainController mainController, Function<Task, Boolean> filter,
                String title, boolean dragAndDroppable)
      throws IOException {
    this.tasks = mainController.getTaskRegistry();
    this.filter = filter;
    refreshData();
    allTasksList.setCellFactory(c -> new CellController(mainController, this,
        dragAndDroppable));
    this.mainController = mainController;
    this.title.setText(title);
  }

  /** {@inheritDoc} */
  @FXML
  @Override
  public void refreshData() {
    allTasksList.setItems(FXCollections.observableArrayList(
        tasks.getActiveTasks().stream().filter(filter::apply)
            .collect(Collectors.toList())));
    try {
      tasks.save();
    } catch (IOException e) {
      mainController.exceptionHandler(e, "Failed to save task");
    }
  }
}
