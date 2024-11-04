package your.package.name; // 1. Define the package

import javafx.scene.Scene; // 2. Scene for the view
import javafx.scene.control.*; // 3. Controls for the view
import javafx.scene.image.Image; // 4. For images
import javafx.scene.image.ImageView; // 5. For displaying images
import javafx.scene.layout.*; // 6. Layout management
import javafx.stage.FileChooser; // 7. File chooser for selecting CSV
import javafx.stage.Stage; // 8. Stage for the window
import java.io.File; // 9. File type
import java.util.List; // 10. List type
import java.util.Optional; // 11. For optional results
import javafx.scene.control.Alert.AlertType; // 12. Alert types
import javafx.scene.control.ButtonType; // 13. Button types

public class LibraryView { // 1. Library view class
    private BookManager bookManager; // 2. Reference to BookManager
    private VBox bookshelf; // 3. VBox to layout books
    private TextField searchField; // 4. Search field
    private Label bookCountLabel; // 5. Label for book counts
    private ListView<Book> bookListView; // 6. List view for displaying books
    private Stage primaryStage; // 7. Reference to primary stage

    public void show(Stage stage) { // 1. Show the library view
        primaryStage = stage; // 2. Save primary stage reference
        stage.setTitle("My Book Library"); // 3. Set title
        bookshelf = new VBox(10); // 4. Initialize bookshelf layout
        searchField = new TextField(); // 5. Initialize search field
        bookCountLabel = new Label(); // 6. Initialize book count label
        Button addButton = new Button("Add Item"); // 7. Button to add items
        addButton.setOnAction(e -> addBook()); // 8. Set action for adding book
        Button loadButton = new Button("Load CSV"); // 9. Button to load CSV
        loadButton.setOnAction(e -> loadCSV(stage)); // 10. Set action for loading CSV

        HBox topBar = new HBox(10, searchField, addButton, loadButton); // 11. Top bar layout
        VBox layout = new VBox(10, topBar, bookCountLabel, bookshelf); // 12. Main layout

        Scene scene = new Scene(layout, 800, 600); // 13. Scene setup
        stage.setScene(scene); // 14. Set the scene
        stage.show(); // 15. Show the stage
    }

    private void loadCSV(Stage stage) { // 1. Load CSV file method
        FileChooser fileChooser = new FileChooser(); // 2. Create a file chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv")); // 3. Add filter
        File file = fileChooser.showOpenDialog(stage); // 4. Show dialog
        if (file != null) { // 5. Check if file is selected
            bookManager = new BookManager(file.getAbsolutePath()); // 6. Initialize BookManager
            displayBooks(bookManager.getBooks()); // 7. Display books
        }
    }

    private void displayBooks(List<Book> books) { // 1. Display books method
        bookshelf.getChildren().clear(); // 2. Clear existing books
        for (Book book : books) { // 3. Loop through books
            HBox bookDisplay = new HBox(10); // 4. Horizontal box for book display
            ImageView coverImage = new ImageView(new Image(new File(book.getCoverImagePath()).toURI().toString())); // 5. Load image
            coverImage.setFitWidth(100); // 6. Set image width
            coverImage.setPreserveRatio(true); // 7. Preserve aspect ratio
            Label titleLabel = new Label(book.getTitle()); // 8. Title label
            Label authorLabel = new Label(book.getAuthor()); // 9. Author label

            bookDisplay.getChildren().addAll(coverImage, titleLabel, authorLabel); // 10. Add components
            bookDisplay.setOnMouseClicked(e -> highlightBook(book)); // 11. Highlight on click
            bookshelf.getChildren().add(bookDisplay); // 12. Add to bookshelf
        }
        updateBookCount(); // 13. Update book count display
    }

    private void highlightBook(Book book) { // 1. Highlight book method
        Alert alert = new Alert(AlertType.INFORMATION); // 2. Create alert dialog
        alert.setTitle("Book Details"); // 3. Set title
        alert.setHeaderText(book.getTitle() + " by " + book.getAuthor()); // 4. Set header text
        alert.setContentText("This book has been read: " + (book.isRead() ? "Read" : "Unread") + "\n" +
                "This book is in my physical collection: " + (book.isOwned() ? "Owned" : "Unowned")); // 5. Set content text

        ButtonType updateButton = new ButtonType("Update Info"); // 6. Update button
        ButtonType removeButton = new ButtonType("Remove Book"); // 7. Remove button
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE); // 8. Cancel button
        alert.getButtonTypes().setAll(updateButton, removeButton, cancelButton); // 9. Set button types

        Optional<ButtonType> result = alert.showAndWait(); // 10. Show alert and wait for response
        if (result.isPresent()) { // 11. Check result
            if (result.get() == updateButton) { // 12. Update button clicked
                // Implement updating logic (not shown here)
            } else if (result.get() == removeButton) { // 13. Remove button clicked
                bookManager.removeBook(book); // 14. Remove book
                displayBooks(bookManager.getBooks()); // 15. Refresh display
            }
        }
    }

    private void addBook() { // 1. Add book method
        Dialog<Book> dialog = new Dialog<>(); // 2. Create dialog
        dialog.setTitle("Add New Book"); // 3. Set title

        // Create input fields
        TextField titleInput = new TextField(); // 4. Title input
        TextField authorInput = new TextField(); // 5. Author input
        CheckBox readCheck = new CheckBox("Read?"); // 6. Read checkbox
        CheckBox ownedCheck = new CheckBox("Owned?"); // 7. Owned checkbox
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE); // 8. Add button type
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL); // 9. Add buttons

        // Layout for dialog
        VBox dialogPane = new VBox(10, new Label("Title:"), titleInput, new Label("Author:"), authorInput, readCheck, ownedCheck); // 10. Layout
        dialog.getDialogPane().setContent(dialogPane); // 11. Set content

        // Handle result
        dialog.setResultConverter(dialogButton -> { // 12. Handle dialog result
            if (dialogButton == addButtonType) { // 13. Add button clicked
                String coverImagePath = ""; // 14. Default cover image path
                // Logic to choose image could go here
                return new Book(titleInput.getText(), authorInput.getText(), readCheck.isSelected(), ownedCheck.isSelected(), coverImagePath); // 15. Return new Book
            }
            return null; // 16. Cancelled
        });

        Optional<Book> result = dialog.showAndWait(); // 17. Show dialog and wait
        result.ifPresent(book -> { // 18. If a book is returned
            bookManager.addBook(book); // 19. Add book to manager
            displayBooks(bookManager.getBooks()); // 20. Refresh display
        });
    }

    private void updateBookCount() { // 1. Update book count method
        long readCount = bookManager.getBooks().stream().filter(Book::isRead).count(); // 2. Count read books
        long ownedCount = bookManager.getBooks().stream().filter(Book::isOwned).count(); // 3. Count owned books
        bookCountLabel.setText("Read: " + readCount + " | Owned: " + ownedCount); // 4. Update label
    }
}
