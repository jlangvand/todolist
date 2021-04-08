package utilities;

public enum Priority {
  HIGH("High"),
  MEDIUM("Medium"),
  LOW("Low"),
  DEFAULT("Not set");

  private String description;

  Priority(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
