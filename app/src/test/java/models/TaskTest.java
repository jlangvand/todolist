package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {
  Task task;

  private static Object deserialize(byte[] bytes) throws ClassNotFoundException, IOException {
    ByteArrayInputStream b = new ByteArrayInputStream(bytes);
    ObjectInputStream o = new ObjectInputStream(b);
    return o.readObject();
  }

  @BeforeEach
  public void initTask() {
    task = new Task("Title");
    task.setDescription("Description may be a little longer than the title.");
    task.setFinishedDate(LocalDate.now().plusDays(2));
    task.setDeadline(LocalDate.now().plusDays(3));
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
  void testInstance() throws Exception {
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
}