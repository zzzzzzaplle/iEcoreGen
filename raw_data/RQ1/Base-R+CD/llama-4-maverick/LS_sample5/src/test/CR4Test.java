import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
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
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        
        // Set up date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Check out 3 different books with user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(sdf.parse("2023-01-01"));
        checkout1.setEndDate(sdf.parse("2023-01-15"));
        userM001.getCheckOuts().add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(sdf.parse("2023-02-01"));
        checkout2.setEndDate(sdf.parse("2023-02-10"));
        userM001.getCheckOuts().add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(sdf.parse("2023-03-15"));
        checkout3.setEndDate(sdf.parse("2023-03-30"));
        userM001.getCheckOuts().add(checkout3);
        
        // Check out 2 same book with user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB001);
        checkout4.setStartDate(sdf.parse("2023-04-01"));
        checkout4.setEndDate(sdf.parse("2023-04-12"));
        userM002.getCheckOuts().add(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB001);
        checkout5.setStartDate(sdf.parse("2023-04-15"));
        checkout5.setEndDate(sdf.parse("2023-04-20"));
        userM002.getCheckOuts().add(checkout5);
        
        // Add users to library system
        librarySystem.getUsers().add(userM001);
        librarySystem.getUsers().add(userM002);
        
        // Calculate total unique books checked out
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Assert results
        assertEquals(3, resultM001);
        assertEquals(1, resultM002);
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
        
        // Create book
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        
        // Set up date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Check out 1 book with user G001
        CheckOut checkout = new CheckOut();
        checkout.setBook(bookB001);
        checkout.setStartDate(sdf.parse("2023-05-01"));
        checkout.setEndDate(sdf.parse("2023-05-10"));
        userG001.getCheckOuts().add(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(userG001);
        
        // Calculate total unique books checked out
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Assert result
        assertEquals(1, resultG001);
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
        librarySystem.getUsers().add(userM003);
        
        // Calculate total unique books checked out
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Assert result
        assertEquals(0, resultM003);
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypes() throws ParseException {
        // Create Member user M004
        User userM004 = new User();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        
        // Set up date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Check out 4 different books with user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(sdf.parse("2023-06-01"));
        checkout1.setEndDate(sdf.parse("2023-07-01"));
        userM004.getCheckOuts().add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(sdf.parse("2023-06-01"));
        checkout2.setEndDate(sdf.parse("2023-07-01"));
        userM004.getCheckOuts().add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(sdf.parse("2023-06-01"));
        checkout3.setEndDate(sdf.parse("2023-07-01"));
        userM004.getCheckOuts().add(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(sdf.parse("2023-06-01"));
        checkout4.setEndDate(sdf.parse("2023-07-01"));
        userM004.getCheckOuts().add(checkout4);
        
        // Add user to library system
        librarySystem.getUsers().add(userM004);
        
        // Calculate total unique books checked out
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Assert result
        assertEquals(4, resultM004);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypes() throws ParseException {
        // Create Guest user G002
        User userG002 = new User();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        
        // Set up date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Check out 2 same book with user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(sdf.parse("2023-07-15"));
        checkout1.setEndDate(sdf.parse("2023-08-01"));
        userG002.getCheckOuts().add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB001);
        checkout2.setStartDate(sdf.parse("2023-08-15"));
        checkout2.setEndDate(sdf.parse("2023-09-01"));
        userG002.getCheckOuts().add(checkout2);
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB004);
        checkout3.setStartDate(sdf.parse("2024-06-01"));
        checkout3.setEndDate(sdf.parse("2024-07-01"));
        userG002.getCheckOuts().add(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(sdf.parse("2024-07-02"));
        checkout4.setEndDate(sdf.parse("2024-07-11"));
        userG002.getCheckOuts().add(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB004);
        checkout5.setStartDate(sdf.parse("2024-08-01"));
        checkout5.setEndDate(sdf.parse("2024-09-01"));
        userG002.getCheckOuts().add(checkout5);
        
        // Add user to library system
        librarySystem.getUsers().add(userG002);
        
        // Calculate total unique books checked out
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Assert result
        assertEquals(2, resultG002);
    }
}