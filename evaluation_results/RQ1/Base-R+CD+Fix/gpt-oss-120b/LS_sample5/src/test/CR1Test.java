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
        // SetUp: Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        librarySystem.addBook(book);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        user.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 23:59:59"));
        user.addCheckOut(checkout2);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 23:59:59"));
        user.addCheckOut(checkout3);
        
        librarySystem.addUser(user);
        
        // Input: Count the number of times the book with title "Java Programming" has been checked out.
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        librarySystem.addBook(book);
        
        // SetUp: No User check out this Book.
        // No users added to the system, so no checkouts exist
        
        // Input: Count the number of times the book with title "Python Basics" has been checked out.
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // SetUp: Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        librarySystem.addBook(book);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 23:59:59"));
        user.addCheckOut(checkout);
        
        librarySystem.addUser(user);
        
        // Input: Count the number of times the book with title "Algorithms" has been checked out.
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // SetUp: Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        librarySystem.addBook(book);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
        User user1 = new User();
        user1.setID("M001");
        user1.setType(UserType.MEMBER);
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 23:59:59"));
        user1.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
        User user2 = new User();
        user2.setID("G001");
        user2.setType(UserType.GUEST);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        user2.addCheckOut(checkout2);
        
        // SetUp: Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
        User user3 = new User();
        user3.setID("M002");
        user3.setType(UserType.MEMBER);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 23:59:59"));
        user3.addCheckOut(checkout3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        librarySystem.addUser(user3);
        
        // Input: Count the number of times the book with title "Data Structures" has been checked out.
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // SetUp: Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        librarySystem.addBook(book);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 23:59:59"));
        user.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 23:59:59"));
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Input: Count the number of times the book with title "Web Development" has been checked out.
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, result);
    }
}