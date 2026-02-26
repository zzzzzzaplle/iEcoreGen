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
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        // Create Member M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 00:00:00"));
        memberM001.addCheckOut(checkout2);
        
        // Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 00:00:00"));
        memberM001.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.getUsers().add(memberM001);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // No User check out this Book
        // No setup needed for checkouts
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        // Create Member M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15)
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 00:00:00"));
        memberM001.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(memberM001);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Create Member M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        // Create Guest G001
        User guestG001 = new User();
        guestG001.setID("G001");
        guestG001.setName("Guest One");
        guestG001.setType(UserType.GUEST);
        
        // Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        guestG001.addCheckOut(checkout2);
        
        // Create Member M002
        User memberM002 = new User();
        memberM002.setID("M002");
        memberM002.setName("Member Two");
        memberM002.setType(UserType.MEMBER);
        
        // Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15)
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 00:00:00"));
        memberM002.addCheckOut(checkout3);
        
        // Add all users to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getUsers().add(guestG001);
        librarySystem.getUsers().add(memberM002);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        // Create Member M001
        User memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setName("Member One");
        memberM001.setType(UserType.MEMBER);
        
        // Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15)
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 00:00:00"));
        memberM001.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        memberM001.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.getUsers().add(memberM001);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, result);
    }
}