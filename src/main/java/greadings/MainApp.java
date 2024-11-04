package your.package.name; // 1. Define the package

import javafx.application.Application; // 2. JavaFX Application
import javafx.stage.Stage; // 3. Stage for the application

public class MainApp extends Application { // 1. Main application class
    @Override
    public void start(Stage primaryStage) { // 2. Start method
        LibraryView libraryView = new LibraryView(); // 3. Create library view
        libraryView.show(primaryStage); // 4. Show library view
    }

    public static void main(String[] args) { // 5. Main method
        launch(args); // 6. Launch application
    }
}
