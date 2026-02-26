import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        // SetUp: Create User M001
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-01-01");
        checkout1.setEndDate("2023-01-15");
        checkout1.setBook(book);
        user.addCheckOut(checkout1);
        book.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15)
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-02-01");
        checkout2.setEndDate("2023-02-15");
        checkout2.setBook(book);
        user.addCheckOut(checkout2);
        book.addCheckOut(checkout2);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15)
        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate("2023-03-01");
        checkout3.setEndDate("2023-03-15");
        checkout3.setBook(book);
        user.addCheckOut(checkout3);
        book.addCheckOut(checkout3);
        
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // SetUp: No User check out this Book (no checkouts added)
        
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        // SetUp: Create User M001
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15)
        CheckOut checkout = new CheckOut();
        checkout.setStartDate("2023-04-01");
        checkout.setEndDate("2023-04-15");
        checkout.setBook(book);
        user.addCheckOut(checkout);
        book.addCheckOut(checkout);
        
        // Input: Count the number of times the book with title "Algorithms" has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // SetUp: Create User M001 (Member)
        User user1 = new User();
        user1.setID("M001");
        user1.setType(UserType.MEMBER);
        
        // SetUp: Create User G001 (Guest)
        User user2 = new User();
        user2.setID("G001");
        user2.setType(UserType.GUEST);
        
        // SetUp: Create User M002 (Member)
        User user3 = new User();
        user3.setID("M002");
        user3.setType(UserType.MEMBER);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-05-01");
        checkout1.setEndDate("2023-05-15");
        checkout1.setBook(book);
        user1.addCheckOut(checkout1);
        book.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15)
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-06-01");
        checkout2.setEndDate("2023-06-15");
        checkout2.setBook(book);
        user2.addCheckOut(checkout2);
        book.addCheckOut(checkout2);
        
        // SetUp: Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15)
        CheckOut checkout3 = new CheckOut();
        checkout3.setStartDate("2023-07-01");
        checkout3.setEndDate("2023-07-15");
        checkout3.setBook(book);
        user3.addCheckOut(checkout3);
        book.addCheckOut(checkout3);
        
        // Input: Count the number of times the book with title "Data Structures" has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        // SetUp: Create User M001
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-15");
        checkout1.setBook(book);
        user.addCheckOut(checkout1);
        book.addCheckOut(checkout1);
        
        // SetUp: Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30)
        CheckOut checkout2 = new CheckOut();
        checkout2.setStartDate("2023-08-16");
        checkout2.setEndDate("2023-08-30");
        checkout2.setBook(book);
        user.addCheckOut(checkout2);
        book.addCheckOut(checkout2);
        
        // Input: Count the number of times the book with title "Web Development" has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals(2, result);
    }
}