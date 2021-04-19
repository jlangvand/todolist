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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRegistryTest {
  private static final String FILENAME = "TaskRegistryTest.bin";
  TaskRegistry taskRegistry;

  @BeforeEach
  void setUp() throws IOException {
    taskRegistry = new TaskRegistry(FILENAME);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.delete(Path.of(FILENAME));
  }

  @Test
  void getTasksTest() throws IOException {
    Task task1 = new Task();
    Task task2 = new Task();
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertTrue(taskRegistry.contains(task1));
    assertTrue(taskRegistry.contains(task2));
  }

  @Test
  void getDoneTasksTest() throws IOException {
    Task task1 = new Task();
    task1.setStatus(Status.ACTIVE);
    Task task2 = new Task();
    task2.setStatus(Status.DONE);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertFalse(taskRegistry.getDoneTasks().contains(task1));
    assertTrue(taskRegistry.getDoneTasks().contains(task2));
  }

  @Test
  void getActiveTasks() throws IOException {
    Task task1 = new Task();
    task1.setStatus(Status.ACTIVE);
    Task task2 = new Task();
    task2.setStatus(Status.DONE);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertTrue(taskRegistry.getActiveTasks().contains(task1));
    assertFalse(taskRegistry.getActiveTasks().contains(task2));
  }

  @Test
  void addTask() throws IOException {
    Task task = new Task();
    taskRegistry.addTask(task);
    assertTrue(taskRegistry.contains(task));
  }

  @Test
  void removeTask() throws IOException {
    Task task = new Task();
    taskRegistry.addTask(task);
    taskRegistry.removeTask(task);
    assertFalse(taskRegistry.contains(task));
  }
}