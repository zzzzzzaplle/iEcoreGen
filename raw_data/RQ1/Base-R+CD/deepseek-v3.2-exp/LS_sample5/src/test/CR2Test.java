import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    private User memberUser;
    private User guestUser;
    private User userCharlie;
    private User userDiana;
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
        
        // Create books
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
        
        // Create users
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
        
        userCharlie = new User();
        userCharlie.setID("U003");
        userCharlie.setName("Charlie");
        userCharlie.setEmail("charlie@example.com");
        userCharlie.setAddress("789 Pine St.");
        userCharlie.setType(UserType.MEMBER);
        
        userDiana = new User();
        userDiana.setID("U004");
        userDiana.setName("Diana");
        userDiana.setEmail("diana@example.com");
        userDiana.setAddress("101 Maple St.");
        userDiana.setType(UserType.MEMBER);
        
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
        librarySystem.addUser(userCharlie);
        librarySystem.addUser(userDiana);
        librarySystem.addUser(userEve);
        librarySystem.addUser(userFrank);
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Set up checkout records for member user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-01-10");
        checkout1.setEndDate("2023-01-15");
        memberUser.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-03-05");
        checkout2.setEndDate("2023-03-10");
        memberUser.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate("2023-05-20");
        checkout3.setEndDate("2023-05-25");
        memberUser.addCheckOut(checkout3);
        
        // Execute method to count unique book borrowings
        int result = librarySystem.uniqueBooksBorrowedByUser(memberUser, "2023-01-01", "2023-12-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Set up checkout records for guest user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate("2023-06-10");
        checkout1.setEndDate("2023-06-15");
        guestUser.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate("2023-06-20");
        checkout2.setEndDate("2023-06-25");
        guestUser.addCheckOut(checkout2);
        
        // Execute method to count unique book borrowings
        int result = librarySystem.uniqueBooksBorrowedByUser(guestUser, "2023-06-01", "2023-06-30");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Execute method to count unique book borrowings for user with no checkouts
        int result = librarySystem.uniqueBooksBorrowedByUser(userCharlie, "2023-07-01", "2023-07-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Set up checkout records for user with overlapping periods for same book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-10");
        userDiana.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-08-25");
        userDiana.addCheckOut(checkout2);
        
        // Execute method to count unique book borrowings for specific period
        int result = librarySystem.uniqueBooksBorrowedByUser(userDiana, "2023-08-01", "2023-08-10");
        
        // Verify expected output - should count unique books only once
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Set up checkout records for user Eve
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate("2023-09-01");
        checkout1.setEndDate("2023-09-10");
        userEve.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate("2023-09-15");
        checkout2.setEndDate("2023-09-20");
        userEve.addCheckOut(checkout2);
        
        // Set up checkout record for user Frank (this should not affect Eve's count)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate("2023-09-05");
        checkout3.setEndDate("2023-09-15");
        userFrank.addCheckOut(checkout3);
        
        // Execute method to count unique book borrowings for user Eve only
        int result = librarySystem.uniqueBooksBorrowedByUser(userEve, "2023-09-01", "2023-09-30");
        
        // Verify expected output - Eve should have 2 unique books
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}