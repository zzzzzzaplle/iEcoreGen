package edu.library.library3.test;

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

public class CR4Test {
    
    private LibraryFactory factory;
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        factory = LibraryFactory.eINSTANCE;
        librarySystem = factory.createLibrarySystem();
    }
    
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member user M001
        User userM001 = factory.createUser();
        userM001.setID("M001");
        userM001.setName("Alice");
        userM001.setEmail("alice@example.com");
        userM001.setAddress("123 Main St");
        userM001.setType(UserType.MEMBER);
        librarySystem.getUsers().add(userM001);
        
        // Create Member user M002
        User userM002 = factory.createUser();
        userM002.setID("M002");
        userM002.setName("Bob");
        userM002.setEmail("bob@example.com");
        userM002.setAddress("456 Elm St");
        userM002.setType(UserType.MEMBER);
        librarySystem.getUsers().add(userM002);
        
        // Create books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setNumberOfPages(100);
        
        Book bookB002 = factory.createBook();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        bookB002.setNumberOfPages(200);
        
        Book bookB003 = factory.createBook();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        bookB003.setNumberOfPages(300);
        
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB002);
        librarySystem.getBooks().add(bookB003);
        
        // Check out 3 different books with user M001
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(parseDate("2023-01-01"));
        checkout1.setEndDate(parseDate("2023-01-15"));
        checkout1.setUser(userM001);
        userM001.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(parseDate("2023-02-01"));
        checkout2.setEndDate(parseDate("2023-02-10"));
        checkout2.setUser(userM001);
        userM001.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(parseDate("2023-03-15"));
        checkout3.setEndDate(parseDate("2023-03-30"));
        checkout3.setUser(userM001);
        userM001.getCheckouts().add(checkout3);
        
        // Check out 2 same book (B001) with user M002
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setBook(bookB001);
        checkout4.setStartDate(parseDate("2023-04-01"));
        checkout4.setEndDate(parseDate("2023-04-12"));
        checkout4.setUser(userM002);
        userM002.getCheckouts().add(checkout4);
        
        CheckOut checkout5 = factory.createCheckOut();
        checkout5.setBook(bookB001);
        checkout5.setStartDate(parseDate("2023-04-15"));
        checkout5.setEndDate(parseDate("2023-04-20"));
        checkout5.setUser(userM002);
        userM002.getCheckouts().add(checkout5);
        
        // Verify results
        int totalCheckoutsM001 = librarySystem.totalUniqueBooksCheckedOutByUser(userM001);
        int totalCheckoutsM002 = librarySystem.totalUniqueBooksCheckedOutByUser(userM002);
        
        assertEquals(3, totalCheckoutsM001);
        assertEquals(1, totalCheckoutsM002);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user G001
        User userG001 = factory.createUser();
        userG001.setID("G001");
        userG001.setName("Charlie");
        userG001.setEmail("charlie@example.com");
        userG001.setAddress("789 Pine St");
        userG001.setType(UserType.GUEST);
        librarySystem.getUsers().add(userG001);
        
        // Create book
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setNumberOfPages(100);
        librarySystem.getBooks().add(bookB001);
        
        // Check out 1 book with user G001
        CheckOut checkout = factory.createCheckOut();
        checkout.setBook(bookB001);
        checkout.setStartDate(parseDate("2023-05-01"));
        checkout.setEndDate(parseDate("2023-05-10"));
        checkout.setUser(userG001);
        userG001.getCheckouts().add(checkout);
        
        // Verify result
        int totalCheckoutsG001 = librarySystem.totalUniqueBooksCheckedOutByUser(userG001);
        assertEquals(1, totalCheckoutsG001);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user M003 with no checkouts
        User userM003 = factory.createUser();
        userM003.setID("M003");
        userM003.setName("Eve");
        userM003.setEmail("eve@example.com");
        userM003.setAddress("654 Maple St");
        userM003.setType(UserType.MEMBER);
        librarySystem.getUsers().add(userM003);
        
        // Verify result
        int totalCheckoutsM003 = librarySystem.totalUniqueBooksCheckedOutByUser(userM003);
        assertEquals(0, totalCheckoutsM003);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Member() {
        // Create Member user M004
        User userM004 = factory.createUser();
        userM004.setID("M004");
        userM004.setName("George");
        userM004.setEmail("george@example.com");
        userM004.setAddress("135 Cedar St");
        userM004.setType(UserType.MEMBER);
        librarySystem.getUsers().add(userM004);
        
        // Create books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setNumberOfPages(100);
        
        Book bookB002 = factory.createBook();
        bookB002.setBarcode("B002");
        bookB002.setTitle("Book 2");
        bookB002.setNumberOfPages(200);
        
        Book bookB003 = factory.createBook();
        bookB003.setBarcode("B003");
        bookB003.setTitle("Book 3");
        bookB003.setNumberOfPages(300);
        
        Book bookB004 = factory.createBook();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        bookB004.setNumberOfPages(400);
        
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB002);
        librarySystem.getBooks().add(bookB003);
        librarySystem.getBooks().add(bookB004);
        
        // Check out 4 different books with user M004
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(parseDate("2023-06-01"));
        checkout1.setEndDate(parseDate("2023-07-01"));
        checkout1.setUser(userM004);
        userM004.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(bookB002);
        checkout2.setStartDate(parseDate("2023-06-01"));
        checkout2.setEndDate(parseDate("2023-07-01"));
        checkout2.setUser(userM004);
        userM004.getCheckouts().add(checkout2);
        
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setBook(bookB003);
        checkout3.setStartDate(parseDate("2023-06-01"));
        checkout3.setEndDate(parseDate("2023-07-01"));
        checkout3.setUser(userM004);
        userM004.getCheckouts().add(checkout3);
        
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(parseDate("2023-06-01"));
        checkout4.setEndDate(parseDate("2023-07-01"));
        checkout4.setUser(userM004);
        userM004.getCheckouts().add(checkout4);
        
        // Verify result
        int totalCheckoutsM004 = librarySystem.totalUniqueBooksCheckedOutByUser(userM004);
        assertEquals(4, totalCheckoutsM004);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypes_Guest() {
        // Create Guest user G002
        User userG002 = factory.createUser();
        userG002.setID("G002");
        userG002.setName("Hannah");
        userG002.setEmail("hannah@example.com");
        userG002.setAddress("246 Spruce St");
        userG002.setType(UserType.GUEST);
        librarySystem.getUsers().add(userG002);
        
        // Create books
        Book bookB001 = factory.createBook();
        bookB001.setBarcode("B001");
        bookB001.setTitle("Book 1");
        bookB001.setNumberOfPages(100);
        
        Book bookB004 = factory.createBook();
        bookB004.setBarcode("B004");
        bookB004.setTitle("Book 4");
        bookB004.setNumberOfPages(400);
        
        librarySystem.getBooks().add(bookB001);
        librarySystem.getBooks().add(bookB004);
        
        // Check out 2 same book (B001) with user G002
        CheckOut checkout1 = factory.createCheckOut();
        checkout1.setBook(bookB001);
        checkout1.setStartDate(parseDate("2023-07-15"));
        checkout1.setEndDate(parseDate("2023-08-01"));
        checkout1.setUser(userG002);
        userG002.getCheckouts().add(checkout1);
        
        CheckOut checkout2 = factory.createCheckOut();
        checkout2.setBook(bookB001);
        checkout2.setStartDate(parseDate("2023-08-15"));
        checkout2.setEndDate(parseDate("2023-09-01"));
        checkout2.setUser(userG002);
        userG002.getCheckouts().add(checkout2);
        
        // Check out B004 3 times with user G002
        CheckOut checkout3 = factory.createCheckOut();
        checkout3.setBook(bookB004);
        checkout3.setStartDate(parseDate("2024-06-01"));
        checkout3.setEndDate(parseDate("2024-07-01"));
        checkout3.setUser(userG002);
        userG002.getCheckouts().add(checkout3);
        
        CheckOut checkout4 = factory.createCheckOut();
        checkout4.setBook(bookB004);
        checkout4.setStartDate(parseDate("2024-07-02"));
        checkout4.setEndDate(parseDate("2024-07-11"));
        checkout4.setUser(userG002);
        userG002.getCheckouts().add(checkout4);
        
        CheckOut checkout5 = factory.createCheckOut();
        checkout5.setBook(bookB004);
        checkout5.setStartDate(parseDate("2024-08-01"));
        checkout5.setEndDate(parseDate("2024-09-01"));
        checkout5.setUser(userG002);
        userG002.getCheckouts().add(checkout5);
        
        // Verify result
        int totalCheckoutsG002 = librarySystem.totalUniqueBooksCheckedOutByUser(userG002);
        assertEquals(2, totalCheckoutsG002);
    }
}