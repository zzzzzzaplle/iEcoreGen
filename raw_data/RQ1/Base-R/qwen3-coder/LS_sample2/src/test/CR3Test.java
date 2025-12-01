import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // SetUp: Create book B001
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setIsbn("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // SetUp: Create loan record
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setStartDate(LocalDate.parse("2023-10-01", formatter));
        loan.setEndDate(LocalDate.parse("2023-10-15", formatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book);
        librarySystem.getLoans().add(loan);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountForUser(user);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
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
        
        // SetUp: Create books B002 and B003
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setIsbn("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setIsbn("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        
        // SetUp: Create loan records for User U002
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-09-20", formatter));
        loan1.setEndDate(LocalDate.parse("2023-09-25", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book1);
        loan2.setStartDate(LocalDate.parse("2023-10-20", formatter));
        loan2.setEndDate(LocalDate.parse("2023-10-25", formatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book2);
        loan3.setStartDate(LocalDate.parse("2023-09-30", formatter));
        loan3.setEndDate(LocalDate.parse("2023-10-05", formatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountForUser(user);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system (no books or loans)
        librarySystem.getUsers().add(user);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountForUser(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        User user = new User();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // SetUp: Create books B004, B005, B006
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setIsbn("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setIsbn("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setIsbn("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        
        // SetUp: Create loan records for User U004
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-09-15", formatter));
        loan1.setEndDate(LocalDate.parse("2023-09-22", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-09-25", formatter));
        loan2.setEndDate(LocalDate.parse("2023-10-02", formatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book3);
        loan3.setStartDate(LocalDate.parse("2023-10-03", formatter));
        loan3.setEndDate(LocalDate.parse("2023-10-10", formatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getBooks().add(book3);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountForUser(user);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        Guest guest = new Guest();
        guest.setId("U005");
        guest.setName("Eve");
        guest.setEmail("eve@example.com");
        guest.setAddress("202 Birch St.");
        
        // SetUp: Create books B007, B008
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setIsbn("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setIsbn("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        
        // SetUp: Create loan records for Guest U005
        Loan loan1 = new Loan();
        loan1.setUser(guest);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-08-20", formatter));
        loan1.setEndDate(LocalDate.parse("2023-08-30", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(guest);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-09-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-09-10", formatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(guest);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountForUser(guest);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}