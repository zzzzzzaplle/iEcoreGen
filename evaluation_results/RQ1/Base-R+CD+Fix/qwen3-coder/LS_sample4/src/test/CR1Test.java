import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        // Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
        
        // Create the book
        Book javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        // Create user M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        // Create three checkout records for the same book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 23:59:59"));
        
        // Add checkouts to user
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        memberM001.addCheckOut(checkout3);
        
        // Add user and book to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getBooks().add(javaBook);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify the book has been checked out 3 times
        assertEquals("The book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: "Counting Checkouts for a Book with No Checkouts"
        
        // Create the book
        Book pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        // Add book to library system (no users or checkouts)
        librarySystem.getBooks().add(pythonBook);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify the book has been checked out 0 times
        assertEquals("The book should have been checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Test Case 3: "Counting Checkouts for a Book with One Checkout"
        
        // Create the book
        Book algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        // Create user M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        // Create one checkout record
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        
        // Add checkout to user
        memberM001.addCheckOut(checkout);
        
        // Add user and book to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getBooks().add(algorithmsBook);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify the book has been checked out 1 time
        assertEquals("The book should have been checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
        
        // Create the book
        Book dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        // Create user M001 (Member)
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        // Create user G001 (Guest)
        User guestG001 = new User();
        guestG001.setID("G001");
        guestG001.setType(UserType.GUEST);
        
        // Create user M002 (Member)
        User memberM002 = new User();
        memberM002.setID("M002");
        memberM002.setType(UserType.MEMBER);
        
        // Create checkout records for each user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 23:59:59"));
        
        // Add checkouts to respective users
        memberM001.addCheckOut(checkout1);
        guestG001.addCheckOut(checkout2);
        memberM002.addCheckOut(checkout3);
        
        // Add users and book to library system
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        users.add(guestG001);
        users.add(memberM002);
        librarySystem.setUsers(users);
        librarySystem.getBooks().add(dataStructuresBook);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify the book has been checked out 3 times
        assertEquals("The book should have been checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Test Case 5: "Counting Checkouts for a Book with Same User"
        
        // Create the book
        Book webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setISBN("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        
        // Create user M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        // Create two checkout records for the same user
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        
        // Add both checkouts to the same user
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        
        // Add user and book to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getBooks().add(webDevelopmentBook);
        
        // Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify the book has been checked out 2 times
        assertEquals("The book should have been checked out 2 times", 2, result);
    }
}