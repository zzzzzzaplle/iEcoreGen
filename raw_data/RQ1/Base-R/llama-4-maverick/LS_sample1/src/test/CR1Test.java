import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Library library;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private MemberUser member1;
    private MemberUser member2;
    private GuestUser guest1;
    
    @Before
    public void setUp() {
        // Initialize library
        library = new Library();
        
        // Create books
        javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create users
        member1 = new MemberUser("John Doe", "john@email.com", "123 Main St", "M001");
        member2 = new MemberUser("Jane Smith", "jane@email.com", "456 Oak St", "M002");
        guest1 = new GuestUser("Guest User", "guest@email.com", "Unknown", "G001");
        
        // Add books to library
        library.addBook(javaBook);
        library.addBook(pythonBook);
        library.addBook(algorithmsBook);
        library.addBook(dataStructuresBook);
        library.addBook(webDevelopmentBook);
        
        // Add users to library
        library.addUser(member1);
        library.addUser(member2);
        library.addUser(guest1);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: Counting Checkouts for a Book with Multiple Checkouts
        
        // Setup: Create loans for Java Programming book
        Loan loan1 = new Loan(javaBook, "2023-01-01", "2023-01-15");
        Loan loan2 = new Loan(javaBook, "2023-02-01", "2023-02-15");
        Loan loan3 = new Loan(javaBook, "2023-03-01", "2023-03-15");
        
        // Add loans to member1 and increment checkout count
        member1.addLoan(loan1);
        javaBook.incrementCheckoutCount();
        member1.addLoan(loan2);
        javaBook.incrementCheckoutCount();
        member1.addLoan(loan3);
        javaBook.incrementCheckoutCount();
        
        // Execute: Count checkouts for Java Programming book
        int result = library.countBookCheckouts("978-3-16-148410-0");
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with multiple checkouts should return 3", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: Counting Checkouts for a Book with No Checkouts
        
        // Setup: Python Basics book has no loans (default checkout count is 0)
        
        // Execute: Count checkouts for Python Basics book
        int result = library.countBookCheckouts("978-1-23-456789-0");
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: Counting Checkouts for a Book with One Checkout
        
        // Setup: Create loan for Algorithms book
        Loan loan = new Loan(algorithmsBook, "2023-04-01", "2023-04-15");
        
        // Add loan to member1 and increment checkout count
        member1.addLoan(loan);
        algorithmsBook.incrementCheckoutCount();
        
        // Execute: Count checkouts for Algorithms book
        int result = library.countBookCheckouts("978-0-12-345678-9");
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book with one checkout should return 1", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
        
        // Setup: Create loans for Data Structures book by different users
        Loan loan1 = new Loan(dataStructuresBook, "2023-05-01", "2023-05-15");
        Loan loan2 = new Loan(dataStructuresBook, "2023-06-01", "2023-06-15");
        Loan loan3 = new Loan(dataStructuresBook, "2023-07-01", "2023-07-15");
        
        // Add loans to different users and increment checkout count
        member1.addLoan(loan1);
        dataStructuresBook.incrementCheckoutCount();
        guest1.addLoan(loan2);
        dataStructuresBook.incrementCheckoutCount();
        member2.addLoan(loan3);
        dataStructuresBook.incrementCheckoutCount();
        
        // Execute: Count checkouts for Data Structures book
        int result = library.countBookCheckouts("978-9-87-654321-0");
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with mix of members and guests should return 3", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: Counting Checkouts for a Book with Same User
        
        // Setup: Create loans for Web Development book by same user
        Loan loan1 = new Loan(webDevelopmentBook, "2023-08-01", "2023-08-15");
        Loan loan2 = new Loan(webDevelopmentBook, "2023-08-16", "2023-08-30");
        
        // Add loans to member1 and increment checkout count
        member1.addLoan(loan1);
        webDevelopmentBook.incrementCheckoutCount();
        member1.addLoan(loan2);
        webDevelopmentBook.incrementCheckoutCount();
        
        // Execute: Count checkouts for Web Development book
        int result = library.countBookCheckouts("978-2-36-547891-0");
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book with same user multiple checkouts should return 2", 2, result);
    }
}