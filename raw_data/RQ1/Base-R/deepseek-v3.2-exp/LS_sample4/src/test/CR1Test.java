import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        // Test Case 1: Counting Checkouts for a Book with Multiple Checkouts
        // Setup: Checkout Java Programming book 3 times by Member M001
        
        // First checkout
        Loan loan1 = new Loan();
        loan1.setBook(javaBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        librarySystem.addLoan(loan1);
        
        // Second checkout
        Loan loan2 = new Loan();
        loan2.setBook(javaBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 15));
        librarySystem.addLoan(loan2);
        
        // Third checkout
        Loan loan3 = new Loan();
        loan3.setBook(javaBook);
        loan3.setUser(memberM001);
        loan3.setStartDate(LocalDate.of(2023, 3, 1));
        loan3.setEndDate(LocalDate.of(2023, 3, 15));
        librarySystem.addLoan(loan3);
        
        // Verify the checkout count
        int checkoutCount = librarySystem.getBookCheckoutCount(javaBook);
        assertEquals("Java Programming book should have 3 checkouts", 3, checkoutCount);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Test Case 2: Counting Checkouts for a Book with No Checkouts
        // Setup: Python Basics book has no checkouts
        
        // Verify the checkout count
        int checkoutCount = librarySystem.getBookCheckoutCount(pythonBook);
        assertEquals("Python Basics book should have 0 checkouts", 0, checkoutCount);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // Test Case 3: Counting Checkouts for a Book with One Checkout
        // Setup: Checkout Algorithms book once by Member M001
        
        Loan loan = new Loan();
        loan.setBook(algorithmsBook);
        loan.setUser(memberM001);
        loan.setStartDate(LocalDate.of(2023, 4, 1));
        loan.setEndDate(LocalDate.of(2023, 4, 15));
        librarySystem.addLoan(loan);
        
        // Verify the checkout count
        int checkoutCount = librarySystem.getBookCheckoutCount(algorithmsBook);
        assertEquals("Algorithms book should have 1 checkout", 1, checkoutCount);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
        // Setup: Checkout Data Structures book by Member M001, Guest G001, and Member M002
        
        // Checkout by Member M001
        Loan loan1 = new Loan();
        loan1.setBook(dataStructuresBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 15));
        librarySystem.addLoan(loan1);
        
        // Checkout by Guest G001
        Loan loan2 = new Loan();
        loan2.setBook(dataStructuresBook);
        loan2.setUser(guestG001);
        loan2.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 6, 15));
        librarySystem.addLoan(loan2);
        
        // Checkout by Member M002
        Loan loan3 = new Loan();
        loan3.setBook(dataStructuresBook);
        loan3.setUser(memberM002);
        loan3.setStartDate(LocalDate.of(2023, 7, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 15));
        librarySystem.addLoan(loan3);
        
        // Verify the checkout count
        int checkoutCount = librarySystem.getBookCheckoutCount(dataStructuresBook);
        assertEquals("Data Structures book should have 3 checkouts", 3, checkoutCount);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // Test Case 5: Counting Checkouts for a Book with Same User
        // Setup: Checkout Web Development book twice by the same Member M001
        
        // First checkout
        Loan loan1 = new Loan();
        loan1.setBook(webDevelopmentBook);
        loan1.setUser(memberM001);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 15));
        librarySystem.addLoan(loan1);
        
        // Second checkout
        Loan loan2 = new Loan();
        loan2.setBook(webDevelopmentBook);
        loan2.setUser(memberM001);
        loan2.setStartDate(LocalDate.of(2023, 8, 16));
        loan2.setEndDate(LocalDate.of(2023, 8, 30));
        librarySystem.addLoan(loan2);
        
        // Verify the checkout count
        int checkoutCount = librarySystem.getBookCheckoutCount(webDevelopmentBook);
        assertEquals("Web Development book should have 2 checkouts", 2, checkoutCount);
    }
}