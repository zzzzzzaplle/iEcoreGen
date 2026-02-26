import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member users
        MemberUser userM001 = new MemberUser();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        
        MemberUser userM002 = new MemberUser();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        
        librarySystem.addUser(userM001);
        librarySystem.addUser(userM002);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("B001");
        book1.setTitle("Book 1");
        book1.setBarcode("BC001");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setISBN("B002");
        book2.setTitle("Book 2");
        book2.setBarcode("BC002");
        book2.setNumberOfPages(300);
        
        Book book3 = new Book();
        book3.setISBN("B003");
        book3.setTitle("Book 3");
        book3.setBarcode("BC003");
        book3.setNumberOfPages(400);
        
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Create loans for user M001 (3 different books)
        Loan loan1M001 = new Loan();
        loan1M001.setBook(book1);
        loan1M001.setUser(userM001);
        loan1M001.setStartDate("2023-01-01");
        loan1M001.setEndDate("2023-01-15");
        
        Loan loan2M001 = new Loan();
        loan2M001.setBook(book2);
        loan2M001.setUser(userM001);
        loan2M001.setStartDate("2023-02-01");
        loan2M001.setEndDate("2023-02-10");
        
        Loan loan3M001 = new Loan();
        loan3M001.setBook(book3);
        loan3M001.setUser(userM001);
        loan3M001.setStartDate("2023-03-15");
        loan3M001.setEndDate("2023-03-30");
        
        librarySystem.addLoan(loan1M001);
        librarySystem.addLoan(loan2M001);
        librarySystem.addLoan(loan3M001);
        
        // Create loans for user M002 (2 same books)
        Loan loan1M002 = new Loan();
        loan1M002.setBook(book1);
        loan1M002.setUser(userM002);
        loan1M002.setStartDate("2023-04-01");
        loan1M002.setEndDate("2023-04-12");
        
        Loan loan2M002 = new Loan();
        loan2M002.setBook(book1);
        loan2M002.setUser(userM002);
        loan2M002.setStartDate("2023-04-15");
        loan2M002.setEndDate("2023-04-20");
        
        librarySystem.addLoan(loan1M002);
        librarySystem.addLoan(loan2M002);
        
        // Test total unique checkouts for M001
        int resultM001 = librarySystem.getTotalUniqueBooksCheckedOut(userM001);
        assertEquals("Member user M001 should have 3 unique book checkouts", 3, resultM001);
        
        // Test total unique checkouts for M002
        int resultM002 = librarySystem.getTotalUniqueBooksCheckedOut(userM002);
        assertEquals("Member user M002 should have 1 unique book checkout", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user
        GuestUser userG001 = new GuestUser();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        
        librarySystem.addUser(userG001);
        
        // Create book
        Book book1 = new Book();
        book1.setISBN("B001");
        book1.setTitle("Book 1");
        book1.setBarcode("BC001");
        book1.setNumberOfPages(200);
        
        librarySystem.addBook(book1);
        
        // Create loan for user G001
        Loan loanG001 = new Loan();
        loanG001.setBook(book1);
        loanG001.setUser(userG001);
        loanG001.setStartDate("2023-05-01");
        loanG001.setEndDate("2023-05-10");
        
        librarySystem.addLoan(loanG001);
        
        // Test total unique checkouts for G001
        int result = librarySystem.getTotalUniqueBooksCheckedOut(userG001);
        assertEquals("Guest user G001 should have 1 unique book checkout", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        MemberUser userM003 = new MemberUser();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        
        librarySystem.addUser(userM003);
        
        // Test total unique checkouts for M003 (should be 0)
        int result = librarySystem.getTotalUniqueBooksCheckedOut(userM003);
        assertEquals("Member user M003 with no checkouts should have 0 unique book checkouts", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create Member user
        MemberUser userM004 = new MemberUser();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        
        librarySystem.addUser(userM004);
        
        // Create 4 different books
        Book book1 = new Book();
        book1.setISBN("B001");
        book1.setTitle("Book 1");
        book1.setBarcode("BC001");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setISBN("B002");
        book2.setTitle("Book 2");
        book2.setBarcode("BC002");
        book2.setNumberOfPages(300);
        
        Book book3 = new Book();
        book3.setISBN("B003");
        book3.setTitle("Book 3");
        book3.setBarcode("BC003");
        book3.setNumberOfPages(400);
        
        Book book4 = new Book();
        book4.setISBN("B004");
        book4.setTitle("Book 4");
        book4.setBarcode("BC004");
        book4.setNumberOfPages(500);
        
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Create loans for user M004 (4 different books)
        Loan loan1M004 = new Loan();
        loan1M004.setBook(book1);
        loan1M004.setUser(userM004);
        loan1M004.setStartDate("2023-06-01");
        loan1M004.setEndDate("2023-07-01");
        
        Loan loan2M004 = new Loan();
        loan2M004.setBook(book2);
        loan2M004.setUser(userM004);
        loan2M004.setStartDate("2023-06-01");
        loan2M004.setEndDate("2023-07-01");
        
        Loan loan3M004 = new Loan();
        loan3M004.setBook(book3);
        loan3M004.setUser(userM004);
        loan3M004.setStartDate("2023-06-01");
        loan3M004.setEndDate("2023-07-01");
        
        Loan loan4M004 = new Loan();
        loan4M004.setBook(book4);
        loan4M004.setUser(userM004);
        loan4M004.setStartDate("2023-06-01");
        loan4M004.setEndDate("2023-07-01");
        
        librarySystem.addLoan(loan1M004);
        librarySystem.addLoan(loan2M004);
        librarySystem.addLoan(loan3M004);
        librarySystem.addLoan(loan4M004);
        
        // Test total unique checkouts for M004
        int result = librarySystem.getTotalUniqueBooksCheckedOut(userM004);
        assertEquals("Member user M004 should have 4 unique book checkouts", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create Guest user
        GuestUser userG002 = new GuestUser();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        
        librarySystem.addUser(userG002);
        
        // Create books
        Book book1 = new Book();
        book1.setISBN("B001");
        book1.setTitle("Book 1");
        book1.setBarcode("BC001");
        book1.setNumberOfPages(200);
        
        Book book4 = new Book();
        book4.setISBN("B004");
        book4.setTitle("Book 4");
        book4.setBarcode("BC004");
        book4.setNumberOfPages(500);
        
        librarySystem.addBook(book1);
        librarySystem.addBook(book4);
        
        // Create loans for user G002
        // 2 same books (B001)
        Loan loan1G002 = new Loan();
        loan1G002.setBook(book1);
        loan1G002.setUser(userG002);
        loan1G002.setStartDate("2023-07-15");
        loan1G002.setEndDate("2023-08-01");
        
        Loan loan2G002 = new Loan();
        loan2G002.setBook(book1);
        loan2G002.setUser(userG002);
        loan2G002.setStartDate("2023-08-15");
        loan2G002.setEndDate("2023-09-01");
        
        // 3 same books (B004)
        Loan loan3G002 = new Loan();
        loan3G002.setBook(book4);
        loan3G002.setUser(userG002);
        loan3G002.setStartDate("2024-06-01");
        loan3G002.setEndDate("2024-07-01");
        
        Loan loan4G002 = new Loan();
        loan4G002.setBook(book4);
        loan4G002.setUser(userG002);
        loan4G002.setStartDate("2024-07-02");
        loan4G002.setEndDate("2024-07-11");
        
        Loan loan5G002 = new Loan();
        loan5G002.setBook(book4);
        loan5G002.setUser(userG002);
        loan5G002.setStartDate("2024-08-01");
        loan5G002.setEndDate("2024-09-01");
        
        librarySystem.addLoan(loan1G002);
        librarySystem.addLoan(loan2G002);
        librarySystem.addLoan(loan3G002);
        librarySystem.addLoan(loan4G002);
        librarySystem.addLoan(loan5G002);
        
        // Test total unique checkouts for G002
        int result = librarySystem.getTotalUniqueBooksCheckedOut(userG002);
        assertEquals("Guest user G002 should have 2 unique book checkouts", 2, result);
    }
}