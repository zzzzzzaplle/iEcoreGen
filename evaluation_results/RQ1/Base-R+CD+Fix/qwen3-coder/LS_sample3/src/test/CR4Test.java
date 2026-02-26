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
        bookB003.setNumberOfPages(250);
        
        // Create checkouts for user M001 with 3 different books
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
        
        // Create checkouts for user M002 with 2 same books (B001)
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
        
        // Calculate total unique books for each user
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Verify expected results
        assertEquals("Total unique books for M001 should be 3", 3, resultM001);
        assertEquals("Total unique books for M002 should be 1", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Create Guest user G001
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
        
        // Create checkout for user G001 with 1 book
        CheckOut checkoutG001 = new CheckOut();
        checkoutG001.setBook(bookB001);
        checkoutG001.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkoutG001.setEndDate(dateFormat.parse("2023-05-10 23:59:59"));
        userG001.addCheckOut(checkoutG001);
        
        // Calculate total unique books for guest user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Verify expected result
        assertEquals("Total unique books for G001 should be 1", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() throws Exception {
        // Create Member user M003 with no checkouts
        User userM003 = new User();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        // No checkouts added intentionally
        
        // Calculate total unique books for user with no checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Verify expected result
        assertEquals("Total unique books for M003 with no checkouts should be 0", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserWithMultipleBooks() throws Exception {
        // Create Member user M004
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
        bookB003.setNumberOfPages(250);
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        bookB004.setISBN("ISBN004");
        bookB004.setNumberOfPages(400);
        
        // Create checkouts for user M004 with 4 different books
        CheckOut checkout1M004 = new CheckOut();
        checkout1M004.setBook(bookB001);
        checkout1M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1M004.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout1M004);
        
        CheckOut checkout2M004 = new CheckOut();
        checkout2M004.setBook(bookB002);
        checkout2M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2M004.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout2M004);
        
        CheckOut checkout3M004 = new CheckOut();
        checkout3M004.setBook(bookB003);
        checkout3M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3M004.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout3M004);
        
        CheckOut checkout4M004 = new CheckOut();
        checkout4M004.setBook(bookB004);
        checkout4M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4M004.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        userM004.addCheckOut(checkout4M004);
        
        // Calculate total unique books for user M004
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Verify expected result
        assertEquals("Total unique books for M004 should be 4", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() throws Exception {
        // Create Guest user G002
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
        bookB004.setNumberOfPages(400);
        
        // Create checkouts for user G002 with duplicate books
        // 2 checkouts for B001
        CheckOut checkout1G002 = new CheckOut();
        checkout1G002.setBook(bookB001);
        checkout1G002.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1G002.setEndDate(dateFormat.parse("2023-08-01 23:59:59"));
        userG002.addCheckOut(checkout1G002);
        
        CheckOut checkout2G002 = new CheckOut();
        checkout2G002.setBook(bookB001);
        checkout2G002.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2G002.setEndDate(dateFormat.parse("2023-09-01 23:59:59"));
        userG002.addCheckOut(checkout2G002);
        
        // 3 checkouts for B004
        CheckOut checkout3G002 = new CheckOut();
        checkout3G002.setBook(bookB004);
        checkout3G002.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3G002.setEndDate(dateFormat.parse("2024-07-01 23:59:59"));
        userG002.addCheckOut(checkout3G002);
        
        CheckOut checkout4G002 = new CheckOut();
        checkout4G002.setBook(bookB004);
        checkout4G002.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4G002.setEndDate(dateFormat.parse("2024-07-11 23:59:59"));
        userG002.addCheckOut(checkout4G002);
        
        CheckOut checkout5G002 = new CheckOut();
        checkout5G002.setBook(bookB004);
        checkout5G002.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5G002.setEndDate(dateFormat.parse("2024-09-01 23:59:59"));
        userG002.addCheckOut(checkout5G002);
        
        // Calculate total unique books for user G002
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Verify expected result (should count unique books only)
        assertEquals("Total unique books for G002 should be 2", 2, result);
    }
}