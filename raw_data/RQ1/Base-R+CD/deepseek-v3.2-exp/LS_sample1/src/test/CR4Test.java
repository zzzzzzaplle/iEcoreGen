import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(200);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(300);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(400);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(500);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Create users
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);
        
        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);
        
        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);
        
        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);
        
        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);
        
        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);
        
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
        // Set up checkouts for member user M001 (3 different books)
        librarySystem.createCheckOut(memberUser1, book1, "2023-01-01", "2023-01-15");
        librarySystem.createCheckOut(memberUser1, book2, "2023-02-01", "2023-02-10");
        librarySystem.createCheckOut(memberUser1, book3, "2023-03-15", "2023-03-30");
        
        // Set up checkouts for member user M002 (2 same books)
        librarySystem.createCheckOut(memberUser2, book1, "2023-04-01", "2023-04-12");
        librarySystem.createCheckOut(memberUser2, book1, "2023-04-15", "2023-04-20");
        
        // Calculate total unique checkouts for M001
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        assertEquals("Member M001 should have 3 unique book checkouts", 3, resultM001);
        
        // Calculate total unique checkouts for M002
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        assertEquals("Member M002 should have 1 unique book checkout", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Set up checkout for guest user G001 (1 book)
        librarySystem.createCheckOut(guestUser1, book1, "2023-05-01", "2023-05-10");
        
        // Calculate total unique checkouts for G001
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        assertEquals("Guest G001 should have 1 unique book checkout", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Member user M003 has no checkouts set up
        
        // Calculate total unique checkouts for M003
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        assertEquals("Member M003 with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Set up checkouts for member user M004 (4 different books)
        librarySystem.createCheckOut(memberUser4, book1, "2023-06-01", "2023-07-01");
        librarySystem.createCheckOut(memberUser4, book2, "2023-06-01", "2023-07-01");
        librarySystem.createCheckOut(memberUser4, book3, "2023-06-01", "2023-07-01");
        librarySystem.createCheckOut(memberUser4, book4, "2023-06-01", "2023-07-01");
        
        // Calculate total unique checkouts for M004
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        assertEquals("Member M004 should have 4 unique book checkouts", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Set up checkouts for guest user G002 (2 same book B001 and 3 same book B004)
        librarySystem.createCheckOut(guestUser2, book1, "2023-07-15", "2023-08-01");
        librarySystem.createCheckOut(guestUser2, book1, "2023-08-15", "2023-09-01");
        
        librarySystem.createCheckOut(guestUser2, book4, "2024-06-01", "2024-07-01");
        librarySystem.createCheckOut(guestUser2, book4, "2024-07-02", "2024-07-11");
        librarySystem.createCheckOut(guestUser2, book4, "2024-08-01", "2024-09-01");
        
        // Calculate total unique checkouts for G002
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, result);
    }
}