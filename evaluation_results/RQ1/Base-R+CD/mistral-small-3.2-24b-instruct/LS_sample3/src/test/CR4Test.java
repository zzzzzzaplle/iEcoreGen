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
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(300);
        
        // Add checkouts for user M001 (3 different books)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        checkout1.setBook(book1);
        userM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10 23:59:59"));
        checkout2.setBook(book2);
        userM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30 23:59:59"));
        checkout3.setBook(book3);
        userM001.addCheckOut(checkout3);
        
        // Add checkouts for user M002 (2 same book B001)
        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12 23:59:59"));
        checkout4.setBook(book1);
        userM002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20 23:59:59"));
        checkout5.setBook(book1);
        userM002.addCheckOut(checkout5);
        
        // Add users to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Calculate total unique books for each user
        int totalM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int totalM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Verify expected results
        assertEquals("Total unique books for M001 should be 3", 3, totalM001);
        assertEquals("Total unique books for M002 should be 1", 1, totalM002);
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
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        // Add checkout for user G001 (1 book)
        CheckOut checkout = new CheckOut();
        checkout.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-05-10 23:59:59"));
        checkout.setBook(book1);
        userG001.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.addUser(userG001);
        
        // Calculate total unique books for user G001
        int totalG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Verify expected result
        assertEquals("Total unique books for G001 should be 1", 1, totalG001);
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
        
        // Add user to library system (no checkouts added)
        librarySystem.addUser(userM003);
        
        // Calculate total unique books for user M003
        int totalM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Verify expected result
        assertEquals("Total unique books for M003 should be 0", 0, totalM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() throws Exception {
        // Create Member user M004
        User userM004 = new User();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        
        // Create books B001, B002, B003, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(300);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(400);
        
        // Add checkouts for user M004 (4 different books)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        checkout1.setBook(book1);
        userM004.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        checkout2.setBook(book2);
        userM004.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        checkout3.setBook(book3);
        userM004.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01 23:59:59"));
        checkout4.setBook(book4);
        userM004.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.addUser(userM004);
        
        // Calculate total unique books for user M004
        int totalM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Verify expected result
        assertEquals("Total unique books for M004 should be 4", 4, totalM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() throws Exception {
        // Create Guest user G002
        User userG002 = new User();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Create books B001, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(400);
        
        // Add checkouts for user G002 (multiple checkouts of same books)
        // 2 checkouts of B001
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01 23:59:59"));
        checkout1.setBook(book1);
        userG002.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01 23:59:59"));
        checkout2.setBook(book1);
        userG002.addCheckOut(checkout2);
        
        // 3 checkouts of B004
        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01 23:59:59"));
        checkout3.setBook(book4);
        userG002.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11 23:59:59"));
        checkout4.setBook(book4);
        userG002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01 23:59:59"));
        checkout5.setBook(book4);
        userG002.addCheckOut(checkout5);
        
        // Add user to library system
        librarySystem.addUser(userG002);
        
        // Calculate total unique books for user G002
        int totalG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Verify expected result (should be 2 unique books: B001 and B004)
        assertEquals("Total unique books for G002 should be 2", 2, totalG002);
    }
}