import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create a MEMBER user with ID: U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books: Book1 and Book2
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        // Record CheckOuts
        librarySystem.createCheckOut(user, book1, "2023-01-10", "2023-01-15");
        librarySystem.createCheckOut(user, book2, "2023-03-05", "2023-03-10");
        librarySystem.createCheckOut(user, book1, "2023-05-20", "2023-05-25");
        
        // Execute the method to count unique book borrowings for user U001
        int result = librarySystem.uniqueBooksBorrowedByUser(user, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create a GUEST user with ID: U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books: Book3 and Book4
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        // Record CheckOuts
        librarySystem.createCheckOut(user, book3, "2023-06-10", "2023-06-15");
        librarySystem.createCheckOut(user, book4, "2023-06-20", "2023-06-25");
        
        // Execute the method to count unique book borrowings for user U002
        int result = librarySystem.uniqueBooksBorrowedByUser(user, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create a MEMBER user with ID: U003
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // Execute the method to count unique book borrowings for user U003
        int result = librarySystem.uniqueBooksBorrowedByUser(user, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create a MEMBER user with ID: U004
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book: Book5
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        // Record CheckOuts
        librarySystem.createCheckOut(user, book5, "2023-08-01", "2023-08-10");
        librarySystem.createCheckOut(user, book5, "2023-08-15", "2023-08-25");
        
        // Execute the method to count unique book borrowings for user U004
        int result = librarySystem.uniqueBooksBorrowedByUser(user, "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create a MEMBER user with ID: U005
        User user1 = new User();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006
        User user2 = new User();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books: Book6 and Book7
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Record CheckOuts
        librarySystem.createCheckOut(user1, book6, "2023-09-01", "2023-09-10");
        librarySystem.createCheckOut(user1, book7, "2023-09-15", "2023-09-20");
        librarySystem.createCheckOut(user2, book6, "2023-09-05", "2023-09-15");
        
        // Execute the method to count unique book borrowings for user U005
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}