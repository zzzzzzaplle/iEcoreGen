import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents a book in the library system.
 */
class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    
    /**
     * Default constructor for Book.
     */
    public Book() {
        this.title = "";
        this.barcode = "";
        this.isbn = "";
        this.numberOfPages = 0;
    }
    
    // Getters and setters
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(barcode, book.barcode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}

/**
 * Represents a user in the library system.
 */
class User {
    private String name;
    private String email;
    private String address;
    private String id;
    
    /**
     * Default constructor for User.
     */
    public User() {
        this.name = "";
        this.email = "";
        this.address = "";
        this.id = "";
    }
    
    // Getters and setters
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a guest user in the library system.
 */
class Guest extends User {
    /**
     * Default constructor for Guest.
     */
    public Guest() {
        super();
    }
}

/**
 * Represents a member user in the library system.
 */
class Member extends User {
    /**
     * Default constructor for Member.
     */
    public Member() {
        super();
    }
}

/**
 * Represents a book loan record in the library system.
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
        this.book = new Book();
        this.user = new User();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
    }
    
    // Getters and setters
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
 * Main library system that manages books, users, and loans.
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
    
    // Getters and setters
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
     * Counts the number of times a specific book has been checked out.
     * 
     * @param book The book to count checkouts for
     * @return The number of times the book has been checked out
     */
    public int countBookCheckouts(Book book) {
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
     * 
     * @param user The user to check
     * @param startDate The start date of the period (inclusive)
     * @param endDate The end date of the period (inclusive)
     * @return The number of unique books borrowed by the user during the specified period
     */
    public int countUniqueBooksBorrowedByUserInPeriod(User user, LocalDate startDate, LocalDate endDate) {
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : loans) {
            // Check if the loan belongs to the user and falls within the date range
            if (loan.getUser().equals(user) && 
                !loan.getStartDate().isBefore(startDate) && 
                !loan.getStartDate().isAfter(endDate)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        return uniqueBooks.size();
    }
    
    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * 
     * @param user The user to calculate the average for
     * @return The average page count of unique books borrowed by the user, or 0 if no books were borrowed
     */
    public double calculateAveragePageCountForUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        
        // Collect all unique books borrowed by the user
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        if (uniqueBooks.isEmpty()) {
            return 0;
        }
        
        // Calculate the average page count
        int totalPageCount = 0;
        for (Book book : uniqueBooks) {
            totalPageCount += book.getNumberOfPages();
        }
        
        return (double) totalPageCount / uniqueBooks.size();
    }
    
    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param user The user to check
     * @return The number of unique books checked out by the user
     */
    public int countUniqueBooksCheckedOutByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        return uniqueBooks.size();
    }
}