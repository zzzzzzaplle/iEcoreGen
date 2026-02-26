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
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        
        // Create books
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
        
        // Add books to library system
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getBooks().add(book3);
        librarySystem.getBooks().add(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member users
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
        
        // Add users to library system
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(member2);
        
        // Create loans for member1 (3 different books)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(member1);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(member1);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 10));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(member1);
        loan3.setStartDate(LocalDate.of(2023, 3, 15));
        loan3.setEndDate(LocalDate.of(2023, 3, 30));
        
        // Create loans for member2 (2 same book - B001)
        Loan loan4 = new Loan();
        loan4.setBook(book1);
        loan4.setUser(member2);
        loan4.setStartDate(LocalDate.of(2023, 4, 1));
        loan4.setEndDate(LocalDate.of(2023, 4, 12));
        
        Loan loan5 = new Loan();
        loan5.setBook(book1);
        loan5.setUser(member2);
        loan5.setStartDate(LocalDate.of(2023, 4, 15));
        loan5.setEndDate(LocalDate.of(2023, 4, 20));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        librarySystem.getLoans().add(loan5);
        
        // Test countUniqueBooksCheckedOutByUser for member1
        int result1 = librarySystem.countUniqueBooksCheckedOutByUser(member1);
        assertEquals("Member M001 should have 3 unique books checked out", 3, result1);
        
        // Test countUniqueBooksCheckedOutByUser for member2
        int result2 = librarySystem.countUniqueBooksCheckedOutByUser(member2);
        assertEquals("Member M002 should have 1 unique book checked out", 1, result2);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user
        guest1 = new Guest();
        guest1.setId("G001");
        guest1.setName("Charlie");
        guest1.setEmail("charlie@example.com");
        guest1.setAddress("789 Pine St");
        
        // Add user to library system
        librarySystem.getUsers().add(guest1);
        
        // Create loan for guest1 (1 book - B001)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guest1);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 10));
        
        // Add loan to library system
        librarySystem.getLoans().add(loan1);
        
        // Test countUniqueBooksCheckedOutByUser for guest1
        int result = librarySystem.countUniqueBooksCheckedOutByUser(guest1);
        assertEquals("Guest G001 should have 1 unique book checked out", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        member3 = new Member();
        member3.setId("M003");
        member3.setName("Eve");
        member3.setEmail("eve@example.com");
        member3.setAddress("654 Maple St");
        
        // Add user to library system
        librarySystem.getUsers().add(member3);
        
        // No loans created for member3
        
        // Test countUniqueBooksCheckedOutByUser for member3
        int result = librarySystem.countUniqueBooksCheckedOutByUser(member3);
        assertEquals("Member M003 should have 0 unique books checked out", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create Member user
        member4 = new Member();
        member4.setId("M004");
        member4.setName("George");
        member4.setEmail("george@example.com");
        member4.setAddress("135 Cedar St");
        
        // Add user to library system
        librarySystem.getUsers().add(member4);
        
        // Create loans for member4 (4 different books)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(member4);
        loan1.setStartDate(LocalDate.of(2023, 6, 1));
        loan1.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(member4);
        loan2.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(member4);
        loan3.setStartDate(LocalDate.of(2023, 6, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(member4);
        loan4.setStartDate(LocalDate.of(2023, 6, 1));
        loan4.setEndDate(LocalDate.of(2023, 7, 1));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        
        // Test countUniqueBooksCheckedOutByUser for member4
        int result = librarySystem.countUniqueBooksCheckedOutByUser(member4);
        assertEquals("Member M004 should have 4 unique books checked out", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create Guest user
        guest2 = new Guest();
        guest2.setId("G002");
        guest2.setName("Hannah");
        guest2.setEmail("hannah@example.com");
        guest2.setAddress("246 Spruce St");
        
        // Add user to library system
        librarySystem.getUsers().add(guest2);
        
        // Create loans for guest2 (2 same book - B001)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guest2);
        loan1.setStartDate(LocalDate.of(2023, 7, 15));
        loan1.setEndDate(LocalDate.of(2023, 8, 1));
        
        Loan loan2 = new Loan();
        loan2.setBook(book1);
        loan2.setUser(guest2);
        loan2.setStartDate(LocalDate.of(2023, 8, 15));
        loan2.setEndDate(LocalDate.of(2023, 9, 1));
        
        // Create loans for guest2 (3 same book - B004)
        Loan loan3 = new Loan();
        loan3.setBook(book4);
        loan3.setUser(guest2);
        loan3.setStartDate(LocalDate.of(2024, 6, 1));
        loan3.setEndDate(LocalDate.of(2024, 7, 1));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(guest2);
        loan4.setStartDate(LocalDate.of(2024, 7, 2));
        loan4.setEndDate(LocalDate.of(2024, 7, 11));
        
        Loan loan5 = new Loan();
        loan5.setBook(book4);
        loan5.setUser(guest2);
        loan5.setStartDate(LocalDate.of(2024, 8, 1));
        loan5.setEndDate(LocalDate.of(2024, 9, 1));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        librarySystem.getLoans().add(loan4);
        librarySystem.getLoans().add(loan5);
        
        // Test countUniqueBooksCheckedOutByUser for guest2
        int result = librarySystem.countUniqueBooksCheckedOutByUser(guest2);
        assertEquals("Guest G002 should have 2 unique books checked out", 2, result);
    }
}