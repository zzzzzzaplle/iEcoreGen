import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_calculateTotalCheckoutsForMemberUser() throws ParseException {
        // Create Member user M001
        User alice = new User();
        alice.setID("M001");
        alice.setName("Alice");
        alice.setEmail("alice@example.com");
        alice.setAddress("123 Main St");
        alice.setType(UserType.MEMBER);
        
        // Create Member user M002
        User bob = new User();
        bob.setID("M002");
        bob.setName("Bob");
        bob.setEmail("bob@example.com");
        bob.setAddress("456 Elm St");
        bob.setType(UserType.MEMBER);
        
        // Create books B001, B002, B003
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        
        // Check out 3 different books with user M001
        CheckOut co1 = new CheckOut();
        co1.setBook(book1);
        co1.setStartDate(dateFormat.parse("2023-01-01"));
        co1.setEndDate(dateFormat.parse("2023-01-15"));
        
        CheckOut co2 = new CheckOut();
        co2.setBook(book2);
        co2.setStartDate(dateFormat.parse("2023-02-01"));
        co2.setEndDate(dateFormat.parse("2023-02-10"));
        
        CheckOut co3 = new CheckOut();
        co3.setBook(book3);
        co3.setStartDate(dateFormat.parse("2023-03-15"));
        co3.setEndDate(dateFormat.parse("2023-03-30"));
        
        alice.addCheckOut(co1);
        alice.addCheckOut(co2);
        alice.addCheckOut(co3);
        
        // Check out 2 same book (B001) with user M002
        CheckOut co4 = new CheckOut();
        co4.setBook(book1);
        co4.setStartDate(dateFormat.parse("2023-04-01"));
        co4.setEndDate(dateFormat.parse("2023-04-12"));
        
        CheckOut co5 = new CheckOut();
        co5.setBook(book1);
        co5.setStartDate(dateFormat.parse("2023-04-15"));
        co5.setEndDate(dateFormat.parse("2023-04-20"));
        
        bob.addCheckOut(co4);
        bob.addCheckOut(co5);
        
        // Add users to library system
        librarySystem.addUser(alice);
        librarySystem.addUser(bob);
        
        // Verify results
        int totalCheckoutsM001 = librarySystem.totalUniqueBooksCheckedOutByUser(alice);
        int totalCheckoutsM002 = librarySystem.totalUniqueBooksCheckedOutByUser(bob);
        
        assertEquals(3, totalCheckoutsM001);
        assertEquals(1, totalCheckoutsM002);
    }
    
    @Test
    public void testCase2_calculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user G001
        User charlie = new User();
        charlie.setID("G001");
        charlie.setName("Charlie");
        charlie.setEmail("charlie@example.com");
        charlie.setAddress("789 Pine St");
        charlie.setType(UserType.GUEST);
        
        // Create book B001
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        // Check out 1 book with user G001
        CheckOut co1 = new CheckOut();
        co1.setBook(book1);
        co1.setStartDate(dateFormat.parse("2023-05-01"));
        co1.setEndDate(dateFormat.parse("2023-05-10"));
        
        charlie.addCheckOut(co1);
        
        // Add user to library system
        librarySystem.addUser(charlie);
        
        // Verify result
        int totalCheckoutsG001 = librarySystem.totalUniqueBooksCheckedOutByUser(charlie);
        
        assertEquals(1, totalCheckoutsG001);
    }
    
    @Test
    public void testCase3_mixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        User eve = new User();
        eve.setID("M003");
        eve.setName("Eve");
        eve.setEmail("eve@example.com");
        eve.setAddress("654 Maple St");
        eve.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(eve);
        
        // Verify result
        int totalCheckoutsM003 = librarySystem.totalUniqueBooksCheckedOutByUser(eve);
        
        assertEquals(0, totalCheckoutsM003);
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_member() throws ParseException {
        // Create Member user M004
        User george = new User();
        george.setID("M004");
        george.setName("George");
        george.setEmail("george@example.com");
        george.setAddress("135 Cedar St");
        george.setType(UserType.MEMBER);
        
        // Create books B001, B002, B003, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        
        // Check out 4 different books with user M004
        CheckOut co1 = new CheckOut();
        co1.setBook(book1);
        co1.setStartDate(dateFormat.parse("2023-06-01"));
        co1.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut co2 = new CheckOut();
        co2.setBook(book2);
        co2.setStartDate(dateFormat.parse("2023-06-01"));
        co2.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut co3 = new CheckOut();
        co3.setBook(book3);
        co3.setStartDate(dateFormat.parse("2023-06-01"));
        co3.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut co4 = new CheckOut();
        co4.setBook(book4);
        co4.setStartDate(dateFormat.parse("2023-06-01"));
        co4.setEndDate(dateFormat.parse("2023-07-01"));
        
        george.addCheckOut(co1);
        george.addCheckOut(co2);
        george.addCheckOut(co3);
        george.addCheckOut(co4);
        
        // Add user to library system
        librarySystem.addUser(george);
        
        // Verify result
        int totalCheckoutsM004 = librarySystem.totalUniqueBooksCheckedOutByUser(george);
        
        assertEquals(4, totalCheckoutsM004);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_guest() throws ParseException {
        // Create Guest user G002
        User hannah = new User();
        hannah.setID("G002");
        hannah.setName("Hannah");
        hannah.setEmail("hannah@example.com");
        hannah.setAddress("246 Spruce St");
        hannah.setType(UserType.GUEST);
        
        // Create books B001, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        
        // Check out 2 same book (B001) with user G002
        CheckOut co1 = new CheckOut();
        co1.setBook(book1);
        co1.setStartDate(dateFormat.parse("2023-07-15"));
        co1.setEndDate(dateFormat.parse("2023-08-01"));
        
        CheckOut co2 = new CheckOut();
        co2.setBook(book1);
        co2.setStartDate(dateFormat.parse("2023-08-15"));
        co2.setEndDate(dateFormat.parse("2023-09-01"));
        
        // Check out B004 3 times with user G002
        CheckOut co3 = new CheckOut();
        co3.setBook(book4);
        co3.setStartDate(dateFormat.parse("2024-06-01"));
        co3.setEndDate(dateFormat.parse("2024-07-01"));
        
        CheckOut co4 = new CheckOut();
        co4.setBook(book4);
        co4.setStartDate(dateFormat.parse("2024-07-02"));
        co4.setEndDate(dateFormat.parse("2024-07-11"));
        
        CheckOut co5 = new CheckOut();
        co5.setBook(book4);
        co5.setStartDate(dateFormat.parse("2024-08-01"));
        co5.setEndDate(dateFormat.parse("2024-09-01"));
        
        hannah.addCheckOut(co1);
        hannah.addCheckOut(co2);
        hannah.addCheckOut(co3);
        hannah.addCheckOut(co4);
        hannah.addCheckOut(co5);
        
        // Add user to library system
        librarySystem.addUser(hannah);
        
        // Verify result
        int totalCheckoutsG002 = librarySystem.totalUniqueBooksCheckedOutByUser(hannah);
        
        assertEquals(2, totalCheckoutsG002);
    }
}