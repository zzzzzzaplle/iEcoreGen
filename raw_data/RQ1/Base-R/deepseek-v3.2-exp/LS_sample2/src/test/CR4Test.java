import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member user M001: Alice
        MemberUser userM001 = new MemberUser();
        userM001.setId("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        librarySystem.addUser(userM001);
        
        // Create Member user M002: Bob
        MemberUser userM002 = new MemberUser();
        userM002.setId("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        librarySystem.addUser(userM002);
        
        // Create books B001, B002, B003
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(100);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(200);
        librarySystem.addBook(book2);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(300);
        librarySystem.addBook(book3);
        
        // Check out 3 different books for user M001
        Loan loan1M001 = new Loan();
        loan1M001.setBook(book1);
        loan1M001.setUser(userM001);
        loan1M001.setStartDate("2023-01-01");
        loan1M001.setEndDate("2023-01-15");
        librarySystem.addLoan(loan1M001);
        
        Loan loan2M001 = new Loan();
        loan2M001.setBook(book2);
        loan2M001.setUser(userM001);
        loan2M001.setStartDate("2023-02-01");
        loan2M001.setEndDate("2023-02-10");
        librarySystem.addLoan(loan2M001);
        
        Loan loan3M001 = new Loan();
        loan3M001.setBook(book3);
        loan3M001.setUser(userM001);
        loan3M001.setStartDate("2023-03-15");
        loan3M001.setEndDate("2023-03-30");
        librarySystem.addLoan(loan3M001);
        
        // Check out 2 same books (B001) for user M002
        Loan loan1M002 = new Loan();
        loan1M002.setBook(book1);
        loan1M002.setUser(userM002);
        loan1M002.setStartDate("2023-04-01");
        loan1M002.setEndDate("2023-04-12");
        librarySystem.addLoan(loan1M002);
        
        Loan loan2M002 = new Loan();
        loan2M002.setBook(book1);
        loan2M002.setUser(userM002);
        loan2M002.setStartDate("2023-04-15");
        loan2M002.setEndDate("2023-04-20");
        librarySystem.addLoan(loan2M002);
        
        // Verify total unique checkouts for M001 = 3
        assertEquals(3, librarySystem.getTotalUniqueBooksBorrowed(userM001));
        
        // Verify total unique checkouts for M002 = 1
        assertEquals(1, librarySystem.getTotalUniqueBooksBorrowed(userM002));
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user G001: Charlie
        GuestUser userG001 = new GuestUser();
        userG001.setId("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        librarySystem.addUser(userG001);
        
        // Create book B001
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(100);
        librarySystem.addBook(book1);
        
        // Check out 1 book for user G001
        Loan loan1G001 = new Loan();
        loan1G001.setBook(book1);
        loan1G001.setUser(userG001);
        loan1G001.setStartDate("2023-05-01");
        loan1G001.setEndDate("2023-05-10");
        librarySystem.addLoan(loan1G001);
        
        // Verify total unique checkouts for G001 = 1
        assertEquals(1, librarySystem.getTotalUniqueBooksBorrowed(userG001));
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user M003: Eve with no checkouts
        MemberUser userM003 = new MemberUser();
        userM003.setId("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        librarySystem.addUser(userM003);
        
        // Verify total unique checkouts for M003 = 0
        assertEquals(0, librarySystem.getTotalUniqueBooksBorrowed(userM003));
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create Member user M004: George
        MemberUser userM004 = new MemberUser();
        userM004.setId("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        librarySystem.addUser(userM004);
        
        // Create 4 different books
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(100);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(200);
        librarySystem.addBook(book2);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(300);
        librarySystem.addBook(book3);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(400);
        librarySystem.addBook(book4);
        
        // Check out 4 different books for user M004
        Loan loan1M004 = new Loan();
        loan1M004.setBook(book1);
        loan1M004.setUser(userM004);
        loan1M004.setStartDate("2023-06-01");
        loan1M004.setEndDate("2023-07-01");
        librarySystem.addLoan(loan1M004);
        
        Loan loan2M004 = new Loan();
        loan2M004.setBook(book2);
        loan2M004.setUser(userM004);
        loan2M004.setStartDate("2023-06-01");
        loan2M004.setEndDate("2023-07-01");
        librarySystem.addLoan(loan2M004);
        
        Loan loan3M004 = new Loan();
        loan3M004.setBook(book3);
        loan3M004.setUser(userM004);
        loan3M004.setStartDate("2023-06-01");
        loan3M004.setEndDate("2023-07-01");
        librarySystem.addLoan(loan3M004);
        
        Loan loan4M004 = new Loan();
        loan4M004.setBook(book4);
        loan4M004.setUser(userM004);
        loan4M004.setStartDate("2023-06-01");
        loan4M004.setEndDate("2023-07-01");
        librarySystem.addLoan(loan4M004);
        
        // Verify total unique checkouts for M004 = 4
        assertEquals(4, librarySystem.getTotalUniqueBooksBorrowed(userM004));
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create Guest user G002: Hannah
        GuestUser userG002 = new GuestUser();
        userG002.setId("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        librarySystem.addUser(userG002);
        
        // Create books B001 and B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(100);
        librarySystem.addBook(book1);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(400);
        librarySystem.addBook(book4);
        
        // Check out B001 twice for user G002
        Loan loan1G002 = new Loan();
        loan1G002.setBook(book1);
        loan1G002.setUser(userG002);
        loan1G002.setStartDate("2023-07-15");
        loan1G002.setEndDate("2023-08-01");
        librarySystem.addLoan(loan1G002);
        
        Loan loan2G002 = new Loan();
        loan2G002.setBook(book1);
        loan2G002.setUser(userG002);
        loan2G002.setStartDate("2023-08-15");
        loan2G002.setEndDate("2023-09-01");
        librarySystem.addLoan(loan2G002);
        
        // Check out B004 three times for user G002
        Loan loan3G002 = new Loan();
        loan3G002.setBook(book4);
        loan3G002.setUser(userG002);
        loan3G002.setStartDate("2024-06-01");
        loan3G002.setEndDate("2024-07-01");
        librarySystem.addLoan(loan3G002);
        
        Loan loan4G002 = new Loan();
        loan4G002.setBook(book4);
        loan4G002.setUser(userG002);
        loan4G002.setStartDate("2024-07-02");
        loan4G002.setEndDate("2024-07-11");
        librarySystem.addLoan(loan4G002);
        
        Loan loan5G002 = new Loan();
        loan5G002.setBook(book4);
        loan5G002.setUser(userG002);
        loan5G002.setStartDate("2024-08-01");
        loan5G002.setEndDate("2024-09-01");
        librarySystem.addLoan(loan5G002);
        
        // Verify total unique checkouts for G002 = 2
        assertEquals(2, librarySystem.getTotalUniqueBooksBorrowed(userG002));
    }
}