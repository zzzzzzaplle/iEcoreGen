import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private User user;
    private Book book1, book2, book3;
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user and book
        user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        book1 = new Book("Java Programming", "B001", "123456789", 300);
        
        // Create checkout record
        Loan loan = new Loan(book1, "2023-10-01", "2023-10-15");
        user.addLoan(loan);
        
        // Calculate average page count
        double result = user.calculateAveragePageCount();
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user and books
        user = new MemberUser("Bob", "bob@example.com", "456 Elm St.", "U002");
        book1 = new Book("Data Structures", "B002", "987654321", 500);
        book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // Create checkout records (multiple checkouts for same book and different book)
        Loan loan1 = new Loan(book1, "2023-09-20", "2023-09-25");
        Loan loan2 = new Loan(book1, "2023-10-20", "2023-10-25"); // Same book, should be counted only once
        Loan loan3 = new Loan(book2, "2023-09-30", "2023-10-05");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Calculate average page count
        double result = user.calculateAveragePageCount();
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user with no books
        user = new MemberUser("Charlie", "charlie@example.com", "789 Oak St.", "U003");
        
        // Calculate average page count
        double result = user.calculateAveragePageCount();
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user and books with varying page counts
        user = new MemberUser("Daisy", "daisy@example.com", "101 Maple St.", "U004");
        book1 = new Book("Web Development", "B004", "321321321", 250);
        book2 = new Book("Machine Learning", "B005", "456456456", 350);
        book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        // Create checkout records
        Loan loan1 = new Loan(book1, "2023-09-15", "2023-09-22");
        Loan loan2 = new Loan(book2, "2023-09-25", "2023-10-02");
        Loan loan3 = new Loan(book3, "2023-10-03", "2023-10-10");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Calculate average page count
        double result = user.calculateAveragePageCount();
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user and books
        user = new GuestUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        book1 = new Book("Networking", "B007", "111111111", 400);
        book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        // Create checkout records
        Loan loan1 = new Loan(book1, "2023-08-20", "2023-08-30");
        Loan loan2 = new Loan(book2, "2023-09-01", "2023-09-10");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Calculate average page count
        double result = user.calculateAveragePageCount();
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}