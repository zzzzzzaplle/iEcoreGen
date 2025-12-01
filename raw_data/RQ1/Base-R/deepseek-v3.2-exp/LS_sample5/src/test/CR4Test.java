import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Initialize library system
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
        book4.setNumberOfPages(250);
        
        // Add books to library
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
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
        
        // Add users to library
        librarySystem.addUser(member1);
        librarySystem.addUser(member2);
        librarySystem.addUser(member3);
        librarySystem.addUser(member4);
        librarySystem.addUser(guest1);
        librarySystem.addUser(guest2);
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create loans for member M001 (3 different books)
        Loan loan1_m1 = new Loan();
        loan1_m1.setBook(book1);
        loan1_m1.setUser(member1);
        loan1_m1.setStartDate("2023-01-01");
        loan1_m1.setEndDate("2023-01-15");
        
        Loan loan2_m1 = new Loan();
        loan2_m1.setBook(book2);
        loan2_m1.setUser(member1);
        loan2_m1.setStartDate("2023-02-01");
        loan2_m1.setEndDate("2023-02-10");
        
        Loan loan3_m1 = new Loan();
        loan3_m1.setBook(book3);
        loan3_m1.setUser(member1);
        loan3_m1.setStartDate("2023-03-15");
        loan3_m1.setEndDate("2023-03-30");
        
        // Create loans for member M002 (2 same book B001)
        Loan loan1_m2 = new Loan();
        loan1_m2.setBook(book1);
        loan1_m2.setUser(member2);
        loan1_m2.setStartDate("2023-04-01");
        loan1_m2.setEndDate("2023-04-12");
        
        Loan loan2_m2 = new Loan();
        loan2_m2.setBook(book1);
        loan2_m2.setUser(member2);
        loan2_m2.setStartDate("2023-04-15");
        loan2_m2.setEndDate("2023-04-20");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_m1);
        librarySystem.addLoan(loan2_m1);
        librarySystem.addLoan(loan3_m1);
        librarySystem.addLoan(loan1_m2);
        librarySystem.addLoan(loan2_m2);
        
        // Calculate total unique checkouts
        int resultM001 = librarySystem.getTotalUniqueBooksCheckedOut(member1);
        int resultM002 = librarySystem.getTotalUniqueBooksCheckedOut(member2);
        
        // Verify expected results
        assertEquals("Member M001 should have 3 unique book checkouts", 3, resultM001);
        assertEquals("Member M002 should have 1 unique book checkout", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create loan for guest G001 (1 book)
        Loan loan1_g1 = new Loan();
        loan1_g1.setBook(book1);
        loan1_g1.setUser(guest1);
        loan1_g1.setStartDate("2023-05-01");
        loan1_g1.setEndDate("2023-05-10");
        
        // Add loan to library system
        librarySystem.addLoan(loan1_g1);
        
        // Calculate total unique checkouts
        int resultG001 = librarySystem.getTotalUniqueBooksCheckedOut(guest1);
        
        // Verify expected result
        assertEquals("Guest G001 should have 1 unique book checkout", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // No loans created for member M003
        
        // Calculate total unique checkouts
        int resultM003 = librarySystem.getTotalUniqueBooksCheckedOut(member3);
        
        // Verify expected result
        assertEquals("Member M003 should have 0 unique book checkouts", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create loans for member M004 (4 different books)
        Loan loan1_m4 = new Loan();
        loan1_m4.setBook(book1);
        loan1_m4.setUser(member4);
        loan1_m4.setStartDate("2023-06-01");
        loan1_m4.setEndDate("2023-07-01");
        
        Loan loan2_m4 = new Loan();
        loan2_m4.setBook(book2);
        loan2_m4.setUser(member4);
        loan2_m4.setStartDate("2023-06-01");
        loan2_m4.setEndDate("2023-07-01");
        
        Loan loan3_m4 = new Loan();
        loan3_m4.setBook(book3);
        loan3_m4.setUser(member4);
        loan3_m4.setStartDate("2023-06-01");
        loan3_m4.setEndDate("2023-07-01");
        
        Loan loan4_m4 = new Loan();
        loan4_m4.setBook(book4);
        loan4_m4.setUser(member4);
        loan4_m4.setStartDate("2023-06-01");
        loan4_m4.setEndDate("2023-07-01");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_m4);
        librarySystem.addLoan(loan2_m4);
        librarySystem.addLoan(loan3_m4);
        librarySystem.addLoan(loan4_m4);
        
        // Calculate total unique checkouts
        int resultM004 = librarySystem.getTotalUniqueBooksCheckedOut(member4);
        
        // Verify expected result
        assertEquals("Member M004 should have 4 unique book checkouts", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create loans for guest G002 (2 same book B001 and 3 same book B004)
        Loan loan1_g2 = new Loan();
        loan1_g2.setBook(book1);
        loan1_g2.setUser(guest2);
        loan1_g2.setStartDate("2023-07-15");
        loan1_g2.setEndDate("2023-08-01");
        
        Loan loan2_g2 = new Loan();
        loan2_g2.setBook(book1);
        loan2_g2.setUser(guest2);
        loan2_g2.setStartDate("2023-08-15");
        loan2_g2.setEndDate("2023-09-01");
        
        Loan loan3_g2 = new Loan();
        loan3_g2.setBook(book4);
        loan3_g2.setUser(guest2);
        loan3_g2.setStartDate("2024-06-01");
        loan3_g2.setEndDate("2024-07-01");
        
        Loan loan4_g2 = new Loan();
        loan4_g2.setBook(book4);
        loan4_g2.setUser(guest2);
        loan4_g2.setStartDate("2024-07-02");
        loan4_g2.setEndDate("2024-07-11");
        
        Loan loan5_g2 = new Loan();
        loan5_g2.setBook(book4);
        loan5_g2.setUser(guest2);
        loan5_g2.setStartDate("2024-08-01");
        loan5_g2.setEndDate("2024-09-01");
        
        // Add loans to library system
        librarySystem.addLoan(loan1_g2);
        librarySystem.addLoan(loan2_g2);
        librarySystem.addLoan(loan3_g2);
        librarySystem.addLoan(loan4_g2);
        librarySystem.addLoan(loan5_g2);
        
        // Calculate total unique checkouts
        int resultG002 = librarySystem.getTotalUniqueBooksCheckedOut(guest2);
        
        // Verify expected result
        assertEquals("Guest G002 should have 2 unique book checkouts", 2, resultG002);
    }
}