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
    private List<CheckOut> checkOuts;

    public Book() {
        this.checkOuts = new ArrayList<>();
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

    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    public void setCheckOuts(List<CheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }

    /**
     * Adds a checkout record to this book's checkout history
     * @param checkOut The checkout record to add
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOut != null) {
            this.checkOuts.add(checkOut);
        }
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

    /**
     * Adds a checkout record to this user's checkout history
     * @param checkOut The checkout record to add
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOut != null) {
            this.checkOuts.add(checkOut);
        }
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

    /**
     * Sets the start date from a string in yyyy-MM-dd format
     * @param startDateString The start date string in yyyy-MM-dd format
     * @throws DateTimeException if the string cannot be parsed
     */
    public void setStartDate(String startDateString) {
        this.startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Sets the end date from a string in yyyy-MM-dd format
     * @param endDateString The end date string in yyyy-MM-dd format
     * @throws DateTimeException if the string cannot be parsed
     */
    public void setEndDate(String endDateString) {
        this.endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    /**
     * Adds a user to the library system
     * @param user The user to add
     */
    public void addUser(User user) {
        if (user != null) {
            this.users.add(user);
        }
    }

    /**
     * Adds a book to the library system
     * @param book The book to add
     */
    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
        }
    }

    /**
     * Counts the number of times a specific book has been checked out
     * @param book The book to count checkouts for
     * @return The total number of times the book has been checked out
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            return 0;
        }
        return book.getCheckOuts().size();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period
     * @param user The user to check
     * @param start The start date of the period (inclusive)
     * @param end The end date of the period (inclusive)
     * @return The number of unique books borrowed by the user during the specified period
     */
    public int uniqueBooksBorrowedByUser(User user, LocalDate start, LocalDate end) {
        if (user == null || start == null || end == null) {
            return 0;
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            LocalDate checkoutDate = checkOut.getStartDate();
            if ((checkoutDate.isEqual(start) || checkoutDate.isAfter(start)) && 
                (checkoutDate.isEqual(end) || checkoutDate.isBefore(end))) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user
     * @param user The user to calculate the average for
     * @return The average page count of unique books borrowed by the user, or 0 if no books were borrowed
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            return 0.0;
        }
        
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
     * @param user The user to count unique books for
     * @return The total number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            return 0;
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            uniqueBooks.add(checkOut.getBook());
        }
        
        return uniqueBooks.size();
    }
}