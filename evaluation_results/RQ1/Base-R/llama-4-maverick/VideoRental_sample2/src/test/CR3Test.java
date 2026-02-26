import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private RentalManager rentalManager;
    private Customer customer;
    private VideoTape tape;
    
    @Before
    public void setUp() {
        rentalManager = new RentalManager();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setIdCard("C001");
        
        // Add 5 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 5; i++) {
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setTapeId("TEMP_" + i);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            transaction.addRental(rental);
            customer.addRentalTransaction(transaction);
        }
        
        // Setup: Create available Tape T001 with no active rentals
        tape = new VideoTape();
        tape.setId("T001");
        
        // Add customer and tape to rental manager
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Execute: C001 rents tape "T001"
        boolean result = rentalManager.addVideoTapeRental("C001", "T001");
        
        // Verify: Expected output is True
        assertTrue("Rental should be successful when customer has <20 rentals, no past due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setIdCard("C002");
        
        // Add 20 active rentals (all unreturned, due dates 7 days after rental)
        for (int i = 1; i <= 20; i++) {
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setTapeId("TEMP_" + i);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            transaction.addRental(rental);
            customer.addRentalTransaction(transaction);
        }
        
        // Setup: Create available Tape T002
        tape = new VideoTape();
        tape.setId("T002");
        
        // Add customer and tape to rental manager
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Execute: C002 rents tape "T002"
        boolean result = rentalManager.addVideoTapeRental("C002", "T002");
        
        // Verify: Expected output is False (customer has max 20 rentals)
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        customer = new Customer();
        customer.setIdCard("C003");
        
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("TEMP_OVERDUE");
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2025-01-01"); // Due date is 4 days before current date
        rental.setReturnDate(null);
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        // Setup: Create available Tape T003
        tape = new VideoTape();
        tape.setId("T003");
        
        // Add customer and tape to rental manager
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Execute: C003 rents tape "T003"
        boolean result = rentalManager.addVideoTapeRental("C003", "T003");
        
        // Verify: Expected output is False (customer has overdue fees)
        assertFalse("Rental should fail when customer has past due amount", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setIdCard("C004");
        
        // Setup: Create Tape T004 with active rental by another customer
        tape = new VideoTape();
        tape.setId("T004");
        
        // Create another customer C010 who has rented T004
        Customer customerC010 = new Customer();
        customerC010.setIdCard("C010");
        
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Tape is still rented out
        transaction.addRental(rental);
        customerC010.addRentalTransaction(transaction);
        
        // Add both customers and tape to rental manager
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customerC010);
        rentalManager.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Execute: C004 rents tape "T004"
        boolean result = rentalManager.addVideoTapeRental("C004", "T004");
        
        // Verify: Expected output is False (tape is unavailable)
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setIdCard("C005");
        
        // Add 20 active rentals
        for (int i = 1; i <= 20; i++) {
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setTapeId("TEMP_" + i);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            transaction.addRental(rental);
            customer.addRentalTransaction(transaction);
        }
        
        // Add one overdue rental (due_date="2024-12-31")
        RentalTransaction overdueTransaction = new RentalTransaction();
        Rental overdueRental = new Rental();
        overdueRental.setTapeId("OVERDUE_TAPE");
        overdueRental.setRentalDate("2024-12-24");
        overdueRental.setDueDate("2024-12-31"); // Overdue by 5 days
        overdueRental.setReturnDate(null);
        overdueTransaction.addRental(overdueRental);
        customer.addRentalTransaction(overdueTransaction);
        
        // Setup: Create Tape T005 with active rental by another customer
        tape = new VideoTape();
        tape.setId("T005");
        
        // Create another customer C011 who has rented T005
        Customer customerC011 = new Customer();
        customerC011.setIdCard("C011");
        
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("T005");
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null); // Tape is still rented out
        transaction.addRental(rental);
        customerC011.addRentalTransaction(transaction);
        
        // Add both customers and tape to rental manager
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customerC011);
        rentalManager.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Execute: C005 rents tape "T005"
        boolean result = rentalManager.addVideoTapeRental("C005", "T005");
        
        // Verify: Expected output is False (all conditions fail)
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}