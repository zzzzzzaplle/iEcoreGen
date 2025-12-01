import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaProgrammingBook;
    private Book pythonBasicsBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private Member memberM001;
    private Member memberM002;
    private Guest guestG001;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaProgrammingBook = new Book();
        javaProgrammingBook.setTitle("Java Programming");
        javaProgrammingBook.setBarcode("123456");
        javaProgrammingBook.setIsbn("978-3-16-148410-0");
        javaProgrammingBook.setPages(500);
        
        pythonBasicsBook = new Book();
        pythonBasicsBook.setTitle("Python Basics");
        pythonBasicsBook.setBarcode("654321");
        pythonBasicsBook.setIsbn("978-1-23-456789-0");
        pythonBasicsBook.setPages(400);
        
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
        
        // Create users
        memberM001 = new Member();
        memberM001.setId("M001");
        memberM001.setName("Member One");
        
        memberM002 = new Member();
        memberM002.setId("M002");
        memberM002.setName("Member Two");
        
        guestG001 = new Guest();
        guestG001.setId("G001");
        guestG001.setName("Guest One");
        
        // Add books and users to library system
        librarySystem.getBooks().add(javaProgrammingBook);
        librarySystem.getBooks().add(pythonBasicsBook);
        librarySystem.getBooks().add(algorithmsBook);
        librarySystem.getBooks().add(dataStructuresBook);
        librarySystem.getBooks().add(webDevelopmentBook);
        
        librarySystem.getUsers().add(memberM001);
        librarySystem.getUsers().add(memberM002);
        librarySystem.getUsers().add(guestG001);
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() {
        // Test Case 1: "Counting Checkouts for a Book with Multiple Checkouts"
        
        // SetUp: Create loans for Java Programming book
        Loan loan1 = new Loan();
        loan1.setBook(javaProgrammingBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(javaProgrammingBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 15));
        
        Loan loan3 = new Loan();
        loan3.setBook(javaProgrammingBook);
        loan3.setUser(memberM001);
        loan3.setStartDate(LocalDate.of(2023, 3, 1));
        loan3.setEndDate(LocalDate.of(2023, 3, 15));
        
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = librarySystem.countBookCheckouts(javaProgrammingBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: "Counting Checkouts for a Book with No Checkouts"
        
        // SetUp: No User check out Python Basics Book (no loans added)
        
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = librarySystem.countBookCheckouts(pythonBasicsBook);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: "Counting Checkouts for a Book with One Checkout"
        
        // SetUp: Create loan for Algorithms book
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate(LocalDate.of(2023, 4, 1));
        loan.setEndDate(LocalDate.of(2023, 4, 15));
        
        librarySystem.getLoans().add(loan);
        
        // Input: Count the number of times the book with title "Algorithms" has been checked out
        int result = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: "Counting Checkouts for a Book with Mix of Members and Guests"
        
        // SetUp: Create loans for Data Structures book by different users
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
        
        // Input: Count the number of times the book with title "Data Structures" has been checked out
        int result = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() {
        // Test Case 5: "Counting Checkouts for a Book with Same User"
        
        // SetUp: Create multiple loans for Web Development book by the same user
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
        
        // Input: Count the number of times the book with title "Web Development" has been checked out
        int result = librarySystem.countBookCheckouts(webDevelopmentBook);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals(2, result);
    }
}