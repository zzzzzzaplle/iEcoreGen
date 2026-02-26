import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // Test Case 1: "Single User Borrowing Calculation for a Member"
        // Input: Calculate the number of books borrowed by a MEMBER user over a specific period from 2023-01-01 to 2023-12-31
        
        // SetUp Step 1: Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        Member member = new Member();
        member.setId("U001");
        member.setName("Alice");
        member.setEmail("alice@example.com");
        member.setAddress("123 Main St.");
        librarySystem.addUser(member);
        
        // SetUp Step 2: Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book();
        book1.setIsbn("978-3-16-148410-0");
        book1.setBarcode("B001");
        book1.setTitle("Book 1");
        
        Book book2 = new Book();
        book2.setIsbn("978-1-4028-9467-7");
        book2.setBarcode("B002");
        book2.setTitle("Book 2");
        
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        
        // SetUp Step 3: Record CheckOuts
        // CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        Loan loan1 = new Loan();
        loan1.setBook(book1);
        loan1.setUser(member);
        loan1.setStartDate("2023-01-10");
        loan1.setEndDate("2023-01-15");
        librarySystem.addLoan(loan1);
        
        // CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setUser(member);
        loan2.setStartDate("2023-03-05");
        loan2.setEndDate("2023-03-10");
        librarySystem.addLoan(loan2);
        
        // CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
        Loan loan3 = new Loan();
        loan3.setBook(book1);
        loan3.setUser(member);
        loan3.setStartDate("2023-05-20");
        loan3.setEndDate("2023-05-25");
        librarySystem.addLoan(loan3);
        
        // SetUp Step 4: Execute the method to count unique book borrowings for user U001 in the defined period
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(member, "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Member should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // Test Case 2: "Single User Borrowing Calculation for a Guest"
        // Input: Calculate the number of books borrowed by a GUEST user over the period from 2023-06-01 to 2023-06-30
        
        // SetUp Step 1: Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        Guest guest = new Guest();
        guest.setId("U002");
        guest.setName("Bob");
        guest.setEmail("bob@example.com");
        guest.setAddress("456 Oak St.");
        librarySystem.addUser(guest);
        
        // SetUp Step 2: Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book();
        book3.setIsbn("978-0-1234-5678-9");
        book3.setBarcode("B003");
        book3.setTitle("Book 3");
        
        Book book4 = new Book();
        book4.setIsbn("978-1-2345-6789-7");
        book4.setBarcode("B004");
        book4.setTitle("Book 4");
        
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // SetUp Step 3: Record CheckOuts
        // CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        Loan loan1 = new Loan();
        loan1.setBook(book3);
        loan1.setUser(guest);
        loan1.setStartDate("2023-06-10");
        loan1.setEndDate("2023-06-15");
        librarySystem.addLoan(loan1);
        
        // CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
        Loan loan2 = new Loan();
        loan2.setBook(book4);
        loan2.setUser(guest);
        loan2.setStartDate("2023-06-20");
        loan2.setEndDate("2023-06-25");
        librarySystem.addLoan(loan2);
        
        // SetUp Step 4: Execute the method to count unique book borrowings for user U002 in the defined period
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(guest, "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals("Guest should have borrowed 2 unique books", 2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // Test Case 3: "No Borrowing Activity within Date Range"
        // Input: Calculate the number of books borrowed by a MEMBER user over a period with no checkouts
        
        // SetUp Step 1: Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        Member member = new Member();
        member.setId("U003");
        member.setName("Charlie");
        member.setEmail("charlie@example.com");
        member.setAddress("789 Pine St.");
        librarySystem.addUser(member);
        
        // SetUp Step 2: Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(member, "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals("Member with no loans should have 0 unique books borrowed", 0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // Test Case 4: "Overlapping Checkout Period"
        // Input: Calculate the number of books borrowed by a MEMBER user with overlapping checkout periods
        
        // SetUp Step 1: Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        Member member = new Member();
        member.setId("U004");
        member.setName("Diana");
        member.setEmail("diana@example.com");
        member.setAddress("101 Maple St.");
        librarySystem.addUser(member);
        
        // SetUp Step 2: Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book();
        book5.setIsbn("978-3-16-148410-1");
        book5.setBarcode("B005");
        book5.setTitle("Book 5");
        librarySystem.addBook(book5);
        
        // SetUp Step 3: Record CheckOuts
        // CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        Loan loan1 = new Loan();
        loan1.setBook(book5);
        loan1.setUser(member);
        loan1.setStartDate("2023-08-01");
        loan1.setEndDate("2023-08-10");
        librarySystem.addLoan(loan1);
        
        // CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
        Loan loan2 = new Loan();
        loan2.setBook(book5);
        loan2.setUser(member);
        loan2.setStartDate("2023-08-15");
        loan2.setEndDate("2023-08-25");
        librarySystem.addLoan(loan2);
        
        // SetUp Step 4: Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(member, "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals("Member should have 1 unique book borrowed in the specified period", 1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // Test Case 5: "Multiple Users with Different Borrowing Activities"
        // Input: Calculate the number of books borrowed by multiple users to check for unique counting
        
        // SetUp Step 1: Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        Member member = new Member();
        member.setId("U005");
        member.setName("Eve");
        member.setEmail("eve@example.com");
        member.setAddress("202 Birch St.");
        librarySystem.addUser(member);
        
        // SetUp Step 2: Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        Guest guest = new Guest();
        guest.setId("U006");
        guest.setName("Frank");
        guest.setEmail("frank@example.com");
        guest.setAddress("303 Cedar St.");
        librarySystem.addUser(guest);
        
        // SetUp Step 3: Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book();
        book6.setIsbn("978-0-321-56789-1");
        book6.setBarcode("B006");
        book6.setTitle("Book 6");
        
        Book book7 = new Book();
        book7.setIsbn("978-0-12-345678-9");
        book7.setBarcode("B007");
        book7.setTitle("Book 7");
        
        librarySystem.addBook(book6);
        librarySystem.addBook(book7);
        
        // SetUp Step 4: Record CheckOuts
        // CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        Loan loan1 = new Loan();
        loan1.setBook(book6);
        loan1.setUser(member);
        loan1.setStartDate("2023-09-01");
        loan1.setEndDate("2023-09-10");
        librarySystem.addLoan(loan1);
        
        // CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        Loan loan2 = new Loan();
        loan2.setBook(book7);
        loan2.setUser(member);
        loan2.setStartDate("2023-09-15");
        loan2.setEndDate("2023-09-20");
        librarySystem.addLoan(loan2);
        
        // CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
        Loan loan3 = new Loan();
        loan3.setBook(book6);
        loan3.setUser(guest);
        loan3.setStartDate("2023-09-05");
        loan3.setEndDate("2023-09-15");
        librarySystem.addLoan(loan3);
        
        // SetUp Step 5: Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = librarySystem.getUniqueBooksBorrowedInPeriod(member, "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals("Member U005 should have borrowed 2 unique books", 2, result);
    }
}