import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private LibrarySystem librarySystem;
    
    @Before
    public void setUp() {
        librarySystem = new LibrarySystem();
    }
    
    @Test
    public void testCase1_CalculateTotalCheckoutsForMemberUser() {
        // Create Member users
        MemberUser alice = new MemberUser("Alice", "alice@example.com", "123 Main St", "M001");
        MemberUser bob = new MemberUser("Bob", "bob@example.com", "456 Elm St", "M002");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "ISBN001", 200);
        Book book2 = new Book("Book2", "B002", "ISBN002", 300);
        Book book3 = new Book("Book3", "B003", "ISBN003", 400);
        
        // Add users and books to library system
        librarySystem.addUser(alice);
        librarySystem.addUser(bob);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        
        // Check out 3 different books for Alice
        librarySystem.checkoutBook(alice, book1, "2023-01-01", "2023-01-15");
        librarySystem.checkoutBook(alice, book2, "2023-02-01", "2023-02-10");
        librarySystem.checkoutBook(alice, book3, "2023-03-15", "2023-03-30");
        
        // Check out 2 same book (B001) for Bob
        librarySystem.checkoutBook(bob, book1, "2023-04-01", "2023-04-12");
        librarySystem.checkoutBook(bob, book1, "2023-04-15", "2023-04-20");
        
        // Calculate total unique books for each user
        int aliceTotal = alice.countTotalUniqueBooks();
        int bobTotal = bob.countTotalUniqueBooks();
        
        // Verify expected results
        assertEquals("Alice should have 3 unique books checked out", 3, aliceTotal);
        assertEquals("Bob should have 1 unique book checked out", 1, bobTotal);
    }
    
    @Test
    public void testCase2_CalculateTotalCheckoutsForGuestUser() {
        // Create Guest user
        GuestUser charlie = new GuestUser("Charlie", "charlie@example.com", "789 Pine St", "G001");
        
        // Create book
        Book book1 = new Book("Book1", "B001", "ISBN001", 200);
        
        // Add user and book to library system
        librarySystem.addUser(charlie);
        librarySystem.addBook(book1);
        
        // Check out 1 book for Charlie
        librarySystem.checkoutBook(charlie, book1, "2023-05-01", "2023-05-10");
        
        // Calculate total unique books for guest user
        int charlieTotal = charlie.countTotalUniqueBooks();
        
        // Verify expected result
        assertEquals("Charlie should have 1 unique book checked out", 1, charlieTotal);
    }
    
    @Test
    public void testCase3_MixedUserTypesWithNoCheckouts() {
        // Create Member user with no checkouts
        MemberUser eve = new MemberUser("Eve", "eve@example.com", "654 Maple St", "M003");
        
        // Add user to library system
        librarySystem.addUser(eve);
        
        // Calculate total unique books for user with no checkouts
        int eveTotal = eve.countTotalUniqueBooks();
        
        // Verify expected result
        assertEquals("Eve should have 0 unique books checked out", 0, eveTotal);
    }
    
    @Test
    public void testCase4_CalculateTotalCheckoutsForMultipleUsersWithMixedTypesMember() {
        // Create Member user
        MemberUser george = new MemberUser("George", "george@example.com", "135 Cedar St", "M004");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "ISBN001", 200);
        Book book2 = new Book("Book2", "B002", "ISBN002", 300);
        Book book3 = new Book("Book3", "B003", "ISBN003", 400);
        Book book4 = new Book("Book4", "B004", "ISBN004", 500);
        
        // Add user and books to library system
        librarySystem.addUser(george);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);
        librarySystem.addBook(book3);
        librarySystem.addBook(book4);
        
        // Check out 4 different books for George
        librarySystem.checkoutBook(george, book1, "2023-06-01", "2023-07-01");
        librarySystem.checkoutBook(george, book2, "2023-06-01", "2023-07-01");
        librarySystem.checkoutBook(george, book3, "2023-06-01", "2023-07-01");
        librarySystem.checkoutBook(george, book4, "2023-06-01", "2023-07-01");
        
        // Calculate total unique books for George
        int georgeTotal = george.countTotalUniqueBooks();
        
        // Verify expected result
        assertEquals("George should have 4 unique books checked out", 4, georgeTotal);
    }
    
    @Test
    public void testCase5_CalculateTotalCheckoutsForMultipleUsersWithMixedTypesGuest() {
        // Create Guest user
        GuestUser hannah = new GuestUser("Hannah", "hannah@example.com", "246 Spruce St", "G002");
        
        // Create books
        Book book1 = new Book("Book1", "B001", "ISBN001", 200);
        Book book4 = new Book("Book4", "B004", "ISBN004", 500);
        
        // Add user and books to library system
        librarySystem.addUser(hannah);
        librarySystem.addBook(book1);
        librarySystem.addBook(book4);
        
        // Check out 2 same book (B001) for Hannah
        librarySystem.checkoutBook(hannah, book1, "2023-07-15", "2023-08-01");
        librarySystem.checkoutBook(hannah, book1, "2023-08-15", "2023-09-01");
        
        // Check out B004 3 times for Hannah
        librarySystem.checkoutBook(hannah, book4, "2024-06-01", "2024-07-01");
        librarySystem.checkoutBook(hannah, book4, "2024-07-02", "2024-07-11");
        librarySystem.checkoutBook(hannah, book4, "2024-08-01", "2024-09-01");
        
        // Calculate total unique books for Hannah
        int hannahTotal = hannah.countTotalUniqueBooks();
        
        // Verify expected result
        assertEquals("Hannah should have 2 unique books checked out", 2, hannahTotal);
    }
}