package com.example.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Enumeration representing the type of a library user.
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

    // -------------------- Getters and Setters --------------------

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

    // -------------------- Equality based on barcode --------------------
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
 * Represents a checkout record tying a book to a borrowing period.
 */
class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    /** No‑argument constructor required by the specification. */
    public CheckOut() {
    }

    // -------------------- Getters and Setters --------------------

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
 * Represents a user of the library (member or guest).
 */
class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public User() {
    }

    // -------------------- Getters and Setters --------------------

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
}

/**
 * Core class managing library users, books and providing analytical operations.
 */
 class LibrarySystem {
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public LibrarySystem() {
    }

    // -------------------- Getters and Setters --------------------

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
     * Adds a user to the library system.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Adds a book to the library system.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        this.books.add(book);
    }

    /**
     * Counts the total number of times a specific book has been checked out
     * by any user in the system.
     *
     * @param book the book whose checkout count is required
     * @return the number of checkout occurrences for the given book
     */
    public int countBookCheckOuts(Book book) {
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
     * Returns the number of unique books a particular user has borrowed
     * within a specified date range (inclusive). A book is considered
     * unique by its barcode.
     *
     * @param user  the user whose borrowing activity is examined
     * @param start the start date of the period (inclusive)
     * @param end   the end date of the period (inclusive)
     * @return the count of distinct books borrowed by the user in the period
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            Date checkoutStart = co.getStartDate();
            if (!checkoutStart.before(start) && !checkoutStart.after(end)) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the distinct books borrowed
     * by a specific user across all of their checkout history.
     *
     * @param user the user whose average page count is to be calculated
     * @return the average page count of unique books; returns 0.0 if the user
     *         has not borrowed any books
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            uniqueBooks.add(co.getBook());
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
     * Determines the total number of distinct books that have ever been
     * checked out by the specified user (no date restriction).
     *
     * @param user the user whose unique checkout count is required
     * @return the count of distinct books the user has ever borrowed
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            uniqueBooks.add(co.getBook());
        }
        return uniqueBooks.size();
    }
}