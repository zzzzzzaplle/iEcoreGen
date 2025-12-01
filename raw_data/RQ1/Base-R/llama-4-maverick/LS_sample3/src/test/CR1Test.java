import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private MemberUser memberM001;
    private MemberUser memberM002;
    private GuestUser guestG001;
    
    @Before
    public void setUp() {
        // Initialize library system
        librarySystem = new LibrarySystem();
        
        // Create books as specified in test cases
        javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create users
        memberM001 = new MemberUser("Member One", "m001@email.com", "Address 1", "M001");
        memberM002 = new MemberUser("Member Two", "m002@email.com", "Address 2", "M002");
        guestG001 = new GuestUser("Guest One", "g001@email.com", "Address 3", "G001");
        
        // Add books and users to library system
        librarySystem.addBook(javaBook);
        librarySystem.addBook(pythonBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: Counting Checkouts for a Book with Multiple Checkouts
        
        // SetUp: Checkout Java Programming book 3 times by Member M001
        librarySystem.checkoutBook(memberM001, javaBook, "2023-01-01", "2023-01-15");
        librarySystem.checkoutBook(memberM001, javaBook, "2023-02-01", "2023-02-15");
        librarySystem.checkoutBook(memberM001, javaBook, "2023-03-01", "2023-03-15");
        
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = librarySystem.countBookCheckouts("978-3-16-148410-0");
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: Counting Checkouts for a Book with No Checkouts
        
        // SetUp: Create Python Basics book but no user checks it out
        // (Book is already created in setUp, but no checkouts performed)
        
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = librarySystem.countBookCheckouts("978-1-23-456789-0");
        
        // Expected Output: The book has been checked out 0 time
        assertEquals("Book should have been checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: Counting Checkouts for a Book with One Checkout
        
        // SetUp: Checkout Algorithms book once by Member M001
        librarySystem.checkoutBook(memberM001, algorithmsBook, "2023-04-01", "2023-04-15");
        
        // Input: Count the number of times the book with title "Algorithms" has been checked out
        int result = librarySystem.countBookCheckouts("978-0-12-345678-9");
        
        // Expected Output: The book has been checked out 1 time
        assertEquals("Book should have been checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
        
        // SetUp: Checkout Data Structures book by different users
        librarySystem.checkoutBook(memberM001, dataStructuresBook, "2023-05-01", "2023-05-15");
        librarySystem.checkoutBook(guestG001, dataStructuresBook, "2023-06-01", "2023-06-15");
        librarySystem.checkoutBook(memberM002, dataStructuresBook, "2023-07-01", "2023-07-15");
        
        // Input: Count the number of times the book with title "Data Structures" has been checked out
        int result = librarySystem.countBookCheckouts("978-9-87-654321-0");
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: Counting Checkouts for a Book with Same User
        
        // SetUp: Checkout Web Development book twice by the same Member M001
        librarySystem.checkoutBook(memberM001, webDevelopmentBook, "2023-08-01", "2023-08-15");
        librarySystem.checkoutBook(memberM001, webDevelopmentBook, "2023-08-16", "2023-08-30");
        
        // Input: Count the number of times the book with title "Web Development" has been checked out
        int result = librarySystem.countBookCheckouts("978-2-36-547891-0");
        
        // Expected Output: The book has been checked out 2 times
        assertEquals("Book should have been checked out 2 times", 2, result);
    }
}