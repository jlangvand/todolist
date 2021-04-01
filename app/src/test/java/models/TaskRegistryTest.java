package models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRegistryTest {
  private static final String FILENAME = "TaskRegistryTest.bin";

  @BeforeEach
  void setUp() throws IOException {

  }

  @AfterAll
  static void cleanup() throws IOException {
    Files.delete(Path.of(FILENAME));
  }

  @Test
  void getTasksTest() throws IOException {
    TaskRegistry taskRegistry = new TaskRegistry(FILENAME);
    Task task1 = new Task("Meeting a friend");
    Task task2 = new Task("Reading a book");
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertTrue(taskRegistry.getTasks().contains(task1));
    assertTrue(taskRegistry.getTasks().contains(task2));
  }

  @Test
  void getTasksByStatus() {
  }

  @Test
  void getTasksByPriority() {
  }

  @Test
  void getTasksByDate() {
  }

  @Test
  void testGetTasksByDate() {
  }

  @Test
  void getHighPriorityTasks() {
  }

  @Test
  void getMediumPriorityTasks() {
  }

  @Test
  void getLowPriorityTasks() {
  }

  @Test
  void getDoneTasks() {
  }

  @Test
  void getActiveTasks() {
  }

  @Test
  void addTask() throws IOException {


  }

  @Test
  void removeTask() {
  }
}