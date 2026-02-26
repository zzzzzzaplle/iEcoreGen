import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        // Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        Member user = new Member();
        user.setId("U001");
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAddress("123 Main St.");
        
        // Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        
        // Record CheckOuts
        // CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(user);
        loan1.setStartDate(LocalDate.parse("2023-01-10", formatter));
        loan1.setEndDate(LocalDate.parse("2023-01-15", formatter));
        
        // CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(user);
        loan2.setStartDate(LocalDate.parse("2023-03-05", formatter));
        loan2.setEndDate(LocalDate.parse("2023-03-10", formatter));
        
        // CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
        Loan loan3 = new Loan();
        loan3.setBook(book1);
        loan3.setUser(user);
        loan3.setStartDate(LocalDate.parse("2023-05-20", formatter));
        loan3.setEndDate(LocalDate.parse("2023-05-25", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute the method to count unique book borrowings for user U001 in the defined period
        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        Guest user = new Guest();
        user.setId("U002");
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setAddress("456 Oak St.");
        
        // Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        
        // Record CheckOuts
        // CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        Loan loan1 = new Loan();
        loan1.setBook(book3);
        loan1.setUser(user);
        loan1.setStartDate(LocalDate.parse("2023-06-10", formatter));
        loan1.setEndDate(LocalDate.parse("2023-06-15", formatter));
        
        // CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
        Loan loan2 = new Loan();
        loan2.setBook(book4);
        loan2.setUser(user);
        loan2.setStartDate(LocalDate.parse("2023-06-20", formatter));
        loan2.setEndDate(LocalDate.parse("2023-06-25", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Execute the method to count unique book borrowings for user U002 in the defined period
        LocalDate startDate = LocalDate.parse("2023-06-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-06-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Total unique books borrowed should be 2", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        Member user = new Member();
        user.setId("U003");
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setAddress("789 Pine St.");
        
        // Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        LocalDate startDate = LocalDate.parse("2023-07-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-07-31", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Total unique books borrowed should be 0", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        Member user = new Member();
        user.setId("U004");
        user.setName("Diana");
        user.setEmail("diana@example.com");
        user.setAddress("101 Maple St.");
        
        // Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setBarcode("B005");
        book5.setTitle("Book 5");
        
        // Record CheckOuts
        // CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        Loan loan1 = new Loan();
        loan1.setBook(book5);
        loan1.setUser(user);
        loan1.setStartDate(LocalDate.parse("2023-08-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-08-10", formatter));
        
        // CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
        Loan loan2 = new Loan();
        loan2.setBook(book5);
        loan2.setUser(user);
        loan2.setStartDate(LocalDate.parse("2023-08-15", formatter));
        loan2.setEndDate(LocalDate.parse("2023-08-25", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        
        // Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        LocalDate startDate = LocalDate.parse("2023-08-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-08-10", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(user, startDate, endDate);
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Total unique books borrowed should be 1", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        Member user1 = new Member();
        user1.setId("U005");
        user1.setName("Eve");
        user1.setEmail("eve@example.com");
        user1.setAddress("202 Birch St.");
        
        // Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        Guest user2 = new Guest();
        user2.setId("U006");
        user2.setName("Frank");
        user2.setEmail("frank@example.com");
        user2.setAddress("303 Cedar St.");
        
        // Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setBarcode("B006");
        book6.setTitle("Book 6");
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setBarcode("B007");
        book7.setTitle("Book 7");
        
        // Record CheckOuts
        // CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        Loan loan1 = new Loan();
        loan1.setBook(book6);
        loan1.setUser(user1);
        loan1.setStartDate(LocalDate.parse("2023-09-01", formatter));
        loan1.setEndDate(LocalDate.parse("2023-09-10", formatter));
        
        // CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        Loan loan2 = new Loan();
        loan2.setBook(book7);
        loan2.setUser(user1);
        loan2.setStartDate(LocalDate.parse("2023-09-15", formatter));
        loan2.setEndDate(LocalDate.parse("2023-09-20", formatter));
        
        // CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
        Loan loan3 = new Loan();
        loan3.setBook(book6);
        loan3.setUser(user2);
        loan3.setStartDate(LocalDate.parse("2023-09-05", formatter));
        loan3.setEndDate(LocalDate.parse("2023-09-15", formatter));
        
        // Add loans to library system
        librarySystem.getLoans().add(loan1);
        librarySystem.getLoans().add(loan2);
        librarySystem.getLoans().add(loan3);
        
        // Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        LocalDate startDate = LocalDate.parse("2023-09-01", formatter);
        LocalDate endDate = LocalDate.parse("2023-09-30", formatter);
        int result = librarySystem.countUniqueBooksBorrowedByUserInPeriod(user1, startDate, endDate);
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Total unique books borrowed by U005 should be 2", 2, result);
    }
}