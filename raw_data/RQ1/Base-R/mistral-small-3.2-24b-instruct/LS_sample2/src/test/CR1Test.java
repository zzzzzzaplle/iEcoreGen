import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book book;
    private MemberUser memberM001;
    private MemberUser memberM002;
    private GuestUser guestG001;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create common test users
        memberM001 = new MemberUser();
        memberM001.setId("M001");
        memberM001.setName("Member One");
        
        memberM002 = new MemberUser();
        memberM002.setId("M002");
        memberM002.setName("Member Two");
        
        guestG001 = new GuestUser();
        guestG001.setId("G001");
        guestG001.setName("Guest One");
        
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: Counting Checkouts for a Book with Multiple Checkouts
        // Create a Book object with title: "Java Programming"
        book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setIsbn("978-3-16-148410-0");
        book.setNumberOfPages(500);
        librarySystem.addBook(book);
        
        // Checkout this book by Member M001 three times with different date ranges
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-01-01", formatter), LocalDate.parse("2023-01-15", formatter));
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-02-01", formatter), LocalDate.parse("2023-02-15", formatter));
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-03-01", formatter), LocalDate.parse("2023-03-15", formatter));
        
        // Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckouts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, checkoutCount);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: Counting Checkouts for a Book with No Checkouts
        // Create a Book object with title: "Python Basics"
        book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setIsbn("978-1-23-456789-0");
        book.setNumberOfPages(400);
        librarySystem.addBook(book);
        
        // No User check out this Book (no createBookLoan calls)
        
        // Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckouts(book);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals("Book should have been checked out 0 times", 0, checkoutCount);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: Counting Checkouts for a Book with One Checkout
        // Create a Book object with title: "Algorithms"
        book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setIsbn("978-0-12-345678-9");
        book.setNumberOfPages(700);
        librarySystem.addBook(book);
        
        // Checkout this book by Member M001 once
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-04-01", formatter), LocalDate.parse("2023-04-15", formatter));
        
        // Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckouts(book);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals("Book should have been checked out 1 time", 1, checkoutCount);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
        // Create a Book object with title: "Data Structures"
        book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setIsbn("978-9-87-654321-0");
        book.setNumberOfPages(600);
        librarySystem.addBook(book);
        
        // Checkout this book by different users (Member M001, Guest G001, Member M002)
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-05-01", formatter), LocalDate.parse("2023-05-15", formatter));
        librarySystem.createBookLoan(book, guestG001, LocalDate.parse("2023-06-01", formatter), LocalDate.parse("2023-06-15", formatter));
        librarySystem.createBookLoan(book, memberM002, LocalDate.parse("2023-07-01", formatter), LocalDate.parse("2023-07-15", formatter));
        
        // Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckouts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, checkoutCount);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: Counting Checkouts for a Book with Same User
        // Create a Book object with title: "Web Development"
        book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setIsbn("978-2-36-547891-0");
        book.setNumberOfPages(450);
        librarySystem.addBook(book);
        
        // Checkout this book by Member M001 twice with consecutive date ranges
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-08-01", formatter), LocalDate.parse("2023-08-15", formatter));
        librarySystem.createBookLoan(book, memberM001, LocalDate.parse("2023-08-16", formatter), LocalDate.parse("2023-08-30", formatter));
        
        // Count the number of times the book has been checked out
        int checkoutCount = librarySystem.countBookCheckouts(book);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals("Book should have been checked out 2 times", 2, checkoutCount);
    }
}