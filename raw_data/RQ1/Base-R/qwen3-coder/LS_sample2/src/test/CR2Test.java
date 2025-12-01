import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR2Test {
    private LibrarySystem librarySystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp
        Member member = new Member();
        member.setId("U001");
        member.setName("Alice");
        member.setEmail("alice@example.com");
        member.setAddress("123 Main St.");
        
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setBarcode("B001");
        book1.setTitle("Book1");
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setBarcode("B002");
        book2.setTitle("Book2");
        
        Loan checkout1 = new Loan();
        checkout1.setUser(member);
        checkout1.setBook(book1);
        checkout1.setStartDate(LocalDate.parse("2023-01-10", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-01-15", formatter));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(member);
        checkout2.setBook(book2);
        checkout2.setStartDate(LocalDate.parse("2023-03-05", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-03-10", formatter));
        
        Loan checkout3 = new Loan();
        checkout3.setUser(member);
        checkout3.setBook(book1);
        checkout3.setStartDate(LocalDate.parse("2023-05-20", formatter));
        checkout3.setEndDate(LocalDate.parse("2023-05-25", formatter));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        librarySystem.getLoans().add(checkout3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(member, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member should have borrowed 2 unique books during the period", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp
        Guest guest = new Guest();
        guest.setId("U002");
        guest.setName("Bob");
        guest.setEmail("bob@example.com");
        guest.setAddress("456 Oak St.");
        
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setBarcode("B003");
        book3.setTitle("Book3");
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setBarcode("B004");
        book4.setTitle("Book4");
        
        Loan checkout1 = new Loan();
        checkout1.setUser(guest);
        checkout1.setBook(book3);
        checkout1.setStartDate(LocalDate.parse("2023-06-10", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-06-15", formatter));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(guest);
        checkout2.setBook(book4);
        checkout2.setStartDate(LocalDate.parse("2023-06-20", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-06-25", formatter));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-06-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(guest, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest should have borrowed 2 unique books during the period", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp
        Member member = new Member();
        member.setId("U003");
        member.setName("Charlie");
        member.setEmail("charlie@example.com");
        member.setAddress("789 Pine St.");
        
        // No checkouts recorded for this user
        
        // Execute the method to count unique book borrowings for user U003 for the period
        LocalDate startDate = LocalDate.parse("2023-07-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(member, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Member should have 0 unique books borrowed during the period", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp
        Member member = new Member();
        member.setId("U004");
        member.setName("Diana");
        member.setEmail("diana@example.com");
        member.setAddress("101 Maple St.");
        
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setBarcode("B005");
        book5.setTitle("Book5");
        
        Loan checkout1 = new Loan();
        checkout1.setUser(member);
        checkout1.setBook(book5);
        checkout1.setStartDate(LocalDate.parse("2023-08-01", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-08-10", formatter));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(member);
        checkout2.setBook(book5);
        checkout2.setStartDate(LocalDate.parse("2023-08-15", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-08-25", formatter));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        LocalDate startDate = LocalDate.parse("2023-08-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-08-10", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(member, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Member should have 1 unique book borrowed during the specified period", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp
        Member member = new Member();
        member.setId("U005");
        member.setName("Eve");
        member.setEmail("eve@example.com");
        member.setAddress("202 Birch St.");
        
        Guest guest = new Guest();
        guest.setId("U006");
        guest.setName("Frank");
        guest.setEmail("frank@example.com");
        guest.setAddress("303 Cedar St.");
        
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setBarcode("B006");
        book6.setTitle("Book6");
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setBarcode("B007");
        book7.setTitle("Book7");
        
        Loan checkout1 = new Loan();
        checkout1.setUser(member);
        checkout1.setBook(book6);
        checkout1.setStartDate(LocalDate.parse("2023-09-01", formatter));
        checkout1.setEndDate(LocalDate.parse("2023-09-10", formatter));
        
        Loan checkout2 = new Loan();
        checkout2.setUser(member);
        checkout2.setBook(book7);
        checkout2.setStartDate(LocalDate.parse("2023-09-15", formatter));
        checkout2.setEndDate(LocalDate.parse("2023-09-20", formatter));
        
        Loan checkout3 = new Loan();
        checkout3.setUser(guest);
        checkout3.setBook(book6);
        checkout3.setStartDate(LocalDate.parse("2023-09-05", formatter));
        checkout3.setEndDate(LocalDate.parse("2023-09-15", formatter));
        
        librarySystem.getLoans().add(checkout1);
        librarySystem.getLoans().add(checkout2);
        librarySystem.getLoans().add(checkout3);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        LocalDate startDate = LocalDate.parse("2023-09-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-09-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(member, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Member U005 should have 2 unique books borrowed during the period", 2, result);
    }
}