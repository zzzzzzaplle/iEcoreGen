package edu.library.library4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.library.Book;
import edu.library.CheckOut;
import edu.library.LibraryFactory;
import edu.library.LibrarySystem;
import edu.library.User;
import edu.library.UserType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
    }
    
    /**
     * Test Case 1: Counting Checkouts for a Book with Multiple Checkouts
     * Input: Count the number of times the book with title "Java Programming" has been checked out.
     */
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // SetUp
        // 1. Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = factory.createBook();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        librarySystem.getBooks().add(book);
        
        // 2. Checkout this book by Member M001 (start date: 2023-01-01, end date: 2023-01-15).
        User member1 = createUser("M001", "Member One", UserType.MEMBER);
        CheckOut checkout1 = createCheckout(book, member1, "2023-01-01", "2023-01-15");
        
        // 3. Checkout this book by Member M001 (start date: 2023-02-01, end date: 2023-02-15).
        CheckOut checkout2 = createCheckout(book, member1, "2023-02-01", "2023-02-15");
        
        // 4. Checkout this book by Member M001 (start date: 2023-03-01, end date: 2023-03-15).
        CheckOut checkout3 = createCheckout(book, member1, "2023-03-01", "2023-03-15");
        
        librarySystem.getUsers().add(member1);
        
        // Execute and verify
        int result = librarySystem.countBookCheckOuts(book);
        assertEquals("The book has been checked out 3 times.", 3, result);
    }
    
    /**
     * Test Case 2: Counting Checkouts for a Book with No Checkouts
     * Input: Count the number of times the book with title "Python Basics" has been checked out.
     */
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() throws ParseException {
        // SetUp
        // 1. Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = factory.createBook();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        librarySystem.getBooks().add(book);
        
        // 2. No User check out this Book.
        
        // Execute and verify
        int result = librarySystem.countBookCheckOuts(book);
        assertEquals("The book has been checked out 0 time.", 0, result);
    }
    
    /**
     * Test Case 3: Counting Checkouts for a Book with One Checkout
     * Input: Count the number of times the book with title "Algorithms" has been checked out.
     */
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() throws ParseException {
        // SetUp
        // 1. Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = factory.createBook();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        librarySystem.getBooks().add(book);
        
        // 2. Checkout this book by Member M001 (start date: 2023-04-01, end date: 2023-04-15).
        User member1 = createUser("M001", "Member One", UserType.MEMBER);
        CheckOut checkout = createCheckout(book, member1, "2023-04-01", "2023-04-15");
        
        librarySystem.getUsers().add(member1);
        
        // Execute and verify
        int result = librarySystem.countBookCheckOuts(book);
        assertEquals("The book has been checked out 1 time.", 1, result);
    }
    
    /**
     * Test Case 4: Counting Checkouts for a Book with Mix of Members and Guests
     * Input: Count the number of times the book with title "Data Structures" has been checked out.
     */
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // SetUp
        // 1. Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = factory.createBook();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        librarySystem.getBooks().add(book);
        
        // 2. Checkout this book by Member M001 (start date: 2023-05-01, end date: 2023-05-15).
        User member1 = createUser("M001", "Member One", UserType.MEMBER);
        CheckOut checkout1 = createCheckout(book, member1, "2023-05-01", "2023-05-15");
        
        // 3. Checkout this book by Guest G001 (start date: 2023-06-01, end date: 2023-06-15).
        User guest1 = createUser("G001", "Guest One", UserType.GUEST);
        CheckOut checkout2 = createCheckout(book, guest1, "2023-06-01", "2023-06-15");
        
        // 4. Checkout this book by Member M002 (start date: 2023-07-01, end date: 2023-07-15).
        User member2 = createUser("M002", "Member Two", UserType.MEMBER);
        CheckOut checkout3 = createCheckout(book, member2, "2023-07-01", "2023-07-15");
        
        librarySystem.getUsers().add(member1);
        librarySystem.getUsers().add(guest1);
        librarySystem.getUsers().add(member2);
        
        // Execute and verify
        int result = librarySystem.countBookCheckOuts(book);
        assertEquals("The book has been checked out 3 times.", 3, result);
    }
    
    /**
     * Test Case 5: Counting Checkouts for a Book with Same User
     * Input: Count the number of times the book with title "Web Development" has been checked out.
     */
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() throws ParseException {
        // SetUp
        // 1. Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = factory.createBook();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        librarySystem.getBooks().add(book);
        
        // 2. Checkout this book by Member M001 (start date: 2023-08-01, end date: 2023-08-15).
        User member1 = createUser("M001", "Member One", UserType.MEMBER);
        CheckOut checkout1 = createCheckout(book, member1, "2023-08-01", "2023-08-15");
        
        // 3. Checkout this book by Member M001 (start date: 2023-08-16, end date: 2023-08-30).
        CheckOut checkout2 = createCheckout(book, member1, "2023-08-16", "2023-08-30");
        
        librarySystem.getUsers().add(member1);
        
        // Execute and verify
        int result = librarySystem.countBookCheckOuts(book);
        assertEquals("The book has been checked out 2 times.", 2, result);
    }
    
    // Helper methods
    private User createUser(String id, String name, UserType type) {
        User user = factory.createUser();
        user.setID(id);
        user.setName(name);
        user.setType(type);
        user.setEmail(name.toLowerCase().replace(" ", ".") + "@library.com");
        user.setAddress("123 Library St");
        return user;
    }
    
    private CheckOut createCheckout(Book book, User user, String startDateStr, String endDateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        CheckOut checkout = factory.createCheckOut();
        checkout.setBook(book);
        checkout.setStartDate(sdf.parse(startDateStr));
        checkout.setEndDate(sdf.parse(endDateStr));
        
        user.getCheckouts().add(checkout);
        return checkout;
    }
}