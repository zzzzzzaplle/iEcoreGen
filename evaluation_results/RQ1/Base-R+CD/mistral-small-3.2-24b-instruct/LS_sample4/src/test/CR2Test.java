import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() throws ParseException {
        // Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-03-05 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10 23:59:59"));
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate(dateFormat.parse("2023-05-20 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25 23:59:59"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        Date startDate = dateFormat.parse("2023-01-01 00:00:00");
        Date endDate = dateFormat.parse("2023-12-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() throws ParseException {
        // Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate(dateFormat.parse("2023-06-10 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate(dateFormat.parse("2023-06-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25 23:59:59"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        Date startDate = dateFormat.parse("2023-06-01 00:00:00");
        Date endDate = dateFormat.parse("2023-06-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        Date startDate = dateFormat.parse("2023-07-01 00:00:00");
        Date endDate = dateFormat.parse("2023-07-31 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate(dateFormat.parse("2023-08-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate(dateFormat.parse("2023-08-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25 23:59:59"));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        Date startDate = dateFormat.parse("2023-08-01 00:00:00");
        Date endDate = dateFormat.parse("2023-08-10 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        User user1 = new User();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        User user2 = new User();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Record CheckOuts for U005
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10 23:59:59"));
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20 23:59:59"));
        
        user1.addCheckOut(checkout1);
        user1.addCheckOut(checkout2);
        
        // Record CheckOuts for U006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate(dateFormat.parse("2023-09-05 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15 23:59:59"));
        
        user2.addCheckOut(checkout3);
        
        librarySystem.addUser(user1);
        librarySystem.addUser(user2);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        Date startDate = dateFormat.parse("2023-09-01 00:00:00");
        Date endDate = dateFormat.parse("2023-09-30 23:59:59");
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}