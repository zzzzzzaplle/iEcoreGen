import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void testCase1_SingleUserBorrowingCalculationForMember() throws Exception {
        // Create a MEMBER user with ID: U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        book1.setBarcode("B001");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        book2.setBarcode("B002");
        book2.setNumberOfPages(300);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-03-05 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10 23:59:59"));
        user.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate(dateFormat.parse("2023-05-20 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25 23:59:59"));
        user.addCheckOut(checkout3);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() throws Exception {
        // Create a GUEST user with ID: U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        book3.setBarcode("B003");
        book3.setNumberOfPages(250);
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        book4.setBarcode("B004");
        book4.setNumberOfPages(350);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate(dateFormat.parse("2023-06-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate(dateFormat.parse("2023-06-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25 23:59:59"));
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-06-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() throws Exception {
        // Create a MEMBER user with ID: U003
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings (no checkouts recorded)
        Date startDate = dateFormat.parse("2023-07-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() throws Exception {
        // Create a MEMBER user with ID: U004
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        book5.setBarcode("B005");
        book5.setNumberOfPages(400);
        
        // Record CheckOuts (same book borrowed twice)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10 23:59:59"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25 23:59:59"));
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for first period only
        Date startDate = dateFormat.parse("2023-08-01 00:00:00");
        Date endDate = dateFormat.parse("2023-08-10 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify the result (should count as 1 unique book despite multiple checkouts)
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() throws Exception {
        // Create a MEMBER user with ID: U005
        User user1 = new User();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006
        User user2 = new User();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        book6.setBarcode("B006");
        book6.setNumberOfPages(280);
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        book7.setBarcode("B007");
        book7.setNumberOfPages(320);
        
        // Record CheckOuts for user U005
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10 23:59:59"));
        user1.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20 23:59:59"));
        user1.addCheckOut(checkout2);
        
        // Record CheckOuts for user U006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate(dateFormat.parse("2023-09-05 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15 23:59:59"));
        user2.addCheckOut(checkout3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        
        // Execute the method to count unique book borrowings for user U005
        Date startDate = dateFormat.parse("2023-09-01 00:00:00");
        Date endDate = dateFormat.parse("2023-09-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startDate, endDate);
        
        // Verify the result (user U005 borrowed 2 unique books)
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}