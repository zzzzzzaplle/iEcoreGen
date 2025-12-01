import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user U001
        User user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        
        // SetUp: Create book B001
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        
        // SetUp: Create CheckOut record for User U001 with book B001
        try {
            Loan loan = new Loan(book, "2023-10-01", "2023-10-15");
            user.addLoan(loan);
        } catch (Exception e) {
            fail("Loan creation failed: " + e.getMessage());
        }
        
        librarySystem.addUser(user);
        
        // Calculate average pages for period that includes the loan
        double result = librarySystem.calculateAveragePageCount(user, "2023-10-01", "2023-10-15");
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user U002
        User user = new MemberUser("Bob", "bob@example.com", "456 Elm St.", "U002");
        
        // SetUp: Create books
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // SetUp: Create CheckOut records for User U002
        try {
            // First checkout for B002
            Loan loan1 = new Loan(book1, "2023-09-20", "2023-09-25");
            user.addLoan(loan1);
            
            // Second checkout for B002 (same book, should be counted once)
            Loan loan2 = new Loan(book1, "2023-10-20", "2023-10-25");
            user.addLoan(loan2);
            
            // Checkout for B003
            Loan loan3 = new Loan(book2, "2023-09-30", "2023-10-05");
            user.addLoan(loan3);
        } catch (Exception e) {
            fail("Loan creation failed: " + e.getMessage());
        }
        
        librarySystem.addUser(user);
        
        // Calculate average pages for period that includes all loans
        double result = librarySystem.calculateAveragePageCount(user, "2023-09-20", "2023-10-25");
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        // (Book2 is counted only once even though checked out twice)
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003 with no borrowed books
        User user = new MemberUser("Charlie", "charlie@example.com", "789 Oak St.", "U003");
        
        librarySystem.addUser(user);
        
        // Calculate average pages for any period
        double result = librarySystem.calculateAveragePageCount(user, "2023-01-01", "2023-12-31");
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        User user = new MemberUser("Daisy", "daisy@example.com", "101 Maple St.", "U004");
        
        // SetUp: Create books with varying page counts
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        // SetUp: Create CheckOut records for User U004
        try {
            Loan loan1 = new Loan(book1, "2023-09-15", "2023-09-22");
            user.addLoan(loan1);
            
            Loan loan2 = new Loan(book2, "2023-09-25", "2023-10-02");
            user.addLoan(loan2);
            
            Loan loan3 = new Loan(book3, "2023-10-03", "2023-10-10");
            user.addLoan(loan3);
        } catch (Exception e) {
            fail("Loan creation failed: " + e.getMessage());
        }
        
        librarySystem.addUser(user);
        
        // Calculate average pages for period that includes all loans
        double result = librarySystem.calculateAveragePageCount(user, "2023-09-15", "2023-10-10");
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        User user = new GuestUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        
        // SetUp: Create books
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        // SetUp: Create CheckOut records for User U005
        try {
            Loan loan1 = new Loan(book1, "2023-08-20", "2023-08-30");
            user.addLoan(loan1);
            
            Loan loan2 = new Loan(book2, "2023-09-01", "2023-09-10");
            user.addLoan(loan2);
        } catch (Exception e) {
            fail("Loan creation failed: " + e.getMessage());
        }
        
        librarySystem.addUser(user);
        
        // Calculate average pages for period that includes all loans
        double result = librarySystem.calculateAveragePageCount(user, "2023-08-20", "2023-09-10");
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}