import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_averagePagesCalculationForSingleBookBorrowed() throws ParseException {
        // Create a user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = new User("Alice", "alice@example.com", "123 Main St.", "U001", UserType.MEMBER);
        
        // Create a book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300.
        Book book = new Book("Java Programming", "B001", "123456789", 300);
        
        // Add user and book to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book);
        
        // Create a CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15".
        Date startDate = dateFormat.parse("2023-10-01");
        Date endDate = dateFormat.parse("2023-10-15");
        librarySystem.checkoutBook(user, book, startDate, endDate);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages.
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(300.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase2_averagePagesCalculationForMultipleBooksBorrowed() throws ParseException {
        // Create a user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = new User("Bob", "bob@example.com", "456 Elm St.", "U002", UserType.MEMBER);
        
        // Create books
        // Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500.
        Book book1 = new Book("Data Structures", "B002", "987654321", 500);
        // Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600.
        Book book2 = new Book("Algorithms", "B003", "123123123", 600);
        
        // Add user and books to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // Create CheckOut records for User U002
        // CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25".
        Date startDate1 = dateFormat.parse("2023-09-20");
        Date endDate1 = dateFormat.parse("2023-09-25");
        librarySystem.checkoutBook(user, book1, startDate1, endDate1);
        
        // CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25".
        Date startDate2 = dateFormat.parse("2023-10-20");
        Date endDate2 = dateFormat.parse("2023-10-25");
        librarySystem.checkoutBook(user, book1, startDate2, endDate2);
        
        // CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05".
        Date startDate3 = dateFormat.parse("2023-09-30");
        Date endDate3 = dateFormat.parse("2023-10-05");
        librarySystem.checkoutBook(user, book2, startDate3, endDate3);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages.
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(550.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase3_noBooksBorrowed() {
        // Create a user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = new User("Charlie", "charlie@example.com", "789 Oak St.", "U003", UserType.MEMBER);
        
        // Add user to library system
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined).
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(0.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase4_averagePagesCalculationForBooksWithDifferentPageCounts() throws ParseException {
        // Create a user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = new User("Daisy", "daisy@example.com", "101 Maple St.", "U004", UserType.MEMBER);
        
        // Create books
        // Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250.
        Book book1 = new Book("Web Development", "B004", "321321321", 250);
        // Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350.
        Book book2 = new Book("Machine Learning", "B005", "456456456", 350);
        // Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450.
        Book book3 = new Book("Database Systems", "B006", "654654654", 450);
        
        // Add user and books to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Create CheckOut records for User U004
        // CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22".
        Date startDate1 = dateFormat.parse("2023-09-15");
        Date endDate1 = dateFormat.parse("2023-09-22");
        librarySystem.checkoutBook(user, book1, startDate1, endDate1);
        
        // CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02".
        Date startDate2 = dateFormat.parse("2023-09-25");
        Date endDate2 = dateFormat.parse("2023-10-02");
        librarySystem.checkoutBook(user, book2, startDate2, endDate2);
        
        // CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10".
        Date startDate3 = dateFormat.parse("2023-10-03");
        Date endDate3 = dateFormat.parse("2023-10-10");
        librarySystem.checkoutBook(user, book3, startDate3, endDate3);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages.
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(350.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase5_averagePagesCalculationForGuestUser() throws ParseException {
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = new User("Eve", "eve@example.com", "202 Birch St.", "U005", UserType.GUEST);
        
        // Create books
        // Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400.
        Book book1 = new Book("Networking", "B007", "111111111", 400);
        // Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350.
        Book book2 = new Book("Cybersecurity", "B008", "222222222", 350);
        
        // Add user and books to library system
        librarySystem.addUser(user);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // Create CheckOut records for User U005
        // CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30".
        Date startDate1 = dateFormat.parse("2023-08-20");
        Date endDate1 = dateFormat.parse("2023-08-30");
        librarySystem.checkoutBook(user, book1, startDate1, endDate1);
        
        // CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10".
        Date startDate2 = dateFormat.parse("2023-09-01");
        Date endDate2 = dateFormat.parse("2023-09-10");
        librarySystem.checkoutBook(user, book2, startDate2, endDate2);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages.
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(375.0, averagePages, 0.001);
    }
}