import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a book in the library.
 */
 class Book {
    private String title;
    private String barcode;   // unique identifier
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

    // ---------- Getters & Setters ----------
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

    // ---------- Equality based on barcode ----------
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
 * Abstract base class for all users of the library.
 */
public abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;   // unique identifier

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

    // ---------- Getters & Setters ----------
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

    // Equality based on id
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
 * Represents a member user.
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
    private String startDate; // format yyyy-MM-dd
    private String endDate;   // format yyyy-MM-dd

    /** Unparameterized constructor. */
    public Loan() {
    }

    /** Parameterized constructor for convenience. */
    public Loan(Book book, User user, String startDate, String endDate) {
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // ---------- Getters & Setters ----------
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

    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the loan.
     *
     * @param startDate date in format yyyy-MM-dd
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the loan.
     *
     * @param endDate date in format yyyy-MM-dd
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

/**
 * Core class that manages books, users and loans.
 */
 class LibrarySystem {
    private List<Loan> loans = new ArrayList<>();

    /** Unparameterized constructor. */
    public LibrarySystem() {
    }

    /**
     * Adds a new loan record to the system.
     *
     * @param loan the loan to be added
     */
    public void addLoan(Loan loan) {
        if (loan != null) {
            loans.add(loan);
        }
    }

    /**
     * Counts the total number of times a specific book has been checked out.
     *
     * @param book the book to count check‑outs for
     * @return the number of loan records that reference the given book
     */
    public int countCheckouts(Book book) {
        if (book == null) {
            return 0;
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
     * Determines how many unique books a particular user borrowed during a specified period.
     *
     * @param user the user whose borrowing activity is examined
     * @param periodStart start of the period (inclusive) in format yyyy-MM-dd
     * @param periodEnd   end of the period (inclusive) in format yyyy-MM-dd
     * @return the count of distinct books borrowed by the user in the given period
     * @throws IllegalArgumentException if date strings cannot be parsed or periodStart is after periodEnd
     */
    public int countUniqueBooksBorrowedByUserDuringPeriod(User user, String periodStart, String periodEnd) {
        if (user == null) {
            return 0;
        }

        LocalDate start = parseDate(periodStart);
        LocalDate end = parseDate(periodEnd);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("periodStart must be on or before periodEnd");
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (!user.equals(loan.getUser())) {
                continue;
            }
            LocalDate loanStart = parseDate(loan.getStartDate());
            LocalDate loanEnd = parseDate(loan.getEndDate());

            // Check if loan interval overlaps the requested period
            if (!loanEnd.isBefore(start) && !loanStart.isAfter(end)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user (across all time).
     *
     * @param user the user whose borrowed books are analysed
     * @return the average number of pages; returns 0.0 if the user has not borrowed any books
     */
    public double averagePageCountOfUniqueBooksBorrowedByUser(User user) {
        if (user == null) {
            return 0.0;
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
        for (Book b : uniqueBooks) {
            totalPages += b.getNumberOfPages();
        }
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user (across all time).
     *
     * @param user the user whose borrowing history is examined
     * @return the count of distinct books the user has ever borrowed
     */
    public int totalUniqueBooksBorrowedByUser(User user) {
        if (user == null) {
            return 0;
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : loans) {
            if (user.equals(loan.getUser())) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    // -----------------------------------------------------------------------
    // Helper method(s)
    // -----------------------------------------------------------------------

    /**
     * Parses a date string in the format yyyy-MM-dd into a {@link LocalDate}.
     *
     * @param dateStr the date string
     * @return the parsed {@link LocalDate}
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date format, expected yyyy-MM-dd: " + dateStr, ex);
        }
    }

    /**
     * Returns the internal list of loans (read‑only copy) – useful for testing.
     *
     * @return a copy of the list of loans
     */
    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Replaces the current loan collection with a new one.
     *
     * @param loans the new list of loans
     */
    public void setLoans(List<Loan> loans) {
        if (loans != null) {
            this.loans = new ArrayList<>(loans);
        } else {
            this.loans = new ArrayList<>();
        }
    }
}