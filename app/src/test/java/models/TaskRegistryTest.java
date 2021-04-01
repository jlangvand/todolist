package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRegistryTest {
  private TaskRegistry taskRegistry;


  @BeforeEach
  void setUp() throws IOException {

  }

  @Test
  void getTasksTest() throws IOException {
    taskRegistry = new TaskRegistry("test2.bin");
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