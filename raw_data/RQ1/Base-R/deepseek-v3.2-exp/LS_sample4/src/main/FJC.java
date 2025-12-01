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
    public Book() {
    }

    /**
     * Gets the title of the book.
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the barcode of the book.
     * @return the barcode of the book
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the book.
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the ISBN of the book.
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn the ISBN to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the number of pages in the book.
     * @return the number of pages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     * @param numberOfPages the number of pages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * Gets the number of times this book has been checked out.
     * @return the checkout count
     */
    public int getCheckoutCount() {
        return checkoutCount;
    }

    /**
     * Sets the number of times this book has been checked out.
     * @param checkoutCount the checkout count to set
     */
    public void setCheckoutCount(int checkoutCount) {
        this.checkoutCount = checkoutCount;
    }

    /**
     * Increments the checkout count by 1.
     */
    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

/**
 * Represents a loan record for a book checkout.
 */
class Loan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Default constructor for Loan.
     */
    public Loan() {
    }

    /**
     * Gets the book associated with this loan.
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this loan.
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the user associated with this loan.
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this loan.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the start date of the loan.
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the loan.
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the loan.
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the loan.
     * @param endDate the end date to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

/**
 * Abstract base class for all users in the library system.
 */
abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<Loan> loans;

    /**
     * Default constructor for User.
     */
    public User() {
        this.loans = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the user.
     * @return the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the ID of the user.
     * @return the ID of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the list of loans for this user.
     * @return the list of loans
     */
    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Sets the list of loans for this user.
     * @param loans the list of loans to set
     */
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Adds a loan to the user's loan history.
     * @param loan the loan to add
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }
}

/**
 * Represents a member user in the library system.
 */
class MemberUser extends User {
    /**
     * Default constructor for MemberUser.
     */
    public MemberUser() {
    }
}

/**
 * Represents a guest user in the library system.
 */
class GuestUser extends User {
    /**
     * Default constructor for GuestUser.
     */
    public GuestUser() {
    }
}

/**
 * Main library system class that manages books, users, and loans.
 */
class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    /**
     * Default constructor for LibrarySystem.
     */
    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    /**
     * Gets the list of books in the library.
     * @return the list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books in the library.
     * @param books the list of books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Gets the list of users in the library.
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users in the library.
     * @param users the list of users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of loans in the library.
     * @return the list of loans
     */
    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Sets the list of loans in the library.
     * @param loans the list of loans to set
     */
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Adds a book to the library system.
     * @param book the book to add
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Adds a user to the library system.
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Adds a loan to the library system and updates the book's checkout count.
     * @param loan the loan to add
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.getBook().incrementCheckoutCount();
        loan.getUser().addLoan(loan);
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * @param book the book to count checkouts for
     * @return the number of times the book has been checked out
     */
    public int getBookCheckoutCount(Book book) {
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * @param user the user to check
     * @param startDateStr the start date of the period (format: yyyy-MM-dd)
     * @param endDateStr the end date of the period (format: yyyy-MM-dd)
     * @return the number of unique books borrowed by the user during the specified period
     * @throws IllegalArgumentException if the date format is invalid
     */
    public int getUniqueBooksBorrowedInPeriod(User user, String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            if (!loan.getStartDate().isBefore(startDate) && !loan.getEndDate().isAfter(endDate)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * @param user the user to calculate average page count for
     * @return the average page count of unique books borrowed by the user, or 0 if no books borrowed
     */
    public double getAveragePageCountOfUniqueBooks(User user) {
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
     * Calculates the total number of unique books checked out by a specific user.
     * @param user the user to count unique books for
     * @return the total number of unique books checked out by the user
     */
    public int getTotalUniqueBooksCheckedOut(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (Loan loan : user.getLoans()) {
            uniqueBooks.add(loan.getBook());
        }
        return uniqueBooks.size();
    }
}