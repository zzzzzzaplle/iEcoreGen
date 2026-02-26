import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaProgrammingBook;
    private Book pythonBasicsBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private User memberM001;
    private User memberM002;
    private User guestG001;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaProgrammingBook = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        pythonBasicsBook = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        algorithmsBook = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        dataStructuresBook = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        webDevelopmentBook = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create users
        memberM001 = new User("Member One", "member1@library.com", "123 Main St", "M001", UserType.MEMBER);
        memberM002 = new User("Member Two", "member2@library.com", "456 Oak St", "M002", UserType.MEMBER);
        guestG001 = new User("Guest One", "guest1@library.com", "789 Pine St", "G001", UserType.GUEST);
        
        // Add books to library system
        List<Book> books = new ArrayList<>();
        books.add(javaProgrammingBook);
        books.add(pythonBasicsBook);
        books.add(algorithmsBook);
        books.add(dataStructuresBook);
        books.add(webDevelopmentBook);
        librarySystem.setBooks(books);
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() {
        // SetUp: Create checkouts for Java Programming book by Member M001
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15), javaProgrammingBook);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 15), javaProgrammingBook);
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 15), javaProgrammingBook);
        
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        memberM001.addCheckOut(checkout3);
        
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for Java Programming book
        int result = librarySystem.countBookCheckOuts(javaProgrammingBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should be checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: No users have checked out Python Basics book
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for Python Basics book
        int result = librarySystem.countBookCheckOuts(pythonBasicsBook);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals("Book should be checked out 0 times", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() {
        // SetUp: Create one checkout for Algorithms book by Member M001
        CheckOut checkout = new CheckOut(LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 15), algorithmsBook);
        memberM001.addCheckOut(checkout);
        
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for Algorithms book
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals("Book should be checked out 1 time", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() {
        // SetUp: Create checkouts for Data Structures book by different users
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 15), dataStructuresBook);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 15), dataStructuresBook);
        CheckOut checkout3 = new CheckOut(LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 15), dataStructuresBook);
        
        memberM001.addCheckOut(checkout1);
        guestG001.addCheckOut(checkout2);
        memberM002.addCheckOut(checkout3);
        
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        users.add(guestG001);
        users.add(memberM002);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for Data Structures book
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book should be checked out 3 times", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() {
        // SetUp: Create two checkouts for Web Development book by the same Member M001
        CheckOut checkout1 = new CheckOut(LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 15), webDevelopmentBook);
        CheckOut checkout2 = new CheckOut(LocalDate.of(2023, 8, 16), LocalDate.of(2023, 8, 30), webDevelopmentBook);
        
        memberM001.addCheckOut(checkout1);
        memberM001.addCheckOut(checkout2);
        
        List<User> users = new ArrayList<>();
        users.add(memberM001);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for Web Development book
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals("Book should be checked out 2 times", 2, result);
    }
}