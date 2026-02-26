import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // SetUp: Create book and users with multiple checkouts
        Book javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // First checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        member1.addCheckOut(checkout1);
        
        // Second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 23:59:59"));
        member1.addCheckOut(checkout2);
        
        // Third checkout
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 23:59:59"));
        member1.addCheckOut(checkout3);
        
        librarySystem.getUsers().add(member1);
        
        // Test: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals("Book with multiple checkouts should return 3", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create book but no users check it out
        Book pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        // Test: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Assert: The book has been checked out 0 time
        assertEquals("Book with no checkouts should return 0", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws ParseException {
        // SetUp: Create book and one checkout
        Book algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        member1.addCheckOut(checkout);
        
        librarySystem.getUsers().add(member1);
        
        // Test: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Assert: The book has been checked out 1 time
        assertEquals("Book with one checkout should return 1", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // SetUp: Create book and checkouts by different users
        Book dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        // Member M001 checkout
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 23:59:59"));
        member1.addCheckOut(checkout1);
        
        // Guest G001 checkout
        User guest1 = new User();
        guest1.setID("G001");
        guest1.setType(UserType.GUEST);
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        guest1.addCheckOut(checkout2);
        
        // Member M002 checkout
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 23:59:59"));
        member2.addCheckOut(checkout3);
        
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(guest1);
        librarySystem.getUsers().add(member2);
        
        // Test: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Assert: The book has been checked out 3 times
        assertEquals("Book with mixed member and guest checkouts should return 3", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws ParseException {
        // SetUp: Create book and multiple checkouts by same user
        Book webDevBook = new Book();
        webDevBook.setTitle("Web Development");
        webDevBook.setBarcode("131415");
        webDevBook.setISBN("978-2-36-547891-0");
        webDevBook.setNumberOfPages(450);
        
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // First checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        member1.addCheckOut(checkout1);
        
        // Second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        member1.addCheckOut(checkout2);
        
        librarySystem.getUsers().add(member1);
        
        // Test: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckOuts(webDevBook);
        
        // Assert: The book has been checked out 2 times
        assertEquals("Book with multiple checkouts by same user should return 2", 2, result);
    }
}