import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR4Test {
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
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

    @Before
    public void setUp() throws ParseException {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
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
    }

    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws ParseException {
        // Create Member user M001
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);
        
        // Create Member user M002
        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);
        
        // Check out 3 different books with user M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        memberUser1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10 00:00:00"));
        memberUser1.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30 00:00:00"));
        memberUser1.addCheckOut(checkout3);
        
        // Check out 2 same books (B001) with user M002
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book1);
        checkout4.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12 00:00:00"));
        memberUser2.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book1);
        checkout5.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20 00:00:00"));
        memberUser2.addCheckOut(checkout5);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(memberUser1);
        users.add(memberUser2);
        librarySystem.setUsers(users);
        
        // Calculate and verify total unique checkouts for M001
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        assertEquals("Member M001 should have 3 unique book checkouts", 3, result1);
        
        // Calculate and verify total unique checkouts for M002
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        assertEquals("Member M002 should have 1 unique book checkout", 1, result2);
    }

    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user G001
        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);
        
        // Check out 1 book with user G001
        CheckOut checkout = new CheckOut();
        checkout.setBook(book1);
        checkout.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-05-10 00:00:00"));
        guestUser1.addCheckOut(checkout);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(guestUser1);
        librarySystem.setUsers(users);
        
        // Calculate and verify total unique checkouts for G001
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        assertEquals("Guest G001 should have 1 unique book checkout", 1, result);
    }

    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(memberUser3);
        librarySystem.setUsers(users);
        
        // Calculate and verify total unique checkouts for M003
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        assertEquals("Member M003 with no checkouts should return 0", 0, result);
    }

    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes1() throws ParseException {
        // Create Member user M004
        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);
        
        // Check out 4 different books with user M004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        memberUser4.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        memberUser4.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        memberUser4.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        memberUser4.addCheckOut(checkout4);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(memberUser4);
        librarySystem.setUsers(users);
        
        // Calculate and verify total unique checkouts for M004
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        assertEquals("Member M004 should have 4 unique book checkouts", 4, result);
    }

    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes2() throws ParseException {
        // Create Guest user G002
        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);
        
        // Check out 2 same books (B001) with user G002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01 00:00:00"));
        guestUser2.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01 00:00:00"));
        guestUser2.addCheckOut(checkout2);
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book4);
        checkout3.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01 00:00:00"));
        guestUser2.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11 00:00:00"));
        guestUser2.addCheckOut(checkout4);
        
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(book4);
        checkout5.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01 00:00:00"));
        guestUser2.addCheckOut(checkout5);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(guestUser2);
        librarySystem.setUsers(users);
        
        // Calculate and verify total unique checkouts for G002
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, result);
    }
}