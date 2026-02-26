import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create book and users
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Process checkouts for the same book by same user
        librarySystem.processCheckout(user, book, "2023-01-01", "2023-01-15");
        librarySystem.processCheckout(user, book, "2023-02-01", "2023-02-15");
        librarySystem.processCheckout(user, book, "2023-03-01", "2023-03-15");
        
        // Test: Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create book with no checkouts
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // Test: Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create book and user
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Process one checkout
        librarySystem.processCheckout(user, book, "2023-04-01", "2023-04-15");
        
        // Test: Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create book and multiple users
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        // Process checkouts by different users
        librarySystem.processCheckout(member1, book, "2023-05-01", "2023-05-15");
        librarySystem.processCheckout(guest, book, "2023-06-01", "2023-06-15");
        librarySystem.processCheckout(member2, book, "2023-07-01", "2023-07-15");
        
        // Test: Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create book and user
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Process checkouts for the same book by same user consecutively
        librarySystem.processCheckout(user, book, "2023-08-01", "2023-08-15");
        librarySystem.processCheckout(user, book, "2023-08-16", "2023-08-30");
        
        // Test: Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Verify: The book has been checked out 2 times
        assertEquals(2, result);
    }
}