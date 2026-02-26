import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member users
        MemberUser user1 = new MemberUser("Alice", "alice@example.com", "123 Main St", "M001");
        MemberUser user2 = new MemberUser("Bob", "bob@example.com", "456 Elm St", "M002");
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN001", 200);
        Book book2 = new Book("Book 2", "B002", "ISBN002", 300);
        Book book3 = new Book("Book 3", "B003", "ISBN003", 400);
        
        // Create loans for user M001 (3 different books)
        Loan loan1 = new Loan(book1, "2023-01-01", "2023-01-15");
        Loan loan2 = new Loan(book2, "2023-02-01", "2023-02-10");
        Loan loan3 = new Loan(book3, "2023-03-15", "2023-03-30");
        user1.addLoan(loan1);
        user1.addLoan(loan2);
        user1.addLoan(loan3);
        
        // Create loans for user M002 (2 same book B001)
        Loan loan4 = new Loan(book1, "2023-04-01", "2023-04-12");
        Loan loan5 = new Loan(book1, "2023-04-15", "2023-04-20");
        user2.addLoan(loan4);
        user2.addLoan(loan5);
        
        // Add users and books to library system
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Calculate total unique books for each user
        int result1 = librarySystem.calculateTotalUniqueBooksCheckedOut("M001");
        int result2 = librarySystem.calculateTotalUniqueBooksCheckedOut("M002");
        
        // Verify expected outputs
        assertEquals("Member M001 should have 3 unique books checked out", 3, result1);
        assertEquals("Member M002 should have 1 unique book checked out", 1, result2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user
        GuestUser user = new GuestUser("Charlie", "charlie@example.com", "789 Pine St", "G001");
        
        // Create book
        Book book1 = new Book("Book 1", "B001", "ISBN001", 200);
        
        // Create loan for user G001 (1 book)
        Loan loan1 = new Loan(book1, "2023-05-01", "2023-05-10");
        user.addLoan(loan1);
        
        // Add user and book to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        
        // Calculate total unique books
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut("G001");
        
        // Verify expected output
        assertEquals("Guest G001 should have 1 unique book checked out", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        MemberUser user = new MemberUser("Eve", "eve@example.com", "654 Maple St", "M003");
        
        // Add user to library system (no loans added)
        librarySystem.addUser(user);
        
        // Calculate total unique books
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut("M003");
        
        // Verify expected output
        assertEquals("Member M003 with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Create Member user
        MemberUser user = new MemberUser("George", "george@example.com", "135 Cedar St", "M004");
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN001", 200);
        Book book2 = new Book("Book 2", "B002", "ISBN002", 300);
        Book book3 = new Book("Book 3", "B003", "ISBN003", 400);
        Book book4 = new Book("Book 4", "B004", "ISBN004", 500);
        
        // Create loans for user M004 (4 different books)
        Loan loan1 = new Loan(book1, "2023-06-01", "2023-07-01");
        user.addLoan(loan1);
        
        // Add user and books to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Calculate total unique books
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut("M004");
        
        // Verify expected output
        assertEquals("Member M004 should have 1 unique book checked out", 1, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() {
        // Create Guest user
        GuestUser user = new GuestUser("Hannah", "hannah@example.com", "246 Spruce St", "G002");
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN001", 200);
        Book book4 = new Book("Book 4", "B004", "ISBN004", 500);
        
        // Create loans for user G002 (2 same book B001)
        Loan loan1 = new Loan(book1, "2023-07-15", "2023-08-01");
        Loan loan2 = new Loan(book1, "2023-08-15", "2023-09-01");
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Add user and books to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book4);
        
        // Calculate total unique books
        int result = librarySystem.calculateTotalUniqueBooksCheckedOut("G002");
        
        // Verify expected output
        assertEquals("Guest G002 should have 1 unique book checked out", 1, result);
    }
}