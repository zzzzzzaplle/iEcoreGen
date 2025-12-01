import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a book in the library system.
 */
class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    private int checkoutCount;

    /**
     * Default constructor.
     */
    public Book() {
        this.checkoutCount = 0;
    }

    // Getters and setters
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

    /**
     * Increments the checkout count for this book.
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }

    /**
     * Gets the checkout count for this book.
     * @return The number of times this book has been checked out.
     */
    public int getCheckoutCount() {
        return checkoutCount;
    }
}

/**
 * Represents a user in the library system.
 */
abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;

    /**
     * Default constructor.
     */
    public User() {
        // Default constructor
    }

    // Getters and setters
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
}

/**
 * Represents a member user in the library system.
 */
class MemberUser extends User {
    // Inherits all fields and methods from User
}

/**
 * Represents a guest user in the library system.
 */
class GuestUser extends User {
    // Inherits all fields and methods from User
}

/**
 * Represents a book loan in the library system.
 */
class BookLoan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Default constructor.
     */
    public BookLoan() {
        // Default constructor
    }

    // Getters and setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

/**
 * Represents the library system.
 */
class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<BookLoan> bookLoans;

    /**
     * Default constructor.
     */
    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookLoans = new ArrayList<>();
    }

    // Getters and setters
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    /**
     * Adds a new book to the library system.
     * @param book The book to add.
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Adds a new user to the library system.
     * @param user The user to add.
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Creates a new book loan.
     * @param book The book to loan.
     * @param user The user borrowing the book.
     * @param startDate The start date of the loan.
     * @param endDate The end date of the loan.
     */
    public void createBookLoan(Book book, User user, LocalDate startDate, LocalDate endDate) {
        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        this.bookLoans.add(loan);
        book.incrementCheckoutCount();
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * @param book The book to count checkouts for.
     * @return The number of times the book has been checked out.
     */
    public int countBookCheckouts(Book book) {
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * @param user The user to count unique books for.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The number of unique books borrowed by the user during the period.
     */
    public int countUniqueBooksBorrowedByUser(User user, LocalDate startDate, LocalDate endDate) {
        Map<String, Boolean> uniqueBooks = new HashMap<>();
        for (BookLoan loan : bookLoans) {
            if (loan.getUser().equals(user) &&
                !loan.getStartDate().isBefore(startDate) &&
                !loan.getEndDate().isAfter(endDate)) {
                uniqueBooks.put(loan.getBook().getIsbn(), true);
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * @param user The user to calculate the average page count for.
     * @return The average page count of the unique books borrowed by the user.
     */
    public double calculateAveragePageCountForUser(User user) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (BookLoan loan : bookLoans) {
            if (loan.getUser().equals(user) && !uniqueBooks.contains(loan.getBook())) {
                uniqueBooks.add(loan.getBook());
            }
        }
        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }
        int totalPages = 0;
        for (Book book : uniqueBooks) {
            totalPages += book.getNumberOfPages();
        }
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * @param user The user to calculate the total unique books for.
     * @return The total number of unique books checked out by the user.
     */
    public int calculateTotalUniqueBooksCheckedOutByUser(User user) {
        Map<String, Boolean> uniqueBooks = new HashMap<>();
        for (BookLoan loan : bookLoans) {
            if (loan.getUser().equals(user)) {
                uniqueBooks.put(loan.getBook().getIsbn(), true);
            }
        }
        return uniqueBooks.size();
    }
}