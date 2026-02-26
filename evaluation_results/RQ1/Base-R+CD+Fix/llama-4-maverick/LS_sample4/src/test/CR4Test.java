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
        // Initialize library system
        librarySystem = new LibrarySystem();
        
        // Create test books
        book1 = new Book("Book 1", "B001", "ISBN001", 200);
        book2 = new Book("Book 2", "B002", "ISBN002", 300);
        book3 = new Book("Book 3", "B003", "ISBN003", 400);
        book4 = new Book("Book 4", "B004", "ISBN004", 500);
        
        // Create test users
        memberUser1 = new User("Alice", "alice@example.com", "123 Main St", "M001", UserType.MEMBER);
        memberUser2 = new User("Bob", "bob@example.com", "456 Elm St", "M002", UserType.MEMBER);
        guestUser1 = new User("Charlie", "charlie@example.com", "789 Pine St", "G001", UserType.GUEST);
        memberUser3 = new User("Eve", "eve@example.com", "654 Maple St", "M003", UserType.MEMBER);
        memberUser4 = new User("George", "george@example.com", "135 Cedar St", "M004", UserType.MEMBER);
        guestUser2 = new User("Hannah", "hannah@example.com", "246 Spruce St", "G002", UserType.GUEST);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(memberUser1);
        users.add(memberUser2);
        users.add(guestUser1);
        users.add(memberUser3);
        users.add(memberUser4);
        users.add(guestUser2);
        librarySystem.setUsers(users);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Set up checkouts for member user M001
        CheckOut checkout1 = new CheckOut("2023-01-01", "2023-01-15", book1);
        CheckOut checkout2 = new CheckOut("2023-02-01", "2023-02-10", book2);
        CheckOut checkout3 = new CheckOut("2023-03-15", "2023-03-30", book3);
        
        memberUser1.addCheckOut(checkout1);
        memberUser1.addCheckOut(checkout2);
        memberUser1.addCheckOut(checkout3);
        
        // Set up checkouts for member user M002 (same book checked out twice)
        CheckOut checkout4 = new CheckOut("2023-04-01", "2023-04-12", book1);
        CheckOut checkout5 = new CheckOut("2023-04-15", "2023-04-20", book1);
        
        memberUser2.addCheckOut(checkout4);
        memberUser2.addCheckOut(checkout5);
        
        // Test total unique books for M001
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        assertEquals("Member M001 should have 3 unique books checked out", 3, result1);
        
        // Test total unique books for M002
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        assertEquals("Member M002 should have 1 unique book checked out", 1, result2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Set up checkout for guest user G001
        CheckOut checkout1 = new CheckOut("2023-05-01", "2023-05-10", book1);
        
        guestUser1.addCheckOut(checkout1);
        
        // Test total unique books for G001
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        assertEquals("Guest G001 should have 1 unique book checked out", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Member user M003 has no checkouts
        
        // Test total unique books for M003
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        assertEquals("Member M003 with no checkouts should have 0 unique books", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserWithMultipleBooks() {
        // Set up checkouts for member user M004 (4 different books)
        CheckOut checkout1 = new CheckOut("2023-06-01", "2023-07-01", book1);
        CheckOut checkout2 = new CheckOut("2023-06-01", "2023-07-01", book2);
        CheckOut checkout3 = new CheckOut("2023-06-01", "2023-07-01", book3);
        CheckOut checkout4 = new CheckOut("2023-06-01", "2023-07-01", book4);
        
        memberUser4.addCheckOut(checkout1);
        memberUser4.addCheckOut(checkout2);
        memberUser4.addCheckOut(checkout3);
        memberUser4.addCheckOut(checkout4);
        
        // Test total unique books for M004
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        assertEquals("Member M004 should have 4 unique books checked out", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() {
        // Set up checkouts for guest user G002 (same book checked out multiple times, different books)
        CheckOut checkout1 = new CheckOut("2023-07-15", "2023-08-01", book1);
        CheckOut checkout2 = new CheckOut("2023-08-15", "2023-09-01", book1); // Same book as checkout1
        
        CheckOut checkout3 = new CheckOut("2024-06-01", "2024-07-01", book4);
        CheckOut checkout4 = new CheckOut("2024-07-02", "2024-07-11", book4); // Same book as checkout3
        CheckOut checkout5 = new CheckOut("2024-08-01", "2024-09-01", book4); // Same book as checkout3
        
        guestUser2.addCheckOut(checkout1);
        guestUser2.addCheckOut(checkout2);
        guestUser2.addCheckOut(checkout3);
        guestUser2.addCheckOut(checkout4);
        guestUser2.addCheckOut(checkout5);
        
        // Test total unique books for G002
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        assertEquals("Guest G002 should have 2 unique books checked out", 2, result);
    }
}