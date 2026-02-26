import java.time.LocalDate;
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
 * Class representing a book in the library system.
 */
class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;

    /**
     * Unparameterized constructor for Book.
     */
    public Book() {}

    /**
     * Constructor for Book.
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

    /**
     * Gets the title of the book.
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the barcode of the book.
     * @return The barcode of the book.
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the book.
     * @param barcode The barcode to set.
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the ISBN of the book.
     * @return The ISBN of the book.
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Sets the ISBN of the book.
     * @param ISBN The ISBN to set.
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Gets the number of pages in the book.
     * @return The number of pages in the book.
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages in the book.
     * @param numberOfPages The number of pages to set.
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}

/**
 * Class representing a checkout record.
 */
class CheckOut {
    private LocalDate startDate;
    private LocalDate endDate;
    private Book book;

    /**
     * Unparameterized constructor for CheckOut.
     */
    public CheckOut() {}

    /**
     * Constructor for CheckOut.
     * @param startDate The start date of the checkout.
     * @param endDate The end date of the checkout.
     * @param book The book being checked out.
     */
    public CheckOut(LocalDate startDate, LocalDate endDate, Book book) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.book = book;
    }

    /**
     * Gets the start date of the checkout.
     * @return The start date of the checkout.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the checkout.
     * @param startDate The start date to set.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the checkout.
     * @return The end date of the checkout.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the checkout.
     * @param endDate The end date to set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the book being checked out.
     * @return The book being checked out.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book being checked out.
     * @param book The book to set.
     */
    public void setBook(Book book) {
        this.book = book;
    }
}

/**
 * Class representing a user in the library system.
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /**
     * Unparameterized constructor for User.
     */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    /**
     * Constructor for User.
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

    /**
     * Gets the name of the user.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the user.
     * @return The address of the user.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the ID of the user.
     * @return The ID of the user.
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the ID of the user.
     * @param ID The ID to set.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets the type of the user.
     * @return The type of the user.
     */
    public UserType getType() {
        return type;
    }

    /**
     * Sets the type of the user.
     * @param type The type to set.
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Gets the list of checkouts for the user.
     * @return The list of checkouts for the user.
     */
    public List<CheckOut> getCheckOuts() {
        return checkOuts;
    }

    /**
     * Sets the list of checkouts for the user.
     * @param checkOuts The list of checkouts to set.
     */
    public void setCheckOuts(List<CheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }

    /**
     * Adds a checkout to the user's list of checkouts.
     * @param checkOut The checkout to add.
     */
    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }
}

/**
 * Class representing the library system.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /**
     * Unparameterized constructor for LibrarySystem.
     */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    /**
     * Constructor for LibrarySystem.
     * @param users The list of users in the system.
     * @param books The list of books in the system.
     */
    public LibrarySystem(List<User> users, List<Book> books) {
        this.users = users;
        this.books = books;
    }

    /**
     * Gets the list of users in the system.
     * @return The list of users in the system.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets the list of users in the system.
     * @param users The list of users to set.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of books in the system.
     * @return The list of books in the system.
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books in the system.
     * @param books The list of books to set.
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Counts the number of times a specific book has been checked out.
     * @param book The book to count checkouts for.
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

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * @param user The user to check.
     * @param start The start date of the period.
     * @param end The end date of the period.
     * @return The number of unique books borrowed by the user during the period.
     */
    public int uniqueBooksBorrowedByUser(User user, LocalDate start, LocalDate end) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (!uniqueBooks.contains(checkOut.getBook()) 
                    && !checkOut.getStartDate().isBefore(start) 
                    && !checkOut.getEndDate().isAfter(end)) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * @param user The user to check.
     * @return The average page count of the unique books borrowed by the user.
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        List<Book> uniqueBooks = new ArrayList<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            if (!uniqueBooks.contains(checkOut.getBook())) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        if (uniqueBooks.isEmpty()) {
            return 0;
        }
        int totalPages = 0;
        for (Book book : uniqueBooks) {
            totalPages += book.getNumberOfPages();
        }
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     * @param user The user to check.
     * @return The total number of unique books checked out by the user.
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