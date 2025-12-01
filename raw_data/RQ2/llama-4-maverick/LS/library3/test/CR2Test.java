package edu.library.library3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.library.LibraryFactory;
import edu.library.LibraryPackage;
import edu.library.LibrarySystem;
import edu.library.User;
import edu.library.Book;
import edu.library.CheckOut;
import edu.library.UserType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        // Initialize the factory and library system
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
    }
    
    @Test
    public void testCase1_singleUserBorrowingCalculationForMember() throws ParseException {
        // Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        User user = factory.createUser();
        user.setID("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        user.setType(UserType.MEMBER);
        librarySystem.getUsers().add(user);
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = factory.createBook();
        book1.setISBN("978-3-16-148410-0");
        book1.setTitle("Book1");
        
        Book book2 = factory.createBook();
        book2.setISBN("978-1-4028-9467-7");
        book2.setTitle("Book2");
        
        librarySystem.getBooks().add(book1);
        librarySystem.getBooks().add(book2);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Record CheckOuts
        // CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        CheckOut checkOut1 = factory.createCheckOut();
        checkOut1.setBook(book1);
        checkOut1.setStartDate(sdf.parse("2023-01-10"));
        checkOut1.setEndDate(sdf.parse("2023-01-15"));
        user.getCheckouts().add(checkOut1);
        
        // CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        CheckOut checkOut2 = factory.createCheckOut();
        checkOut2.setBook(book2);
        checkOut2.setStartDate(sdf.parse("2023-03-05"));
        checkOut2.setEndDate(sdf.parse("2023-03-10"));
        user.getCheckouts().add(checkOut2);
        
        // CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
        CheckOut checkOut3 = factory.createCheckOut();
        checkOut3.setBook(book1);
        checkOut3.setStartDate(sdf.parse("2023-05-20"));
        checkOut3.setEndDate(sdf.parse("2023-05-25"));
        user.getCheckouts().add(checkOut3);
        
        // Define the period from 2023-01-01 to 2023-12-31
        Date start = sdf.parse("2023-01-01");
        Date end = sdf.parse("2023-12-31");
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_singleUserBorrowingCalculationForGuest() throws ParseException {
        // Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        User user = factory.createUser();
        user.setID("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        user.setType(UserType.GUEST);
        librarySystem.getUsers().add(user);
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = factory.createBook();
        book3.setISBN("978-0-1234-5678-9");
        book3.setTitle("Book3");
        
        Book book4 = factory.createBook();
        book4.setISBN("978-1-2345-6789-7");
        book4.setTitle("Book4");
        
        librarySystem.getBooks().add(book3);
        librarySystem.getBooks().add(book4);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Record CheckOuts
        // CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        CheckOut checkOut1 = factory.createCheckOut();
        checkOut1.setBook(book3);
        checkOut1.setStartDate(sdf.parse("2023-06-10"));
        checkOut1.setEndDate(sdf.parse("2023-06-15"));
        user.getCheckouts().add(checkOut1);
        
        // CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
        CheckOut checkOut2 = factory.createCheckOut();
        checkOut2.setBook(book4);
        checkOut2.setStartDate(sdf.parse("2023-06-20"));
        checkOut2.setEndDate(sdf.parse("2023-06-25"));
        user.getCheckouts().add(checkOut2);
        
        // Define the period from 2023-06-01 to 2023-06-30
        Date start = sdf.parse("2023-06-01");
        Date end = sdf.parse("2023-06-30");
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_noBorrowingActivityWithinDateRange() throws ParseException {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        User user = factory.createUser();
        user.setID("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        user.setType(UserType.MEMBER);
        librarySystem.getUsers().add(user);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Define the period from 2023-07-01 to 2023-07-31
        Date start = sdf.parse("2023-07-01");
        Date end = sdf.parse("2023-07-31");
        
        // Execute the method to count unique book borrowings for user U003 for the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_overlappingCheckoutPeriod() throws ParseException {
        // Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        User user = factory.createUser();
        user.setID("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        user.setType(UserType.MEMBER);
        librarySystem.getUsers().add(user);
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = factory.createBook();
        book5.setISBN("978-3-16-148410-1");
        book5.setTitle("Book5");
        librarySystem.getBooks().add(book5);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Record CheckOuts
        // CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        CheckOut checkOut1 = factory.createCheckOut();
        checkOut1.setBook(book5);
        checkOut1.setStartDate(sdf.parse("2023-08-01"));
        checkOut1.setEndDate(sdf.parse("2023-08-10"));
        user.getCheckouts().add(checkOut1);
        
        // CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
        CheckOut checkOut2 = factory.createCheckOut();
        checkOut2.setBook(book5);
        checkOut2.setStartDate(sdf.parse("2023-08-15"));
        checkOut2.setEndDate(sdf.parse("2023-08-25"));
        user.getCheckouts().add(checkOut2);
        
        // Define the period from 2023-08-01 to 2023-08-10
        Date start = sdf.parse("2023-08-01");
        Date end = sdf.parse("2023-08-10");
        
        // Execute the method to count unique book borrowings for user U004 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user, start, end);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_multipleUsersWithDifferentBorrowingActivities() throws ParseException {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        User user1 = factory.createUser();
        user1.setID("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        user1.setType(UserType.MEMBER);
        librarySystem.getUsers().add(user1);
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        User user2 = factory.createUser();
        user2.setID("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        user2.setType(UserType.GUEST);
        librarySystem.getUsers().add(user2);
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = factory.createBook();
        book6.setISBN("978-0-321-56789-1");
        book6.setTitle("Book6");
        
        Book book7 = factory.createBook();
        book7.setISBN("978-0-12-345678-9");
        book7.setTitle("Book7");
        
        librarySystem.getBooks().add(book6);
        librarySystem.getBooks().add(book7);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Record CheckOuts
        // CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        CheckOut checkOut1 = factory.createCheckOut();
        checkOut1.setBook(book6);
        checkOut1.setStartDate(sdf.parse("2023-09-01"));
        checkOut1.setEndDate(sdf.parse("2023-09-10"));
        user1.getCheckouts().add(checkOut1);
        
        // CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        CheckOut checkOut2 = factory.createCheckOut();
        checkOut2.setBook(book7);
        checkOut2.setStartDate(sdf.parse("2023-09-15"));
        checkOut2.setEndDate(sdf.parse("2023-09-20"));
        user1.getCheckouts().add(checkOut2);
        
        // CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
        CheckOut checkOut3 = factory.createCheckOut();
        checkOut3.setBook(book6);
        checkOut3.setStartDate(sdf.parse("2023-09-05"));
        checkOut3.setEndDate(sdf.parse("2023-09-15"));
        user2.getCheckouts().add(checkOut3);
        
        // Define the period from 2023-09-01 to 2023-09-30
        Date start = sdf.parse("2023-09-01");
        Date end = sdf.parse("2023-09-30");
        
        // Execute the method to count unique book borrowings for user U005 in the defined period
        int result = librarySystem.uniqueBooksBorrowedByUser(user1, start, end);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}