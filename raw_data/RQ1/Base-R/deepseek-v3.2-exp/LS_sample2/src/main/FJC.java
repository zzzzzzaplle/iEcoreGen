import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a book in the library system
 */
class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    private int checkoutCount;

    /**
     * Default constructor
     */
    public Book() {
    }

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
     * Increments the checkout count for this book
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

/**
 * Represents a user in the library system
 */
abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<Loan> loans;

    /**
     * Default constructor
     */
    public User() {
        this.loans = new ArrayList<>();
    }

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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Adds a loan to the user's loan history
     * @param loan the loan to add
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.getBook().incrementCheckoutCount();
    }
}

/**
 * Represents a member user
 */
class MemberUser extends User {
    /**
     * Default constructor
     */
    public MemberUser() {
    }
}

/**
 * Represents a guest user
 */
class GuestUser extends User {
    /**
     * Default constructor
     */
    public GuestUser() {
    }
}

/**
 * Represents a book loan with start and end dates
 */
class Loan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Default constructor
     */
    public Loan() {
    }

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

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

/**
 * Main library system class that manages books, users, and loans
 */
class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    /**
     * Default constructor
     */
    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Adds a book to the library system
     * @param book the book to add
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Adds a user to the library system
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Adds a loan to the library system and associates it with the user
     * @param loan the loan to add
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.getUser().addLoan(loan);
    }

    /**
     * Counts the number of times a specific book has been checked out
     * @param book the book to check checkout count for
     * @return the number of times the book has been checked out
     */
    public int getBookCheckoutCount(Book book) {
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period
     * @param user the user to check
     * @param startDate the start date of the period (format: yyyy-MM-dd)
     * @param endDate the end date of the period (format: yyyy-MM-dd)
     * @return the number of unique books borrowed by the user during the specified period
     */
    public int getUniqueBooksBorrowedInPeriod(User user, String startDate, String endDate) {
        LocalDate periodStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate periodEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : user.getLoans()) {
            LocalDate loanStart = loan.getStartDate();
            LocalDate loanEnd = loan.getEndDate();
            
            // Check if the loan overlaps with the specified period
            if (!loanEnd.isBefore(periodStart) && !loanStart.isAfter(periodEnd)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * @param user the user to calculate average page count for
     * @return the average page count of unique books borrowed by the user, or 0 if no books borrowed
     */
    public double getAveragePageCountOfBorrowedBooks(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        int totalPages = 0;
        
        for (Loan loan : user.getLoans()) {
            Book book = loan.getBook();
            if (uniqueBooks.add(book)) {
                totalPages += book.getNumberOfPages();
            }
        }
        
        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }
        
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user
     * @param user the user to count unique books for
     * @return the total number of unique books checked out by the user
     */
    public int getTotalUniqueBooksBorrowed(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : user.getLoans()) {
            uniqueBooks.add(loan.getBook());
        }
        
        return uniqueBooks.size();
    }
}