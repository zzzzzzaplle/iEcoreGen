import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

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
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Test Case 1: Single User Borrowing Calculation for a Member
        // SetUp: Create MEMBER user with ID: U001
        memberUser = new User();
        memberUser.setID("U001");
        memberUser.setName("Alice");
        memberUser.setEmail("alice@example.com");
        memberUser.setAddress("123 Main St.");
        memberUser.setType(UserType.MEMBER);
        
        // Create checkouts for the member user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-01-10");
        checkout1.setEndDate("2023-01-15");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-03-05");
        checkout2.setEndDate("2023-03-10");
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1); // Same book as checkout1
        checkout3.setStartDate("2023-05-20");
        checkout3.setEndDate("2023-05-25");
        
        memberUser.addCheckOut(checkout1);
        memberUser.addCheckOut(checkout2);
        memberUser.addCheckOut(checkout3);
        
        librarySystem.addUser(memberUser);
        
        // Execute the method to count unique book borrowings for user U001
        int result = librarySystem.uniqueBooksBorrowedByUser(memberUser, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Test Case 2: Single User Borrowing Calculation for a Guest
        // SetUp: Create GUEST user with ID: U002
        guestUser = new User();
        guestUser.setID("U002");
        guestUser.setName("Bob");
        guestUser.setEmail("bob@example.com");
        guestUser.setAddress("456 Oak St.");
        guestUser.setType(UserType.GUEST);
        
        // Create checkouts for the guest user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate("2023-06-10");
        checkout1.setEndDate("2023-06-15");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate("2023-06-20");
        checkout2.setEndDate("2023-06-25");
        
        guestUser.addCheckOut(checkout1);
        guestUser.addCheckOut(checkout2);
        
        librarySystem.addUser(guestUser);
        
        // Execute the method to count unique book borrowings for user U002
        int result = librarySystem.uniqueBooksBorrowedByUser(guestUser, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Test Case 3: No Borrowing Activity within Date Range
        // SetUp: Create MEMBER user with ID: U003 (no checkouts)
        userWithNoCheckouts = new User();
        userWithNoCheckouts.setID("U003");
        userWithNoCheckouts.setName("Charlie");
        userWithNoCheckouts.setEmail("charlie@example.com");
        userWithNoCheckouts.setAddress("789 Pine St.");
        userWithNoCheckouts.setType(UserType.MEMBER);
        
        librarySystem.addUser(userWithNoCheckouts);
        
        // Execute the method to count unique book borrowings for user U003
        int result = librarySystem.uniqueBooksBorrowedByUser(userWithNoCheckouts, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no checkouts should have 0 unique books borrowed", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Test Case 4: Overlapping Checkout Period
        // SetUp: Create MEMBER user with ID: U004
        userWithOverlappingCheckouts = new User();
        userWithOverlappingCheckouts.setID("U004");
        userWithOverlappingCheckouts.setName("Diana");
        userWithOverlappingCheckouts.setEmail("diana@example.com");
        userWithOverlappingCheckouts.setAddress("101 Maple St.");
        userWithOverlappingCheckouts.setType(UserType.MEMBER);
        
        // Create checkouts for the same book with overlapping periods
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-10");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5); // Same book as checkout1
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-08-25");
        
        userWithOverlappingCheckouts.addCheckOut(checkout1);
        userWithOverlappingCheckouts.addCheckOut(checkout2);
        
        librarySystem.addUser(userWithOverlappingCheckouts);
        
        // Execute the method to count unique book borrowings for user U004
        int result = librarySystem.uniqueBooksBorrowedByUser(userWithOverlappingCheckouts, "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("User with overlapping checkouts of same book should have 1 unique book", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Test Case 5: Multiple Users with Different Borrowing Activities
        // SetUp: Create MEMBER user with ID: U005
        userEve = new User();
        userEve.setID("U005");
        userEve.setName("Eve");
        userEve.setEmail("eve@example.com");
        userEve.setAddress("202 Birch St.");
        userEve.setType(UserType.MEMBER);
        
        // SetUp: Create GUEST user with ID: U006
        userFrank = new User();
        userFrank.setID("U006");
        userFrank.setName("Frank");
        userFrank.setEmail("frank@example.com");
        userFrank.setAddress("303 Cedar St.");
        userFrank.setType(UserType.GUEST);
        
        // Create checkouts for user Eve (U005)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate("2023-09-01");
        checkout1.setEndDate("2023-09-10");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate("2023-09-15");
        checkout2.setEndDate("2023-09-20");
        
        userEve.addCheckOut(checkout1);
        userEve.addCheckOut(checkout2);
        
        // Create checkout for user Frank (U006)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6); // Same book as Eve's first checkout
        checkout3.setStartDate("2023-09-05");
        checkout3.setEndDate("2023-09-15");
        
        userFrank.addCheckOut(checkout3);
        
        librarySystem.addUser(userEve);
        librarySystem.addUser(userFrank);
        
        // Execute the method to count unique book borrowings for user U005 (Eve)
        int result = librarySystem.uniqueBooksBorrowedByUser(userEve, "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("User Eve should have borrowed 2 unique books", 2, result);
    }

}