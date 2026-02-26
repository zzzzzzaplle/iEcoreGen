import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Map<String, Book> books;
    private Map<String, User> users;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        books = new HashMap<>();
        users = new HashMap<>();
        librarySystem.setBooks(books);
        librarySystem.setUsers(users);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create book and check it out 3 times by the same user
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setIsbn("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        User user = new User();
        user.setId("M001");
        
        // First checkout
        Loan loan1 = new Loan();
        loan1.setBook(book);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        user.addLoan(loan1);
        book.incrementCheckoutCount();
        
        // Second checkout
        Loan loan2 = new Loan();
        loan2.setBook(book);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 15));
        user.addLoan(loan2);
        book.incrementCheckoutCount();
        
        // Third checkout
        Loan loan3 = new Loan();
        loan3.setBook(book);
        loan3.setStartDate(LocalDate.of(2023, 3, 1));
        loan3.setEndDate(LocalDate.of(2023, 3, 15));
        user.addLoan(loan3);
        book.incrementCheckoutCount();
        
        // Add book and user to library system
        books.put("123456", book);
        users.put("M001", user);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckouts("123456");
        
        // Verify: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create book but no checkouts
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setIsbn("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // Add book to library system (no user or loans needed)
        books.put("654321", book);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckouts("654321");
        
        // Verify: The book has been checked out 0 times
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create book and check it out once
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setIsbn("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        User user = new User();
        user.setId("M001");
        
        // Single checkout
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setStartDate(LocalDate.of(2023, 4, 1));
        loan.setEndDate(LocalDate.of(2023, 4, 15));
        user.addLoan(loan);
        book.incrementCheckoutCount();
        
        // Add book and user to library system
        books.put("789012", book);
        users.put("M001", user);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckouts("789012");
        
        // Verify: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create book and check it out by multiple users
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setIsbn("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        User user1 = new User();
        user1.setId("M001");
        User user2 = new User();
        user2.setId("G001");
        User user3 = new User();
        user3.setId("M002");
        
        // First checkout by M001
        Loan loan1 = new Loan();
        loan1.setBook(book);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 15));
        user1.addLoan(loan1);
        book.incrementCheckoutCount();
        
        // Second checkout by G001
        Loan loan2 = new Loan();
        loan2.setBook(book);
        loan2.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 6, 15));
        user2.addLoan(loan2);
        book.incrementCheckoutCount();
        
        // Third checkout by M002
        Loan loan3 = new Loan();
        loan3.setBook(book);
        loan3.setStartDate(LocalDate.of(2023, 7, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 15));
        user3.addLoan(loan3);
        book.incrementCheckoutCount();
        
        // Add book and users to library system
        books.put("101112", book);
        users.put("M001", user1);
        users.put("G001", user2);
        users.put("M002", user3);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckouts("101112");
        
        // Verify: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create book and check it out twice by the same user
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setIsbn("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        User user = new User();
        user.setId("M001");
        
        // First checkout
        Loan loan1 = new Loan();
        loan1.setBook(book);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 15));
        user.addLoan(loan1);
        book.incrementCheckoutCount();
        
        // Second checkout
        Loan loan2 = new Loan();
        loan2.setBook(book);
        loan2.setStartDate(LocalDate.of(2023, 8, 16));
        loan2.setEndDate(LocalDate.of(2023, 8, 30));
        user.addLoan(loan2);
        book.incrementCheckoutCount();
        
        // Add book and user to library system
        books.put("131415", book);
        users.put("M001", user);
        
        // Execute: Count checkouts for the book
        int result = librarySystem.countBookCheckouts("131415");
        
        // Verify: The book has been checked out 2 times
        assertEquals(2, result);
    }
}