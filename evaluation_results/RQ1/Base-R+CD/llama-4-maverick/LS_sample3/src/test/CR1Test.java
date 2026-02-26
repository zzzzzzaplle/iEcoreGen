import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        member.setCheckOuts(new ArrayList<>());
        
        // Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        member.getCheckOuts().add(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        member.getCheckOuts().add(checkout2);
        
        // Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        member.getCheckOuts().add(checkout3);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() throws ParseException {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // No User check out this Book.
        // Add empty user list to library system
        librarySystem.setUsers(new ArrayList<>());
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() throws ParseException {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        member.setCheckOuts(new ArrayList<>());
        
        // Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01"));
        checkout.setEndDate(dateFormat.parse("2023-04-15"));
        member.getCheckOuts().add(checkout);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Create Member M001
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        member1.setCheckOuts(new ArrayList<>());
        
        // Create Guest G001
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        guest.setCheckOuts(new ArrayList<>());
        
        // Create Member M002
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        member2.setCheckOuts(new ArrayList<>());
        
        // Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15"));
        member1.getCheckOuts().add(checkout1);
        
        // Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15"));
        guest.getCheckOuts().add(checkout2);
        
        // Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15"));
        member2.getCheckOuts().add(checkout3);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(guest);
        users.add(member2);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() throws ParseException {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        member.setCheckOuts(new ArrayList<>());
        
        // Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15"));
        member.getCheckOuts().add(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30"));
        member.getCheckOuts().add(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, result);
    }
}