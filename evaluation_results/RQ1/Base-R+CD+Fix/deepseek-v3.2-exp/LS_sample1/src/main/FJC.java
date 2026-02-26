import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
}

class CheckOut {
    private Date startDate;
    private Date endDate;
    private Book book;

    public CheckOut() {
    }

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

class User {
    private String name;
    private String email;
    private String address;
    private String ID;
    private UserType type;
    private List<CheckOut> checkOuts;

    public User() {
        this.checkOuts = new ArrayList<>();
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
}

class LibrarySystem {
    private List<User> users;
    private List<Book> books;

    public LibrarySystem() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

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
     * Counts the number of times a specific book has been checked out by all users.
     * This method iterates through all users and their checkouts to count occurrences of the specified book.
     *
     * @param book the book to count checkouts for
     * @return the total number of times the book has been checked out
     * @throws IllegalArgumentException if the book parameter is null
     */
    public int countBookCheckOuts(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        
        int count = 0;
        for (User user : users) {
            for (CheckOut checkOut : user.getCheckOuts()) {
                if (checkOut.getBook().equals(book)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Determines how many unique books a particular user has borrowed during a specified period.
     * A book is considered borrowed during the period if the checkout's start date is on or after
     * the specified start date and the checkout's end date is on or before the specified end date.
     *
     * @param user the user to check borrow history for
     * @param start the start date of the period (inclusive)
     * @param end the end date of the period (inclusive)
     * @return the number of unique books borrowed by the user during the specified period
     * @throws IllegalArgumentException if user, start, or end parameters are null, or if start date is after end date
     */
    public int uniqueBooksBorrowedByUser(User user, Date start, Date end) {
        if (user == null || start == null || end == null) {
            throw new IllegalArgumentException("User, start date, and end date cannot be null");
        }
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            Date checkoutStart = checkOut.getStartDate();
            Date checkoutEnd = checkOut.getEndDate();
            
            if ((checkoutStart.equals(start) || checkoutStart.after(start)) && 
                (checkoutEnd.equals(end) || checkoutEnd.before(end))) {
                uniqueBooks.add(checkOut.getBook());
            }
        }
        return uniqueBooks.size();
    }

    /**
     * Calculates the average page count of the unique books borrowed by a specific user.
     * This method considers all books the user has ever checked out, counting each book only once.
     *
     * @param user the user to calculate average page count for
     * @return the average page count of unique books borrowed by the user, or 0.0 if no books were borrowed
     * @throws IllegalArgumentException if the user parameter is null
     */
    public double averageNumberOfPagesUniqueBooksByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            uniqueBooks.add(checkOut.getBook());
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
     * This method counts each book only once regardless of how many times it was checked out.
     *
     * @param user the user to count unique books for
     * @return the number of unique books checked out by the user
     * @throws IllegalArgumentException if the user parameter is null
     */
    public int totalUniqueBooksCheckedOutByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        Set<Book> uniqueBooks = new HashSet<>();
        for (CheckOut checkOut : user.getCheckOuts()) {
            uniqueBooks.add(checkOut.getBook());
        }
        return uniqueBooks.size();
    }
}