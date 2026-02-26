import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private Book javaBook;
    private Book pythonBook;
    private Book algorithmsBook;
    private Book dataStructuresBook;
    private Book webDevelopmentBook;
    private User member1;
    private User member2;
    private User guest1;
    
    @Before
    public void setUp() throws ParseException {
        librarySystem = new LibrarySystem();
        
        // Create books
        javaBook = new Book();
        javaBook.setTitle("Java Programming");
        javaBook.setBarcode("123456");
        javaBook.setISBN("978-3-16-148410-0");
        javaBook.setNumberOfPages(500);
        
        pythonBook = new Book();
        pythonBook.setTitle("Python Basics");
        pythonBook.setBarcode("654321");
        pythonBook.setISBN("978-1-23-456789-0");
        pythonBook.setNumberOfPages(400);
        
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
        
        // Add books to library
        librarySystem.getBooks().add(javaBook);
        librarySystem.getBooks().add(pythonBook);
        librarySystem.getBooks().add(algorithmsBook);
        librarySystem.getBooks().add(dataStructuresBook);
        librarySystem.getBooks().add(webDevelopmentBook);
        
        // Create users
        member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        guest1 = new User();
        guest1.setID("G001");
        guest1.setType(UserType.GUEST);
        
        // Add users to library
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(member2);
        librarySystem.getUsers().add(guest1);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        // Set up checkouts for Test Case 1
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        member1.getCheckOuts().add(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        member1.getCheckOuts().add(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        member1.getCheckOuts().add(checkout3);
        
        // Set up checkouts for Test Case 3
        CheckOut checkout4 = new CheckOut();
        checkout4.setBook(algorithmsBook);
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-15"));
        member1.getCheckOuts().add(checkout4);
        
        // Set up checkouts for Test Case 4
        CheckOut checkout5 = new CheckOut();
        checkout5.setBook(dataStructuresBook);
        checkout5.setStartDate(dateFormat.parse("2023-05-01"));
        checkout5.setEndDate(dateFormat.parse("2023-05-15"));
        member1.getCheckOuts().add(checkout5);
        
        CheckOut checkout6 = new CheckOut();
        checkout6.setBook(dataStructuresBook);
        checkout6.setStartDate(dateFormat.parse("2023-06-01"));
        checkout6.setEndDate(dateFormat.parse("2023-06-15"));
        guest1.getCheckOuts().add(checkout6);
        
        CheckOut checkout7 = new CheckOut();
        checkout7.setBook(dataStructuresBook);
        checkout7.setStartDate(dateFormat.parse("2023-07-01"));
        checkout7.setEndDate(dateFormat.parse("2023-07-15"));
        member2.getCheckOuts().add(checkout7);
        
        // Set up checkouts for Test Case 5
        CheckOut checkout8 = new CheckOut();
        checkout8.setBook(webDevelopmentBook);
        checkout8.setStartDate(dateFormat.parse("2023-08-01"));
        checkout8.setEndDate(dateFormat.parse("2023-08-15"));
        member1.getCheckOuts().add(checkout8);
        
        CheckOut checkout9 = new CheckOut();
        checkout9.setBook(webDevelopmentBook);
        checkout9.setStartDate(dateFormat.parse("2023-08-16"));
        checkout9.setEndDate(dateFormat.parse("2023-08-30"));
        member1.getCheckOuts().add(checkout9);
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() {
        // Input: Count the number of times the book with title "Java Programming" has been checked out
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() {
        // Input: Count the number of times the book with title "Python Basics" has been checked out
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
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