import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private LibrarySystem librarySystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_singleMemberUserBorrowingCalculation() {
        // Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        librarySystem.addUser(user);
        
        // Create books
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // Record checkouts
        LocalDate startDate1 = LocalDate.parse("2023-01-10", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-01-15", formatter);
        librarySystem.createBookLoan(book1, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-03-05", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-03-10", formatter);
        librarySystem.createBookLoan(book2, user, startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.parse("2023-05-20", formatter);
        LocalDate endDate3 = LocalDate.parse("2023-05-25", formatter);
        librarySystem.createBookLoan(book1, user, startDate3, endDate3);
        
        // Execute method for period 2023-01-01 to 2023-12-31
        LocalDate periodStart = LocalDate.parse("2023-01-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-12-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_singleGuestUserBorrowingCalculation() {
        // Create a GUEST user
        GuestUser user = new GuestUser();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        librarySystem.addUser(user);
        
        // Create books
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Record checkouts
        LocalDate startDate1 = LocalDate.parse("2023-06-10", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-06-15", formatter);
        librarySystem.createBookLoan(book3, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-06-20", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-06-25", formatter);
        librarySystem.createBookLoan(book4, user, startDate2, endDate2);
        
        // Execute method for period 2023-06-01 to 2023-06-30
        LocalDate periodStart = LocalDate.parse("2023-06-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-06-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() {
        // Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        librarySystem.addUser(user);
        
        // Execute method for period 2023-07-01 to 2023-07-31 (no checkouts recorded)
        LocalDate periodStart = LocalDate.parse("2023-07-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-07-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() {
        // Create a MEMBER user
        MemberUser user = new MemberUser();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        librarySystem.addUser(user);
        
        // Create a book
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        librarySystem.addBook(book5);
        
        // Record checkouts
        LocalDate startDate1 = LocalDate.parse("2023-08-01", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-08-10", formatter);
        librarySystem.createBookLoan(book5, user, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-08-15", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-08-25", formatter);
        librarySystem.createBookLoan(book5, user, startDate2, endDate2);
        
        // Execute method for period 2023-08-01 to 2023-08-10
        LocalDate periodStart = LocalDate.parse("2023-08-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-08-10", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUser(user, periodStart, periodEnd);
        
        // Verify expected output
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() {
        // Create MEMBER user U005
        MemberUser userU005 = new MemberUser();
        userU005.setId("U005");
        userU005.setName("Eve");
        userU005.setEmail("eve@example.com");
        userU005.setAddress("202 Birch St.");
        librarySystem.addUser(userU005);
        
        // Create GUEST user U006
        GuestUser userU006 = new GuestUser();
        userU006.setId("U006");
        userU006.setName("Frank");
        userU006.setEmail("frank@example.com");
        userU006.setAddress("303 Cedar St.");
        librarySystem.addUser(userU006);
        
        // Create books
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        librarySystem.addBook(book6);
        librarySystem.addBook(book7);
        
        // Record checkouts
        LocalDate startDate1 = LocalDate.parse("2023-09-01", formatter);
        LocalDate endDate1 = LocalDate.parse("2023-09-10", formatter);
        librarySystem.createBookLoan(book6, userU005, startDate1, endDate1);
        
        LocalDate startDate2 = LocalDate.parse("2023-09-15", formatter);
        LocalDate endDate2 = LocalDate.parse("2023-09-20", formatter);
        librarySystem.createBookLoan(book7, userU005, startDate2, endDate2);
        
        LocalDate startDate3 = LocalDate.parse("2023-09-05", formatter);
        LocalDate endDate3 = LocalDate.parse("2023-09-15", formatter);
        librarySystem.createBookLoan(book6, userU006, startDate3, endDate3);
        
        // Execute method for user U005 from 2023-09-01 to 2023-09-30
        LocalDate periodStart = LocalDate.parse("2023-09-01", formatter);
        LocalDate periodEnd = LocalDate.parse("2023-09-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUser(userU005, periodStart, periodEnd);
        
        // Verify expected output
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}