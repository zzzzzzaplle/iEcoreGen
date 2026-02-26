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
        // Set up checkouts for member user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.of(2023, 1, 1));
        checkout1.setEndDate(LocalDate.of(2023, 1, 15));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.of(2023, 2, 1));
        checkout2.setEndDate(LocalDate.of(2023, 2, 10));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(LocalDate.of(2023, 3, 15));
        checkout3.setEndDate(LocalDate.of(2023, 3, 30));
        
        memberUser1.addCheckOut(checkout1);
        memberUser1.addCheckOut(checkout2);
        memberUser1.addCheckOut(checkout3);
        
        // Set up checkouts for member user M002 (same book borrowed twice)
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(LocalDate.of(2023, 4, 1));
        checkout4.setEndDate(LocalDate.of(2023, 4, 12));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(LocalDate.of(2023, 4, 15));
        checkout5.setEndDate(LocalDate.of(2023, 4, 20));
        
        memberUser2.addCheckOut(checkout4);
        memberUser2.addCheckOut(checkout5);
        
        // Test unique books for M001
        int result1 = librarySystem.uniqueBooksBorrowedByUser(memberUser1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertEquals("Member M001 should have 3 unique book checkouts", 3, result1);
        
        // Test unique books for M002 (should count same book only once)
        int result2 = librarySystem.uniqueBooksBorrowedByUser(memberUser2, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertEquals("Member M002 should have 1 unique book checkout", 1, result2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Set up checkout for guest user G001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.of(2023, 5, 1));
        checkout1.setEndDate(LocalDate.of(2023, 5, 10));
        
        guestUser1.addCheckOut(checkout1);
        
        // Test unique books for G001
        int result = librarySystem.uniqueBooksBorrowedByUser(guestUser1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertEquals("Guest G001 should have 1 unique book checkout", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Member user M003 has no checkouts
        int result = librarySystem.uniqueBooksBorrowedByUser(memberUser3, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertEquals("Member M003 with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Set up checkouts for member user M004 (4 different books)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.of(2023, 6, 1));
        checkout1.setEndDate(LocalDate.of(2023, 7, 1));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.of(2023, 6, 1));
        checkout2.setEndDate(LocalDate.of(2023, 7, 1));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(LocalDate.of(2023, 6, 1));
        checkout3.setEndDate(LocalDate.of(2023, 7, 1));
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(LocalDate.of(2023, 6, 1));
        checkout4.setEndDate(LocalDate.of(2023, 7, 1));
        
        memberUser4.addCheckOut(checkout1);
        memberUser4.addCheckOut(checkout2);
        memberUser4.addCheckOut(checkout3);
        memberUser4.addCheckOut(checkout4);
        
        // Test unique books for M004
        int result = librarySystem.uniqueBooksBorrowedByUser(memberUser4, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        assertEquals("Member M004 should have 4 unique book checkouts", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Set up checkouts for guest user G002 (same book borrowed twice, another book borrowed three times)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.of(2023, 7, 15));
        checkout1.setEndDate(LocalDate.of(2023, 8, 1));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(LocalDate.of(2023, 8, 15));
        checkout2.setEndDate(LocalDate.of(2023, 9, 1));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate(LocalDate.of(2024, 6, 1));
        checkout3.setEndDate(LocalDate.of(2024, 7, 1));
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(LocalDate.of(2024, 7, 2));
        checkout4.setEndDate(LocalDate.of(2024, 7, 11));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate(LocalDate.of(2024, 8, 1));
        checkout5.setEndDate(LocalDate.of(2024, 9, 1));
        
        guestUser2.addCheckOut(checkout1);
        guestUser2.addCheckOut(checkout2);
        guestUser2.addCheckOut(checkout3);
        guestUser2.addCheckOut(checkout4);
        guestUser2.addCheckOut(checkout5);
        
        // Test unique books for G002 (should count only unique books across all time)
        int result = librarySystem.uniqueBooksBorrowedByUser(guestUser2, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 12, 31));
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, result);
    }
}