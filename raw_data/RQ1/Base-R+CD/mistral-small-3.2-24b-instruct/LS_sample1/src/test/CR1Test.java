import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // Create book with specified attributes
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        // Create member user
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        
        // Create three checkouts for the same book by the same member
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 23:59:59"));
        
        // Add checkouts to member
        member.addCheckOut(checkout1);
        member.addCheckOut(checkout2);
        member.addCheckOut(checkout3);
        
        // Add book and member to library system
        librarySystem.addBook(book);
        librarySystem.addUser(member);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify the book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Create book with specified attributes
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // Add book to library system (no users or checkouts)
        librarySystem.addBook(book);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify the book has been checked out 0 times
        assertEquals("Book should have been checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create book with specified attributes
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        // Create member user
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        
        // Create one checkout for the book
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        
        // Add checkout to member
        member.addCheckOut(checkout);
        
        // Add book and member to library system
        librarySystem.addBook(book);
        librarySystem.addUser(member);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify the book has been checked out 1 time
        assertEquals("Book should have been checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create book with specified attributes
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Create first member user
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // Create guest user
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        
        // Create second member user
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        // Create checkouts for different users
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 23:59:59"));
        
        // Add checkouts to respective users
        member1.addCheckOut(checkout1);
        guest.addCheckOut(checkout2);
        member2.addCheckOut(checkout3);
        
        // Add book and users to library system
        librarySystem.addBook(book);
        librarySystem.addUser(member1);
        librarySystem.addUser(guest);
        librarySystem.addUser(member2);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify the book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create book with specified attributes
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        // Create member user
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        
        // Create two checkouts for the same book by the same member with consecutive dates
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        
        // Add both checkouts to the same member
        member.addCheckOut(checkout1);
        member.addCheckOut(checkout2);
        
        // Add book and member to library system
        librarySystem.addBook(book);
        librarySystem.addUser(member);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify the book has been checked out 2 times
        assertEquals("Book should have been checked out 2 times", 2, result);
    }
}