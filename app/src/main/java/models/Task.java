package models;

import utilities.Priority;

import java.time.LocalDate;

public class Task {
  private String description;
  private Priority priority;
  private String category;
  private LocalDate startedDate;
  private LocalDate finishedDate;
  private LocalDate deadline;
  private String title;

  public Task(String title) {
    this.setTitle(title);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDate getStartedDate() {
    return startedDate;
  }

  public void setStartedDate(LocalDate startedDate) {
    this.startedDate = startedDate;
  }

  public LocalDate getFinishedDate() {
    return finishedDate;
  }

  public void setFinishedDate(LocalDate finishedDate) {
    this.finishedDate = finishedDate;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
