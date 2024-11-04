package your.package.name; // 1. Define the package

public class Book { // 1. Book class to represent a book
    private String title; // 2. Title of the book
    private String author; // 3. Author of the book
    private boolean read; // 4. Read status
    private boolean owned; // 5. Owned status
    private String coverImagePath; // 6. Path to the cover image

    public Book(String title, String author, boolean read, boolean owned, String coverImagePath) { // 7. Constructor
        this.title = title;
        this.author = author;
        this.read = read;
        this.owned = owned;
        this.coverImagePath = coverImagePath;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isRead() { return read; }
    public boolean isOwned() { return owned; }
    public String getCoverImagePath() { return coverImagePath; }
}
