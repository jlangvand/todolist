package utilities;

/**
 * Priority enum.
 */
public enum Priority {
  HIGH("High Priority"),
  MEDIUM("Medium Priority"),
  LOW("Low Priority"),
  DEFAULT("No Priority");

  private final String description;

  Priority(String description) {
    this.description = description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return description;
  }
}
