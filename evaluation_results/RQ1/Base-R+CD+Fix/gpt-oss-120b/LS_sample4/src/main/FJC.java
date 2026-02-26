import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enumeration of possible user types.
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
        // default constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Two books are considered equal if they have the same barcode.
     *
     * @param obj the other object
     * @return true if the barcodes match
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return barcode != null && barcode.equals(other.barcode);
    }

    /**
     * Hash code based on the barcode.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return barcode == null ? 0 : barcode.hashCode();
    }
}

/**
 * Represents a user of the library system (member or guest).
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

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Adds a checkout record to this user.
     *
     * @param checkout the checkout to add
     */
    public void addCheckOut(CheckOut checkout) {
        if (checkout != null) {
            this.checkOuts.add(checkout);
        }
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
        // default constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
 * Core library system handling users, books and analytics.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** Unparameterized constructor required by the specification. */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Determines the number of unique books a particular user has borrowed during a
     * specified period (inclusive). A checkout is considered within the period if
     * its startDate is on/after {@code start} and its endDate is on/before {@code end}.
     *
     * @param user  the user to analyse
     * @param start the start of the period (inclusive)
     * @param end   the end of the period (inclusive)
     * @return the count of distinct books borrowed by the user in the period
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            return 0;
        }
        Set<Book> unique = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            Date s = co.getStartDate();
            Date e = co.getEndDate();
            if (s != null && e != null && !s.before(start) && !e.after(end)) {
                unique.add(co.getBook());
            }
        }
        return unique.size();
    }

    /**
     * Calculates the total number of unique books that the given user has ever
     * checked out (regardless of dates).
     *
     * @param user the user whose unique checkout count is required
     * @return the number of distinct books the user has borrowed
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            return 0;
        }
        Set<Book> unique = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            unique.add(co.getBook());
        }
        return unique.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a
     * specific user. If the user has not borrowed any books, the method returns 0.0.
     *
     * @param user the user whose average page count is required
     * @return the average page count of distinct books borrowed by the user
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            return 0.0;
        }
        Set<Book> unique = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            unique.add(co.getBook());
        }
        if (unique.isEmpty()) {
            return 0.0;
        }
        int totalPages = 0;
        for (Book b : unique) {
            totalPages += b.getNumberOfPages();
        }
        return (double) totalPages / unique.size();
    }
}