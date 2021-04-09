module todolist.app.main {
  requires javafx.controls;
  requires com.jfoenix;
  requires javafx.fxml;
  requires java.logging;

  opens todolist.app to javafx.fxml;
  exports todolist.app;

  opens controllers to javafx.fxml;
  exports controllers;

  opens models to javafx.fxml;
  exports models;

  opens utilities to javafx.fxml;
  exports utilities;
}