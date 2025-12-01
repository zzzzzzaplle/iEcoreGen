import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() throws ParseException {
        // Test Case 1: Average Pages Calculation for Single Book Borrowed
        
        // SetUp: Create user U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // SetUp: Create book B001
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // SetUp: Create CheckOut record
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-10-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-10-15 00:00:00"));
        
        // Add checkout to user
        user.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method and verify result
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals("Average pages should be 300.0 for single book", 300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() throws ParseException {
        // Test Case 2: Average Pages Calculation for Multiple Books Borrowed
        
        // SetUp: Create user U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // SetUp: Create books
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        
        // SetUp: Create CheckOut records
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-09-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-25 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-10-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-25 00:00:00"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book2);
        checkout3.setStartDate(dateFormat.parse("2023-09-30 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-05 00:00:00"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method and verify result
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals("Average pages should be 550.0 for two unique books", 550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Test Case 3: No Books Borrowed
        
        // SetUp: Create user U003 with no books
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method and verify result
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals("Average pages should be 0.0 when no books borrowed", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() throws ParseException {
        // Test Case 4: Average Pages Calculation for Books with Different Page Counts
        
        // SetUp: Create user U004
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // SetUp: Create books
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        
        // SetUp: Create CheckOut records
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-22 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-09-25 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-02 00:00:00"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-10-03 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-10 00:00:00"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method and verify result
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals("Average pages should be 350.0 for three books with different page counts", 350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() throws ParseException {
        // Test Case 5: Average Pages Calculation for Guest User
        
        // SetUp: Create guest user U005
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // SetUp: Create books
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        
        // SetUp: Create CheckOut records
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-08-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-10 00:00:00"));
        
        // Add checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method and verify result
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals("Average pages should be 375.0 for guest user with two books", 375.0, result, 0.001);
    }
}