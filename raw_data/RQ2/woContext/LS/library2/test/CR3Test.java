package edu.library.library2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.library.Book;
import edu.library.User;
import edu.library.CheckOut;
import edu.library.LibrarySystem;
import edu.library.LibraryFactory;
import edu.library.UserType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and library system using Ecore factory pattern
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AveragePagesCalculationForSingleBookBorrowed() throws Exception {
        // Create user with ID: U001, Name: "Alice", Email: "alice@example.com", Address: "123 Main St."
        User user = factory.createUser();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create book B001 with title: "Java Programming", ISBN: "123456789", barcode: "B001", number of pages: 300
        Book book = factory.createBook();
        book.setTitle("Java Programming");
        book.setISBN("123456789");
        book.setBarcode("B001");
        book.setNumberOfPages(300);
        
        // Create CheckOut record for User U001 with the book B001
        CheckOut checkout = factory.createCheckOut();
        checkout.setStartDate(dateFormat.parse("2023-10-01 00:00:00"));
        checkout.setEndDate(dateFormat.parse("2023-10-15 00:00:00"));
        checkout.setBook(book);
        
        // Add checkout to user
        user.getCheckouts().add(checkout);
        
        // Add user and book to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book);
        
        // Calculate average number of pages
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = 300 / 1 = 300.0 pages
        assertEquals(300.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase2_AveragePagesCalculationForMultipleBooksBorrowed() throws Exception {
        // Create user with ID: U002, Name: "Bob", Email: "bob@example.com", Address: "456 Elm St."
        User user = factory.createUser();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Elm St.");
        
        // Create books
        Book book1 = factory.createBook();
        book1.setTitle("Data Structures");
        book1.setISBN("987654321");
        book1.setBarcode("B002");
        book1.setNumberOfPages(500);
        
        Book book2 = factory.createBook();
        book2.setTitle("Algorithms");
        book2.setISBN("123123123");
        book2.setBarcode("B003");
        book2.setNumberOfPages(600);
        
        // Create CheckOut records for User U002
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-09-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-25 00:00:00"));
        checkout1.setBook(book1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-10-20 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-25 00:00:00"));
        checkout2.setBook(book1);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-09-30 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-05 00:00:00"));
        checkout3.setBook(book2);
        
        // Add checkouts to user
        user.getCheckouts().add(checkout1);
        user.getCheckouts().add(checkout2);
        user.getCheckouts().add(checkout3);
        
        // Add user and books to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        
        // Calculate average number of pages
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (500 + 600) / 2 = 550.0 pages
        assertEquals(550.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase3_NoBooksBorrowed() throws Exception {
        // Create user with ID: U003, Name: "Charlie", Email: "charlie@example.com", Address: "789 Oak St."
        User user = factory.createUser();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Oak St.");
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Calculate average number of pages for user with no borrowed books
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = 0 / 0 = 0.0 pages
        assertEquals(0.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase4_AveragePagesCalculationForBooksWithDifferentPageCounts() throws Exception {
        // Create user with ID: U004, Name: "Daisy", Email: "daisy@example.com", Address: "101 Maple St."
        User user = factory.createUser();
        user.setID("U004");
        user.setName("Daisy");
        user.setEmail("daisy@example.com");
        user.setAddress("101 Maple St.");
        
        // Create books with different page counts
        Book book1 = factory.createBook();
        book1.setTitle("Web Development");
        book1.setISBN("321321321");
        book1.setBarcode("B004");
        book1.setNumberOfPages(250);
        
        Book book2 = factory.createBook();
        book2.setTitle("Machine Learning");
        book2.setISBN("456456456");
        book2.setBarcode("B005");
        book2.setNumberOfPages(350);
        
        Book book3 = factory.createBook();
        book3.setTitle("Database Systems");
        book3.setISBN("654654654");
        book3.setBarcode("B006");
        book3.setNumberOfPages(450);
        
        // Create CheckOut records for User U004
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-09-15 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-09-22 00:00:00"));
        checkout1.setBook(book1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-09-25 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-10-02 00:00:00"));
        checkout2.setBook(book2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-10-03 00:00:00"));
        checkout3.setEndDate(dateFormat.parse("2023-10-10 00:00:00"));
        checkout3.setBook(book3);
        
        // Add checkouts to user
        user.getCheckouts().add(checkout1);
        user.getCheckouts().add(checkout2);
        user.getCheckouts().add(checkout3);
        
        // Add user and books to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        librarySystem.getBooks().add(book3);
        
        // Calculate average number of pages
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (250 + 350 + 450) / 3 = 350.0 pages
        assertEquals(350.0, averagePages, 0.001);
    }
    
    @Test
    public void testCase5_AveragePagesCalculationForGuestUser() throws Exception {
        // Create a guest user with ID: U005, Name: "Eve", Email: "eve@example.com", Address: "202 Birch St."
        User user = factory.createUser();
        user.setID("U005");
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setAddress("202 Birch St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book1 = factory.createBook();
        book1.setTitle("Networking");
        book1.setISBN("111111111");
        book1.setBarcode("B007");
        book1.setNumberOfPages(400);
        
        Book book2 = factory.createBook();
        book2.setTitle("Cybersecurity");
        book2.setISBN("222222222");
        book2.setBarcode("B008");
        book2.setNumberOfPages(350);
        
        // Create CheckOut records for User U005
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-08-20 00:00:00"));
        checkout1.setEndDate(dateFormat.parse("2023-08-30 00:00:00"));
        checkout1.setBook(book1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-09-01 00:00:00"));
        checkout2.setEndDate(dateFormat.parse("2023-09-10 00:00:00"));
        checkout2.setBook(book2);
        
        // Add checkouts to user
        user.getCheckouts().add(checkout1);
        user.getCheckouts().add(checkout2);
        
        // Add user and books to library system
        librarySystem.getUsers().add(user);
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        
        // Calculate average number of pages
        double averagePages = librarySystem.averageNumberOfPagesUniqueBooksByUser(user);
        
        // Expected Output: Average number of pages = (400 + 350) / 2 = 375.0 pages
        assertEquals(375.0, averagePages, 0.001);
    }
}