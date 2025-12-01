import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
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
        User member1 = new User();
        member1.setID("M001");
        member1.setName("Alice");
        member1.setEmail("alice@example.com");
        member1.setAddress("123 Main St");
        member1.setType(UserType.MEMBER);
        
        // Create Member user M002
        User member2 = new User();
        member2.setID("M002");
        member2.setName("Bob");
        member2.setEmail("bob@example.com");
        member2.setAddress("456 Elm St");
        member2.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setBarcode("B001");
        Book book2 = new Book();
        book2.setBarcode("B002");
        Book book3 = new Book();
        book3.setBarcode("B003");
        
        // Set up checkouts for M001 (3 different books)
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
        
        member1.getCheckOuts().add(checkout1);
        member1.getCheckOuts().add(checkout2);
        member1.getCheckOuts().add(checkout3);
        
        // Set up checkouts for M002 (2 same book - B001)
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        
        member2.getCheckOuts().add(checkout4);
        member2.getCheckOuts().add(checkout5);
        
        // Add users to library system
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(member2);
        
        // Calculate total unique checkouts
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(member1);
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(member2);
        
        // Assert results
        assertEquals(3, result1);
        assertEquals(1, result2);
    }
    
    @Test
    public void testCase2_calculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user G001
        User guest1 = new User();
        guest1.setID("G001");
        guest1.setName("Charlie");
        guest1.setEmail("charlie@example.com");
        guest1.setAddress("789 Pine St");
        guest1.setType(UserType.GUEST);
        
        // Create book
        Book book1 = new Book();
        book1.setBarcode("B001");
        
        // Set up checkout for G001 (1 book)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-10"));
        
        guest1.getCheckOuts().add(checkout1);
        
        // Add user to library system
        librarySystem.getUsers().add(guest1);
        
        // Calculate total unique checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest1);
        
        // Assert result
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        User member3 = new User();
        member3.setID("M003");
        member3.setName("Eve");
        member3.setEmail("eve@example.com");
        member3.setAddress("654 Maple St");
        member3.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.getUsers().add(member3);
        
        // Calculate total unique checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member3);
        
        // Assert result
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypes1() throws ParseException {
        // Create Member user M004
        User member4 = new User();
        member4.setID("M004");
        member4.setName("George");
        member4.setEmail("george@example.com");
        member4.setAddress("135 Cedar St");
        member4.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setBarcode("B001");
        Book book2 = new Book();
        book2.setBarcode("B002");
        Book book3 = new Book();
        book3.setBarcode("B003");
        Book book4 = new Book();
        book4.setBarcode("B004");
        
        // Set up checkouts for M004 (4 different books)
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
        
        member4.getCheckOuts().add(checkout1);
        member4.getCheckOuts().add(checkout2);
        member4.getCheckOuts().add(checkout3);
        member4.getCheckOuts().add(checkout4);
        
        // Add user to library system
        librarySystem.getUsers().add(member4);
        
        // Calculate total unique checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member4);
        
        // Assert result
        assertEquals(4, result);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypes2() throws ParseException {
        // Create Guest user G002
        User guest2 = new User();
        guest2.setID("G002");
        guest2.setName("Hannah");
        guest2.setEmail("hannah@example.com");
        guest2.setAddress("246 Spruce St");
        guest2.setType(UserType.GUEST);
        
        // Create books
        Book book1 = new Book();
        book1.setBarcode("B001");
        Book book4 = new Book();
        book4.setBarcode("B004");
        
        // Set up checkouts for G002 (2 same book B001, 3 same book B004)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        
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
        
        guest2.getCheckOuts().add(checkout1);
        guest2.getCheckOuts().add(checkout2);
        guest2.getCheckOuts().add(checkout3);
        guest2.getCheckOuts().add(checkout4);
        guest2.getCheckOuts().add(checkout5);
        
        // Add user to library system
        librarySystem.getUsers().add(guest2);
        
        // Calculate total unique checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest2);
        
        // Assert result
        assertEquals(2, result);
    }
}