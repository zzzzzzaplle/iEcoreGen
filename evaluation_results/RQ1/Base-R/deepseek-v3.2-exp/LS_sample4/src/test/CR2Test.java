import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    private MemberUser memberUser1;
    private GuestUser guestUser1;
    private MemberUser memberUser2;
    private MemberUser memberUser3;
    private MemberUser memberUser4;
    private MemberUser memberUser5;
    private GuestUser guestUser2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;
    private Book book6;
    private Book book7;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create users
        memberUser1 = new MemberUser();
        memberUser1.setId("U001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St.");
        
        guestUser1 = new GuestUser();
        guestUser1.setId("U002");
        guestUser1.setName("Bob");
        guestUser1.setEmail("bob@example.com");
        guestUser1.setAddress("456 Oak St.");
        
        memberUser2 = new MemberUser();
        memberUser2.setId("U003");
        memberUser2.setName("Charlie");
        memberUser2.setEmail("charlie@example.com");
        memberUser2.setAddress("789 Pine St.");
        
        memberUser3 = new MemberUser();
        memberUser3.setId("U004");
        memberUser3.setName("Diana");
        memberUser3.setEmail("diana@example.com");
        memberUser3.setAddress("101 Maple St.");
        
        memberUser4 = new MemberUser();
        memberUser4.setId("U005");
        memberUser4.setName("Eve");
        memberUser4.setEmail("eve@example.com");
        memberUser4.setAddress("202 Birch St.");
        
        guestUser2 = new GuestUser();
        guestUser2.setId("U006");
        guestUser2.setName("Frank");
        guestUser2.setEmail("frank@example.com");
        guestUser2.setAddress("303 Cedar St.");
        
        // Create books
        book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Add users and books to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(guestUser1);
        librarySystem.addUser(memberUser2);
        librarySystem.addUser(memberUser3);
        librarySystem.addUser(memberUser4);
        librarySystem.addUser(guestUser2);
        
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        librarySystem.addBook(book5);
        librarySystem.addBook(book6);
        librarySystem.addBook(book7);
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Test Case 1: Single User Borrowing Calculation for a Member
        // Set up loans for memberUser1 (Alice)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(memberUser1);
        loan1.setStartDate(LocalDate.of(2023, 1, 10));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(memberUser1);
        loan2.setStartDate(LocalDate.of(2023, 3, 5));
        loan2.setEndDate(LocalDate.of(2023, 3, 10));
        
        Loan loan3 = new Loan();
        loan3.setBook(book1);
        loan3.setUser(memberUser1);
        loan3.setStartDate(LocalDate.of(2023, 5, 20));
        loan3.setEndDate(LocalDate.of(2023, 5, 25));
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Execute method to count unique book borrowings for user U001 from 2023-01-01 to 2023-12-31
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(memberUser1, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Test Case 2: Single User Borrowing Calculation for a Guest
        // Set up loans for guestUser1 (Bob)
        Loan loan1 = new Loan();
        loan1.setBook(book3);
        loan1.setUser(guestUser1);
        loan1.setStartDate(LocalDate.of(2023, 6, 10));
        loan1.setEndDate(LocalDate.of(2023, 6, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book4);
        loan2.setUser(guestUser1);
        loan2.setStartDate(LocalDate.of(2023, 6, 20));
        loan2.setEndDate(LocalDate.of(2023, 6, 25));
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Execute method to count unique book borrowings for user U002 from 2023-06-01 to 2023-06-30
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(guestUser1, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest user should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Test Case 3: No Borrowing Activity within Date Range
        // Execute method to count unique book borrowings for user U003 from 2023-07-01 to 2023-07-31
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(memberUser2, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("User with no loans should have 0 unique books borrowed", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Test Case 4: Overlapping Checkout Period
        // Set up loans for memberUser3 (Diana)
        Loan loan1 = new Loan();
        loan1.setBook(book5);
        loan1.setUser(memberUser3);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 10));
        
        Loan loan2 = new Loan();
        loan2.setBook(book5);
        loan2.setUser(memberUser3);
        loan2.setStartDate(LocalDate.of(2023, 8, 15));
        loan2.setEndDate(LocalDate.of(2023, 8, 25));
        
        // Add loans to library system
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        
        // Execute method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(memberUser3,