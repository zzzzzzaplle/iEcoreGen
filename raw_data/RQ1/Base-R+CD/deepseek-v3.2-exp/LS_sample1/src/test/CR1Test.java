import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaProgrammingBook;
    private Book pythonBasicsBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private User memberM001;
    private User memberM002;
    private User guestG001;
    
    @Before
    public void setUp() {
        // Initialize library system
        librarySystem = new LibrarySystem();
        
        // Create users
        memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member M001");
        memberM001.setType(UserType.MEMBER);
        
        memberM002 = new User();
        memberM002.setID("M002");
        memberM002.setName("Member M002");
        memberM002.setType(UserType.MEMBER);
        
        guestG001 = new User();
        guestG001.setID("G001");
        guestG001.setName("Guest G001");
        guestG001.setType(UserType.GUEST);
        
        // Add users to library system
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
        
        // Create books
        javaProgrammingBook = new Book();
        javaProgrammingBook.setTitle("Java Programming");
        javaProgrammingBook.setBarcode("123456");
        javaProgrammingBook.setISBN("978-3-16-148410-0");
        javaProgrammingBook.setNumberOfPages(500);
        
        pythonBasicsBook = new Book();
        pythonBasicsBook.setTitle("Python Basics");
        pythonBasicsBook.setBarcode("654321");
        pythonBasicsBook.setISBN("978-1-23-456789-0");
        pythonBasicsBook.setNumberOfPages(400);
        
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
        librarySystem.addBook(javaProgrammingBook);
        librarySystem.addBook(pythonBasicsBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create checkout records for Java Programming book
        librarySystem.createCheckOut(memberM001, javaProgrammingBook, "2023-01-01", "2023-01-15");
        librarySystem.createCheckOut(memberM001, javaProgrammingBook, "2023-02-01", "2023-02-15");
        librarySystem.createCheckOut(memberM001, javaProgrammingBook, "2023-03-01", "2023-03-15");
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(javaProgrammingBook);
        
        // Verify the expected output
        assertEquals("The book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Python Basics book has no checkouts
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(pythonBasicsBook);
        
        // Verify the expected output
        assertEquals("The book should have been checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create checkout record for Algorithms book
        librarySystem.createCheckOut(memberM001, algorithmsBook, "2023-04-01", "2023-04-15");
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify the expected output
        assertEquals("The book should have been checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create checkout records for Data Structures book by different users
        librarySystem.createCheckOut(memberM001, dataStructuresBook, "2023-05-01", "2023-05-15");
        librarySystem.createCheckOut(guestG001, dataStructuresBook, "2023-06-01", "2023-06-15");
        librarySystem.createCheckOut(memberM002, dataStructuresBook, "2023-07-01", "2023-07-15");
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify the expected output
        assertEquals("The book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create checkout records for Web Development book by the same user
        librarySystem.createCheckOut(memberM001, webDevelopmentBook, "2023-08-01", "2023-08-15");
        librarySystem.createCheckOut(memberM001, webDevelopmentBook, "2023-08-16", "2023-08-30");
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify the expected output
        assertEquals("The book should have been checked out 2 times", 2, result);
    }
}