import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member user M001
        User user1 = new User();
        user1.setId("M001");
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        user1.setAddress("123 Main St");
        librarySystem.addUser(user1);
        
        // Create Member user M002
        User user2 = new User();
        user2.setId("M002");
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        user2.setAddress("456 Elm St");
        librarySystem.addUser(user2);
        
        // Create books B001, B002, B003
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(300);
        librarySystem.addBook(book2);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(400);
        librarySystem.addBook(book3);
        
        // Check out 3 different books with user M001
        librarySystem.checkoutBook("M001", "B001", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15));
        librarySystem.checkoutBook("M001", "B002", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 10));
        librarySystem.checkoutBook("M001", "B003", LocalDate.of(2023, 3, 15), LocalDate.of(2023, 3, 30));
        
        // Check out 2 same books with user M002
        librarySystem.checkoutBook("M002", "B001", LocalDate.of(2023, 4, 1), LocalDate.of(2023, 4, 12));
        librarySystem.checkoutBook("M002", "B001", LocalDate.of(2023, 4, 15), LocalDate.of(2023, 4, 20));
        
        // Calculate total unique books for M001 and M002
        int resultM001 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M001");
        int resultM002 = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M002");
        
        // Verify expected outputs
        assertEquals("M001 should have 3 unique books", 3, resultM001);
        assertEquals("M002 should have 1 unique book", 1, resultM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user G001
        User user = new User();
        user.setId("G001");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St");
        librarySystem.addUser(user);
        
        // Create book B001
        Book book = new Book();
        book.setBarcode("B001");
        book.setTitle("Book 1");
        book.setIsbn("ISBN001");
        book.setNumberOfPages(200);
        librarySystem.addBook(book);
        
        // Check out 1 book with user G001
        librarySystem.checkoutBook("G001", "B001", LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 10));
        
        // Calculate total unique books for G001
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("G001");
        
        // Verify expected output
        assertEquals("G001 should have 1 unique book", 1, result);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        User user = new User();
        user.setId("M003");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("654 Maple St");
        librarySystem.addUser(user);
        
        // Calculate total unique books for M003
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M003");
        
        // Verify expected output
        assertEquals("M003 should have 0 unique books", 0, result);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Create Member user M004
        User user = new User();
        user.setId("M004");
        user.setName("George");
        user.setEmail("george@example.com");
        user.setAddress("135 Cedar St");
        librarySystem.addUser(user);
        
        // Create books B001, B002, B003, B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        librarySystem.addBook(book1);
        
        Book book2 = new Book();
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        book2.setIsbn("ISBN002");
        book2.setNumberOfPages(300);
        librarySystem.addBook(book2);
        
        Book book3 = new Book();
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        book3.setIsbn("ISBN003");
        book3.setNumberOfPages(400);
        librarySystem.addBook(book3);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(500);
        librarySystem.addBook(book4);
        
        // Check out 4 different books with user M004
        librarySystem.checkoutBook("M004", "B001", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.checkoutBook("M004", "B002", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.checkoutBook("M004", "B003", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        librarySystem.checkoutBook("M004", "B004", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 7, 1));
        
        // Calculate total unique books for M004
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("M004");
        
        // Verify expected output
        assertEquals("M004 should have 4 unique books", 4, result);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes() {
        // Create Guest user G002
        User user = new User();
        user.setId("G002");
        user.setName("Hannah");
        user.setEmail("hannah@example.com");
        user.setAddress("246 Spruce St");
        librarySystem.addUser(user);
        
        // Create books B001 and B004
        Book book1 = new Book();
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        book1.setIsbn("ISBN001");
        book1.setNumberOfPages(200);
        librarySystem.addBook(book1);
        
        Book book4 = new Book();
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        book4.setIsbn("ISBN004");
        book4.setNumberOfPages(500);
        librarySystem.addBook(book4);
        
        // Check out 2 same books (B001) with user G002
        librarySystem.checkoutBook("G002", "B001", LocalDate.of(2023, 7, 15), LocalDate.of(2023, 8, 1));
        librarySystem.checkoutBook("G002", "B001", LocalDate.of(2023, 8, 15), LocalDate.of(2023, 9, 1));
        
        // Check out B004 3 times with user G002
        librarySystem.checkoutBook("G002", "B004", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 7, 1));
        librarySystem.checkoutBook("G002", "B004", LocalDate.of(2024, 7, 2), LocalDate.of(2024, 7, 11));
        librarySystem.checkoutBook("G002", "B004", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 9, 1));
        
        // Calculate total unique books for G002
        int result = librarySystem.calculateTotalUniqueBooksCheckedOutByUser("G002");
        
        // Verify expected output
        assertEquals("G002 should have 2 unique books", 2, result);
    }
}