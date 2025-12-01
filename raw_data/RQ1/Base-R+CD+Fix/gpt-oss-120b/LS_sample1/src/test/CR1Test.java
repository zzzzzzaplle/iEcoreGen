import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // Create book
        Book javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        // Create user
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Add checkouts for the same book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 00:00:00"));
        memberM001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 00:00:00"));
        memberM001.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(memberM001);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify the book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Create book
        Book pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        // No users check out this book
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify the book has been checked out 0 times
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create book
        Book algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        // Create user
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Add one checkout for the book
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 00:00:00"));
        memberM001.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.addUser(memberM001);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify the book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create book
        Book dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        // Create member user M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Create guest user G001
        User guestG001 = new User();
        guestG001.setID("G001");
        guestG001.setName("Guest One");
        guestG001.setType(UserType.GUEST);
        
        // Create member user M002
        User memberM002 = new User();
        memberM002.setID("M002");
        memberM002.setName("Member Two");
        memberM002.setType(UserType.MEMBER);
        
        // Add checkouts by different users for the same book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        guestG001.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 00:00:00"));
        memberM002.addCheckOut(checkout3);
        
        // Add all users to library system
        librarySystem.addUser(memberM001);
        librarySystem.addUser(guestG001);
        librarySystem.addUser(memberM002);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify the book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create book
        Book webDevBook = new Book();
        webDevBook.setTitle("Web Development");
        webDevBook.setBarcode("131415");
        webDevBook.setISBN("978-2-36-547891-0");
        webDevBook.setNumberOfPages(450);
        
        // Create user
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Add multiple checkouts by the same user for the same book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        memberM001.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.addUser(memberM001);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(webDevBook);
        
        // Verify the book has been checked out 2 times
        assertEquals(2, result);
    }
}