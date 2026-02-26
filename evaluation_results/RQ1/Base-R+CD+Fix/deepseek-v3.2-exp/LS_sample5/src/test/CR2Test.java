import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    private User memberUser;
    private User guestUser;
    private User userWithNoCheckouts;
    private User userWithOverlappingCheckouts;
    private User userEve;
    private User userFrank;
    
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;
    private Book book6;
    private Book book7;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books for all test cases
        book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        librarySystem.addBook(book5);
        librarySystem.addBook(book6);
        librarySystem.addBook(book7);
        
        // Create users for test cases
        memberUser = new User();
        memberUser.setID("U001");
        memberUser.setName("Alice");
        memberUser.setEmail("alice@example.com");
        memberUser.setAddress("123 Main St.");
        memberUser.setType(UserType.MEMBER);
        
        guestUser = new User();
        guestUser.setID("U002");
        guestUser.setName("Bob");
        guestUser.setEmail("bob@example.com");
        guestUser.setAddress("456 Oak St.");
        guestUser.setType(UserType.GUEST);
        
        userWithNoCheckouts = new User();
        userWithNoCheckouts.setID("U003");
        userWithNoCheckouts.setName("Charlie");
        userWithNoCheckouts.setEmail("charlie@example.com");
        userWithNoCheckouts.setAddress("789 Pine St.");
        userWithNoCheckouts.setType(UserType.MEMBER);
        
        userWithOverlappingCheckouts = new User();
        userWithOverlappingCheckouts.setID("U004");
        userWithOverlappingCheckouts.setName("Diana");
        userWithOverlappingCheckouts.setEmail("diana@example.com");
        userWithOverlappingCheckouts.setAddress("101 Maple St.");
        userWithOverlappingCheckouts.setType(UserType.MEMBER);
        
        userEve = new User();
        userEve.setID("U005");
        userEve.setName("Eve");
        userEve.setEmail("eve@example.com");
        userEve.setAddress("202 Birch St.");
        userEve.setType(UserType.MEMBER);
        
        userFrank = new User();
        userFrank.setID("U006");
        userFrank.setName("Frank");
        userFrank.setEmail("frank@example.com");
        userFrank.setAddress("303 Cedar St.");
        userFrank.setType(UserType.GUEST);
        
        // Add users to library system
        librarySystem.addUser(memberUser);
        librarySystem.addUser(guestUser);
        librarySystem.addUser(userWithNoCheckouts);
        librarySystem.addUser(userWithOverlappingCheckouts);
        librarySystem.addUser(userEve);
        librarySystem.addUser(userFrank);
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Set up checkout records for member user
        librarySystem.processCheckout(memberUser, book1, "2023-01-10", "2023-01-15");
        librarySystem.processCheckout(memberUser, book2, "2023-03-05", "2023-03-10");
        librarySystem.processCheckout(memberUser, book1, "2023-05-20", "2023-05-25");
        
        // Execute method to count unique book borrowings for user U001
        int result = librarySystem.uniqueBooksBorrowedByUser(memberUser, "2023-01-01", "2023-12-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Set up checkout records for guest user
        librarySystem.processCheckout(guestUser, book3, "2023-06-10", "2023-06-15");
        librarySystem.processCheckout(guestUser, book4, "2023-06-20", "2023-06-25");
        
        // Execute method to count unique book borrowings for user U002
        int result = librarySystem.uniqueBooksBorrowedByUser(guestUser, "2023-06-01", "2023-06-30");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Execute method to count unique book borrowings for user with no checkouts
        int result = librarySystem.uniqueBooksBorrowedByUser(userWithNoCheckouts, "2023-07-01", "2023-07-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Set up overlapping checkout records for user
        librarySystem.processCheckout(userWithOverlappingCheckouts, book5, "2023-08-01", "2023-08-10");
        librarySystem.processCheckout(userWithOverlappingCheckouts, book5, "2023-08-15", "2023-08-25");
        
        // Execute method to count unique book borrowings for user U004
        int result = librarySystem.uniqueBooksBorrowedByUser(userWithOverlappingCheckouts, "2023-08-01", "2023-08-10");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Set up checkout records for multiple users
        librarySystem.processCheckout(userEve, book6, "2023-09-01", "2023-09-10");
        librarySystem.processCheckout(userEve, book7, "2023-09-15", "2023-09-20");
        librarySystem.processCheckout(userFrank, book6, "2023-09-05", "2023-09-15");
        
        // Execute method to count unique book borrowings for user U005
        int result = librarySystem.uniqueBooksBorrowedByUser(userEve, "2023-09-01", "2023-09-30");
        
        // Verify expected output
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}