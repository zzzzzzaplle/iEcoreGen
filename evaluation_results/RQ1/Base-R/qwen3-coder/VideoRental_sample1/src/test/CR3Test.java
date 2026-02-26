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
        // Setup Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C001");
        customer.setPastDueAmount(0.0);
        
        // Create 5 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + (i + 5)); // Use different tape IDs to avoid conflict with T001
            rental.setRentalDate(LocalDate.of(2025, 1, i));
            rental.setDueDate(LocalDate.of(2025, 1, i).plusDays(7));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Test the rental operation
        boolean result = rentalSystem.addVideoTapeRental(customer, "T001", 7);
        
        // Verify result is true (successful rental)
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setAccountId("C002");
        customer.setPastDueAmount(0.0);
        
        // Create 20 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + (i + 10)); // Use different tape IDs to avoid conflict with T002
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T002");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Test the rental operation
        boolean result = rentalSystem.addVideoTapeRental(customer, "T002", 7);
        
        // Verify result is false (customer has max rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        // Create an overdue rental (due date is 2025-01-02, current date is 2025-01-05 - 3 days overdue)
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T00X"); // Use different tape ID
        overdueRental.setRentalDate(LocalDate.of(2025, 1, 1));
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2));
        overdueRental.setReturnDate(null);
        overdueRental.setRentalDays(1);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Calculate the past due amount (3 days overdue × $0.50 = $1.50)
        double overdueFee = overdueRental.calculatePastDueAmount();
        customer.setPastDueAmount(overdueFee);
        
        // Create available Tape T003
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        tape.setAvailable(true);
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Test the rental operation
        boolean result = rentalSystem.addVideoTapeRental(customer, "T003", 7);
        
        // Verify result is false (customer has overdue fees)
        assertFalse("Rental should fail when customer has past due amount", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setAccountId("C004");
        customer.setPastDueAmount(0.0);
        customer.setRentals(new ArrayList<>());
        
        // Setup another customer C010 who has Tape T004 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setAccountId("C010");
        
        Rental activeRental = new Rental();
        activeRental.setTapeId("T004");
        activeRental.setRentalDate(LocalDate.of(2025, 1, 1));
        activeRental.setDueDate(LocalDate.of(2025, 1, 5));
        activeRental.setReturnDate(null);
        activeRental.setRentalDays(4);
        
        List<Rental> otherRentals = new ArrayList<>();
        otherRentals.add(activeRental);
        otherCustomer.setRentals(otherRentals);
        
        // Create Tape T004
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T004");
        tape.setAvailable(false); // Mark as unavailable since it's rented
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Test the rental operation
        boolean result = rentalSystem.addVideoTapeRental(customer, "T004", 7);
        
        // Verify result is false (tape is unavailable)
        assertFalse("Rental should fail when tape is not available", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup Customer C005 with 20 active rentals and overdue fees
        Customer customer = new Customer();
        customer.setAccountId("C005");
        
        // Create 20 active rentals for customer
        List<Rental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setTapeId("T00" + (i + 20)); // Use different tape IDs
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setReturnDate(null);
            rental.setRentalDays(7);
            rentals.add(rental);
        }
        
        // Add one overdue rental (due date 2024-12-31, current date 2025-01-01 - 1 day overdue)
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("T00Y");
        overdueRental.setRentalDate(LocalDate.of(2024, 12, 30));
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31));
        overdueRental.setReturnDate(null);
        overdueRental.setRentalDays(1);
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Calculate and set past due amount (1 day overdue × $0.50 = $0.50)
        double overdueFee = overdueRental.calculatePastDueAmount();
        customer.setPastDueAmount(overdueFee);
        
        // Setup another customer C011 who has Tape T005 rented
        Customer otherCustomer = new Customer();
        otherCustomer.setAccountId("C011");
        
        Rental activeRental = new Rental();
        activeRental.setTapeId("T005");
        activeRental.setRentalDate(LocalDate.of(2025, 1, 1));
        activeRental.setDueDate(LocalDate.of(2025, 1, 5));
        activeRental.setReturnDate(null);
        activeRental.setRentalDays(4);
        
        List<Rental> otherRentals = new ArrayList<>();
        otherRentals.add(activeRental);
        otherCustomer.setRentals(otherRentals);
        
        // Create Tape T005
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T005");
        tape.setAvailable(false); // Mark as unavailable since it's rented
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        rentalSystem.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalSystem.setVideoTapes(tapes);
        
        // Test the rental operation
        boolean result = rentalSystem.addVideoTapeRental(customer, "T005", 7);
        
        // Verify result is false (all conditions fail)
        assertFalse("Rental should fail when customer has 20+ rentals, past due amount, and tape is unavailable", result);
    }
}