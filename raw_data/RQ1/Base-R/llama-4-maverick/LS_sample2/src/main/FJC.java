import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    /**
     * Gets the title of the book.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * 
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the barcode of the book.
     * 
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the book.
     * 
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the ISBN of the book.
     * 
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * 
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the number of pages in the book.
     * 
     * @return the numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     * 
     * @param numberOfPages the numberOfPages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * Gets the checkout count of the book.
     * 
     * @return the checkoutCount
     */
    public int getCheckoutCount() {
        return checkoutCount;
    }

    /**
     * Increments the checkout count of the book.
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
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

    /**
     * Gets the name of the user.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the user.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * 
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the ID of the user.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of loans associated with the user.
     * 
     * @return the loans
     */
    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Adds a loan to the user's list of loans.
     * 
     * @param loan the loan to add
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
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
 * Represents a loan in the library system.
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
    public Loan(Book book, String startDate, String endDate) throws DateTimeParseException {
        this.book = book;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        book.incrementCheckoutCount();
    }

    /**
     * Gets the book associated with the loan.
     * 
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with the loan.
     * 
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the start date of the loan.
     * 
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the loan.
     * 
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the loan.
     * 
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the loan.
     * 
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

/**
 * Provides utility methods for the library system.
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
     * @param books the list of books in the system
     * @param users the list of users in the system
     */
    public LibrarySystem(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
    }

    /**
     * Gets the list of books in the system.
     * 
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Adds a book to the system's list of books.
     * 
     * @param book the book to add
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Gets the list of users in the system.
     * 
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Adds a user to the system's list of users.
     * 
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * 
     * @param book the book to check
     * @return the number of times the book has been checked out
     */
    public int countBookCheckouts(Book book) {
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * 
     * @param user      the user to check
     * @param startDate the start date of the period
     * @param endDate   the end date of the period
     * @return the number of unique books borrowed by the user during the period
     */
    public int countUniqueBooksBorrowed(User user, String startDate, String endDate) {
        Set<Book> uniqueBooks = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        for (Loan loan : user.getLoans()) {
            if ((loan.getStartDate().isAfter(start) || loan.getStartDate().isEqual(start))
                    && (loan.getEndDate().isBefore(end) || loan.getEndDate().isEqual(end))) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * 
     * @param user      the user to check
     * @param startDate the start date of the period
     * @param endDate   the end date of the period
     * @return the average page count of the unique books borrowed by the user during the period
     */
    public double calculateAveragePageCount(User user, String startDate, String endDate) {
        Set<Book> uniqueBooks = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        for (Loan loan : user.getLoans()) {
            if ((loan.getStartDate().isAfter(start) || loan.getStartDate().isEqual(start))
                    && (loan.getEndDate().isBefore(end) || loan.getEndDate().isEqual(end))) {
                uniqueBooks.add(loan.getBook());
            }
        }

        if (uniqueBooks.isEmpty()) {
            return 0;
        }

        int totalPages = 0;
        for (Book book : uniqueBooks) {
            totalPages += book.getNumberOfPages();
        }
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param user the user to check
     * @return the total number of unique books checked out by the user
     */
    public int calculateTotalUniqueBooksCheckedOut(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            uniqueBooks.add(loan.getBook());
        }
        return uniqueBooks.size();
    }
}