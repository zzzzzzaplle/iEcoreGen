import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem system;
    private VideoTape tapeT001;
    private VideoTape tapeT002;
    private VideoTape tapeT003;
    private VideoTape tapeT004;
    private VideoTape tapeT005;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        
        // Create all test tapes
        tapeT001 = new VideoTape();
        tapeT001.setBarCodeId("T001");
        
        tapeT002 = new VideoTape();
        tapeT002.setBarCodeId("T002");
        
        tapeT003 = new VideoTape();
        tapeT003.setBarCodeId("T003");
        
        tapeT004 = new VideoTape();
        tapeT004.setBarCodeId("T004");
        
        tapeT005 = new VideoTape();
        tapeT005.setBarCodeId("T005");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        system.getVideoTapes().add(tapeT001);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = system.checkTapeAvailability(tapeT001, "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        system.getVideoTapes().add(tapeT002);
        
        Customer customerC001 = new Customer();
        customerC001.setIdCard("C001");
        system.getCustomers().add(customerC001);
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setVideoTape(tapeT002);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        transaction.getRentals().add(rental);
        customerC001.getRentalTransactions().add(transaction);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = system.checkTapeAvailability(tapeT002, "2025-01-01");
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        system.getVideoTapes().add(tapeT003);
        
        Customer customerC002 = new Customer();
        customerC002.setIdCard("C002");
        system.getCustomers().add(customerC002);
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setVideoTape(tapeT003);
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31");
        transaction.getRentals().add(rental);
        customerC002.getRentalTransactions().add(transaction);
        
        // Test tape availability for current_date="2025-01-01"
        boolean result = system.checkTapeAvailability(tapeT003, "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when previously rented but already returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        system.getVideoTapes().add(tapeT004);
        
        Customer customerC003 = new Customer();
        customerC003.setIdCard("C003");
        system.getCustomers().add(customerC003);
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setVideoTape(tapeT004);
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null);
        transaction.getRentals().add(rental);
        customerC003.getRentalTransactions().add(transaction);
        
        // Test tape availability for current_date="2025-01-10"
        boolean result = system.checkTapeAvailability(tapeT004, "2025-01-10");
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when it has an overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        system.getVideoTapes().add(tapeT005);
        
        Customer customerC004 = new Customer();
        customerC004.setIdCard("C004");
        system.getCustomers().add(customerC004);
        
        Customer customerC005 = new Customer();
        customerC005.setIdCard("C005");
        system.getCustomers().add(customerC005);
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        RentalTransaction transaction1 = new RentalTransaction();
        Rental rental1 = new Rental();
        rental1.setVideoTape(tapeT005);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        transaction1.getRentals().add(rental1);
        customerC004.getRentalTransactions().add(transaction1);
        
        // Test availability after first rental (should be True since tape was returned)
        boolean resultAfterFirstRental = system.checkTapeAvailability(tapeT005, "2025-01-10");
        assertTrue("Tape T005 should be available after first customer returned it", resultAfterFirstRental);
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        RentalTransaction transaction2 = new RentalTransaction();
        Rental rental2 = new Rental();
        rental2.setVideoTape(tapeT005);
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        transaction2.getRentals().add(rental2);
        customerC005.getRentalTransactions().add(transaction2);
        
        // Test availability after second rental (should be False since tape is currently rented)
        boolean resultAfterSecondRental = system.checkTapeAvailability(tapeT005, "2025-01-10");
        assertFalse("Tape T005 should be unavailable when second customer has not returned it", resultAfterSecondRental);
    }
}