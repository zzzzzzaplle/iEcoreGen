import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private Member member1;
    private Member member2;
    private Guest guest1;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
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
        member1 = new Member();
        member1.setId("M001");
        member1.setName("Member One");
        
        member2 = new Member();
        member2.setId("M002");
        member2.setName("Member Two");
        
        guest1 = new Guest();
        guest1.setId("G001");
        guest1.setName("Guest One");
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() {
        // Set up: Create loan records for Java Programming book
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(member1);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(member1);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 15));
        
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(member1);
        loan3.setStartDate(LocalDate.of(2023, 3, 1));
        loan3.setEndDate(LocalDate.of(2023, 3, 15));
        
        List<Loan> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
        librarySystem.setLoans(loans);
        
        // Execute: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckouts(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() {
        // Set up: No loans for Python Basics book
        librarySystem.setLoans(new ArrayList<>());
        
        // Execute: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckouts(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() {
        // Set up: Create one loan record for Algorithms book
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(member1);
        loan.setStartDate(LocalDate.of(2023, 4, 1));
        loan.setEndDate(LocalDate.of(2023, 4, 15));
        
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        librarySystem.setLoans(loans);
        
        // Execute: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Set up: Create loan records for Data Structures book with different users
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(member1);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guest1);
        loan2.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 6, 15));
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(member2);
        loan3.setStartDate(LocalDate.of(2023, 7, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 15));
        
        List<Loan> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
        librarySystem.setLoans(loans);
        
        // Execute: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() {
        // Set up: Create two loan records for Web Development book by the same user
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(member1);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(member1);
        loan2.setStartDate(LocalDate.of(2023, 8, 16));
        loan2.setEndDate(LocalDate.of(2023, 8, 30));
        
        List<Loan> loans = new ArrayList<>();
        loans.add(loan1);
        loans.add(loan2);
        librarySystem.setLoans(loans);
        
        // Execute: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckouts(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Book should have 2 checkouts", 2, result);
    }
}