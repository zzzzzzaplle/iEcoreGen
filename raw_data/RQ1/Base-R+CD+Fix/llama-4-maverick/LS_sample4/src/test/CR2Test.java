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
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // SetUp: Create books
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 200);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 300);
        
        // SetUp: Record CheckOuts
        CheckOut checkout1 = new CheckOut("2023-01-10", "2023-01-15", book1);
        CheckOut checkout2 = new CheckOut("2023-03-05", "2023-03-10", book2);
        CheckOut checkout3 = new CheckOut("2023-05-20", "2023-05-25", book1);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 12, 31);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create a GUEST user with ID: U002
        User user = new User("Bob", "bob@example.com", "456 Oak St.", "U002", UserType.GUEST);
        
        // SetUp: Create books
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 250);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        
        // SetUp: Record CheckOuts
        CheckOut checkout1 = new CheckOut("2023-06-10", "2023-06-15", book3);
        CheckOut checkout2 = new CheckOut("2023-06-20", "2023-06-25", book4);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate start = LocalDate.of(2023, 6, 1);
        LocalDate end = LocalDate.of(2023, 6, 30);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create a MEMBER user with ID: U003
        User user = new User("Charlie", "charlie@example.com", "789 Pine St.", "U003", UserType.MEMBER);
        
        // Execute the method to count unique book borrowings for user U003 for the period
        LocalDate start = LocalDate.of(2023, 7, 1);
        LocalDate end = LocalDate.of(2023, 7, 31);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create a MEMBER user with ID: U004
        User user = new User("Diana", "diana@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // SetUp: Create a book
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 400);
        
        // SetUp: Record CheckOuts
        CheckOut checkout1 = new CheckOut("2023-08-01", "2023-08-10", book5);
        CheckOut checkout2 = new CheckOut("2023-08-15", "2023-08-25", book5);
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        LocalDate start = LocalDate.of(2023, 8, 1);
        LocalDate end = LocalDate.of(2023, 8, 10);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create users
        User userU005 = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.MEMBER);
        User userU006 = new User("Frank", "frank@example.com", "303 Cedar St.", "U006", UserType.GUEST);
        
        // SetUp: Create books
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 280);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 320);
        
        // SetUp: Record CheckOuts
        CheckOut checkout1 = new CheckOut("2023-09-01", "2023-09-10", book6);
        CheckOut checkout2 = new CheckOut("2023-09-15", "2023-09-20", book7);
        CheckOut checkout3 = new CheckOut("2023-09-05", "2023-09-15", book6);
        
        userU005.addCheckOut(checkout1);
        userU005.addCheckOut(checkout2);
        userU006.addCheckOut(checkout3);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        LocalDate start = LocalDate.of(2023, 9, 1);
        LocalDate end = LocalDate.of(2023, 9, 30);
        int result = librarySystem.uniqueBooksBorrowedByUser(userU005, start, end);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}