import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;

    public Book() {
    }

    public Book(String title, String barcode, String isbn, int numberOfPages) {
        this.title = title;
        this.barcode = barcode;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
    }

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}

class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<BookLoan> bookLoans;

    public User() {
        this.bookLoans = new ArrayList<>();
    }

    public User(String name, String email, String address, String id) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.id = id;
        this.bookLoans = new ArrayList<>();
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(List<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    public void addBookLoan(BookLoan bookLoan) {
        this.bookLoans.add(bookLoan);
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The number of unique books borrowed by the user during the specified period.
     */
    public int countUniqueBooksBorrowedDuringPeriod(LocalDate startDate, LocalDate endDate) {
        return (int) this.bookLoans.stream()
                .filter(loan -> !loan.getStartDate().isBefore(startDate) && !loan.getEndDate().isAfter(endDate))
                .map(BookLoan::getBook)
                .collect(Collectors.toSet())
                .size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @return The average page count of the unique books borrowed by the user.
     */
    public double calculateAveragePageCountOfBorrowedBooks() {
        List<Book> uniqueBooks = this.bookLoans.stream()
                .map(BookLoan::getBook)
                .collect(Collectors.toList());

        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }

        int totalPages = uniqueBooks.stream()
                .mapToInt(Book::getNumberOfPages)
                .sum();

        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @return The total number of unique books checked out by the user.
     */
    public int calculateTotalUniqueBooksCheckedOut() {
        return (int) this.bookLoans.stream()
                .map(BookLoan::getBook)
                .collect(Collectors.toSet())
                .size();
    }
}

class BookLoan {
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;

    public BookLoan() {
    }

    public BookLoan(Book book, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

class LibrarySystem {
    private List<Book> books;
    private List<User> users;
    private Map<Book, Integer> bookCheckoutCounts;

    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookCheckoutCounts = new HashMap<>();
    }

    public LibrarySystem(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
        this.bookCheckoutCounts = new HashMap<>();
        initializeBookCheckoutCounts();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    private void initializeBookCheckoutCounts() {
        for (Book book : books) {
            bookCheckoutCounts.put(book, 0);
        }
    }

    public void addBook(Book book) {
        this.books.add(book);
        this.bookCheckoutCounts.put(book, 0);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Counts the number of times a specific book has been checked out.
     *
     * @param book The book to count checkouts for.
     * @return The number of times the book has been checked out.
     */
    public int countBookCheckouts(Book book) {
        return bookCheckoutCounts.getOrDefault(book, 0);
    }

    /**
     * Increments the checkout count for a specific book.
     *
     * @param book The book to increment the checkout count for.
     */
    public void incrementBookCheckoutCount(Book book) {
        int currentCount = bookCheckoutCounts.getOrDefault(book, 0);
        bookCheckoutCounts.put(book, currentCount + 1);
    }

    /**
     * Adds a new book loan for a user in the library system.
     *
     * @param user The user who is checking out the book.
     * @param book The book being checked out.
     * @param startDate The start date of the loan.
     * @param endDate The end date of the loan.
     */
    public void addBookLoan(User user, Book book, LocalDate startDate, LocalDate endDate) {
        BookLoan bookLoan = new BookLoan(book, startDate, endDate);
        user.addBookLoan(bookLoan);
        incrementBookCheckoutCount(book);
    }
}