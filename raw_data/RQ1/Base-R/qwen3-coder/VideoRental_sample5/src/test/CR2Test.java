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
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Test Tape 1");
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setTapes(tapes);
        
        // Input: tape_id="T001", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = rentalSystem.isTapeAvailable("T001", currentDate);
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setTitle("Test Tape 2");
        
        Customer customer = new Customer();
        customer.setAccountId("C001");
        customer.setIdCardBarcode("ID001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setTapes(tapes);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = rentalSystem.isTapeAvailable("T002", currentDate);
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setTitle("Test Tape 3");
        
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setIdCardBarcode("ID002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        Rental rental = new Rental();
        rental.setRentalId("R002");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2024, 12, 25));
        rental.setDueDate(LocalDate.of(2024, 12, 30));
        rental.setReturnDate(LocalDate.of(2024, 12, 31));
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setTapes(tapes);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        boolean result = rentalSystem.isTapeAvailable("T003", currentDate);
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setTitle("Test Tape 4");
        
        Customer customer = new Customer();
        customer.setAccountId("C003");
        customer.setIdCardBarcode("ID003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        Rental rental = new Rental();
        rental.setRentalId("R003");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setTapes(tapes);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        rentalSystem.setRentals(rentals);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = rentalSystem.isTapeAvailable("T004", currentDate);
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setTitle("Test Tape 5");
        
        Customer customer1 = new Customer();
        customer1.setAccountId("C004");
        customer1.setIdCardBarcode("ID004");
        
        Customer customer2 = new Customer();
        customer2.setAccountId("C005");
        customer2.setIdCardBarcode("ID005");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        Rental rental1 = new Rental();
        rental1.setRentalId("R004");
        rental1.setCustomer(customer1);
        rental1.setTape(tape);
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        Rental rental2 = new Rental();
        rental2.setRentalId("R005");
        rental2.setCustomer(customer2);
        rental2.setTape(tape);
        rental2.setRentalDate(LocalDate.of(2025, 1, 6));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setTapes(tapes);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentalSystem.setRentals(rentals);
        
        // Input: tape_id="T005", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        boolean result = rentalSystem.isTapeAvailable("T005", currentDate);
        
        // Expected Output: False (because second rental is still active)
        assertFalse("Tape T005 should not be available (currently rented by second customer)", result);
    }
}