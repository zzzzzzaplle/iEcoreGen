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
        memberM001 = new MemberUser("Member One", "m001@library.com", "Address 1", "M001");
        memberM002 = new MemberUser("Member Two", "m002@library.com", "Address 2", "M002");
        guestG001 = new GuestUser("Guest One", "g001@library.com", "Address 3", "G001");
        
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
        
        // Setup: Create loans for Java Programming book with Member M001
        try {
            Loan loan1 = new Loan(javaBook, "2023-01-01", "2023-01-15");
            memberM001.addLoan(loan1);
            
            Loan loan2 = new Loan(javaBook, "2023-02-01", "2023-02-15");
            memberM001.addLoan(loan2);
            
            Loan loan3 = new Loan(javaBook, "2023-03-01", "2023-03-15");
            memberM001.addLoan(loan3);
        } catch (Exception e) {
            fail("Exception should not occur during loan creation");
        }
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckouts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: Counting Checkouts for a Book with No Checkouts
        
        // Setup: Python Basics book has no loans (already set up in @Before)
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckouts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: Counting Checkouts for a Book with One Checkout
        
        // Setup: Create one loan for Algorithms book with Member M001
        try {
            Loan loan = new Loan(algorithmsBook, "2023-04-01", "2023-04-15");
            memberM001.addLoan(loan);
        } catch (Exception e) {
            fail("Exception should not occur during loan creation");
        }
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
        
        // Setup: Create loans for Data Structures book with different users
        try {
            Loan loan1 = new Loan(dataStructuresBook, "2023-05-01", "2023-05-15");
            memberM001.addLoan(loan1);
            
            Loan loan2 = new Loan(dataStructuresBook, "2023-06-01", "2023-06-15");
            guestG001.addLoan(loan2);
            
            Loan loan3 = new Loan(dataStructuresBook, "2023-07-01", "2023-07-15");
            memberM002.addLoan(loan3);
        } catch (Exception e) {
            fail("Exception should not occur during loan creation");
        }
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: Counting Checkouts for a Book with Same User
        
        // Setup: Create two loans for Web Development book with same user (Member M001)
        try {
            Loan loan1 = new Loan(webDevelopmentBook, "2023-08-01", "2023-08-15");
            memberM001.addLoan(loan1);
            
            Loan loan2 = new Loan(webDevelopmentBook, "2023-08-16", "2023-08-30");
            memberM001.addLoan(loan2);
        } catch (Exception e) {
            fail("Exception should not occur during loan creation");
        }
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckouts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book should have 2 checkouts", 2, result);
    }
}