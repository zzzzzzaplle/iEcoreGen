import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Types of users that can use the library system.
 */
enum UserType {
    MEMBER,
    GUEST
}

/**
 * Represents a book in the library.
 */
class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;

    /** Unparameterized constructor required by the specification. */
    public Book() {
        // fields are left with default null / 0 values
    }

    // ---------- Getters and Setters ----------
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

    // ---------- Equality based on barcode (assumed unique) ----------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(barcode, book.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}

/**
 * Represents a user (member or guest) of the library.
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /** Unparameterized constructor required by the specification. */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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
     * Adds a checkout record to the user.
     *
     * @param checkOut the checkout to add
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOut != null) {
            this.checkOuts.add(checkOut);
        }
    }

    // ---------- Equality based on ID (assumed unique) ----------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(ID, user.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}

/**
 * Represents a checkout (loan) of a book by a user.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /** Unparameterized constructor required by the specification. */
    public CheckOut() {
        // fields left with default null values
    }

    // ---------- Getters and Setters ----------
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

/**
 * Core class that manages users, books and provides analytics operations.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** Unparameterized constructor required by the specification. */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
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
     * Adds a user to the system.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        if (user != null) {
            this.users.add(user);
        }
    }

    /**
     * Adds a book to the system.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
        }
    }

    /**
     * Counts how many times a specific book has been checked out across all users.
     *
     * @param book the book whose checkout count is required
     * @return the total number of checkout occurrences for the given book
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            return 0;
        }
        int count = 0;
        for (User user : users) {
            for (CheckOut co : user.getCheckOuts()) {
                if (book.equals(co.getBook())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Determines the number of unique books a particular user has borrowed during a specified period.
     *
     * @param user  the user whose borrowing history is examined
     * @param start the start date of the period (inclusive)
     * @param end   the end date of the period (inclusive)
     * @return the count of distinct books borrowed by the user within the period
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            return 0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            Date coStart = co.getStartDate();
            Date coEnd = co.getEndDate();
            if (coStart != null && coEnd != null &&
                !coStart.before(start) && !coEnd.after(end)) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a specific user.
     *
     * @param user the user whose borrowed books are considered
     * @return the average page count of distinct books the user has borrowed; 0.0 if none
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            return 0.0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            if (co.getBook() != null) {
                uniqueBooks.add(co.getBook());
            }
        }
        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }
        int totalPages = 0;
        for (Book b : uniqueBooks) {
            totalPages += b.getNumberOfPages();
        }
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @param user the user whose unique checkout count is required
     * @return the number of distinct books the user has ever checked out
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            return 0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            if (co.getBook() != null) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }
}