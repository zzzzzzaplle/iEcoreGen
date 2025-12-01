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
    private MemberUser memberM001;
    private MemberUser memberM002;
    private GuestUser guestG001;
    
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
        librarySystem.addBook(javaBook);
        librarySystem.addBook(pythonBook);
        librarySystem.addBook(algorithmsBook);
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addBook(webDevelopmentBook);
        
        librarySystem.addUser(memberM001);
        librarySystem.addUser(memberM002);
        librarySystem.addUser(guestG001);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
        
        // Create and add loans for Java Programming book by Member M001
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-01-01");
        loan1.setEndDate("2023-01-15");
        librarySystem.addLoan(loan1);
        
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(memberM001);
        loan2.setStartDate("2023-02-01");
        loan2.setEndDate("2023-02-15");
        librarySystem.addLoan(loan2);
        
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(memberM001);
        loan3.setStartDate("2023-03-01");
        loan3.setEndDate("2023-03-15");
        librarySystem.addLoan(loan3);
        
        // Count checkouts for Java Programming book
        int checkoutCount = librarySystem.getBookCheckoutCount(javaBook);
        
        // Verify the book has been checked out 3 times
        assertEquals("Book with multiple checkouts should have checkout count of 3", 3, checkoutCount);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: "Counting Checkouts for a Book with No Checkouts"
        
        // No loans created for Python Basics book
        
        // Count checkouts for Python Basics book
        int checkoutCount = librarySystem.getBookCheckoutCount(pythonBook);
        
        // Verify the book has been checked out 0 times
        assertEquals("Book with no checkouts should have checkout count of 0", 0, checkoutCount);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: "Counting Checkouts for a Book with One Checkout"
        
        // Create and add loan for Algorithms book by Member M001
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate("2023-04-01");
        loan.setEndDate("2023-04-15");
        librarySystem.addLoan(loan);
        
        // Count checkouts for Algorithms book
        int checkoutCount = librarySystem.getBookCheckoutCount(algorithmsBook);
        
        // Verify the book has been checked out 1 time
        assertEquals("Book with one checkout should have checkout count of 1", 1, checkoutCount);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
        
        // Create and add loans for Data Structures book by different users
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-05-01");
        loan1.setEndDate("2023-05-15");
        librarySystem.addLoan(loan1);
        
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guestG001);
        loan2.setStartDate("2023-06-01");
        loan2.setEndDate("2023-06-15");
        librarySystem.addLoan(loan2);
        
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(memberM002);
        loan3.setStartDate("2023-07-01");
        loan3.setEndDate("2023-07-15");
        librarySystem.addLoan(loan3);
        
        // Count checkouts for Data Structures book
        int checkoutCount = librarySystem.getBookCheckoutCount(dataStructuresBook);
        
        // Verify the book has been checked out 3 times
        assertEquals("Book with mix of members and guests should have checkout count of 3", 3, checkoutCount);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: "Counting Checkouts for a Book with Same User"
        
        // Create and add loans for Web Development book by same user (M001)
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(memberM001);
        loan1.setStartDate("2023-08-01");
        loan1.setEndDate("2023-08-15");
        librarySystem.addLoan(loan1);
        
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(memberM001);
        loan2.setStartDate("2023-08-16");
        loan2.setEndDate("2023-08-30");
        librarySystem.addLoan(loan2);
        
        // Count checkouts for Web Development book
        int checkoutCount = librarySystem.getBookCheckoutCount(webDevelopmentBook);
        
        // Verify the book has been checked out 2 times
        assertEquals("Book with same user checking out multiple times should have checkout count of 2", 2, checkoutCount);
    }
}