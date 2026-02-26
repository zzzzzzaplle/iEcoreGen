import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp: Create a MEMBER user with ID: U001
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create books: Book1 and Book2
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setIsbn("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setIsbn("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        // Record CheckOuts
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setStartDate(LocalDate.of(2023, 1, 10));
        loan1.setEndDate(LocalDate.of(2023, 1, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setStartDate(LocalDate.of(2023, 3, 5));
        loan2.setEndDate(LocalDate.of(2023, 3, 10));
        
        Loan loan3 = new Loan();
        loan3.setBook(book1);
        loan3.setStartDate(LocalDate.of(2023, 5, 20));
        loan3.setEndDate(LocalDate.of(2023, 5, 25));
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        user.addLoan(loan3);
        
        // Add user and books to library system
        Map<String, User> users = new HashMap<>();
        users.put("U001", user);
        librarySystem.setUsers(users);
        
        Map<String, Book> books = new HashMap<>();
        books.put("B001", book1);
        books.put("B002", book2);
        librarySystem.setBooks(books);
        
        // Execute the method to count unique book borrowings for user U001
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U001", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp: Create a GUEST user with ID: U002
        User user = new User();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        
        // Create books: Book3 and Book4
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setIsbn("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setIsbn("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        // Record CheckOuts
        Loan loan1 = new Loan();
        loan1.setBook(book3);
        loan1.setStartDate(LocalDate.of(2023, 6, 10));
        loan1.setEndDate(LocalDate.of(2023, 6, 15));
        
        Loan loan2 = new Loan();
        loan2.setBook(book4);
        loan2.setStartDate(LocalDate.of(2023, 6, 20));
        loan2.setEndDate(LocalDate.of(2023, 6, 25));
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Add user and books to library system
        Map<String, User> users = new HashMap<>();
        users.put("U002", user);
        librarySystem.setUsers(users);
        
        Map<String, Book> books = new HashMap<>();
        books.put("B003", book3);
        books.put("B004", book4);
        librarySystem.setBooks(books);
        
        // Execute the method to count unique book borrowings for user U002
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U002", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp: Create a MEMBER user with ID: U003 (no loans)
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        
        // Add user to library system (no books needed since no loans)
        Map<String, User> users = new HashMap<>();
        users.put("U003", user);
        librarySystem.setUsers(users);
        
        // Execute the method to count unique book borrowings for user U003
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U003", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp: Create a MEMBER user with ID: U004
        User user = new User();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        
        // Create a book: Book5
        Book book5 = new Book();
        book5.setBarcode("B005");
        book5.setIsbn("978-3-16-148410-1");
        book5.setTitle("Book5");
        
        // Record CheckOuts (same book borrowed twice)
        Loan loan1 = new Loan();
        loan1.setBook(book5);
        loan1.setStartDate(LocalDate.of(2023, 8, 1));
        loan1.setEndDate(LocalDate.of(2023, 8, 10));
        
        Loan loan2 = new Loan();
        loan2.setBook(book5);
        loan2.setStartDate(LocalDate.of(2023, 8, 15));
        loan2.setEndDate(LocalDate.of(2023, 8, 25));
        
        user.addLoan(loan1);
        user.addLoan(loan2);
        
        // Add user and book to library system
        Map<String, User> users = new HashMap<>();
        users.put("U004", user);
        librarySystem.setUsers(users);
        
        Map<String, Book> books = new HashMap<>();
        books.put("B005", book5);
        librarySystem.setBooks(books);
        
        // Execute the method to count unique book borrowings for user U004
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 10);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U004", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp: Create two users: U005 (MEMBER) and U006 (GUEST)
        User user5 = new User();
        user5.setId("U005");
        user5.setName("Eve");
        user5.setEmail("eve@example.com");
        user5.setAddress("202 Birch St.");
        
        User user6 = new User();
        user6.setId("U006");
        user6.setName("Frank");
        user6.setEmail("frank@example.com");
        user6.setAddress("303 Cedar St.");
        
        // Create books: Book6 and Book7
        Book book6 = new Book();
        book6.setBarcode("B006");
        book6.setIsbn("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setBarcode("B007");
        book7.setIsbn("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        // Record CheckOuts for both users
        Loan loan1 = new Loan();
        loan1.setBook(book6);
        loan1.setStartDate(LocalDate.of(2023, 9, 1));
        loan1.setEndDate(LocalDate.of(2023, 9, 10));
        
        Loan loan2 = new Loan();
        loan2.setBook(book7);
        loan2.setStartDate(LocalDate.of(2023, 9, 15));
        loan2.setEndDate(LocalDate.of(2023, 9, 20));
        
        Loan loan3 = new Loan();
        loan3.setBook(book6);
        loan3.setStartDate(LocalDate.of(2023, 9, 5));
        loan3.setEndDate(LocalDate.of(2023, 9, 15));
        
        user5.addLoan(loan1);
        user5.addLoan(loan2);
        user6.addLoan(loan3);
        
        // Add users and books to library system
        Map<String, User> users = new HashMap<>();
        users.put("U005", user5);
        users.put("U006", user6);
        librarySystem.setUsers(users);
        
        Map<String, Book> books = new HashMap<>();
        books.put("B006", book6);
        books.put("B007", book7);
        librarySystem.setBooks(books);
        
        // Execute the method to count unique book borrowings for user U005
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U005", startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }

}