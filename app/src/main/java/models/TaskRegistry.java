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

package models;

import dao.PersistentRegistry;
import utilities.Status;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Persistent, ordered list of Task objects.
 *
 * <p>Provides methods for filtering and reordering tasks. Upon creation, it
 * loads tasks from a file if it exists, else it creates a new file. Tasks are
 * written to or removed from the file as they are added to or removed from the
 * list.
 */
public class TaskRegistry extends ArrayList<Task> implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
  private final transient PersistentRegistry fileHandle;

  /**
   * Create a new TaskRegistry with a custom save file.
   *
   * @param fileName file path to use for saving
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry(String fileName) throws IOException {
    fileHandle = new PersistentRegistry(fileName);
    addAll(fileHandle.read());
  }

  /**
   * Create a TaskRegistry using a default save file.
   *
   * @throws IOException throws exception if file IO fails
   */
  public TaskRegistry() throws IOException {
    this.fileHandle = new PersistentRegistry();
    addAll(fileHandle.read());
  }

  /**
   * Save the current list to file.
   *
   * @throws IOException if file IO fails
   */
  public void save() throws IOException {
    fileHandle.save(this);
  }

  /**
   * @param status enum Status
   * @return matching tasks as an ArrayList
   */
  public List<Task> getTasksByStatus(Status status) {
    return stream().filter(t -> t.getStatus().equals(status))
        .collect(Collectors.toList());
  }

  /**
   * Get done tasks.
   *
   * @return list of done tasks
   */
  public List<Task> getDoneTasks() {
    return getTasksByStatus(Status.DONE);
  }

  /**
   * Get active tasks.
   *
   * @return list of active tasks
   */
  public List<Task> getActiveTasks() {
    return getTasksByStatus(Status.ACTIVE);
  }

  /**
   * Get array of indexes of active tasks in increasing order.
   *
   * @return int array of indexes
   */
  public int[] getActiveTasksIndex() {
    var indexes = new int[getActiveTasks().size()];
    var c = 0;
    for (var i = 0; i < size(); i++) {
      if (get(i).getStatus() == Status.ACTIVE) {
        indexes[c] = i;
        c++;
      }
    }
    return indexes;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public String toString() {
    return "TaskRegistry{" +
        "tasks=" + this +
        '}';
  }

  /**
   * Adds a task to the registry, and saves the changes using fileHandler.
   *
   * @param task the task to be added
   * @throws IOException throws exception if file IO fails
   */
  public void addTask(Task task) throws IOException {
    add(0, task);
    save();
  }

  /**
   * Removes a task from the registry, and saves the changes using fileHandler.
   *
   * @param task the task to be removed
   * @throws IOException throws exception if file IO fails
   */
  public void removeTask(Task task) throws IOException {
    remove(task);
    save();
  }

  /**
   * Swaps the index of the task of index a with the task of index b.
   *
   * @param a Index of task A
   * @param b Index of task B
   * @throws IOException throws exception if file IO fails
   */
  public void swapTasksByIndex(int a, int b) throws IOException {
    var taskA = get(a);
    var taskB = get(b);
    set(a, taskB);
    set(b, taskA);
    save();
  }

  /**
   * {@inheritDoc}
   *
   * @param object {@inheritDoc}
   * @return true {@inheritDoc}
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    var registry = (TaskRegistry) object;
    return fileHandle.equals(registry.fileHandle);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fileHandle);
  }
}
