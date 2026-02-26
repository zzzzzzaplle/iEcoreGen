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
        User user = new MemberUser();
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
        
        // SetUp: Create CheckOut record
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setStartDate("2023-10-01");
        loan.setEndDate("2023-10-15");
        
        // Add to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        librarySystem.addLoan(loan);
        
        // Calculate average pages
        double result = librarySystem.getAveragePageCountOfBorrowedBooks(user);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user U002
        User user = new MemberUser();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // SetUp: Create books
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
        
        // SetUp: Create CheckOut records
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate("2023-09-20");
        loan1.setEndDate("2023-09-25");
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book1); // Same book borrowed again
        loan2.setStartDate("2023-10-20");
        loan2.setEndDate("2023-10-25");
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book2);
        loan3.setStartDate("2023-09-30");
        loan3.setEndDate("2023-10-05");
        
        // Add to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Calculate average pages
        double result = librarySystem.getAveragePageCountOfBorrowedBooks(user);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003 with no borrowed books
        User user = new MemberUser();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add to library system
        librarySystem.addUser(user);
        
        // Calculate average pages
        double result = librarySystem.getAveragePageCountOfBorrowedBooks(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        User user = new MemberUser();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // SetUp: Create books
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
        
        // SetUp: Create CheckOut records
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate("2023-09-15");
        loan1.setEndDate("2023-09-22");
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book2);
        loan2.setStartDate("2023-09-25");
        loan2.setEndDate("2023-10-02");
        
        Loan loan3 = new Loan();
        loan3.setUser(user);
        loan3.setBook(book3);
        loan3.setStartDate("2023-10-03");
        loan3.setEndDate("2023-10-10");
        
        // Add to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Calculate average pages
        double result = librarySystem.getAveragePageCountOfBorrowedBooks(user);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        User user = new GuestUser();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        
        // SetUp: Create books
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
        
        // SetUp: Create CheckOut records
        Loan loan1 = new Loan();
        loan1.setUser(user);
        loan1.setBook(book1);
        loan1.setStartDate("2023-08-20");
        loan1.setEndDate("2023-08-30");
        
        Loan loan2 = new Loan();
        loan2.setUser(user);
        loan2.setBook(book2);
        loan2.setStartDate("2023-09-01");
        loan2.setEndDate("2023-09-10");
        
        // Add to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Calculate average pages
        double result = librarySystem.getAveragePageCountOfBorrowedBooks(user);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}