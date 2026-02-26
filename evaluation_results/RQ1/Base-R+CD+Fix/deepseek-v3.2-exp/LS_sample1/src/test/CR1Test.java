import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private User memberM001;
    private User memberM002;
    private User guestG001;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create books
        javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setISBN("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        
        // Create users
        memberM001 = new User();
        memberM001.setName("Member M001");
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        memberM001.setCheckOuts(new ArrayList<>());
        
        memberM002 = new User();
        memberM002.setName("Member M002");
        memberM002.setID("M002");
        memberM002.setType(UserType.MEMBER);
        memberM002.setCheckOuts(new ArrayList<>());
        
        guestG001 = new User();
        guestG001.setName("Guest G001");
        guestG001.setID("G001");
        guestG001.setType(UserType.GUEST);
        guestG001.setCheckOuts(new ArrayList<>());
        
        // Add books to library system
        List<Book> books = new ArrayList<>();
        books.add(javaBook);
        books.add(pythonBook);
        books.add(algorithmsBook);
        books.add(dataStructuresBook);
        books.add(webDevelopmentBook);
        librarySystem.setBooks(books);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        users.add(memberM002);
        users.add(guestG001);
        librarySystem.setUsers(users);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // SetUp: Create checkouts for Java Programming book by Member M001 (3 times)
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
        
        // Add checkouts to member M001
        memberM001.getCheckOuts().add(checkout1);
        memberM001.getCheckOuts().add(checkout2);
        memberM001.getCheckOuts().add(checkout3);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Java Programming book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() throws Exception {
        // SetUp: Python Basics book exists but no checkouts
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Python Basics book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // SetUp: Create checkout for Algorithms book by Member M001 (1 time)
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        
        // Add checkout to member M001
        memberM001.getCheckOuts().add(checkout);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Algorithms book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // SetUp: Create checkouts for Data Structures book by different users
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
        
        // Add checkouts to different users
        memberM001.getCheckOuts().add(checkout1);
        guestG001.getCheckOuts().add(checkout2);
        memberM002.getCheckOuts().add(checkout3);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Data Structures book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // SetUp: Create two checkouts for Web Development book by Member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        
        // Add both checkouts to member M001
        memberM001.getCheckOuts().add(checkout1);
        memberM001.getCheckOuts().add(checkout2);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Web Development book should have 2 checkouts", 2, result);
    }
    

}