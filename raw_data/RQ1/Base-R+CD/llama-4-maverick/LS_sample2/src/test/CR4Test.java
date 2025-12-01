import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        bookB001.setNumberOfPages(100);
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setNumberOfPages(200);
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setNumberOfPages(300);
        
        // Set up checkouts for user M001 (3 different books)
        List<CheckOut> checkOutsM001 = new ArrayList<>();
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        checkOutsM001.add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10"));
        checkOutsM001.add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(dateFormat.parse("2023-03-15"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30"));
        checkOutsM001.add(checkout3);
        
        userM001.setCheckOuts(checkOutsM001);
        
        // Set up checkouts for user M002 (2 same book)
        List<CheckOut> checkOutsM002 = new ArrayList<>();
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB001);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        checkOutsM002.add(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB001);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        checkOutsM002.add(checkout5);
        
        userM002.setCheckOuts(checkOutsM002);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(userM001);
        users.add(userM002);
        librarySystem.setUsers(users);
        
        // Test the calculation
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
        bookB001.setNumberOfPages(100);
        
        // Set up checkout for user G001 (1 book)
        List<CheckOut> checkOutsG001 = new ArrayList<>();
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-10"));
        checkOutsG001.add(checkout1);
        
        userG001.setCheckOuts(checkOutsG001);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(userG001);
        librarySystem.setUsers(users);
        
        // Test the calculation
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
        userM003.setCheckOuts(new ArrayList<>()); // No checkouts
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(userM003);
        librarySystem.setUsers(users);
        
        // Test the calculation
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        assertEquals(0, resultM003);
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
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setNumberOfPages(100);
        
        Book bookB002 = new Book();
        bookB002.setBarcode("B002");
        bookB002.setNumberOfPages(200);
        
        Book bookB003 = new Book();
        bookB003.setBarcode("B003");
        bookB003.setNumberOfPages(300);
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setNumberOfPages(400);
        
        // Set up checkouts for user M004 (4 different books)
        List<CheckOut> checkOutsM004 = new ArrayList<>();
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-06-01"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01"));
        checkOutsM004.add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01"));
        checkOutsM004.add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(dateFormat.parse("2023-06-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01"));
        checkOutsM004.add(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2023-06-01"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01"));
        checkOutsM004.add(checkout4);
        
        userM004.setCheckOuts(checkOutsM004);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(userM004);
        librarySystem.setUsers(users);
        
        // Test the calculation
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        assertEquals(4, resultM004);
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
        
        // Create books
        Book bookB001 = new Book();
        bookB001.setBarcode("B001");
        bookB001.setNumberOfPages(100);
        
        Book bookB004 = new Book();
        bookB004.setBarcode("B004");
        bookB004.setNumberOfPages(400);
        
        // Set up checkouts for user G002 (2 same book B001 and 3 same book B004)
        List<CheckOut> checkOutsG002 = new ArrayList<>();
        
        // Check out B001 twice
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        checkOutsG002.add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(bookB001);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        checkOutsG002.add(checkout2);
        
        // Check out B004 three times
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(bookB004);
        checkout3.setStartDate(dateFormat.parse("2024-06-01"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01"));
        checkOutsG002.add(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(dateFormat.parse("2024-07-02"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11"));
        checkOutsG002.add(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(bookB004);
        checkout5.setStartDate(dateFormat.parse("2024-08-01"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01"));
        checkOutsG002.add(checkout5);
        
        userG002.setCheckOuts(checkOutsG002);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(userG002);
        librarySystem.setUsers(users);
        
        // Test the calculation
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        assertEquals(2, resultG002);
    }
}