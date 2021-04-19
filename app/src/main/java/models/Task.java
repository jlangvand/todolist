/*
 *     Copyright © 2021 Mona Mahmoud Mousa
 *
 *      Authors (in alphabetical order):
 *      Ask Brandsnes Røsand
 *      Joakim Skogø Langvand
 *      Leonard Sandløkk Schiller
 *      Moaaz Bassam Yanes
 *      Mona Mahmoud Mousa
 *
 *     This file is part of Todolist.
 *
 *     Todolist is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Todolist is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Todolist.  If not, see <https://www.gnu.org/licenses/>.
 */

package models;

import utilities.Priority;
import utilities.Status;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Base class handling information about a task.
 *
 * @version 1.0
 * @since 1.0
 */
public class Task implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  private String title;
  private String description;
  private Priority priority;
  private String category;
  private final LocalDate dateAdded;
  private LocalDate finishedDate;
  private LocalDate deadline;
  private LocalTime deadLineTime;
  private Status status;

  /** Default constructor. */
  public Task() {
    this.dateAdded = LocalDate.now();
    this.title = "";
    this.description = "";
    this.category = "default";
    this.priority = Priority.DEFAULT;
    this.status = Status.ACTIVE;
    this.finishedDate = null;
    this.deadline = LocalDate.now().plusDays(7);
    this.deadLineTime = LocalTime.now();
  }

  /**
   * Get description.
   *
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set description.
   *
   * @param description description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get priority.
   *
   * @return priority
   */
  public Priority getPriority() {
    return priority;
  }

  /**
   * Set priority.
   *
   * @param priority description
   */
  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  /**
   * Get category.
   *
   * @return category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Set category.
   *
   * @param category description
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Get date added.
   *
   * @return date added
   */
  public LocalDate getDateAdded() {
    return dateAdded;
  }

  /**
   * Get date finished.
   *
   * @return date finished
   */
  public LocalDate getFinishedDate() {
    return finishedDate;
  }

  /**
   * Set date finished.
   *
   * @param finishedDate date finished
   */
  public void setFinishedDate(LocalDate finishedDate) {
    this.finishedDate = finishedDate;
  }

  /**
   * Get status.
   *
   * @return status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Set status.
   *
   * @param status status
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Get deadline.
   *
   * @return deadline
   */
  public LocalDate getDeadline() {
    return deadline;
  }

  /**
   * Set deadline.
   *
   * @param deadline deadline
   */
  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  /**
   * Get title.
   *
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set title.
   *
   * @param title title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Get deadline time of day.
   *
   * @return deadline time of day
   */
  public LocalTime getDeadLineTime() {
    return deadLineTime;
  }

  /**
   * Set deadline time of day.
   *
   * @param deadLineTime deadline time of day
   */
  public void setDeadLineTime(LocalTime deadLineTime) {
    this.deadLineTime = deadLineTime;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    boolean result;
    if (this == object) {
      result = true;
    } else if (!(object instanceof Task)) {
      result = false;
    } else {
      Task task = (Task) object;
      result =
          getPriority() == task.getPriority() && Objects.equals(getCategory()
              , task.getCategory()) && Objects.equals(getDateAdded(),
              task.getDateAdded()) && Objects.equals(getFinishedDate(),
              task.getFinishedDate()) && Objects.equals(getDeadline(),
              task.getDeadline()) && Objects.equals(getTitle(),
              task.getTitle());
    }
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(getPriority(), getCategory(), getDateAdded(),
        getFinishedDate(), getDeadline(), getTitle());
  }

  /** {@inheritDoc} */
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
