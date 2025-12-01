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
        // Set up user
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Set up book
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setIsbn("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // Set up loan
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setStartDate(LocalDate.of(2023, 10, 1));
        loan.setEndDate(LocalDate.of(2023, 10, 15));
        
        // Add loan to user
        user.addLoan(loan);
        
        // Add user and book to library system
        librarySystem.getUsers().put("U001", user);
        librarySystem.getBooks().put("B001", book);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountByUser("U001");
        
        // Verify result
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Set up user
        User user = new User();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Set up books
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
        
        // Set up loans
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.of(2023, 9, 20));
        loan1.setEndDate(LocalDate.of(2023, 9, 25));
        
        Loan loan2 = new Loan();
        loan2.setBook(book1);
        loan2.setStartDate(LocalDate.of(2023, 10, 20));
        loan2.setEndDate(LocalDate.of(2023, 10, 25));
        
        Loan loan3 = new Loan();
        loan3.setBook(book2);
        loan3.setStartDate(LocalDate.of(2023, 9, 30));
        loan3.setEndDate(LocalDate.of(2023, 10, 5));
        
        // Add loans to user
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Add user and books to library system
        librarySystem.getUsers().put("U002", user);
        librarySystem.getBooks().put("B002", book1);
        librarySystem.getBooks().put("B003", book2);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountByUser("U002");
        
        // Verify result - should count unique books only (B002 and B003)
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Set up user with no loans
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system
        librarySystem.getUsers().put("U003", user);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountByUser("U003");
        
        // Verify result - should be 0.0 when no books borrowed
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Set up user
        User user = new User();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Set up books with varying page counts
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
        
        // Set up loans
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.of(2023, 9, 15));
        loan1.setEndDate(LocalDate.of(2023, 9, 22));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.of(2023, 9, 25));
        loan2.setEndDate(LocalDate.of(2023, 10, 2));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setStartDate(LocalDate.of(2023, 10, 3));
        loan3.setEndDate(LocalDate.of(2023, 10, 10));
        
        // Add loans to user
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Add user and books to library system
        librarySystem.getUsers().put("U004", user);
        librarySystem.getBooks().put("B004", book1);
        librarySystem.getBooks().put("B005", book2);
        librarySystem.getBooks().put("B006", book3);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountByUser("U004");
        
        // Verify result - average of 250, 350, 450 = 350.0
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Set up guest user
        User user = new User();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        
        // Set up books
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
        
        // Set up loans
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.of(2023, 8, 20));
        loan1.setEndDate(LocalDate.of(2023, 8, 30));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.of(2023, 9, 1));
        loan2.setEndDate(LocalDate.of(2023, 9, 10));
        
        // Add loans to user
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Add user and books to library system
        librarySystem.getUsers().put("U005", user);
        librarySystem.getBooks().put("B007", book1);
        librarySystem.getBooks().put("B008", book2);
        
        // Calculate average page count
        double result = librarySystem.calculateAveragePageCountByUser("U005");
        
        // Verify result - average of 400 and 350 = 375.0
        assertEquals(375.0, result, 0.001);
    }
}