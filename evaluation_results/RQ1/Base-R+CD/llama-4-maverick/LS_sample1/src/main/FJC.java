import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Enum representing the type of user.
 */
enum UserType {
    MEMBER,
    GUEST
}

/**
 * Class representing a Book in the library system.
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
     * Constructor for Book with all fields.
     * @param title The title of the book.
     * @param barcode The barcode of the book.
     * @param ISBN The ISBN of the book.
     * @param numberOfPages The number of pages in the book.
     */
    public Book(String title, String barcode, String ISBN, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.ISBN = ISBN;
        this.numberOfPages = numberOfPages;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }
    public int getNumberOfPages() { return numberOfPages; }
    public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }
}

/**
 * Class representing a CheckOut in the library system.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /**
     * Default constructor for CheckOut.
     */
    public CheckOut() {}

    /**
     * Constructor for CheckOut with all fields.
     * @param startDate The start date of the checkout.
     * @param endDate The end date of the checkout.
     * @param book The book being checked out.
     */
    public CheckOut(Date startDate, Date endDate, Book book) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.book = book;
    }

    // Getters and Setters
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}

/**
 * Class representing a User in the library system.
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
     * Constructor for User with all fields.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param address The address of the user.
     * @param ID The ID of the user.
     * @param type The type of the user.
     */
    public User(String name, String email, String address, String ID, UserType type) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.ID = ID;
        this.type = type;
        this.checkOuts = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public UserType getType() { return type; }
    public void setType(UserType type) { this.type = type; }
    public List<CheckOut> getCheckOuts() { return checkOuts; }
    public void setCheckOuts(List<CheckOut> checkOuts) { this.checkOuts = checkOuts; }
}

/**
 * Class representing the Library System.
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
     * Constructor for LibrarySystem with all fields.
     * @param users The list of users in the system.
     * @param books The list of books in the system.
     */
    public LibrarySystem(List<User> users, List<Book> books) {
        this.users = users;
        this.books = books;
    }

    // Getters and Setters
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    /**
     * Counts the total number of unique books checked out by a specific user.
     * @param user The user to check.
     * @return The total number of unique books checked out.
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        List<CheckOut> checkOuts = user.getCheckOuts();
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : checkOuts) {
            if (!uniqueBooks.contains(checkOut.getBook())) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a specific user.
     * @param user The user to check.
     * @return The average number of pages.
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        List<CheckOut> checkOuts = user.getCheckOuts();
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : checkOuts) {
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
     * Determines the number of unique books borrowed by a particular user during a specified period.
     * @param user The user to check.
     * @param start The start date of the period.
     * @param end The end date of the period.
     * @return The number of unique books borrowed.
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        List<CheckOut> checkOuts = user.getCheckOuts();
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : checkOuts) {
            if (checkOut.getStartDate().after(start) && checkOut.getEndDate().before(end)) {
                if (!uniqueBooks.contains(checkOut.getBook())) {
                    uniqueBooks.add(checkOut.getBook());
                }
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * @param book The book to check.
     * @return The number of times the book has been checked out.
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
}