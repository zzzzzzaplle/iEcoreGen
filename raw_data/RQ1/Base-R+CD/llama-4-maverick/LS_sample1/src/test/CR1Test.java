import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_countingCheckoutsForBookWithMultipleCheckouts() throws ParseException {
        // Create a Book object with title: "Java Programming", barcode: "123456", ISBN: "978-3-16-148410-0", pages: 500.
        Book book = new Book("Java Programming", "123456", "978-3-16-148410-0", 500);
        
        // Create Member M001
        User member = new User("Member1", "member1@example.com", "Address1", "M001", UserType.MEMBER);
        
        // Create checkouts
        Date startDate1 = dateFormat.parse("2023-01-01");
        Date endDate1 = dateFormat.parse("2023-01-15");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book);
        
        Date startDate2 = dateFormat.parse("2023-02-01");
        Date endDate2 = dateFormat.parse("2023-02-15");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book);
        
        Date startDate3 = dateFormat.parse("2023-03-01");
        Date endDate3 = dateFormat.parse("2023-03-15");
        CheckOut checkout3 = new CheckOut(startDate3, endDate3, book);
        
        // Add checkouts to member
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkout1);
        checkOuts.add(checkout2);
        checkOuts.add(checkout3);
        member.setCheckOuts(checkOuts);
        
        // Add member to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count checkouts
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_countingCheckoutsForBookWithNoCheckouts() throws ParseException {
        // Create a Book object with title: "Python Basics", barcode: "654321", ISBN: "978-1-23-456789-0", pages: 400.
        Book book = new Book("Python Basics", "654321", "978-1-23-456789-0", 400);
        
        // No User check out this Book.
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Add an empty list of users
        librarySystem.setUsers(new ArrayList<>());
        
        // Count checkouts
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 0 time.
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_countingCheckoutsForBookWithOneCheckout() throws ParseException {
        // Create a Book object with title: "Algorithms", barcode: "789012", ISBN: "978-0-12-345678-9", pages: 700.
        Book book = new Book("Algorithms", "789012", "978-0-12-345678-9", 700);
        
        // Create Member M001
        User member = new User("Member1", "member1@example.com", "Address1", "M001", UserType.MEMBER);
        
        // Create checkout
        Date startDate = dateFormat.parse("2023-04-01");
        Date endDate = dateFormat.parse("2023-04-15");
        CheckOut checkout = new CheckOut(startDate, endDate, book);
        
        // Add checkout to member
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkout);
        member.setCheckOuts(checkOuts);
        
        // Add member to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count checkouts
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 1 time.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_countingCheckoutsForBookWithMixOfMembersAndGuests() throws ParseException {
        // Create a Book object with title: "Data Structures", barcode: "101112", ISBN: "978-9-87-654321-0", pages: 600.
        Book book = new Book("Data Structures", "101112", "978-9-87-654321-0", 600);
        
        // Create Member M001
        User member1 = new User("Member1", "member1@example.com", "Address1", "M001", UserType.MEMBER);
        
        // Create Guest G001
        User guest = new User("Guest1", "guest1@example.com", "Address2", "G001", UserType.GUEST);
        
        // Create Member M002
        User member2 = new User("Member2", "member2@example.com", "Address3", "M002", UserType.MEMBER);
        
        // Create checkouts
        Date startDate1 = dateFormat.parse("2023-05-01");
        Date endDate1 = dateFormat.parse("2023-05-15");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book);
        
        Date startDate2 = dateFormat.parse("2023-06-01");
        Date endDate2 = dateFormat.parse("2023-06-15");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book);
        
        Date startDate3 = dateFormat.parse("2023-07-01");
        Date endDate3 = dateFormat.parse("2023-07-15");
        CheckOut checkout3 = new CheckOut(startDate3, endDate3, book);
        
        // Add checkouts to users
        member1.getCheckOuts().add(checkout1);
        guest.getCheckOuts().add(checkout2);
        member2.getCheckOuts().add(checkout3);
        
        // Add users to library system
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(guest);
        users.add(member2);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count checkouts
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 3 times.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_countingCheckoutsForBookWithSameUser() throws ParseException {
        // Create a Book object with title: "Web Development", barcode: "131415", ISBN: "978-2-36-547891-0", pages: 450.
        Book book = new Book("Web Development", "131415", "978-2-36-547891-0", 450);
        
        // Create Member M001
        User member = new User("Member1", "member1@example.com", "Address1", "M001", UserType.MEMBER);
        
        // Create checkouts
        Date startDate1 = dateFormat.parse("2023-08-01");
        Date endDate1 = dateFormat.parse("2023-08-15");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book);
        
        Date startDate2 = dateFormat.parse("2023-08-16");
        Date endDate2 = dateFormat.parse("2023-08-30");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book);
        
        // Add checkouts to member
        List<CheckOut> checkOuts = new ArrayList<>();
        checkOuts.add(checkout1);
        checkOuts.add(checkout2);
        member.setCheckOuts(checkOuts);
        
        // Add member to library system
        List<User> users = new ArrayList<>();
        users.add(member);
        librarySystem.setUsers(users);
        
        // Add book to library system
        List<Book> books = new ArrayList<>();
        books.add(book);
        librarySystem.setBooks(books);
        
        // Count checkouts
        int result = librarySystem.countBookCheckOuts(book);
        
        // Expected Output: The book has been checked out 2 times.
        assertEquals(2, result);
    }
}