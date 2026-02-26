import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup test data
        customer.setId("C001");
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create and setup Rental 1
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03 00:00:00"));
        
        // Create and setup Rental 2
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice(null, currentDate);
        
        // Verify expected result
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup test data
        customer.setId("C002");
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create and setup Rental 1
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Add rental to transaction
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice(null, currentDate);
        
        // Verify expected result
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup test data
        customer.setId("C003");
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create and setup Rental 1
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09 00:00:00"));
        
        // Create and setup Rental 2
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice(null, currentDate);
        
        // Verify expected result
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup test data
        customer.setId("C004");
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create and setup Rental 1 (overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07 00:00:00"));
        
        // Create and setup Rental 2 (on-time)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14 00:00:00"));
        
        // Add rentals to transaction
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice(null, currentDate);
        
        // Verify expected result
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup test data
        customer.setId("C006");
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Create and setup Rental 1 (unreturned)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to transaction
        transaction.getRentals().add(rental1);
        
        // Calculate total price
        double result = transaction.calculateTotalPrice(null, currentDate);
        
        // Verify expected result
        assertEquals(11.50, result, 0.001);
    }
}