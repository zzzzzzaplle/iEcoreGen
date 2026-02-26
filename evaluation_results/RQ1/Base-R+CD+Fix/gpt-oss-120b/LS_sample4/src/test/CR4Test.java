import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws Exception {
        // Create Member user M001: Alice
        User userM001 = new User();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        userM001.setType(UserType.MEMBER);
        
        // Create Member user M002: Bob
        User userM002 = new User();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        userM002.setType(UserType.MEMBER);
        
        // Create books B001, B002, B003
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setISBN("ISBN001");
        bookB001.setNumberOfPages(200);
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        bookB002.setISBN("ISBN002");
        bookB002.setNumberOfPages(300);
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        bookB003.setISBN("ISBN003");
        bookB003.setNumberOfPages(400);
        
        // Check Out 3 different books (B001, B002, B003) with user M001
        CheckOut checkout1M001 = new CheckOut();
        checkout1M001.setBook(bookB001);
        checkout1M001.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1M001.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        userM001.addCheckOut(checkout1M001);
        
        CheckOut checkout2M001 = new CheckOut();
        checkout2M001.setBook(bookB002);
        checkout2M001.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2M001.setEndDate(dateFormat.parse("2023-02-10 23:59:59"));
        userM001.addCheckOut(checkout2M001);
        
        CheckOut checkout3M001 = new CheckOut();
        checkout3M001.setBook(bookB003);
        checkout3M001.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3M001.setEndDate(dateFormat.parse("2023-03-30 23:59:59"));
        userM001.addCheckOut(checkout3M001);
        
        // Check Out 2 same book (B001) with user M002
        CheckOut checkout1M002 = new CheckOut();
        checkout1M002.setBook(bookB001);
        checkout1M002.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout1M002.setEndDate(dateFormat.parse("2023-04-12 23:59:59"));
        userM002.addCheckOut(checkout1M002);
        
        CheckOut checkout2M002 = new CheckOut();
        checkout2M002.setBook(bookB001);
        checkout2M002.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout2M002.setEndDate(dateFormat.parse("2023-04-20 23:59:59"));
        userM002.addCheckOut(checkout2M002);
        
        // Add users to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Calculate and verify results
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        assertEquals("Total Member Checkouts for M001 should be 3", 3, resultM001);
        assertEquals("Total Member Checkouts for M002 should be 1", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Create Guest user G001: Charlie
        User userG001 = new User();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        userG001.setType(UserType.GUEST);
        
        // Create book B001
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setISBN("ISBN001");
        bookB001.setNumberOfPages(200);
        
        // Check Out 1 book (B001) with user G001
        CheckOut checkoutG001 = new CheckOut();
        checkoutG001.setBook(bookB001);
        checkoutG001.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkoutG001.setEndDate(dateFormat.parse("2023-05-10 23:59:59"));
        userG001.addCheckOut(checkoutG001);
        
        // Add user to library system
        librarySystem.addUser(userG001);
        
        // Calculate and verify result
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        assertEquals("Total Guest Checkouts for G001 should be 1", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() throws Exception {
        // Create Member user M003: Eve with no checkouts
        User userM003 = new User();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        // No checkouts added
        
        // Add user to library system
        librarySystem.addUser(userM003);
        
        // Calculate and verify result
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        assertEquals("Total Checkouts for M003 with no checkouts should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() throws Exception {
        // Create Member user M004: George
        User userM004 = new User();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        
        // Create books B001, B002, B003, B004
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setISBN("ISBN001");
        bookB001.setNumberOfPages(200);
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        bookB002.setISBN("ISBN002");
        bookB002.setNumberOfPages(300);
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        bookB003.setISBN("ISBN003");
        bookB003.setNumberOfPages(400);
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        bookB004.setISBN("ISBN004");
        bookB004.setNumberOfPages(500);
        
        // Check out 4 different books (B001, B002, B003, B004) with user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.addUser(userM004);
        
        // Calculate and verify result
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        assertEquals("Total Checkouts for M004 should be 4", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() throws Exception {
        // Create Guest user G002: Hannah
        User userG002 = new User();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Create books B001 and B004
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setISBN("ISBN001");
        bookB001.setNumberOfPages(200);
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        bookB004.setISBN("ISBN004");
        bookB004.setNumberOfPages(500);
        
        // Check out 2 same book (B001) with user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01 23:59:59"));
        userG002.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB001);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01 23:59:59"));
        userG002.addCheckOut(checkout2);
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB004);
        checkout3.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01 23:59:59"));
        userG002.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11 23:59:59"));
        userG002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB004);
        checkout5.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01 23:59:59"));
        userG002.addCheckOut(checkout5);
        
        // Add user to library system
        librarySystem.addUser(userG002);
        
        // Calculate and verify result
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        assertEquals("Total Checkouts for G002 should be 2", 2, result);
    }
}