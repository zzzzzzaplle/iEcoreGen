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
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Create test books
        javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setIsbn("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setIsbn("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
        algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setIsbn("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setIsbn("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setIsbn("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        
        // Create test users
        memberM001 = new Member();
        memberM001.setId("M001");
        memberM001.setName("Member One");
        
        memberM002 = new Member();
        memberM002.setId("M002");
        memberM002.setName("Member Two");
        
        guestG001 = new Guest();
        guestG001.setId("G001");
        guestG001.setName("Guest One");
        
        // Add users to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getUsers().add(memberM002);
        librarySystem.getUsers().add(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create loan records for Java Programming book with 3 checkouts by Member M001
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.parse("2023-01-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-01-15", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.parse("2023-02-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-02-15", formatter));
        
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(memberM001);
        loan3.setStartDate(LocalDate.parse("2023-03-01", formatter));
        loan3.setEndDate(LocalDate.parse("2023-03-15", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckouts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Java Programming book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Python Basics book exists but has no loan records
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckouts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Python Basics book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create loan record for Algorithms book with 1 checkout by Member M001
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate(LocalDate.parse("2023-04-01", formatter));
        loan.setEndDate(LocalDate.parse("2023-04-15", formatter));
        
        // Add loan to library system
        librarySystem.getLoans().add(loan);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Algorithms book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create loan records for Data Structures book with mixed users
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.parse("2023-05-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-05-15", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guestG001);
        loan2.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-06-15", formatter));
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(memberM002);
        loan3.setStartDate(LocalDate.parse("2023-07-01", formatter));
        loan3.setEndDate(LocalDate.parse("2023-07-15", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Data Structures book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create loan records for Web Development book with same user (M001)
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.parse("2023-08-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-08-15", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.parse("2023-08-16", formatter));
        loan2.setEndDate(LocalDate.parse("2023-08-30", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckouts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Web Development book should have 2 checkouts", 2, result);
    }
}