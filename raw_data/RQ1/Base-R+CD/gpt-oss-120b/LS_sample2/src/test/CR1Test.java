import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private User member1;
    private User member2;
    private User guest1;
    
    @Before
    public void setUp() throws ParseException {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setISBN("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        
        // Add books to library system
        librarySystem.addBook(javaBook);
        librarySystem.addBook(pythonBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        // Create users
        member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        guest1 = new User();
        guest1.setID("G001");
        guest1.setType(UserType.GUEST);
        
        // Add users to library system
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(guest1);
    }
    
    @Test
    public void testCase1_countCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Setup: Create checkouts for Java Programming book
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(sdf.parse("2023-01-01"));
        checkout1.setEndDate(sdf.parse("2023-01-15"));
        member1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(sdf.parse("2023-02-01"));
        checkout2.setEndDate(sdf.parse("2023-02-15"));
        member1.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(sdf.parse("2023-03-01"));
        checkout3.setEndDate(sdf.parse("2023-03-15"));
        member1.addCheckOut(checkout3);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countCheckoutsForBookWithNoCheckouts() {
        // Setup: No checkouts for Python Basics book (already created in setUp)
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Assert: The book has been checked out 0 times
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countCheckoutsForBookWithOneCheckout() throws ParseException {
        // Setup: Create one checkout for Algorithms book
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(sdf.parse("2023-04-01"));
        checkout.setEndDate(sdf.parse("2023-04-15"));
        member1.addCheckOut(checkout);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Assert: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Setup: Create checkouts for Data Structures book by different users
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(sdf.parse("2023-05-01"));
        checkout1.setEndDate(sdf.parse("2023-05-15"));
        member1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(sdf.parse("2023-06-01"));
        checkout2.setEndDate(sdf.parse("2023-06-15"));
        guest1.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(sdf.parse("2023-07-01"));
        checkout3.setEndDate(sdf.parse("2023-07-15"));
        member2.addCheckOut(checkout3);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countCheckoutsForBookWithSameUser() throws ParseException {
        // Setup: Create multiple checkouts for Web Development book by the same user
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate(sdf.parse("2023-08-01"));
        checkout1.setEndDate(sdf.parse("2023-08-15"));
        member1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate(sdf.parse("2023-08-16"));
        checkout2.setEndDate(sdf.parse("2023-08-30"));
        member1.addCheckOut(checkout2);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Assert: The book has been checked out 2 times
        assertEquals(2, result);
    }
}