import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    // Test Case 1: Calculate Total Checkouts for Member User
    @Test
    public void testCase1_calculateTotalUniqueBooksForMemberUsers() {
        // SetUp: Create Member users M001 and M002
        User userM001 = new User();
        userM001.setId("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        
        User userM002 = new User();
        userM002.setId("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        
        // SetUp: Create books B001, B002, B003
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(300);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(400);
        
        // SetUp: Add books to library system
        Map<String, Book> books = new HashMap<>();
        books.put("B001", book1);
        books.put("B002", book2);
        books.put("B003", book3);
        librarySystem.setBooks(books);
        
        // SetUp: Create loans for user M001 (3 different books)
        Loan loan1M001 = new Loan();
        loan1M001.setBook(book1);
        loan1M001.setStartDate(LocalDate.of(2023, 1, 1));
        loan1M001.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2M001 = new Loan();
        loan2M001.setBook(book2);
        loan2M001.setStartDate(LocalDate.of(2023, 2, 1));
        loan2M001.setEndDate(LocalDate.of(2023, 2, 10));
        
        Loan loan3M001 = new Loan();
        loan3M001.setBook(book3);
        loan3M001.setStartDate(LocalDate.of(2023, 3, 15));
        loan3M001.setEndDate(LocalDate.of(2023, 3, 30));
        
        userM001.addLoan(loan1M001);
        userM001.addLoan(loan2M001);
        userM001.addLoan(loan3M001);
        
        // SetUp: Create loans for user M002 (2 same book B001)
        Loan loan1M002 = new Loan();
        loan1M002.setBook(book1);
        loan1M002.setStartDate(LocalDate.of(2023, 4, 1));
        loan1M002.setEndDate(LocalDate.of(2023, 4, 12));
        
        Loan loan2M002 = new Loan();
        loan2M002.setBook(book1);
        loan2M002.setStartDate(LocalDate.of(2023, 4, 15));
        loan2M002.setEndDate(LocalDate.of(2023, 4, 20));
        
        userM002.addLoan(loan1M002);
        userM002.addLoan(loan2M002);
        
        // SetUp: Add users to library system
        Map<String, User> users = new HashMap<>();
        users.put("M001", userM001);
        users.put("M002", userM002);
        librarySystem.setUsers(users);
        
        // Execute and Verify: Total unique books for M001 should be 3
        int resultM001 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M001");
        assertEquals("M001 should have 3 unique books", 3, resultM001);
        
        // Execute and Verify: Total unique books for M002 should be 1
        int resultM002 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M002");
        assertEquals("M002 should have 1 unique book", 1, resultM002);
    }
    
    // Test Case 2: Calculate Total Checkouts for Guest User
    @Test
    public void testCase2_calculateTotalUniqueBooksForGuestUser() {
        // SetUp: Create Guest user G001
        User userG001 = new User();
        userG001.setId("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        
        // SetUp: Create book B001
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        
        // SetUp: Add book to library system
        Map<String, Book> books = new HashMap<>();
        books.put("B001", book1);
        librarySystem.setBooks(books);
        
        // SetUp: Create loan for user G001 (1 book)
        Loan loan1G001 = new Loan();
        loan1G001.setBook(book1);
        loan1G001.setStartDate(LocalDate.of(2023, 5, 1));
        loan1G001.setEndDate(LocalDate.of(2023, 5, 10));
        
        userG001.addLoan(loan1G001);
        
        // SetUp: Add user to library system
        Map<String, User> users = new HashMap<>();
        users.put("G001", userG001);
        librarySystem.setUsers(users);
        
        // Execute and Verify: Total unique books for G001 should be 1
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("G001");
        assertEquals("G001 should have 1 unique book", 1, result);
    }
    
    // Test Case 3: Mixed User Types with No Checkouts
    @Test
    public void testCase3_calculateTotalUniqueBooksForUserWithNoCheckouts() {
        // SetUp: Create Member user M003 with no loans
        User userM003 = new User();
        userM003.setId("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        
        // SetUp: Add user to library system (no books needed since no loans)
        Map<String, User> users = new HashMap<>();
        users.put("M003", userM003);
        librarySystem.setUsers(users);
        
        // SetUp: Add empty books map to library system
        librarySystem.setBooks(new HashMap<>());
        
        // Execute and Verify: Total unique books for M003 should be 0
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M003");
        assertEquals("M003 should have 0 unique books", 0, result);
    }
    
    // Test Case 4: Calculate Total Checkouts for Multiple Users with Mixed Types
    @Test
    public void testCase4_calculateTotalUniqueBooksForMemberWithMultipleBooks() {
        // SetUp: Create Member user M004
        User userM004 = new User();
        userM004.setId("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        
        // SetUp: Create books B001, B002, B003, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(300);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(400);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(500);
        
        // SetUp: Add books to library system
        Map<String, Book> books = new HashMap<>();
        books.put("B001", book1);
        books.put("B002", book2);
        books.put("B003", book3);
        books.put("B004", book4);
        librarySystem.setBooks(books);
        
        // SetUp: Create loans for user M004 (4 different books)
        Loan loan1M004 = new Loan();
        loan1M004.setBook(book1);
        loan1M004.setStartDate(LocalDate.of(2023, 6, 1));
        loan1M004.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan2M004 = new Loan();
        loan2M004.setBook(book2);
        loan2M004.setStartDate(LocalDate.of(2023, 6, 1));
        loan2M004.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan3M004 = new Loan();
        loan3M004.setBook(book3);
        loan3M004.setStartDate(LocalDate.of(2023, 6, 1));
        loan3M004.setEndDate(LocalDate.of(2023, 7, 1));
        
        Loan loan4M004 = new Loan();
        loan4M004.setBook(book4);
        loan4M004.setStartDate(LocalDate.of(2023, 6, 1));
        loan4M004.setEndDate(LocalDate.of(2023, 7, 1));
        
        userM004.addLoan(loan1M004);
        userM004.addLoan(loan2M004);
        userM004.addLoan(loan3M004);
        userM004.addLoan(loan4M004);
        
        // SetUp: Add user to library system
        Map<String, User> users = new HashMap<>();
        users.put("M004", userM004);
        librarySystem.setUsers(users);
        
        // Execute and Verify: Total unique books for M004 should be 4
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M004");
        assertEquals("M004 should have 4 unique books", 4, result);
    }
    
    // Test Case 5: Calculate Total Checkouts for Multiple Users with Mixed Types
    @Test
    public void testCase5_calculateTotalUniqueBooksForGuestWithDuplicateBooks() {
        // SetUp: Create Guest user G002
        User userG002 = new User();
        userG002.setId("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        
        // SetUp: Create books B001 and B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(500);
        
        // SetUp: Add books to library system
        Map<String, Book> books = new HashMap<>();
        books.put("B001", book1);
        books.put("B004", book4);
        librarySystem.setBooks(books);
        
        // SetUp: Create loans for user G002 (B001 twice, B004 three times)
        // B001 loans
        Loan loan1G002_B001 = new Loan();
        loan1G002_B001.setBook(book1);
        loan1G002_B001.setStartDate(LocalDate.of(2023, 7, 15));
        loan1G002_B001.setEndDate(LocalDate.of(2023, 8, 1));
        
        Loan loan2G002_B001 = new Loan();
        loan2G002_B001.setBook(book1);
        loan2G002_B001.setStartDate(LocalDate.of(2023, 8, 15));
        loan2G002_B001.setEndDate(LocalDate.of(2023, 9, 1));
        
        // B004 loans
        Loan loan1G002_B004 = new Loan();
        loan1G002_B004.setBook(book4);
        loan1G002_B004.setStartDate(LocalDate.of(2024, 6, 1));
        loan1G002_B004.setEndDate(LocalDate.of(2024, 7, 1));
        
        Loan loan2G002_B004 = new Loan();
        loan2G002_B004.setBook(book4);
        loan2G002_B004.setStartDate(LocalDate.of(2024, 7, 2));
        loan2G002_B004.setEndDate(LocalDate.of(2024, 7, 11));
        
        Loan loan3G002_B004 = new Loan();
        loan3G002_B004.setBook(book4);
        loan3G002_B004.setStartDate(LocalDate.of(2024, 8, 1));
        loan3G002_B004.setEndDate(LocalDate.of(2024, 9, 1));
        
        userG002.addLoan(loan1G002_B001);
        userG002.addLoan(loan2G002_B001);
        userG002.addLoan(loan1G002_B004);
        userG002.addLoan(loan2G002_B004);
        userG002.addLoan(loan3G002_B004);
        
        // SetUp: Add user to library system
        Map<String, User> users = new HashMap<>();
        users.put("G002", userG002);
        librarySystem.setUsers(users);
        
        // Execute and Verify: Total unique books for G002 should be 2
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("G002");
        assertEquals("G002 should have 2 unique books", 2, result);
    }
    

}