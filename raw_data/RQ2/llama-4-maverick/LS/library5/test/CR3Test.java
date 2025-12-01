package edu.library.library5.test;

import edu.library.Book;
import edu.library.CheckOut;
import edu.library.LibraryFactory;
import edu.library.LibrarySystem;
import edu.library.User;
import edu.library.UserType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {

    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testCase1_singleBookBorrowed() throws ParseException {
        // Create a user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = factory.createUser();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        librarySystem.getUsers().add(user);

        // Create a book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300.
        Book book = factory.createBook();
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        librarySystem.getBooks().add(book);

        // Create a CheckOut record for User U001 with the book B001, start date: "2023-10-01", end date: "2023-10-15".
        CheckOut checkout = factory.createCheckOut();
        Date startDate = dateFormat.parse("2023-10-01");
        Date endDate = dateFormat.parse("2023-10-15");
        checkout.setStartDate(startDate);
        checkout.setEndDate(endDate);
        checkout.setBook(book);
        checkout.setUser(user);
        user.getCheckouts().add(checkout);

        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages.
        double average = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(300.0, average, 0.001);
    }

    @Test
    public void testCase2_multipleBooksBorrowed() throws ParseException {
        // Create a user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = factory.createUser();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        librarySystem.getUsers().add(user);

        // Create books:
        // - Book 1: title: "Data Structures", ISBN: "987654321", barcode: "B002", number of pages: 500.
        Book book1 = factory.createBook();
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        librarySystem.getBooks().add(book1);

        // - Book 2: title: "Algorithms", ISBN: "123123123", barcode: "B003", number of pages: 600.
        Book book2 = factory.createBook();
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        librarySystem.getBooks().add(book2);

        // Create CheckOut records for User U002:
        // - CheckOut for B002, start date: "2023-09-20", end date: "2023-09-25".
        CheckOut checkout1 = factory.createCheckOut();
        Date startDate1 = dateFormat.parse("2023-09-20");
        Date endDate1 = dateFormat.parse("2023-09-25");
        checkout1.setStartDate(startDate1);
        checkout1.setEndDate(endDate1);
        checkout1.setBook(book1);
        checkout1.setUser(user);
        user.getCheckouts().add(checkout1);

        // - CheckOut for B002, start date: "2023-10-20", end date: "2023-10-25".
        CheckOut checkout2 = factory.createCheckOut();
        Date startDate2 = dateFormat.parse("2023-10-20");
        Date endDate2 = dateFormat.parse("2023-10-25");
        checkout2.setStartDate(startDate2);
        checkout2.setEndDate(endDate2);
        checkout2.setBook(book1); // Same book as checkout1
        checkout2.setUser(user);
        user.getCheckouts().add(checkout2);

        // - CheckOut for B003, start date: "2023-09-30", end date: "2023-10-05".
        CheckOut checkout3 = factory.createCheckOut();
        Date startDate3 = dateFormat.parse("2023-09-30");
        Date endDate3 = dateFormat.parse("2023-10-05");
        checkout3.setStartDate(startDate3);
        checkout3.setEndDate(endDate3);
        checkout3.setBook(book2);
        checkout3.setUser(user);
        user.getCheckouts().add(checkout3);

        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages.
        double average = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(550.0, average, 0.001);
    }

    @Test
    public void testCase3_noBooksBorrowed() {
        // Create a user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = factory.createUser();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        librarySystem.getUsers().add(user);

        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages (or handle as undefined).
        double average = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(0.0, average, 0.001);
    }

    @Test
    public void testCase4_booksWithDifferentPageCounts() throws ParseException {
        // Create a user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = factory.createUser();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        librarySystem.getUsers().add(user);

        // Create books:
        // - Book 1: title: "Web Development", ISBN: "321321321", barcode: "B004", number of pages: 250.
        Book book1 = factory.createBook();
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        librarySystem.getBooks().add(book1);

        // - Book 2: title: "Machine Learning", ISBN: "456456456", barcode: "B005", number of pages: 350.
        Book book2 = factory.createBook();
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        librarySystem.getBooks().add(book2);

        // - Book 3: title: "Database Systems", ISBN: "654654654", barcode: "B006", number of pages: 450.
        Book book3 = factory.createBook();
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        librarySystem.getBooks().add(book3);

        // Create CheckOut records for User U004:
        // - CheckOut for B004, start date: "2023-09-15", end date: "2023-09-22".
        CheckOut checkout1 = factory.createCheckOut();
        Date startDate1 = dateFormat.parse("2023-09-15");
        Date endDate1 = dateFormat.parse("2023-09-22");
        checkout1.setStartDate(startDate1);
        checkout1.setEndDate(endDate1);
        checkout1.setBook(book1);
        checkout1.setUser(user);
        user.getCheckouts().add(checkout1);

        // - CheckOut for B005, start date: "2023-09-25", end date: "2023-10-02".
        CheckOut checkout2 = factory.createCheckOut();
        Date startDate2 = dateFormat.parse("2023-09-25");
        Date endDate2 = dateFormat.parse("2023-10-02");
        checkout2.setStartDate(startDate2);
        checkout2.setEndDate(endDate2);
        checkout2.setBook(book2);
        checkout2.setUser(user);
        user.getCheckouts().add(checkout2);

        // - CheckOut for B006, start date: "2023-10-03", end date: "2023-10-10".
        CheckOut checkout3 = factory.createCheckOut();
        Date startDate3 = dateFormat.parse("2023-10-03");
        Date endDate3 = dateFormat.parse("2023-10-10");
        checkout3.setStartDate(startDate3);
        checkout3.setEndDate(endDate3);
        checkout3.setBook(book3);
        checkout3.setUser(user);
        user.getCheckouts().add(checkout3);

        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages.
        double average = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(350.0, average, 0.001);
    }

    @Test
    public void testCase5_guestUser() throws ParseException {
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = factory.createUser();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        librarySystem.getUsers().add(user);

        // Create books:
        // - Book 1: title: "Networking", ISBN: "111111111", barcode: "B007", number of pages: 400.
        Book book1 = factory.createBook();
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        librarySystem.getBooks().add(book1);

        // - Book 2: title: "Cybersecurity", ISBN: "222222222", barcode: "B008", number of pages: 350.
        Book book2 = factory.createBook();
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        librarySystem.getBooks().add(book2);

        // Create CheckOut records for User U005:
        // - CheckOut for B007, start date: "2023-08-20", end date: "2023-08-30".
        CheckOut checkout1 = factory.createCheckOut();
        Date startDate1 = dateFormat.parse("2023-08-20");
        Date endDate1 = dateFormat.parse("2023-08-30");
        checkout1.setStartDate(startDate1);
        checkout1.setEndDate(endDate1);
        checkout1.setBook(book1);
        checkout1.setUser(user);
        user.getCheckouts().add(checkout1);

        // - CheckOut for B008, start date: "2023-09-01", end date: "2023-09-10".
        CheckOut checkout2 = factory.createCheckOut();
        Date startDate2 = dateFormat.parse("2023-09-01");
        Date endDate2 = dateFormat.parse("2023-09-10");
        checkout2.setStartDate(startDate2);
        checkout2.setEndDate(endDate2);
        checkout2.setBook(book2);
        checkout2.setUser(user);
        user.getCheckouts().add(checkout2);

        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages.
        double average = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        assertEquals(375.0, average, 0.001);
    }
}