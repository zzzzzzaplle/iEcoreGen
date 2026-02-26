import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForABookWithMultipleCheckouts() throws ParseException {
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        // Create user
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        user.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 00:00:00"));
        user.addCheckOut(checkout2);
        
        // Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 00:00:00"));
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, librarySystem.countBookCheckOuts(book));
    }
    
    @Test
    public void testCase2_CountingCheckoutsForABookWithNoCheckouts() {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // Add book to library system
        librarySystem.getBooks().add(book);
        
        // No User check out this Book.
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, librarySystem.countBookCheckOuts(book));
    }
    
    @Test
    public void testCase3_CountingCheckoutsForABookWithOneCheckout() throws ParseException {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        // Create user
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 00:00:00"));
        user.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, librarySystem.countBookCheckOuts(book));
    }
    
    @Test
    public void testCase4_CountingCheckoutsForABookWithMixOfMembersAndGuests() throws ParseException {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Create member user
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // Create guest user
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        
        // Create another member user
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 00:00:00"));
        member1.addCheckOut(checkout1);
        
        // Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        guest.addCheckOut(checkout2);
        
        // Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 00:00:00"));
        member2.addCheckOut(checkout3);
        
        // Add users to library system
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(guest);
        librarySystem.getUsers().add(member2);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, librarySystem.countBookCheckOuts(book));
    }
    
    @Test
    public void testCase5_CountingCheckoutsForABookWithSameUser() throws ParseException {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        // Create user
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 00:00:00"));
        user.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, librarySystem.countBookCheckOuts(book));
    }
}