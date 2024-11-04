package your.package.name; // 1. Define the package

import java.io.*; // 2. Import for file operations
import java.nio.file.*; // 3. Import for file paths
import java.util.*; // 4. Import for collections

public class BookManager { // 1. Manage book data
    private List<Book> books = new ArrayList<>(); // 2. List to store books
    private String filePath; // 3. File path for CSV

    public BookManager(String filePath) { // 4. Constructor
        this.filePath = filePath;
        loadBooks(); // 5. Load books from file
    }

    private void loadBooks() { // 6. Load books from CSV
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // 7. Read file
            String line;
            while ((line = br.readLine()) != null) { // 8. Read each line
                String[] data = line.split(","); // 9. Split by comma
                if (data.length == 5) { // 10. Check for the correct number of fields
                    String title = data[0];
                    String author = data[1];
                    boolean read = Boolean.parseBoolean(data[2]);
                    boolean owned = Boolean.parseBoolean(data[3]);
                    String coverImagePath = data[4];
                    books.add(new Book(title, author, read, owned, coverImagePath)); // 11. Create and add book
                }
            }
        } catch (IOException e) { // 12. Handle exceptions
            e.printStackTrace();
        }
    }

    public void saveBooks() { // 13. Save books to CSV
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) { // 14. Write to file
            for (Book book : books) { // 15. Loop through books
                pw.println(String.join(",", // 16. Join fields by comma
                        book.getTitle(),
                        book.getAuthor(),
                        String.valueOf(book.isRead()),
                        String.valueOf(book.isOwned()),
                        book.getCoverImagePath()
                ));
            }
        } catch (IOException e) { // 17. Handle exceptions
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() { // 18. Return the list of books
        return books;
    }

    public void addBook(Book book) { // 19. Add a new book
        books.add(book);
        saveBooks(); // 20. Save changes
    }

    public void removeBook(Book book) { // 21. Remove a book
        books.remove(book);
        saveBooks(); // 22. Save changes
    }
}
