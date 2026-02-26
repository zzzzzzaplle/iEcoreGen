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
        
        // Add books to library system
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member users
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
        
        // Add users to library system
        librarySystem.addUser(memberUser1);
        librarySystem.addUser(memberUser2);
        
        // Create loans for M001 - 3 different books
        Loan loan1_m1 = new Loan();
        loan1_m1.setBook(book1);
        loan1_m1.setUser(memberUser1);
        loan1_m1.setStartDate("2023-01-01");
        loan1_m1.setEndDate("2023-01-15");
        
        Loan loan2_m1 = new Loan();
        loan2_m1.setBook(book2);
        loan2_m1.setUser(memberUser1);
        loan2_m1.setStartDate("2023-02-01");
        loan2_m1.setEndDate("2023-02-10");
        
        Loan loan3_m1 = new Loan();
        loan3_m1.setBook(book3);
        loan3_m1.setUser(memberUser1);
        loan3_m1.setStartDate("2023-03-15");
        loan3_m1.setEndDate("2023-03-30");
        
        // Create loans for M002 - 2 same books (B001)
        Loan loan1_m2 = new Loan();
        loan1_m2.setBook(book1);
        loan1_m2.setUser(memberUser2);
        loan1_m2.setStartDate("2023-04-01");
        loan1_m2.setEndDate("2023-04-12");
        
        Loan loan2_m2 = new Loan();
        loan2_m2.setBook(book1);
        loan2_m2.setUser(memberUser2);
        loan2_m2.setStartDate("2023-04-15");
        loan2_m2.setEndDate("2023-04-20");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_m1);
        librarySystem.addLoan(loan2_m1);
        librarySystem.addLoan(loan3_m1);
        librarySystem.addLoan(loan1_m2);
        librarySystem.addLoan(loan2_m2);
        
        // Test total unique books for M001
        int resultM001 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser1);
        assertEquals("Member M001 should have 3 unique books checked out", 3, resultM001);
        
        // Test total unique books for M002
        int resultM002 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser2);
        assertEquals("Member M002 should have 1 unique book checked out", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user
        guestUser1 = new GuestUser();
        guestUser1.setId("G001");
        guestUser1.setName("Charlie");
        guestUser1.setEmail("charlie@example.com");
        guestUser1.setAddress("789 Pine St");
        
        // Add user to library system
        librarySystem.addUser(guestUser1);
        
        // Create loan for G001 - 1 book (B001)
        Loan loan1_g1 = new Loan();
        loan1_g1.setBook(book1);
        loan1_g1.setUser(guestUser1);
        loan1_g1.setStartDate("2023-05-01");
        loan1_g1.setEndDate("2023-05-10");
        
        // Add loan to library system
        librarySystem.addLoan(loan1_g1);
        
        // Test total unique books for G001
        int resultG001 = librarySystem.getTotalUniqueBooksCheckedOut(guestUser1);
        assertEquals("Guest G001 should have 1 unique book checked out", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        memberUser3 = new MemberUser();
        memberUser3.setId("M003");
        memberUser3.setName("Eve");
        memberUser3.setEmail("eve@example.com");
        memberUser3.setAddress("654 Maple St");
        
        // Add user to library system
        librarySystem.addUser(memberUser3);
        
        // Test total unique books for M003 (no loans)
        int resultM003 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser3);
        assertEquals("Member M003 should have 0 unique books checked out", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypesMember() {
        // Create Member user
        memberUser4 = new MemberUser();
        memberUser4.setId("M004");
        memberUser4.setName("George");
        memberUser4.setEmail("george@example.com");
        memberUser4.setAddress("135 Cedar St");
        
        // Add user to library system
        librarySystem.addUser(memberUser4);
        
        // Create loans for M004 - 4 different books
        Loan loan1_m4 = new Loan();
        loan1_m4.setBook(book1);
        loan1_m4.setUser(memberUser4);
        loan1_m4.setStartDate("2023-06-01");
        loan1_m4.setEndDate("2023-07-01");
        
        Loan loan2_m4 = new Loan();
        loan2_m4.setBook(book2);
        loan2_m4.setUser(memberUser4);
        loan2_m4.setStartDate("2023-06-01");
        loan2_m4.setEndDate("2023-07-01");
        
        Loan loan3_m4 = new Loan();
        loan3_m4.setBook(book3);
        loan3_m4.setUser(memberUser4);
        loan3_m4.setStartDate("2023-06-01");
        loan3_m4.setEndDate("2023-07-01");
        
        Loan loan4_m4 = new Loan();
        loan4_m4.setBook(book4);
        loan4_m4.setUser(memberUser4);
        loan4_m4.setStartDate("2023-06-01");
        loan4_m4.setEndDate("2023-07-01");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_m4);
        librarySystem.addLoan(loan2_m4);
        librarySystem.addLoan(loan3_m4);
        librarySystem.addLoan(loan4_m4);
        
        // Test total unique books for M004
        int resultM004 = librarySystem.getTotalUniqueBooksCheckedOut(memberUser4);
        assertEquals("Member M004 should have 4 unique books checked out", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypesGuest() {
        // Create Guest user
        guestUser2 = new GuestUser();
        guestUser2.setId("G002");
        guestUser2.setName("Hannah");
        guestUser2.setEmail("hannah@example.com");
        guestUser2.setAddress("246 Spruce St");
        
        // Add user to library system
        librarySystem.addUser(guestUser2);
        
        // Create loans for G002 - 2 same book (B001)
        Loan loan1_g2 = new Loan();
        loan1_g2.setBook(book1);
        loan1_g2.setUser(guestUser2);
        loan1_g2.setStartDate("2023-07-15");
        loan1_g2.setEndDate("2023-08-01");
        
        Loan loan2_g2 = new Loan();
        loan2_g2.setBook(book1);
        loan2_g2.setUser(guestUser2);
        loan2_g2.setStartDate("2023-08-15");
        loan2_g2.setEndDate("2023-09-01");
        
        // Create loans for G002 - B004 3 times
        Loan loan3_g2 = new Loan();
        loan3_g2.setBook(book4);
        loan3_g2.setUser(guestUser2);
        loan3_g2.setStartDate("2024-06-01");
        loan3_g2.setEndDate("2024-07-01");
        
        Loan loan4_g2 = new Loan();
        loan4_g2.setBook(book4);
        loan4_g2.setUser(guestUser2);
        loan4_g2.setStartDate("2024-07-02");
        loan4_g2.setEndDate("2024-07-11");
        
        Loan loan5_g2 = new Loan();
        loan5_g2.setBook(book4);
        loan5_g2.setUser(guestUser2);
        loan5_g2.setStartDate("2024-08-01");
        loan5_g2.setEndDate("2024-09-01");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_g2);
        librarySystem.addLoan(loan2_g2);
        librarySystem.addLoan(loan3_g2);
        librarySystem.addLoan(loan4_g2);
        librarySystem.addLoan(loan5_g2);
        
        // Test total unique books for G002
        int resultG002 = librarySystem.getTotalUniqueBooksCheckedOut(guestUser2);
        assertEquals("Guest G002 should have 2 unique books checked out", 2, resultG002);
    }
}