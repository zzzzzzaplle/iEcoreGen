import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR2Test {
    private RentalSystem system;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        system = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup
        VideoTape tape = new VideoTape("T001", "Test Tape 1");
        system.addVideoTape(tape);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = system.isTapeAvailable("T001", currentDate);
        
        // Verify
        assertTrue("Tape T001 should be available", result);
    }

    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup
        VideoTape tape = new VideoTape("T002", "Test Tape 2");
        Customer customer = new Customer("C001", "Customer 1");
        system.addVideoTape(tape);
        system.addCustomer(customer);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        
        RentalTransaction rental = new RentalTransaction(
            "TRANS001", "C001", "T002", rentalDate, dueDate
        );
        rental.setReturnDate(null); // Unreturned
        customer.addRental(rental);
        system.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = system.isTapeAvailable("T002", currentDate);
        
        // Verify
        assertFalse("Tape T002 should not be available (rented out)", result);
    }

    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup
        VideoTape tape = new VideoTape("T003", "Test Tape 3");
        Customer customer = new Customer("C002", "Customer 2");
        system.addVideoTape(tape);
        system.addCustomer(customer);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-25", formatter);
        LocalDate dueDate = LocalDate.parse("2024-12-30", formatter);
        LocalDate returnDate = LocalDate.parse("2024-12-31", formatter);
        
        RentalTransaction rental = new RentalTransaction(
            "TRANS002", "C002", "T003", rentalDate, dueDate
        );
        rental.setReturnDate(returnDate); // Returned
        customer.addRental(rental);
        system.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute
        boolean result = system.isTapeAvailable("T003", currentDate);
        
        // Verify
        assertTrue("Tape T003 should be available (returned)", result);
    }

    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup
        VideoTape tape = new VideoTape("T004", "Test Tape 4");
        Customer customer = new Customer("C003", "Customer 3");
        system.addVideoTape(tape);
        system.addCustomer(customer);
        
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-01", formatter);
        
        RentalTransaction rental = new RentalTransaction(
            "TRANS003", "C003", "T004", rentalDate, dueDate
        );
        rental.setReturnDate(null); // Unreturned and overdue
        customer.addRental(rental);
        system.getRentals().add(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute
        boolean result = system.isTapeAvailable("T004", currentDate);
        
        // Verify
        assertFalse("Tape T004 should not be available (overdue rental)", result);
    }

    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup
        VideoTape tape = new VideoTape("T005", "Test Tape 5");
        Customer customer1 = new Customer("C004", "Customer 4");
        Customer customer2 = new Customer("C005", "Customer 5");
        system.addVideoTape(tape);
        system.addCustomer(customer1);
        system.addCustomer(customer2);
        
        // First rental - returned
        LocalDate rentalDate1 = LocalDate.parse("2025-01-01", formatter);
        LocalDate dueDate1 = LocalDate.parse("2025-01-05", formatter);
        LocalDate returnDate1 = LocalDate.parse("2025-01-01", formatter);
        
        RentalTransaction rental1 = new RentalTransaction(
            "TRANS004", "C004", "T005", rentalDate1, dueDate1
        );
        rental1.setReturnDate(returnDate1);
        customer1.addRental(rental1);
        system.getRentals().add(rental1);
        
        // Second rental - unreturned
        LocalDate rentalDate2 = LocalDate.parse("2025-01-06", formatter);
        LocalDate dueDate2 = LocalDate.parse("2025-01-15", formatter);
        
        RentalTransaction rental2 = new RentalTransaction(
            "TRANS005", "C005", "T005", rentalDate2, dueDate2
        );
        rental2.setReturnDate(null);
        customer2.addRental(rental2);
        system.getRentals().add(rental2);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute
        boolean result = system.isTapeAvailable("T005", currentDate);
        
        // Verify
        assertFalse("Tape T005 should not be available (currently rented by C005)", result);
    }
}