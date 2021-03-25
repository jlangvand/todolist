module todolist.app.main {
    requires javafx.controls;
    requires com.jfoenix;
    requires javafx.fxml;

    opens todolist.app to javafx.fxml;
    exports todolist.app;


}