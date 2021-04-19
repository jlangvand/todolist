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

/**
 * Utility class holding project wide constants.
 */
public class Constants {

  /** Task list FXML name. */
  public static final String ALL_TASKS_FXML_NAME = "AllTasks";

  /** Done tasks FXML name */
  public static final String DONE_TASKS_FXML_NAME = "DoneTasks";

  /** Task form FXML name */
  public static final String TASK_FORM_FXML_NAME = "TaskForm";

  /** View task FXML name */
  public static final String VIEW_TASK_FXML_NAME = "ViewTask";

  /** Default file name for persistent registry */
  public static final String DEFAULT_FILE_NAME = "registry.todo";

  private Constants() {
    // Hiding the implicit constructor
  }
}
