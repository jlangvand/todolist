package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskRegistryTest {
    private TaskRegistry taskRegistry;



    @BeforeEach
    void setUp() throws IOException {

    }

    @Test
    void getTasksTest() throws IOException {
        ArrayList<Task>tasks=new ArrayList<>();
        taskRegistry=new TaskRegistry("test2.bin");
        Task task1=new Task("Meeting a friend");
        Task task2=new Task("Reading a book");
        taskRegistry.addTask(task1);
        taskRegistry.addTask(task2);

        assertEquals(task1,taskRegistry.getTasks().get(0));
        assertEquals(task2,taskRegistry.getTasks().get(1));
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