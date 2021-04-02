package utilities;

public enum Priority {
  HIGH {
    @Override
    public String toString() {
      return "High";
    }
  },
  MEDIUM {
    @Override
    public String toString() {
      return "Medium";
    }
  },
  LOW {
    @Override
    public String toString() {
      return "Low";
    }
  },
  DEFAULT {
    @Override
    public String toString() {
      return "Not set";
    }
  }
}
