package models;

import utilities.Priority;
import utilities.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
 * Base class handling information about a task.
 *
 * @version 1.0
 * @since  1.0
 * */
public class Task implements Serializable {
  private static final long serialVersionUID = 1L;

  private String title;
  private String description;
  private Priority priority;
  private String category;
  private LocalDate dateAdded;
  private LocalDate finishedDate;
  private LocalDate deadline;
  private Status status;

  /** Create task providing a title. */
  public Task(String title) {
    super();
    this.setTitle(title);
  }

  /**
   * Default constructor.
   *
   * <p>TODO(joakilan): Unnecessary? A task shouldn't be constructed without
   * required data..
   */
  public Task() {
    this.dateAdded = LocalDate.now();
    this.title = "";
    this.description = "";
    this.category = "default";
    this.priority = Priority.DEFAULT;
    this.status = Status.ACTIVE;
    this.finishedDate = null;
    this.deadline = null;
  }

  /** Get description. */
  public String getDescription() {
    return description;
  }

  /** Set description. */
  public void setDescription(String description) {
    this.description = description;
  }

  /** Get priority. */
  public Priority getPriority() {
    return priority;
  }

  /** Set priority. */
  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  /** Get category. */
  public String getCategory() {
    return category;
  }

  /** Set category. */
  public void setCategory(String category) {
    this.category = category;
  }

  /** Get start date */
  public LocalDate getDateAdded() {
    return dateAdded;
  }

  /** Set started date. */
  public void setDateAdded(LocalDate dateAdded) {
    this.dateAdded = dateAdded;
  }

  /** Get finished date. */
  public LocalDate getFinishedDate() {
    return finishedDate;
  }

  /** Get status. */
  public Status getStatus() {
    return status;
  }

  /** Set finished date. */
  public void setFinishedDate(LocalDate finishedDate) {
    this.finishedDate = finishedDate;
  }

  /** Get deadline. */
  public LocalDate getDeadline() {
    return deadline;
  }

  /** Set deadline. */
  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  /** Get title. */
  public String getTitle() {
    return title;
  }

  /** Set title. */
  public void setTitle(String title) {
    this.title = title;
  }

  /** Sets the task's status. */
  public void setStatus(Status status) {
    this.status = status;
  }

  /** Get priority as String */
  public String getPriorityString() {
    if (priority == Priority.HIGH) {
      return "High";
    } else if (priority == Priority.MEDIUM) {
      return "Medium";
    } else if (priority == Priority.LOW) {
      return "Low";
    }
    return "No Priority";
  }

  /**
   * Check equality.
   *
   * @param object Task instance to compare to
   * @return true if equal, else false
   */
  @Override
  public boolean equals(Object object) {
    // TODO(joakilan): Any reason not to simply return equality of hashCode()?
    boolean result;
    if (this == object) {
      result = true;
    } else if (!(object instanceof Task)) {
      result = false;
    } else {
      Task task = (Task) object;
      result = getPriority() == task.getPriority() && Objects.equals(getCategory(), task.getCategory()) && Objects.equals(getDateAdded(), task.getDateAdded()) && Objects.equals(getFinishedDate(), task.getFinishedDate()) && Objects.equals(getDeadline(), task.getDeadline()) && Objects.equals(getTitle(), task.getTitle());
    }
    return result;
  }

  /** Get hash of this instance. */
  @Override
  public int hashCode() {
    return Objects.hash(getPriority(), getCategory(), getDateAdded(), getFinishedDate(), getDeadline(), getTitle());
  }

  /** Get String representation of this instance. */
  @Override
  public String toString() {
    return "Task{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", priority=" + priority +
        ", category='" + category + '\'' +
        ", startedDate=" + dateAdded +
        ", finishedDate=" + finishedDate +
        ", deadline=" + deadline +
        ", status=" + status +
        '}';
  }
}
