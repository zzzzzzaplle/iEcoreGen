import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        MemberUser user = new MemberUser();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        // Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setBook(book1);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-01-10");
        checkout1.setEndDate("2023-01-15");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book2);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-03-05");
        checkout2.setEndDate("2023-03-10");
        
        Loan checkout3 = new Loan();
        checkout3.setBook(book1);
        checkout3.setUser(user);
        checkout3.setStartDate("2023-05-20");
        checkout3.setEndDate("2023-05-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        user.addLoan(checkout3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        GuestUser user = new GuestUser();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        // Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setBook(book3);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-06-10");
        checkout1.setEndDate("2023-06-15");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book4);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-06-20");
        checkout2.setEndDate("2023-06-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        MemberUser user = new MemberUser();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        
        // Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        MemberUser user = new MemberUser();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        // Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setBook(book5);
        checkout1.setUser(user);
        checkout1.setStartDate("2023-08-01");
        checkout1.setEndDate("2023-08-10");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book5);
        checkout2.setUser(user);
        checkout2.setStartDate("2023-08-15");
        checkout2.setEndDate("2023-08-25");
        
        // Add loans to user
        user.addLoan(checkout1);
        user.addLoan(checkout2);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(user, "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        MemberUser userU005 = new MemberUser();
        userU005.setId("U005");
        userU005.setName("Eve");
        userU005.setEmail("eve@example.com");
        userU005.setAddress("202 Birch St.");
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        GuestUser userU006 = new GuestUser();
        userU006.setId("U006");
        userU006.setName("Frank");
        userU006.setEmail("frank@example.com");
        userU006.setAddress("303 Cedar St.");
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setBook(book6);
        checkout1.setUser(userU005);
        checkout1.setStartDate("2023-09-01");
        checkout1.setEndDate("2023-09-10");
        
        Loan checkout2 = new Loan();
        checkout2.setBook(book7);
        checkout2.setUser(userU005);
        checkout2.setStartDate("2023-09-15");
        checkout2.setEndDate("2023-09-20");
        
        Loan checkout3 = new Loan();
        checkout3.setBook(book6);
        checkout3.setUser(userU006);
        checkout3.setStartDate("2023-09-05");
        checkout3.setEndDate("2023-09-15");
        
        // Add loans to users
        userU005.addLoan(checkout1);
        userU005.addLoan(checkout2);
        userU006.addLoan(checkout3);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(userU005, "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}