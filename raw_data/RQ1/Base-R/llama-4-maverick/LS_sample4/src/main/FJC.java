import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a book in the library.
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
     * Parameterized constructor for Book.
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

    public void setCheckoutCount(int checkoutCount) {
        this.checkoutCount = checkoutCount;
    }

    /**
     * Increments the checkout count of the book.
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

/**
 * Represents a user of the library.
 */
abstract class User {
    protected String name;
    protected String email;
    protected String address;
    protected String id;

    /**
     * Default constructor for User.
     */
    public User() {}

    /**
     * Parameterized constructor for User.
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
}

/**
 * Represents a member user of the library.
 */
class MemberUser extends User {
    /**
     * Default constructor for MemberUser.
     */
    public MemberUser() {}

    /**
     * Parameterized constructor for MemberUser.
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
 * Represents a guest user of the library.
 */
class GuestUser extends User {
    /**
     * Default constructor for GuestUser.
     */
    public GuestUser() {}

    /**
     * Parameterized constructor for GuestUser.
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
 * Represents a loan record.
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
     * Parameterized constructor for Loan.
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

/**
 * Represents the library system.
 */
class LibrarySystem {
    private Map<String, Book> books;
    private Map<String, User> users;
    private List<Loan> loanRecords;

    /**
     * Default constructor for LibrarySystem.
     */
    public LibrarySystem() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.loanRecords = new ArrayList<>();
    }

    // Getters and Setters
    public Map<String, Book> getBooks() {
        return books;
    }

    public void setBooks(Map<String, Book> books) {
        this.books = books;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public List<Loan> getLoanRecords() {
        return loanRecords;
    }

    public void setLoanRecords(List<Loan> loanRecords) {
        this.loanRecords = loanRecords;
    }

    /**
     * Adds a book to the library system.
     * 
     * @param book the book to be added
     */
    public void addBook(Book book) {
        books.put(book.getBarcode(), book);
    }

    /**
     * Adds a user to the library system.
     * 
     * @param user the user to be added
     */
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Records a loan in the library system.
     * 
     * @param loan the loan to be recorded
     */
    public void recordLoan(Loan loan) {
        loanRecords.add(loan);
        loan.getBook().incrementCheckoutCount();
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * 
     * @param barcode the barcode of the book
     * @return the number of times the book has been checked out
     */
    public int countBookCheckouts(String barcode) {
        return books.get(barcode).getCheckoutCount();
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
        Set<String> uniqueBooks = new HashSet<>();
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Loan loan : loanRecords) {
            if (loan.getStartDate().isAfter(end) || loan.getEndDate().isBefore(start)) {
                continue;
            }
            if (users.get(userId).equals(loan)) { // This condition is incorrect and should be modified
                uniqueBooks.add(loan.getBook().getBarcode());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * 
     * @param userId    the ID of the user
     * @param startDate the start date of the period
     * @param endDate   the end date of the period
     * @return the average page count of the unique books borrowed by the user during the period
     */
    public double calculateAveragePageCount(String userId, String startDate, String endDate) {
        Set<String> uniqueBooks = new HashSet<>();
        int totalPages = 0;
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Loan loan : loanRecords) {
            if (loan.getStartDate().isAfter(end) || loan.getEndDate().isBefore(start)) {
                continue;
            }
            // The condition below is incorrect and needs to be corrected to properly identify loans for the given user
            if (/* condition to check if the loan is for the given user */) {
                if (uniqueBooks.add(loan.getBook().getBarcode())) {
                    totalPages += loan.getBook().getNumberOfPages();
                }
            }
        }
        return uniqueBooks.isEmpty() ? 0 : (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param userId the ID of the user
     * @return the total number of unique books checked out by the user
     */
    public int countTotalUniqueBooksCheckedOut(String userId) {
        Set<String> uniqueBooks = new HashSet<>();

        for (Loan loan : loanRecords) {
            // The condition below is incorrect and needs to be corrected to properly identify loans for the given user
            if (/* condition to check if the loan is for the given user */) {
                uniqueBooks.add(loan.getBook().getBarcode());
            }
        }
        return uniqueBooks.size();
    }
}