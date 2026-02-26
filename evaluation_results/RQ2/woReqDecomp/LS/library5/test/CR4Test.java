package edu.library.library5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.library.Book;
import edu.library.CheckOut;
import edu.library.LibraryFactory;
import edu.library.LibrarySystem;
import edu.library.User;
import edu.library.UserType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize factory and library system using Ecore factory pattern
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() throws Exception {
        // Create Member users
        User userM001 = factory.createUser();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        userM001.setType(UserType.MEMBER);
        
        User userM002 = factory.createUser();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        userM002.setType(UserType.MEMBER);
        
        // Create books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        
        Book bookB002 = factory.createBook();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        
        Book bookB003 = factory.createBook();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        
        // Add books to library system
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB002);
        librarySystem.getBooks().add(bookB003);
        
        // Add users to library system
        librarySystem.getUsers().add(userM001);
        librarySystem.getUsers().add(userM002);
        
        // Create checkouts for user M001 (3 different books)
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        checkout1.setBook(bookB001);
        userM001.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-10"));
        checkout2.setBook(bookB002);
        userM001.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-03-15"));
        checkout3.setEndDate(dateFormat.parse("2023-03-30"));
        checkout3.setBook(bookB003);
        userM001.getCheckouts().add(checkout3);
        
        // Create checkouts for user M002 (2 same book B001)
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setStartDate(dateFormat.parse("2023-04-01"));
        checkout4.setEndDate(dateFormat.parse("2023-04-12"));
        checkout4.setBook(bookB001);
        userM002.getCheckouts().add(checkout4);
        
        CheckOut checkout5 = factory.createCheckOut();
        checkout5.setStartDate(dateFormat.parse("2023-04-15"));
        checkout5.setEndDate(dateFormat.parse("2023-04-20"));
        checkout5.setBook(bookB001);
        userM002.getCheckouts().add(checkout5);
        
        // Calculate total unique books for each user
        int totalM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int totalM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        // Verify expected results
        assertEquals("User M001 should have 3 unique books", 3, totalM001);
        assertEquals("User M002 should have 1 unique book (same book checked out twice)", 1, totalM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() throws Exception {
        // Create Guest user
        User userG001 = factory.createUser();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        userG001.setType(UserType.GUEST);
        
        // Create book
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        
        // Add book and user to library system
        librarySystem.getBooks().add(bookB001);
        librarySystem.getUsers().add(userG001);
        
        // Create checkout for user G001
        CheckOut checkout = factory.createCheckOut();
        checkout.setStartDate(dateFormat.parse("2023-05-01"));
        checkout.setEndDate(dateFormat.parse("2023-05-10"));
        checkout.setBook(bookB001);
        userG001.getCheckouts().add(checkout);
        
        // Calculate total unique books for guest user
        int totalG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        
        // Verify expected result
        assertEquals("Guest user G001 should have 1 unique book", 1, totalG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        User userM003 = factory.createUser();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        
        // Add user to library system (no books or checkouts needed)
        librarySystem.getUsers().add(userM003);
        
        // Calculate total unique books for user with no checkouts
        int totalM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        
        // Verify expected result
        assertEquals("User M003 with no checkouts should have 0 unique books", 0, totalM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes1() throws Exception {
        // Create Member user
        User userM004 = factory.createUser();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        
        // Create 4 different books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        
        Book bookB002 = factory.createBook();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        
        Book bookB003 = factory.createBook();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        
        Book bookB004 = factory.createBook();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        
        // Add books and user to library system
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB002);
        librarySystem.getBooks().add(bookB003);
        librarySystem.getBooks().add(bookB004);
        librarySystem.getUsers().add(userM004);
        
        // Create checkouts for 4 different books with user M004
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-06-01"));
        checkout1.setEndDate(dateFormat.parse("2023-07-01"));
        checkout1.setBook(bookB001);
        userM004.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-07-01"));
        checkout2.setBook(bookB002);
        userM004.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-06-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-01"));
        checkout3.setBook(bookB003);
        userM004.getCheckouts().add(checkout3);
        
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setStartDate(dateFormat.parse("2023-06-01"));
        checkout4.setEndDate(dateFormat.parse("2023-07-01"));
        checkout4.setBook(bookB004);
        userM004.getCheckouts().add(checkout4);
        
        // Calculate total unique books for user M004
        int totalM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        
        // Verify expected result
        assertEquals("User M004 should have 4 unique books", 4, totalM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes2() throws Exception {
        // Create Guest user
        User userG002 = factory.createUser();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        
        // Create books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        
        Book bookB004 = factory.createBook();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        
        // Add books and user to library system
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB004);
        librarySystem.getUsers().add(userG002);
        
        // Create checkouts for user G002 - 2 same book B001
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-07-15"));
        checkout1.setEndDate(dateFormat.parse("2023-08-01"));
        checkout1.setBook(bookB001);
        userG002.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-01"));
        checkout2.setBook(bookB001);
        userG002.getCheckouts().add(checkout2);
        
        // Create checkouts for user G002 - B004 3 times
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2024-06-01"));
        checkout3.setEndDate(dateFormat.parse("2024-07-01"));
        checkout3.setBook(bookB004);
        userG002.getCheckouts().add(checkout3);
        
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setStartDate(dateFormat.parse("2024-07-02"));
        checkout4.setEndDate(dateFormat.parse("2024-07-11"));
        checkout4.setBook(bookB004);
        userG002.getCheckouts().add(checkout4);
        
        CheckOut checkout5 = factory.createCheckOut();
        checkout5.setStartDate(dateFormat.parse("2024-08-01"));
        checkout5.setEndDate(dateFormat.parse("2024-09-01"));
        checkout5.setBook(bookB004);
        userG002.getCheckouts().add(checkout5);
        
        // Calculate total unique books for user G002
        int totalG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        
        // Verify expected result - should be 2 unique books (B001 and B004)
        assertEquals("Guest user G002 should have 2 unique books", 2, totalG002);
    }
}