import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() {
        // SetUp
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        Book book = new Book();
        book.setBarcode("B001");
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setNumberOfPages(300);
        
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate("2023-10-01");
        checkout.setEndDate("2023-10-15");
        
        user.addCheckOut(checkout);
        book.addCheckOut(checkout);
        
        // Execute test
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected output
        assertEquals("Average pages should be 300.0 for single book", 300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        Book book1 = new Book();
        book1.setBarcode("B002");
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setNumberOfPages(500);
        
        Book book2 = new Book();
        book2.setBarcode("B003");
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setNumberOfPages(600);
        
        // First checkout for B002
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-09-20");
        checkout1.setEndDate("2023-09-25");
        user.addCheckOut(checkout1);
        book1.addCheckOut(checkout1);
        
        // Second checkout for B002 (same book)
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate("2023-10-20");
        checkout2.setEndDate("2023-10-25");
        user.addCheckOut(checkout2);
        book1.addCheckOut(checkout2);
        
        // Checkout for B003
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book2);
        checkout3.setStartDate("2023-09-30");
        checkout3.setEndDate("2023-10-05");
        user.addCheckOut(checkout3);
        book2.addCheckOut(checkout3);
        
        // Execute test
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected output
        assertEquals("Average pages should be 550.0 for two unique books", 550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Execute test
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected output
        assertEquals("Average pages should be 0.0 when no books borrowed", 0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        Book book1 = new Book();
        book1.setBarcode("B004");
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setNumberOfPages(250);
        
        Book book2 = new Book();
        book2.setBarcode("B005");
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setNumberOfPages(350);
        
        Book book3 = new Book();
        book3.setBarcode("B006");
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setNumberOfPages(450);
        
        // Checkout for B004
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-09-15");
        checkout1.setEndDate("2023-09-22");
        user.addCheckOut(checkout1);
        book1.addCheckOut(checkout1);
        
        // Checkout for B005
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-09-25");
        checkout2.setEndDate("2023-10-02");
        user.addCheckOut(checkout2);
        book2.addCheckOut(checkout2);
        
        // Checkout for B006
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate("2023-10-03");
        checkout3.setEndDate("2023-10-10");
        user.addCheckOut(checkout3);
        book3.addCheckOut(checkout3);
        
        // Execute test
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected output
        assertEquals("Average pages should be 350.0 for three books with different page counts", 350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        Book book1 = new Book();
        book1.setBarcode("B007");
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setNumberOfPages(400);
        
        Book book2 = new Book();
        book2.setBarcode("B008");
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setNumberOfPages(350);
        
        // Checkout for B007
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate("2023-08-20");
        checkout1.setEndDate("2023-08-30");
        user.addCheckOut(checkout1);
        book1.addCheckOut(checkout1);
        
        // Checkout for B008
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate("2023-09-01");
        checkout2.setEndDate("2023-09-10");
        user.addCheckOut(checkout2);
        book2.addCheckOut(checkout2);
        
        // Execute test
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Verify expected output
        assertEquals("Average pages should be 375.0 for guest user with two books", 375.0, result, 0.001);
    }
}