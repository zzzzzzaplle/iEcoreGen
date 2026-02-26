import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_tapeIsAvailable() {
        // Test Case 1: "Tape is available"
        // Input: tape_id="T001", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T001" 2. No active rentals for T001
        // Expected Output: True
        
        // Create tape T001 and add to system
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Verify tape availability - should be true since no active rentals
        boolean result = rentalSystem.checkTapeAvailability("T001");
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_tapeIsRentedOut() {
        // Test Case 2: "Tape is rented out"
        // Input: tape_id="T002", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T002". Create Customer C001.
        //        2. C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T002 and add to system
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setTitle("Test Tape 2");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Create customer C001
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        // Create rental for T002 with return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setTapeId("T002");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Unreturned
        
        // Add rental to system and customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        customer.setRentals(rentals);
        
        // Verify tape availability - should be false since it's currently rented
        boolean result = rentalSystem.checkTapeAvailability("T002");
        assertFalse("Tape T002 should not be available when rented with null return date", result);
    }
    
    @Test
    public void testCase3_tapeWasRentedButReturned() {
        // Test Case 3: "Tape was rented but returned"
        // Input: tape_id="T003", current_date="2025-01-01"
        // Setup: 1. Create Tape with id="T003". Create Customer C002.
        //        2. C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        // Expected Output: True
        
        // Create tape T003 and add to system
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setTitle("Test Tape 3");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Create customer C002
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        // Create rental for T003 with return_date set (returned)
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate(LocalDate.of(2024, 12, 25));
        rental.setDueDate(LocalDate.of(2024, 12, 30));
        rental.setReturnDate(LocalDate.of(2024, 12, 31)); // Returned
        
        // Add rental to system and customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        customer.setRentals(rentals);
        
        // Verify tape availability - should be true since it was returned
        boolean result = rentalSystem.checkTapeAvailability("T003");
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_tapeExistsButHasOverdueRental() {
        // Test Case 4: "Tape exists but has overdue rental"
        // Input: tape_id="T004", current_date="2025-01-10"
        // Setup: 1. Create Tape with id="T004". Create Customer C003.
        //        2. C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        // Expected Output: False
        
        // Create tape T004 and add to system
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setTitle("Test Tape 4");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Create customer C003
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create rental for T004 with return_date=null (unreturned) and overdue
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null); // Unreturned and overdue
        
        // Add rental to system and customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        customer.setRentals(rentals);
        
        // Verify tape availability - should be false since it's overdue and unreturned
        boolean result = rentalSystem.checkTapeAvailability("T004");
        assertFalse("Tape T004 should not be available when overdue and unreturned", result);
    }
    
    @Test
    public void testCase5_tapeWasRentedButReturnedByMultipleCustomers() {
        // Test Case 5: "Tape was rented but returned by multiple customers"
        // Input: tape_id="T005", current_date="2025-01-10"
        // Setup: 
        // 1. Create Tape with id="T005". Create Customer C004, C005.
        // 2. C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // 3. C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        // Expected Output: The first creation: True. The second creation: False.
        
        // Create tape T005 and add to system
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setTitle("Test Tape 5");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Create customers C004 and C005
        Customer customer1 = new Customer();
        customer1.setAccountId("C004");
        Customer customer2 = new Customer();
        customer2.setAccountId("C005");
        
        // Create first rental for C004 (returned)
        Rental rental1 = new Rental();
        rental1.setTapeId("T005");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned same day
        
        // Create second rental for C005 (unreturned)
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate(LocalDate.of(2025, 1, 6));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null); // Unreturned
        
        // Add both rentals to system
        List<Rental> systemRentals = new ArrayList<>();
        systemRentals.add(rental1);
        systemRentals.add(rental2);
        rentalSystem.setRentals(systemRentals);
        
        // Assign rentals to respective customers
        List<Rental> customer1Rentals = new ArrayList<>();
        customer1Rentals.add(rental1);
        customer1.setRentals(customer1Rentals);
        
        List<Rental> customer2Rentals = new ArrayList<>();
        customer2Rentals.add(rental2);
        customer2.setRentals(customer2Rentals);
        
        // Verify tape availability - should be false due to second rental being active
        boolean result = rentalSystem.checkTapeAvailability("T005");
        assertFalse("Tape T005 should not be available when second rental is active with null return date", result);
    }
}