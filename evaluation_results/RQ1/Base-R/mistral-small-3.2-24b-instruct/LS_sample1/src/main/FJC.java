import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;
    private int checkoutCount;

    public Book() {
        this.title = "";
        this.barcode = "";
        this.ISBN = "";
        this.numberOfPages = 0;
        this.checkoutCount = 0;
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

    public int getCheckoutCount() {
        return checkoutCount;
    }

    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

abstract class User {
    private String name;
    private String email;
    private String address;
    private String ID;

    public User() {
        this.name = "";
        this.email = "";
        this.address = "";
        this.ID = "";
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

class Member extends User {
    public Member() {
        super();
    }
}

class Guest extends User {
    public Guest() {
        super();
    }
}

class Loan {
    private Book book;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan() {
        this.book = new Book();
        this.user = new User();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

class Library {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loans = new ArrayList<>();
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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Counts the number of times a specific book has been checked out.
     *
     * @param book The book to count checkouts for
     * @return The number of times the book has been checked out
     */
    public int countBookCheckouts(Book book) {
        return (int) loans.stream().filter(loan -> loan.getBook().equals(book)).count();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param user The user to count unique books for
     * @param startDate The start date of the period
     * @param endDate The end date of the period
     * @return The number of unique books borrowed by the user during the specified period
     */
    public int countUniqueBooksBorrowedByUser(User user, LocalDate startDate, LocalDate endDate) {
        return (int) loans.stream()
                .filter(loan -> loan.getUser().equals(user) &&
                        !loan.getStartDate().isBefore(startDate) &&
                        !loan.getEndDate().isAfter(endDate))
                .map(Loan::getBook)
                .distinct()
                .count();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @param user The user to calculate the average page count for
     * @return The average page count of the unique books borrowed by the user
     */
    public double calculateAveragePageCount(User user) {
        List<Book> uniqueBooks = loans.stream()
                .filter(loan -> loan.getUser().equals(user))
                .map(Loan::getBook)
                .distinct()
                .toList();

        if (uniqueBooks.isEmpty()) {
            return 0.0;
        }

        int totalPages = uniqueBooks.stream().mapToInt(Book::getNumberOfPages).sum();
        return (double) totalPages / uniqueBooks.size();
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @param user The user to calculate the total number of unique books for
     * @return The total number of unique books checked out by the user
     */
    public int calculateTotalUniqueBooksCheckedOut(User user) {
        return (int) loans.stream()
                .filter(loan -> loan.getUser().equals(user))
                .map(Loan::getBook)
                .distinct()
                .count();
    }

    /**
     * Adds a new loan to the library system.
     *
     * @param loan The loan to add
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.getBook().incrementCheckoutCount();
    }

    /**
     * Adds a new book to the library system.
     *
     * @param book The book to add
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Adds a new user to the library system.
     *
     * @param user The user to add
     */
    public void addUser(User user) {
        users.add(user);
    }
}