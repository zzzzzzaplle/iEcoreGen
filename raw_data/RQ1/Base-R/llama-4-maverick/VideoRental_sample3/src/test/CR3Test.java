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
    public void testCase1_successfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setIdCard("C001");
        
        // Add 5 active rentals to customer
        for (int i = 1; i <= 5; i++) {
            VideoTape tempTape = new VideoTape();
            tempTape.setBarCodeId("TEMP" + i);
            
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setVideoTape(tempTape);
            rental.setRentalDate("2025-01-0" + i);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7));
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
        }
        
        // Setup: Create available Tape T001
        tape = new VideoTape();
        tape.setBarCodeId("T001");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer, tape, "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is True
        assertTrue("Rental should be successful when customer has <20 rentals and tape is available", result);
    }
    
    @Test
    public void testCase2_customerHasMaxRentals() {
        // Setup: Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setIdCard("C002");
        
        // Add 20 active rentals to customer
        for (int i = 1; i <= 20; i++) {
            VideoTape tempTape = new VideoTape();
            tempTape.setBarCodeId("TEMP" + i);
            
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setVideoTape(tempTape);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
        }
        
        // Setup: Create available Tape T002
        tape = new VideoTape();
        tape.setBarCodeId("T002");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer, tape, "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when customer has 20 rentals", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active overdue rental
        customer = new Customer();
        customer.setIdCard("C003");
        
        // Add overdue rental (due date 2025-01-02, current date 2025-01-05)
        VideoTape tempTape = new VideoTape();
        tempTape.setBarCodeId("TEMP1");
        
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setVideoTape(tempTape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-02"); // Due 3 days ago
        // return_date remains null (unreturned)
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        
        // Setup: Create available Tape T003
        tape = new VideoTape();
        tape.setBarCodeId("T003");
        
        // Add customer and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        boolean result = system.addVideoTapeRental(customer, tape, "2025-01-05", "2025-01-12");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setIdCard("C004");
        
        // Setup: Create Tape T004 with active rental by another customer C010
        tape = new VideoTape();
        tape.setBarCodeId("T004");
        
        Customer otherCustomer = new Customer();
        otherCustomer.setIdCard("C010");
        
        RentalTransaction otherTransaction = new RentalTransaction();
        Rental otherRental = new Rental();
        otherRental.setVideoTape(tape);
        otherRental.setRentalDate("2025-01-01");
        otherRental.setDueDate("2025-01-05");
        // return_date remains null (still rented)
        
        otherTransaction.getRentals().add(otherRental);
        otherCustomer.getRentalTransactions().add(otherTransaction);
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer, tape, "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setIdCard("C005");
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoTape tempTape = new VideoTape();
            tempTape.setBarCodeId("TEMP" + i);
            
            RentalTransaction transaction = new RentalTransaction();
            Rental rental = new Rental();
            rental.setVideoTape(tempTape);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
        }
        
        // Add one overdue rental (due_date="2024-12-31")
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarCodeId("OVERDUE");
        
        RentalTransaction overdueTransaction = new RentalTransaction();
        Rental overdueRental = new Rental();
        overdueRental.setVideoTape(overdueTape);
        overdueRental.setRentalDate("2024-12-25");
        overdueTape.setDueDate("2024-12-31"); // Overdue by 1 day on 2025-01-01
        // return_date remains null (unreturned)
        
        overdueTransaction.getRentals().add(overdueRental);
        customer.getRentalTransactions().add(overdueTransaction);
        
        // Setup: Create Tape T005 with active rental by another customer C011
        tape = new VideoTape();
        tape.setBarCodeId("T005");
        
        Customer otherCustomer = new Customer();
        otherCustomer.setIdCard("C011");
        
        RentalTransaction otherTransaction = new RentalTransaction();
        Rental otherRental = new Rental();
        otherRental.setVideoTape(tape);
        otherRental.setRentalDate("2025-01-01");
        otherRental.setDueDate("2025-01-05");
        // return_date remains null (still rented)
        
        otherTransaction.getRentals().add(otherRental);
        otherCustomer.getRentalTransactions().add(otherTransaction);
        
        // Add both customers and tape to system
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(otherCustomer);
        system.setCustomers(customers);
        
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        system.setVideoTapes(tapes);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = system.addVideoTapeRental(customer, tape, "2025-01-01", "2025-01-08");
        
        // Verify: Expected output is False
        assertFalse("Rental should fail when all conditions are violated", result);
    }
}