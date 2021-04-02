package models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Priority;
import utilities.Status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

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
    Task task1 = new Task("Get the first task");
    Task task2 = new Task("Get the second task");
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertTrue(taskRegistry.getTasks().contains(task1));
    assertTrue(taskRegistry.getTasks().contains(task2));
  }

  @Test
  void getTasksByDate() throws IOException {
    Task task1 = new Task("Get A task by deadline date");
    task1.setDeadline(LocalDate.now().plusDays(3));
    Task task2=new Task("Get task B by deadline date ");
    task2.setDeadline(LocalDate.now().plusDays(5));
    assertTrue(taskRegistry.getTasksByDate(LocalDate.now().plusDays(3)).contains(task1));
    assertFalse(taskRegistry.getTasksByDate(LocalDate.now().plusDays(3)).contains(task2));
  }

  @Test
  void testGetTasksByDate() throws IOException {
    Task task1 = new Task("Get a task A by start and finished date");
    task1.setDateAdded(LocalDate.now().plusDays(3));
    task1.setFinishedDate(LocalDate.now().plusWeeks(1));
    Task task2=new Task("Get task by B deadline date ");
    task2.setDateAdded(LocalDate.now().minusWeeks(3));
    task2.setFinishedDate(LocalDate.now().minusWeeks(1));
    assertTrue(taskRegistry.getTasksByDate(LocalDate.now().plusDays(4),LocalDate.now().plusDays(7)).contains(task1));
    assertFalse(taskRegistry.getTasksByDate(LocalDate.now().plusDays(4),LocalDate.now().plusDays(7)).contains(task2));

  }

  @Test
  void getHighPriorityTasks() throws IOException {
    Task task1 = new Task("Task has a high priority");
    task1.setPriority(Priority.HIGH);
    Task task2=new Task("Task has a medium priority");
    task2.setPriority(Priority.MEDIUM);
    Task task3=new Task("Task has a low priority");
    task3.setPriority(Priority.LOW);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    taskRegistry.addTask(task3);
    assertTrue(taskRegistry.getHighPriorityTasks().contains(task1));
    assertFalse(taskRegistry.getHighPriorityTasks().contains(task2));
    assertFalse(taskRegistry.getHighPriorityTasks().contains(task3));
  }

  @Test
  void getMediumPriorityTasks() throws IOException {
    Task task1 = new Task("Task has a high priority");
    task1.setPriority(Priority.HIGH);
    Task task2=new Task("Task has a medium priority");
    task2.setPriority(Priority.MEDIUM);
    Task task3=new Task("Task has a low priority");
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    taskRegistry.addTask(task3);
    assertFalse(taskRegistry.getMediumPriorityTasks().contains(task1));
    assertTrue(taskRegistry.getMediumPriorityTasks().contains(task2));
    assertFalse(taskRegistry.getMediumPriorityTasks().contains(task3));
  }

  @Test
  void getLowPriorityTasks() throws IOException {
    Task task1 = new Task("Task has a high priority");
    task1.setPriority(Priority.HIGH);
    Task task2=new Task("Task has a medium priority");
    task2.setPriority(Priority.MEDIUM);
    Task task3=new Task("Task has a low priority");
    task3.setPriority(Priority.LOW);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    taskRegistry.addTask(task3);
    assertFalse(taskRegistry.getLowPriorityTasks().contains(task1));
    assertFalse(taskRegistry.getLowPriorityTasks().contains(task2));
    assertTrue(taskRegistry.getLowPriorityTasks().contains(task3));
  }

  @Test
  void getDoneTasksTest() throws IOException {
    Task task1 = new Task("Task is ACTIVE");
    task1.setStatus(Status.ACTIVE);
    Task task2=new Task("Task is DONE");
    task2.setStatus(Status.DONE);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertFalse(taskRegistry.getDoneTasks().contains(task1));
    assertTrue(taskRegistry.getDoneTasks().contains(task2));
  }

  @Test
  void getActiveTasks() throws IOException {
    Task task1 = new Task("Task is ACTIVE");
    task1.setStatus(Status.ACTIVE);
    Task task2=new Task("Task is DONE");
    task2.setStatus(Status.DONE);
    taskRegistry.addTask(task1);
    taskRegistry.addTask(task2);
    assertTrue(taskRegistry.getActiveTasks().contains(task1));
    assertFalse(taskRegistry.getActiveTasks().contains(task2));
  }

  @Test
  void addTask() throws IOException {
    Task task=new Task("Add a new task");
    taskRegistry.addTask(task);
    assertTrue(taskRegistry.getTasks().contains(task));
  }

  @Test
  void removeTask() throws IOException {
    Task task=new Task("Task to remove");
    taskRegistry.addTask(task);
    taskRegistry.removeTask(task);
    assertFalse(taskRegistry.getTasks().contains(task));

  }

  @Test
  void getTasksByDateAdded() throws IOException {
    Task taskA = new Task("A");
    Task taskB = new Task("B");
    taskA.setDateAdded(LocalDate.now().minusWeeks(1));
    taskRegistry.addTask(taskA);
    taskRegistry.addTask(taskB);
    assertTrue(taskRegistry.getTasksByDateAdded(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)).contains(taskB));
    assertFalse(taskRegistry.getTasksByDateAdded(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)).contains(taskA));
  }
}