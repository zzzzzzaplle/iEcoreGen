import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR2Test {
    private LibrarySystem librarySystem;
    private Member memberUser;
    private Guest guestUser;
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
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create MEMBER user
        memberUser = new Member();
        memberUser.setId("U001");
        memberUser.setName("Alice");
        memberUser.setEmail("alice@example.com");
        memberUser.setAddress("123 Main St.");
        
        // SetUp: Create books
        book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setBarcode("BOOK001");
        
        book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setBarcode("BOOK002");
        
        // SetUp: Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setUser(memberUser);
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.of(2023, 1, 10));
        checkout1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(memberUser);
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.of(2023, 3, 5));
        checkout2.setEndDate(LocalDate.of(2023, 3, 10));
        
        Loan checkout3 = new Loan();
        checkout3.setUser(memberUser);
        checkout3.setBook(book1);
        checkout3.setStartDate(LocalDate.of(2023, 5, 20));
        checkout3.setEndDate(LocalDate.of(2023, 5, 25));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        librarySystem.getLoans().add(checkout3);
        
        // Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(memberUser, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create GUEST user
        guestUser = new Guest();
        guestUser.setId("U002");
        guestUser.setName("Bob");
        guestUser.setEmail("bob@example.com");
        guestUser.setAddress("456 Oak St.");
        
        // SetUp: Create books
        book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setBarcode("BOOK003");
        
        book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setBarcode("BOOK004");
        
        // SetUp: Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setUser(guestUser);
        checkout1.setBook(book3);
        checkout1.setStartDate(LocalDate.of(2023, 6, 10));
        checkout1.setEndDate(LocalDate.of(2023, 6, 15));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(guestUser);
        checkout2.setBook(book4);
        checkout2.setStartDate(LocalDate.of(2023, 6, 20));
        checkout2.setEndDate(LocalDate.of(2023, 6, 25));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        
        // Execute the method to count unique book borrowings
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(guestUser, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create MEMBER user
        memberUser = new Member();
        memberUser.setId("U003");
        memberUser.setName("Charlie");
        memberUser.setEmail("charlie@example.com");
        memberUser.setAddress("789 Pine St.");
        
        // Execute the method to count unique book borrowings (no loans recorded)
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(memberUser, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create MEMBER user
        memberUser = new Member();
        memberUser.setId("U004");
        memberUser.setName("Diana");
        memberUser.setEmail("diana@example.com");
        memberUser.setAddress("101 Maple St.");
        
        // SetUp: Create book
        book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setBarcode("BOOK005");
        
        // SetUp: Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setUser(memberUser);
        checkout1.setBook(book5);
        checkout1.setStartDate(LocalDate.of(2023, 8, 1));
        checkout1.setEndDate(LocalDate.of(2023, 8, 10));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(memberUser);
        checkout2.setBook(book5);
        checkout2.setStartDate(LocalDate.of(2023, 8, 15));
        checkout2.setEndDate(LocalDate.of(2023, 8, 25));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        
        // Execute the method to count unique book borrowings for specific period
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 10);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(memberUser, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create MEMBER user
        Member memberUser1 = new Member();
        memberUser1.setId("U005");
        memberUser1.setName("Eve");
        memberUser1.setEmail("eve@example.com");
        memberUser1.setAddress("202 Birch St.");
        
        // SetUp: Create GUEST user
        Guest guestUser1 = new Guest();
        guestUser1.setId("U006");
        guestUser1.setName("Frank");
        guestUser1.setEmail("frank@example.com");
        guestUser1.setAddress("303 Cedar St.");
        
        // SetUp: Create books
        book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setBarcode("BOOK006");
        
        book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setBarcode("BOOK007");
        
        // SetUp: Record CheckOuts
        Loan checkout1 = new Loan();
        checkout1.setUser(memberUser1);
        checkout1.setBook(book6);
        checkout1.setStartDate(LocalDate.of(2023, 9, 1));
        checkout1.setEndDate(LocalDate.of(2023, 9, 10));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(memberUser1);
        checkout2.setBook(book7);
        checkout2.setStartDate(LocalDate.of(2023, 9, 15));
        checkout2.setEndDate(LocalDate.of(2023, 9, 20));
        
        Loan checkout3 = new Loan();
        checkout3.setUser(guestUser1);
        checkout3.setBook(book6);
        checkout3.setStartDate(LocalDate.of(2023, 9, 5));
        checkout3.setEndDate(LocalDate.of(2023, 9, 15));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        librarySystem.getLoans().add(checkout3);
        
        // Execute the method to count unique book borrowings for user U005
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(memberUser1, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
    

}