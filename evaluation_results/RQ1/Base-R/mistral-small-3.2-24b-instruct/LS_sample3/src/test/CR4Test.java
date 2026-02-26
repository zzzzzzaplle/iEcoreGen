import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private User memberUser1;
    private User memberUser2;
    private User guestUser1;
    private User memberUser3;
    private User memberUser4;
    private User guestUser2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books
        book1 = new Book("Book 1", "B001", "ISBN001", 100);
        book2 = new Book("Book 2", "B002", "ISBN002", 200);
        book3 = new Book("Book 3", "B003", "ISBN003", 300);
        book4 = new Book("Book 4", "B004", "ISBN004", 400);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Create users
        memberUser1 = new User("Alice", "alice@example.com", "123 Main St", "M001");
        memberUser2 = new User("Bob", "bob@example.com", "456 Elm St", "M002");
        guestUser1 = new User("Charlie", "charlie@example.com", "789 Pine St", "G001");
        memberUser3 = new User("Eve", "eve@example.com", "654 Maple St", "M003");
        memberUser4 = new User("George", "george@example.com", "135 Cedar St", "M004");
        guestUser2 = new User("Hannah", "hannah@example.com", "246 Spruce St", "G002");
        
        // Add users to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        librarySystem.addUser(guestUser1);
        librarySystem.addUser(memberUser3);
        librarySystem.addUser(memberUser4);
        librarySystem.addUser(guestUser2);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Test Case 1: Calculate Total Checkouts for Member User
        
        // SetUp: Check Out 3 different books (B001, B002, B003) with user M001
        librarySystem.addBookLoan(memberUser1, book1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15));
        librarySystem.addBookLoan(memberUser1, book2, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 10));
        librarySystem.addBookLoan(memberUser1, book3, LocalDate.of(2023, 3, 15), LocalDate.of(2023, 3, 30));
        
        // SetUp: Check Out 2 same book (B001) with user M002
        librarySystem.addBookLoan(memberUser2, book1, LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 12));
        librarySystem.addBookLoan(memberUser2, book1, LocalDate.of(2023, 4, 15), LocalDate.of(2023, 4, 20));
        
        // Calculate total unique books checked out
        int totalCheckoutsM001 = memberUser1.calculateTotalUniqueBooksCheckedOut();
        int totalCheckoutsM002 = memberUser2.calculateTotalUniqueBooksCheckedOut();
        
        // Expected Output: Total Member Checkouts for M001 = 3; Total Member Checkouts for M002 = 1;
        assertEquals("M001 should have 3 unique books checked out", 3, totalCheckoutsM001);
        assertEquals("M002 should have 1 unique book checked out", 1, totalCheckoutsM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Test Case 2: Calculate Total Checkouts for Guest User
        
        // SetUp: Check Out 1 book (B001) with user G001
        librarySystem.addBookLoan(guestUser1, book1, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 10));
        
        // Calculate total unique books checked out
        int totalCheckoutsG001 = guestUser1.calculateTotalUniqueBooksCheckedOut();
        
        // Expected Output: Total Guest Checkouts for G001 = 1;
        assertEquals("G001 should have 1 unique book checked out", 1, totalCheckoutsG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Test Case 3: Mixed User Types with No Checkouts
        
        // SetUp: 0 Check Out with user M003 (already created in setup)
        
        // Calculate total unique books checked out
        int totalCheckoutsM003 = memberUser3.calculateTotalUniqueBooksCheckedOut();
        
        // Expected Output: Total Checkouts for M003 = 0
        assertEquals("M003 should have 0 unique books checked out", 0, totalCheckoutsM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Test Case 4: Calculate Total Checkouts for Multiple Users with Mixed Types (Member)
        
        // SetUp: Check out 4 different books (B001, B002, B003, B004) with user M004
        librarySystem.addBookLoan(memberUser4, book1, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.addBookLoan(memberUser4, book2, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.addBookLoan(memberUser4, book3, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.addBookLoan(memberUser4, book4, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        
        // Calculate total unique books checked out
        int totalCheckoutsM004 = memberUser4.calculateTotalUniqueBooksCheckedOut();
        
        // Expected Output: Total Checkouts for M004 = 4;
        assertEquals("M004 should have 4 unique books checked out", 4, totalCheckoutsM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Test Case 5: Calculate Total Checkouts for Multiple Users with Mixed Types (Guest)
        
        // SetUp: Check out 2 same book (B001) with user G002
        librarySystem.addBookLoan(guestUser2, book1, LocalDate.of(2023, 7, 15), LocalDate.of(2023, 8, 1));
        librarySystem.addBookLoan(guestUser2, book1, LocalDate.of(2023, 8, 15), LocalDate.of(2023, 9, 1));
        
        // SetUp: Check out B004 3 times with user G002
        librarySystem.addBookLoan(guestUser2, book4, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 7, 1));
        librarySystem.addBookLoan(guestUser2, book4, LocalDate.of(2024, 7, 2), LocalDate.of(2024, 7, 11));
        librarySystem.addBookLoan(guestUser2, book4, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 9, 1));
        
        // Calculate total unique books checked out
        int totalCheckoutsG002 = guestUser2.calculateTotalUniqueBooksCheckedOut();
        
        // Expected Output: Total Checkouts for G002 = 2;
        assertEquals("G002 should have 2 unique books checked out", 2, totalCheckoutsG002);
    }
}