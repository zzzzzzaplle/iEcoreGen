import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        User userM001 = new User();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        userM001.setType(UserType.MEMBER);
        
        // Create Member user M002
        User userM002 = new User();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        userM002.setType(UserType.MEMBER);
        
        // Add users to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        
        // Checkout 3 different books with user M001
        CheckOut co1 = new CheckOut();
        co1.setBook(bookB001);
        co1.setStartDate(dateFormat.parse("2023-01-01"));
        co1.setEndDate(dateFormat.parse("2023-01-15"));
        userM001.addCheckOut(co1);
        
        CheckOut co2 = new CheckOut();
        co2.setBook(bookB002);
        co2.setStartDate(dateFormat.parse("2023-02-01"));
        co2.setEndDate(dateFormat.parse("2023-02-10"));
        userM001.addCheckOut(co2);
        
        CheckOut co3 = new CheckOut();
        co3.setBook(bookB003);
        co3.setStartDate(dateFormat.parse("2023-03-15"));
        co3.setEndDate(dateFormat.parse("2023-03-30"));
        userM001.addCheckOut(co3);
        
        // Checkout 2 same book (B001) with user M002
        CheckOut co4 = new CheckOut();
        co4.setBook(bookB001);
        co4.setStartDate(dateFormat.parse("2023-04-01"));
        co4.setEndDate(dateFormat.parse("2023-04-12"));
        userM002.addCheckOut(co4);
        
        CheckOut co5 = new CheckOut();
        co5.setBook(bookB001);
        co5.setStartDate(dateFormat.parse("2023-04-15"));
        co5.setEndDate(dateFormat.parse("2023-04-20"));
        userM002.addCheckOut(co5);
        
        // Verify total unique books checked out
        assertEquals(3, librarySystem.totalUniqueBooksCheckedOutByUser(userM001));
        assertEquals(1, librarySystem.totalUniqueBooksCheckedOutByUser(userM002));
    }
    
    @Test
    public void testCase2_calculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user G001
        User userG001 = new User();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        userG001.setType(UserType.GUEST);
        
        // Add user to library system
        librarySystem.addUser(userG001);
        
        // Create book
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        
        // Checkout 1 book with user G001
        CheckOut co1 = new CheckOut();
        co1.setBook(bookB001);
        co1.setStartDate(dateFormat.parse("2023-05-01"));
        co1.setEndDate(dateFormat.parse("2023-05-10"));
        userG001.addCheckOut(co1);
        
        // Verify total unique books checked out
        assertEquals(1, librarySystem.totalUniqueBooksCheckedOutByUser(userG001));
    }
    
    @Test
    public void testCase3_mixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        User userM003 = new User();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(userM003);
        
        // Verify total unique books checked out is 0
        assertEquals(0, librarySystem.totalUniqueBooksCheckedOutByUser(userM003));
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_member() throws ParseException {
        // Create Member user M004
        User userM004 = new User();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(userM004);
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        
        // Checkout 4 different books with user M004
        CheckOut co1 = new CheckOut();
        co1.setBook(bookB001);
        co1.setStartDate(dateFormat.parse("2023-06-01"));
        co1.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(co1);
        
        CheckOut co2 = new CheckOut();
        co2.setBook(bookB002);
        co2.setStartDate(dateFormat.parse("2023-06-01"));
        co2.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(co2);
        
        CheckOut co3 = new CheckOut();
        co3.setBook(bookB003);
        co3.setStartDate(dateFormat.parse("2023-06-01"));
        co3.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(co3);
        
        CheckOut co4 = new CheckOut();
        co4.setBook(bookB004);
        co4.setStartDate(dateFormat.parse("2023-06-01"));
        co4.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(co4);
        
        // Verify total unique books checked out
        assertEquals(4, librarySystem.totalUniqueBooksCheckedOutByUser(userM004));
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypes_guest() throws ParseException {
        // Create Guest user G002
        User userG002 = new User();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Add user to library system
        librarySystem.addUser(userG002);
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        
        // Checkout 2 same book (B001) with user G002
        CheckOut co1 = new CheckOut();
        co1.setBook(bookB001);
        co1.setStartDate(dateFormat.parse("2023-07-15"));
        co1.setEndDate(dateFormat.parse("2023-08-01"));
        userG002.addCheckOut(co1);
        
        CheckOut co2 = new CheckOut();
        co2.setBook(bookB001);
        co2.setStartDate(dateFormat.parse("2023-08-15"));
        co2.setEndDate(dateFormat.parse("2023-09-01"));
        userG002.addCheckOut(co2);
        
        // Checkout B004 3 times with user G002
        CheckOut co3 = new CheckOut();
        co3.setBook(bookB004);
        co3.setStartDate(dateFormat.parse("2024-06-01"));
        co3.setEndDate(dateFormat.parse("2024-07-01"));
        userG002.addCheckOut(co3);
        
        CheckOut co4 = new CheckOut();
        co4.setBook(bookB004);
        co4.setStartDate(dateFormat.parse("2024-07-02"));
        co4.setEndDate(dateFormat.parse("2024-07-11"));
        userG002.addCheckOut(co4);
        
        CheckOut co5 = new CheckOut();
        co5.setBook(bookB004);
        co5.setStartDate(dateFormat.parse("2024-08-01"));
        co5.setEndDate(dateFormat.parse("2024-09-01"));
        userG002.addCheckOut(co5);
        
        // Verify total unique books checked out
        assertEquals(2, librarySystem.totalUniqueBooksCheckedOutByUser(userG002));
    }
}