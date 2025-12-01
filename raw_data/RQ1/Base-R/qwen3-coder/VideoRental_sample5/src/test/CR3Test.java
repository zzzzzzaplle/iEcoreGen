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
        List<Rental> customerRentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i).plusDays(7));
            rental.setReturnDate(null);
            rental.setCustomer(customer);
            customerRentals.add(rental);
        }
        customer.setRentals(customerRentals);
        
        // Create available Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getTapes().add(tape);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T001", 7);
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setPastDueAmount(0.0);
        
        // Create 20 active rentals for customer
        List<Rental> customerRentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-01"));
            rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(7));
            rental.setReturnDate(null);
            rental.setCustomer(customer);
            customerRentals.add(rental);
        }
        customer.setRentals(customerRentals);
        
        // Create available Tape T002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getTapes().add(tape);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T002", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is 3 days overdue
        Customer customer = new Customer();
        customer.setAccountId("C003");
        customer.setPastDueAmount(1.5); // 3 days overdue at $0.5 per day = $1.5
        
        // Create overdue rental
        Rental overdueRental = new Rental();
        overdueRental.setRentalDate(LocalDate.parse("2025-01-01"));
        overdueRental.setDueDate(LocalDate.parse("2025-01-02")); // Due yesterday
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setCustomer(customer);
        
        List<Rental> customerRentals = new ArrayList<>();
        customerRentals.add(overdueRental);
        customer.setRentals(customerRentals);
        
        // Create available Tape T003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getTapes().add(tape);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T003", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has past due amount", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountId("C004");
        customer.setPastDueAmount(0.0);
        customer.setRentals(new ArrayList<>());
        
        // Create another customer C010 who has Tape T004 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setAccountId("C010");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setAvailable(false);
        
        // Create active rental for Tape T004 by another customer
        Rental activeRental = new Rental();
        activeRental.setCustomer(otherCustomer);
        activeRental.setTape(tape);
        activeRental.setRentalDate(LocalDate.parse("2025-01-01"));
        activeRental.setDueDate(LocalDate.parse("2025-01-05"));
        activeRental.setReturnDate(null); // Not returned
        
        // Add customers, tape, and rental to system
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getCustomers().add(otherCustomer);
        rentalSystem.getTapes().add(tape);
        rentalSystem.getRentals().add(activeRental);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T004", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setAccountId("C005");
        customer.setPastDueAmount(5.0); // Overdue amount $5.00
        
        // Create 20 active rentals for customer
        List<Rental> customerRentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalDate(LocalDate.parse("2025-01-01"));
            rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(7));
            rental.setReturnDate(null);
            rental.setCustomer(customer);
            customerRentals.add(rental);
        }
        
        // Add one overdue rental
        Rental overdueRental = new Rental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-25"));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31")); // Due date passed
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setCustomer(customer);
        customerRentals.add(overdueRental);
        
        customer.setRentals(customerRentals);
        
        // Create another customer C011 who has Tape T005 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setAccountId("C011");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setAvailable(false);
        
        // Create active rental for Tape T005 by another customer
        Rental activeRental = new Rental();
        activeRental.setCustomer(otherCustomer);
        activeRental.setTape(tape);
        activeRental.setRentalDate(LocalDate.parse("2025-01-01"));
        activeRental.setDueDate(LocalDate.parse("2025-01-05"));
        activeRental.setReturnDate(null); // Not returned
        
        // Add customers, tape, and rental to system
        rentalSystem.getCustomers().add(customer);
        rentalSystem.getCustomers().add(otherCustomer);
        rentalSystem.getTapes().add(tape);
        rentalSystem.getRentals().add(activeRental);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental(customer, "T005", 7);
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}