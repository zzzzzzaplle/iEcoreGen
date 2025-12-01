import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a book in the library.
 */
 class Book {

    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;

    /** Unparameterized constructor */
    public Book() {
    }

    /** Parameterized constructor for convenience */
    public Book(String title, String barcode, String isbn, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------
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

    // ------------------------------------------------------------------------
    // Equality based on unique barcode (assumed to be unique per physical copy)
    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(barcode, book.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}

/**
 * Abstract base class for all users of the library system.
 */
public abstract class User {

    private String name;
    private String email;
    private String address;
    private String id; // Unique identifier for the user

    /** Unparameterized constructor */
    public User() {
    }

    /** Parameterized constructor for convenience */
    public User(String name, String email, String address, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------
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

    // ------------------------------------------------------------------------
    // Equality based on unique user id
    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a library member (full privileges).
 */
 class MemberUser extends User {

    /** Unparameterized constructor */
    public MemberUser() {
        super();
    }

    /** Parameterized constructor for convenience */
    public MemberUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a guest user (limited privileges).
 */
 class GuestUser extends User {

    /** Unparameterized constructor */
    public GuestUser() {
        super();
    }

    /** Parameterized constructor for convenience */
    public GuestUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a single loan (checkout) of a book by a user.
 */
 class Loan {

    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate; // Expected return date

    /** Unparameterized constructor */
    public Loan() {
    }

    /** Parameterized constructor for convenience */
    public Loan(Book book, User user, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------
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
 * Core library system that stores books, users and loan records.
 * Provides analytical operations required by the functional specification.
 */
 class LibrarySystem {

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    /** Unparameterized constructor */
    public LibrarySystem() {
    }

    // ------------------------------------------------------------------------
    // Basic collection manipulation helpers
    // ------------------------------------------------------------------------

    /**
     * Adds a book to the library collection.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    /**
     * Registers a user (member or guest) in the system.
     *
     * @param user the user to register
     */
    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    /**
     * Records a new loan (checkout) in the system.
     *
     * @param loan the loan to record
     */
    public void addLoan(Loan loan) {
        if (loan != null) {
            loans.add(loan);
        }
    }

    // ------------------------------------------------------------------------
    // Functional Requirement Implementations
    // ------------------------------------------------------------------------

    /**
     * Counts the total number of times a specific book has been checked out.
     *
     * @param book the book whose checkout count is required
     * @return the number of loan records that reference the given book
     * @throws IllegalArgumentException if {@code book} is {@code null}
     */
    public int countCheckouts(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        int count = 0;
        for (Loan loan : loans) {
            if (book.equals(loan.getBook())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param user  the user whose borrowing activity is examined
     * @param from  the start date of the period (inclusive)
     * @param to    the end date of the period (inclusive)
     * @return the count of distinct books borrowed by the user within the period
     * @throws IllegalArgumentException if any argument is {@code null} or if {@code from} is after {@code to}
     */
    public int countUniqueBooksBorrowedByUserInPeriod(User user, LocalDate from, LocalDate to) {
        validateUserAndPeriod(user, from, to);
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (user.equals(loan.getUser())
                    && !loan.getStartDate().isBefore(from)
                    && !loan.getStartDate().isAfter(to)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user (over all time).
     *
     * @param user the user whose borrowed books are analysed
     * @return the average number of pages; returns {@code 0.0} if the user has not borrowed any books
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public double averagePageCountOfUniqueBooksBorrowedByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (user.equals(loan.getUser())) {
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
     * Calculates the total number of unique books ever checked out by a specific user.
     *
     * @param user the user whose borrowing history is examined
     * @return the count of distinct books the user has ever borrowed
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (user.equals(loan.getUser())) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    // ------------------------------------------------------------------------
    // Helper validation method
    // ------------------------------------------------------------------------
    private void validateUserAndPeriod(User user, LocalDate from, LocalDate to) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Period dates cannot be null.");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }
    }

    // ------------------------------------------------------------------------
    // Getters for internal collections (read‑only copies)
    // ------------------------------------------------------------------------
    /**
     * Returns an unmodifiable view of all books in the system.
     *
     * @return list of books
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Returns an unmodifiable view of all registered users.
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Returns an unmodifiable view of all loan records.
     *
     * @return list of loans
     */
    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }
}