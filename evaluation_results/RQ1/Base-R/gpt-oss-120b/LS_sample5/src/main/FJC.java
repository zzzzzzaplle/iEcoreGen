import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a book in the library.
 */
 class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    private int checkoutCount;                 // number of times this book was checked out

    /** Unparameterized constructor required for testing. */
    public Book() {
        this.checkoutCount = 0;
    }

    /** @return the title of the book */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the barcode of the book */
    public String getBarcode() {
        return barcode;
    }

    /** @param barcode the barcode to set */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /** @return the ISBN of the book */
    public String getIsbn() {
        return isbn;
    }

    /** @param isbn the ISBN to set */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /** @return the number of pages */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /** @param numberOfPages the page count to set */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /** @return how many times the book has been checked out */
    public int getCheckoutCount() {
        return checkoutCount;
    }

    /** Increments the checkout counter – called when a new loan is created. */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

/**
 * Abstract base class for a library user (Member or Guest).
 */
public abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<Loan> loans;                  // all loans belonging to this user

    /** Unparameterized constructor required for testing. */
    public User() {
        this.loans = new ArrayList<>();
    }

    /** @return user's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return user's email */
    public String getEmail() {
        return email;
    }

    /** @param email the email to set */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return user's address */
    public String getAddress() {
        return address;
    }

    /** @param address the address to set */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return user's identifier */
    public String getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return an immutable view of the user's loans */
    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    /** Adds a loan to the user's loan list. */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }
}

/**
 * Concrete class for a library member.
 */
 class Member extends User {
    /** Unparameterized constructor required for testing. */
    public Member() {
        super();
    }
}

/**
 * Concrete class for a library guest.
 */
 class Guest extends User {
    /** Unparameterized constructor required for testing. */
    public Guest() {
        super();
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

    /** Unparameterized constructor required for testing. */
    public Loan() {
    }

    /** @return the borrowed book */
    public Book getBook() {
        return book;
    }

    /** @param book the book to set */
    public void setBook(Book book) {
        this.book = book;
    }

    /** @return the user who borrowed the book */
    public User getUser() {
        return user;
    }

    /** @param user the user to set */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return the start date of the loan */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** @param startDate the start date to set */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** @return the end date of the loan */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** @param endDate the end date to set */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

/**
 * Central class that manages books, users and loans.
 */
 class Library {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    /** Unparameterized constructor required for testing. */
    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    /** @return a copy of the book collection */
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    /** Adds a book to the library collection. */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /** @return a copy of the user collection */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /** Adds a user (member or guest) to the library. */
    public void addUser(User user) {
        this.users.add(user);
    }

    /** @return a copy of all recorded loans */
    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Checks out a book for a user.
     *
     * @param user        the user borrowing the book
     * @param book        the book to be borrowed
     * @param startString start date in ISO format (yyyy-MM-dd)
     * @param endString   end date in ISO format (yyyy-MM-dd)
     * @throws IllegalArgumentException if any argument is null or dates are invalid
     */
    public void checkoutBook(User user, Book book, String startString, String endString) {
        if (user == null || book == null || startString == null || endString == null) {
            throw new IllegalArgumentException("User, book and dates must not be null.");
        }

        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startString, fmt);
            end = LocalDate.parse(endString, fmt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Dates must be in format yyyy-MM-dd", e);
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setStartDate(start);
        loan.setEndDate(end);

        // record loan
        this.loans.add(loan);
        user.addLoan(loan);
        book.incrementCheckoutCount();
    }

    /**
     * Returns how many times a specific book has been checked out.
     *
     * @param book the book whose checkout count is requested
     * @return the checkout count
     * @throws IllegalArgumentException if the book is null
     */
    public int getCheckoutCount(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param user the user whose borrowing history is examined
     * @param from start of the period (inclusive)
     * @param to   end of the period (inclusive)
     * @return number of distinct books borrowed in the period
     * @throws IllegalArgumentException if arguments are null or period is invalid
     */
    public int getUniqueBooksBorrowed(User user, LocalDate from, LocalDate to) {
        if (user == null || from == null || to == null) {
            throw new IllegalArgumentException("User and dates must not be null.");
        }
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            // loan overlaps the period?
            if (!loan.getEndDate().isBefore(from) && !loan.getStartDate().isAfter(to)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @param user the user whose average page count is required
     * @return average number of pages; 0.0 if the user has not borrowed any books
     * @throws IllegalArgumentException if user is null
     */
    public double getAveragePageCount(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            uniqueBooks.add(loan.getBook());
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
     * Calculates the total number of unique books checked out by a specific user (overall history).
     *
     * @param user the user to evaluate
     * @return count of distinct books ever borrowed by the user
     * @throws IllegalArgumentException if user is null
     */
    public int getTotalUniqueBooks(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }

        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            uniqueBooks.add(loan.getBook());
        }
        return uniqueBooks.size();
    }
}