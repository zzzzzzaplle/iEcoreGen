import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_singleUserBorrowingCalculationForMember() throws Exception {
        // Create a MEMBER user with ID: U001
        MemberUser user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 300);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 250);
        
        // Record CheckOuts
        Loan checkout1 = new Loan(book1, "2023-01-10", "2023-01-15");
        Loan checkout2 = new Loan(book2, "2023-03-05", "2023-03-10");
        Loan checkout3 = new Loan(book1, "2023-05-20", "2023-05-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        user.addLoan(checkout3);
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.countUniqueBooksBorrowed(user, "2023-01-01", "2023-12-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_singleUserBorrowingCalculationForGuest() throws Exception {
        // Create a GUEST user with ID: U002
        GuestUser user = new GuestUser("Bob", "bob@example.com", "456 Oak St.", "U002");
        
        // Create books
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 400);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        
        // Record CheckOuts
        Loan checkout1 = new Loan(book3, "2023-06-10", "2023-06-15");
        Loan checkout2 = new Loan(book4, "2023-06-20", "2023-06-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.countUniqueBooksBorrowed(user, "2023-06-01", "2023-06-30");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() throws Exception {
        // Create a MEMBER user with ID: U003
        MemberUser user = new MemberUser("Charlie", "charlie@example.com", "789 Pine St.", "U003");
        
        // Execute the method to count unique book borrowings (no loans added)
        int result = librarySystem.countUniqueBooksBorrowed(user, "2023-07-01", "2023-07-31");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() throws Exception {
        // Create a MEMBER user with ID: U004
        MemberUser user = new MemberUser("Diana", "diana@example.com", "101 Maple St.", "U004");
        
        // Create a book
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 280);
        
        // Record CheckOuts
        Loan checkout1 = new Loan(book5, "2023-08-01", "2023-08-10");
        Loan checkout2 = new Loan(book5, "2023-08-15", "2023-08-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.countUniqueBooksBorrowed(user, "2023-08-01", "2023-08-10");
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() throws Exception {
        // Create a MEMBER user with ID: U005
        MemberUser userU005 = new MemberUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        
        // Create a GUEST user with ID: U006
        GuestUser userU006 = new GuestUser("Frank", "frank@example.com", "303 Cedar St.", "U006");
        
        // Create books
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 320);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 290);
        
        // Record CheckOuts for U005
        Loan checkout1 = new Loan(book6, "2023-09-01", "2023-09-10");
        Loan checkout2 = new Loan(book7, "2023-09-15", "2023-09-20");
        
        // Record CheckOuts for U006
        Loan checkout3 = new Loan(book6, "2023-09-05", "2023-09-15");
        
        // Add loans to respective users
        userU005.addLoan(checkout1);
        userU005.addLoan(checkout2);
        userU006.addLoan(checkout3);
        
        // Execute the method to count unique book borrowings for user U005
        int result = librarySystem.countUniqueBooksBorrowed(userU005, "2023-09-01", "2023-09-30");
        
        // Verify expected output
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}