import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Enum representing different types of users in the library system
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

    /**
     * Gets the book title
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book title
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the book barcode
     * @return the barcode of the book
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the book barcode
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the book ISBN
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the book ISBN
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets the number of pages in the book
     * @return the number of pages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book
     * @param numberOfPages the number of pages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}

/**
 * Represents a checkout record for a book loan
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

    /**
     * Gets the start date of the checkout
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the checkout
     * @param startDate the start date to set (format: yyyy-MM-dd)
     */
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Sets the start date using LocalDate object
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the checkout
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the checkout
     * @param endDate the end date to set (format: yyyy-MM-dd)
     */
    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Sets the end date using LocalDate object
     * @param endDate the end date to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the book associated with this checkout
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this checkout
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
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

    /**
     * Gets the user's name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the user's address
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the user's ID
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the user's ID
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the user type
     * @return the user type
     */
    public UserType getType() {
        return type;
    }

    /**
     * Sets the user type
     * @param type the user type to set
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Gets the list of checkouts for this user
     * @return the list of checkouts
     */
    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    /**
     * Sets the list of checkouts for this user
     * @param checkOuts the list of checkouts to set
     */
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
 * Main library system that manages books, users, and checkout operations
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

    /**
     * Gets the list of users in the system
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users in the system
     * @param users the list of users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of books in the system
     * @return the list of books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books in the system
     * @param books the list of books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Adds a user to the system
     * @param user the user to add
     */
    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    /**
     * Adds a book to the system
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
     * @throws IllegalArgumentException if the book parameter is null
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
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
     * @param startPeriod the start date of the period (format: yyyy-MM-dd)
     * @param endPeriod the end date of the period (format: yyyy-MM-dd)
     * @return the number of unique books borrowed by the user during the specified period
     * @throws IllegalArgumentException if user parameter is null or dates are invalid
     */
    public int uniqueBooksBorrowedByUser(User user, String startPeriod, String endPeriod) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        LocalDate startDate = LocalDate.parse(startPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(endPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        if (user.getCheckOuts() != null) {
            for (CheckOut checkOut : user.getCheckOuts()) {
                LocalDate checkoutStart = checkOut.getStartDate();
                if (checkoutStart != null && 
                    !checkoutStart.isBefore(startDate) && 
                    !checkoutStart.isAfter(endDate) &&
                    checkOut.getBook() != null) {
                    uniqueBooks.add(checkOut.getBook());
                }
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * @param user the user to calculate average for
     * @return the average page count of unique books borrowed by the user, or 0 if no books borrowed
     * @throws IllegalArgumentException if user parameter is null
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        int totalPages = 0;
        
        if (user.getCheckOuts() != null) {
            for (CheckOut checkOut : user.getCheckOuts()) {
                Book book = checkOut.getBook();
                if (book != null && uniqueBooks.add(book)) {
                    totalPages += book.getNumberOfPages();
                }
            }
        }
        
        return uniqueBooks.isEmpty() ? 0 : (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user
     * @param user the user to count unique books for
     * @return the total number of unique books checked out by the user
     * @throws IllegalArgumentException if user parameter is null
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        if (user.getCheckOuts() != null) {
            for (CheckOut checkOut : user.getCheckOuts()) {
                if (checkOut.getBook() != null) {
                    uniqueBooks.add(checkOut.getBook());
                }
            }
        }
        return uniqueBooks.size();
    }
}