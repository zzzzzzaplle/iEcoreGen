import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private User memberUser;
    private User guestUser;
    private User userCharlie;
    private User userDiana;
    private User userEve;
    private User userFrank;
    
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;
    private Book book6;
    private Book book7;
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        // Initialize library system
        librarySystem = new LibrarySystem();
        
        // Create books
        book1 = new Book("Book1", "B001", "978-3-16-148410-0", 300);
        book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 250);
        book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 400);
        book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        book5 = new Book("Book5", "B005", "978-3-16-148410-1", 280);
        book6 = new Book("Book6", "B006", "978-0-321-56789-1", 320);
        book7 = new Book("Book7", "B007", "978-0-12-345678-9", 290);
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        librarySystem.addBook(book5);
        librarySystem.addBook(book6);
        librarySystem.addBook(book7);
        
        // Create users
        memberUser = new User("Alice", "alice@example.com", "123 Main St.", "U001");
        guestUser = new User("Bob", "bob@example.com", "456 Oak St.", "U002");
        userCharlie = new User("Charlie", "charlie@example.com", "789 Pine St.", "U003");
        userDiana = new User("Diana", "diana@example.com", "101 Maple St.", "U004");
        userEve = new User("Eve", "eve@example.com", "202 Birch St.", "U005");
        userFrank = new User("Frank", "frank@example.com", "303 Cedar St.", "U006");
        
        // Add users to library system
        librarySystem.addUser(memberUser);
        librarySystem.addUser(guestUser);
        librarySystem.addUser(userCharlie);
        librarySystem.addUser(userDiana);
        librarySystem.addUser(userEve);
        librarySystem.addUser(userFrank);
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create book loans for member user
        LocalDate startDate1 = LocalDate.of(2023, 1, 10);
        LocalDate endDate1 = LocalDate.of(2023, 1, 15);
        LocalDate startDate2 = LocalDate.of(2023, 3, 5);
        LocalDate endDate2 = LocalDate.of(2023, 3, 10);
        LocalDate startDate3 = LocalDate.of(2023, 5, 20);
        LocalDate endDate3 = LocalDate.of(2023, 5, 25);
        
        librarySystem.addBookLoan(memberUser, book1, startDate1, endDate1);
        librarySystem.addBookLoan(memberUser, book2, startDate2, endDate2);
        librarySystem.addBookLoan(memberUser, book1, startDate3, endDate3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate periodStart = LocalDate.of(2023, 1, 1);
        LocalDate periodEnd = LocalDate.of(2023, 12, 31);
        int result = memberUser.countUniqueBooksBorrowedDuringPeriod(periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create book loans for guest user
        LocalDate startDate1 = LocalDate.of(2023, 6, 10);
        LocalDate endDate1 = LocalDate.of(2023, 6, 15);
        LocalDate startDate2 = LocalDate.of(2023, 6, 20);
        LocalDate endDate2 = LocalDate.of(2023, 6, 25);
        
        librarySystem.addBookLoan(guestUser, book3, startDate1, endDate1);
        librarySystem.addBookLoan(guestUser, book4, startDate2, endDate2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate periodStart = LocalDate.of(2023, 6, 1);
        LocalDate periodEnd = LocalDate.of(2023, 6, 30);
        int result = guestUser.countUniqueBooksBorrowedDuringPeriod(periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: No book loans for user Charlie
        
        // Execute the method to count unique book borrowings for user U003 for the period
        LocalDate periodStart = LocalDate.of(2023, 7, 1);
        LocalDate periodEnd = LocalDate.of(2023, 7, 31);
        int result = userCharlie.countUniqueBooksBorrowedDuringPeriod(periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no book loans should have 0 unique books borrowed", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create overlapping book loans for user Diana
        LocalDate startDate1 = LocalDate.of(2023, 8, 1);
        LocalDate endDate1 = LocalDate.of(2023, 8, 10);
        LocalDate startDate2 = LocalDate.of(2023, 8, 15);
        LocalDate endDate2 = LocalDate.of(2023, 8, 25);
        
        librarySystem.addBookLoan(userDiana, book5, startDate1, endDate1);
        librarySystem.addBookLoan(userDiana, book5, startDate2, endDate2);
        
        // Execute the method to count unique book borrowings for user U004 from the specified period
        LocalDate periodStart = LocalDate.of(2023, 8, 1);
        LocalDate periodEnd = LocalDate.of(2023, 8, 10);
        int result = userDiana.countUniqueBooksBorrowedDuringPeriod(periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("User with overlapping checkout periods should have 1 unique book borrowed", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create book loans for multiple users
        LocalDate startDate1 = LocalDate.of(2023, 9, 1);
        LocalDate endDate1 = LocalDate.of(2023, 9, 10);
        LocalDate startDate2 = LocalDate.of(2023, 9, 15);
        LocalDate endDate2 = LocalDate.of(2023, 9, 20);
        LocalDate startDate3 = LocalDate.of(2023, 9, 5);
        LocalDate endDate3 = LocalDate.of(2023, 9, 15);
        
        librarySystem.addBookLoan(userEve, book6, startDate1, endDate1);
        librarySystem.addBookLoan(userEve, book7, startDate2, endDate2);
        librarySystem.addBookLoan(userFrank, book6, startDate3, endDate3);
        
        // Execute the method to count unique book borrowings for user U005 from the specified period
        LocalDate periodStart = LocalDate.of(2023, 9, 1);
        LocalDate periodEnd = LocalDate.of(2023, 9, 30);
        int result = userEve.countUniqueBooksBorrowedDuringPeriod(periodStart, periodEnd);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("User Eve should have borrowed 2 unique books", 2, result);
    }
}