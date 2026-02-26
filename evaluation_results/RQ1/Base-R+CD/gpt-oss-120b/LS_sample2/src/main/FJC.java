package librarysystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enum representing the type of a user.
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

    /** No‑argument constructor. */
    public Book() {
        // default constructor
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
}

/**
 * Represents a checkout transaction – a user borrowing a book for a period.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /** No‑argument constructor. */
    public CheckOut() {
        // default constructor
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
 * Represents a user of the library system (member or guest).
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    /** No‑argument constructor. */
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
        return Collections.unmodifiableList(checkOuts);
    }

    /**
     * Adds a new checkout record to the user.
     *
     * @param checkOut the checkout to add
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOut != null) {
            this.checkOuts.add(checkOut);
        }
    }

    /**
     * Removes a checkout record from the user.
     *
     * @param checkOut the checkout to remove
     * @return true if the checkout was present and removed, false otherwise
     */
    public boolean removeCheckOut(CheckOut checkOut) {
        return this.checkOuts.remove(checkOut);
    }
}

/**
 * Core class that manages users, books and provides analytical operations.
 */
class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    /** No‑argument constructor. */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // ---------- Getters and Setters ----------
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void setUsers(List<User> users) {
        this.users = (users != null) ? users : new ArrayList<>();
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = (books != null) ? books : new ArrayList<>();
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
     * Determines the number of unique books borrowed by a user within a given period.
     *
     * @param user  the user whose borrowing activity is examined
     * @param start the start date of the period (inclusive)
     * @param end   the end date of the period (inclusive)
     * @return the count of distinct books the user borrowed during the period
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
                !coEnd.before(start) && !coStart.after(end)) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books a user has ever checked out.
     *
     * @param user the user to evaluate
     * @return the number of distinct books the user has borrowed
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
     * Computes the average number of pages of the unique books borrowed by a user.
     *
     * @param user the user whose borrowed books are analysed
     * @return the average page count; returns 0.0 if the user has not borrowed any books
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
}