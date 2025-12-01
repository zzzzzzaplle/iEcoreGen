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
        // Initialize library system before each test
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Test Case 1: "Single User Borrowing Calculation for a Member"
        
        // Step 1: Create a MEMBER user with specified details
        MemberUser user = new MemberUser();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Step 2: Create books with specified ISBNs
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        
        // Step 3: Record CheckOuts as specified
        Loan checkout1 = new Loan();
        checkout1.setBook(book1);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-01-10");
        checkout1.setEndDate("2023-01-15");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book2);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-03-05");
        checkout2.setEndDate("2023-03-10");
        
        Loan checkout3 = new Loan();
        checkout3.setBook(book1);
        checkout3.setUser(user);
        checkout3.setStartDate("2023-05-20");
        checkout3.setEndDate("2023-05-25");
        
        // Add loans to user's loan history
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        user.addLoan(checkout3);
        
        // Step 4: Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, startDate, endDate);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Test Case 2: "Single User Borrowing Calculation for a Guest"
        
        // Step 1: Create a GUEST user with specified details
        GuestUser user = new GuestUser();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        
        // Step 2: Create books with specified ISBNs
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        
        // Step 3: Record CheckOuts as specified
        Loan checkout1 = new Loan();
        checkout1.setBook(book3);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-06-10");
        checkout1.setEndDate("2023-06-15");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book4);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-06-20");
        checkout2.setEndDate("2023-06-25");
        
        // Add loans to user's loan history
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Step 4: Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, startDate, endDate);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Test Case 3: "No Borrowing Activity within Date Range"
        
        // Step 1: Create a MEMBER user with specified details
        MemberUser user = new MemberUser();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        
        // Step 2: Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, startDate, endDate);
        
        // Verify expected output (user has no loans)
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Test Case 4: "Overlapping Checkout Period"
        
        // Step 1: Create a MEMBER user with specified details
        MemberUser user = new MemberUser();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        
        // Step 2: Create a book with specified ISBN
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        
        // Step 3: Record CheckOuts as specified
        Loan checkout1 = new Loan();
        checkout1.setBook(book5);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-10");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book5);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-08-25");
        
        // Add loans to user's loan history
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Step 4: Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 10);
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, startDate, endDate);
        
        // Verify expected output (same book borrowed twice, but should count as 1 unique book)
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Test Case 5: "Multiple Users with Different Borrowing Activities"
        
        // Step 1: Create a MEMBER user with specified details
        MemberUser user1 = new MemberUser();
        user1.setId("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        
        // Step 2: Create a GUEST user with specified details
        GuestUser user2 = new GuestUser();
        user2.setId("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        
        // Step 3: Create books with specified ISBNs
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        
        // Step 4: Record CheckOuts as specified
        // CheckOuts for user U005
        Loan checkout1 = new Loan();
        checkout1.setBook(book6);
        checkout1.setUser(user1);
        checkout1.setStartDate("2023-09-01");
        checkout1.setEndDate("2023-09-10");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book7);
        checkout2.setUser(user1);
        checkout2.setStartDate("2023-09-15");
        checkout2.setEndDate("2023-09-20");
        
        // CheckOut for user U006
        Loan checkout3 = new Loan();
        checkout3.setBook(book6);
        checkout3.setUser(user2);
        checkout3.setStartDate("2023-09-05");
        checkout3.setEndDate("2023-09-15");
        
        // Add loans to respective users' loan history
        user1.addLoan(checkout1);
        user1.addLoan(checkout2);
        user2.addLoan(checkout3);
        
        // Step 5: Execute the method to count unique book borrowings for user U005
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user1, startDate, endDate);
        
        // Verify expected output (U005 borrowed 2 unique books)
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}