import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalManager rentalManager;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalManager = new RentalManager();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", no active rentals for T001
        VideoTape tape = new VideoTape();
        tape.setId("T001");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // Test tape availability for current_date="2025-01-01"
        boolean isAvailable = isTapeAvailable("T001", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", isAvailable);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002", Create Customer C001
        VideoTape tape = new VideoTape();
        tape.setId("T002");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        Customer customer = new Customer();
        customer.setIdCard("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("T002");
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-01"
        boolean isAvailable = isTapeAvailable("T002", "2025-01-01");
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", isAvailable);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003", Create Customer C002
        VideoTape tape = new VideoTape();
        tape.setId("T003");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        Customer customer = new Customer();
        customer.setIdCard("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("T003");
        rental.setRentalDate("2024-12-25");
        rental.setDueDate("2024-12-30");
        rental.setReturnDate("2024-12-31");
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-01"
        boolean isAvailable = isTapeAvailable("T003", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", isAvailable);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004", Create Customer C003
        VideoTape tape = new VideoTape();
        tape.setId("T004");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        Customer customer = new Customer();
        customer.setIdCard("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        rental.setTapeId("T004");
        rental.setRentalDate("2024-12-28");
        rental.setDueDate("2025-01-01");
        rental.setReturnDate(null);
        transaction.addRental(rental);
        customer.addRentalTransaction(transaction);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        rentalManager.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-10"
        boolean isAvailable = isTapeAvailable("T004", "2025-01-10");
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", isAvailable);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        VideoTape tape = new VideoTape();
        tape.setId("T005");
        List<VideoTape> tapes = new ArrayList<>();
        tapes.add(tape);
        rentalManager.setVideoTapes(tapes);
        
        // First customer C004
        Customer customer1 = new Customer();
        customer1.setIdCard("C004");
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        RentalTransaction transaction1 = new RentalTransaction();
        Rental rental1 = new Rental();
        rental1.setTapeId("T005");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        transaction1.addRental(rental1);
        customer1.addRentalTransaction(transaction1);
        
        // Second customer C005
        Customer customer2 = new Customer();
        customer2.setIdCard("C005");
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        RentalTransaction transaction2 = new RentalTransaction();
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate("2025-01-06");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate(null);
        transaction2.addRental(rental2);
        customer2.addRentalTransaction(transaction2);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        rentalManager.setCustomers(customers);
        
        // Test tape availability for current_date="2025-01-10"
        boolean isAvailable = isTapeAvailable("T005", "2025-01-10");
        
        // Expected Output: False (because second rental is still active)
        assertFalse("Tape T005 should not be available (second rental active)", isAvailable);
    }
    
    /**
     * Helper method to check tape availability for a given date
     * Implements the computational requirement: 
     * "Check tape availability for a given date. For the given current date, a tape is unavailable 
     * if it belongs to any active rental whose return date is null or whose return date is after the given date. 
     * Return true if the tape is available; otherwise, false."
     */
    private boolean isTapeAvailable(String tapeId, String currentDate) {
        LocalDate checkDate = LocalDate.parse(currentDate, formatter);
        
        // Get all rentals across all customers
        List<Rental> allRentals = getAllRentals();
        
        for (Rental rental : allRentals) {
            if (rental.getTapeId().equals(tapeId)) {
                // Check if this rental makes the tape unavailable
                if (rental.getReturnDate() == null) {
                    // Unreturned rental - tape is unavailable
                    return false;
                } else {
                    LocalDate returnDate = LocalDate.parse(rental.getReturnDate(), formatter);
                    if (returnDate.isAfter(checkDate)) {
                        // Return date is after current date - tape is unavailable
                        return false;
                    }
                }
            }
        }
        
        // No active rentals found that make the tape unavailable
        return true;
    }
    
    /**
     * Helper method to get all rentals across all customers (similar to RentalManager.getAllRentals())
     */
    private List<Rental> getAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        for (Customer customer : rentalManager.getCustomers()) {
            for (RentalTransaction transaction : customer.getRentalTransactions()) {
                rentals.addAll(transaction.getRentals());
            }
        }
        return rentals;
    }
}