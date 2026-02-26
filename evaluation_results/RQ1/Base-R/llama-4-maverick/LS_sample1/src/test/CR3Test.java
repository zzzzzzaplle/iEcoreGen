import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Library library;
    
    @Before
    public void setUp() {
        library = new Library();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp: Create user U001
        User user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        library.addUser(user);
        
        // SetUp: Create book B001
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        library.addBook(book);
        
        // SetUp: Create CheckOut record for User U001 with book B001
        Loan loan = new Loan(book, "2023-10-01", "2023-10-15");
        user.addLoan(loan);
        book.incrementCheckoutCount();
        
        // Calculate average page count for user U001
        double result = library.calculateAveragePageCount("U001");
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp: Create user U002
        User user = new MemberUser("Bob", "bob@example.com", "456 Elm St.", "U002");
        library.addUser(user);
        
        // SetUp: Create books
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        library.addBook(book1);
        library.addBook(book2);
        
        // SetUp: Create CheckOut records for User U002
        Loan loan1 = new Loan(book1, "2023-09-20", "2023-09-25");
        Loan loan2 = new Loan(book1, "2023-10-20", "2023-10-25");
        Loan loan3 = new Loan(book2, "2023-09-30", "2023-10-05");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        book1.incrementCheckoutCount();
        book1.incrementCheckoutCount();
        book2.incrementCheckoutCount();
        
        // Calculate average page count for user U002
        double result = library.calculateAveragePageCount("U002");
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp: Create user U003 with no borrowed books
        User user = new MemberUser("Charlie", "charlie@example.com", "789 Oak St.", "U003");
        library.addUser(user);
        
        // Calculate average page count for user U003
        double result = library.calculateAveragePageCount("U003");
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp: Create user U004
        User user = new MemberUser("Daisy", "daisy@example.com", "101 Maple St.", "U004");
        library.addUser(user);
        
        // SetUp: Create books
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        
        // SetUp: Create CheckOut records for User U004
        Loan loan1 = new Loan(book1, "2023-09-15", "2023-09-22");
        Loan loan2 = new Loan(book2, "2023-09-25", "2023-10-02");
        Loan loan3 = new Loan(book3, "2023-10-03", "2023-10-10");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        book1.incrementCheckoutCount();
        book2.incrementCheckoutCount();
        book3.incrementCheckoutCount();
        
        // Calculate average page count for user U004
        double result = library.calculateAveragePageCount("U004");
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp: Create guest user U005
        User user = new GuestUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        library.addUser(user);
        
        // SetUp: Create books
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        library.addBook(book1);
        library.addBook(book2);
        
        // SetUp: Create CheckOut records for User U005
        Loan loan1 = new Loan(book1, "2023-08-20", "2023-08-30");
        Loan loan2 = new Loan(book2, "2023-09-01", "2023-09-10");
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        book1.incrementCheckoutCount();
        book2.incrementCheckoutCount();
        
        // Calculate average page count for guest user U005
        double result = library.calculateAveragePageCount("U005");
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, result, 0.001);
    }
}