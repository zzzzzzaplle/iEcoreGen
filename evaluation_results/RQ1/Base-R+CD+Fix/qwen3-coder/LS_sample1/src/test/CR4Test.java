import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws ParseException {
        // Create Member users
        User member1 = new User();
        member1.setID("M001");
        member1.setName("Alice");
        member1.setEmail("alice@example.com");
        member1.setAddress("123 Main St");
        member1.setType(UserType.MEMBER);
        
        User member2 = new User();
        member2.setID("M002");
        member2.setName("Bob");
        member2.setEmail("bob@example.com");
        member2.setAddress("456 Elm St");
        member2.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(300);
        
        // Create checkouts for member1 (3 different books)
        CheckOut checkout1_m1 = new CheckOut();
        checkout1_m1.setBook(book1);
        checkout1_m1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1_m1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        member1.addCheckOut(checkout1_m1);
        
        CheckOut checkout2_m1 = new CheckOut();
        checkout2_m1.setBook(book2);
        checkout2_m1.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2_m1.setEndDate(dateFormat.parse("2023-02-10 00:00:00"));
        member1.addCheckOut(checkout2_m1);
        
        CheckOut checkout3_m1 = new CheckOut();
        checkout3_m1.setBook(book3);
        checkout3_m1.setStartDate(dateFormat.parse("2023-03-15 00:00:00"));
        checkout3_m1.setEndDate(dateFormat.parse("2023-03-30 00:00:00"));
        member1.addCheckOut(checkout3_m1);
        
        // Create checkouts for member2 (2 same book B001)
        CheckOut checkout1_m2 = new CheckOut();
        checkout1_m2.setBook(book1);
        checkout1_m2.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout1_m2.setEndDate(dateFormat.parse("2023-04-12 00:00:00"));
        member2.addCheckOut(checkout1_m2);
        
        CheckOut checkout2_m2 = new CheckOut();
        checkout2_m2.setBook(book1);
        checkout2_m2.setStartDate(dateFormat.parse("2023-04-15 00:00:00"));
        checkout2_m2.setEndDate(dateFormat.parse("2023-04-20 00:00:00"));
        member2.addCheckOut(checkout2_m2);
        
        // Add users to library system
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(member2);
        
        // Test total unique books for member1
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(member1);
        assertEquals("Member M001 should have 3 unique books", 3, result1);
        
        // Test total unique books for member2
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(member2);
        assertEquals("Member M002 should have 1 unique book", 1, result2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user
        User guest = new User();
        guest.setID("G001");
        guest.setName("Charlie");
        guest.setEmail("charlie@example.com");
        guest.setAddress("789 Pine St");
        guest.setType(UserType.GUEST);
        
        // Create book
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        // Create checkout for guest (1 book)
        CheckOut checkout = new CheckOut();
        checkout.setBook(book1);
        checkout.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-05-10 00:00:00"));
        guest.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(guest);
        
        // Test total unique books for guest
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest);
        assertEquals("Guest G001 should have 1 unique book", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        User member = new User();
        member.setID("M003");
        member.setName("Eve");
        member.setEmail("eve@example.com");
        member.setAddress("654 Maple St");
        member.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.getUsers().add(member);
        
        // Test total unique books for member with no checkouts
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member);
        assertEquals("Member M003 with no checkouts should have 0 unique books", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMemberWithMultipleBooks() throws ParseException {
        // Create Member user
        User member = new User();
        member.setID("M004");
        member.setName("George");
        member.setEmail("george@example.com");
        member.setAddress("135 Cedar St");
        member.setType(UserType.MEMBER);
        
        // Create 4 different books
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setISBN("ISBN002");
        book2.setNumberOfPages(200);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setISBN("ISBN003");
        book3.setNumberOfPages(300);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(400);
        
        // Create checkouts for all 4 different books
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        member.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        member.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        member.addCheckOut(checkout3);
        
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(book4);
        checkout4.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01 00:00:00"));
        member.addCheckOut(checkout4);
        
        // Add user to library system
        librarySystem.getUsers().add(member);
        
        // Test total unique books for member
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member);
        assertEquals("Member M004 should have 4 unique books", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForGuestWithDuplicateBooks() throws ParseException {
        // Create Guest user
        User guest = new User();
        guest.setID("G002");
        guest.setName("Hannah");
        guest.setEmail("hannah@example.com");
        guest.setAddress("246 Spruce St");
        guest.setType(UserType.GUEST);
        
        // Create books
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setISBN("ISBN001");
        book1.setNumberOfPages(100);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setISBN("ISBN004");
        book4.setNumberOfPages(400);
        
        // Create checkouts for guest (2 same book B001)
        CheckOut checkout1_g = new CheckOut();
        checkout1_g.setBook(book1);
        checkout1_g.setStartDate(dateFormat.parse("2023-07-15 00:00:00"));
        checkout1_g.setEndDate(dateFormat.parse("2023-08-01 00:00:00"));
        guest.addCheckOut(checkout1_g);
        
        CheckOut checkout2_g = new CheckOut();
        checkout2_g.setBook(book1);
        checkout2_g.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2_g.setEndDate(dateFormat.parse("2023-09-01 00:00:00"));
        guest.addCheckOut(checkout2_g);
        
        // Create checkouts for guest (3 same book B004)
        CheckOut checkout3_g = new CheckOut();
        checkout3_g.setBook(book4);
        checkout3_g.setStartDate(dateFormat.parse("2024-06-01 00:00:00"));
        checkout3_g.setEndDate(dateFormat.parse("2024-07-01 00:00:00"));
        guest.addCheckOut(checkout3_g);
        
        CheckOut checkout4_g = new CheckOut();
        checkout4_g.setBook(book4);
        checkout4_g.setStartDate(dateFormat.parse("2024-07-02 00:00:00"));
        checkout4_g.setEndDate(dateFormat.parse("2024-07-11 00:00:00"));
        guest.addCheckOut(checkout4_g);
        
        CheckOut checkout5_g = new CheckOut();
        checkout5_g.setBook(book4);
        checkout5_g.setStartDate(dateFormat.parse("2024-08-01 00:00:00"));
        checkout5_g.setEndDate(dateFormat.parse("2024-09-01 00:00:00"));
        guest.addCheckOut(checkout5_g);
        
        // Add user to library system
        librarySystem.getUsers().add(guest);
        
        // Test total unique books for guest (should be 2 unique books: B001 and B004)
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest);
        assertEquals("Guest G002 should have 2 unique books", 2, result);
    }
}