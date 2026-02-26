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
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        
        // Check out 3 different books for user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        userM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10 00:00:00"));
        userM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30 00:00:00"));
        userM001.addCheckOut(checkout3);
        
        // Check out 2 same books (B001) for user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12 00:00:00"));
        userM002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20 00:00:00"));
        userM002.addCheckOut(checkout5);
        
        // Add users and books to library system
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Calculate total unique checkouts for each user
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Verify expected results
        assertEquals("M001 should have 3 unique book checkouts", 3, resultM001);
        assertEquals("M002 should have 1 unique book checkout", 1, resultM002);
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
        
        // Check out 1 book for user G001
        CheckOut checkout = new CheckOut();
        checkout.setBook(book1);
        checkout.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-05-10 00:00:00"));
        userG001.addCheckOut(checkout);
        
        // Add user and book to library system
        librarySystem.addUser(userG001);
        librarySystem.addBook(book1);
        
        // Calculate total unique checkouts for guest user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Verify expected result
        assertEquals("G001 should have 1 unique book checkout", 1, result);
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
        
        // Add user to library system (no books or checkouts added)
        librarySystem.addUser(userM003);
        
        // Calculate total unique checkouts for user with no checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Verify expected result
        assertEquals("M003 should have 0 unique book checkouts", 0, result);
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
        
        // Check out 4 different books for user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        userM004.addCheckOut(checkout4);
        
        // Add user and books to library system
        librarySystem.addUser(userM004);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Calculate total unique checkouts for user
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Verify expected result
        assertEquals("M004 should have 4 unique book checkouts", 4, result);
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
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        
        // Check out 2 same books (B001) for user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01 00:00:00"));
        userG002.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01 00:00:00"));
        userG002.addCheckOut(checkout2);
        
        // Check out B004 3 times for user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01 00:00:00"));
        userG002.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11 00:00:00"));
        userG002.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01 00:00:00"));
        userG002.addCheckOut(checkout5);
        
        // Add user and books to library system
        librarySystem.addUser(userG002);
        librarySystem.addBook(book1);
        librarySystem.addBook(book4);
        
        // Calculate total unique checkouts for user (should count only unique books)
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Verify expected result
        assertEquals("G002 should have 2 unique book checkouts", 2, result);
    }
}