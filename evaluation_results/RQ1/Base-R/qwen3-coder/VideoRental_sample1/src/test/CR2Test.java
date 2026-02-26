import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalSystem rentalSystem;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private VideoTape tape1;
    private VideoTape tape2;
    private VideoTape tape3;
    private VideoTape tape4;
    private VideoTape tape5;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        
        // Initialize customers
        customer1 = new Customer();
        customer1.setAccountId("C001");
        
        customer2 = new Customer();
        customer2.setAccountId("C002");
        
        customer3 = new Customer();
        customer3.setAccountId("C003");
        
        customer4 = new Customer();
        customer4.setAccountId("C004");
        
        customer5 = new Customer();
        customer5.setAccountId("C005");
        
        // Initialize tapes
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        
        tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        tape3 = new VideoTape();
        tape3.setBarcodeId("T003");
        
        tape4 = new VideoTape();
        tape4.setBarcodeId("T004");
        
        tape5 = new VideoTape();
        tape5.setBarcodeId("T005");
        
        // Add tapes to rental system
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape1);
        tapes.add(tape2);
        tapes.add(tape3);
        tapes.add(tape4);
        tapes.add(tape5);
        rentalSystem.setVideoTapes(tapes);
        
        // Add customers to rental system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        rentalSystem.setCustomers(customers);
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        String tapeId = "T001";
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Execute: Check tape availability
        boolean result = rentalSystem.checkTapeAvailability(tapeId);
        
        // Verify: Tape should be available
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Customer C001 rents T002 with return_date=null
        String tapeId = "T002";
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Create rental for customer C001
        Rental rental = new Rental();
        rental.setTapeId("T002");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null); // Unreturned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer1.setRentals(rentals);
        
        // Execute: Check tape availability
        boolean result = rentalSystem.checkTapeAvailability(tapeId);
        
        // Verify: Tape should not be available
        assertFalse("Tape T002 should not be available when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Customer C002 rents T003 and returns it
        String tapeId = "T003";
        LocalDate currentDate = LocalDate.of(2025, 1, 1);
        
        // Create rental for customer C002 that has been returned
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate(LocalDate.of(2024, 12, 25));
        rental.setDueDate(LocalDate.of(2024, 12, 30));
        rental.setReturnDate(LocalDate.of(2024, 12, 31)); // Returned
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer2.setRentals(rentals);
        
        // Execute: Check tape availability
        boolean result = rentalSystem.checkTapeAvailability(tapeId);
        
        // Verify: Tape should be available since it was returned
        assertTrue("Tape T003 should be available when rental has been returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Customer C003 rents T004 with return_date=null (overdue)
        String tapeId = "T004";
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // Create overdue rental for customer C003
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate(LocalDate.of(2024, 12, 28));
        rental.setDueDate(LocalDate.of(2025, 1, 1));
        rental.setReturnDate(null); // Unreturned and overdue
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer3.setRentals(rentals);
        
        // Execute: Check tape availability
        boolean result = rentalSystem.checkTapeAvailability(tapeId);
        
        // Verify: Tape should not be available due to overdue rental
        assertFalse("Tape T004 should not be available when there is an overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Multiple customers rent T005
        String tapeId = "T005";
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // First rental: C004 rents and returns T005
        Rental rental1 = new Rental();
        rental1.setTapeId("T005");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1)); // Returned
        
        List<Rental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer4.setRentals(rentals1);
        
        // Second rental: C005 rents T005 but hasn't returned it
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate(LocalDate.of(2025, 1, 6));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(null); // Unreturned
        
        List<Rental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer5.setRentals(rentals2);
        
        // Execute: Check tape availability
        boolean result = rentalSystem.checkTapeAvailability(tapeId);
        
        // Verify: Tape should not be available due to second rental
        assertFalse("Tape T005 should not be available when currently rented out", result);
    }
}