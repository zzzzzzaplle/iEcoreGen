import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_averagePagesSingleBookBorrowed() {
        // Test Case 1: Average Pages Calculation for Single Book Borrowed
        // Create user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300
        Book book = new Book();
        book.setBarcode("B001");
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setNumberOfPages(300);
        
        // Create CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15"
        CheckOut checkout = new CheckOut();
        checkout.setBook(book);
        checkout.setStartDate(LocalDate.parse("2023-10-01", formatter));
        checkout.setEndDate(LocalDate.parse("2023-10-15", formatter));
        
        user.addCheckOut(checkout);
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        double expectedAverage = 300.0;
        double actualAverage = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        assertEquals("Average pages should be 300.0 for single book", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averagePagesMultipleBooksBorrowed() {
        // Test Case 2: Average Pages Calculation for Multiple Books Borrowed
        // Create user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create books:
        // Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500
        Book book1 = new Book();
        book1.setBarcode("B002");
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setNumberOfPages(500);
        
        // Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600
        Book book2 = new Book();
        book2.setBarcode("B003");
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setNumberOfPages(600);
        
        // Create CheckOut records for User U002:
        // CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25"
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.parse("2023-09-20", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-09-25", formatter));
        
        // CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25"
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book1);
        checkout2.setStartDate(LocalDate.parse("2023-10-20", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-10-25", formatter));
        
        // CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05"
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book2);
        checkout3.setStartDate(LocalDate.parse("2023-09-30", formatter));
        checkout3.setEndDate(LocalDate.parse("2023-10-05", formatter));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        double expectedAverage = 550.0;
        double actualAverage = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        assertEquals("Average pages should be 550.0 for multiple books", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_noBooksBorrowed() {
        // Test Case 3: No Books Borrowed
        // Create user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = 0.0 pages
        double expectedAverage = 0.0;
        double actualAverage = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        assertEquals("Average pages should be 0.0 when no books borrowed", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averagePagesDifferentPageCounts() {
        // Test Case 4: Average Pages Calculation for Books with Different Page Counts
        // Create user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create books:
        // Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250
        Book book1 = new Book();
        book1.setBarcode("B004");
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setNumberOfPages(250);
        
        // Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350
        Book book2 = new Book();
        book2.setBarcode("B005");
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setNumberOfPages(350);
        
        // Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450
        Book book3 = new Book();
        book3.setBarcode("B006");
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setNumberOfPages(450);
        
        // Create CheckOut records for User U004:
        // CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22"
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.parse("2023-09-15", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-09-22", formatter));
        
        // CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02"
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.parse("2023-09-25", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-10-02", formatter));
        
        // CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10"
        CheckOut checkout3 = new CheckOut();
        checkout3.setBook(book3);
        checkout3.setStartDate(LocalDate.parse("2023-10-03", formatter));
        checkout3.setEndDate(LocalDate.parse("2023-10-10", formatter));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        user.addCheckOut(checkout3);
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        double expectedAverage = 350.0;
        double actualAverage = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        assertEquals("Average pages should be 350.0 for books with different page counts", expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averagePagesGuestUser() {
        // Test Case 5: Average Pages Calculation for Guest User
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // Create books:
        // Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400
        Book book1 = new Book();
        book1.setBarcode("B007");
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setNumberOfPages(400);
        
        // Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350
        Book book2 = new Book();
        book2.setBarcode("B008");
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setNumberOfPages(350);
        
        // Create CheckOut records for User U005:
        // CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30"
        CheckOut checkout1 = new CheckOut();
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.parse("2023-08-20", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-08-30", formatter));
        
        // CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10"
        CheckOut checkout2 = new CheckOut();
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.parse("2023-09-01", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-09-10", formatter));
        
        user.addCheckOut(checkout1);
        user.addCheckOut(checkout2);
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        double expectedAverage = 375.0;
        double actualAverage = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        assertEquals("Average pages should be 375.0 for guest user", expectedAverage, actualAverage, 0.001);
    }
}