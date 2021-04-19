/**
 * Main application module.
 */
module todolist.app.main {
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.controls;
  requires com.jfoenix;
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