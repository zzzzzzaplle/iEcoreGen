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
        // Setup: Customer C001 with two rentals returned early (no overdue fees)
        customer.setId("C001");
        
        // Create Rental 1: Returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        
        // Create Rental 2: Returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20"
        double result = transaction.calculateTotalPrice("2025-01-01", "2025-01-20");
        
        // Expected: Rental 1: 2 days base fee ($2) + 0 overdue = $2
        // Rental 2: 11 days base fee ($11) + 0 overdue = $11
        // Total = $13.00
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Customer C002 with one overdue rental
        customer.setId("C002");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20"
        double result = transaction.calculateTotalPrice("2025-01-01", "2025-01-20");
        
        // Expected: 11 days base fee ($11) + 7 days overdue ($3.50) = $14.50
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Customer C003 with two overdue rentals
        customer.setId("C003");
        
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
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20"
        double result = transaction.calculateTotalPrice("2025-01-01", "2025-01-20");
        
        // Expected: Rental 1: 8 days base ($8) + 4 days overdue ($2.00) = $10.00
        // Rental 2: 8 days base ($8) + 3 days overdue ($1.50) = $9.50
        // Total = $19.50
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Customer C004 with one overdue and one on-time rental
        customer.setId("C004");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        
        // Create Rental 2: On-time (returned before due date)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20"
        double result = transaction.calculateTotalPrice("2025-01-01", "2025-01-20");
        
        // Expected: Rental 1: 6 days base ($6) + 2 days overdue ($1.00) = $7.00
        // Rental 2: 4 days base ($4) + 0 overdue = $4.00
        // Total = $11.00
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Customer C006 with unreturned tape that is overdue
        customer.setId("C006");
        
        // Create Rental 1: Unreturned, 5 days overdue based on current date
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Unreturned
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-10"
        double result = transaction.calculateTotalPrice("2025-01-01", "2025-01-10");
        
        // Expected: 9 days base fee ($9) + 5 days overdue ($2.50) = $11.50
        assertEquals(11.50, result, 0.001);
    }
}