import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // Create user U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book B001
        Book book = new Book();
        book.setBarcode("B001");
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setNumberOfPages(300);
        
        // Create checkout record
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate("2023-10-01");
        checkout.setEndDate("2023-10-15");
        
        // Add checkout to user
        user.addCheckOut(checkout);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify result
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Create user U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create book B002
        Book book1 = new Book();
        book1.setBarcode("B002");
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setNumberOfPages(500);
        
        // Create book B003
        Book book2 = new Book();
        book2.setBarcode("B003");
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setNumberOfPages(600);
        
        // Create first checkout for B002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-09-20");
        checkout1.setEndDate("2023-09-25");
        
        // Create second checkout for B002 (same book, should count as unique book only once)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate("2023-10-20");
        checkout2.setEndDate("2023-10-25");
        
        // Create checkout for B003
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book2);
        checkout3.setStartDate("2023-09-30");
        checkout3.setEndDate("2023-10-05");
        
        // Add all checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages (should count each unique book only once)
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify result: (500 + 600) / 2 = 550.0
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create user U003 with no books borrowed
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages for user with no checkouts
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify result should be 0.0
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Create user U004
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create book B004
        Book book1 = new Book();
        book1.setBarcode("B004");
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setNumberOfPages(250);
        
        // Create book B005
        Book book2 = new Book();
        book2.setBarcode("B005");
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setNumberOfPages(350);
        
        // Create book B006
        Book book3 = new Book();
        book3.setBarcode("B006");
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setNumberOfPages(450);
        
        // Create checkout for B004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-09-15");
        checkout1.setEndDate("2023-09-22");
        
        // Create checkout for B005
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-09-25");
        checkout2.setEndDate("2023-10-02");
        
        // Create checkout for B006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate("2023-10-03");
        checkout3.setEndDate("2023-10-10");
        
        // Add all checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify result: (250 + 350 + 450) / 3 = 350.0
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Create guest user U005
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // Create book B007
        Book book1 = new Book();
        book1.setBarcode("B007");
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setNumberOfPages(400);
        
        // Create book B008
        Book book2 = new Book();
        book2.setBarcode("B008");
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setNumberOfPages(350);
        
        // Create checkout for B007
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-08-20");
        checkout1.setEndDate("2023-08-30");
        
        // Create checkout for B008
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-09-01");
        checkout2.setEndDate("2023-09-10");
        
        // Add both checkouts to user
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Calculate average pages for guest user
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify result: (400 + 350) / 2 = 375.0
        assertEquals(375.0, result, 0.001);
    }
}