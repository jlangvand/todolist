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

package dao;
import models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PersistentRegistryTest {
  static final String FILENAME = "test.bin";
  Task taskA = new Task();

  @BeforeEach
  void setUp() {
    try {
      PersistentRegistry pr = new PersistentRegistry(FILENAME);
      ArrayList<Task> tasks = new ArrayList<>();
      tasks.add(taskA);
      pr.save(tasks);
    } catch (IOException e) {
      System.err.println("Failed to open file: " + e.getMessage());
    }
  }

  @Test
  void testRead() {
    try {
      PersistentRegistry pr = new PersistentRegistry(FILENAME);
      List<Task> tasks = pr.read();
      assertTrue(tasks.contains(taskA));
    } catch (IOException e) {
      System.err.println("Failed to open file: " + e.getMessage());
    }
  }

  @Test
  void testSave() {
    try {
      PersistentRegistry pr = new PersistentRegistry(FILENAME);
      Task taskB = new Task();
      List<Task> tasks = pr.read();
      tasks.add(taskB);
      pr.save(tasks);
      tasks = pr.read();
      assertTrue(tasks.contains(taskB));
      assertTrue(tasks.contains(taskA));
    } catch (IOException e) {
      System.err.println("Failed to open file: " + e.getMessage());
    }
  }
}
