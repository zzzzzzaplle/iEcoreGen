import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create test books
        book1 = new Book("Book 1", "B001", "ISBN001", 200);
        book2 = new Book("Book 2", "B002", "ISBN002", 300);
        book3 = new Book("Book 3", "B003", "ISBN003", 400);
        book4 = new Book("Book 4", "B004", "ISBN004", 500);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws Exception {
        // Test Case 1: Calculate Total Checkouts for Member User
        
        // Step 1: Create a Member user with ID: M001
        MemberUser userM001 = new MemberUser("Alice", "alice@example.com", "123 Main St", "M001");
        
        // Step 2: Create a Member user with ID: M002
        MemberUser userM002 = new MemberUser("Bob", "bob@example.com", "456 Elm St", "M002");
        
        // Step 3: Check Out 3 different books (B001, B002, B003) with user M001
        Loan loan1M001 = new Loan(book1, "2023-01-01", "2023-01-15");
        Loan loan2M001 = new Loan(book2, "2023-02-01", "2023-02-10");
        Loan loan3M001 = new Loan(book3, "2023-03-15", "2023-03-30");
        userM001.addLoan(loan1M001);
        userM001.addLoan(loan2M001);
        userM001.addLoan(loan3M001);
        
        // Step 4: Check Out 2 same book (B001) with user M002
        Loan loan1M002 = new Loan(book1, "2023-04-01", "2023-04-12");
        Loan loan2M002 = new Loan(book1, "2023-04-15", "2023-04-20");
        userM002.addLoan(loan1M002);
        userM002.addLoan(loan2M002);
        
        // Calculate total unique books for M001 and M002
        int resultM001 = librarySystem.calculateTotalUniqueBooksCheckedOut(userM001);
        int resultM002 = librarySystem.calculateTotalUniqueBooksCheckedOut(userM002);
        
        // Expected Output: Total Member Checkouts for M001 = 3; Total Member Checkouts for M002 = 1
        assertEquals("M001 should have 3 unique books checked out", 3, resultM001);
        assertEquals("M002 should have 1 unique book checked out", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Test Case 2: Calculate Total Checkouts for Guest User
        
        // Step 1: Create a Guest user with ID: G001
        GuestUser userG001 = new GuestUser("Charlie", "charlie@example.com", "789 Pine St", "G001");
        
        // Step 2: Check Out 1 book (B001) with user G001
        Loan loanG001 = new Loan(book1, "2023-05-01", "2023-05-10");
        userG001.addLoan(loanG001);
        
        // Calculate total unique books for G001
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut(userG001);
        
        // Expected Output: Total Guest Checkouts for G001 = 1
        assertEquals("G001 should have 1 unique book checked out", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Test Case 3: Mixed User Types with No Checkouts
        
        // Step 1: Create a Member user with ID: M003 with no checkouts
        MemberUser userM003 = new MemberUser("Eve", "eve@example.com", "654 Maple St", "M003");
        
        // Step 2: 0 Check Out with user M003 (no loans added)
        
        // Calculate total unique books for M003
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut(userM003);
        
        // Expected Output: Total Checkouts for M003 = 0
        assertEquals("M003 should have 0 unique books checked out", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserMultipleBooks() throws Exception {
        // Test Case 4: Calculate Total Checkouts for Multiple Users with Mixed Types (Member)
        
        // Step 1: Create a Member user with ID: M004
        MemberUser userM004 = new MemberUser("George", "george@example.com", "135 Cedar St", "M004");
        
        // Step 2: Check out 4 different books (B001, B002, B003, B004) with user M004
        Loan loan1M004 = new Loan(book1, "2023-06-01", "2023-07-01");
        Loan loan2M004 = new Loan(book2, "2023-06-01", "2023-07-01");
        Loan loan3M004 = new Loan(book3, "2023-06-01", "2023-07-01");
        Loan loan4M004 = new Loan(book4, "2023-06-01", "2023-07-01");
        userM004.addLoan(loan1M004);
        userM004.addLoan(loan2M004);
        userM004.addLoan(loan3M004);
        userM004.addLoan(loan4M004);
        
        // Calculate total unique books for M004
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut(userM004);
        
        // Expected Output: Total Checkouts for M004 = 4
        assertEquals("M004 should have 4 unique books checked out", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserDuplicateBooks() throws Exception {
        // Test Case 5: Calculate Total Checkouts for Multiple Users with Mixed Types (Guest)
        
        // Step 1: Create a Guest user with ID: G002
        GuestUser userG002 = new GuestUser("Hannah", "hannah@example.com", "246 Spruce St", "G002");
        
        // Step 2: Check out 2 same book (B001) with user G002
        Loan loan1G002 = new Loan(book1, "2023-07-15", "2023-08-01");
        Loan loan2G002 = new Loan(book1, "2023-08-15", "2023-09-01");
        
        // Step 3: Check out B004 3 times with user G002
        Loan loan3G002 = new Loan(book4, "2024-06-01", "2024-07-01");
        Loan loan4G002 = new Loan(book4, "2024-07-02", "2024-07-11");
        Loan loan5G002 = new Loan(book4, "2024-08-01", "2024-09-01");
        
        userG002.addLoan(loan1G002);
        userG002.addLoan(loan2G002);
        userG002.addLoan(loan3G002);
        userG002.addLoan(loan4G002);
        userG002.addLoan(loan5G002);
        
        // Calculate total unique books for G002
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut(userG002);
        
        // Expected Output: Total Checkouts for G002 = 2
        assertEquals("G002 should have 2 unique books checked out", 2, result);
    }
}