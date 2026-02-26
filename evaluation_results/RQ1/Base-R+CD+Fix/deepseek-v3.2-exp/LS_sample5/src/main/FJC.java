import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

enum UserType {
    MEMBER,
    GUEST
}

class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;
    private int checkoutCount;

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

    public int getCheckoutCount() {
        return checkoutCount;
    }

    public void setCheckoutCount(int checkoutCount) {
        this.checkoutCount = checkoutCount;
    }

    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    public User() {
        this.checkOuts = new ArrayList<>();
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

    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }
}

class CheckOut {
    private LocalDate startDate;
    private LocalDate endDate;
    private Book book;

    public CheckOut() {
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Counts the number of times a specific book has been checked out across all users
     * 
     * @param book The book to count checkouts for
     * @return The total number of times the book has been checked out
     */
    public int countBookCheckOuts(Book book) {
        return book.getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period
     * 
     * @param user The user to check for unique book borrows
     * @param start The start date of the period (format: yyyy-MM-dd)
     * @param end The end date of the period (format: yyyy-MM-dd)
     * @return The number of unique books borrowed by the user during the specified period
     * @throws IllegalArgumentException if start date is after end date
     */
    public int uniqueBooksBorrowedByUser(User user, String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            LocalDate checkoutDate = checkOut.getStartDate();
            if (!checkoutDate.isBefore(startDate) && !checkoutDate.isAfter(endDate)) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * 
     * @param user The user to calculate average page count for
     * @return The average number of pages of unique books borrowed by the user, or 0.0 if no books borrowed
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            uniqueBooks.add(checkOut.getBook());
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
     * 
     * @param user The user to count unique books for
     * @return The total number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            uniqueBooks.add(checkOut.getBook());
        }
        return uniqueBooks.size();
    }

    /**
     * Helper method to process a checkout for a user and update book checkout count
     * 
     * @param user The user checking out the book
     * @param book The book being checked out
     * @param startDate The start date of the checkout (format: yyyy-MM-dd)
     * @param endDate The end date of the checkout (format: yyyy-MM-dd)
     */
    public void processCheckout(User user, Book book, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        
        CheckOut checkOut = new CheckOut();
        checkOut.setBook(book);
        checkOut.setStartDate(start);
        checkOut.setEndDate(end);
        
        user.addCheckOut(checkOut);
        book.incrementCheckoutCount();
    }
}