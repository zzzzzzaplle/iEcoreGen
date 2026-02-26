import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Library library;
    
    @Before
    public void setUp() {
        library = new Library();
    }
    
    @Test
    public void testCase1_SingleUserBorrowingCalculationForMember() {
        // SetUp
        // 1. Create a MEMBER user with ID: U001, name: "Alice", email: "alice@example.com", address: "123 Main St."
        MemberUser user = new MemberUser("Alice", "alice@example.com", "123 Main St.", "U001");
        library.addUser(user);
        
        // 2. Create books: Book1 (ISBN: 978-3-16-148410-0), Book2 (ISBN: 978-1-4028-9467-7)
        Book book1 = new Book("Book1", "B001", "978-3-16-148410-0", 200);
        Book book2 = new Book("Book2", "B002", "978-1-4028-9467-7", 300);
        library.addBook(book1);
        library.addBook(book2);
        
        // 3. Record CheckOuts
        // - CheckOut1 for Book1 from 2023-01-10 to 2023-01-15
        Loan loan1 = new Loan(book1, "2023-01-10", "2023-01-15");
        user.addLoan(loan1);
        
        // - CheckOut2 for Book2 from 2023-03-05 to 2023-03-10
        Loan loan2 = new Loan(book2, "2023-03-05", "2023-03-10");
        user.addLoan(loan2);
        
        // - CheckOut3 for Book1 from 2023-05-20 to 2023-05-25
        Loan loan3 = new Loan(book1, "2023-05-20", "2023-05-25");
        user.addLoan(loan3);
        
        // 4. Execute the method to count unique book borrowings for user U001 in the defined period
        int result = library.countUniqueBooksBorrowedByUser("U001", "2023-01-01", "2023-12-31");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase2_SingleUserBorrowingCalculationForGuest() {
        // SetUp
        // 1. Create a GUEST user with ID: U002, name: "Bob", email: "bob@example.com", address: "456 Oak St."
        GuestUser user = new GuestUser("Bob", "bob@example.com", "456 Oak St.", "U002");
        library.addUser(user);
        
        // 2. Create books: Book3 (ISBN: 978-0-1234-5678-9), Book4 (ISBN: 978-1-2345-6789-7)
        Book book3 = new Book("Book3", "B003", "978-0-1234-5678-9", 250);
        Book book4 = new Book("Book4", "B004", "978-1-2345-6789-7", 350);
        library.addBook(book3);
        library.addBook(book4);
        
        // 3. Record CheckOuts
        // - CheckOut1 for Book3 from 2023-06-10 to 2023-06-15
        Loan loan1 = new Loan(book3, "2023-06-10", "2023-06-15");
        user.addLoan(loan1);
        
        // - CheckOut2 for Book4 from 2023-06-20 to 2023-06-25
        Loan loan2 = new Loan(book4, "2023-06-20", "2023-06-25");
        user.addLoan(loan2);
        
        // 4. Execute the method to count unique book borrowings for user U002 in the defined period
        int result = library.countUniqueBooksBorrowedByUser("U002", "2023-06-01", "2023-06-30");
        
        // Expected Output: Total unique books borrowed = 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase3_NoBorrowingActivityWithinDateRange() {
        // SetUp
        // 1. Create a MEMBER user with ID: U003, name: "Charlie", email: "charlie@example.com", address: "789 Pine St."
        MemberUser user = new MemberUser("Charlie", "charlie@example.com", "789 Pine St.", "U003");
        library.addUser(user);
        
        // 2. Execute the method to count unique book borrowings for user U003 for the period from 2023-07-01 to 2023-07-31
        int result = library.countUniqueBooksBorrowedByUser("U003", "2023-07-01", "2023-07-31");
        
        // Expected Output: Total unique books borrowed = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_OverlappingCheckoutPeriod() {
        // SetUp
        // 1. Create a MEMBER user with ID: U004, name: "Diana", email: "diana@example.com", address: "101 Maple St."
        MemberUser user = new MemberUser("Diana", "diana@example.com", "101 Maple St.", "U004");
        library.addUser(user);
        
        // 2. Create a book: Book5 (ISBN: 978-3-16-148410-1)
        Book book5 = new Book("Book5", "B005", "978-3-16-148410-1", 400);
        library.addBook(book5);
        
        // 3. Record CheckOuts
        // - CheckOut1 for Book5 from 2023-08-01 to 2023-08-10
        Loan loan1 = new Loan(book5, "2023-08-01", "2023-08-10");
        user.addLoan(loan1);
        
        // - CheckOut2 for Book5 again from 2023-08-15 to 2023-08-25
        Loan loan2 = new Loan(book5, "2023-08-15", "2023-08-25");
        user.addLoan(loan2);
        
        // 4. Execute the method to count unique book borrowings for user U004 from 2023-08-01 to 2023-08-10
        int result = library.countUniqueBooksBorrowedByUser("U004", "2023-08-01", "2023-08-10");
        
        // Expected Output: Total unique books borrowed = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase5_MultipleUsersWithDifferentBorrowingActivities() {
        // SetUp
        // 1. Create a MEMBER user with ID: U005, name: "Eve", email: "eve@example.com", address: "202 Birch St."
        MemberUser user1 = new MemberUser("Eve", "eve@example.com", "202 Birch St.", "U005");
        library.addUser(user1);
        
        // 2. Create a GUEST user with ID: U006, name: "Frank", email: "frank@example.com", address: "303 Cedar St."
        GuestUser user2 = new GuestUser("Frank", "frank@example.com", "303 Cedar St.", "U006");
        library.addUser(user2);
        
        // 3. Create books: Book6 (ISBN: 978-0-321-56789-1), Book7 (ISBN: 978-0-12-345678-9)
        Book book6 = new Book("Book6", "B006", "978-0-321-56789-1", 275);
        Book book7 = new Book("Book7", "B007", "978-0-12-345678-9", 325);
        library.addBook(book6);
        library.addBook(book7);
        
        // 4. Record CheckOuts
        // - CheckOut1 for Book6 by U005 from 2023-09-01 to 2023-09-10
        Loan loan1 = new Loan(book6, "2023-09-01", "2023-09-10");
        user1.addLoan(loan1);
        
        // - CheckOut2 for Book7 by U005 from 2023-09-15 to 2023-09-20
        Loan loan2 = new Loan(book7, "2023-09-15", "2023-09-20");
        user1.addLoan(loan2);
        
        // - CheckOut3 for Book6 by U006 from 2023-09-05 to 2023-09-15
        Loan loan3 = new Loan(book6, "2023-09-05", "2023-09-15");
        user2.addLoan(loan3);
        
        // 5. Execute the method to count unique book borrowings for user U005 from 2023-09-01 to 2023-09-30
        int result = library.countUniqueBooksBorrowedByUser("U005", "2023-09-01", "2023-09-30");
        
        // Expected Output: Total unique books borrowed by U005 = 2
        assertEquals(2, result);
    }
}