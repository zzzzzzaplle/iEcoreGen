import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    private VideoTape tape;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate("2025-01-0" + i);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7));
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T001
        tape = new VideoTape();
        tape.setId("T001");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental("C001", "T001", "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T002
        tape = new VideoTape();
        tape.setId("T002");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental("C002", "T002", "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        customer = new Customer();
        customer.setId("C003");
        List<Rental> rentals = new ArrayList<>();
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T999");
        overdueRental.setRentalDate("2024-12-29");
        overdueRental.setDueDate("2025-01-05");
        overdueRental.setReturnDate(null); // Not returned, 3 days overdue as of 2025-01-08
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        tape = new VideoTape();
        tape.setId("T003");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-08" (3 days after due date)
        boolean result = system.addVideoTapeRental("C003", "T003", "2025-01-08", "2025-01-15");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental by another customer C010
        tape = new VideoTape();
        tape.setId("T004");
        
        // Create another customer C010 who has T004 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C010");
        List<Rental> otherRentals = new ArrayList<>();
        Rental activeRental = new Rental();
        activeRental.setTapeId("T004");
        activeRental.setRentalDate("2024-12-29");
        activeRental.setDueDate("2025-01-05");
        activeRental.setReturnDate(null); // Tape is still rented
        otherRentals.add(activeRental);
        otherCustomer.setRentals(otherRentals);
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental("C004", "T004", "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        List<Rental> rentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + i);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            rentals.add(rental);
        }
        
        // Add 1 overdue rental (due_date="2024-12-31")
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T020");
        overdueRental.setRentalDate("2024-12-24");
        overdueRental.setDueDate("2024-12-31");
        overdueRental.setReturnDate(null); // Overdue as of 2025-01-01
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental by another customer C011
        tape = new VideoTape();
        tape.setId("T005");
        
        // Create another customer C011 who has T005 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C011");
        List<Rental> otherRentals = new ArrayList<>();
        Rental activeRental = new Rental();
        activeRental.setTapeId("T005");
        activeRental.setRentalDate("2024-12-29");
        activeRental.setDueDate("2025-01-05");
        activeRental.setReturnDate(null); // Tape is still rented
        otherRentals.add(activeRental);
        otherCustomer.setRentals(otherRentals);
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental("C005", "T005", "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}