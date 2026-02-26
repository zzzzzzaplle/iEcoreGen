import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

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
        
        // Create test books
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
        
        // Create test users
        memberM001 = new User();
        memberM001.setName("Member One");
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        memberM002 = new User();
        memberM002.setName("Member Two");
        memberM002.setID("M002");
        memberM002.setType(UserType.MEMBER);
        
        guestG001 = new User();
        guestG001.setName("Guest One");
        guestG001.setID("G001");
        guestG001.setType(UserType.GUEST);
        
        // Add books to library system
        librarySystem.addBook(javaProgrammingBook);
        librarySystem.addBook(pythonBasicsBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        // Add users to library system
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
        
        // Setup: Create checkouts for Java Programming book by Member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaProgrammingBook);
        checkout1.setStartDate("2023-01-01");
        checkout1.setEndDate("2023-01-15");
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaProgrammingBook);
        checkout2.setStartDate("2023-02-01");
        checkout2.setEndDate("2023-02-15");
        memberM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaProgrammingBook);
        checkout3.setStartDate("2023-03-01");
        checkout3.setEndDate("2023-03-15");
        memberM001.addCheckOut(checkout3);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckOuts(javaProgrammingBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Java Programming book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: "Counting Checkouts for a Book with No Checkouts"
        
        // Setup: No users check out Python Basics book (already done in setUp)
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckOuts(pythonBasicsBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Python Basics book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: "Counting Checkouts for a Book with One Checkout"
        
        // Setup: Create one checkout for Algorithms book by Member M001
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate("2023-04-01");
        checkout.setEndDate("2023-04-15");
        memberM001.addCheckOut(checkout);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Algorithms book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
        
        // Setup: Create checkouts for Data Structures book by different users
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate("2023-05-01");
        checkout1.setEndDate("2023-05-15");
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate("2023-06-01");
        checkout2.setEndDate("2023-06-15");
        guestG001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate("2023-07-01");
        checkout3.setEndDate("2023-07-15");
        memberM002.addCheckOut(checkout3);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Data Structures book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: "Counting Checkouts for a Book with Same User"
        
        // Setup: Create two checkouts for Web Development book by Member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-15");
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate("2023-08-16");
        checkout2.setEndDate("2023-08-30");
        memberM001.addCheckOut(checkout2);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Web Development book should have 2 checkouts", 2, result);
    }
}