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
        // Create a MEMBER user with ID: U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setBarcode("B001");
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setBarcode("B002");
        
        // Record CheckOuts
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
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Execute the method to count unique book borrowings for user U001
        int result = librarySystem.uniqueBooksBorrowedByUser(
            user, 
            LocalDate.of(2023, 1, 1), 
            LocalDate.of(2023, 12, 31)
        );
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Create a GUEST user with ID: U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setBarcode("B003");
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setBarcode("B004");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate("2023-06-10");
        checkout1.setEndDate("2023-06-15");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate("2023-06-20");
        checkout2.setEndDate("2023-06-25");
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Execute the method to count unique book borrowings for user U002
        int result = librarySystem.uniqueBooksBorrowedByUser(
            user, 
            LocalDate.of(2023, 6, 1), 
            LocalDate.of(2023, 6, 30)
        );
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Create a MEMBER user with ID: U003
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // No checkouts recorded for this user
        
        // Execute the method to count unique book borrowings for user U003
        int result = librarySystem.uniqueBooksBorrowedByUser(
            user, 
            LocalDate.of(2023, 7, 1), 
            LocalDate.of(2023, 7, 31)
        );
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Create a MEMBER user with ID: U004
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setBarcode("B005");
        
        // Record CheckOuts for the same book in overlapping periods
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-10");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5); // Same book again
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-08-25");
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Execute the method to count unique book borrowings for user U004
        int result = librarySystem.uniqueBooksBorrowedByUser(
            user, 
            LocalDate.of(2023, 8, 1), 
            LocalDate.of(2023, 8, 10)
        );
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("User should have 1 unique book even with multiple checkouts of same book", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Create a MEMBER user with ID: U005
        User user1 = new User();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006 (not used in this test case calculation)
        User user2 = new User();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setBarcode("B006");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setBarcode("B007");
        
        // Record CheckOuts for user U005
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate("2023-09-01");
        checkout1.setEndDate("2023-09-10");
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate("2023-09-15");
        checkout2.setEndDate("2023-09-20");
        
        user1.addCheckOut(checkout1);
        user1.addCheckOut(checkout2);
        
        // Record CheckOut for user U006 (not used in assertion)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate("2023-09-05");
        checkout3.setEndDate("2023-09-15");
        user2.addCheckOut(checkout3);
        
        // Execute the method to count unique book borrowings for user U005 only
        int result = librarySystem.uniqueBooksBorrowedByUser(
            user1, 
            LocalDate.of(2023, 9, 1), 
            LocalDate.of(2023, 9, 30)
        );
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("User U005 should have borrowed 2 unique books", 2, result);
    }
}