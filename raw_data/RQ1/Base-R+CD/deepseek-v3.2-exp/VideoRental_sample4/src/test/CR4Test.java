import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction rentalTransaction;
    
    @Before
    public void setUp() {
        customer = new Customer();
        rentalTransaction = new RentalTransaction();
        rentalTransaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer.setId("C001");
        Date currentDate = new Date("2025-01-20 00:00:00");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(new Date("2025-01-03 00:00:00"));
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(new Date("2025-01-15 00:00:00"));
        rental2.setReturnDate(new Date("2025-01-12 00:00:00"));
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalTransaction.getRentals().add(rental1);
        rentalTransaction.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalTransaction.calculateTotalPrice(new Date("2025-01-01 00:00:00"), currentDate);
        
        // Verify
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer.setId("C002");
        Date currentDate = new Date("2025-01-20 00:00:00");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(new Date("2025-01-12 00:00:00"));
        
        // Add rental to customer and transaction
        customer.getRentals().add(rental1);
        rentalTransaction.getRentals().add(rental1);
        
        // Execute
        double totalPrice = rentalTransaction.calculateTotalPrice(new Date("2025-01-01 00:00:00"), currentDate);
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer.setId("C003");
        Date currentDate = new Date("2025-01-20 00:00:00");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(new Date("2025-01-09 00:00:00"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(new Date("2025-01-15 00:00:00"));
        rental2.setReturnDate(new Date("2025-01-18 00:00:00"));
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalTransaction.getRentals().add(rental1);
        rentalTransaction.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalTransaction.calculateTotalPrice(new Date("2025-01-01 00:00:00"), currentDate);
        
        // Verify
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer.setId("C004");
        Date currentDate = new Date("2025-01-20 00:00:00");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(new Date("2025-01-07 00:00:00"));
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(new Date("2025-01-15 00:00:00"));
        rental2.setReturnDate(new Date("2025-01-14 00:00:00"));
        
        // Add rentals to customer and transaction
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalTransaction.getRentals().add(rental1);
        rentalTransaction.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalTransaction.calculateTotalPrice(new Date("2025-01-01 00:00:00"), currentDate);
        
        // Verify
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer.setId("C006");
        Date currentDate = new Date("2025-01-10 00:00:00");
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(new Date("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Unreturned
        
        // Add rental to customer and transaction
        customer.getRentals().add(rental1);
        rentalTransaction.getRentals().add(rental1);
        
        // Execute
        double totalPrice = rentalTransaction.calculateTotalPrice(new Date("2025-01-01 00:00:00"), currentDate);
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}