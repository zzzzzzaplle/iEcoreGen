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
        // 1. Create a user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = new User();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // 2. Create a book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300.
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // 3. Create a CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15".
        CheckOut checkOut = new CheckOut();
        checkOut.setStartDate("2023-10-01");
        checkOut.setEndDate("2023-10-15");
        checkOut.setBook(book);
        user.addCheckOut(checkOut);
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages.
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(300.0, result, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() {
        // SetUp
        // 1. Create a user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = new User();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // 2. Create books:
        //    - Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500.
        Book book1 = new Book();
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        
        //    - Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600.
        Book book2 = new Book();
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        
        // 3. Create CheckOut records for User U002:
        //    - CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25".
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setStartDate("2023-09-20");
        checkOut1.setEndDate("2023-09-25");
        checkOut1.setBook(book1);
        user.addCheckOut(checkOut1);
        
        //    - CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25".
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setStartDate("2023-10-20");
        checkOut2.setEndDate("2023-10-25");
        checkOut2.setBook(book1);
        user.addCheckOut(checkOut2);
        
        //    - CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05".
        CheckOut checkOut3 = new CheckOut();
        checkOut3.setStartDate("2023-09-30");
        checkOut3.setEndDate("2023-10-05");
        checkOut3.setBook(book2);
        user.addCheckOut(checkOut3);
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages.
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(550.0, result, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() {
        // SetUp
        // 1. Create a user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = new User();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined).
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() {
        // SetUp
        // 1. Create a user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = new User();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // 2. Create books:
        //    - Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250.
        Book book1 = new Book();
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        
        //    - Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350.
        Book book2 = new Book();
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        
        //    - Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450.
        Book book3 = new Book();
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        
        // 3. Create CheckOut records for User U004:
        //    - CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22".
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setStartDate("2023-09-15");
        checkOut1.setEndDate("2023-09-22");
        checkOut1.setBook(book1);
        user.addCheckOut(checkOut1);
        
        //    - CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02".
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setStartDate("2023-09-25");
        checkOut2.setEndDate("2023-10-02");
        checkOut2.setBook(book2);
        user.addCheckOut(checkOut2);
        
        //    - CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10".
        CheckOut checkOut3 = new CheckOut();
        checkOut3.setStartDate("2023-10-03");
        checkOut3.setEndDate("2023-10-10");
        checkOut3.setBook(book3);
        user.addCheckOut(checkOut3);
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages.
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(350.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() {
        // SetUp
        // 1. Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = new User();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // 2. Create books:
        //    - Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400.
        Book book1 = new Book();
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        
        //    - Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350.
        Book book2 = new Book();
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        
        // 3. Create CheckOut records for User U005:
        //    - CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30".
        CheckOut checkOut1 = new CheckOut();
        checkOut1.setStartDate("2023-08-20");
        checkOut1.setEndDate("2023-08-30");
        checkOut1.setBook(book1);
        user.addCheckOut(checkOut1);
        
        //    - CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10".
        CheckOut checkOut2 = new CheckOut();
        checkOut2.setStartDate("2023-09-01");
        checkOut2.setEndDate("2023-09-10");
        checkOut2.setBook(book2);
        user.addCheckOut(checkOut2);
        
        librarySystem.addUser(user);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages.
        double result = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(375.0, result, 0.001);
    }
}