import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user Alice
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // SetUp: Create book B001
        Book book = new Book();
        book.setBarcode("B001");
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setNumberOfPages(300);
        
        // SetUp: Create checkout record
        librarySystem.processCheckout(user, book, "2023-10-01", "2023-10-15");
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user Bob
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // SetUp: Create books
        Book book1 = new Book();
        book1.setBarcode("B002");
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setNumberOfPages(500);
        
        Book book2 = new Book();
        book2.setBarcode("B003");
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setNumberOfPages(600);
        
        // SetUp: Create checkout records (including duplicate checkout for B002)
        librarySystem.processCheckout(user, book1, "2023-09-20", "2023-09-25");
        librarySystem.processCheckout(user, book1, "2023-10-20", "2023-10-25"); // Duplicate book
        librarySystem.processCheckout(user, book2, "2023-09-30", "2023-10-05");
        
        // Calculate average pages (should count unique books only)
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user Charlie with no books
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Calculate average pages for user with no checkouts
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user Daisy
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // SetUp: Create books with different page counts
        Book book1 = new Book();
        book1.setBarcode("B004");
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setNumberOfPages(250);
        
        Book book2 = new Book();
        book2.setBarcode("B005");
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setNumberOfPages(350);
        
        Book book3 = new Book();
        book3.setBarcode("B006");
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setNumberOfPages(450);
        
        // SetUp: Create checkout records for all three books
        librarySystem.processCheckout(user, book1, "2023-09-15", "2023-09-22");
        librarySystem.processCheckout(user, book2, "2023-09-25", "2023-10-02");
        librarySystem.processCheckout(user, book3, "2023-10-03", "2023-10-10");
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user Eve
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // SetUp: Create books
        Book book1 = new Book();
        book1.setBarcode("B007");
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setNumberOfPages(400);
        
        Book book2 = new Book();
        book2.setBarcode("B008");
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setNumberOfPages(350);
        
        // SetUp: Create checkout records
        librarySystem.processCheckout(user, book1, "2023-08-20", "2023-08-30");
        librarySystem.processCheckout(user, book2, "2023-09-01", "2023-09-10");
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}