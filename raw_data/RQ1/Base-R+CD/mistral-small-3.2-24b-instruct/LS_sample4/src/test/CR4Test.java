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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws Exception {
        // Create Member users
        User userM001 = new User();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        userM001.setType(UserType.MEMBER);
        
        User userM002 = new User();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        userM002.setType(UserType.MEMBER);
        
        // Create books
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
        
        // Create checkouts for user M001 (3 different books)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        userM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10"));
        userM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-03-15"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30"));
        userM001.addCheckOut(checkout3);
        
        // Create checkouts for user M002 (2 same books)
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        userM002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        userM002.addCheckOut(checkout5);
        
        // Add users to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Verify expected outputs
        assertEquals("Total Member Checkouts for M001 should be 3", 3, librarySystem.totalUniqueBooksCheckedOutByUser(userM001));
        assertEquals("Total Member Checkouts for M002 should be 1", 1, librarySystem.totalUniqueBooksCheckedOutByUser(userM002));
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Create Guest user
        User userG001 = new User();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        userG001.setType(UserType.GUEST);
        
        // Create book
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setISBN("ISBN001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(100);
        
        // Create checkout for user G001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-10"));
        userG001.addCheckOut(checkout1);
        
        // Add user to library system
        librarySystem.addUser(userG001);
        
        // Verify expected output
        assertEquals("Total Guest Checkouts for G001 should be 1", 1, librarySystem.totalUniqueBooksCheckedOutByUser(userG001));
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        User userM003 = new User();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        
        // Add user to library system (no checkouts added)
        librarySystem.addUser(userM003);
        
        // Verify expected output
        assertEquals("Total Checkouts for M003 should be 0", 0, librarySystem.totalUniqueBooksCheckedOutByUser(userM003));
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserMultipleBooks() throws Exception {
        // Create Member user
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
        
        // Create checkouts for user M004 (4 different books)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-06-01"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-06-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2023-06-01"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.addUser(userM004);
        
        // Verify expected output
        assertEquals("Total Checkouts for M004 should be 4", 4, librarySystem.totalUniqueBooksCheckedOutByUser(userM004));
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicates() throws Exception {
        // Create Guest user
        User userG002 = new User();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Create books
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
        
        // Create checkouts for user G002 - 2 same book (B001)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        userG002.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        userG002.addCheckOut(checkout2);
        
        // Create checkouts for user G002 - B004 3 times
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate(dateFormat.parse("2024-06-01"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01"));
        userG002.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2024-07-02"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11"));
        userG002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate(dateFormat.parse("2024-08-01"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01"));
        userG002.addCheckOut(checkout5);
        
        // Add user to library system
        librarySystem.addUser(userG002);
        
        // Verify expected output (2 unique books: B001 and B004)
        assertEquals("Total Checkouts for G002 should be 2", 2, librarySystem.totalUniqueBooksCheckedOutByUser(userG002));
    }
}