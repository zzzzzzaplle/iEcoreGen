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
        
        // Create books
        javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create users
        memberM001 = new MemberUser("John Doe", "john@email.com", "123 Main St", "M001");
        memberM002 = new MemberUser("Jane Smith", "jane@email.com", "456 Oak St", "M002");
        guestG001 = new GuestUser("Guest User", "guest@email.com", "Unknown", "G001");
        
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
        // Setup: Create loans for Java Programming book with multiple checkouts
        Loan loan1 = new Loan(javaBook, "2023-01-01", "2023-01-15");
        Loan loan2 = new Loan(javaBook, "2023-02-01", "2023-02-15");
        Loan loan3 = new Loan(javaBook, "2023-03-01", "2023-03-15");
        
        // Add loans to user and increment checkout count
        memberM001.addLoan(loan1);
        javaBook.incrementCheckoutCount();
        memberM001.addLoan(loan2);
        javaBook.incrementCheckoutCount();
        memberM001.addLoan(loan3);
        javaBook.incrementCheckoutCount();
        
        // Test: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckouts("123456");
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with multiple checkouts should return 3", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Setup: Python Basics book has no checkouts (no loans created)
        
        // Test: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckouts("654321");
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Setup: Create one loan for Algorithms book
        Loan loan = new Loan(algorithmsBook, "2023-04-01", "2023-04-15");
        
        // Add loan to user and increment checkout count
        memberM001.addLoan(loan);
        algorithmsBook.incrementCheckoutCount();
        
        // Test: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckouts("789012");
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book with one checkout should return 1", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Setup: Create loans for Data Structures book with mixed users
        Loan loan1 = new Loan(dataStructuresBook, "2023-05-01", "2023-05-15");
        Loan loan2 = new Loan(dataStructuresBook, "2023-06-01", "2023-06-15");
        Loan loan3 = new Loan(dataStructuresBook, "2023-07-01", "2023-07-15");
        
        // Add loans to different users and increment checkout count
        memberM001.addLoan(loan1);
        dataStructuresBook.incrementCheckoutCount();
        guestG001.addLoan(loan2);
        dataStructuresBook.incrementCheckoutCount();
        memberM002.addLoan(loan3);
        dataStructuresBook.incrementCheckoutCount();
        
        // Test: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckouts("101112");
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with mixed member/guest checkouts should return 3", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Setup: Create two loans for Web Development book with same user
        Loan loan1 = new Loan(webDevelopmentBook, "2023-08-01", "2023-08-15");
        Loan loan2 = new Loan(webDevelopmentBook, "2023-08-16", "2023-08-30");
        
        // Add loans to same user and increment checkout count
        memberM001.addLoan(loan1);
        webDevelopmentBook.incrementCheckoutCount();
        memberM001.addLoan(loan2);
        webDevelopmentBook.incrementCheckoutCount();
        
        // Test: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckouts("131415");
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book with same user multiple checkouts should return 2", 2, result);
    }
}