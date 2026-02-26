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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_singleUserBorrowingCalculationForMember() throws ParseException {
        // Create a MEMBER user with ID: U001
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("978-3-16-148410-0");
        book1.setBarcode("B001");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setISBN("978-1-4028-9467-7");
        book2.setBarcode("B002");
        book2.setTitle("Book2");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(dateFormat.parse("2023-01-10"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(dateFormat.parse("2023-03-05"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10"));
        user.addCheckOut(checkout2);
        
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book1);
        checkout3.setStartDate(dateFormat.parse("2023-05-20"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25"));
        user.addCheckOut(checkout3);
        
        // Define period
        Date start = dateFormat.parse("2023-01-01");
        Date end = dateFormat.parse("2023-12-31");
        
        // Execute method and verify result
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_singleUserBorrowingCalculationForGuest() throws ParseException {
        // Create a GUEST user with ID: U002
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = new Book();
        book3.setISBN("978-0-1234-5678-9");
        book3.setBarcode("B003");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setISBN("978-1-2345-6789-7");
        book4.setBarcode("B004");
        book4.setTitle("Book4");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book3);
        checkout1.setStartDate(dateFormat.parse("2023-06-10"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book4);
        checkout2.setStartDate(dateFormat.parse("2023-06-20"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25"));
        user.addCheckOut(checkout2);
        
        // Define period
        Date start = dateFormat.parse("2023-06-01");
        Date end = dateFormat.parse("2023-06-30");
        
        // Execute method and verify result
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with ID: U003
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // Define period
        Date start = dateFormat.parse("2023-07-01");
        Date end = dateFormat.parse("2023-07-31");
        
        // Execute method and verify result
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user with ID: U004
        User user = new User();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create book
        Book book5 = new Book();
        book5.setISBN("978-3-16-148410-1");
        book5.setBarcode("B005");
        book5.setTitle("Book5");
        
        // Record CheckOuts
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book5);
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10"));
        user.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book5);
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25"));
        user.addCheckOut(checkout2);
        
        // Define period
        Date start = dateFormat.parse("2023-08-01");
        Date end = dateFormat.parse("2023-08-10");
        
        // Execute method and verify result
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create a MEMBER user with ID: U005
        User userU005 = new User();
        userU005.setID("U005");
        userU005.setName("Eve");
        userU005.setEmail("eve@example.com");
        userU005.setAddress("202 Birch St.");
        userU005.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006
        User userU006 = new User();
        userU006.setID("U006");
        userU006.setName("Frank");
        userU006.setEmail("frank@example.com");
        userU006.setAddress("303 Cedar St.");
        userU006.setType(UserType.GUEST);
        
        // Create books
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setBarcode("B006");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setBarcode("B007");
        book7.setTitle("Book7");
        
        // Record CheckOuts for U005
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book6);
        checkout1.setStartDate(dateFormat.parse("2023-09-01"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10"));
        userU005.addCheckOut(checkout1);
        
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book7);
        checkout2.setStartDate(dateFormat.parse("2023-09-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20"));
        userU005.addCheckOut(checkout2);
        
        // Record CheckOut for U006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book6);
        checkout3.setStartDate(dateFormat.parse("2023-09-05"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15"));
        userU006.addCheckOut(checkout3);
        
        // Define period
        Date start = dateFormat.parse("2023-09-01");
        Date end = dateFormat.parse("2023-09-30");
        
        // Execute method for U005 and verify result
        int result = librarySystem.uniqueBooksBorrowedByUser(userU005, start, end);
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}