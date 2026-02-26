import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleUserMemberBorrowingCalculation() throws ParseException {
        // Create a MEMBER user
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-03-05 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10 00:00:00"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate(dateFormat.parse("2023-05-20 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25 00:00:00"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserGuestBorrowingCalculation() throws ParseException {
        // Create a GUEST user
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate(dateFormat.parse("2023-06-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate(dateFormat.parse("2023-06-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25 00:00:00"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-06-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with no checkouts
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-07-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25 00:00:00"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method for the first period
        Date startDate = dateFormat.parse("2023-08-01 00:00:00");
        Date endDate = dateFormat.parse("2023-08-10 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create first user (MEMBER)
        User user1 = new User();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create second user (GUEST)
        User user2 = new User();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        
        // Record CheckOuts for user1 (Eve)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20 00:00:00"));
        
        user1.addCheckOut(checkout1);
        user1.addCheckOut(checkout2);
        
        // Record CheckOuts for user2 (Frank)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate(dateFormat.parse("2023-09-05 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15 00:00:00"));
        
        user2.addCheckOut(checkout3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        
        // Execute the method for user1 (Eve)
        Date startDate = dateFormat.parse("2023-09-01 00:00:00");
        Date endDate = dateFormat.parse("2023-09-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}