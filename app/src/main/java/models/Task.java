package models;

import utilities.Priority;
import utilities.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
* This is  is base class which handle the information about the task.
* This task allows the application to assign values for all the task.
*
* @version 1.0
* @since  1.0
* */
public class Task implements Serializable {

  private static final long serialVersionUID=2L;

  /** Represents the task's title.
   */
  private String title;
  /** Represents the description of the task.
  */
  private String description;
  /** Represtns the task's priority
  */
  private Priority priority;
  /** Represents the task's category.
  */
  private String category;
  /*Represents the task's startedDate.
  */
  private LocalDate startedDate;
  /**Represents the task's finishedDate.
  */
  private LocalDate finishedDate;
  /** Represents the task's deadline.
  */
  private LocalDate deadline;




  /*Represents the task's status
  * */
  private Status status;
/** task's constructor
 * Creates a task with the specific title
 * @param title representing The task's title
*/
  public Task(String title) {
    super();
    this.setTitle(title);
  }
/*
* Constructor without parameters
* */
    public Task() { }

    /**Gets the task's description.
 * @return a string representing task's description.
*/
  public String getDescription() {
    return description;
  }
/**Sets the task's description.
 * @param description- String.
*/
  public void setDescription(String description) {
    this.description = description;
  }
/**Gets the task's priority.
 * @return a Priority representing priority.
*/
  public Priority getPriority() {
    return priority;
  }
/**Sets the task's priority.
 * @param priority-Priority.
*/
  public void setPriority(Priority priority) {
    this.priority = priority;
  }
/** Gets the task's category.
 * @return A Category representing task's category.
*/
  public String getCategory() {
    return category;
  }
/**Sets the task's category.
 * @param category-String.
*/
  public void setCategory(String category) {
    this.category = category;
  }
/** Gets The task's startDate
 * @return localDate representing the task's startedDate.
*/
  public LocalDate getStartedDate() {
    return startedDate;
  }
/**Sets the task's startedDate
 * @param startedDate-localDate.
*/
  public void setStartedDate(LocalDate startedDate) {
    this.startedDate = startedDate;
  }
/**Gets task's finishedDate.
 * @return localDate representing the task's finishedDate.
*/
  public LocalDate getFinishedDate() {
    return finishedDate;
  }
  /**Gets task's status
   * @return status representing the task's status
   * */
  public Status getStatus() {
    return status;
  }
/**Sets' The task's finishedDate.
 * @param finishedDate -localDate.
*/
  public void setFinishedDate(LocalDate finishedDate) {
    this.finishedDate = finishedDate;
  }
/** Gets task's deadline.
 * @return A localDate representing the task's deadline.
*/
  public LocalDate getDeadline() {
    return deadline;
  }

  /**Sets the task's deadline.
   * @param deadline .
   */
  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  /**Gets the task's title.
   * @return A String representing the task's title.
   */
  public String getTitle() {
    return title;
  }

  /**Sets the task's title.
   * @param title-String.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets the task's status.
   *
   * @param status-Status.
   */
  public void setStatus(Status status) {
    this.status = status;
  }

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
   * This is  method which check the equality of tasks.
   *
   * @param object-an Object.
   * @return A boolean true if the tasks are equal, or false if not.
   */
  @Override
  public boolean equals(Object object) {
    boolean result;
    if (this == object) {
      result = true;
    } else if (!(object instanceof Task)) {
      result = false;
    } else {
      Task task = (Task) object;
      result = getPriority() == task.getPriority() && Objects.equals(getCategory(), task.getCategory()) && Objects.equals(getStartedDate(), task.getStartedDate()) && Objects.equals(getFinishedDate(), task.getFinishedDate()) && Objects.equals(getDeadline(), task.getDeadline()) && Objects.equals(getTitle(), task.getTitle());
    }
    return result;
  }

  /**Returns the hash code of a non-null argument and 0 for a null argument.
   * hashCode in class Object.
   * @return hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash( getPriority(), getCategory(), getStartedDate(), getFinishedDate(), getDeadline(), getTitle());
  }
/**Returns the all task's objects information
 * @return A String representing the object's data.
*/
  @Override
  public String toString() {
    return "Task{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", priority=" + priority +
            ", category='" + category + '\'' +
            ", startedDate=" + startedDate +
            ", finishedDate=" + finishedDate +
            ", deadline=" + deadline +
            ", status=" + status +
            '}';
  }
}
