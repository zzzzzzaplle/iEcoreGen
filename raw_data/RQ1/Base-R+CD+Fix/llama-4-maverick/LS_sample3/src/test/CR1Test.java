import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // SetUp: Create book and checkouts
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Create first checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 00:00:00"));
        
        // Create second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15 00:00:00"));
        
        // Create third checkout
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15 00:00:00"));
        
        // Add checkouts to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        checkouts.add(checkout3);
        user.setCheckOuts(checkouts);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // SetUp: Create book with no checkouts
        Book book = new Book();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        
        // No users or checkouts are added to the library system
        
        // Input: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // SetUp: Create book and one checkout
        Book book = new Book();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Create checkout
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-04-15 00:00:00"));
        
        // Add checkout to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout);
        user.setCheckOuts(checkouts);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // SetUp: Create book and checkouts by different users
        Book book = new Book();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        
        // Create Member M001
        User member1 = new User();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15 00:00:00"));
        
        List<CheckOut> checkouts1 = new ArrayList<>();
        checkouts1.add(checkout1);
        member1.setCheckOuts(checkouts1);
        
        // Create Guest G001
        User guest = new User();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15 00:00:00"));
        
        List<CheckOut> checkouts2 = new ArrayList<>();
        checkouts2.add(checkout2);
        guest.setCheckOuts(checkouts2);
        
        // Create Member M002
        User member2 = new User();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15 00:00:00"));
        
        List<CheckOut> checkouts3 = new ArrayList<>();
        checkouts3.add(checkout3);
        member2.setCheckOuts(checkouts3);
        
        // Add all users to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(guest);
        users.add(member2);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // SetUp: Create book and multiple checkouts by same user
        Book book = new Book();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        
        User user = new User();
        user.setID("M001");
        user.setType(UserType.MEMBER);
        
        // Create first checkout
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15 00:00:00"));
        
        // Create second checkout
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        
        // Add both checkouts to user
        List<CheckOut> checkouts = new ArrayList<>();
        checkouts.add(checkout1);
        checkouts.add(checkout2);
        user.setCheckOuts(checkouts);
        
        // Add user to library system
        List<User> users = new ArrayList<>();
        users.add(user);
        librarySystem.setUsers(users);
        
        // Input: Count checkouts for the book
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals(2, result);
    }
}