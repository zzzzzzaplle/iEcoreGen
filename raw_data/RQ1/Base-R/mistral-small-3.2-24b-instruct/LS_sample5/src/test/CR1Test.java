import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private LibrarySystem library;
    private Book book1, book2, book3, book4, book5;
    private User userM001, userM002, userG001;
    
    @Before
    public void setUp() {
        library = new LibrarySystem();
        
        // Create books
        book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setBarcode("123456");
        book1.setIsbn("978-3-16-148410-0");
        book1.setNumberOfPages(500);
        
        book2 = new Book();
        book2.setTitle("Python Basics");
        book2.setBarcode("654321");
        book2.setIsbn("978-1-23-456789-0");
        book2.setNumberOfPages(400);
        
        book3 = new Book();
        book3.setTitle("Algorithms");
        book3.setBarcode("789012");
        book3.setIsbn("978-0-12-345678-9");
        book3.setNumberOfPages(700);
        
        book4 = new Book();
        book4.setTitle("Data Structures");
        book4.setBarcode("101112");
        book4.setIsbn("978-9-87-654321-0");
        book4.setNumberOfPages(600);
        
        book5 = new Book();
        book5.setTitle("Web Development");
        book5.setBarcode("131415");
        book5.setIsbn("978-2-36-547891-0");
        book5.setNumberOfPages(450);
        
        // Create users
        userM001 = new User();
        userM001.setId("M001");
        userM001.setName("Member M001");
        
        userM002 = new User();
        userM002.setId("M002");
        userM002.setName("Member M002");
        
        userG001 = new User();
        userG001.setId("G001");
        userG001.setName("Guest G001");
        
        // Add books and users to library system
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        
        library.addUser(userM001);
        library.addUser(userM002);
        library.addUser(userG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
        // SetUp: Checkout book "Java Programming" 3 times by Member M001
        library.checkoutBook("M001", "123456", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15));
        library.checkoutBook("M001", "123456", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 15));
        library.checkoutBook("M001", "123456", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 15));
        
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = library.countBookCheckouts("123456");
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: "Counting Checkouts for a Book with No Checkouts"
        // SetUp: Book "Python Basics" exists but no checkouts
        
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = library.countBookCheckouts("654321");
        
        // Expected Output: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: "Counting Checkouts for a Book with One Checkout"
        // SetUp: Checkout book "Algorithms" once by Member M001
        library.checkoutBook("M001", "789012", LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 15));
        
        // Input: Count the number of times the book with title "Algorithms" has been checked out
        int result = library.countBookCheckouts("789012");
        
        // Expected Output: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
        // SetUp: Checkout book "Data Structures" by different users
        library.checkoutBook("M001", "101112", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 15));
        library.checkoutBook("G001", "101112", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 15));
        library.checkoutBook("M002", "101112", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 15));
        
        // Input: Count the number of times the book with title "Data Structures" has been checked out
        int result = library.countBookCheckouts("101112");
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: "Counting Checkouts for a Book with Same User"
        // SetUp: Checkout book "Web Development" twice by Member M001
        library.checkoutBook("M001", "131415", LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 15));
        library.checkoutBook("M001", "131415", LocalDate.of(2023, 8, 16), LocalDate.of(2023, 8, 30));
        
        // Input: Count the number of times the book with title "Web Development" has been checked out
        int result = library.countBookCheckouts("131415");
        
        // Expected Output: The book has been checked out 2 times
        assertEquals(2, result);
    }
}