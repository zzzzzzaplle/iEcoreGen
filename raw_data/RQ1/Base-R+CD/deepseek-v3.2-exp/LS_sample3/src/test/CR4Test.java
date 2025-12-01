import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {

    private LibrarySystem librarySystem;
    private User memberUser1;
    private User memberUser2;
    private User guestUser1;
    private User memberUser3;
    private User memberUser4;
    private User guestUser2;

    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();

        // Create books for testing
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

        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);

        // Create users
        memberUser1 = new User();
        memberUser1.setID("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        memberUser1.setType(UserType.MEMBER);

        memberUser2 = new User();
        memberUser2.setID("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        memberUser2.setType(UserType.MEMBER);

        guestUser1 = new User();
        guestUser1.setID("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        guestUser1.setType(UserType.GUEST);

        memberUser3 = new User();
        memberUser3.setID("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        memberUser3.setType(UserType.MEMBER);

        memberUser4 = new User();
        memberUser4.setID("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        memberUser4.setType(UserType.MEMBER);

        guestUser2 = new User();
        guestUser2.setID("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        guestUser2.setType(UserType.GUEST);

        // Add users to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        librarySystem.addUser(guestUser1);
        librarySystem.addUser(memberUser3);
        librarySystem.addUser(memberUser4);
        librarySystem.addUser(guestUser2);
    }

    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create checkout records for member user M001 with 3 different books
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-01-01");
        checkout1.setEndDate("2023-01-15");
        checkout1.setBook(librarySystem.getBooks().get(0)); // B001

        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-02-01");
        checkout2.setEndDate("2023-02-10");
        checkout2.setBook(librarySystem.getBooks().get(1)); // B002

        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate("2023-03-15");
        checkout3.setEndDate("2023-03-30");
        checkout3.setBook(librarySystem.getBooks().get(2)); // B003

        memberUser1.addCheckOut(checkout1);
        memberUser1.addCheckOut(checkout2);
        memberUser1.addCheckOut(checkout3);

        // Create checkout records for member user M002 with 2 same books (B001)
        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate("2023-04-01");
        checkout4.setEndDate("2023-04-12");
        checkout4.setBook(librarySystem.getBooks().get(0)); // B001

        CheckOut checkout5 = new CheckOut();
        checkout5.setStartDate("2023-04-15");
        checkout5.setEndDate("2023-04-20");
        checkout5.setBook(librarySystem.getBooks().get(0)); // B001

        memberUser2.addCheckOut(checkout4);
        memberUser2.addCheckOut(checkout5);

        // Test total unique books for M001
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser1);
        assertEquals("Member M001 should have 3 unique books", 3, result1);

        // Test total unique books for M002
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser2);
        assertEquals("Member M002 should have 1 unique book", 1, result2);
    }

    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create checkout record for guest user G001 with 1 book
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-05-01");
        checkout1.setEndDate("2023-05-10");
        checkout1.setBook(librarySystem.getBooks().get(0)); // B001

        guestUser1.addCheckOut(checkout1);

        // Test total unique books for G001
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser1);
        assertEquals("Guest G001 should have 1 unique book", 1, result);
    }

    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // User M003 has no checkouts (already set up in setUp method)

        // Test total unique books for M003 (should be 0)
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser3);
        assertEquals("Member M003 with no checkouts should have 0 unique books", 0, result);
    }

    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberUserWithMultipleBooks() {
        // Create checkout records for member user M004 with 4 different books
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-06-01");
        checkout1.setEndDate("2023-07-01");
        checkout1.setBook(librarySystem.getBooks().get(0)); // B001

        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-06-01");
        checkout2.setEndDate("2023-07-01");
        checkout2.setBook(librarySystem.getBooks().get(1)); // B002

        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate("2023-06-01");
        checkout3.setEndDate("2023-07-01");
        checkout3.setBook(librarySystem.getBooks().get(2)); // B003

        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate("2023-06-01");
        checkout4.setEndDate("2023-07-01");
        checkout4.setBook(librarySystem.getBooks().get(3)); // B004

        memberUser4.addCheckOut(checkout1);
        memberUser4.addCheckOut(checkout2);
        memberUser4.addCheckOut(checkout3);
        memberUser4.addCheckOut(checkout4);

        // Test total unique books for M004
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(memberUser4);
        assertEquals("Member M004 should have 4 unique books", 4, result);
    }

    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestUserWithDuplicateBooks() {
        // Create checkout records for guest user G002 with 2 same books (B001) and 3
        // same books (B004)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-07-15");
        checkout1.setEndDate("2023-08-01");
        checkout1.setBook(librarySystem.getBooks().get(0)); // B001

        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-09-01");
        checkout2.setBook(librarySystem.getBooks().get(0)); // B001

        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate("2024-06-01");
        checkout3.setEndDate("2024-07-01");
        checkout3.setBook(librarySystem.getBooks().get(3)); // B004

        CheckOut checkout4 = new CheckOut();
        checkout4.setStartDate("2024-07-02");
        checkout4.setEndDate("2024-07-11");
        checkout4.setBook(librarySystem.getBooks().get(3)); // B004

        CheckOut checkout5 = new CheckOut();
        checkout5.setStartDate("2024-08-01");
        checkout5.setEndDate("2024-09-01");
        checkout5.setBook(librarySystem.getBooks().get(3)); // B004

        guestUser2.addCheckOut(checkout1);
        guestUser2.addCheckOut(checkout2);
        guestUser2.addCheckOut(checkout3);
        guestUser2.addCheckOut(checkout4);
        guestUser2.addCheckOut(checkout5);

        // Test total unique books for G002 (should count B001 and B004 only once each)
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guestUser2);
        assertEquals("Guest G002 should have 2 unique books", 2, result);
    }

}