import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Library library;
    
    @Before
    public void setUp() {
        library = new Library();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // SetUp: Create Member users
        MemberUser member1 = new MemberUser("Alice", "alice@example.com", "123 Main St", "M001");
        MemberUser member2 = new MemberUser("Bob", "bob@example.com", "456 Elm St", "M002");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "ISBN001", 100);
        Book book2 = new Book("Book2", "B002", "ISBN002", 200);
        Book book3 = new Book("Book3", "B003", "ISBN003", 300);
        
        // Check Out 3 different books with user M001
        Loan loan1M1 = new Loan(book1, "2023-01-01", "2023-01-15");
        Loan loan2M1 = new Loan(book2, "2023-02-01", "2023-02-10");
        Loan loan3M1 = new Loan(book3, "2023-03-15", "2023-03-30");
        member1.addLoan(loan1M1);
        member1.addLoan(loan2M1);
        member1.addLoan(loan3M1);
        
        // Check Out 2 same book (B001) with user M002
        Loan loan1M2 = new Loan(book1, "2023-04-01", "2023-04-12");
        Loan loan2M2 = new Loan(book1, "2023-04-15", "2023-04-20");
        member2.addLoan(loan1M2);
        member2.addLoan(loan2M2);
        
        // Add users and books to library
        library.addUser(member1);
        library.addUser(member2);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        
        // Calculate total unique books for M001
        int resultM001 = library.calculateTotalUniqueBooksCheckedOut("M001");
        assertEquals("Total unique books for M001 should be 3", 3, resultM001);
        
        // Calculate total unique books for M002
        int resultM002 = library.calculateTotalUniqueBooksCheckedOut("M002");
        assertEquals("Total unique books for M002 should be 1", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // SetUp: Create Guest user
        GuestUser guest1 = new GuestUser("Charlie", "charlie@example.com", "789 Pine St", "G001");
        
        // Create book
        Book book1 = new Book("Book1", "B001", "ISBN001", 100);
        
        // Check Out 1 book with user G001
        Loan loan1G1 = new Loan(book1, "2023-05-01", "2023-05-10");
        guest1.addLoan(loan1G1);
        
        // Add user and book to library
        library.addUser(guest1);
        library.addBook(book1);
        
        // Calculate total unique books for G001
        int resultG001 = library.calculateTotalUniqueBooksCheckedOut("G001");
        assertEquals("Total unique books for G001 should be 1", 1, resultG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // SetUp: Create Member user with no checkouts
        MemberUser member3 = new MemberUser("Eve", "eve@example.com", "654 Maple St", "M003");
        
        // Add user to library
        library.addUser(member3);
        
        // Calculate total unique books for M003 (should be 0)
        int resultM003 = library.calculateTotalUniqueBooksCheckedOut("M003");
        assertEquals("Total unique books for M003 should be 0", 0, resultM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // SetUp: Create Member user
        MemberUser member4 = new MemberUser("George", "george@example.com", "135 Cedar St", "M004");
        
        // Create 4 different books
        Book book1 = new Book("Book1", "B001", "ISBN001", 100);
        Book book2 = new Book("Book2", "B002", "ISBN002", 200);
        Book book3 = new Book("Book3", "B003", "ISBN003", 300);
        Book book4 = new Book("Book4", "B004", "ISBN004", 400);
        
        // Check out 4 different books with user M004
        Loan loan1M4 = new Loan(book1, "2023-06-01", "2023-07-01");
        Loan loan2M4 = new Loan(book2, "2023-06-01", "2023-07-01");
        Loan loan3M4 = new Loan(book3, "2023-06-01", "2023-07-01");
        Loan loan4M4 = new Loan(book4, "2023-06-01", "2023-07-01");
        member4.addLoan(loan1M4);
        member4.addLoan(loan2M4);
        member4.addLoan(loan3M4);
        member4.addLoan(loan4M4);
        
        // Add user and books to library
        library.addUser(member4);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        
        // Calculate total unique books for M004
        int resultM004 = library.calculateTotalUniqueBooksCheckedOut("M004");
        assertEquals("Total unique books for M004 should be 4", 4, resultM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // SetUp: Create Guest user
        GuestUser guest2 = new GuestUser("Hannah", "hannah@example.com", "246 Spruce St", "G002");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "ISBN001", 100);
        Book book4 = new Book("Book4", "B004", "ISBN004", 400);
        
        // Check out 2 same book (B001) with user G002
        Loan loan1G2 = new Loan(book1, "2023-07-15", "2023-08-01");
        Loan loan2G2 = new Loan(book1, "2023-08-15", "2023-09-01");
        guest2.addLoan(loan1G2);
        guest2.addLoan(loan2G2);
        
        // Check out B004 3 times with user G002
        Loan loan3G2 = new Loan(book4, "2024-06-01", "2024-07-01");
        Loan loan4G2 = new Loan(book4, "2024-07-02", "2024-07-11");
        Loan loan5G2 = new Loan(book4, "2024-08-01", "2024-09-01");
        guest2.addLoan(loan3G2);
        guest2.addLoan(loan4G2);
        guest2.addLoan(loan5G2);
        
        // Add user and books to library
        library.addUser(guest2);
        library.addBook(book1);
        library.addBook(book4);
        
        // Calculate total unique books for G002
        int resultG002 = library.calculateTotalUniqueBooksCheckedOut("G002");
        assertEquals("Total unique books for G002 should be 2", 2, resultG002);
    }
}