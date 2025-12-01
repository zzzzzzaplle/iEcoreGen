import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesSingleBookBorrowed() {
        // SetUp: Create user with single book checkout
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        CheckOut checkout = new CheckOut("2023-10-01", "2023-10-15", book);
        user.addCheckOut(checkout);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesMultipleBooksBorrowed() {
        // SetUp: Create user with multiple book checkouts (including duplicate book)
        User user = new User("Bob", "bob@example.com", "456 Elm St.", "U002", UserType.MEMBER);
        
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // Multiple checkouts including duplicate book (B002 checked out twice)
        CheckOut checkout1 = new CheckOut("2023-09-20", "2023-09-25", book1);
        CheckOut checkout2 = new CheckOut("2023-10-20", "2023-10-25", book1); // Duplicate book
        CheckOut checkout3 = new CheckOut("2023-09-30", "2023-10-05", book2);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Calculate average pages (should only count unique books)
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user with no checkouts
        User user = new User("Charlie", "charlie@example.com", "789 Oak St.", "U003", UserType.MEMBER);
        
        // Calculate average pages for user with no books
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesDifferentPageCounts() {
        // SetUp: Create user with books of varying page counts
        User user = new User("Daisy", "daisy@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        CheckOut checkout1 = new CheckOut("2023-09-15", "2023-09-22", book1);
        CheckOut checkout2 = new CheckOut("2023-09-25", "2023-10-02", book2);
        CheckOut checkout3 = new CheckOut("2023-10-03", "2023-10-10", book3);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesGuestUser() {
        // SetUp: Create guest user with book checkouts
        User user = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.GUEST);
        
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        CheckOut checkout1 = new CheckOut("2023-08-20", "2023-08-30", book1);
        CheckOut checkout2 = new CheckOut("2023-09-01", "2023-09-10", book2);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Calculate average pages for guest user
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}