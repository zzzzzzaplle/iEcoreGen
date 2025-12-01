import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 200);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 300);
        
        // Record CheckOuts
        Date startDate1 = dateFormat.parse("2023-01-10");
        Date endDate1 = dateFormat.parse("2023-01-15");
        CheckOut checkOut1 = new CheckOut(startDate1, endDate1, book1);
        
        Date startDate2 = dateFormat.parse("2023-03-05");
        Date endDate2 = dateFormat.parse("2023-03-10");
        CheckOut checkOut2 = new CheckOut(startDate2, endDate2, book2);
        
        Date startDate3 = dateFormat.parse("2023-05-20");
        Date endDate3 = dateFormat.parse("2023-05-25");
        CheckOut checkOut3 = new CheckOut(startDate3, endDate3, book1);
        
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkOut1);
        checkOuts.add(checkOut2);
        checkOuts.add(checkOut3);
        user.setCheckOuts(checkOuts);
        
        // Define period: 2023-01-01 to 2023-12-31
        Date periodStart = dateFormat.parse("2023-01-01");
        Date periodEnd = dateFormat.parse("2023-12-31");
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_singleUserBorrowingCalculationForGuest() throws ParseException {
        // Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        User user = new User("Bob", "bob@example.com", "456 Oak St.", "U002", UserType.GUEST);
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 250);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        
        // Record CheckOuts
        Date startDate1 = dateFormat.parse("2023-06-10");
        Date endDate1 = dateFormat.parse("2023-06-15");
        CheckOut checkOut1 = new CheckOut(startDate1, endDate1, book3);
        
        Date startDate2 = dateFormat.parse("2023-06-20");
        Date endDate2 = dateFormat.parse("2023-06-25");
        CheckOut checkOut2 = new CheckOut(startDate2, endDate2, book4);
        
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkOut1);
        checkOuts.add(checkOut2);
        user.setCheckOuts(checkOuts);
        
        // Define period: 2023-06-01 to 2023-06-30
        Date periodStart = dateFormat.parse("2023-06-01");
        Date periodEnd = dateFormat.parse("2023-06-30");
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        User user = new User("Charlie", "charlie@example.com", "789 Pine St.", "U003", UserType.MEMBER);
        
        // No checkouts recorded for this user
        
        // Define period: 2023-07-01 to 2023-07-31
        Date periodStart = dateFormat.parse("2023-07-01");
        Date periodEnd = dateFormat.parse("2023-07-31");
        
        // Execute the method to count unique book borrowings for user U003 for the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        User user = new User("Diana", "diana@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 400);
        
        // Record CheckOuts
        Date startDate1 = dateFormat.parse("2023-08-01");
        Date endDate1 = dateFormat.parse("2023-08-10");
        CheckOut checkOut1 = new CheckOut(startDate1, endDate1, book5);
        
        Date startDate2 = dateFormat.parse("2023-08-15");
        Date endDate2 = dateFormat.parse("2023-08-25");
        CheckOut checkOut2 = new CheckOut(startDate2, endDate2, book5);
        
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkOut1);
        checkOuts.add(checkOut2);
        user.setCheckOuts(checkOuts);
        
        // Define period: 2023-08-01 to 2023-08-10
        Date periodStart = dateFormat.parse("2023-08-01");
        Date periodEnd = dateFormat.parse("2023-08-10");
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = librarySystem.uniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        User userEve = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.MEMBER);
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        User userFrank = new User("Frank", "frank@example.com", "303 Cedar St.", "U006", UserType.GUEST);
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 150);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 250);
        
        // Record CheckOuts for Eve
        Date startDate1 = dateFormat.parse("2023-09-01");
        Date endDate1 = dateFormat.parse("2023-09-10");
        CheckOut checkOut1 = new CheckOut(startDate1, endDate1, book6);
        
        Date startDate2 = dateFormat.parse("2023-09-15");
        Date endDate2 = dateFormat.parse("2023-09-20");
        CheckOut checkOut2 = new CheckOut(startDate2, endDate2, book7);
        
        List<CheckOut> eveCheckOuts = new ArrayList<>();
        eveCheckOuts.add(checkOut1);
        eveCheckOuts.add(checkOut2);
        userEve.setCheckOuts(eveCheckOuts);
        
        // Record CheckOuts for Frank
        Date startDate3 = dateFormat.parse("2023-09-05");
        Date endDate3 = dateFormat.parse("2023-09-15");
        CheckOut checkOut3 = new CheckOut(startDate3, endDate3, book6);
        
        List<CheckOut> frankCheckOuts = new ArrayList<>();
        frankCheckOuts.add(checkOut3);
        userFrank.setCheckOuts(frankCheckOuts);
        
        // Define period: 2023-09-01 to 2023-09-30
        Date periodStart = dateFormat.parse("2023-09-01");
        Date periodEnd = dateFormat.parse("2023-09-30");
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = librarySystem.uniqueBooksBorrowedByUser(userEve, periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}