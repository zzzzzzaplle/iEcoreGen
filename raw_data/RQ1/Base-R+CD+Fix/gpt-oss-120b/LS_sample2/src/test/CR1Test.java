import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private User member1;
    private User member2;
    private User guest1;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        librarySystem = new LibrarySystem();
        member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        member1.setCheckOuts(new java.util.ArrayList<CheckOut>());
        
        member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        member2.setCheckOuts(new java.util.ArrayList<CheckOut>());
        
        guest1 = new User();
        guest1.setID("G001");
        guest1.setType(UserType.GUEST);
        guest1.setCheckOuts(new java.util.ArrayList<CheckOut>());
        
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(guest1);
        
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // Create book object
        Book javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        librarySystem.addBook(javaBook);
        
        // Create checkout records for member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        
        // Add checkouts to member M001
        member1.addCheckOut(checkout1);
        member1.addCheckOut(checkout2);
        member1.addCheckOut(checkout3);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify expected output
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() throws Exception {
        // Create book object
        Book pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        librarySystem.addBook(pythonBook);
        
        // No checkouts for this book
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify expected output
        assertEquals("Book should have been checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create book object
        Book algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        librarySystem.addBook(algorithmsBook);
        
        // Create checkout record for member M001
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01"));
        checkout.setEndDate(dateFormat.parse("2023-04-15"));
        
        // Add checkout to member M001
        member1.addCheckOut(checkout);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify expected output
        assertEquals("Book should have been checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create book object
        Book dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        librarySystem.addBook(dataStructuresBook);
        
        // Create checkout records for different users
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(dateFormat.parse("2023-07-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15"));
        
        // Add checkouts to respective users
        member1.addCheckOut(checkout1);
        guest1.addCheckOut(checkout2);
        member2.addCheckOut(checkout3);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify expected output
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create book object
        Book webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setISBN("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        librarySystem.addBook(webDevelopmentBook);
        
        // Create checkout records for member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30"));
        
        // Add both checkouts to member M001
        member1.addCheckOut(checkout1);
        member1.addCheckOut(checkout2);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify expected output
        assertEquals("Book should have been checked out 2 times", 2, result);
    }
}