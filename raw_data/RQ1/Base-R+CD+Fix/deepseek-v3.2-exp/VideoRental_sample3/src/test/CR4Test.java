import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup customer C001
        customer.setId("C001");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: returned early, no overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        
        // Create Rental 2: returned early, no overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental date "2025-01-01" and current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Expected: Rental 1: 2 days base fee + 0 overdue = 2, Rental 2: 11 days base fee + 0 overdue = 11, Total = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup customer C002
        customer.setId("C002");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        
        // Add rental to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental date "2025-01-01" and current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Expected: 11 days base fee + 3.50 overdue = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup customer C003
        customer.setId("C003");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental dates and current date
        double totalPrice = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Expected: (8 + 8) base fees + (2.00 + 1.50) overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup customer C004
        customer.setId("C004");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        
        // Create Rental 2: on-time return
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental dates and current date
        double totalPrice = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Expected: (6 + 4) base fees + 1.00 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup customer C006
        customer.setId("C006");
        String currentDate = "2025-01-10";
        
        // Create Rental 1: unreturned, 5 days overdue at current date
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Unreturned
        
        // Add rental to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental date "2025-01-01" and current date "2025-01-10"
        double totalPrice = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Expected: 9 days base fee + 2.50 overdue = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}