import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaProgrammingBook;
    private Book pythonBasicsBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private MemberUser memberM001;
    private MemberUser memberM002;
    private GuestUser guestG001;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaProgrammingBook = new Book();
        javaProgrammingBook.setTitle("Java Programming");
        javaProgrammingBook.setBarcode("123456");
        javaProgrammingBook.setIsbn("978-3-16-148410-0");
        javaProgrammingBook.setNumberOfPages(500);
        
        pythonBasicsBook = new Book();
        pythonBasicsBook.setTitle("Python Basics");
        pythonBasicsBook.setBarcode("654321");
        pythonBasicsBook.setIsbn("978-1-23-456789-0");
        pythonBasicsBook.setNumberOfPages(400);
        
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
        memberM001 = new MemberUser();
        memberM001.setId("M001");
        memberM001.setName("Member One");
        
        memberM002 = new MemberUser();
        memberM002.setId("M002");
        memberM002.setName("Member Two");
        
        guestG001 = new GuestUser();
        guestG001.setId("G001");
        guestG001.setName("Guest One");
        
        // Add books and users to library system
        librarySystem.addBook(javaProgrammingBook);
        librarySystem.addBook(pythonBasicsBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Setup: Create three loans for Java Programming book by Member M001
        Loan loan1 = new Loan();
        loan1.setBook(javaProgrammingBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-01-01");
        loan1.setEndDate("2023-01-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(javaProgrammingBook);
        loan2.setUser(memberM001);
        loan2.setStartDate("2023-02-01");
        loan2.setEndDate("2023-02-15");
        
        Loan loan3 = new Loan();
        loan3.setBook(javaProgrammingBook);
        loan3.setUser(memberM001);
        loan3.setStartDate("2023-03-01");
        loan3.setEndDate("2023-03-15");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Test: Count the number of times Java Programming book has been checked out
        int checkoutCount = librarySystem.getBookCheckoutCount(javaProgrammingBook);
        
        // Verify expected output
        assertEquals("Book with 3 checkouts should return checkout count of 3", 3, checkoutCount);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Setup: Python Basics book exists but no loans created for it
        
        // Test: Count the number of times Python Basics book has been checked out
        int checkoutCount = librarySystem.getBookCheckoutCount(pythonBasicsBook);
        
        // Verify expected output
        assertEquals("Book with no checkouts should return checkout count of 0", 0, checkoutCount);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Setup: Create one loan for Algorithms book by Member M001
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate("2023-04-01");
        loan.setEndDate("2023-04-15");
        
        // Add loan to library system
        librarySystem.addLoan(loan);
        
        // Test: Count the number of times Algorithms book has been checked out
        int checkoutCount = librarySystem.getBookCheckoutCount(algorithmsBook);
        
        // Verify expected output
        assertEquals("Book with 1 checkout should return checkout count of 1", 1, checkoutCount);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Setup: Create loans for Data Structures book by different users
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-05-01");
        loan1.setEndDate("2023-05-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guestG001);
        loan2.setStartDate("2023-06-01");
        loan2.setEndDate("2023-06-15");
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(memberM002);
        loan3.setStartDate("2023-07-01");
        loan3.setEndDate("2023-07-15");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Test: Count the number of times Data Structures book has been checked out
        int checkoutCount = librarySystem.getBookCheckoutCount(dataStructuresBook);
        
        // Verify expected output
        assertEquals("Book with 3 checkouts by different users should return checkout count of 3", 3, checkoutCount);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Setup: Create two loans for Web Development book by the same Member M001
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-08-01");
        loan1.setEndDate("2023-08-15");
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(memberM001);
        loan2.setStartDate("2023-08-16");
        loan2.setEndDate("2023-08-30");
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Test: Count the number of times Web Development book has been checked out
        int checkoutCount = librarySystem.getBookCheckoutCount(webDevelopmentBook);
        
        // Verify expected output
        assertEquals("Book with 2 checkouts by same user should return checkout count of 2", 2, checkoutCount);
    }
}