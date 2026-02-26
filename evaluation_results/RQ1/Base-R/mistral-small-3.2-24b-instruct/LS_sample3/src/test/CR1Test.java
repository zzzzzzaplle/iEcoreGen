import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create book and user
        Book javaBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        User userM001 = new User("Member M001", "m001@example.com", "Address M001", "M001");
        
        // Add book and user to library system
        librarySystem.addBook(javaBook);
        librarySystem.addUser(userM001);
        
        // Checkout the book three times by the same user
        librarySystem.addBookLoan(userM001, javaBook, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15));
        librarySystem.addBookLoan(userM001, javaBook, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 15));
        librarySystem.addBookLoan(userM001, javaBook, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 15));
        
        // Input: Count checkouts for "Java Programming" book
        int actualCheckouts = librarySystem.countBookCheckouts(javaBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times", 3, actualCheckouts);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create book (no user checkout)
        Book pythonBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        
        // Add book to library system (no checkouts performed)
        librarySystem.addBook(pythonBook);
        
        // Input: Count checkouts for "Python Basics" book
        int actualCheckouts = librarySystem.countBookCheckouts(pythonBook);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals("Book should have 0 checkouts", 0, actualCheckouts);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create book and user
        Book algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        User userM001 = new User("Member M001", "m001@example.com", "Address M001", "M001");
        
        // Add book and user to library system
        librarySystem.addBook(algorithmsBook);
        librarySystem.addUser(userM001);
        
        // Checkout the book once
        librarySystem.addBookLoan(userM001, algorithmsBook, LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 15));
        
        // Input: Count checkouts for "Algorithms" book
        int actualCheckouts = librarySystem.countBookCheckouts(algorithmsBook);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals("Book should have been checked out 1 time", 1, actualCheckouts);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create book and multiple users
        Book dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        User userM001 = new User("Member M001", "m001@example.com", "Address M001", "M001");
        User guestG001 = new User("Guest G001", "g001@example.com", "Address G001", "G001");
        User userM002 = new User("Member M002", "m002@example.com", "Address M002", "M002");
        
        // Add book and users to library system
        librarySystem.addBook(dataStructuresBook);
        librarySystem.addUser(userM001);
        librarySystem.addUser(guestG001);
        librarySystem.addUser(userM002);
        
        // Checkout the book by different users
        librarySystem.addBookLoan(userM001, dataStructuresBook, LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 15));
        librarySystem.addBookLoan(guestG001, dataStructuresBook, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 15));
        librarySystem.addBookLoan(userM002, dataStructuresBook, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 15));
        
        // Input: Count checkouts for "Data Structures" book
        int actualCheckouts = librarySystem.countBookCheckouts(dataStructuresBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should have been checked out 3 times by different users", 3, actualCheckouts);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create book and user
        Book webDevBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        User userM001 = new User("Member M001", "m001@example.com", "Address M001", "M001");
        
        // Add book and user to library system
        librarySystem.addBook(webDevBook);
        librarySystem.addUser(userM001);
        
        // Checkout the book twice by the same user
        librarySystem.addBookLoan(userM001, webDevBook, LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 15));
        librarySystem.addBookLoan(userM001, webDevBook, LocalDate.of(2023, 8, 16), LocalDate.of(2023, 8, 30));
        
        // Input: Count checkouts for "Web Development" book
        int actualCheckouts = librarySystem.countBookCheckouts(webDevBook);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals("Book should have been checked out 2 times by the same user", 2, actualCheckouts);
    }
}