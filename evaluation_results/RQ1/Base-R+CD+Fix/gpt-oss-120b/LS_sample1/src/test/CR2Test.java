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
        // SetUp: Create a MEMBER user
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setBarcode("BOOK001");
        book1.setTitle("Book 1");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setBarcode("BOOK002");
        book2.setTitle("Book 2");
        book2.setNumberOfPages(300);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-03-05 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate(dateFormat.parse("2023-05-20 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25 23:59:59"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() throws Exception {
        // SetUp: Create a GUEST user
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setBarcode("BOOK003");
        book3.setTitle("Book 3");
        book3.setNumberOfPages(250);
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setBarcode("BOOK004");
        book4.setTitle("Book 4");
        book4.setNumberOfPages(350);
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate(dateFormat.parse("2023-06-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate(dateFormat.parse("2023-06-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25 23:59:59"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-06-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() throws Exception {
        // SetUp: Create a MEMBER user with no checkouts
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Execute the method
        Date startDate = dateFormat.parse("2023-07-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() throws Exception {
        // SetUp: Create a MEMBER user
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setBarcode("BOOK005");
        book5.setTitle("Book 5");
        book5.setNumberOfPages(400);
        
        // Record CheckOuts (same book, different periods)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25 23:59:59"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Execute the method for first period only
        Date startDate = dateFormat.parse("2023-08-01 00:00:00");
        Date endDate = dateFormat.parse("2023-08-10 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() throws Exception {
        // SetUp: Create MEMBER user U005
        User user5 = new User();
        user5.setID("U005");
        user5.setName("Eve");
        user5.setEmail("eve@example.com");
        user5.setAddress("202 Birch St.");
        user5.setType(UserType.MEMBER);
        
        // SetUp: Create GUEST user U006
        User user6 = new User();
        user6.setID("U006");
        user6.setName("Frank");
        user6.setEmail("frank@example.com");
        user6.setAddress("303 Cedar St.");
        user6.setType(UserType.GUEST);
        
        // Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setBarcode("BOOK006");
        book6.setTitle("Book 6");
        book6.setNumberOfPages(280);
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setBarcode("BOOK007");
        book7.setTitle("Book 7");
        book7.setNumberOfPages(320);
        
        // Record CheckOuts for U005
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20 23:59:59"));
        
        // Record CheckOuts for U006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate(dateFormat.parse("2023-09-05 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15 23:59:59"));
        
        // Add checkouts to respective users
        user5.addCheckOut(checkout1);
        user5.addCheckOut(checkout2);
        user6.addCheckOut(checkout3);
        
        // Add users to library system
        librarySystem.addUser(user5);
        librarySystem.addUser(user6);
        
        // Execute the method for user U005 only
        Date startDate = dateFormat.parse("2023-09-01 00:00:00");
        Date endDate = dateFormat.parse("2023-09-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user5, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}