package com.example.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enum representing the type of a user in the library system.
 */
 enum UserType {
    MEMBER,
    GUEST
}

/**
 * Represents a book in the library.
 */
class Book {

    /** Title of the book */
    private String title;
    /** Barcode of the book – assumed to be unique */
    private String barcode;
    /** ISBN of the book */
    private String ISBN;
    /** Number of pages of the book */
    private int numberOfPages;

    /** Default (no‑argument) constructor */
    public Book() {
        // empty constructor for frameworks / testing
    }

    /** Full‑argument constructor (convenient for manual creation) */
    public Book(String title, String barcode, String ISBN, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.ISBN = ISBN;
        this.numberOfPages = numberOfPages;
    }

    /* ---------- Getters & Setters ---------- */

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

    /* ---------- Equality based on barcode (unique identifier) ---------- */

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
 * Represents a checkout (loan) of a book by a user.
 */
class CheckOut {

    /** Date when the loan starts */
    private Date startDate;
    /** Date when the loan ends */
    private Date endDate;
    /** Book that is being loaned */
    private Book book;

    /** Default (no‑argument) constructor */
    public CheckOut() {
        // empty constructor for frameworks / testing
    }

    /** Full‑argument constructor */
    public CheckOut(Date startDate, Date endDate, Book book) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.book = book;
    }

    /* ---------- Getters & Setters ---------- */

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

    /** Full name of the user */
    private String name;
    /** Email address */
    private String email;
    /** Physical address */
    private String address;
    /** Unique identifier of the user */
    private String ID;
    /** Type of the user (MEMBER or GUEST) */
    private UserType type;
    /** List of check‑outs performed by the user */
    private List<CheckOut> checkOuts;

    /** Default (no‑argument) constructor */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    /** Full‑argument constructor (except checkOuts, which start empty) */
    public User(String name, String email, String address, String ID, UserType type) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.ID = ID;
        this.type = type;
        this.checkOuts = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

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
     * Adds a new checkout entry for this user.
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
 * Core class managing users, books and providing analytics.
 */
 class LibrarySystem {

    /** All registered users */
    private List<User> users;
    /** All books available in the library */
    private List<Book> books;

    /** Default (no‑argument) constructor – initializes internal collections */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    /* ---------- Getters & Setters ---------- */

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
     * Registers a new user in the system.
     *
     * @param user the user to register
     */
    public void addUser(User user) {
        if (user != null && !users.contains(user)) {
            users.add(user);
        }
    }

    /**
     * Adds a new book to the library collection.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        if (book != null && !books.contains(book)) {
            books.add(book);
        }
    }

    /**
     * Counts how many times the specified book has been checked out by any user.
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
     * Returns the number of unique books a specific user has borrowed.
     *
     * @param user the user whose unique borrowed books are counted
     * @return the count of distinct books borrowed by the user
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
     * Calculates the average number of pages of the distinct books borrowed by a user.
     *
     * @param user the user whose borrowed books are examined
     * @return the average page count of unique books; returns 0.0 if the user has not borrowed any books
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
     * Determines how many distinct books a user borrowed within a given time interval.
     * A checkout is considered within the interval if its startDate is on or after {@code start}
     * and its endDate is on or before {@code end}.
     *
     * @param user  the user whose borrowing activity is examined
     * @param start the start of the period (inclusive)
     * @param end   the end of the period (inclusive)
     * @return number of unique books borrowed in the period; 0 if none match or parameters are invalid
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
     * Convenience method to create a checkout for a user and automatically add it to the system.
     *
     * @param user      the user performing the checkout
     * @param book      the book being checked out
     * @param startDate the start date of the loan
     * @param endDate   the end date of the loan
     * @throws IllegalArgumentException if any argument is null or if the book is not part of the library
     */
    public void checkoutBook(User user, Book book, Date startDate, Date endDate) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!books.contains(book)) {
            throw new IllegalArgumentException("Book is not registered in the library");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        CheckOut co = new CheckOut(startDate, endDate, book);
        user.addCheckOut(co);
    }
}