import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalTransaction rentalTransaction;
    
    @Before
    public void setUp() {
        rentalTransaction = new RentalTransaction();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Create customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create and set up Rental 1: returned early, no overdue fee
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setId("T001");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03"); // returned early
        
        // Create and set up Rental 2: returned early, no overdue fee
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setId("T002");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12"); // returned early
        
        // Add rentals to customer
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Set customer for rental transaction
        rentalTransaction.setCustomer(customer);
        rentalTransaction.addRental(rental1);
        rentalTransaction.addRental(rental2);
        
        // Calculate total price
        double totalPrice = rentalTransaction.calculateTotalPrice();
        
        // Verify result: Rental 1: 2 days × $1 = $2, Rental 2: 11 days × $1 = $11, Total = $13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Create customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create and set up Rental 1: 7 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setId("T003");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12"); // 7 days overdue
        
        // Add rental to customer
        customer.addRental(rental1);
        
        // Set customer for rental transaction
        rentalTransaction.setCustomer(customer);
        rentalTransaction.addRental(rental1);
        
        // Calculate total price
        double totalPrice = rentalTransaction.calculateTotalPrice();
        
        // Verify result: Base fee: 11 days × $1 = $11, Overdue: 7 days × $0.5 = $3.50, Total = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create and set up Rental 1: 4 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setId("T004");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09"); // 4 days overdue
        
        // Create and set up Rental 2: 3 days overdue
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setId("T005");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18"); // 3 days overdue
        
        // Add rentals to customer
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Set customer for rental transaction
        rentalTransaction.setCustomer(customer);
        rentalTransaction.addRental(rental1);
        rentalTransaction.addRental(rental2);
        
        // Calculate total price
        double totalPrice = rentalTransaction.calculateTotalPrice();
        
        // Verify result: Base fees: 8+8 = $16, Overdue: 2.00+1.50 = $3.50, Total = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Create customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create and set up Rental 1: 2 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setId("T006");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07"); // 2 days overdue
        
        // Create and set up Rental 2: on-time
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setId("T007");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14"); // on-time
        
        // Add rentals to customer
        customer.addRental(rental1);
        customer.addRental(rental2);
        
        // Set customer for rental transaction
        rentalTransaction.setCustomer(customer);
        rentalTransaction.addRental(rental1);
        rentalTransaction.addRental(rental2);
        
        // Calculate total price
        double totalPrice = rentalTransaction.calculateTotalPrice();
        
        // Verify result: Base fees: 6+4 = $10, Overdue: $1.00, Total = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Create customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create and set up Rental 1: unreturned, 5 days overdue as of current date
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setId("T008");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // unreturned
        
        // Add rental to customer
        customer.addRental(rental1);
        
        // Set customer for rental transaction
        rentalTransaction.setCustomer(customer);
        rentalTransaction.addRental(rental1);
        
        // Calculate total price (uses current date 2025-01-10 for overdue calculation)
        double totalPrice = rentalTransaction.calculateTotalPrice();
        
        // Verify result: Base fee: 9 days × $1 = $9, Overdue: 5 days × $0.5 = $2.50, Total = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}