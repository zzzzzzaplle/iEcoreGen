import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * Enumeration for the type of a user.
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

    /** Default constructor */
    public Book() {
        // empty constructor
    }

    // ---------- Getters & Setters ----------
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

    public void setISBN(String iSBN) {
        ISBN = iSBN;
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
 * Represents a checkout (loan) of a book.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /** Default constructor */
    public CheckOut() {
        // empty constructor
    }

    // ---------- Getters & Setters ----------
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
 * Represents a user of the library system.
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /** Default constructor */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
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

    public void setID(String iD) {
        ID = iD;
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
}

/**
 * Core library system handling users, books and analytics.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** Default constructor */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // ---------- Getters & Setters ----------
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
     * @return the total number of checkouts for the given book
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
     * specified period. A checkout is considered within the period if its date range
     * overlaps with the supplied start/end dates.
     *
     * @param user  the user whose borrowings are examined
     * @param start the start of the period (inclusive)
     * @param end   the end of the period (inclusive)
     * @return the count of distinct books borrowed by the user in the period
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            return 0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            if (isOverlapping(co.getStartDate(), co.getEndDate(), start, end)) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a specific user.
     *
     * @param user the user whose borrowed books are considered
     * @return the average page count; returns 0.0 if the user has not borrowed any books
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            return 0.0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            uniqueBooks.add(co.getBook());
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
     * Calculates the total number of distinct books that a user has ever checked out.
     *
     * @param user the user whose checkout history is examined
     * @return the count of unique books borrowed by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            return 0;
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            uniqueBooks.add(co.getBook());
        }
        return uniqueBooks.size();
    }

    /**
     * Helper method to determine if two date intervals overlap.
     *
     * @param start1 start of first interval
     * @param end1   end of first interval
     * @param start2 start of second interval
     * @param end2   end of second interval
     * @return true if intervals overlap, false otherwise
     */
    private boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        // Null checks: treat null dates as non-overlapping
        if (start1 == null || end1 == null || start2 == null || end2 == null) {
            return false;
        }
        // Overlap exists when the start of one interval is <= end of the other and vice‑versa
        return !start1.after(end2) && !start2.after(end1);
    }
}