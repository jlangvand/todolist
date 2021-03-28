package dao;

import models.Task;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import utilities.Priority;
import utilities.Status;

import java.time.LocalDate;

class PersistentRegistryTest {
    Task task=new Task();

    @Before
    public void setUp() throws Exception{
        task.setTitle("Meeting a friend");
        task.setDescription("I will meet him at restaurant ");
        task.setPriority(Priority.LOW);
        task.setCategory("social");
        task.setStartedDate(LocalDate.of(2021,5,14));
        task.setFinishedDate(LocalDate.of(2021,5,14));
        task.setDeadline(LocalDate.of(2021,5,14));
        task.setStatus(Status.ACTIVE);
    }


    @Test
    void saveData() {

    }

    @Test
    void loadDate() {

    }
}