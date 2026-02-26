import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Book in the library system.
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
     * Constructor for Book with parameters.
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

    /**
     * Increments the checkout count of the book.
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

/**
 * Represents a User in the library system.
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
     * Constructor for User with parameters.
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

    /**
     * Adds a loan to the user's loan history.
     * 
     * @param loan the loan to be added
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }
}

/**
 * Represents a MEMBER User in the library system.
 */
class MemberUser extends User {
    /**
     * Default constructor for MemberUser.
     */
    public MemberUser() {
        super();
    }

    /**
     * Constructor for MemberUser with parameters.
     * 
     * @param name    the name of the member user
     * @param email   the email of the member user
     * @param address the address of the member user
     * @param id      the ID of the member user
     */
    public MemberUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a GUEST User in the library system.
 */
class GuestUser extends User {
    /**
     * Default constructor for GuestUser.
     */
    public GuestUser() {
        super();
    }

    /**
     * Constructor for GuestUser with parameters.
     * 
     * @param name    the name of the guest user
     * @param email   the email of the guest user
     * @param address the address of the guest user
     * @param id      the ID of the guest user
     */
    public GuestUser(String name, String email, String address, String id) {
        super(name, email, address, id);
    }
}

/**
 * Represents a Loan in the library system.
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
     * Constructor for Loan with parameters.
     * 
     * @param book      the book being loaned
     * @param startDate the start date of the loan
     * @param endDate   the end date of the loan
     */
    public Loan(Book book, String startDate, String endDate) {
        this.book = book;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Getters and Setters
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

/**
 * Manages the library's book collection and user borrowing history.
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
     * Constructor for LibrarySystem with parameters.
     * 
     * @param books the list of books in the library
     * @param users the list of users in the library
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

    /**
     * Adds a book to the library's collection.
     * 
     * @param book the book to be added
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Adds a user to the library's user list.
     * 
     * @param user the user to be added
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * 
     * @param barcode the barcode of the book
     * @return the number of times the book has been checked out
     */
    public int countBookCheckouts(String barcode) {
        for (Book book : books) {
            if (book.getBarcode().equals(barcode)) {
                return book.getCheckoutCount();
            }
        }
        return 0;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * 
     * @param userId    the ID of the user
     * @param startDate the start date of the period
     * @param endDate   the end date of the period
     * @return the number of unique books borrowed by the user during the period
     */
    public int countUniqueBooksBorrowedByUser(String userId, String startDate, String endDate) {
        User user = findUserById(userId);
        if (user == null) return 0;

        Set<String> uniqueBookBarcodes = new HashSet<>();
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Loan loan : user.getLoans()) {
            if ((loan.getStartDate().isAfter(start) || loan.getStartDate().isEqual(start)) &&
                (loan.getEndDate().isBefore(end) || loan.getEndDate().isEqual(end))) {
                uniqueBookBarcodes.add(loan.getBook().getBarcode());
            }
        }
        return uniqueBookBarcodes.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * 
     * @param userId    the ID of the user
     * @return the average page count of the unique books borrowed by the user
     */
    public double calculateAveragePageCount(String userId) {
        User user = findUserById(userId);
        if (user == null) return 0;

        Set<String> uniqueBookBarcodes = new HashSet<>();
        int totalPages = 0;

        for (Loan loan : user.getLoans()) {
            String barcode = loan.getBook().getBarcode();
            if (!uniqueBookBarcodes.contains(barcode)) {
                uniqueBookBarcodes.add(barcode);
                totalPages += loan.getBook().getNumberOfPages();
            }
        }
        return uniqueBookBarcodes.isEmpty() ? 0 : (double) totalPages / uniqueBookBarcodes.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param userId the ID of the user
     * @return the total number of unique books checked out by the user
     */
    public int calculateTotalUniqueBooksCheckedOut(String userId) {
        User user = findUserById(userId);
        if (user == null) return 0;

        Set<String> uniqueBookBarcodes = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            uniqueBookBarcodes.add(loan.getBook().getBarcode());
        }
        return uniqueBookBarcodes.size();
    }

    /**
     * Finds a user by their ID.
     * 
     * @param id the ID of the user
     * @return the user with the specified ID, or null if not found
     */
    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}