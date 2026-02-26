import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        librarySystem.addBook(book);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.addUser(member);
        
        // Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        member.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        member.addCheckOut(checkout2);
        
        // Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        member.addCheckOut(checkout3);
        
        // Count the number of times the book has been checked out
        int count = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, count);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() throws ParseException {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        librarySystem.addBook(book);
        
        // No User check out this Book.
        
        // Count the number of times the book has been checked out
        int count = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, count);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() throws ParseException {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        librarySystem.addBook(book);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.addUser(member);
        
        // Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01"));
        checkout.setEndDate(dateFormat.parse("2023-04-15"));
        member.addCheckOut(checkout);
        
        // Count the number of times the book has been checked out
        int count = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, count);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        librarySystem.addBook(book);
        
        // Create Member M001
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        librarySystem.addUser(member1);
        
        // Create Guest G001
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        librarySystem.addUser(guest);
        
        // Create Member M002
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        librarySystem.addUser(member2);
        
        // Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15"));
        member1.addCheckOut(checkout1);
        
        // Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15"));
        guest.addCheckOut(checkout2);
        
        // Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15"));
        member2.addCheckOut(checkout3);
        
        // Count the number of times the book has been checked out
        int count = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, count);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() throws ParseException {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        librarySystem.addBook(book);
        
        // Create Member M001
        User member = new User();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.addUser(member);
        
        // Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15"));
        member.addCheckOut(checkout1);
        
        // Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30"));
        member.addCheckOut(checkout2);
        
        // Count the number of times the book has been checked out
        int count = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, count);
    }
}