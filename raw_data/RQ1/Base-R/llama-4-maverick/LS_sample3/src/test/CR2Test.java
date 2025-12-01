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
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create a MEMBER user with ID: U001
        MemberUser user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        
        // SetUp: Create books
        Book book1 = new Book("Book1 Title", "BARCODE001", "978-3-16-148410-0", 200);
        Book book2 = new Book("Book2 Title", "BARCODE002", "978-1-4028-9467-7", 300);
        
        // SetUp: Record CheckOuts
        Loan loan1 = new Loan(book1, "2023-01-10", "2023-01-15");
        Loan loan2 = new Loan(book2, "2023-03-05", "2023-03-10");
        Loan loan3 = new Loan(book1, "2023-05-20", "2023-05-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        int result = user.countUniqueBooksBorrowed("2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create a GUEST user with ID: U002
        GuestUser user = new GuestUser("Bob", "bob@example.com", "456 Oak St.", "U002");
        
        // SetUp: Create books
        Book book3 = new Book("Book3 Title", "BARCODE003", "978-0-1234-5678-9", 250);
        Book book4 = new Book("Book4 Title", "BARCODE004", "978-1-2345-6789-7", 350);
        
        // SetUp: Record CheckOuts
        Loan loan1 = new Loan(book3, "2023-06-10", "2023-06-15");
        Loan loan2 = new Loan(book4, "2023-06-20", "2023-06-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        int result = user.countUniqueBooksBorrowed("2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create a MEMBER user with ID: U003 (no loans added)
        MemberUser user = new MemberUser("Charlie", "charlie@example.com", "789 Pine St.", "U003");
        
        // Execute the method to count unique book borrowings for user U003 for the period
        int result = user.countUniqueBooksBorrowed("2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no loans should have 0 unique books borrowed", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create a MEMBER user with ID: U004
        MemberUser user = new MemberUser("Diana", "diana@example.com", "101 Maple St.", "U004");
        
        // SetUp: Create a book
        Book book5 = new Book("Book5 Title", "BARCODE005", "978-3-16-148410-1", 400);
        
        // SetUp: Record CheckOuts (same book borrowed twice)
        Loan loan1 = new Loan(book5, "2023-08-01", "2023-08-10");
        Loan loan2 = new Loan(book5, "2023-08-15", "2023-08-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = user.countUniqueBooksBorrowed("2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("User should have 1 unique book borrowed during the first checkout period", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create users
        MemberUser user1 = new MemberUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        GuestUser user2 = new GuestUser("Frank", "frank@example.com", "303 Cedar St.", "U006");
        
        // SetUp: Create books
        Book book6 = new Book("Book6 Title", "BARCODE006", "978-0-321-56789-1", 275);
        Book book7 = new Book("Book7 Title", "BARCODE007", "978-0-12-345678-9", 325);
        
        // SetUp: Record CheckOuts
        Loan loan1 = new Loan(book6, "2023-09-01", "2023-09-10");  // User1 borrows Book6
        Loan loan2 = new Loan(book7, "2023-09-15", "2023-09-20");  // User1 borrows Book7
        Loan loan3 = new Loan(book6, "2023-09-05", "2023-09-15");  // User2 borrows Book6
        
        user1.addLoan(loan1);
        user1.addLoan(loan2);
        user2.addLoan(loan3);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = user1.countUniqueBooksBorrowed("2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("User U005 should have borrowed 2 unique books", 2, result);
    }
}