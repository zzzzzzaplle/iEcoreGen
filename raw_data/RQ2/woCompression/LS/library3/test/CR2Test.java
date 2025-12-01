package edu.library.library3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.library.Book;
import edu.library.CheckOut;
import edu.library.LibraryFactory;
import edu.library.LibraryPackage;
import edu.library.LibrarySystem;
import edu.library.User;
import edu.library.UserType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the factory and library system
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() throws ParseException {
        // Create a MEMBER user
        User user = factory.createUser();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        
        // Create books
        Book book1 = factory.createBook();
        book1.setISBN("978-3-16-148410-0");
        
        Book book2 = factory.createBook();
        book2.setISBN("978-1-4028-9467-7");
        
        // Create checkouts
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-01-10"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        checkout1.setBook(book1);
        user.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-03-05"));
        checkout2.setEndDate(dateFormat.parse("2023-03-10"));
        checkout2.setBook(book2);
        user.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-05-20"));
        checkout3.setEndDate(dateFormat.parse("2023-05-25"));
        checkout3.setBook(book1);
        user.getCheckouts().add(checkout3);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-12-31");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() throws ParseException {
        // Create a GUEST user
        User user = factory.createUser();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        
        // Create books
        Book book3 = factory.createBook();
        book3.setISBN("978-0-1234-5678-9");
        
        Book book4 = factory.createBook();
        book4.setISBN("978-1-2345-6789-7");
        
        // Create checkouts
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-06-10"));
        checkout1.setEndDate(dateFormat.parse("2023-06-15"));
        checkout1.setBook(book3);
        user.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-06-20"));
        checkout2.setEndDate(dateFormat.parse("2023-06-25"));
        checkout2.setBook(book4);
        user.getCheckouts().add(checkout2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method
        Date startDate = dateFormat.parse("2023-06-01");
        Date endDate = dateFormat.parse("2023-06-30");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify result
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with no checkouts
        User user = factory.createUser();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method
        Date startDate = dateFormat.parse("2023-07-01");
        Date endDate = dateFormat.parse("2023-07-31");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify result
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user
        User user = factory.createUser();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        
        // Create a book
        Book book5 = factory.createBook();
        book5.setISBN("978-3-16-148410-1");
        
        // Create overlapping checkouts for the same book
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-10"));
        checkout1.setBook(book5);
        user.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-08-15"));
        checkout2.setEndDate(dateFormat.parse("2023-08-25"));
        checkout2.setBook(book5);
        user.getCheckouts().add(checkout2);
        
        // Add user to library system
        librarySystem.getUsers().add(user);
        
        // Execute method for first period only
        Date startDate = dateFormat.parse("2023-08-01");
        Date endDate = dateFormat.parse("2023-08-10");
        int result = librarySystem.uniqueBooksBorrowedByUser(user, startDate, endDate);
        
        // Verify result - should count as 1 unique book even with multiple checkouts
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create first user (MEMBER)
        User user1 = factory.createUser();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        
        // Create second user (GUEST)
        User user2 = factory.createUser();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        
        // Create books
        Book book6 = factory.createBook();
        book6.setISBN("978-0-321-56789-1");
        
        Book book7 = factory.createBook();
        book7.setISBN("978-0-12-345678-9");
        
        // Create checkouts for first user
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setStartDate(dateFormat.parse("2023-09-01"));
        checkout1.setEndDate(dateFormat.parse("2023-09-10"));
        checkout1.setBook(book6);
        user1.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setStartDate(dateFormat.parse("2023-09-15"));
        checkout2.setEndDate(dateFormat.parse("2023-09-20"));
        checkout2.setBook(book7);
        user1.getCheckouts().add(checkout2);
        
        // Create checkout for second user (shares book6)
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setStartDate(dateFormat.parse("2023-09-05"));
        checkout3.setEndDate(dateFormat.parse("2023-09-15"));
        checkout3.setBook(book6);
        user2.getCheckouts().add(checkout3);
        
        // Add users to library system
        librarySystem.getUsers().add(user1);
        librarySystem.getUsers().add(user2);
        
        // Execute method for first user
        Date startDate = dateFormat.parse("2023-09-01");
        Date endDate = dateFormat.parse("2023-09-30");
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, startDate, endDate);
        
        // Verify result - user1 should have 2 unique books
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}