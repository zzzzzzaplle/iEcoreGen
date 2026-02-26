import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private MemberUser memberUser1;
    private MemberUser memberUser2;
    private MemberUser memberUser3;
    private MemberUser memberUser4;
    private GuestUser guestUser1;
    private GuestUser guestUser2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create member users
        memberUser1 = new MemberUser();
        memberUser1.setId("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        
        memberUser2 = new MemberUser();
        memberUser2.setId("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        
        memberUser3 = new MemberUser();
        memberUser3.setId("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        
        memberUser4 = new MemberUser();
        memberUser4.setId("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        
        // Create guest users
        guestUser1 = new GuestUser();
        guestUser1.setId("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        
        guestUser2 = new GuestUser();
        guestUser2.setId("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        
        // Create books
        book1 = new Book();
        book1.setBarcode("B001");
        book1.setIsbn("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(200);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setIsbn("ISBN002");
        book2.setTitle("Book 2");
        book2.setNumberOfPages(300);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setIsbn("ISBN003");
        book3.setTitle("Book 3");
        book3.setNumberOfPages(400);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setIsbn("ISBN004");
        book4.setTitle("Book 4");
        book4.setNumberOfPages(500);
        
        // Add users and books to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        librarySystem.addUser(memberUser3);
        librarySystem.addUser(memberUser4);
        librarySystem.addUser(guestUser1);
        librarySystem.addUser(guestUser2);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Check Out 3 different books (B001, B002, B003) with user M001
        librarySystem.createBookLoan(book1, memberUser1, LocalDate.parse("2023-01-01", formatter), LocalDate.parse("2023-01-15", formatter));
        librarySystem.createBookLoan(book2, memberUser1, LocalDate.parse("2023-02-01", formatter), LocalDate.parse("2023-02-10", formatter));
        librarySystem.createBookLoan(book3, memberUser1, LocalDate.parse("2023-03-15", formatter), LocalDate.parse("2023-03-30", formatter));
        
        // Check Out 2 same book (B001) with user M002
        librarySystem.createBookLoan(book1, memberUser2, LocalDate.parse("2023-04-01", formatter), LocalDate.parse("2023-04-12", formatter));
        librarySystem.createBookLoan(book1, memberUser2, LocalDate.parse("2023-04-15", formatter), LocalDate.parse("2023-04-20", formatter));
        
        // Calculate total unique checkouts for member users
        int totalMember1 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(memberUser1);
        int totalMember2 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(memberUser2);
        
        // Verify expected outputs
        assertEquals("Total Member Checkouts for M001 should be 3", 3, totalMember1);
        assertEquals("Total Member Checkouts for M002 should be 1", 1, totalMember2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Check Out 1 book (B001) with user G001
        librarySystem.createBookLoan(book1, guestUser1, LocalDate.parse("2023-05-01", formatter), LocalDate.parse("2023-05-10", formatter));
        
        // Calculate total unique checkouts for guest user
        int totalGuest1 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(guestUser1);
        
        // Verify expected output
        assertEquals("Total Guest Checkouts for G001 should be 1", 1, totalGuest1);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // No checkouts for user M003
        
        // Calculate total unique checkouts for member user with no checkouts
        int totalMember3 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(memberUser3);
        
        // Verify expected output
        assertEquals("Total Checkouts for M003 should be 0", 0, totalMember3);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Check out 4 different books (B001, B002, B003, B004) with user M004
        librarySystem.createBookLoan(book1, memberUser4, LocalDate.parse("2023-06-01", formatter), LocalDate.parse("2023-07-01", formatter));
        librarySystem.createBookLoan(book2, memberUser4, LocalDate.parse("2023-06-01", formatter), LocalDate.parse("2023-07-01", formatter));
        librarySystem.createBookLoan(book3, memberUser4, LocalDate.parse("2023-06-01", formatter), LocalDate.parse("2023-07-01", formatter));
        librarySystem.createBookLoan(book4, memberUser4, LocalDate.parse("2023-06-01", formatter), LocalDate.parse("2023-07-01", formatter));
        
        // Calculate total unique checkouts for member user
        int totalMember4 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(memberUser4);
        
        // Verify expected output
        assertEquals("Total Checkouts for M004 should be 4", 4, totalMember4);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Check out 2 same book (B001) with user G002
        librarySystem.createBookLoan(book1, guestUser2, LocalDate.parse("2023-07-15", formatter), LocalDate.parse("2023-08-01", formatter));
        librarySystem.createBookLoan(book1, guestUser2, LocalDate.parse("2023-08-15", formatter), LocalDate.parse("2023-09-01", formatter));
        
        // Check out B004 3 times with user G002
        librarySystem.createBookLoan(book4, guestUser2, LocalDate.parse("2024-06-01", formatter), LocalDate.parse("2024-07-01", formatter));
        librarySystem.createBookLoan(book4, guestUser2, LocalDate.parse("2024-07-02", formatter), LocalDate.parse("2024-07-11", formatter));
        librarySystem.createBookLoan(book4, guestUser2, LocalDate.parse("2024-08-01", formatter), LocalDate.parse("2024-09-01", formatter));
        
        // Calculate total unique checkouts for guest user
        int totalGuest2 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser(guestUser2);
        
        // Verify expected output
        assertEquals("Total Checkouts for G002 should be 2", 2, totalGuest2);
    }
}