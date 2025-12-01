import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 300);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 250);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.parse("2023-01-10", formatter), 
                                         LocalDate.parse("2023-01-15", formatter), book1);
        CheckOut checkout2 = new CheckOut(LocalDate.parse("2023-03-05", formatter), 
                                         LocalDate.parse("2023-03-10", formatter), book2);
        CheckOut checkout3 = new CheckOut(LocalDate.parse("2023-05-20", formatter), 
                                         LocalDate.parse("2023-05-25", formatter), book1);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        User user = new User("Bob", "bob@example.com", "456 Oak St.", "U002", UserType.GUEST);
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 400);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.parse("2023-06-10", formatter), 
                                         LocalDate.parse("2023-06-15", formatter), book3);
        CheckOut checkout2 = new CheckOut(LocalDate.parse("2023-06-20", formatter), 
                                         LocalDate.parse("2023-06-25", formatter), book4);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-06-30", formatter);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        User user = new User("Charlie", "charlie@example.com", "789 Pine St.", "U003", UserType.MEMBER);
        
        // Add user to library system (no checkouts recorded)
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        LocalDate startDate = LocalDate.parse("2023-07-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-31", formatter);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        User user = new User("Diana", "diana@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 500);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.parse("2023-08-01", formatter), 
                                         LocalDate.parse("2023-08-10", formatter), book5);
        CheckOut checkout2 = new CheckOut(LocalDate.parse("2023-08-15", formatter), 
                                         LocalDate.parse("2023-08-25", formatter), book5);
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        LocalDate startDate = LocalDate.parse("2023-08-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-08-10", formatter);
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        User user1 = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.MEMBER);
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        User user2 = new User("Frank", "frank@example.com", "303 Cedar St.", "U006", UserType.GUEST);
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 600);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 450);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut(LocalDate.parse("2023-09-01", formatter), 
                                         LocalDate.parse("2023-09-10", formatter), book6);
        CheckOut checkout2 = new CheckOut(LocalDate.parse("2023-09-15", formatter), 
                                         LocalDate.parse("2023-09-20", formatter), book7);
        CheckOut checkout3 = new CheckOut(LocalDate.parse("2023-09-05", formatter), 
                                         LocalDate.parse("2023-09-15", formatter), book6);
        
        // Add checkouts to respective users
        user1.addCheckOut(checkout1);
        user1.addCheckOut(checkout2);
        user2.addCheckOut(checkout3);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        LocalDate startDate = LocalDate.parse("2023-09-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-09-30", formatter);
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}