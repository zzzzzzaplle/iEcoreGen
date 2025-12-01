import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

enum UserType {
    MEMBER,
    GUEST
}

class Book {
    private String title;
    private String barcode;
    private String ISBN;
    private int numberOfPages;

    public Book() {
        this.title = "";
        this.barcode = "";
        this.ISBN = "";
        this.numberOfPages = 0;
    }

    // Getters and setters
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return barcode.equals(book.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}

class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    public User() {
        this.name = "";
        this.email = "";
        this.address = "";
        this.ID = "";
        this.type = UserType.MEMBER;
        this.checkOuts = new ArrayList<>();
    }

    // Getters and setters
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

    public void addCheckOut(CheckOut checkOut) {
        this.checkOuts.add(checkOut);
    }
}

class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    public CheckOut() {
        this.startDate = new Date();
        this.endDate = new Date();
        this.book = new Book();
    }

    // Getters and setters
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

class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    // Getters and setters
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
     * Counts the number of times a specific book has been checked out.
     *
     * @param book The book to count checkouts for
     * @return The number of times the book has been checked out
     */
    public int countBookCheckOuts(Book book) {
        int count = 0;
        for (User user : users) {
            for (CheckOut checkout : user.getCheckOuts()) {
                if (checkout.getBook().equals(book)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     *
     * @param user  The user to check
     * @param start The start date of the period (inclusive)
     * @param end   The end date of the period (inclusive)
     * @return The number of unique books borrowed by the user during the specified period
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkout : user.getCheckOuts()) {
            // Check if the checkout period overlaps with the specified period
            if (!(checkout.getStartDate().after(end) || checkout.getEndDate().before(start))) {
                uniqueBooks.add(checkout.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     *
     * @param user The user for which to calculate the average
     * @return The average page count of unique books borrowed by the user
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkout : user.getCheckOuts()) {
            uniqueBooks.add(checkout.getBook());
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
     * Calculates the total number of unique books checked out by a specific user.
     *
     * @param user The user for which to count unique books
     * @return The number of unique books checked out by the user
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkout : user.getCheckOuts()) {
            uniqueBooks.add(checkout.getBook());
        }
        return uniqueBooks.size();
    }
}