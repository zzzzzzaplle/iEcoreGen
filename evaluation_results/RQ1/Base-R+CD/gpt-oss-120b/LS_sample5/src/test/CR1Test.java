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
        javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Add books to library
        librarySystem.addBook(javaBook);
        librarySystem.addBook(pythonBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        // Create users
        member1 = new User("Member 1", "m1@example.com", "Address 1", "M001", UserType.MEMBER);
        member2 = new User("Member 2", "m2@example.com", "Address 2", "M002", UserType.MEMBER);
        guest1 = new User("Guest 1", "g1@example.com", "Address 3", "G001", UserType.GUEST);
        
        // Add users to library
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(guest1);
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Setup: Checkout the Java Programming book 3 times by Member M001
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate1 = sdf.parse("2023-01-01");
        Date endDate1 = sdf.parse("2023-01-15");
        librarySystem.checkoutBook(member1, javaBook, startDate1, endDate1);
        
        Date startDate2 = sdf.parse("2023-02-01");
        Date endDate2 = sdf.parse("2023-02-15");
        librarySystem.checkoutBook(member1, javaBook, startDate2, endDate2);
        
        Date startDate3 = sdf.parse("2023-03-01");
        Date endDate3 = sdf.parse("2023-03-15");
        librarySystem.checkoutBook(member1, javaBook, startDate3, endDate3);
        
        // Execute: Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckOuts(javaBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals(3, checkoutCount);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() {
        // Setup: No checkouts for Python Basics book
        
        // Execute: Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckOuts(pythonBook);
        
        // Assert: The book has been checked out 0 times
        assertEquals(0, checkoutCount);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() throws ParseException {
        // Setup: Checkout the Algorithms book 1 time by Member M001
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate = sdf.parse("2023-04-01");
        Date endDate = sdf.parse("2023-04-15");
        librarySystem.checkoutBook(member1, algorithmsBook, startDate, endDate);
        
        // Execute: Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Assert: The book has been checked out 1 time
        assertEquals(1, checkoutCount);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Setup: Checkout the Data Structures book by Member M001, Guest G001, and Member M002
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate1 = sdf.parse("2023-05-01");
        Date endDate1 = sdf.parse("2023-05-15");
        librarySystem.checkoutBook(member1, dataStructuresBook, startDate1, endDate1);
        
        Date startDate2 = sdf.parse("2023-06-01");
        Date endDate2 = sdf.parse("2023-06-15");
        librarySystem.checkoutBook(guest1, dataStructuresBook, startDate2, endDate2);
        
        Date startDate3 = sdf.parse("2023-07-01");
        Date endDate3 = sdf.parse("2023-07-15");
        librarySystem.checkoutBook(member2, dataStructuresBook, startDate3, endDate3);
        
        // Execute: Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals(3, checkoutCount);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() throws ParseException {
        // Setup: Checkout the Web Development book 2 times by Member M001
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startDate1 = sdf.parse("2023-08-01");
        Date endDate1 = sdf.parse("2023-08-15");
        librarySystem.checkoutBook(member1, webDevelopmentBook, startDate1, endDate1);
        
        Date startDate2 = sdf.parse("2023-08-16");
        Date endDate2 = sdf.parse("2023-08-30");
        librarySystem.checkoutBook(member1, webDevelopmentBook, startDate2, endDate2);
        
        // Execute: Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Assert: The book has been checked out 2 times
        assertEquals(2, checkoutCount);
    }
}