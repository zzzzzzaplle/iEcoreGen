import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        
        // Create books B001, B002, B003
        Book book1 = new Book();
        book1.setBarcode("B001");
        Book book2 = new Book();
        book2.setBarcode("B002");
        Book book3 = new Book();
        book3.setBarcode("B003");
        
        // Check out 3 different books with user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-03-15"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30"));
        
        userM001.addCheckOut(checkout1);
        userM001.addCheckOut(checkout2);
        userM001.addCheckOut(checkout3);
        
        // Check out 2 same book (B001) with user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        
        userM002.addCheckOut(checkout4);
        userM002.addCheckOut(checkout5);
        
        // Add users to library system
        librarySystem.getUsers().add(userM001);
        librarySystem.getUsers().add(userM002);
        
        // Calculate total unique books checked out
        int totalCheckoutsM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int totalCheckoutsM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Verify expected results
        assertEquals(3, totalCheckoutsM001);
        assertEquals(1, totalCheckoutsM002);
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
        
        // Create book B001
        Book book1 = new Book();
        book1.setBarcode("B001");
        
        // Check out 1 book with user G001
        CheckOut checkout = new CheckOut();
        checkout.setBook(book1);
        checkout.setStartDate(dateFormat.parse("2023-05-01"));
        checkout.setEndDate(dateFormat.parse("2023-05-10"));
        
        userG001.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(userG001);
        
        // Calculate total unique books checked out
        int totalCheckoutsG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Verify expected result
        assertEquals(1, totalCheckoutsG001);
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
        int totalCheckoutsM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Verify expected result
        assertEquals(0, totalCheckoutsM003);
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
        
        // Create books B001, B002, B003, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        Book book2 = new Book();
        book2.setBarcode("B002");
        Book book3 = new Book();
        book3.setBarcode("B003");
        Book book4 = new Book();
        book4.setBarcode("B004");
        
        // Check out 4 different books with user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-06-01"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-06-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01"));
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2023-06-01"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01"));
        
        userM004.addCheckOut(checkout1);
        userM004.addCheckOut(checkout2);
        userM004.addCheckOut(checkout3);
        userM004.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.getUsers().add(userM004);
        
        // Calculate total unique books checked out
        int totalCheckoutsM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Verify expected result
        assertEquals(4, totalCheckoutsM004);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForGuestUserWithMultipleSameBooks() throws ParseException {
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
        Book book4 = new Book();
        book4.setBarcode("B004");
        
        // Check out 2 same book (B001) with user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate(dateFormat.parse("2024-06-01"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01"));
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2024-07-02"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11"));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate(dateFormat.parse("2024-08-01"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01"));
        
        userG002.addCheckOut(checkout1);
        userG002.addCheckOut(checkout2);
        userG002.addCheckOut(checkout3);
        userG002.addCheckOut(checkout4);
        userG002.addCheckOut(checkout5);
        
        // Add user to library system
        librarySystem.getUsers().add(userG002);
        
        // Calculate total unique books checked out
        int totalCheckoutsG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Verify expected result
        assertEquals(2, totalCheckoutsG002);
    }
}