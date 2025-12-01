import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        // Initialize library system before each test
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // Create user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = new Member();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setIsbn("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // Create CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15"
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setStartDate("2023-10-01");
        loan.setEndDate("2023-10-15");
        
        // Add book, user, and loan to library system
        librarySystem.addBook(book);
        librarySystem.addUser(user);
        librarySystem.addLoan(loan);
        
        // Calculate average pages and verify expected result
        double result = librarySystem.getAveragePageCountOfUniqueBooks(user);
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Create user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = new Member();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create books
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
        
        // Create CheckOut records for User U002
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(user);
        loan1.setStartDate("2023-09-20");
        loan1.setEndDate("2023-09-25");
        
        Loan loan2 = new Loan();
        loan2.setBook(book1); // Same book B002 borrowed again
        loan2.setUser(user);
        loan2.setStartDate("2023-10-20");
        loan2.setEndDate("2023-10-25");
        
        Loan loan3 = new Loan();
        loan3.setBook(book2);
        loan3.setUser(user);
        loan3.setStartDate("2023-09-30");
        loan3.setEndDate("2023-10-05");
        
        // Add books, user, and loans to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addUser(user);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Calculate average pages and verify expected result (unique books only)
        double result = librarySystem.getAveragePageCountOfUniqueBooks(user);
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = new Member();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system (no books or loans added)
        librarySystem.addUser(user);
        
        // Calculate average pages and verify expected result (0.0 for no books)
        double result = librarySystem.getAveragePageCountOfUniqueBooks(user);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Create user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = new Member();
        user.setId("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create books
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
        
        // Create CheckOut records for User U004
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(user);
        loan1.setStartDate("2023-09-15");
        loan1.setEndDate("2023-09-22");
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(user);
        loan2.setStartDate("2023-09-25");
        loan2.setEndDate("2023-10-02");
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(user);
        loan3.setStartDate("2023-10-03");
        loan3.setEndDate("2023-10-10");
        
        // Add books, user, and loans to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addUser(user);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Calculate average pages and verify expected result
        double result = librarySystem.getAveragePageCountOfUniqueBooks(user);
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = new Guest();
        user.setId("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        
        // Create books
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
        
        // Create CheckOut records for User U005
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(user);
        loan1.setStartDate("2023-08-20");
        loan1.setEndDate("2023-08-30");
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(user);
        loan2.setStartDate("2023-09-01");
        loan2.setEndDate("2023-09-10");
        
        // Add books, user, and loans to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addUser(user);
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Calculate average pages and verify expected result
        double result = librarySystem.getAveragePageCountOfUniqueBooks(user);
        assertEquals(375.0, result, 0.001);
    }
}