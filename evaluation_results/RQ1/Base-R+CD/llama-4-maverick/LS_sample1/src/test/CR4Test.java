import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_calculateTotalCheckoutsForMemberUser() throws ParseException {
        // Create Member user M001
        User member1 = new User("Alice", "alice@example.com", "123 Main St", "M001", UserType.MEMBER);
        
        // Create Member user M002
        User member2 = new User("Bob", "bob@example.com", "456 Elm St", "M002", UserType.MEMBER);
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN1", 100);
        Book book2 = new Book("Book 2", "B002", "ISBN2", 200);
        Book book3 = new Book("Book 3", "B003", "ISBN3", 300);
        
        // Add books to library
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        librarySystem.setBooks(books);
        
        // Add users to library
        List<User> users = new ArrayList<>();
        users.add(member1);
        users.add(member2);
        librarySystem.setUsers(users);
        
        // Check out 3 different books with user M001
        Date startDate1 = dateFormat.parse("2023-01-01");
        Date endDate1 = dateFormat.parse("2023-01-15");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book1);
        
        Date startDate2 = dateFormat.parse("2023-02-01");
        Date endDate2 = dateFormat.parse("2023-02-10");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book2);
        
        Date startDate3 = dateFormat.parse("2023-03-15");
        Date endDate3 = dateFormat.parse("2023-03-30");
        CheckOut checkout3 = new CheckOut(startDate3, endDate3, book3);
        
        member1.getCheckOuts().add(checkout1);
        member1.getCheckOuts().add(checkout2);
        member1.getCheckOuts().add(checkout3);
        
        // Check out 2 same book (B001) with user M002
        Date startDate4 = dateFormat.parse("2023-04-01");
        Date endDate4 = dateFormat.parse("2023-04-12");
        CheckOut checkout4 = new CheckOut(startDate4, endDate4, book1);
        
        Date startDate5 = dateFormat.parse("2023-04-15");
        Date endDate5 = dateFormat.parse("2023-04-20");
        CheckOut checkout5 = new CheckOut(startDate5, endDate5, book1);
        
        member2.getCheckOuts().add(checkout4);
        member2.getCheckOuts().add(checkout5);
        
        // Calculate total unique books checked out
        int result1 = librarySystem.totalUniqueBooksCheckedOutByUser(member1);
        int result2 = librarySystem.totalUniqueBooksCheckedOutByUser(member2);
        
        // Assert results
        assertEquals(3, result1);
        assertEquals(1, result2);
    }
    
    @Test
    public void testCase2_calculateTotalCheckoutsForGuestUser() throws ParseException {
        // Create Guest user G001
        User guest1 = new User("Charlie", "charlie@example.com", "789 Pine St", "G001", UserType.GUEST);
        
        // Create book
        Book book1 = new Book("Book 1", "B001", "ISBN1", 100);
        
        // Add book to library
        List<Book> books = new ArrayList<>();
        books.add(book1);
        librarySystem.setBooks(books);
        
        // Add user to library
        List<User> users = new ArrayList<>();
        users.add(guest1);
        librarySystem.setUsers(users);
        
        // Check out 1 book with user G001
        Date startDate = dateFormat.parse("2023-05-01");
        Date endDate = dateFormat.parse("2023-05-10");
        CheckOut checkout = new CheckOut(startDate, endDate, book1);
        
        guest1.getCheckOuts().add(checkout);
        
        // Calculate total unique books checked out
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest1);
        
        // Assert result
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_mixedUserTypesWithNoCheckouts() {
        // Create Member user M003
        User member3 = new User("Eve", "eve@example.com", "654 Maple St", "M003", UserType.MEMBER);
        
        // Add user to library
        List<User> users = new ArrayList<>();
        users.add(member3);
        librarySystem.setUsers(users);
        
        // No checkouts for user M003
        
        // Calculate total unique books checked out
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member3);
        
        // Assert result
        assertEquals(0, result);
    }
    
    @Test
    public void testCase4_calculateTotalCheckoutsForMultipleUsersWithMixedTypesMember() throws ParseException {
        // Create Member user M004
        User member4 = new User("George", "george@example.com", "135 Cedar St", "M004", UserType.MEMBER);
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN1", 100);
        Book book2 = new Book("Book 2", "B002", "ISBN2", 200);
        Book book3 = new Book("Book 3", "B003", "ISBN3", 300);
        Book book4 = new Book("Book 4", "B004", "ISBN4", 400);
        
        // Add books to library
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        librarySystem.setBooks(books);
        
        // Add user to library
        List<User> users = new ArrayList<>();
        users.add(member4);
        librarySystem.setUsers(users);
        
        // Check out 4 different books with user M004
        Date startDate1 = dateFormat.parse("2023-06-01");
        Date endDate1 = dateFormat.parse("2023-07-01");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book1);
        
        Date startDate2 = dateFormat.parse("2023-06-01");
        Date endDate2 = dateFormat.parse("2023-07-01");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book2);
        
        Date startDate3 = dateFormat.parse("2023-06-01");
        Date endDate3 = dateFormat.parse("2023-07-01");
        CheckOut checkout3 = new CheckOut(startDate3, endDate3, book3);
        
        Date startDate4 = dateFormat.parse("2023-06-01");
        Date endDate4 = dateFormat.parse("2023-07-01");
        CheckOut checkout4 = new CheckOut(startDate4, endDate4, book4);
        
        member4.getCheckOuts().add(checkout1);
        member4.getCheckOuts().add(checkout2);
        member4.getCheckOuts().add(checkout3);
        member4.getCheckOuts().add(checkout4);
        
        // Calculate total unique books checked out
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(member4);
        
        // Assert result
        assertEquals(4, result);
    }
    
    @Test
    public void testCase5_calculateTotalCheckoutsForMultipleUsersWithMixedTypesGuest() throws ParseException {
        // Create Guest user G002
        User guest2 = new User("Hannah", "hannah@example.com", "246 Spruce St", "G002", UserType.GUEST);
        
        // Create books
        Book book1 = new Book("Book 1", "B001", "ISBN1", 100);
        Book book4 = new Book("Book 4", "B004", "ISBN4", 400);
        
        // Add books to library
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book4);
        librarySystem.setBooks(books);
        
        // Add user to library
        List<User> users = new ArrayList<>();
        users.add(guest2);
        librarySystem.setUsers(users);
        
        // Check out 2 same book (B001) with user G002
        Date startDate1 = dateFormat.parse("2023-07-15");
        Date endDate1 = dateFormat.parse("2023-08-01");
        CheckOut checkout1 = new CheckOut(startDate1, endDate1, book1);
        
        Date startDate2 = dateFormat.parse("2023-08-15");
        Date endDate2 = dateFormat.parse("2023-09-01");
        CheckOut checkout2 = new CheckOut(startDate2, endDate2, book1);
        
        // Check out B004 3 times with user G002
        Date startDate3 = dateFormat.parse("2024-06-01");
        Date endDate3 = dateFormat.parse("2024-07-01");
        CheckOut checkout3 = new CheckOut(startDate3, endDate3, book4);
        
        Date startDate4 = dateFormat.parse("2024-07-02");
        Date endDate4 = dateFormat.parse("2024-07-11");
        CheckOut checkout4 = new CheckOut(startDate4, endDate4, book4);
        
        Date startDate5 = dateFormat.parse("2024-08-01");
        Date endDate5 = dateFormat.parse("2024-09-01");
        CheckOut checkout5 = new CheckOut(startDate5, endDate5, book4);
        
        guest2.getCheckOuts().add(checkout1);
        guest2.getCheckOuts().add(checkout2);
        guest2.getCheckOuts().add(checkout3);
        guest2.getCheckOuts().add(checkout4);
        guest2.getCheckOuts().add(checkout5);
        
        // Calculate total unique books checked out
        int result = librarySystem.totalUniqueBooksCheckedOutByUser(guest2);
        
        // Assert result
        assertEquals(2, result);
    }
}