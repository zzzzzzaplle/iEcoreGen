import java.time.LocalDate;
import java.util.ArrayList;
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

    /** Unparameterized constructor. */
    public Book() {
    }

    /** Parameterized constructor for convenience. */
    public Book(String title, String barcode, String isbn, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
    }

    /* ---------- Getters & Setters ---------- */

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
     * Equality is based on the unique barcode of a book.
     */
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
 * Abstract base class for all users (members and guests).
 */
public abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;

    /** Unparameterized constructor. */
    public User() {
    }

    /** Parameterized constructor for convenience. */
    public User(String name, String email, String address, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
    }

    /* ---------- Getters & Setters ---------- */

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

    /**
     * Equality based on the unique user ID.
     */
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
 * Represents a library member.
 */
 class MemberUser extends User {
    /** Unparameterized constructor. */
    public MemberUser() {
        super();
    }

    /** Parameterized constructor for convenience. */
    public MemberUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a guest user.
 */
 class GuestUser extends User {
    /** Unparameterized constructor. */
    public GuestUser() {
        super();
    }

    /** Parameterized constructor for convenience. */
    public GuestUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a loan (checkout) of a book by a user.
 */
 class Loan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    /** Unparameterized constructor. */
    public Loan() {
    }

    /** Parameterized constructor for convenience. */
    public Loan(Book book, User user, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /* ---------- Getters & Setters ---------- */

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
 * Core class managing books, users and loans, and providing statistical queries.
 */
 class LibrarySystem {
    private final List<Book> books = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    /** Unparameterized constructor. */
    public LibrarySystem() {
    }

    /* ---------- Management Methods ---------- */

    /**
     * Adds a new book to the library collection.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Registers a new user (member or guest) in the system.
     *
     * @param user the user to register
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Records a new loan (checkout) in the system.
     *
     * @param loan the loan to record
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    /* ---------- Functional Requirement Methods ---------- */

    /**
     * Counts the total number of times a specific book has been checked out.
     *
     * @param book the book whose checkout count is required
     * @return the number of loans that involve the given book
     */
    public int countCheckouts(Book book) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getBook().equals(book)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * A loan is considered within the period if its start date is on or after {@code from}
     * and on or before {@code to}.
     *
     * @param user the user whose borrowing activity is examined
     * @param from the start date of the period (inclusive)
     * @param to   the end date of the period (inclusive)
     * @return the number of distinct books borrowed by the user in the given period
     */
    public int countUniqueBooksBorrowed(User user, LocalDate from, LocalDate to) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)
                    && !loan.getStartDate().isBefore(from)
                    && !loan.getStartDate().isAfter(to)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * across all of their loans.
     *
     * @param user the user whose average page count is to be calculated
     * @return the average number of pages; returns 0.0 if the user has not borrowed any books
     */
    public double averagePageCountOfUniqueBooks(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
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
     * Calculates the total number of unique books that have ever been checked out by a specific user.
     *
     * @param user the user whose unique checkout count is required
     * @return the count of distinct books the user has borrowed
     */
    public int totalUniqueBooks(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /* ---------- Additional Accessors (Optional) ---------- */

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }
}