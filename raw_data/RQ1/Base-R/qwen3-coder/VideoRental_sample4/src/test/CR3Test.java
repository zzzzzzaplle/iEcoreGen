import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C001");
        customer.setPastDueAmount(0.0);
        
        // Create 5 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T" + String.format("%03d", i + 100));
            rental.setRentalDate(LocalDate.of(2025, 1, i));
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        rentalSystem.getVideoTapes().add(tape);
        
        // Execute: Add video tape rental
        boolean result = rentalSystem.addVideoTapeRental(customer, "T001", 3);
        
        // Verify: Should return true since all conditions pass
        assertTrue("Rental should be successful when customer has <20 rentals, no past due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setPastDueAmount(0.0);
        
        // Create 20 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T" + String.format("%03d", i + 200));
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        rentalSystem.getVideoTapes().add(tape);
        
        // Execute: Add video tape rental
        boolean result = rentalSystem.addVideoTapeRental(customer, "T002", 3);
        
        // Verify: Should return false since customer has 20 rentals (max limit)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setAccountId("C003");
        customer.setPastDueAmount(1.5); // 3 days overdue at $0.5 per day
        
        // Create 1 active overdue rental for customer
        List<Rental> rentals = new ArrayList<>();
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T300");
        overdueRental.setRentalDate(LocalDate.of(2025, 1, 1));
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due 1 day after rental
        overdueRental.setReturnDate(null); // Not returned
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create available Tape T003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        rentalSystem.getVideoTapes().add(tape);
        
        // Execute: Add video tape rental
        boolean result = rentalSystem.addVideoTapeRental(customer, "T003", 3);
        
        // Verify: Should return false since customer has overdue fees
        assertFalse("Rental should fail when customer has past due amount", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountId("C004");
        customer.setPastDueAmount(0.0);
        customer.setRentals(new ArrayList<>());
        
        // Create Tape T004 with active rental by another customer
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setAvailable(true);
        rentalSystem.getVideoTapes().add(tape);
        
        // Create active rental for T004 by another customer C010
        Rental existingRental = new Rental();
        existingRental.setTapeId("T004");
        existingRental.setRentalDate(LocalDate.of(2025, 1, 1));
        existingRental.setDueDate(LocalDate.of(2025, 1, 5));
        existingRental.setReturnDate(null);
        rentalSystem.getRentals().add(existingRental);
        
        // Execute: Add video tape rental
        boolean result = rentalSystem.addVideoTapeRental(customer, "T004", 3);
        
        // Verify: Should return false since tape is unavailable
        assertFalse("Rental should fail when tape is already rented by another customer", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and overdue amount
        Customer customer = new Customer();
        customer.setAccountId("C005");
        customer.setPastDueAmount(5.0); // Overdue amount $5.00
        
        // Create 20 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T" + String.format("%03d", i + 500));
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        
        // Add one overdue rental
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T599");
        overdueRental.setRentalDate(LocalDate.of(2024, 12, 25));
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31)); // Overdue since 2024-12-31
        overdueRental.setReturnDate(null);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create Tape T005 with active rental by another customer
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setAvailable(true);
        rentalSystem.getVideoTapes().add(tape);
        
        // Create active rental for T005 by another customer C011
        Rental existingRental = new Rental();
        existingRental.setTapeId("T005");
        existingRental.setRentalDate(LocalDate.of(2025, 1, 1));
        existingRental.setDueDate(LocalDate.of(2025, 1, 5));
        existingRental.setReturnDate(null);
        rentalSystem.getRentals().add(existingRental);
        
        // Execute: Add video tape rental
        boolean result = rentalSystem.addVideoTapeRental(customer, "T005", 3);
        
        // Verify: Should return false since all conditions fail
        assertFalse("Rental should fail when customer has 20+ rentals, overdue amount, and tape is unavailable", result);
    }
}