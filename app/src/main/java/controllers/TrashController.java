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

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import models.Task;
import models.TaskRegistry;

import java.io.IOException;

public class TrashController implements ListController {

  @FXML private JFXListView<Task> allTasksList;

  private TaskRegistry tasks;
  private MainController mainController;

  void initData(MainController mainController) {
    this.tasks = mainController.getTaskRegistry();
    refreshData();
    allTasksList.setCellFactory(c -> new CellController(mainController, this,
        false));
    this.mainController = mainController;
    allTasksList.setOnMouseReleased(event -> {
      if (!allTasksList.getSelectionModel().isEmpty()) {
        mainController.loadDisplayTaskView(
            allTasksList.getSelectionModel().getSelectedItem());
      }
    });
  }

  @FXML
  @Override
  public void refreshData() {
    allTasksList.setItems(FXCollections.observableArrayList(tasks.getDoneTasks()));
    try {
      tasks.save();
    } catch (IOException e) {
      mainController.exceptionHandler(e, "Failed to save task");
    }
  }
}
