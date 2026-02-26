import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private Member memberM001;
    private Member memberM002;
    private Guest guestG001;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create test books
        javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setIsbn("978-3-16-148410-0");
        javaBook.setPages(500);
        
        pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setIsbn("978-1-23-456789-0");
        pythonBook.setPages(400);
        
        algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setIsbn("978-0-12-345678-9");
        algorithmsBook.setPages(700);
        
        dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setIsbn("978-9-87-654321-0");
        dataStructuresBook.setPages(600);
        
        webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setIsbn("978-2-36-547891-0");
        webDevelopmentBook.setPages(450);
        
        // Create test users
        memberM001 = new Member();
        memberM001.setName("Member M001");
        memberM001.setId("M001");
        
        memberM002 = new Member();
        memberM002.setName("Member M002");
        memberM002.setId("M002");
        
        guestG001 = new Guest();
        guestG001.setName("Guest G001");
        guestG001.setId("G001");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Set up: Create loans for Java Programming book with multiple checkouts
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 15));
        
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(memberM001);
        loan3.setStartDate(LocalDate.of(2023, 3, 1));
        loan3.setEndDate(LocalDate.of(2023, 3, 15));
        
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckouts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with multiple checkouts should return count of 3", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Set up: Create Python Basics book but no loans
        // No loans added to library system
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckouts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book with no checkouts should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Set up: Create loan for Algorithms book with one checkout
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate(LocalDate.of(2023, 4, 1));
        loan.setEndDate(LocalDate.of(2023, 4, 15));
        
        librarySystem.getLoans().add(loan);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book with one checkout should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Set up: Create loans for Data Structures book with mix of members and guests
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guestG001);
        loan2.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 6, 15));
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(memberM002);
        loan3.setStartDate(LocalDate.of(2023, 7, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 15));
        
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book with mix of members and guests should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Set up: Create loans for Web Development book with same user
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.of(2023, 8, 16));
        loan2.setEndDate(LocalDate.of(2023, 8, 30));
        
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckouts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book with same user multiple checkouts should return count of 2", 2, result);
    }
}