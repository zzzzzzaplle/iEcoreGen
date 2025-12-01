import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
    public void setUp() throws ParseException {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaProgrammingBook = new Book();
        javaProgrammingBook.setTitle("Java Programming");
        javaProgrammingBook.setBarcode("123456");
        javaProgrammingBook.setISBN("978-3-16-148410-0");
        javaProgrammingBook.setNumberOfPages(500);
        
        pythonBasicsBook = new Book();
        pythonBasicsBook.setTitle("Python Basics");
        pythonBasicsBook.setBarcode("654321");
        pythonBasicsBook.setISBN("978-1-23-456789-0");
        pythonBasicsBook.setNumberOfPages(400);
        
        algorithmsBook = new Book();
        algorithmsBook.setTitle("Algorithms");
        algorithmsBook.setBarcode("789012");
        algorithmsBook.setISBN("978-0-12-345678-9");
        algorithmsBook.setNumberOfPages(700);
        
        dataStructuresBook = new Book();
        dataStructuresBook.setTitle("Data Structures");
        dataStructuresBook.setBarcode("101112");
        dataStructuresBook.setISBN("978-9-87-654321-0");
        dataStructuresBook.setNumberOfPages(600);
        
        webDevelopmentBook = new Book();
        webDevelopmentBook.setTitle("Web Development");
        webDevelopmentBook.setBarcode("131415");
        webDevelopmentBook.setISBN("978-2-36-547891-0");
        webDevelopmentBook.setNumberOfPages(450);
        
        // Add books to library system
        librarySystem.getBooks().add(javaProgrammingBook);
        librarySystem.getBooks().add(pythonBasicsBook);
        librarySystem.getBooks().add(algorithmsBook);
        librarySystem.getBooks().add(dataStructuresBook);
        librarySystem.getBooks().add(webDevelopmentBook);
        
        // Create users
        memberM001 = new User();
        memberM001.setID("M001");
        memberM001.setType(UserType.MEMBER);
        
        memberM002 = new User();
        memberM002.setID("M002");
        memberM002.setType(UserType.MEMBER);
        
        guestG001 = new User();
        guestG001.setID("G001");
        guestG001.setType(UserType.GUEST);
        
        // Add users to library system
        librarySystem.getUsers().add(memberM001);
        librarySystem.getUsers().add(memberM002);
        librarySystem.getUsers().add(guestG001);
        
        // Set up date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Test Case 1 Setup
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaProgrammingBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        memberM001.getCheckOuts().add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaProgrammingBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        memberM001.getCheckOuts().add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaProgrammingBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        memberM001.getCheckOuts().add(checkout3);
        
        // Test Case 3 Setup
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(algorithmsBook);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-15"));
        memberM001.getCheckOuts().add(checkout4);
        
        // Test Case 4 Setup
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(dataStructuresBook);
        checkout5.setStartDate(dateFormat.parse("2023-05-01"));
        checkout5.setEndDate(dateFormat.parse("2023-05-15"));
        memberM001.getCheckOuts().add(checkout5);
        
        CheckOut checkout6 = new CheckOut();
        checkout6.setBook(dataStructuresBook);
        checkout6.setStartDate(dateFormat.parse("2023-06-01"));
        checkout6.setEndDate(dateFormat.parse("2023-06-15"));
        guestG001.getCheckOuts().add(checkout6);
        
        CheckOut checkout7 = new CheckOut();
        checkout7.setBook(dataStructuresBook);
        checkout7.setStartDate(dateFormat.parse("2023-07-01"));
        checkout7.setEndDate(dateFormat.parse("2023-07-15"));
        memberM002.getCheckOuts().add(checkout7);
        
        // Test Case 5 Setup
        CheckOut checkout8 = new CheckOut();
        checkout8.setBook(webDevelopmentBook);
        checkout8.setStartDate(dateFormat.parse("2023-08-01"));
        checkout8.setEndDate(dateFormat.parse("2023-08-15"));
        memberM001.getCheckOuts().add(checkout8);
        
        CheckOut checkout9 = new CheckOut();
        checkout9.setBook(webDevelopmentBook);
        checkout9.setStartDate(dateFormat.parse("2023-08-16"));
        checkout9.setEndDate(dateFormat.parse("2023-08-30"));
        memberM001.getCheckOuts().add(checkout9);
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() {
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = librarySystem.countBookCheckOuts(javaProgrammingBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() {
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = librarySystem.countBookCheckOuts(pythonBasicsBook);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() {
        // Input: Count the number of times the book with title "Algorithms" has been checked out
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() {
        // Input: Count the number of times the book with title "Data Structures" has been checked out
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() {
        // Input: Count the number of times the book with title "Web Development" has been checked out
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals(2, result);
    }
}