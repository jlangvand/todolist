package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Priority;
import utilities.Status;

import java.io.*;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest{
        Task task;

        @BeforeEach
        public void initTask(){
            task=new Task();
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
        void consistencyTest() throws Exception {
            byte[] serialized1 = serialize(task);
            byte[] serialized2 = serialize(task);

            Object deserialized1 = deserialize(serialized1);
            Object deserialized2 = deserialize(serialized2);
            assertEquals(deserialized1, deserialized2);
            assertEquals(task, deserialized1);
            assertEquals(task, deserialized2);
        }

        @Test
        void testInstance()throws Exception {
            byte[] serialized = serialize(task);
            Object deserialized = deserialize(serialized);

            assertTrue(deserialized instanceof Task);
            assertEquals(task, deserialized);
        }

        private byte[] serialize(Object obj) throws Exception {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(obj);
            return b.toByteArray();
        }

        private static Object deserialize(byte[] bytes) throws ClassNotFoundException,IOException {
            ByteArrayInputStream b = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(b);
            return o.readObject();
        }

}