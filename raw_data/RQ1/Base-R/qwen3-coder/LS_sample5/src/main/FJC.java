import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Represents a book in the library system
 */
class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int pages;
    
    /**
     * Default constructor for Book
     */
    public Book() {
        this.title = "";
        this.barcode = "";
        this.isbn = "";
        this.pages = 0;
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
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
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
 * Represents a user in the library system
 */
abstract class User {
    private String name;
    private String email;
    private String address;
    private String id;
    
    /**
     * Default constructor for User
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
}

/**
 * Represents a member user
 */
class Member extends User {
    /**
     * Default constructor for Member
     */
    public Member() {
        super();
    }
}

/**
 * Represents a guest user
 */
class Guest extends User {
    /**
     * Default constructor for Guest
     */
    public Guest() {
        super();
    }
}

/**
 * Represents a book loan record
 */
class Loan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    
    /**
     * Default constructor for Loan
     */
    public Loan() {
        this.book = new Book();
        this.user = new Member(); // Default to Member
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
    }
    
    // Getters and setters
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
        if (book == null) {
            this.book = new Book();
        }
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
        if (user == null) {
            this.user = new Member();
        }
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if (startDate == null) {
            this.startDate = LocalDate.now();
        }
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if (endDate == null) {
            this.endDate = LocalDate.now();
        }
    }
}

/**
 * Main library system that manages books, users, and loans
 */
class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;
    
    /**
     * Default constructor for LibrarySystem
     */
    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    
    // Getters and setters for collections
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books != null ? books : new ArrayList<>();
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users != null ? users : new ArrayList<>();
    }
    
    public List<Loan> getLoans() {
        return loans;
    }
    
    public void setLoans(List<Loan> loans) {
        this.loans = loans != null ? loans : new ArrayList<>();
    }
    
    /**
     * Count the number of times a specific book has been checked out
     * 
     * @param book The book to count checkouts for
     * @return The number of times the book has been checked out
     */
    public int countBookCheckouts(Book book) {
        if (book == null) {
            return 0;
        }
        
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getBook() != null && loan.getBook().equals(book)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Determine how many unique books a particular user has borrowed during a specified period
     * 
     * @param user The user to check
     * @param startDate The start date of the period (inclusive)
     * @param endDate The end date of the period (inclusive)
     * @return The number of unique books borrowed by the user in the specified period
     * @throws IllegalArgumentException if startDate or endDate is null
     */
    public int countUniqueBooksBorrowedByUserInPeriod(User user, LocalDate startDate, LocalDate endDate) {
        if (user == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("User and date range cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : loans) {
            // Check if loan belongs to the user and is within the date range
            if (loan.getUser() != null && loan.getUser().equals(user) &&
                loan.getStartDate() != null && loan.getEndDate() != null &&
                !loan.getStartDate().isAfter endDate && 
                !loan.getEndDate().isBefore(startDate)) {
                
                if (loan.getBook() != null) {
                    uniqueBooks.add(loan.getBook());
                }
            }
        }
        
        return uniqueBooks.size();
    }
    
    /**
     * Calculate the average page count of the unique books borrowed by a specific user
     * 
     * @param user The user to calculate average for
     * @return The average page count of unique books borrowed by the user, or 0 if no books borrowed
     * @throws IllegalArgumentException if user is null
     */
    public double calculateAveragePageCountForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        
        // Collect all unique books borrowed by the user
        for (Loan loan : loans) {
            if (loan.getUser() != null && loan.getUser().equals(user) && loan.getBook() != null) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }
        
        int totalPages = 0;
        for (Book book : uniqueBooks) {
            totalPages += book.getPages();
        }
        
        return (double) totalPages / uniqueBooks.size();
    }
    
    /**
     * Calculate the total number of unique books checked out by a specific user
     * 
     * @param user The user to check
     * @return The number of unique books checked out by the user
     * @throws IllegalArgumentException if user is null
     */
    public int countUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        
        for (Loan loan : loans) {
            if (loan.getUser() != null && loan.getUser().equals(user) && loan.getBook() != null) {
                uniqueBooks.add(loan.getBook());
            }
        }
        
        return uniqueBooks.size();
    }
}