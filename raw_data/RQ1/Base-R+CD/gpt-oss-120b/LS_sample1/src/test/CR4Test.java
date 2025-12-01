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
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        
        // Add users and books to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        librarySystem.addBook(bookB001);
        librarySystem.addBook(bookB002);
        librarySystem.addBook(bookB003);
        
        // Check out 3 different books with user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        userM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10"));
        userM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(dateFormat.parse("2023-03-15"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30"));
        userM001.addCheckOut(checkout3);
        
        // Check out 2 same book (B001) with user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB001);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        userM002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB001);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        userM002.addCheckOut(checkout5);
        
        // Verify results
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
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
        bookB001.setTitle("Book 1");
        
        // Add user and book to library system
        librarySystem.addUser(userG001);
        librarySystem.addBook(bookB001);
        
        // Check out 1 book with user G001
        CheckOut checkout = new CheckOut();
        checkout.setBook(bookB001);
        checkout.setStartDate(dateFormat.parse("2023-05-01"));
        checkout.setEndDate(dateFormat.parse("2023-05-10"));
        userG001.addCheckOut(checkout);
        
        // Verify result
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
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
        librarySystem.addUser(userM003);
        
        // Verify result
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
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
        bookB001.setTitle("Book 1");
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        
        // Add user and books to library system
        librarySystem.addUser(userM004);
        librarySystem.addBook(bookB001);
        librarySystem.addBook(bookB002);
        librarySystem.addBook(bookB003);
        librarySystem.addBook(bookB004);
        
        // Check out 4 different books with user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-06-01"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(dateFormat.parse("2023-06-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2023-06-01"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01"));
        userM004.addCheckOut(checkout4);
        
        // Verify result
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
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
        bookB001.setTitle("Book 1");
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        
        // Add user and books to library system
        librarySystem.addUser(userG002);
        librarySystem.addBook(bookB001);
        librarySystem.addBook(bookB004);
        
        // Check out 2 same book (B001) with user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        userG002.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB001);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        userG002.addCheckOut(checkout2);
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB004);
        checkout3.setStartDate(dateFormat.parse("2024-06-01"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01"));
        userG002.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2024-07-02"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11"));
        userG002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB004);
        checkout5.setStartDate(dateFormat.parse("2024-08-01"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01"));
        userG002.addCheckOut(checkout5);
        
        // Verify result
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        assertEquals(2, resultG002);
    }
}