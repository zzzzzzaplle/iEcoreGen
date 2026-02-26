import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a book in the library.
 */
 class Book {

    private String title;
    private String barcode;
    private String isbn;
    private int pages;

    /** Unparameterized constructor */
    public Book() {
    }

    /** Parameterized constructor – convenience only */
    public Book(String title, String barcode, String isbn, int pages) {
        this.title = title;
        this.barcode = barcode;
        this.isbn = isbn;
        this.pages = pages;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    // -------------------------------------------------------------------------
    // Equality – a book is identified uniquely by its barcode.
    // -------------------------------------------------------------------------

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
 * Base class for all users of the library.
 */
public abstract class User {

    private String name;
    private String email;
    private String address;
    private String id; // unique identifier

    /** Unparameterized constructor */
    public User() {
    }

    /** Parameterized constructor – convenience only */
    public User(String name, String email, String address, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Equality – a user is identified uniquely by its id.
    // -------------------------------------------------------------------------

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

    /** Unparameterized constructor */
    public MemberUser() {
        super();
    }

    /** Parameterized constructor – convenience only */
    public MemberUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a guest user.
 */
 class GuestUser extends User {

    /** Unparameterized constructor */
    public GuestUser() {
        super();
    }

    /** Parameterized constructor – convenience only */
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
    private LocalDate startDate; // loan start
    private LocalDate endDate;   // loan due/return date

    /** Unparameterized constructor */
    public Loan() {
    }

    /** Parameterized constructor – convenience only */
    public Loan(Book book, User user, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
 * Central class that manages books, users and loans.
 * It also implements the functional requirements described in the specification.
 */
 class LibrarySystem {

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    /** Unparameterized constructor */
    public LibrarySystem() {
    }

    // -------------------------------------------------------------------------
    // Helper methods to add data – used by tests or UI code
    // -------------------------------------------------------------------------

    /**
     * Adds a book to the library collection.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Adds a user (member or guest) to the system.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Records a loan (checkout) of a book by a user.
     *
     * @param loan the loan to record
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    // -------------------------------------------------------------------------
    // Functional Requirement Implementations
    // -------------------------------------------------------------------------

    /**
     * Counts the total number of times the given book has been checked out.
     *
     * @param book the book whose checkout count is required
     * @return the number of loan records that reference the given book
     */
    public int getCheckoutCount(Book book) {
        if (book == null) {
            return 0;
        }
        return (int) loans.stream()
                .filter(l -> book.equals(l.getBook()))
                .count();
    }

    /**
     * Determines how many *unique* books a particular user has borrowed during a
     * specified period (inclusive). A loan belongs to the period if its start
     * date is on or after {@code periodStart} and its end date is on or before
     * {@code periodEnd}.
     *
     * @param user        the user whose borrowing activity is examined
     * @param periodStart the start of the period (inclusive)
     * @param periodEnd   the end of the period (inclusive)
     * @return the count of distinct books borrowed by the user in the period
     */
    public int getUniqueBooksBorrowedByUserWithinPeriod(User user,
                                                        LocalDate periodStart,
                                                        LocalDate periodEnd) {
        if (user == null || periodStart == null || periodEnd == null) {
            return 0;
        }

        Set<Book> distinctBooks = loans.stream()
                .filter(l -> user.equals(l.getUser()))
                .filter(l -> !l.getStartDate().isBefore(periodStart) && !l.getEndDate().isAfter(periodEnd))
                .map(Loan::getBook)
                .collect(Collectors.toSet());

        return distinctBooks.size();
    }

    /**
     * Calculates the average page count of the *unique* books borrowed by a
     * specific user (across all time). If the user has not borrowed any books,
     * the method returns {@code 0.0}.
     *
     * @param user the user whose average page count is required
     * @return the average number of pages of distinct books borrowed by the user
     */
    public double getAveragePageCountOfUniqueBooksBorrowedByUser(User user) {
        if (user == null) {
            return 0.0;
        }

        Set<Book> distinctBooks = loans.stream()
                .filter(l -> user.equals(l.getUser()))
                .map(Loan::getBook)
                .collect(Collectors.toSet());

        if (distinctBooks.isEmpty()) {
            return 0.0;
        }

        int totalPages = distinctBooks.stream()
                .mapToInt(Book::getPages)
                .sum();

        return (double) totalPages / distinctBooks.size();
    }

    /**
     * Calculates the total number of *unique* books that have ever been checked
     * out by a specific user.
     *
     * @param user the user whose unique checkout total is required
     * @return the count of distinct books borrowed by the user
     */
    public int getTotalUniqueBooksBorrowedByUser(User user) {
        if (user == null) {
            return 0;
        }

        Set<Book> distinctBooks = loans.stream()
                .filter(l -> user.equals(l.getUser()))
                .map(Loan::getBook)
                .collect(Collectors.toSet());

        return distinctBooks.size();
    }

    // -------------------------------------------------------------------------
    // Additional getters for test verification (optional)
    // -------------------------------------------------------------------------

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}