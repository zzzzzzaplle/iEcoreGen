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
        book1.setISBN("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setISBN("ISBN002");
        book2.setTitle("Book 2");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setISBN("ISBN003");
        book3.setTitle("Book 3");
        book3.setNumberOfPages(300);
        
        // Add checkouts for user M001 (3 different books)
        CheckOut checkout1_M001 = new CheckOut();
        checkout1_M001.setBook(book1);
        checkout1_M001.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1_M001.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        userM001.addCheckOut(checkout1_M001);
        
        CheckOut checkout2_M001 = new CheckOut();
        checkout2_M001.setBook(book2);
        checkout2_M001.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2_M001.setEndDate(dateFormat.parse("2023-02-10 00:00:00"));
        userM001.addCheckOut(checkout2_M001);
        
        CheckOut checkout3_M001 = new CheckOut();
        checkout3_M001.setBook(book3);
        checkout3_M001.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3_M001.setEndDate(dateFormat.parse("2023-03-30 00:00:00"));
        userM001.addCheckOut(checkout3_M001);
        
        // Add checkouts for user M002 (2 same book - B001)
        CheckOut checkout1_M002 = new CheckOut();
        checkout1_M002.setBook(book1);
        checkout1_M002.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout1_M002.setEndDate(dateFormat.parse("2023-04-12 00:00:00"));
        userM002.addCheckOut(checkout1_M002);
        
        CheckOut checkout2_M002 = new CheckOut();
        checkout2_M002.setBook(book1); // Same book as checkout1_M002
        checkout2_M002.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout2_M002.setEndDate(dateFormat.parse("2023-04-20 00:00:00"));
        userM002.addCheckOut(checkout2_M002);
        
        // Add users to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Test total unique books for M001
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        assertEquals("Member M001 should have 3 unique books checked out", 3, resultM001);
        
        // Test total unique books for M002
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        assertEquals("Member M002 should have 1 unique book checked out", 1, resultM002);
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
        book1.setISBN("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(100);
        
        // Add checkout for user G001 (1 book)
        CheckOut checkout1_G001 = new CheckOut();
        checkout1_G001.setBook(book1);
        checkout1_G001.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1_G001.setEndDate(dateFormat.parse("2023-05-10 00:00:00"));
        userG001.addCheckOut(checkout1_G001);
        
        // Add user to library system
        librarySystem.addUser(userG001);
        
        // Test total unique books for G001
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        assertEquals("Guest G001 should have 1 unique book checked out", 1, resultG001);
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
        // No checkouts added
        
        // Add user to library system
        librarySystem.addUser(userM003);
        
        // Test total unique books for M003 (should be 0)
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        assertEquals("Member M003 with no checkouts should have 0 unique books", 0, resultM003);
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
        
        // Create 4 different books
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setISBN("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setISBN("ISBN002");
        book2.setTitle("Book 2");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setISBN("ISBN003");
        book3.setTitle("Book 3");
        book3.setNumberOfPages(300);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setISBN("ISBN004");
        book4.setTitle("Book 4");
        book4.setNumberOfPages(400);
        
        // Add 4 different checkouts for user M004
        CheckOut checkout1_M004 = new CheckOut();
        checkout1_M004.setBook(book1);
        checkout1_M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1_M004.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout1_M004);
        
        CheckOut checkout2_M004 = new CheckOut();
        checkout2_M004.setBook(book2);
        checkout2_M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2_M004.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout2_M004);
        
        CheckOut checkout3_M004 = new CheckOut();
        checkout3_M004.setBook(book3);
        checkout3_M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3_M004.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout3_M004);
        
        CheckOut checkout4_M004 = new CheckOut();
        checkout4_M004.setBook(book4);
        checkout4_M004.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4_M004.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout4_M004);
        
        // Add user to library system
        librarySystem.addUser(userM004);
        
        // Test total unique books for M004
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        assertEquals("Member M004 should have 4 unique books checked out", 4, resultM004);
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
        
        // Create books B001 and B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setISBN("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(100);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setISBN("ISBN004");
        book4.setTitle("Book 4");
        book4.setNumberOfPages(400);
        
        // Add 2 checkouts for same book B001
        CheckOut checkout1_G002 = new CheckOut();
        checkout1_G002.setBook(book1);
        checkout1_G002.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1_G002.setEndDate(dateFormat.parse("2023-08-01 00:00:00"));
        userG002.addCheckOut(checkout1_G002);
        
        CheckOut checkout2_G002 = new CheckOut();
        checkout2_G002.setBook(book1); // Same book as checkout1_G002
        checkout2_G002.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2_G002.setEndDate(dateFormat.parse("2023-09-01 00:00:00"));
        userG002.addCheckOut(checkout2_G002);
        
        // Add 3 checkouts for same book B004
        CheckOut checkout3_G002 = new CheckOut();
        checkout3_G002.setBook(book4);
        checkout3_G002.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3_G002.setEndDate(dateFormat.parse("2024-07-01 00:00:00"));
        userG002.addCheckOut(checkout3_G002);
        
        CheckOut checkout4_G002 = new CheckOut();
        checkout4_G002.setBook(book4); // Same book as checkout3_G002
        checkout4_G002.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4_G002.setEndDate(dateFormat.parse("2024-07-11 00:00:00"));
        userG002.addCheckOut(checkout4_G002);
        
        CheckOut checkout5_G002 = new CheckOut();
        checkout5_G002.setBook(book4); // Same book as checkout3_G002 and checkout4_G002
        checkout5_G002.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5_G002.setEndDate(dateFormat.parse("2024-09-01 00:00:00"));
        userG002.addCheckOut(checkout5_G002);
        
        // Add user to library system
        librarySystem.addUser(userG002);
        
        // Test total unique books for G002 (should be 2 unique books: B001 and B004)
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        assertEquals("Guest G002 should have 2 unique books checked out", 2, resultG002);
    }
}