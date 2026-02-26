package com.example.library;

import java.util.ArrayList;
import java.util.Collections;
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

    /** Title of the book. */
    private String title;

    /** Unique barcode of the book. */
    private String barcode;

    /** International Standard Book Number. */
    private String ISBN;

    /** Number of pages in the book. */
    private int numberOfPages;

    /** No‑argument constructor required by the specification. */
    public Book() {
        // default constructor
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------
    // Equality – a book is uniquely identified by its barcode.
    // ------------------------------------------------------------------------

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
 * Represents a checkout (loan) of a book by a user.
 */
 class CheckOut {

    /** Date when the loan starts. */
    private Date startDate;

    /** Date when the loan ends. */
    private Date endDate;

    /** Book that is being borrowed. */
    private Book book;

    /** No‑argument constructor required by the specification. */
    public CheckOut() {
        // default constructor
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate == null ? null : new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate == null ? null : new Date(endDate.getTime());
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

/**
 * Represents a user of the library – either a member or a guest.
 */
 class User {

    /** Full name of the user. */
    private String name;

    /** Email address of the user. */
    private String email;

    /** Physical address of the user. */
    private String address;

    /** Unique identifier of the user. */
    private String ID;

    /** Type of the user (MEMBER or GUEST). */
    private UserType type;

    /** List of checkouts performed by the user. */
    private List<CheckOut> checkOuts;

    /** No‑argument constructor required by the specification. */
    public User() {
        this.checkOuts = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

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
        return Collections.unmodifiableList(checkOuts);
    }

    /**
     * Adds a new checkout to the user.
     *
     * @param checkOut the checkout to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code checkOut} is {@code null}
     */
    public void addCheckOut(CheckOut checkOut) {
        if (checkOut == null) {
            throw new IllegalArgumentException("CheckOut cannot be null");
        }
        this.checkOuts.add(checkOut);
    }

    // ------------------------------------------------------------------------
    // Equality – a user is uniquely identified by its ID.
    // ------------------------------------------------------------------------

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
 * Core class that manages users, books and provides statistical operations.
 */
 class LibrarySystem {

    /** All registered users of the library. */
    private List<User> users;

    /** All books available in the library. */
    private List<Book> books;

    /** No‑argument constructor required by the specification. */
    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    // Getters and Setters for collections (read‑only view)
    // ------------------------------------------------------------------------

    /**
     * Returns an unmodifiable view of the registered users.
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Returns an unmodifiable view of the books in the library.
     *
     * @return list of books
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Adds a new user to the system.
     *
     * @param user the user to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.users.add(user);
    }

    /**
     * Adds a new book to the system.
     *
     * @param book the book to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code book} is {@code null}
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        this.books.add(book);
    }

    // ------------------------------------------------------------------------
    // Functional requirement implementations
    // ------------------------------------------------------------------------

    /**
     * Counts how many times a specific book has been checked out by any user.
     *
     * @param book the book to count checkouts for; must not be {@code null}
     * @return the total number of checkouts for the given book
     * @throws IllegalArgumentException if {@code book} is {@code null}
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
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
     * Determines the number of unique books a particular user has borrowed
     * during a specified period.
     *
     * @param user  the user whose borrow history is examined; must not be {@code null}
     * @param start the start of the period (inclusive); must not be {@code null}
     * @param end   the end of the period (inclusive); must not be {@code null}
     * @return the count of distinct books borrowed by the user within the period
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            throw new IllegalArgumentException("User and dates cannot be null");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            Date coStart = co.getStartDate();
            Date coEnd = co.getEndDate();
            // Consider a checkout within the period if its start date is on/after start
            // and its end date is on/before end.
            if (!coStart.after(end) && !coEnd.before(start)) {
                uniqueBooks.add(co.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average number of pages of the unique books borrowed by a
     * specific user (across all time).
     *
     * @param user the user whose borrowed books are examined; must not be {@code null}
     * @return the average page count of distinct books; {@code 0.0} if the user has not borrowed any books
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
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
     * Calculates the total number of unique books that have been checked out by
     * a specific user (across all time).
     *
     * @param user the user whose borrowing history is examined; must not be {@code null}
     * @return the count of distinct books the user has ever checked out
     * @throws IllegalArgumentException if {@code user} is {@code null}
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut co : user.getCheckOuts()) {
            uniqueBooks.add(co.getBook());
        }
        return uniqueBooks.size();
    }
}