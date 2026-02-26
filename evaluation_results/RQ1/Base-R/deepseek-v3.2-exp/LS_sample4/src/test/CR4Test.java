import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private MemberUser memberUser1;
    private MemberUser memberUser2;
    private MemberUser memberUser3;
    private MemberUser memberUser4;
    private GuestUser guestUser1;
    private GuestUser guestUser2;
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
        book1.setNumberOfPages(200);
        
        book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(300);
        
        book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(400);
        
        book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(500);
        
        // Add books to library
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // SetUp: Create Member users
        memberUser1 = new MemberUser();
        memberUser1.setId("M001");
        memberUser1.setName("Alice");
        memberUser1.setEmail("alice@example.com");
        memberUser1.setAddress("123 Main St");
        
        memberUser2 = new MemberUser();
        memberUser2.setId("M002");
        memberUser2.setName("Bob");
        memberUser2.setEmail("bob@example.com");
        memberUser2.setAddress("456 Elm St");
        
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        
        // Create loans for M001 (3 different books)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(memberUser1);
        loan1.setStartDate(LocalDate.of(2023, 1, 1));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(memberUser1);
        loan2.setStartDate(LocalDate.of(2023, 2, 1));
        loan2.setEndDate(LocalDate.of(2023, 2, 10));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(memberUser1);
        loan3.setStartDate(LocalDate.of(2023, 3, 15));
        loan3.setEndDate(LocalDate.of(2023, 3, 30));
        
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        
        // Create loans for M002 (2 same book - B001)
        Loan loan4 = new Loan();
        loan4.setBook(book1);
        loan4.setUser(memberUser2);
        loan4.setStartDate(LocalDate.of(2023, 4, 1));
        loan4.setEndDate(LocalDate.of(2023, 4, 12));
        
        Loan loan5 = new Loan();
        loan5.setBook(book1);
        loan5.setUser(memberUser2);
        loan5.setStartDate(LocalDate.of(2023, 4, 15));
        loan5.setEndDate(LocalDate.of(2023, 4, 20));
        
        librarySystem.addLoan(loan4);
        librarySystem.addLoan(loan5);
        
        // Test: Calculate total unique checkouts for M001
        int resultM001 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser1);
        assertEquals("Member M001 should have 3 unique books checked out", 3, resultM001);
        
        // Test: Calculate total unique checkouts for M002
        int resultM002 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser2);
        assertEquals("Member M002 should have 1 unique book checked out", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // SetUp: Create Guest user
        guestUser1 = new GuestUser();
        guestUser1.setId("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        
        librarySystem.addUser(guestUser1);
        
        // Create loan for G001 (1 book - B001)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guestUser1);
        loan1.setStartDate(LocalDate.of(2023, 5, 1));
        loan1.setEndDate(LocalDate.of(2023, 5, 10));
        
        librarySystem.addLoan(loan1);
        
        // Test: Calculate total unique checkouts for G001
        int resultG001 = librarySystem.getTotalUniqueBooksCheckedOut(guestUser1);
        assertEquals("Guest G001 should have 1 unique book checked out", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // SetUp: Create Member user with no checkouts
        memberUser3 = new MemberUser();
        memberUser3.setId("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        
        librarySystem.addUser(memberUser3);
        
        // Test: Calculate total unique checkouts for M003 (should be 0)
        int resultM003 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser3);
        assertEquals("Member M003 with no checkouts should have 0 unique books", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // SetUp: Create Member user
        memberUser4 = new MemberUser();
        memberUser4.setId("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        
        librarySystem.addUser(memberUser4);
        
        // Create loans for M004 (4 different books)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(memberUser4);
        loan1.setStartDate(LocalDate.of(2023, 6, 1));
        loan1.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(memberUser4);
        loan1.setStartDate(LocalDate.of(2023, 6, 1));
        loan2.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan3 = new Loan();
        loan3.setBook(book3);
        loan3.setUser(memberUser4);
        loan1.setStartDate(LocalDate.of(2023, 6, 1));
        loan3.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(memberUser4);
        loan1.setStartDate(LocalDate.of(2023, 6, 1));
        loan4.setEndDate(LocalDate.of(2023, 7, 1));
        
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        librarySystem.addLoan(loan4);
        
        // Test: Calculate total unique checkouts for M004
        int resultM004 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser4);
        assertEquals("Member M004 should have 4 unique books checked out", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // SetUp: Create Guest user
        guestUser2 = new GuestUser();
        guestUser2.setId("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        
        librarySystem.addUser(guestUser2);
        
        // Create loans for G002 (2 same book - B001)
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(guestUser2);
        loan1.setStartDate(LocalDate.of(2023, 7, 15));
        loan1.setEndDate(LocalDate.of(2023, 8, 1));
        
        Loan loan2 = new Loan();
        loan2.setBook(book1);
        loan2.setUser(guestUser2);
        loan2.setStartDate(LocalDate.of(2023, 8, 15));
        loan2.setEndDate(LocalDate.of(2023, 9, 1));
        
        // Create loans for G002 (3 same book - B004)
        Loan loan3 = new Loan();
        loan3.setBook(book4);
        loan3.setUser(guestUser2);
        loan3.setStartDate(LocalDate.of(2024, 6, 1));
        loan3.setEndDate(LocalDate.of(2024, 7, 1));
        
        Loan loan4 = new Loan();
        loan4.setBook(book4);
        loan4.setUser(guestUser2);
        loan4.setStartDate(LocalDate.of(2024, 7, 2));
        loan4.setEndDate(LocalDate.of(2024, 7, 11));
        
        Loan loan5 = new Loan();
        loan5.setBook(book4);
        loan5.setUser(guestUser2);
        loan5.setStartDate(LocalDate.of(2024, 8, 1));
        loan5.setEndDate(LocalDate.of(2024, 9, 1));
        
        librarySystem.addLoan(loan1);
        librarySystem.addLoan(loan2);
        librarySystem.addLoan(loan3);
        librarySystem.addLoan(loan4);
        librarySystem.addLoan(loan5);
        
        // Test: Calculate total unique checkouts for G002
        int resultG002 = librarySystem.getTotalUniqueBooksCheckedOut(guestUser2);
        assertEquals("Guest G002 should have 2 unique books checked out", 2, resultG002);
    }
}