import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private User member1;
    private User member2;
    private User guest1;
    private User member3;
    private User member4;
    private User guest2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Before
    public void setUp() throws ParseException {
        librarySystem = new LibrarySystem();
        
        // Create users
        member1 = new User("Alice", "alice@example.com", "123 Main St", "M001", UserType.MEMBER);
        member2 = new User("Bob", "bob@example.com", "456 Elm St", "M002", UserType.MEMBER);
        guest1 = new User("Charlie", "charlie@example.com", "789 Pine St", "G001", UserType.GUEST);
        member3 = new User("Eve", "eve@example.com", "654 Maple St", "M003", UserType.MEMBER);
        member4 = new User("George", "george@example.com", "135 Cedar St", "M004", UserType.MEMBER);
        guest2 = new User("Hannah", "hannah@example.com", "246 Spruce St", "G002", UserType.GUEST);
        
        // Create books
        book1 = new Book("Book 1", "B001", "ISBN1", 100);
        book2 = new Book("Book 2", "B002", "ISBN2", 200);
        book3 = new Book("Book 3", "B003", "ISBN3", 300);
        book4 = new Book("Book 4", "B004", "ISBN4", 400);
        
        // Add books to library
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Add users to library
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(guest1);
        librarySystem.addUser(member3);
        librarySystem.addUser(member4);
        librarySystem.addUser(guest2);
    }
    
    @Test
    public void testCase1_calculateTotalCheckoutsForMemberUser() throws ParseException {
        // Check Out 3 different books (B001, B002, B003) with user M001
        Date startDate1 = dateFormat.parse("2023-01-01");
        Date endDate1 = dateFormat.parse("2023-01-15");
        librarySystem.checkoutBook(member1, book1, startDate1, endDate1);
        
        Date startDate2 = dateFormat.parse("2023-02-01");
        Date endDate2 = dateFormat.parse("2023-02-10");
        librarySystem.checkoutBook(member1, book2, startDate2, endDate2);
        
        Date startDate3 = dateFormat.parse("2023-03-15");
        Date endDate3 = dateFormat.parse("2023-03-30");
        librarySystem.checkoutBook(member1, book3, startDate3, endDate3);
        
        // Check Out 2 same book (B001) with user M002
        Date startDate4 = dateFormat.parse("2023-04-01");
        Date endDate4 = dateFormat.parse("2023-04-12");
        librarySystem.checkoutBook(member2, book1, startDate4, endDate4);
        
        Date startDate5 = dateFormat.parse("2023-04-15");
        Date endDate5 = dateFormat.parse("2023-04-20");
        librarySystem.checkoutBook(member2, book1, startDate5, endDate5);
        
        // Verify total unique books checked out
        int member1Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(member1);
        int member2Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(member2);
        
        assertEquals(3, member1Checkouts);
        assertEquals(1, member2Checkouts);
    }
    
    @Test
    public void testCase2_calculateTotalCheckoutsForGuestUser() throws ParseException {
        // Check Out 1 book (B001) with user G001
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-05-10");
        librarySystem.checkoutBook(guest1, book1, startDate, endDate);
        
        // Verify total unique books checked out
        int guest1Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(guest1);
        
        assertEquals(1, guest1Checkouts);
    }
    
    @Test
    public void testCase3_mixedUserTypesWithNoCheckouts() {
        // User M003 has no checkouts
        int member3Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(member3);
        
        assertEquals(0, member3Checkouts);
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_member() throws ParseException {
        // Check out 4 different books (B001, B002, B003, B004) with user M004
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-07-01");
        librarySystem.checkoutBook(member4, book1, startDate, endDate);
        librarySystem.checkoutBook(member4, book2, startDate, endDate);
        librarySystem.checkoutBook(member4, book3, startDate, endDate);
        librarySystem.checkoutBook(member4, book4, startDate, endDate);
        
        // Verify total unique books checked out
        int member4Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(member4);
        
        assertEquals(4, member4Checkouts);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_guest() throws ParseException {
        // Check out 2 same book (B001) with user G002
        Date startDate1 = dateFormat.parse("2023-07-15");
        Date endDate1 = dateFormat.parse("2023-08-01");
        librarySystem.checkoutBook(guest2, book1, startDate1, endDate1);
        
        Date startDate2 = dateFormat.parse("2023-08-15");
        Date endDate2 = dateFormat.parse("2023-09-01");
        librarySystem.checkoutBook(guest2, book1, startDate2, endDate2);
        
        // Check out B004 3 times with user G002
        Date startDate3 = dateFormat.parse("2024-06-01");
        Date endDate3 = dateFormat.parse("2024-07-01");
        librarySystem.checkoutBook(guest2, book4, startDate3, endDate3);
        
        Date startDate4 = dateFormat.parse("2024-07-02");
        Date endDate4 = dateFormat.parse("2024-07-11");
        librarySystem.checkoutBook(guest2, book4, startDate4, endDate4);
        
        Date startDate5 = dateFormat.parse("2024-08-01");
        Date endDate5 = dateFormat.parse("2024-09-01");
        librarySystem.checkoutBook(guest2, book4, startDate5, endDate5);
        
        // Verify total unique books checked out
        int guest2Checkouts = librarySystem.totalUniqueBooksCheckedOutByUser(guest2);
        
        assertEquals(2, guest2Checkouts);
    }
}