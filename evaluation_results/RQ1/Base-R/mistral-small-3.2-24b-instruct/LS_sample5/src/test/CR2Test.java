import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp
        // 1. Create a MEMBER user with ID: U001
        User user = new User();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        librarySystem.addUser(user);
        
        // 2. Create books
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setBarcode("B001");
        book1.setTitle("Book1");
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setBarcode("B002");
        book2.setTitle("Book2");
        librarySystem.addBook(book2);
        
        // 3. Record CheckOuts
        // CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        librarySystem.checkoutBook("U001", "B001", LocalDate.of(2023, 1, 10), LocalDate.of(2023, 1, 15));
        
        // CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        librarySystem.checkoutBook("U001", "B002", LocalDate.of(2023, 3, 5), LocalDate.of(2023, 3, 10));
        
        // CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
        librarySystem.checkoutBook("U001", "B001", LocalDate.of(2023, 5, 20), LocalDate.of(2023, 5, 25));
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U001", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp
        // 1. Create a GUEST user with ID: U002
        User user = new User();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        librarySystem.addUser(user);
        
        // 2. Create books
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setBarcode("B003");
        book3.setTitle("Book3");
        librarySystem.addBook(book3);
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setBarcode("B004");
        book4.setTitle("Book4");
        librarySystem.addBook(book4);
        
        // 3. Record CheckOuts
        // CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        librarySystem.checkoutBook("U002", "B003", LocalDate.of(2023, 6, 10), LocalDate.of(2023, 6, 15));
        
        // CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
        librarySystem.checkoutBook("U002", "B004", LocalDate.of(2023, 6, 20), LocalDate.of(2023, 6, 25));
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate startDate = LocalDate.of(2023, 6, 1);
        LocalDate endDate = LocalDate.of(2023, 6, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U002", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp
        // 1. Create a MEMBER user with ID: U003
        User user = new User();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        librarySystem.addUser(user);
        
        // Execute the method to count unique book borrowings for user U003 for the period
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U003", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp
        // 1. Create a MEMBER user with ID: U004
        User user = new User();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        librarySystem.addUser(user);
        
        // 2. Create a book: Book5
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setBarcode("B005");
        book5.setTitle("Book5");
        librarySystem.addBook(book5);
        
        // 3. Record CheckOuts
        // CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        librarySystem.checkoutBook("U004", "B005", LocalDate.of(2023, 8, 1), LocalDate.of(2023, 8, 10));
        
        // CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
        librarySystem.checkoutBook("U004", "B005", LocalDate.of(2023, 8, 15), LocalDate.of(2023, 8, 25));
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 10);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U004", startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp
        // 1. Create a MEMBER user with ID: U005
        User user1 = new User();
        user1.setId("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        librarySystem.addUser(user1);
        
        // 2. Create a GUEST user with ID: U006
        User user2 = new User();
        user2.setId("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        librarySystem.addUser(user2);
        
        // 3. Create books
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setBarcode("B006");
        book6.setTitle("Book6");
        librarySystem.addBook(book6);
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setBarcode("B007");
        book7.setTitle("Book7");
        librarySystem.addBook(book7);
        
        // 4. Record CheckOuts
        // CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        librarySystem.checkoutBook("U005", "B006", LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10));
        
        // CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        librarySystem.checkoutBook("U005", "B007", LocalDate.of(2023, 9, 15), LocalDate.of(2023, 9, 20));
        
        // CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
        librarySystem.checkoutBook("U006", "B006", LocalDate.of(2023, 9, 5), LocalDate.of(2023, 9, 15));
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);
        int result = librarySystem.countUniqueBooksBorrowedByUser("U005", startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}