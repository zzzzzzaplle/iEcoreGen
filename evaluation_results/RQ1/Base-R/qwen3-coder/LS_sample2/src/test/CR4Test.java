import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private Guest guest1;
    private Guest guest2;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Initialize users
        member1 = new Member();
        member1.setId("M001");
        member1.setName("Alice");
        member1.setEmail("alice@example.com");
        member1.setAddress("123 Main St");
        
        member2 = new Member();
        member2.setId("M002");
        member2.setName("Bob");
        member2.setEmail("bob@example.com");
        member2.setAddress("456 Elm St");
        
        member3 = new Member();
        member3.setId("M003");
        member3.setName("Eve");
        member3.setEmail("eve@example.com");
        member3.setAddress("654 Maple St");
        
        member4 = new Member();
        member4.setId("M004");
        member4.setName("George");
        member4.setEmail("george@example.com");
        member4.setAddress("135 Cedar St");
        
        guest1 = new Guest();
        guest1.setId("G001");
        guest1.setName("Charlie");
        guest1.setEmail("charlie@example.com");
        guest1.setAddress("789 Pine St");
        
        guest2 = new Guest();
        guest2.setId("G002");
        guest2.setName("Hannah");
        guest2.setEmail("hannah@example.com");
        guest2.setAddress("246 Spruce St");
        
        // Initialize books
        book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(100);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(200);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(300);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(400);
        
        // Add books and users to library system
        librarySystem.getBooks().addAll(Arrays.asList(book1, book2, book3, book4));
        librarySystem.getUsers().addAll(Arrays.asList(member1, member2, member3, member4, guest1, guest2));
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Test Case 1: Calculate Total Checkouts for Member User
        // Set up loans for member M001 with 3 different books
        Loan loan1 = new Loan();
        loan1.setUser(member1);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-01-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-01-15", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(member1);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-02-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-02-10", formatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(member1);
        loan3.setBook(book3);
        loan3.setStartDate(LocalDate.parse("2023-03-15", formatter));
        loan3.setEndDate(LocalDate.parse("2023-03-30", formatter));
        
        // Set up loans for member M002 with 2 same book (B001)
        Loan loan4 = new Loan();
        loan4.setUser(member2);
        loan4.setBook(book1);
        loan4.setStartDate(LocalDate.parse("2023-04-01", formatter));
        loan4.setEndDate(LocalDate.parse("2023-04-12", formatter));
        
        Loan loan5 = new Loan();
        loan5.setUser(member2);
        loan5.setBook(book1);
        loan5.setStartDate(LocalDate.parse("2023-04-15", formatter));
        loan5.setEndDate(LocalDate.parse("2023-04-20", formatter));
        
        // Add all loans to library system
        librarySystem.getLoans().addAll(Arrays.asList(loan1, loan2, loan3, loan4, loan5));
        
        // Calculate unique books checked out by M001 and M002
        int resultM001 = librarySystem.countUniqueBooksCheckedOutByUser(member1);
        int resultM002 = librarySystem.countUniqueBooksCheckedOutByUser(member2);
        
        // Verify expected results
        assertEquals("Member M001 should have 3 unique book checkouts", 3, resultM001);
        assertEquals("Member M002 should have 1 unique book checkout", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Test Case 2: Calculate Total Checkouts for Guest User
        // Set up loan for guest G001 with 1 book
        Loan loan1 = new Loan();
        loan1.setUser(guest1);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-05-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-05-10", formatter));
        
        // Add loan to library system
        librarySystem.getLoans().add(loan1);
        
        // Calculate unique books checked out by G001
        int resultG001 = librarySystem.countUniqueBooksCheckedOutByUser(guest1);
        
        // Verify expected result
        assertEquals("Guest G001 should have 1 unique book checkout", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Test Case 3: Mixed User Types with No Checkouts
        // No loans set up for member M003
        
        // Calculate unique books checked out by M003
        int resultM003 = librarySystem.countUniqueBooksCheckedOutByUser(member3);
        
        // Verify expected result
        assertEquals("Member M003 should have 0 unique book checkouts", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Test Case 4: Calculate Total Checkouts for Multiple Users with Mixed Types (Member)
        // Set up loans for member M004 with 4 different books
        Loan loan1 = new Loan();
        loan1.setUser(member4);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(member4);
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(member4);
        loan3.setBook(book3);
        loan3.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan3.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan4 = new Loan();
        loan4.setUser(member4);
        loan4.setBook(book4);
        loan4.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan4.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        // Add all loans to library system
        librarySystem.getLoans().addAll(Arrays.asList(loan1, loan2, loan3, loan4));
        
        // Calculate unique books checked out by M004
        int resultM004 = librarySystem.countUniqueBooksCheckedOutByUser(member4);
        
        // Verify expected result
        assertEquals("Member M004 should have 4 unique book checkouts", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Test Case 5: Calculate Total Checkouts for Multiple Users with Mixed Types (Guest)
        // Set up loans for guest G002 with 2 same book (B001) and B004 3 times
        Loan loan1 = new Loan();
        loan1.setUser(guest2);
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.parse("2023-07-15", formatter));
        loan1.setEndDate(LocalDate.parse("2023-08-01", formatter));
        
        Loan loan2 = new Loan();
        loan2.setUser(guest2);
        loan2.setBook(book1);
        loan2.setStartDate(LocalDate.parse("2023-08-15", formatter));
        loan2.setEndDate(LocalDate.parse("2023-09-01", formatter));
        
        Loan loan3 = new Loan();
        loan3.setUser(guest2);
        loan3.setBook(book4);
        loan3.setStartDate(LocalDate.parse("2024-06-01", formatter));
        loan3.setEndDate(LocalDate.parse("2024-07-01", formatter));
        
        Loan loan4 = new Loan();
        loan4.setUser(guest2);
        loan4.setBook(book4);
        loan4.setStartDate(LocalDate.parse("2024-07-02", formatter));
        loan4.setEndDate(LocalDate.parse("2024-07-11", formatter));
        
        Loan loan5 = new Loan();
        loan5.setUser(guest2);
        loan5.setBook(book4);
        loan5.setStartDate(LocalDate.parse("2024-08-01", formatter));
        loan5.setEndDate(LocalDate.parse("2024-09-01", formatter));
        
        // Add all loans to library system
        librarySystem.getLoans().addAll(Arrays.asList(loan1, loan2, loan3, loan4, loan5));
        
        // Calculate unique books checked out by G002
        int resultG002 = librarySystem.countUniqueBooksCheckedOutByUser(guest2);
        
        // Verify expected result
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, resultG002);
    }
}