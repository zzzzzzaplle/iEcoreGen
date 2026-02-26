import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type of a {@link User}.
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

    /** No‑argument constructor required by the specification. */
    public Book() {
    }

    // --------------------------------------------------------------------- //
    // Getters and Setters
    // --------------------------------------------------------------------- //

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

    // --------------------------------------------------------------------- //
    // Equality based on barcode – a barcode uniquely identifies a book.
    // --------------------------------------------------------------------- //

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
 * Represents a user of the library (member or guest).
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /** No‑argument constructor required by the specification. */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    // --------------------------------------------------------------------- //
    // Getters and Setters
    // --------------------------------------------------------------------- //

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
     * @param checkOut the checkout to add
     */
    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }

    // --------------------------------------------------------------------- //
    // Equality based on ID – an ID uniquely identifies a user.
    // --------------------------------------------------------------------- //

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
 * Represents a single checkout transaction – a user borrowing a book.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /** No‑argument constructor required by the specification. */
    public CheckOut() {
    }

    // --------------------------------------------------------------------- //
    // Getters and Setters
    // --------------------------------------------------------------------- //

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
 * Core service class that contains the library's data and implements
 * the required analytics.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** No‑argument constructor required by the specification. */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // --------------------------------------------------------------------- //
    // Getters and Setters
    // --------------------------------------------------------------------- //

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
     * Counts how many times the given {@link Book} has been checked out
     * across all users.
     *
     * @param book the book whose checkout count is required; must not be {@code null}
     * @return the total number of checkout occurrences for the supplied book
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
     * Calculates the total number of **unique** books that have been checked out
     * by the supplied {@link User} (over the whole history of that user).
     *
     * @param user the user whose unique checkout count is required; must not be {@code null}
     * @return the number of distinct books the user has ever borrowed
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

    /**
     * Computes the average number of pages of the **unique** books borrowed by the
     * supplied {@link User}. If the user has not borrowed any books, the method
     * returns {@code 0.0}.
     *
     * @param user the user whose average page count is required; must not be {@code null}
     * @return the average number of pages of distinct books borrowed by the user
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
     * Determines how many **unique** books a particular user borrowed during a
     * specific period (inclusive). A checkout is considered to be within the
     * period when its {@code startDate} is on or after {@code start} and its
     * {@code endDate} is on or before {@code end}.
     *
     * @param user  the user whose borrowing activity is examined; must not be {@code null}
     * @param start the start of the period (inclusive); must not be {@code null}
     * @param end   the end of the period (inclusive); must not be {@code null}
     * @return the number of distinct books borrowed by the user in the given period
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
                !coStart.before(start) && !coEnd.after(end) &&
                co.getBook() != null) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Adds a new {@link User} to the system.
     *
     * @param user the user to add; must not be {@code null}
     */
    public void addUser(User user) {
        if (user != null) {
            this.users.add(user);
        }
    }

    /**
     * Adds a new {@link Book} to the system.
     *
     * @param book the book to add; must not be {@code null}
     */
    public void addBook(Book book) {
        if (book != null) {
            this.books.add(book);
        }
    }
}