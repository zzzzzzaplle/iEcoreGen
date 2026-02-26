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

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private LibraryFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize the library system and factory using Ecore factory pattern
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CountingCheckoutsForBookWithMultipleCheckouts() throws Exception {
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500
        Book book = factory.createBook();
        book.setTitle("Java Programming");
        book.setBarcode("123456");
        book.setISBN("978-3-16-148410-0");
        book.setNumberOfPages(500);
        librarySystem.getBooks().add(book);
        
        // Create a Member user
        User member = factory.createUser();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.getUsers().add(member);
        
        // Checkout this book by Member M001 three times with different date ranges
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-01-01"));
        checkout1.setEndDate(dateFormat.parse("2023-01-15"));
        member.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-02-01"));
        checkout2.setEndDate(dateFormat.parse("2023-02-15"));
        member.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-03-01"));
        checkout3.setEndDate(dateFormat.parse("2023-03-15"));
        member.getCheckouts().add(checkout3);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book with multiple checkouts should return count of 3", 3, result);
    }
    
    @Test
    public void testCase2_CountingCheckoutsForBookWithNoCheckouts() {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400
        Book book = factory.createBook();
        book.setTitle("Python Basics");
        book.setBarcode("654321");
        book.setISBN("978-1-23-456789-0");
        book.setNumberOfPages(400);
        librarySystem.getBooks().add(book);
        
        // No User check out this Book
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time
        assertEquals("Book with no checkouts should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_CountingCheckoutsForBookWithOneCheckout() throws Exception {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700
        Book book = factory.createBook();
        book.setTitle("Algorithms");
        book.setBarcode("789012");
        book.setISBN("978-0-12-345678-9");
        book.setNumberOfPages(700);
        librarySystem.getBooks().add(book);
        
        // Create a Member user
        User member = factory.createUser();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.getUsers().add(member);
        
        // Checkout this book by Member M001 once
        CheckOut checkout = factory.createCheckOut();
        checkout.setBook(book);
        checkout.setStartDate(dateFormat.parse("2023-04-01"));
        checkout.setEndDate(dateFormat.parse("2023-04-15"));
        member.getCheckouts().add(checkout);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time
        assertEquals("Book with one checkout should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_CountingCheckoutsForBookWithMixOfMembersAndGuests() throws Exception {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600
        Book book = factory.createBook();
        book.setTitle("Data Structures");
        book.setBarcode("101112");
        book.setISBN("978-9-87-654321-0");
        book.setNumberOfPages(600);
        librarySystem.getBooks().add(book);
        
        // Create Member M001
        User member1 = factory.createUser();
        member1.setID("M001");
        member1.setType(UserType.MEMBER);
        librarySystem.getUsers().add(member1);
        
        // Create Guest G001
        User guest = factory.createUser();
        guest.setID("G001");
        guest.setType(UserType.GUEST);
        librarySystem.getUsers().add(guest);
        
        // Create Member M002
        User member2 = factory.createUser();
        member2.setID("M002");
        member2.setType(UserType.MEMBER);
        librarySystem.getUsers().add(member2);
        
        // Checkout this book by Member M001
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-05-01"));
        checkout1.setEndDate(dateFormat.parse("2023-05-15"));
        member1.getCheckouts().add(checkout1);
        
        // Checkout this book by Guest G001
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-06-01"));
        checkout2.setEndDate(dateFormat.parse("2023-06-15"));
        guest.getCheckouts().add(checkout2);
        
        // Checkout this book by Member M002
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setBook(book);
        checkout3.setStartDate(dateFormat.parse("2023-07-01"));
        checkout3.setEndDate(dateFormat.parse("2023-07-15"));
        member2.getCheckouts().add(checkout3);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times
        assertEquals("Book with mixed member and guest checkouts should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_CountingCheckoutsForBookWithSameUser() throws Exception {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450
        Book book = factory.createBook();
        book.setTitle("Web Development");
        book.setBarcode("131415");
        book.setISBN("978-2-36-547891-0");
        book.setNumberOfPages(450);
        librarySystem.getBooks().add(book);
        
        // Create a Member user
        User member = factory.createUser();
        member.setID("M001");
        member.setType(UserType.MEMBER);
        librarySystem.getUsers().add(member);
        
        // Checkout this book by Member M001 twice with different date ranges
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(book);
        checkout1.setStartDate(dateFormat.parse("2023-08-01"));
        checkout1.setEndDate(dateFormat.parse("2023-08-15"));
        member.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(book);
        checkout2.setStartDate(dateFormat.parse("2023-08-16"));
        checkout2.setEndDate(dateFormat.parse("2023-08-30"));
        member.getCheckouts().add(checkout2);
        
        // Count the number of times the book has been checked out
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times
        assertEquals("Book with same user checking out multiple times should return count of 2", 2, result);
    }
}