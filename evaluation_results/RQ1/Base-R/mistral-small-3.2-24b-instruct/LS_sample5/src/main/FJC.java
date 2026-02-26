import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    private int checkoutCount;

    public Book() {
        this.checkoutCount = 0;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getCheckoutCount() {
        return checkoutCount;
    }

    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<CheckOut> checkOuts;

    public User() {
        this.checkOuts = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }
}

class CheckOut {
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;

    public CheckOut() {
    }

    // Getters and Setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

class LibrarySystem {
    private Map<String, Book> books;
    private Map<String, User> users;

    public LibrarySystem() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    /**
     * Adds a book to the library system.
     *
     * @param book The book to add.
     */
    public void addBook(Book book) {
        books.put(book.getBarcode(), book);
    }

    /**
     * Adds a user to the library system.
     *
     * @param user The user to add.
     */
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Checks out a book for a user.
     *
     * @param userId The ID of the user.
     * @param bookBarcode The barcode of the book.
     * @param startDate The start date of the checkout.
     * @param endDate The end date of the checkout.
     * @throws IllegalArgumentException If the user or book is not found.
     */
    public void checkoutBook(String userId, String bookBarcode, LocalDate startDate, LocalDate endDate) {
        User user = users.get(userId);
        Book book = books.get(bookBarcode);

        if (user == null || book == null) {
            throw new IllegalArgumentException("User or book not found.");
        }

        CheckOut checkOut = new CheckOut();
        checkOut.setBook(book);
        checkOut.setStartDate(startDate);
        checkOut.setEndDate(endDate);

        user.addCheckOut(checkOut);
        book.incrementCheckoutCount();
    }

    /**
     * Counts the number of times a specific book has been checked out.
     *
     * @param bookBarcode The barcode of the book.
     * @return The number of times the book has been checked out.
     * @throws IllegalArgumentException If the book is not found.
     */
    public int countBookCheckouts(String bookBarcode) {
        Book book = books.get(bookBarcode);

        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }

        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param userId The ID of the user.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The number of unique books borrowed by the user during the period.
     * @throws IllegalArgumentException If the user is not found.
     */
    public int countUniqueBooksBorrowedByUser(String userId, LocalDate startDate, LocalDate endDate) {
        User user = users.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return user.getCheckOuts().stream()
                .filter(checkOut -> !checkOut.getStartDate().isBefore(startDate) && !checkOut.getEndDate().isAfter(endDate))
                .map(CheckOut::getBook)
                .distinct()
                .toArray().length;
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @param userId The ID of the user.
     * @return The average page count of the unique books borrowed by the user.
     * @throws IllegalArgumentException If the user is not found or has not borrowed any books.
     */
    public double calculateAveragePageCountByUser(String userId) {
        User user = users.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        List<Book> uniqueBooks = user.getCheckOuts().stream()
                .map(CheckOut::getBook)
                .distinct()
                .toList();

        if (uniqueBooks.isEmpty()) {
            throw new IllegalArgumentException("User has not borrowed any books.");
        }

        return uniqueBooks.stream()
                .mapToInt(Book::getNumberOfPages)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @param userId The ID of the user.
     * @return The total number of unique books checked out by the user.
     * @throws IllegalArgumentException If the user is not found.
     */
    public int calculateTotalUniqueBooksCheckedOutByUser(String userId) {
        User user = users.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        return user.getCheckOuts().stream()
                .map(CheckOut::getBook)
                .distinct()
                .toArray().length;
    }
}