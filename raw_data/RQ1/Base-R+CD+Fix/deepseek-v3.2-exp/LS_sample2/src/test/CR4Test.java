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
        book4.setNumberOfPages(500);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Set up member user 1 (M001)
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);
        
        // Set up member user 2 (M002)
        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);
        
        // Add users to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        
        // Create checkouts for member user 1 (3 different books)
        CheckOut checkout1_user1 = new CheckOut();
        checkout1_user1.setStartDate("2023-01-01");
        checkout1_user1.setEndDate("2023-01-15");
        checkout1_user1.setBook(book1);
        memberUser1.addCheckOut(checkout1_user1);
        book1.addCheckOut(checkout1_user1);
        
        CheckOut checkout2_user1 = new CheckOut();
        checkout2_user1.setStartDate("2023-02-01");
        checkout2_user1.setEndDate("2023-02-10");
        checkout2_user1.setBook(book2);
        memberUser1.addCheckOut(checkout2_user1);
        book2.addCheckOut(checkout2_user1);
        
        CheckOut checkout3_user1 = new CheckOut();
        checkout3_user1.setStartDate("2023-03-15");
        checkout3_user1.setEndDate("2023-03-30");
        checkout3_user1.setBook(book3);
        memberUser1.addCheckOut(checkout3_user1);
        book3.addCheckOut(checkout3_user1);
        
        // Create checkouts for member user 2 (2 same book B001)
        CheckOut checkout1_user2 = new CheckOut();
        checkout1_user2.setStartDate("2023-04-01");
        checkout1_user2.setEndDate("2023-04-12");
        checkout1_user2.setBook(book1);
        memberUser2.addCheckOut(checkout1_user2);
        book1.addCheckOut(checkout1_user2);
        
        CheckOut checkout2_user2 = new CheckOut();
        checkout2_user2.setStartDate("2023-04-15");
        checkout2_user2.setEndDate("2023-04-20");
        checkout2_user2.setBook(book1);
        memberUser2.addCheckOut(checkout2_user2);
        book1.addCheckOut(checkout2_user2);
        
        // Calculate total unique checkouts for each user
        int resultUser1 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        int resultUser2 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        
        // Verify expected results
        assertEquals("Total unique books checked out by M001 should be 3", 3, resultUser1);
        assertEquals("Total unique books checked out by M002 should be 1", 1, resultUser2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Set up guest user 1 (G001)
        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);
        
        // Add user to library system
        librarySystem.addUser(guestUser1);
        
        // Create checkout for guest user 1 (1 book B001)
        CheckOut checkout_user1 = new CheckOut();
        checkout_user1.setStartDate("2023-05-01");
        checkout_user1.setEndDate("2023-05-10");
        checkout_user1.setBook(book1);
        guestUser1.addCheckOut(checkout_user1);
        book1.addCheckOut(checkout_user1);
        
        // Calculate total unique checkouts for guest user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        
        // Verify expected result
        assertEquals("Total unique books checked out by G001 should be 1", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Set up member user 3 (M003) with no checkouts
        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(memberUser3);
        
        // Calculate total unique checkouts for user with no checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        
        // Verify expected result
        assertEquals("Total unique books checked out by M003 should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Set up member user 4 (M004)
        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(memberUser4);
        
        // Create checkouts for member user 4 (4 different books)
        CheckOut checkout1_user4 = new CheckOut();
        checkout1_user4.setStartDate("2023-06-01");
        checkout1_user4.setEndDate("2023-07-01");
        checkout1_user4.setBook(book1);
        memberUser4.addCheckOut(checkout1_user4);
        book1.addCheckOut(checkout1_user4);
        
        CheckOut checkout2_user4 = new CheckOut();
        checkout2_user4.setStartDate("2023-06-01");
        checkout2_user4.setEndDate("2023-07-01");
        checkout2_user4.setBook(book2);
        memberUser4.addCheckOut(checkout2_user4);
        book2.addCheckOut(checkout2_user4);
        
        CheckOut checkout3_user4 = new CheckOut();
        checkout3_user4.setStartDate("2023-06-01");
        checkout3_user4.setEndDate("2023-07-01");
        checkout3_user4.setBook(book3);
        memberUser4.addCheckOut(checkout3_user4);
        book3.addCheckOut(checkout3_user4);
        
        CheckOut checkout4_user4 = new CheckOut();
        checkout4_user4.setStartDate("2023-06-01");
        checkout4_user4.setEndDate("2023-07-01");
        checkout4_user4.setBook(book4);
        memberUser4.addCheckOut(checkout4_user4);
        book4.addCheckOut(checkout4_user4);
        
        // Calculate total unique checkouts for member user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        
        // Verify expected result
        assertEquals("Total unique books checked out by M004 should be 4", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Set up guest user 2 (G002)
        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);
        
        // Add user to library system
        librarySystem.addUser(guestUser2);
        
        // Create checkouts for guest user 2 (2 same book B001)
        CheckOut checkout1_user2 = new CheckOut();
        checkout1_user2.setStartDate("2023-07-15");
        checkout1_user2.setEndDate("2023-08-01");
        checkout1_user2.setBook(book1);
        guestUser2.addCheckOut(checkout1_user2);
        book1.addCheckOut(checkout1_user2);
        
        CheckOut checkout2_user2 = new CheckOut();
        checkout2_user2.setStartDate("2023-08-15");
        checkout2_user2.setEndDate("2023-09-01");
        checkout2_user2.setBook(book1);
        guestUser2.addCheckOut(checkout2_user2);
        book1.addCheckOut(checkout2_user2);
        
        // Create checkouts for guest user 2 (3 same book B004)
        CheckOut checkout3_user2 = new CheckOut();
        checkout3_user2.setStartDate("2024-06-01");
        checkout3_user2.setEndDate("2024-07-01");
        checkout3_user2.setBook(book4);
        guestUser2.addCheckOut(checkout3_user2);
        book4.addCheckOut(checkout3_user2);
        
        CheckOut checkout4_user2 = new CheckOut();
        checkout4_user2.setStartDate("2024-07-02");
        checkout4_user2.setEndDate("2024-07-11");
        checkout4_user2.setBook(book4);
        guestUser2.addCheckOut(checkout4_user2);
        book4.addCheckOut(checkout4_user2);
        
        CheckOut checkout5_user2 = new CheckOut();
        checkout5_user2.setStartDate("2024-08-01");
        checkout5_user2.setEndDate("2024-09-01");
        checkout5_user2.setBook(book4);
        guestUser2.addCheckOut(checkout5_user2);
        book4.addCheckOut(checkout5_user2);
        
        // Calculate total unique checkouts for guest user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        
        // Verify expected result
        assertEquals("Total unique books checked out by G002 should be 2", 2, result);
    }
}