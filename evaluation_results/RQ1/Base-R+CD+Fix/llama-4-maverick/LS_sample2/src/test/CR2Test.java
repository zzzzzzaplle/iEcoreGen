import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Create a MEMBER user with ID: U001
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // Create books
        Book book1 = new Book("Book1", "BC001", "978-3-16-148410-0", 300);
        Book book2 = new Book("Book2", "BC002", "978-1-4028-9467-7", 250);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 1, 10), LocalDate.of(2023, 1, 15), book1);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 3, 5), LocalDate.of(2023, 3, 10), book2);
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 5, 20), LocalDate.of(2023, 5, 25), book1);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U001
        LocalDate startPeriod = LocalDate.of(2023, 1, 1);
        LocalDate endPeriod = LocalDate.of(2023, 12, 31);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startPeriod, endPeriod);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Create a GUEST user with ID: U002
        User user = new User("Bob", "bob@example.com", "456 Oak St.", "U002", UserType.GUEST);
        
        // Create books
        Book book3 = new Book("Book3", "BC003", "978-0-1234-5678-9", 400);
        Book book4 = new Book("Book4", "BC004", "978-1-2345-6789-7", 350);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 6, 10), LocalDate.of(2023, 6, 15), book3);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 6, 20), LocalDate.of(2023, 6, 25), book4);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U002
        LocalDate startPeriod = LocalDate.of(2023, 6, 1);
        LocalDate endPeriod = LocalDate.of(2023, 6, 30);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startPeriod, endPeriod);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Create a MEMBER user with ID: U003
        User user = new User("Charlie", "charlie@example.com", "789 Pine St.", "U003", UserType.MEMBER);
        
        // Add user to library system (no checkouts recorded)
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U003
        LocalDate startPeriod = LocalDate.of(2023, 7, 1);
        LocalDate endPeriod = LocalDate.of(2023, 7, 31);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startPeriod, endPeriod);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Create a MEMBER user with ID: U004
        User user = new User("Diana", "diana@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // Create a book
        Book book5 = new Book("Book5", "BC005", "978-3-16-148410-1", 280);
        
        // Record CheckOuts (same book borrowed multiple times)
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 10), book5);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 8, 15), LocalDate.of(2023, 8, 25), book5);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U004
        LocalDate startPeriod = LocalDate.of(2023, 8, 1);
        LocalDate endPeriod = LocalDate.of(2023, 8, 10);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startPeriod, endPeriod);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Create a MEMBER user with ID: U005
        User user1 = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.MEMBER);
        
        // Create a GUEST user with ID: U006
        User user2 = new User("Frank", "frank@example.com", "303 Cedar St.", "U006", UserType.GUEST);
        
        // Create books
        Book book6 = new Book("Book6", "BC006", "978-0-321-56789-1", 320);
        Book book7 = new Book("Book7", "BC007", "978-0-12-345678-9", 290);
        
        // Record CheckOuts for user U005
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10), book6);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 9, 15), LocalDate.of(2023, 9, 20), book7);
        
        // Record CheckOuts for user U006
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 15), book6);
        
        // Add checkouts to respective users
        user1.addCheckOut(checkout1);
        user1.addCheckOut(checkout2);
        user2.addCheckOut(checkout3);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U005
        LocalDate startPeriod = LocalDate.of(2023, 9, 1);
        LocalDate endPeriod = LocalDate.of(2023, 9, 30);
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startPeriod, endPeriod);
        
        // Verify expected output
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}