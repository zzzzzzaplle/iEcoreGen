import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private User user;
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // Create a user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        user = new User("Alice", "alice@example.com", "123 Main St.", "U001");
        
        // Create a book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300.
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        
        // Create a CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15".
        BookLoan bookLoan = new BookLoan(book, LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 15));
        user.addBookLoan(bookLoan);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages.
        double expectedAverage = 300.0;
        double actualAverage = user.calculateAveragePageCountOfBorrowedBooks();
        
        assertEquals("Average page count should be 300.0 for single book", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // Create a user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        user = new User("Bob", "bob@example.com", "456 Elm St.", "U002");
        
        // Create books:
        // Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500.
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        // Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600.
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // Create CheckOut records for User U002:
        // CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25".
        BookLoan loan1 = new BookLoan(book1, LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25));
        // CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25".
        BookLoan loan2 = new BookLoan(book1, LocalDate.of(2023, 10, 20), LocalDate.of(2023, 10, 25));
        // CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05".
        BookLoan loan3 = new BookLoan(book2, LocalDate.of(2023, 9, 30), LocalDate.of(2023, 10, 5));
        
        user.addBookLoan(loan1);
        user.addBookLoan(loan2);
        user.addBookLoan(loan3);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages.
        double expectedAverage = 550.0;
        double actualAverage = user.calculateAveragePageCountOfBorrowedBooks();
        
        assertEquals("Average page count should be 550.0 for multiple books", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // Create a user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        user = new User("Charlie", "charlie@example.com", "789 Oak St.", "U003");
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined).
        double expectedAverage = 0.0;
        double actualAverage = user.calculateAveragePageCountOfBorrowedBooks();
        
        assertEquals("Average page count should be 0.0 when no books borrowed", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // Create a user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        user = new User("Daisy", "daisy@example.com", "101 Maple St.", "U004");
        
        // Create books:
        // Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250.
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        // Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350.
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        // Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450.
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        // Create CheckOut records for User U004:
        // CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22".
        BookLoan loan1 = new BookLoan(book1, LocalDate.of(2023, 9, 15), LocalDate.of(2023, 9, 22));
        // CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02".
        BookLoan loan2 = new BookLoan(book2, LocalDate.of(2023, 9, 25), LocalDate.of(2023, 10, 2));
        // CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10".
        BookLoan loan3 = new BookLoan(book3, LocalDate.of(2023, 10, 3), LocalDate.of(2023, 10, 10));
        
        user.addBookLoan(loan1);
        user.addBookLoan(loan2);
        user.addBookLoan(loan3);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages.
        double expectedAverage = 350.0;
        double actualAverage = user.calculateAveragePageCountOfBorrowedBooks();
        
        assertEquals("Average page count should be 350.0 for books with different page counts", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        user = new User("Eve", "eve@example.com", "202 Birch St.", "U005");
        
        // Create books:
        // Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400.
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        // Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350.
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        // Create CheckOut records for User U005:
        // CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30".
        BookLoan loan1 = new BookLoan(book1, LocalDate.of(2023, 8, 20), LocalDate.of(2023, 8, 30));
        // CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10".
        BookLoan loan2 = new BookLoan(book2, LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10));
        
        user.addBookLoan(loan1);
        user.addBookLoan(loan2);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages.
        double expectedAverage = 375.0;
        double actualAverage = user.calculateAveragePageCountOfBorrowedBooks();
        
        assertEquals("Average page count should be 375.0 for guest user", expectedAverage, actualAverage, 0.001);
    }
}