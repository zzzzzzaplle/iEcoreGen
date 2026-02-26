import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * Default constructor for Book.
     */
    public Book() {}

    /**
     * Constructs a Book with the given details.
     * 
     * @param title         the title of the book
     * @param barcode       the barcode of the book
     * @param isbn          the ISBN of the book
     * @param numberOfPages the number of pages in the book
     */
    public Book(String title, String barcode, String isbn, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.checkoutCount = 0;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }
    public int getCheckoutCount() { return checkoutCount; }
    public void setCheckoutCount(int checkoutCount) { this.checkoutCount = checkoutCount; }
    public void incrementCheckoutCount() { this.checkoutCount++; }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", barcode='" + barcode + '\'' +
                ", isbn='" + isbn + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}

/**
 * Represents a user in the library system.
 */
abstract class User {
    protected String name;
    protected String email;
    protected String address;
    protected String id;
    protected List<Loan> loans;

    /**
     * Default constructor for User.
     */
    public User() {
        this.loans = new ArrayList<>();
    }

    /**
     * Constructs a User with the given details.
     * 
     * @param name    the name of the user
     * @param email   the email of the user
     * @param address the address of the user
     * @param id      the ID of the user
     */
    public User(String name, String email, String address, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
        this.loans = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<Loan> getLoans() { return loans; }
    public void setLoans(List<Loan> loans) { this.loans = loans; }
    public void addLoan(Loan loan) { this.loans.add(loan); }

    /**
     * Calculates the number of unique books borrowed by this user during a specified period.
     * 
     * @param startDate the start date of the period (inclusive)
     * @param endDate   the end date of the period (inclusive)
     * @return the number of unique books borrowed
     */
    public int countUniqueBooksBorrowed(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        Set<String> uniqueIsbns = new HashSet<>();
        for (Loan loan : loans) {
            if (!loan.getStartDate().isAfter(end) && !loan.getEndDate().isBefore(start)) {
                uniqueIsbns.add(loan.getBook().getIsbn());
            }
        }
        return uniqueIsbns.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by this user.
     * 
     * @return the average page count
     */
    public double calculateAveragePageCount() {
        Set<String> uniqueIsbns = new HashSet<>();
        int totalPages = 0;
        for (Loan loan : loans) {
            if (uniqueIsbns.add(loan.getBook().getIsbn())) {
                totalPages += loan.getBook().getNumberOfPages();
            }
        }
        return uniqueIsbns.isEmpty() ? 0 : (double) totalPages / uniqueIsbns.size();
    }

    /**
     * Calculates the total number of unique books checked out by this user.
     * 
     * @return the total number of unique books
     */
    public int countTotalUniqueBooks() {
        Set<String> uniqueIsbns = new HashSet<>();
        for (Loan loan : loans) {
            uniqueIsbns.add(loan.getBook().getIsbn());
        }
        return uniqueIsbns.size();
    }
}

/**
 * Represents a MEMBER user in the library system.
 */
class MemberUser extends User {
    /**
     * Default constructor for MemberUser.
     */
    public MemberUser() {
        super();
    }

    /**
     * Constructs a MemberUser with the given details.
     * 
     * @param name    the name of the user
     * @param email   the email of the user
     * @param address the address of the user
     * @param id      the ID of the user
     */
    public MemberUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a GUEST user in the library system.
 */
class GuestUser extends User {
    /**
     * Default constructor for GuestUser.
     */
    public GuestUser() {
        super();
    }

    /**
     * Constructs a GuestUser with the given details.
     * 
     * @param name    the name of the user
     * @param email   the email of the user
     * @param address the address of the user
     * @param id      the ID of the user
     */
    public GuestUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a loan of a book to a user.
 */
class Loan {
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Default constructor for Loan.
     */
    public Loan() {}

    /**
     * Constructs a Loan with the given details.
     * 
     * @param book      the book being loaned
     * @param startDate the start date of the loan
     * @param endDate   the end date of the loan
     */
    public Loan(Book book, String startDate, String endDate) {
        this.book = book;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
    }

    // Getters and Setters
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
    }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.endDate = LocalDate.parse(endDate, formatter);
    }
}

/**
 * Manages the library's collection of books and user loans.
 */
class LibrarySystem {
    private List<Book> books;
    private List<User> users;

    /**
     * Default constructor for LibrarySystem.
     */
    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Constructs a LibrarySystem with the given books and users.
     * 
     * @param books the list of books
     * @param users the list of users
     */
    public LibrarySystem(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
    }

    // Getters and Setters
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
    public void addBook(Book book) { this.books.add(book); }
    public void addUser(User user) { this.users.add(user); }

    /**
     * Counts the number of times a specific book has been checked out.
     * 
     * @param isbn the ISBN of the book
     * @return the number of times the book has been checked out
     */
    public int countBookCheckouts(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book.getCheckoutCount();
            }
        }
        return 0;
    }

    /**
     * Checks out a book to a user.
     * 
     * @param user  the user checking out the book
     * @param book  the book being checked out
     * @param startDate the start date of the loan
     * @param endDate   the end date of the loan
     */
    public void checkoutBook(User user, Book book, String startDate, String endDate) {
        Loan loan = new Loan(book, startDate, endDate);
        user.addLoan(loan);
        book.incrementCheckoutCount();
    }
}