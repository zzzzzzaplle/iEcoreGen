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
        // SetUp: Create book and checkouts
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Checkout 1
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        user.addCheckOut(checkout1);
        
        // Checkout 2
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 00:00:00"));
        user.addCheckOut(checkout2);
        
        // Checkout 3
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 00:00:00"));
        user.addCheckOut(checkout3);
        
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        
        // Test: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() throws Exception {
        // SetUp: Create book but no checkouts
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        librarySystem.addBook(book);
        
        // Test: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // SetUp: Create book with one checkout
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 00:00:00"));
        user.addCheckOut(checkout);
        
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        
        // Test: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // SetUp: Create book with checkouts from different users
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Member M001 checkout
        User user1 = new User();
        user1.setID("M001");
        user1.setType(UserType.MEMBER);
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 00:00:00"));
        user1.addCheckOut(checkout1);
        
        // Guest G001 checkout
        User user2 = new User();
        user2.setID("G001");
        user2.setType(UserType.GUEST);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        user2.addCheckOut(checkout2);
        
        // Member M002 checkout
        User user3 = new User();
        user3.setID("M002");
        user3.setType(UserType.MEMBER);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 00:00:00"));
        user3.addCheckOut(checkout3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        librarySystem.addUser(user3);
        librarySystem.addBook(book);
        
        // Test: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // SetUp: Create book with multiple checkouts by same user
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Checkout 1
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 00:00:00"));
        user.addCheckOut(checkout1);
        
        // Checkout 2
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        
        // Test: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book should have 2 checkouts", 2, result);
    }
}