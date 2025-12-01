import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Enum representing the type of user in the library system
 */
enum UserType {
    MEMBER,
    GUEST
}

/**
 * Represents a book in the library system
 */
class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;
    
    /**
     * Default constructor
     */
    public Book() {
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
    
    public String getISBN() {
        return ISBN;
    }
    
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    
    public int getNumberOfPages() {
        return numberOfPages;
    }
    
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    
    /**
     * Compares this book to another object for equality based on ISBN
     * @param obj the object to compare with
     * @return true if the books have the same ISBN, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(ISBN, book.ISBN);
    }
    
    /**
     * Generates hash code based on ISBN
     * @return hash code of the book
     */
    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
}

/**
 * Represents a book checkout record with start and end dates
 */
class CheckOut {
    private LocalDate startDate;
    private LocalDate endDate;
    private Book book;
    
    /**
     * Default constructor
     */
    public CheckOut() {
    }
    
    // Getters and setters
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
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    /**
     * Sets start date from string in format yyyy-MM-dd
     * @param startDateString the start date as string
     */
    public void setStartDate(String startDateString) {
        this.startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    /**
     * Sets end date from string in format yyyy-MM-dd
     * @param endDateString the end date as string
     */
    public void setEndDate(String endDateString) {
        this.endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}

/**
 * Represents a user in the library system
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;
    
    /**
     * Default constructor
     */
    public User() {
        this.checkOuts = new ArrayList<>();
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
    
    public String getID() {
        return ID;
    }
    
    public void setID(String ID) {
        this.ID = ID;
    }
    
    public UserType getType() {
        return type;
    }
    
    public void setType(UserType type) {
        this.type = type;
    }
    
    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }
    
    public void setCheckOuts(List<CheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }
    
    /**
     * Adds a checkout record to the user's checkout history
     * @param checkOut the checkout record to add
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOuts == null) {
            checkOuts = new ArrayList<>();
        }
        checkOuts.add(checkOut);
    }
}

/**
 * Main library system class that manages books and users
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;
    
    /**
     * Default constructor
     */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }
    
    // Getters and setters
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    /**
     * Adds a user to the library system
     * @param user the user to add
     */
    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
    
    /**
     * Adds a book to the library system
     * @param book the book to add
     */
    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
    }
    
    /**
     * Counts the number of times a specific book has been checked out by all users
     * @param book the book to count checkouts for
     * @return the total number of times the book has been checked out
     */
    public int countBookCheckOuts(Book book) {
        if (users == null || book == null) {
            return 0;
        }
        
        int count = 0;
        for (User user : users) {
            if (user.getCheckOuts() != null) {
                for (CheckOut checkOut : user.getCheckOuts()) {
                    if (checkOut.getBook() != null && checkOut.getBook().equals(book)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    /**
     * Determines how many unique books a particular user has borrowed during a specified period
     * @param user the user to check
     * @param start the start date of the period (inclusive)
     * @param end the end date of the period (inclusive)
     * @return the number of unique books borrowed by the user during the specified period
     */
    public int uniqueBooksBorrowedByUser(User user, LocalDate start, LocalDate end) {
        if (user == null || user.getCheckOuts() == null || start == null || end == null) {
            return 0;
        }
        
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            LocalDate checkoutDate = checkOut.getStartDate();
            if (checkoutDate != null && 
                !checkoutDate.isBefore(start) && 
                !checkoutDate.isAfter(end)) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }
    
    /**
     * Overloaded method to determine unique books borrowed by user using string dates
     * @param user the user to check
     * @param startString the start date string in format yyyy-MM-dd
     * @param endString the end date string in format yyyy-MM-dd
     * @return the number of unique books borrowed by the user during the specified period
     * @throws IllegalArgumentException if date strings are in invalid format
     */
    public int uniqueBooksBorrowedByUser(User user, String startString, String endString) {
        LocalDate start = LocalDate.parse(startString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(endString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return uniqueBooksBorrowedByUser(user, start, end);
    }
    
    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * @param user the user to calculate for
     * @return the average page count of unique books borrowed by the user, or 0 if no books borrowed
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null || user.getCheckOuts() == null || user.getCheckOuts().isEmpty()) {
            return 0.0;
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (checkOut.getBook() != null) {
                uniqueBooks.add(checkOut.getBook());
            }
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
     * Calculates the total number of unique books checked out by a specific user
     * @param user the user to calculate for
     * @return the total number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null || user.getCheckOuts() == null) {
            return 0;
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (checkOut.getBook() != null) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }
}