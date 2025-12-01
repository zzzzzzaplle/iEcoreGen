import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user U001
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        librarySystem.addUser(user);
        
        // SetUp: Create book B001
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setIsbn("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        librarySystem.addBook(book);
        
        // SetUp: Create CheckOut record for User U001 with book B001
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 15);
        librarySystem.checkoutBook("U001", "B001", startDate, endDate);
        
        // Execute: Calculate average page count for user U001
        double result = librarySystem.calculateAveragePageCountByUser("U001");
        
        // Verify: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user U002
        User user = new User();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        librarySystem.addUser(user);
        
        // SetUp: Create book B002
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setIsbn("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        librarySystem.addBook(book1);
        
        // SetUp: Create book B003
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setIsbn("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        librarySystem.addBook(book2);
        
        // SetUp: Create CheckOut records for User U002
        LocalDate startDate1 = LocalDate.of(2023, 9, 20);
        LocalDate endDate1 = LocalDate.of(2023, 9, 25);
        librarySystem.checkoutBook("U002", "B002", startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.of(2023, 10, 20);
        LocalDate endDate2 = LocalDate.of(2023, 10, 25);
        librarySystem.checkoutBook("U002", "B002", startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.of(2023, 9, 30);
        LocalDate endDate3 = LocalDate.of(2023, 10, 5);
        librarySystem.checkoutBook("U002", "B003", startDate3, endDate3);
        
        // Execute: Calculate average page count for user U002
        double result = librarySystem.calculateAveragePageCountByUser("U002");
        
        // Verify: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003 with no borrowed books
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        librarySystem.addUser(user);
        
        // Execute: Calculate average page count for user U003 (should throw exception)
        librarySystem.calculateAveragePageCountByUser("U003");
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        User user = new User();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        librarySystem.addUser(user);
        
        // SetUp: Create book B004
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setIsbn("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        librarySystem.addBook(book1);
        
        // SetUp: Create book B005
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setIsbn("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        librarySystem.addBook(book2);
        
        // SetUp: Create book B006
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setIsbn("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        librarySystem.addBook(book3);
        
        // SetUp: Create CheckOut records for User U004
        LocalDate startDate1 = LocalDate.of(2023, 9, 15);
        LocalDate endDate1 = LocalDate.of(2023, 9, 22);
        librarySystem.checkoutBook("U004", "B004", startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.of(2023, 9, 25);
        LocalDate endDate2 = LocalDate.of(2023, 10, 2);
        librarySystem.checkoutBook("U004", "B005", startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.of(2023, 10, 3);
        LocalDate endDate3 = LocalDate.of(2023, 10, 10);
        librarySystem.checkoutBook("U004", "B006", startDate3, endDate3);
        
        // Execute: Calculate average page count for user U004
        double result = librarySystem.calculateAveragePageCountByUser("U004");
        
        // Verify: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        User user = new User();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        librarySystem.addUser(user);
        
        // SetUp: Create book B007
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setIsbn("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        librarySystem.addBook(book1);
        
        // SetUp: Create book B008
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setIsbn("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        librarySystem.addBook(book2);
        
        // SetUp: Create CheckOut records for User U005
        LocalDate startDate1 = LocalDate.of(2023, 8, 20);
        LocalDate endDate1 = LocalDate.of(2023, 8, 30);
        librarySystem.checkoutBook("U005", "B007", startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.of(2023, 9, 1);
        LocalDate endDate2 = LocalDate.of(2023, 9, 10);
        librarySystem.checkoutBook("U005", "B008", startDate2, endDate2);
        
        // Execute: Calculate average page count for user U005
        double result = librarySystem.calculateAveragePageCountByUser("U005");
        
        // Verify: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}