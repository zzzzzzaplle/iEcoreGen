import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    private LibrarySystem librarySystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user U001
        MemberUser user = new MemberUser();
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
        
        // SetUp: Create CheckOut record
        LocalDate startDate = LocalDate.parse("2023-10-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-10-15", formatter);
        librarySystem.createBookLoan(book, user, startDate, endDate);
        
        // Calculate average pages and verify expected output
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(300.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user U002
        MemberUser user = new MemberUser();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        librarySystem.addUser(user);
        
        // SetUp: Create books B002 and B003
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setIsbn("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setIsbn("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        librarySystem.addBook(book2);
        
        // SetUp: Create CheckOut records (multiple checkouts for same book and different books)
        LocalDate startDate1 = LocalDate.parse("2023-09-20", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-09-25", formatter);
        librarySystem.createBookLoan(book1, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-10-20", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-10-25", formatter);
        librarySystem.createBookLoan(book1, user, startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.parse("2023-09-30", formatter);
        LocalDate endDate3 = LocalDate.parse("2023-10-05", formatter);
        librarySystem.createBookLoan(book2, user, startDate3, endDate3);
        
        // Calculate average pages and verify expected output
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(550.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003 with no borrowed books
        MemberUser user = new MemberUser();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        librarySystem.addUser(user);
        
        // Calculate average pages and verify expected output
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(0.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        MemberUser user = new MemberUser();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        librarySystem.addUser(user);
        
        // SetUp: Create books B004, B005, B006
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setIsbn("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setIsbn("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        librarySystem.addBook(book2);
        
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setIsbn("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        librarySystem.addBook(book3);
        
        // SetUp: Create CheckOut records
        LocalDate startDate1 = LocalDate.parse("2023-09-15", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-09-22", formatter);
        librarySystem.createBookLoan(book1, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-09-25", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-10-02", formatter);
        librarySystem.createBookLoan(book2, user, startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.parse("2023-10-03", formatter);
        LocalDate endDate3 = LocalDate.parse("2023-10-10", formatter);
        librarySystem.createBookLoan(book3, user, startDate3, endDate3);
        
        // Calculate average pages and verify expected output
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(350.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        GuestUser user = new GuestUser();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        librarySystem.addUser(user);
        
        // SetUp: Create books B007, B008
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setIsbn("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setIsbn("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        librarySystem.addBook(book2);
        
        // SetUp: Create CheckOut records
        LocalDate startDate1 = LocalDate.parse("2023-08-20", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-08-30", formatter);
        librarySystem.createBookLoan(book1, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-09-01", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-09-10", formatter);
        librarySystem.createBookLoan(book2, user, startDate2, endDate2);
        
        // Calculate average pages and verify expected output
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(375.0, averagePages, 0.001);
    }
}