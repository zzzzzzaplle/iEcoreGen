import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
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
        javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create test users
        memberM001 = new User("John Doe", "john@example.com", "123 Main St", "M001", UserType.MEMBER);
        memberM002 = new User("Jane Smith", "jane@example.com", "456 Oak St", "M002", UserType.MEMBER);
        guestG001 = new User("Guest User", "guest@example.com", "Unknown", "G001", UserType.GUEST);
        
        // Add books to library system
        librarySystem.getBooks().add(javaBook);
        librarySystem.getBooks().add(pythonBook);
        librarySystem.getBooks().add(algorithmsBook);
        librarySystem.getBooks().add(dataStructuresBook);
        librarySystem.getBooks().add(webDevelopmentBook);
        
        // Add users to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getUsers().add(memberM002);
        librarySystem.getUsers().add(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create checkouts for Java Programming book by Member M001
        CheckOut checkout1 = new CheckOut("2023-01-01", "2023-01-15", javaBook);
        CheckOut checkout2 = new CheckOut("2023-02-01", "2023-02-15", javaBook);
        CheckOut checkout3 = new CheckOut("2023-03-01", "2023-03-15", javaBook);
        
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        memberM001.addCheckOut(checkout3);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Java Programming book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: No checkouts for Python Basics book (already set up in @Before)
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Python Basics book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create one checkout for Algorithms book by Member M001
        CheckOut checkout = new CheckOut("2023-04-01", "2023-04-15", algorithmsBook);
        memberM001.addCheckOut(checkout);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Algorithms book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create checkouts for Data Structures book by different users
        CheckOut checkout1 = new CheckOut("2023-05-01", "2023-05-15", dataStructuresBook);
        CheckOut checkout2 = new CheckOut("2023-06-01", "2023-06-15", dataStructuresBook);
        CheckOut checkout3 = new CheckOut("2023-07-01", "2023-07-15", dataStructuresBook);
        
        memberM001.addCheckOut(checkout1);
        guestG001.addCheckOut(checkout2);
        memberM002.addCheckOut(checkout3);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Data Structures book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create two checkouts for Web Development book by the same user (Member M001)
        CheckOut checkout1 = new CheckOut("2023-08-01", "2023-08-15", webDevelopmentBook);
        CheckOut checkout2 = new CheckOut("2023-08-16", "2023-08-30", webDevelopmentBook);
        
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Web Development book should have 2 checkouts", 2, result);
    }
}