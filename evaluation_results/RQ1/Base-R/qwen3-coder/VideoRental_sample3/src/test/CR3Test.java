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
        
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 8));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create available Tape T001 with no active rentals
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        rentalSystem.getInventory().add(tape);
        
        // Execute: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T001", 7);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setPastDueAmount(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 8));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create available Tape T002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        rentalSystem.getInventory().add(tape);
        
        // Execute: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T002", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental with overdue fees
        Customer customer = new Customer();
        customer.setAccountId("C003");
        customer.setPastDueAmount(1.5); // 3 days overdue * $0.50 = $1.50
        
        List<Rental> rentals = new ArrayList<>();
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 2));
        rental.setReturnDate(null); // 3 days overdue as of 2025-01-05
        rentals.add(rental);
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create available Tape T003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        rentalSystem.getInventory().add(tape);
        
        // Execute: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T003", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountId("C004");
        customer.setPastDueAmount(0.0);
        customer.setRentals(new ArrayList<>());
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tape T004 with active rental by another customer C010
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setAvailable(true);
        rentalSystem.getInventory().add(tape);
        
        // Create customer C010 who has active rental of T004
        Customer customer010 = new Customer();
        customer010.setAccountId("C010");
        List<Rental> rentals010 = new ArrayList<>();
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null);
        rentals010.add(rental);
        customer010.setRentals(rentals010);
        rentalSystem.getCustomers().add(customer010);
        
        // Execute: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T004", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable (already rented by another customer)", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountId("C005");
        customer.setPastDueAmount(5.0); // Overdue amount $5.00
        
        List<Rental> rentals = new ArrayList<>();
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 8));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        rentalSystem.getCustomers().add(customer);
        
        // Setup: Create Tape T005 with active rental by another customer C011
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setAvailable(true);
        rentalSystem.getInventory().add(tape);
        
        Customer customer011 = new Customer();
        customer011.setAccountId("C011");
        List<Rental> rentals011 = new ArrayList<>();
        Rental rental011 = new Rental();
        rental011.setTapeId("T005");
        rental011.setRentalDate(LocalDate.of(2025, 1, 1));
        rental011.setDueDate(LocalDate.of(2025, 1, 5));
        rental011.setReturnDate(null);
        rentals011.add(rental011);
        customer011.setRentals(rentals011);
        rentalSystem.getCustomers().add(customer011);
        
        // Execute: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T005", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions are violated (max rentals, overdue fees, and tape unavailable)", result);
    }
}