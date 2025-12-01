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
        // Setup
        customer.setId("C001");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Verify expected output
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer.setId("C002");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        
        // Add rental to customer and transaction
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Verify expected output
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
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
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Verify expected output
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer.setId("C004");
        String currentDate = "2025-01-20";
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Verify expected output
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer.setId("C006");
        String currentDate = "2025-01-10";
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        // return_date remains null (unreturned)
        
        // Add rental to customer and transaction
        customer.getRentals().add(rental1);
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice("2025-01-01", currentDate);
        
        // Verify expected output
        assertEquals(11.50, result, 0.001);
    }
}