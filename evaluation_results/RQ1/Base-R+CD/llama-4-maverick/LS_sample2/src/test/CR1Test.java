import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        
        // Add books to library
        List<Book> books = new ArrayList<>();
        books.add(javaBook);
        books.add(pythonBook);
        books.add(algorithmsBook);
        books.add(dataStructuresBook);
        books.add(webDevelopmentBook);
        librarySystem.setBooks(books);
        
        // Add users to library
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(member2);
        users.add(guest1);
        librarySystem.setUsers(users);
    }
    
    @Test
    public void testCase1_countCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Setup checkouts for Java Programming book
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // First checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(javaBook);
        checkout1.setStartDate(sdf.parse("2023-01-01"));
        checkout1.setEndDate(sdf.parse("2023-01-15"));
        
        // Second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(javaBook);
        checkout2.setStartDate(sdf.parse("2023-02-01"));
        checkout2.setEndDate(sdf.parse("2023-02-15"));
        
        // Third checkout
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(javaBook);
        checkout3.setStartDate(sdf.parse("2023-03-01"));
        checkout3.setEndDate(sdf.parse("2023-03-15"));
        
        // Add checkouts to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        checkouts.add(checkout3);
        member1.setCheckOuts(checkouts);
        
        // Execute test
        int result = librarySystem.countBookCheckOuts(javaBook);
        
        // Verify result
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countCheckoutsForBookWithNoCheckouts() {
        // No checkouts are added to any user for Python Basics book
        
        // Execute test
        int result = librarySystem.countBookCheckOuts(pythonBook);
        
        // Verify result
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countCheckoutsForBookWithOneCheckout() throws ParseException {
        // Setup one checkout for Algorithms book
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(algorithmsBook);
        checkout.setStartDate(sdf.parse("2023-04-01"));
        checkout.setEndDate(sdf.parse("2023-04-15"));
        
        // Add checkout to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout);
        member1.setCheckOuts(checkouts);
        
        // Execute test
        int result = librarySystem.countBookCheckOuts(algorithmsBook);
        
        // Verify result
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Setup checkouts for Data Structures book by different user types
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Checkout by Member M001
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(dataStructuresBook);
        checkout1.setStartDate(sdf.parse("2023-05-01"));
        checkout1.setEndDate(sdf.parse("2023-05-15"));
        
        // Checkout by Guest G001
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(dataStructuresBook);
        checkout2.setStartDate(sdf.parse("2023-06-01"));
        checkout2.setEndDate(sdf.parse("2023-06-15"));
        
        // Checkout by Member M002
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(dataStructuresBook);
        checkout3.setStartDate(sdf.parse("2023-07-01"));
        checkout3.setEndDate(sdf.parse("2023-07-15"));
        
        // Add checkouts to respective users
        member1.setCheckOuts(new ArrayList<>());
        member1.getCheckOuts().add(checkout1);
        
        guest1.setCheckOuts(new ArrayList<>());
        guest1.getCheckOuts().add(checkout2);
        
        member2.setCheckOuts(new ArrayList<>());
        member2.getCheckOuts().add(checkout3);
        
        // Execute test
        int result = librarySystem.countBookCheckOuts(dataStructuresBook);
        
        // Verify result
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countCheckoutsForBookWithSameUser() throws ParseException {
        // Setup multiple checkouts for Web Development book by same user
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // First checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(webDevelopmentBook);
        checkout1.setStartDate(sdf.parse("2023-08-01"));
        checkout1.setEndDate(sdf.parse("2023-08-15"));
        
        // Second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(webDevelopmentBook);
        checkout2.setStartDate(sdf.parse("2023-08-16"));
        checkout2.setEndDate(sdf.parse("2023-08-30"));
        
        // Add checkouts to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        member1.setCheckOuts(checkouts);
        
        // Execute test
        int result = librarySystem.countBookCheckOuts(webDevelopmentBook);
        
        // Verify result
        assertEquals(2, result);
    }
}