import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // Create user
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // Create checkout record
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-10-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-10-15 00:00:00"));
        
        // Add checkout to user
        user.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected result
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() throws ParseException {
        // Create user
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create first book
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        
        // Create second book
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        
        // Create first checkout for book B002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-09-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-25 00:00:00"));
        
        // Create second checkout for book B002 (duplicate book)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(dateFormat.parse("2023-10-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-25 00:00:00"));
        
        // Create checkout for book B003
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book2);
        checkout3.setStartDate(dateFormat.parse("2023-09-30 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-05 00:00:00"));
        
        // Add all checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected result (only unique books: 500 + 600 = 1100 / 2 = 550.0)
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create user with no borrowed books
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected result (0.0 for no books)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() throws ParseException {
        // Create user
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create first book
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        
        // Create second book
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        
        // Create third book
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        
        // Create checkout for first book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-22 00:00:00"));
        
        // Create checkout for second book
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-09-25 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-02 00:00:00"));
        
        // Create checkout for third book
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(dateFormat.parse("2023-10-03 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-10 00:00:00"));
        
        // Add all checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected result (250 + 350 + 450 = 1050 / 3 = 350.0)
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() throws ParseException {
        // Create guest user
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // Create first book
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        
        // Create second book
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        
        // Create checkout for first book
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-08-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        
        // Create checkout for second book
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-10 00:00:00"));
        
        // Add all checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected result (400 + 350 = 750 / 2 = 375.0)
        assertEquals(375.0, result, 0.001);
    }
}