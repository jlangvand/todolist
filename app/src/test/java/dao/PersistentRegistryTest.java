package dao;

import models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistentRegistryTest {
  static final String FILENAME = "test.bin";

  Task taskA = new Task("Task A");
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
      Task taskB = new Task("Task B");
      List<Task> tasks = pr.read();
      tasks.add(taskB);
      pr.save((ArrayList<Task>) tasks);
      tasks = pr.read();
      assertTrue(tasks.contains(taskB));
      assertTrue(tasks.contains(taskA));
    } catch (IOException e) {
      System.err.println("Failed to open file: " + e.getMessage());
    }
  }
}