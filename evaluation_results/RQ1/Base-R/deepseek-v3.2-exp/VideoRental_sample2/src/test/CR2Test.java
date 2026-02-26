import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private VideoRentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Input: tape_id="T001", current_date="2025-01-01"
        String tapeId = "T001";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Setup: Create Tape with id="T001"
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(tapeId);
        
        // Execute the test
        boolean result = rentalSystem.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Input: tape_id="T002", current_date="2025-01-01"
        String tapeId = "T002";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Setup: Create Tape with id="T002". Create Customer C001.
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(tapeId);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        // Add rental to system
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Execute the test
        boolean result = rentalSystem.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertFalse("Tape T002 should be unavailable when rented out with null return date", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Input: tape_id="T003", current_date="2025-01-01"
        String tapeId = "T003";
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Setup: Create Tape with id="T003". Create Customer C002.
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(tapeId);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter));
        
        // Add rental to system
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Execute the test
        boolean result = rentalSystem.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Input: tape_id="T004", current_date="2025-01-10"
        String tapeId = "T004";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Setup: Create Tape with id="T004". Create Customer C003.
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(tapeId);
        
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2024-12-28", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null);
        
        // Add rental to system
        customer.getRentals().add(rental);
        rentalSystem.getRentals().add(rental);
        
        // Execute the test
        boolean result = rentalSystem.isTapeAvailable(tape, currentDate);
        
        // Verify result
        assertFalse("Tape T004 should be unavailable when has overdue rental with null return date", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Input: tape_id="T005", current_date="2025-01-10"
        String tapeId = "T005";
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Setup: Create Tape with id="T005". Create Customer C004, C005.
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(tapeId);
        
        Customer customer1 = new Customer();
        customer1.setAccountNumber("C004");
        
        Customer customer2 = new Customer();
        customer2.setAccountNumber("C005");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        Rental rental1 = new Rental();
        rental1.setTape(tape);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        Rental rental2 = new Rental();
        rental2.setTape(tape);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null);
        
        // Add rentals to system
        customer1.getRentals().add(rental1);
        customer2.getRentals().add(rental2);
        rentalSystem.getRentals().add(rental1);
        rentalSystem.getRentals().add(rental2);
        
        // Execute the test
        boolean result = rentalSystem.isTapeAvailable(tape, currentDate);
        
        // Verify result - The tape should be unavailable due to the second rental with null return date
        assertFalse("Tape T005 should be unavailable when currently rented out with null return date", result);
    }
}