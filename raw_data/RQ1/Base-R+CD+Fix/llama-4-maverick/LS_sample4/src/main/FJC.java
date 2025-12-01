import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum representing the type of user.
 */
enum UserType {
    MEMBER,
    GUEST
}

/**
 * Represents a book in the library system.
 */
class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;

    /**
     * Default constructor for Book.
     */
    public Book() {}

    /**
     * Constructor for Book.
     * 
     * @param title        the title of the book
     * @param barcode      the barcode of the book
     * @param ISBN         the ISBN of the book
     * @param numberOfPages the number of pages in the book
     */
    public Book(String title, String barcode, String ISBN, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.ISBN = ISBN;
        this.numberOfPages = numberOfPages;
    }

    /**
     * Gets the title of the book.
     * 
     * @return the title of the book
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
     * @return the barcode of the book
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
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the ISBN of the book.
     * 
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets the number of pages in the book.
     * 
     * @return the number of pages in the book
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     * 
     * @param numberOfPages the number of pages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}

/**
 * Represents a checkout record.
 */
class CheckOut {
    private LocalDate startDate;
    private LocalDate endDate;
    private Book book;

    /**
     * Default constructor for CheckOut.
     */
    public CheckOut() {}

    /**
     * Constructor for CheckOut.
     * 
     * @param startDate the start date of the checkout
     * @param endDate   the end date of the checkout
     * @param book      the book being checked out
     */
    public CheckOut(String startDate, String endDate, Book book) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.startDate = LocalDate.parse(startDate, formatter);
        this.endDate = LocalDate.parse(endDate, formatter);
        this.book = book;
    }

    /**
     * Gets the start date of the checkout.
     * 
     * @return the start date of the checkout
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the checkout.
     * 
     * @param startDate the start date to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the checkout.
     * 
     * @return the end date of the checkout
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the checkout.
     * 
     * @param endDate the end date to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the book being checked out.
     * 
     * @return the book being checked out
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book being checked out.
     * 
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }
}

/**
 * Represents a user in the library system.
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /**
     * Default constructor for User.
     */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    /**
     * Constructor for User.
     * 
     * @param name    the name of the user
     * @param email   the email of the user
     * @param address the address of the user
     * @param ID      the ID of the user
     * @param type    the type of the user
     */
    public User(String name, String email, String address, String ID, UserType type) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.ID = ID;
        this.type = type;
        this.checkOuts = new ArrayList<>();
    }

    /**
     * Gets the name of the user.
     * 
     * @return the name of the user
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
     * @return the email of the user
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
     * @return the address of the user
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
     * @return the ID of the user
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the type of the user.
     * 
     * @return the type of the user
     */
    public UserType getType() {
        return type;
    }

    /**
     * Sets the type of the user.
     * 
     * @param type the type to set
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Gets the checkout records of the user.
     * 
     * @return the checkout records of the user
     */
    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    /**
     * Sets the checkout records of the user.
     * 
     * @param checkOuts the checkout records to set
     */
    public void setCheckOuts(List<CheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }

    /**
     * Adds a checkout record to the user's checkout records.
     * 
     * @param checkOut the checkout record to add
     */
    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }
}

/**
 * Represents the library system.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /**
     * Default constructor for LibrarySystem.
     */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    /**
     * Gets the users in the library system.
     * 
     * @return the users in the library system
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the users in the library system.
     * 
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the books in the library system.
     * 
     * @return the books in the library system
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the books in the library system.
     * 
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * 
     * @param book the book to count checkouts for
     * @return the number of times the book has been checked out
     */
    public int countBookCheckOuts(Book book) {
        return users.stream()
                .flatMap(user -> user.getCheckOuts().stream())
                .filter(checkOut -> checkOut.getBook().equals(book))
                .collect(Collectors.toList()).size();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a
     * specified period.
     * 
     * @param user  the user to check
     * @param start the start date of the period
     * @param end   the end date of the period
     * @return the number of unique books borrowed by the user during the period
     */
    public int uniqueBooksBorrowedByUser(User user, LocalDate start, LocalDate end) {
        return (int) user.getCheckOuts().stream()
                .filter(checkOut -> !checkOut.getStartDate().isBefore(start) && !checkOut.getEndDate().isAfter(end))
                .map(CheckOut::getBook)
                .distinct()
                .count();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific
     * user.
     * 
     * @param user the user to check
     * @return the average page count of the unique books borrowed by the user
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        List<Book> uniqueBooks = user.getCheckOuts().stream()
                .map(CheckOut::getBook)
                .distinct()
                .collect(Collectors.toList());

        return uniqueBooks.stream()
                .mapToDouble(Book::getNumberOfPages)
                .average()
                .orElse(0);
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param user the user to check
     * @return the total number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        return (int) user.getCheckOuts().stream()
                .map(CheckOut::getBook)
                .distinct()
                .count();
    }
}