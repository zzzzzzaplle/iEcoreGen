import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
    private String title;
    private String barcode;
    private String isbn;
    private int numberOfPages;
    private int checkoutCount;

    public Book() {
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

    public int getCheckoutCount() {
        return checkoutCount;
    }

    public void setCheckoutCount(int checkoutCount) {
        this.checkoutCount = checkoutCount;
    }

    public void incrementCheckoutCount() {
        this.checkoutCount++;
    }
}

class User {
    private String name;
    private String email;
    private String address;
    private String id;
    private List<Loan> loans;

    public User() {
        this.loans = new ArrayList<>();
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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }
}

class Loan {
    private Book book;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan() {
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
    private Map<String, Book> books;
    private Map<String, User> users;

    public LibrarySystem() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public void setBooks(Map<String, Book> books) {
        this.books = books;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    /**
     * Counts the number of times a specific book has been checked out.
     *
     * @param bookId The ID of the book to count checkouts for.
     * @return The number of times the book has been checked out.
     * @throws IllegalArgumentException If the book ID is not found.
     */
    public int countBookCheckouts(String bookId) {
        if (!books.containsKey(bookId)) {
            throw new IllegalArgumentException("Book ID not found: " + bookId);
        }
        return books.get(bookId).getCheckoutCount();
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param userId The ID of the user.
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return The number of unique books borrowed by the user during the specified period.
     * @throws IllegalArgumentException If the user ID is not found.
     */
    public int countUniqueBooksBorrowedByUser(String userId, LocalDate startDate, LocalDate endDate) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User ID not found: " + userId);
        }
        User user = users.get(userId);
        int uniqueBooksCount = 0;
        Map<String, Boolean> uniqueBooks = new HashMap<>();

        for (Loan loan : user.getLoans()) {
            if (loan.getStartDate().isAfter(startDate) && loan.getEndDate().isBefore(endDate)) {
                if (!uniqueBooks.containsKey(loan.getBook().getBarcode())) {
                    uniqueBooks.put(loan.getBook().getBarcode(), true);
                    uniqueBooksCount++;
                }
            }
        }
        return uniqueBooksCount;
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @param userId The ID of the user.
     * @return The average page count of the unique books borrowed by the user.
     * @throws IllegalArgumentException If the user ID is not found.
     */
    public double calculateAveragePageCountByUser(String userId) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User ID not found: " + userId);
        }
        User user = users.get(userId);
        int totalPages = 0;
        int uniqueBooksCount = 0;
        Map<String, Boolean> uniqueBooks = new HashMap<>();

        for (Loan loan : user.getLoans()) {
            if (!uniqueBooks.containsKey(loan.getBook().getBarcode())) {
                uniqueBooks.put(loan.getBook().getBarcode(), true);
                totalPages += loan.getBook().getNumberOfPages();
                uniqueBooksCount++;
            }
        }
        return uniqueBooksCount == 0 ? 0 : (double) totalPages / uniqueBooksCount;
    }

    /**
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @param userId The ID of the user.
     * @return The total number of unique books checked out by the user.
     * @throws IllegalArgumentException If the user ID is not found.
     */
    public int calculateTotalUniqueBooksCheckedOutByUser(String userId) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User ID not found: " + userId);
        }
        User user = users.get(userId);
        Map<String, Boolean> uniqueBooks = new HashMap<>();

        for (Loan loan : user.getLoans()) {
            uniqueBooks.put(loan.getBook().getBarcode(), true);
        }
        return uniqueBooks.size();
    }
}