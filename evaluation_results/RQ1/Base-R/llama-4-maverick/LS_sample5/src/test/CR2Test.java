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
        // Create a MEMBER user with ID: U001
        MemberUser user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        
        // Create books: Book1 and Book2
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 200);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 300);
        
        // Record CheckOuts
        Loan loan1 = new Loan(book1, "2023-01-10", "2023-01-15");
        Loan loan2 = new Loan(book2, "2023-03-05", "2023-03-10");
        Loan loan3 = new Loan(book1, "2023-05-20", "2023-05-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U001
        int result = librarySystem.countUniqueBooksBorrowedByUser("U001", "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Create a GUEST user with ID: U002
        GuestUser user = new GuestUser("Bob", "bob@example.com", "456 Oak St.", "U002");
        
        // Create books: Book3 and Book4
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 250);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        
        // Record CheckOuts
        Loan loan1 = new Loan(book3, "2023-06-10", "2023-06-15");
        Loan loan2 = new Loan(book4, "2023-06-20", "2023-06-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U002
        int result = librarySystem.countUniqueBooksBorrowedByUser("U002", "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Create a MEMBER user with ID: U003
        MemberUser user = new MemberUser("Charlie", "charlie@example.com", "789 Pine St.", "U003");
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U003
        int result = librarySystem.countUniqueBooksBorrowedByUser("U003", "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Create a MEMBER user with ID: U004
        MemberUser user = new MemberUser("Diana", "diana@example.com", "101 Maple St.", "U004");
        
        // Create a book: Book5
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 400);
        
        // Record CheckOuts
        Loan loan1 = new Loan(book5, "2023-08-01", "2023-08-10");
        Loan loan2 = new Loan(book5, "2023-08-15", "2023-08-25");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U004
        int result = librarySystem.countUniqueBooksBorrowedByUser("U004", "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Create a MEMBER user with ID: U005
        MemberUser user1 = new MemberUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        
        // Create a GUEST user with ID: U006
        GuestUser user2 = new GuestUser("Frank", "frank@example.com", "303 Cedar St.", "U006");
        
        // Create books: Book6 and Book7
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 280);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 320);
        
        // Record CheckOuts
        Loan loan1 = new Loan(book6, "2023-09-01", "2023-09-10");  // U005 borrows Book6
        Loan loan2 = new Loan(book7, "2023-09-15", "2023-09-20");  // U005 borrows Book7
        Loan loan3 = new Loan(book6, "2023-09-05", "2023-09-15");  // U006 borrows Book6
        
        user1.addLoan(loan1);
        user1.addLoan(loan2);
        user2.addLoan(loan3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        
        // Execute the method to count unique book borrowings for user U005
        int result = librarySystem.countUniqueBooksBorrowedByUser("U005", "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}