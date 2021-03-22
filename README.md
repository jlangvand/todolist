# Todolist

## Project Structure

    ├── build.gradle
    └── src
    ├── main
    │   ├── java
    │   │   ├── controllers
    │   │   ├── dao
    │   │   │   ├── TaskRegistry.java
    │   │   ├── models
    │   │   │   ├── PersistentRegistry.java
    │   │   │   └── Task.java
    │   │   ├── todolist
    │   │   │   └── app
    │   │   │       └── App.java
    │   │   └── utilities
    │   │       ├── Priority.java
    │   │       └── Status.java
    │   └── resources
    │       ├── css
    │       ├── images
    │       └── view
    └── test
    └── java
    └── todolist
    └── app

JavaFX files resides in the `resources` directory. todolist.App is the main class/entry point of the application, and
loads the JavaFX scene. As for now, it is implemented as a Hello World simply to confirm that the project builds
successfully.