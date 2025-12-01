import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
     * Constructor for Book with parameters.
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
     * @return the title
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
     * @return the barcode
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
     * @return the ISBN
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
     * @return the numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     * 
     * @param numberOfPages the numberOfPages to set
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
     * Constructor for CheckOut with parameters.
     * 
     * @param startDate the start date of the checkout
     * @param endDate   the end date of the checkout
     * @param book      the book being checked out
     */
    public CheckOut(LocalDate startDate, LocalDate endDate, Book book) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.book = book;
    }

    /**
     * Gets the start date of the checkout.
     * 
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the checkout.
     * 
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the checkout.
     * 
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the checkout.
     * 
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the book being checked out.
     * 
     * @return the book
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
     * Constructor for User with parameters.
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
     * @return the name
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
     * @return the email
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
     * @return the address
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
     * @return the ID
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
     * @return the type
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
     * Gets the list of checkouts for the user.
     * 
     * @return the checkOuts
     */
    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    /**
     * Sets the list of checkouts for the user.
     * 
     * @param checkOuts the checkOuts to set
     */
    public void setCheckOuts(List<CheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }

    /**
     * Adds a checkout to the user's list of checkouts.
     * 
     * @param checkOut the checkout to add
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
     * Constructor for LibrarySystem with parameters.
     * 
     * @param users the list of users
     * @param books the list of books
     */
    public LibrarySystem(List<User> users, List<Book> books) {
        this.users = users;
        this.books = books;
    }

    /**
     * Gets the list of users in the library system.
     * 
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users in the library system.
     * 
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of books in the library system.
     * 
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books in the library system.
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
        int count = 0;
        for (User user : users) {
            for (CheckOut checkOut : user.getCheckOuts()) {
                if (checkOut.getBook().equals(book)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * 
     * @param user  the user to check
     * @param start the start date of the period
     * @param end   the end date of the period
     * @return the number of unique books borrowed by the user during the period
     */
    public int uniqueBooksBorrowedByUser(User user, LocalDate start, LocalDate end) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (!uniqueBooks.contains(checkOut.getBook()) && 
                (checkOut.getStartDate().isAfter(start) || checkOut.getStartDate().isEqual(start)) && 
                (checkOut.getEndDate().isBefore(end) || checkOut.getEndDate().isEqual(end))) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * 
     * @param user the user to check
     * @return the average page count of the unique books borrowed by the user
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (!uniqueBooks.contains(checkOut.getBook())) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        int totalPages = 0;
        for (Book book : uniqueBooks) {
            totalPages += book.getNumberOfPages();
        }
        return uniqueBooks.isEmpty() ? 0 : (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * 
     * @param user the user to check
     * @return the total number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (!uniqueBooks.contains(checkOut.getBook())) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }
}