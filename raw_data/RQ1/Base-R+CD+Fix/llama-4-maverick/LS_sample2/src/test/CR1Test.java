import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // Create book object
        Book javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        // Create user
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // Create checkouts
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
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        checkouts.add(checkout3);
        member1.setCheckOuts(checkouts);
        
        // Add user and book to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        librarySystem.setUsers(users);
        
        List<Book> books = new ArrayList<>();
        books.add(javaBook);
        librarySystem.setBooks(books);
        
        // Test the countBookCheckOuts method
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify the expected output
        assertEquals("Book should be checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() throws Exception {
        // Create book object
        Book pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        // Add book to library system (no users with checkouts)
        List<Book> books = new ArrayList<>();
        books.add(pythonBook);
        librarySystem.setBooks(books);
        
        List<User> users = new ArrayList<>();
        librarySystem.setUsers(users);
        
        // Test the countBookCheckOuts method
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify the expected output
        assertEquals("Book should be checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create book object
        Book algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        // Create user
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // Create checkout
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        
        // Add checkout to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout);
        member1.setCheckOuts(checkouts);
        
        // Add user and book to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        librarySystem.setUsers(users);
        
        List<Book> books = new ArrayList<>();
        books.add(algorithmsBook);
        librarySystem.setBooks(books);
        
        // Test the countBookCheckOuts method
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify the expected output
        assertEquals("Book should be checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create book object
        Book dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        // Create users
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        User guest1 = new User();
        guest1.setID("G001");
        guest1.setType(UserType.GUEST);
        
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        // Create checkouts
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
        
        // Add checkouts to users
        List<CheckOut> member1Checkouts = new ArrayList<>();
        member1Checkouts.add(checkout1);
        member1.setCheckOuts(member1Checkouts);
        
        List<CheckOut> guest1Checkouts = new ArrayList<>();
        guest1Checkouts.add(checkout2);
        guest1.setCheckOuts(guest1Checkouts);
        
        List<CheckOut> member2Checkouts = new ArrayList<>();
        member2Checkouts.add(checkout3);
        member2.setCheckOuts(member2Checkouts);
        
        // Add users and book to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(guest1);
        users.add(member2);
        librarySystem.setUsers(users);
        
        List<Book> books = new ArrayList<>();
        books.add(dataStructuresBook);
        librarySystem.setBooks(books);
        
        // Test the countBookCheckOuts method
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify the expected output
        assertEquals("Book should be checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create book object
        Book webDevBook = new Book();
        webDevBook.setTitle("Web Development");
        webDevBook.setBarcode("131415");
        webDevBook.setISBN("978-2-36-547891-0");
        webDevBook.setNumberOfPages(450);
        
        // Create user
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        // Create checkouts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevBook);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevBook);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        
        // Add checkouts to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        member1.setCheckOuts(checkouts);
        
        // Add user and book to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        librarySystem.setUsers(users);
        
        List<Book> books = new ArrayList<>();
        books.add(webDevBook);
        librarySystem.setBooks(books);
        
        // Test the countBookCheckOuts method
        int result = librarySystem.countBookCheckOuts(webDevBook);
        
        // Verify the expected output
        assertEquals("Book should be checked out 2 times", 2, result);
    }
}