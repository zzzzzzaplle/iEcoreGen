import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        // Initialize a fresh LibrarySystem before each test
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // Create user U001: Alice
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // Create book B001: Java Programming
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        
        // Create checkout record for user U001 with book B001
        CheckOut checkout = new CheckOut(LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 15), book);
        user.addCheckOut(checkout);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Calculate average number of pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify the result: 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Create user U002: Bob
        User user = new User("Bob", "bob@example.com", "456 Elm St.", "U002", UserType.MEMBER);
        
        // Create books
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // Create checkout records for user U002 (including duplicate checkout for B002)
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), book1);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 10, 20), LocalDate.of(2023, 10, 25), book1);
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 9, 30), LocalDate.of(2023, 10, 5), book2);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Calculate average number of pages (should consider unique books only)
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify the result: (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create user U003: Charlie with no checkouts
        User user = new User("Charlie", "charlie@example.com", "789 Oak St.", "U003", UserType.MEMBER);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Calculate average number of pages for user with no books
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify the result: 0.0 pages (handles empty case)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Create user U004: Daisy
        User user = new User("Daisy", "daisy@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // Create books with different page counts
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        // Create checkout records for user U004
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 9, 15), LocalDate.of(2023, 9, 22), book1);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 9, 25), LocalDate.of(2023, 10, 2), book2);
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 10, 3), LocalDate.of(2023, 10, 10), book3);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Calculate average number of pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify the result: (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Create guest user U005: Eve
        User user = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.GUEST);
        
        // Create books
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        // Create checkout records for user U005
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 8, 20), LocalDate.of(2023, 8, 30), book1);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10), book2);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Calculate average number of pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify the result: (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}