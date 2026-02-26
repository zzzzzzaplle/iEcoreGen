import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    private DateTimeFormatter dateFormatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // Create user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setIsbn("123456789");
        book.setBarcode("B001");
        book.setPages(300);
        
        // Create CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15"
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setStartDate(LocalDate.parse("2023-10-01", dateFormatter));
        loan.setEndDate(LocalDate.parse("2023-10-15", dateFormatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book);
        librarySystem.getLoans().add(loan);
        
        // Calculate average pages and verify expected result
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(300.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Create user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = new User();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create books
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setIsbn("987654321");
        book1.setBarcode("B002");
        book1.setPages(500);
        
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setIsbn("123123123");
        book2.setBarcode("B003");
        book2.setPages(600);
        
        // Create CheckOut records for User U002
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-09-20", dateFormatter));
        loan1.setEndDate(LocalDate.parse("2023-09-25", dateFormatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book1);
        loan2.setStartDate(LocalDate.parse("2023-10-20", dateFormatter));
        loan2.setEndDate(LocalDate.parse("2023-10-25", dateFormatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book2);
        loan3.setStartDate(LocalDate.parse("2023-09-30", dateFormatter));
        loan3.setEndDate(LocalDate.parse("2023-10-05", dateFormatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Calculate average pages and verify expected result
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(550.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system (no books or loans)
        librarySystem.getUsers().add(user);
        
        // Calculate average pages and verify expected result
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(0.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Create user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = new User();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create books
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setIsbn("321321321");
        book1.setBarcode("B004");
        book1.setPages(250);
        
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setIsbn("456456456");
        book2.setBarcode("B005");
        book2.setPages(350);
        
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setIsbn("654654654");
        book3.setBarcode("B006");
        book3.setPages(450);
        
        // Create CheckOut records for User U004
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-09-15", dateFormatter));
        loan1.setEndDate(LocalDate.parse("2023-09-22", dateFormatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-09-25", dateFormatter));
        loan2.setEndDate(LocalDate.parse("2023-10-02", dateFormatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book3);
        loan3.setStartDate(LocalDate.parse("2023-10-03", dateFormatter));
        loan3.setEndDate(LocalDate.parse("2023-10-10", dateFormatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getBooks().add(book3);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Calculate average pages and verify expected result
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(350.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Create guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        Guest user = new Guest();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        
        // Create books
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setIsbn("111111111");
        book1.setBarcode("B007");
        book1.setPages(400);
        
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setIsbn("222222222");
        book2.setBarcode("B008");
        book2.setPages(350);
        
        // Create CheckOut records for User U005
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-08-20", dateFormatter));
        loan1.setEndDate(LocalDate.parse("2023-08-30", dateFormatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-09-01", dateFormatter));
        loan2.setEndDate(LocalDate.parse("2023-09-10", dateFormatter));
        
        // Add objects to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Calculate average pages and verify expected result
        double averagePages = librarySystem.calculateAveragePageCountForUser(user);
        assertEquals(375.0, averagePages, 0.001);
    }
}