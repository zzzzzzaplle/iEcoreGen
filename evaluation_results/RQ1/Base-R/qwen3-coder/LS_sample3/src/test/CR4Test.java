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
        
        // Create books
        book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setPages(100);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setPages(200);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setPages(300);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setPages(400);
        
        // Create users
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
        
        // Add books and users to library system
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getBooks().add(book3);
        librarySystem.getBooks().add(book4);
        
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(member2);
        librarySystem.getUsers().add(member3);
        librarySystem.getUsers().add(member4);
        librarySystem.getUsers().add(guest1);
        librarySystem.getUsers().add(guest2);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create loans for member1: 3 different books
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(member1);
        loan1.setStartDate(LocalDate.parse("2023-01-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-01-15", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(member1);
        loan2.setStartDate(LocalDate.parse("2023-02-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-02-10", formatter));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(member1);
        loan3.setStartDate(LocalDate.parse("2023-03-15", formatter));
        loan3.setEndDate(LocalDate.parse("2023-03-30", formatter));
        
        // Create loans for member2: 2 same book (B001)
        Loan loan4 = new Loan();
        loan4.setBook(book1);
        loan4.setUser(member2);
        loan4.setStartDate(LocalDate.parse("2023-04-01", formatter));
        loan4.setEndDate(LocalDate.parse("2023-04-12", formatter));
        
        Loan loan5 = new Loan();
        loan5.setBook(book1);
        loan5.setUser(member2);
        loan5.setStartDate(LocalDate.parse("2023-04-15", formatter));
        loan5.setEndDate(LocalDate.parse("2023-04-20", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        librarySystem.getLoans().add(loan5);
        
        // Test member1: should have 3 unique books
        int resultMember1 = librarySystem.countUniqueBooksCheckedOutByUser(member1);
        assertEquals("Member M001 should have 3 unique book checkouts", 3, resultMember1);
        
        // Test member2: should have 1 unique book (same book borrowed twice)
        int resultMember2 = librarySystem.countUniqueBooksCheckedOutByUser(member2);
        assertEquals("Member M002 should have 1 unique book checkout", 1, resultMember2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create loan for guest1: 1 book
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guest1);
        loan1.setStartDate(LocalDate.parse("2023-05-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-05-10", formatter));
        
        // Add loan to library system
        librarySystem.getLoans().add(loan1);
        
        // Test guest1: should have 1 unique book
        int resultGuest1 = librarySystem.countUniqueBooksCheckedOutByUser(guest1);
        assertEquals("Guest G001 should have 1 unique book checkout", 1, resultGuest1);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // No loans created for member3
        
        // Test member3: should have 0 unique books (no checkouts)
        int resultMember3 = librarySystem.countUniqueBooksCheckedOutByUser(member3);
        assertEquals("Member M003 should have 0 unique book checkouts", 0, resultMember3);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create loans for member4: 4 different books
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(member4);
        loan1.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(member4);
        loan2.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan2.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(member4);
        loan3.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan3.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(member4);
        loan4.setStartDate(LocalDate.parse("2023-06-01", formatter));
        loan4.setEndDate(LocalDate.parse("2023-07-01", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        
        // Test member4: should have 4 unique books
        int resultMember4 = librarySystem.countUniqueBooksCheckedOutByUser(member4);
        assertEquals("Member M004 should have 4 unique book checkouts", 4, resultMember4);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create loans for guest2: 2 same book (B001) and B004 3 times
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guest2);
        loan1.setStartDate(LocalDate.parse("2023-07-15", formatter));
        loan1.setEndDate(LocalDate.parse("2023-08-01", formatter));
        
        Loan loan2 = new Loan();
        loan2.setBook(book1);
        loan2.setUser(guest2);
        loan2.setStartDate(LocalDate.parse("2023-08-15", formatter));
        loan2.setEndDate(LocalDate.parse("2023-09-01", formatter));
        
        Loan loan3 = new Loan();
        loan3.setBook(book4);
        loan3.setUser(guest2);
        loan3.setStartDate(LocalDate.parse("2024-06-01", formatter));
        loan3.setEndDate(LocalDate.parse("2024-07-01", formatter));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(guest2);
        loan4.setStartDate(LocalDate.parse("2024-07-02", formatter));
        loan4.setEndDate(LocalDate.parse("2024-07-11", formatter));
        
        Loan loan5 = new Loan();
        loan5.setBook(book4);
        loan5.setUser(guest2);
        loan5.setStartDate(LocalDate.parse("2024-08-01", formatter));
        loan5.setEndDate(LocalDate.parse("2024-09-01", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        librarySystem.getLoans().add(loan5);
        
        // Test guest2: should have 2 unique books (B001 and B004)
        int resultGuest2 = librarySystem.countUniqueBooksCheckedOutByUser(guest2);
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, resultGuest2);
    }
}