import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    private LibrarySystem librarySystem;
    private User memberUser1;
    private User memberUser2;
    private User guestUser1;
    private User memberUser3;
    private User memberUser4;
    private User guestUser2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Create books
        book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(200);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(300);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(400);
        
        // Add books to library system
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        librarySystem.setBooks(books);
        
        // Create users
        List<User> users = new ArrayList<>();
        
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);
        users.add(memberUser1);
        
        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);
        users.add(memberUser2);
        
        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);
        users.add(guestUser1);
        
        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);
        users.add(memberUser3);
        
        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);
        users.add(memberUser4);
        
        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);
        users.add(guestUser2);
        
        librarySystem.setUsers(users);
    }

    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws Exception {
        // Set up checkouts for member user M001
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
        
        List<CheckOut> checkoutsM001 = new ArrayList<>();
        checkoutsM001.add(checkout1);
        checkoutsM001.add(checkout2);
        checkoutsM001.add(checkout3);
        memberUser1.setCheckOuts(checkoutsM001);
        
        // Set up checkouts for member user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        
        List<CheckOut> checkoutsM002 = new ArrayList<>();
        checkoutsM002.add(checkout4);
        checkoutsM002.add(checkout5);
        memberUser2.setCheckOuts(checkoutsM002);
        
        // Calculate total unique books checked out for M001 and M002
        int resultM001 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        int resultM002 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        
        // Verify expected results
        assertEquals("Member M001 should have 3 unique books checked out", 3, resultM001);
        assertEquals("Member M002 should have 1 unique book checked out", 1, resultM002);
    }

    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Set up checkouts for guest user G001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-10"));
        
        List<CheckOut> checkoutsG001 = new ArrayList<>();
        checkoutsG001.add(checkout1);
        guestUser1.setCheckOuts(checkoutsG001);
        
        // Calculate total unique books checked out for G001
        int resultG001 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        
        // Verify expected result
        assertEquals("Guest G001 should have 1 unique book checked out", 1, resultG001);
    }

    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // User M003 has no checkouts (empty list by default)
        int resultM003 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        
        // Verify expected result
        assertEquals("Member M003 with no checkouts should return 0", 0, resultM003);
    }

    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() throws Exception {
        // Set up checkouts for member user M004
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
        
        List<CheckOut> checkoutsM004 = new ArrayList<>();
        checkoutsM004.add(checkout1);
        checkoutsM004.add(checkout2);
        checkoutsM004.add(checkout3);
        checkoutsM004.add(checkout4);
        memberUser4.setCheckOuts(checkoutsM004);
        
        // Calculate total unique books checked out for M004
        int resultM004 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        
        // Verify expected result
        assertEquals("Member M004 should have 4 unique books checked out", 4, resultM004);
    }

    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() throws Exception {
        // Set up checkouts for guest user G002
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
        
        List<CheckOut> checkoutsG002 = new ArrayList<>();
        checkoutsG002.add(checkout1);
        checkoutsG002.add(checkout2);
        checkoutsG002.add(checkout3);
        checkoutsG002.add(checkout4);
        checkoutsG002.add(checkout5);
        guestUser2.setCheckOuts(checkoutsG002);
        
        // Calculate total unique books checked out for G002
        int resultG002 = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        
        // Verify expected result
        assertEquals("Guest G002 should have 2 unique books checked out", 2, resultG002);
    }
}