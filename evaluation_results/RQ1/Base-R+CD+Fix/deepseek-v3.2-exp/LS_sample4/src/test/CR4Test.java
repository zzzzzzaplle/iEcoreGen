import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        book4.setNumberOfPages(250);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member user M001
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);
        
        // Create Member user M002
        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);
        
        // Check out 3 different books for user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-01-01");
        checkout1.setEndDate("2023-01-15");
        memberUser1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-02-01");
        checkout2.setEndDate("2023-02-10");
        memberUser1.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate("2023-03-15");
        checkout3.setEndDate("2023-03-30");
        memberUser1.addCheckOut(checkout3);
        
        // Check out 2 same books for user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate("2023-04-01");
        checkout4.setEndDate("2023-04-12");
        memberUser2.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate("2023-04-15");
        checkout5.setEndDate("2023-04-20");
        memberUser2.addCheckOut(checkout5);
        
        // Add users to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        
        // Calculate total unique checkouts
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        
        // Verify expected results
        assertEquals("Total Member Checkouts for M001 should be 3", 3, resultM001);
        assertEquals("Total Member Checkouts for M002 should be 1", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user G001
        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);
        
        // Check out 1 book for user G001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-05-01");
        checkout1.setEndDate("2023-05-10");
        guestUser1.addCheckOut(checkout1);
        
        // Add user to library system
        librarySystem.addUser(guestUser1);
        
        // Calculate total unique checkouts
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        
        // Verify expected result
        assertEquals("Total Guest Checkouts for G001 should be 1", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(memberUser3);
        
        // Calculate total unique checkouts
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        
        // Verify expected result
        assertEquals("Total Checkouts for M003 should be 0", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserWithMultipleBooks() {
        // Create Member user M004
        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);
        
        // Check out 4 different books for user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-06-01");
        checkout1.setEndDate("2023-07-01");
        memberUser4.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-06-01");
        checkout2.setEndDate("2023-07-01");
        memberUser4.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate("2023-06-01");
        checkout3.setEndDate("2023-07-01");
        memberUser4.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate("2023-06-01");
        checkout4.setEndDate("2023-07-01");
        memberUser4.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.addUser(memberUser4);
        
        // Calculate total unique checkouts
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        
        // Verify expected result
        assertEquals("Total Checkouts for M004 should be 4", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() {
        // Create Guest user G002
        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);
        
        // Check out 2 same books (B001) for user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-07-15");
        checkout1.setEndDate("2023-08-01");
        guestUser2.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-09-01");
        guestUser2.addCheckOut(checkout2);
        
        // Check out B004 3 times for user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate("2024-06-01");
        checkout3.setEndDate("2024-07-01");
        guestUser2.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate("2024-07-02");
        checkout4.setEndDate("2024-07-11");
        guestUser2.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate("2024-08-01");
        checkout5.setEndDate("2024-09-01");
        guestUser2.addCheckOut(checkout5);
        
        // Add user to library system
        librarySystem.addUser(guestUser2);
        
        // Calculate total unique checkouts
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        
        // Verify expected result
        assertEquals("Total Checkouts for G002 should be 2", 2, resultG002);
    }
}