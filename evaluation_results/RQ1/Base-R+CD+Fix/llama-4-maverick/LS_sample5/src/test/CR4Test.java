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
        
        // Create books
        book1 = new Book("Book1", "B001", "ISBN001", 200);
        book2 = new Book("Book2", "B002", "ISBN002", 300);
        book3 = new Book("Book3", "B003", "ISBN003", 250);
        book4 = new Book("Book4", "B004", "ISBN004", 400);
        
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        librarySystem.setBooks(books);
        
        // Create users
        List<User> users = new ArrayList<>();
        
        memberUser1 = new User("Alice", "alice@example.com", "123 Main St", "M001", UserType.MEMBER);
        memberUser2 = new User("Bob", "bob@example.com", "456 Elm St", "M002", UserType.MEMBER);
        guestUser1 = new User("Charlie", "charlie@example.com", "789 Pine St", "G001", UserType.GUEST);
        memberUser3 = new User("Eve", "eve@example.com", "654 Maple St", "M003", UserType.MEMBER);
        memberUser4 = new User("George", "george@example.com", "135 Cedar St", "M004", UserType.MEMBER);
        guestUser2 = new User("Hannah", "hannah@example.com", "246 Spruce St", "G002", UserType.GUEST);
        
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
        // Setup: Check Out 3 different books (B001, B002, B003) with user M001
        CheckOut checkout1 = new CheckOut(
            LocalDate.of(2023, 1, 1), 
            LocalDate.of(2023, 1, 15), 
            book1
        );
        CheckOut checkout2 = new CheckOut(
            LocalDate.of(2023, 2, 1), 
            LocalDate.of(2023, 2, 10), 
            book2
        );
        CheckOut checkout3 = new CheckOut(
            LocalDate.of(2023, 3, 15), 
            LocalDate.of(2023, 3, 30), 
            book3
        );
        
        memberUser1.addCheckOut(checkout1);
        memberUser1.addCheckOut(checkout2);
        memberUser1.addCheckOut(checkout3);
        
        // Setup: Check Out 2 same book (B001) with user M002
        CheckOut checkout4 = new CheckOut(
            LocalDate.of(2023, 4, 1), 
            LocalDate.of(2023, 4, 12), 
            book1
        );
        CheckOut checkout5 = new CheckOut(
            LocalDate.of(2023, 4, 15), 
            LocalDate.of(2023, 4, 20), 
            book1
        );
        
        memberUser2.addCheckOut(checkout4);
        memberUser2.addCheckOut(checkout5);
        
        // Calculate total unique books checked out
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        
        // Verify expected results
        assertEquals("Member M001 should have 3 unique book checkouts", 3, resultM001);
        assertEquals("Member M002 should have 1 unique book checkout", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Setup: Check Out 1 book (B001) with user G001
        CheckOut checkout1 = new CheckOut(
            LocalDate.of(2023, 5, 1), 
            LocalDate.of(2023, 5, 10), 
            book1
        );
        
        guestUser1.addCheckOut(checkout1);
        
        // Calculate total unique books checked out
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        
        // Verify expected result
        assertEquals("Guest G001 should have 1 unique book checkout", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Setup: Member user M003 with no checkouts (already set up in @Before)
        
        // Calculate total unique books checked out
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        
        // Verify expected result
        assertEquals("Member M003 with no checkouts should return 0", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserWithMultipleBooks() {
        // Setup: Check out 4 different books (B001, B002, B003, B004) with user M004
        CheckOut checkout1 = new CheckOut(
            LocalDate.of(2023, 6, 1), 
            LocalDate.of(2023, 7, 1), 
            book1
        );
        CheckOut checkout2 = new CheckOut(
            LocalDate.of(2023, 6, 1), 
            LocalDate.of(2023, 7, 1), 
            book2
        );
        CheckOut checkout3 = new CheckOut(
            LocalDate.of(2023, 6, 1), 
            LocalDate.of(2023, 7, 1), 
            book3
        );
        CheckOut checkout4 = new CheckOut(
            LocalDate.of(2023, 6, 1), 
            LocalDate.of(2023, 7, 1), 
            book4
        );
        
        memberUser4.addCheckOut(checkout1);
        memberUser4.addCheckOut(checkout2);
        memberUser4.addCheckOut(checkout3);
        memberUser4.addCheckOut(checkout4);
        
        // Calculate total unique books checked out
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        
        // Verify expected result
        assertEquals("Member M004 should have 4 unique book checkouts", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() {
        // Setup: Check out 2 same book (B001) with user G002
        CheckOut checkout1 = new CheckOut(
            LocalDate.of(2023, 7, 15), 
            LocalDate.of(2023, 8, 1), 
            book1
        );
        CheckOut checkout2 = new CheckOut(
            LocalDate.of(2023, 8, 15), 
            LocalDate.of(2023, 9, 1), 
            book1
        );
        
        // Setup: Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut(
            LocalDate.of(2024, 6, 1), 
            LocalDate.of(2024, 7, 1), 
            book4
        );
        CheckOut checkout4 = new CheckOut(
            LocalDate.of(2024, 7, 2), 
            LocalDate.of(2024, 7, 11), 
            book4
        );
        CheckOut checkout5 = new CheckOut(
            LocalDate.of(2024, 8, 1), 
            LocalDate.of(2024, 9, 1), 
            book4
        );
        
        guestUser2.addCheckOut(checkout1);
        guestUser2.addCheckOut(checkout2);
        guestUser2.addCheckOut(checkout3);
        guestUser2.addCheckOut(checkout4);
        guestUser2.addCheckOut(checkout5);
        
        // Calculate total unique books checked out
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        
        // Verify expected result
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, resultG002);
    }
}