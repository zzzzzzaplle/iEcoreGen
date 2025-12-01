import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setBook(book1);
        checkOut1.setStartDate(dateFormat.parse("2023-01-10"));
        checkOut1.setEndDate(dateFormat.parse("2023-01-15"));
        
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setBook(book2);
        checkOut2.setStartDate(dateFormat.parse("2023-03-05"));
        checkOut2.setEndDate(dateFormat.parse("2023-03-10"));
        
        CheckOut checkOut3 = new CheckOut();
        checkOut3.setBook(book1);
        checkOut3.setStartDate(dateFormat.parse("2023-05-20"));
        checkOut3.setEndDate(dateFormat.parse("2023-05-25"));
        
        user.getCheckOuts().add(checkOut1);
        user.getCheckOuts().add(checkOut2);
        user.getCheckOuts().add(checkOut3);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Define period: 2023-01-01 to 2023-12-31
        Date start = dateFormat.parse("2023-01-01");
        Date end = dateFormat.parse("2023-12-31");
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_singleUserBorrowingCalculationForGuest() throws ParseException {
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
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setBook(book3);
        checkOut1.setStartDate(dateFormat.parse("2023-06-10"));
        checkOut1.setEndDate(dateFormat.parse("2023-06-15"));
        
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setBook(book4);
        checkOut2.setStartDate(dateFormat.parse("2023-06-20"));
        checkOut2.setEndDate(dateFormat.parse("2023-06-25"));
        
        user.getCheckOuts().add(checkOut1);
        user.getCheckOuts().add(checkOut2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Define period: 2023-06-01 to 2023-06-30
        Date start = dateFormat.parse("2023-06-01");
        Date end = dateFormat.parse("2023-06-30");
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Define period: 2023-07-01 to 2023-07-31
        Date start = dateFormat.parse("2023-07-01");
        Date end = dateFormat.parse("2023-07-31");
        
        // Execute the method to count unique book borrowings for user U003 for the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() throws ParseException {
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
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setBook(book5);
        checkOut1.setStartDate(dateFormat.parse("2023-08-01"));
        checkOut1.setEndDate(dateFormat.parse("2023-08-10"));
        
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setBook(book5);
        checkOut2.setStartDate(dateFormat.parse("2023-08-15"));
        checkOut2.setEndDate(dateFormat.parse("2023-08-25"));
        
        user.getCheckOuts().add(checkOut1);
        user.getCheckOuts().add(checkOut2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Define period: 2023-08-01 to 2023-08-10
        Date start = dateFormat.parse("2023-08-01");
        Date end = dateFormat.parse("2023-08-10");
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        User userEve = new User();
        userEve.setID("U005");
        userEve.setName("Eve");
        userEve.setEmail("eve@example.com");
        userEve.setAddress("202 Birch St.");
        userEve.setType(UserType.MEMBER);
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        User userFrank = new User();
        userFrank.setID("U006");
        userFrank.setName("Frank");
        userFrank.setEmail("frank@example.com");
        userFrank.setAddress("303 Cedar St.");
        userFrank.setType(UserType.GUEST);
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Record CheckOuts for Eve
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setBook(book6);
        checkOut1.setStartDate(dateFormat.parse("2023-09-01"));
        checkOut1.setEndDate(dateFormat.parse("2023-09-10"));
        
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setBook(book7);
        checkOut2.setStartDate(dateFormat.parse("2023-09-15"));
        checkOut2.setEndDate(dateFormat.parse("2023-09-20"));
        
        userEve.getCheckOuts().add(checkOut1);
        userEve.getCheckOuts().add(checkOut2);
        
        // Record CheckOuts for Frank
        CheckOut checkOut3 = new CheckOut();
        checkOut3.setBook(book6);
        checkOut3.setStartDate(dateFormat.parse("2023-09-05"));
        checkOut3.setEndDate(dateFormat.parse("2023-09-15"));
        
        userFrank.getCheckOuts().add(checkOut3);
        
        // Add users to library system
        librarySystem.getUsers().add(userEve);
        librarySystem.getUsers().add(userFrank);
        
        // Define period: 2023-09-01 to 2023-09-30
        Date start = dateFormat.parse("2023-09-01");
        Date end = dateFormat.parse("2023-09-30");
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = librarySystem.uniqueBooksBorrowedByUser(userEve, start, end);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}