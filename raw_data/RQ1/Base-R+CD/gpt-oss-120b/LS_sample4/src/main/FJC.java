import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enumeration representing the type of a user in the library system.
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
        // No‑arg constructor required for tests / frameworks
    }

    /** Getters and Setters */

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
     * This definition is used when counting unique books.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return barcode != null && barcode.equals(other.barcode);
    }

    @Override
    public int hashCode() {
        return barcode == null ? 0 : barcode.hashCode();
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
        // No‑arg constructor required for tests / frameworks
    }

    /** Getters and Setters */

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
 * Represents a user (member or guest) of the library.
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

    /** Getters and Setters */

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
 * Core class providing library‑wide operations.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** Default constructor */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    /** Getters and Setters */

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
     * Counts how many times a specific book has been checked out across all users.
     *
     * @param book the book whose checkout count is required; must not be {@code null}
     * @return the total number of checkout occurrences for the given book
     * @throws IllegalArgumentException if {@code book} is {@code null}
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
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
     * Determines the number of unique books a specific user has borrowed.
     *
     * @param user the user whose unique borrowed books are to be counted; must not be {@code null}
     * @return the count of distinct books borrowed by the user
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            if (co.getBook() != null) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a user.
     *
     * @param user the user whose average page count is required; must not be {@code null}
     * @return the average page count of distinct books, or {@code 0.0} if the user has not borrowed any books
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
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
     * Returns the number of unique books borrowed by a user within a specific date range.
     *
     * @param user  the user whose borrowing activity is examined; must not be {@code null}
     * @param start the start date of the period (inclusive); must not be {@code null}
     * @param end   the end date of the period (inclusive); must not be {@code null}
     * @return the count of distinct books borrowed by the user during the period
     * @throws IllegalArgumentException if any argument is {@code null} or if {@code start} is after {@code end}
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            throw new IllegalArgumentException("User and dates cannot be null.");
        }
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date must not be after end date.");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            Date coStart = co.getStartDate();
            Date coEnd = co.getEndDate();
            if (coStart == null || coEnd == null) {
                continue; // ignore malformed checkouts
            }
            // Check if the checkout period overlaps the requested interval
            boolean overlaps = !(coEnd.before(start) || coStart.after(end));
            if (overlaps && co.getBook() != null) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Adds a user to the library system.
     *
     * @param user the user to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        users.add(user);
    }

    /**
     * Adds a book to the library system.
     *
     * @param book the book to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code book} is {@code null}
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        books.add(book);
    }
}