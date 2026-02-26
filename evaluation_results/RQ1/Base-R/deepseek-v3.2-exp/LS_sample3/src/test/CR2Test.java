import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Test Case 1: Single User Borrowing Calculation for a Member
        // Calculate the number of books borrowed by a MEMBER user over a specific period from 2023-01-01 to 2023-12-31
        
        // SetUp Step 1: Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // SetUp Step 2: Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        // SetUp Step 3: Record CheckOuts
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(user);
        loan1.setStartDate("2023-01-10");
        loan1.setEndDate("2023-01-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(user);
        loan2.setStartDate("2023-03-05");
        loan2.setEndDate("2023-03-10");
        
        Loan loan3 = new Loan();
        loan3.setBook(book1); // Same book as loan1
        loan3.setUser(user);
        loan3.setStartDate("2023-05-20");
        loan3.setEndDate("2023-05-25");
        
        // Add loans to user's loan history
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books during the period", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Test Case 2: Single User Borrowing Calculation for a Guest
        // Calculate the number of books borrowed by a GUEST user over the period from 2023-06-01 to 2023-06-30
        
        // SetUp Step 1: Create a GUEST user
        GuestUser user = new GuestUser();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        
        // SetUp Step 2: Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        // SetUp Step 3: Record CheckOuts
        Loan loan1 = new Loan();
        loan1.setBook(book3);
        loan1.setUser(user);
        loan1.setStartDate("2023-06-10");
        loan1.setEndDate("2023-06-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(book4);
        loan2.setUser(user);
        loan2.setStartDate("2023-06-20");
        loan2.setEndDate("2023-06-25");
        
        // Add loans to user's loan history
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books during the period", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Test Case 3: No Borrowing Activity within Date Range
        // Calculate the number of books borrowed by a MEMBER user over a period with no checkouts
        
        // SetUp Step 1: Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        
        // User has no loans (empty loan history)
        
        // Execute the method to count unique book borrowings
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no borrowing activity should return 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Test Case 4: Overlapping Checkout Period
        // Calculate the number of books borrowed by a MEMBER user with overlapping checkout periods
        
        // SetUp Step 1: Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        
        // SetUp Step 2: Create a book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        // SetUp Step 3: Record CheckOuts
        Loan loan1 = new Loan();
        loan1.setBook(book5);
        loan1.setUser(user);
        loan1.setStartDate("2023-08-01");
        loan1.setEndDate("2023-08-10");
        
        Loan loan2 = new Loan();
        loan2.setBook(book5); // Same book as loan1
        loan2.setUser(user);
        loan2.setStartDate("2023-08-15");
        loan2.setEndDate("2023-08-25");
        
        // Add loans to user's loan history
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Execute the method to count unique book borrowings for the first period only
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("User should have borrowed 1 unique book during the first period", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Test Case 5: Multiple Users with Different Borrowing Activities
        // Calculate the number of books borrowed by multiple users to check for unique counting
        
        // SetUp Step 1: Create a MEMBER user
        MemberUser user1 = new MemberUser();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        
        // SetUp Step 2: Create a GUEST user
        GuestUser user2 = new GuestUser();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        
        // SetUp Step 3: Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // SetUp Step 4: Record CheckOuts
        // Loans for user1 (U005)
        Loan loan1 = new Loan();
        loan1.setBook(book6);
        loan1.setUser(user1);
        loan1.setStartDate("2023-09-01");
        loan1.setEndDate("2023-09-10");
        
        Loan loan2 = new Loan();
        loan2.setBook(book7);
        loan2.setUser(user1);
        loan2.setStartDate("2023-09-15");
        loan2.setEndDate("2023-09-20");
        
        // Loan for user2 (U006)
        Loan loan3 = new Loan();
        loan3.setBook(book6); // Same book as loan1 but different user
        loan3.setUser(user2);
        loan3.setStartDate("2023-09-05");
        loan3.setEndDate("2023-09-15");
        
        // Add loans to respective users' loan history
        user1.addLoan(loan1);
        user1.addLoan(loan2);
        user2.addLoan(loan3);
        
        // Execute the method to count unique book borrowings for user U005 only
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user1, "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("User U005 should have borrowed 2 unique books during the period", 2, result);
    }
}