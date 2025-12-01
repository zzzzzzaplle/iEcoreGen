import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        
        // Create books
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
        
        // Create users
        member1 = new Member();
        member1.setId("M001");
        member1.setName("Member One");
        
        member2 = new Member();
        member2.setId("M002");
        member2.setName("Member Two");
        
        guest1 = new Guest();
        guest1.setId("G001");
        guest1.setName("Guest One");
        
        // Add books and users to library system
        librarySystem.addBook(javaBook);
        librarySystem.addBook(pythonBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(guest1);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Setup: Create loans for Java Programming book with Member M001 (3 times)
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(member1);
        loan1.setStartDate("2023-01-01");
        loan1.setEndDate("2023-01-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(member1);
        loan2.setStartDate("2023-02-01");
        loan2.setEndDate("2023-02-15");
        
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(member1);
        loan3.setStartDate("2023-03-01");
        loan3.setEndDate("2023-03-15");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Test: Count checkouts for Java Programming book
        int result = librarySystem.getBookCheckoutCount(javaBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Java Programming book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Setup: Python Basics book has no loans (setup already done in @Before)
        
        // Test: Count checkouts for Python Basics book
        int result = librarySystem.getBookCheckoutCount(pythonBook);
        
        // Verify: The book has been checked out 0 times
        assertEquals("Python Basics book should have 0 checkouts", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Setup: Create loan for Algorithms book with Member M001 (1 time)
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(member1);
        loan.setStartDate("2023-04-01");
        loan.setEndDate("2023-04-15");
        
        // Add loan to library system
        librarySystem.addLoan(loan);
        
        // Test: Count checkouts for Algorithms book
        int result = librarySystem.getBookCheckoutCount(algorithmsBook);
        
        // Verify: The book has been checked out 1 time
        assertEquals("Algorithms book should have 1 checkout", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Setup: Create loans for Data Structures book with different users
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(member1);
        loan1.setStartDate("2023-05-01");
        loan1.setEndDate("2023-05-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guest1);
        loan2.setStartDate("2023-06-01");
        loan2.setEndDate("2023-06-15");
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(member2);
        loan3.setStartDate("2023-07-01");
        loan3.setEndDate("2023-07-15");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Test: Count checkouts for Data Structures book
        int result = librarySystem.getBookCheckoutCount(dataStructuresBook);
        
        // Verify: The book has been checked out 3 times
        assertEquals("Data Structures book should have 3 checkouts", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Setup: Create loans for Web Development book with same user (Member M001)
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(member1);
        loan1.setStartDate("2023-08-01");
        loan1.setEndDate("2023-08-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(member1);
        loan2.setStartDate("2023-08-16");
        loan2.setEndDate("2023-08-30");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Test: Count checkouts for Web Development book
        int result = librarySystem.getBookCheckoutCount(webDevelopmentBook);
        
        // Verify: The book has been checked out 2 times
        assertEquals("Web Development book should have 2 checkouts", 2, result);
    }
}