package utilities;

public enum Priority {
  HIGH("High Priority"),
  MEDIUM("Medium Priority"),
  LOW("Low Priority"),
  DEFAULT("No Priority");

  private String description;

  Priority(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
