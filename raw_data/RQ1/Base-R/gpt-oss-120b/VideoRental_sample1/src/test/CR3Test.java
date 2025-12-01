import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR3Test {
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C001 with 5 active rentals
        Customer customer = new Customer("C001", "Customer One");
        for (int i = 1; i <= 5; i++) {
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i, formatter);
            LocalDate dueDate = rentalDate.plusDays(7);
            RentalTransaction rental = new RentalTransaction(
                "TR_C001_" + i,
                "C001",
                "T00" + i,
                rentalDate,
                dueDate
            );
            customer.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        rentalSystem.addCustomer(customer);
        
        // Create available Tape T001
        VideoTape tape = new VideoTape("T001", "Test Tape 1");
        rentalSystem.addVideoTape(tape);
        
        // Execute test
        boolean result = rentalSystem.addVideoTapeRental("C001", "T001", currentDate);
        
        // Verify
        assertTrue("Rental should be successful", result);
    }

    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C002 with 20 active rentals
        Customer customer = new Customer("C002", "Customer Two");
        for (int i = 1; i <= 20; i++) {
            LocalDate rentalDate = currentDate.minusDays(i);
            LocalDate dueDate = rentalDate.plusDays(7);
            RentalTransaction rental = new RentalTransaction(
                "TR_C002_" + i,
                "C002",
                "T00" + i,
                rentalDate,
                dueDate
            );
            customer.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        rentalSystem.addCustomer(customer);
        
        // Create available Tape T002
        VideoTape tape = new VideoTape("T002", "Test Tape 2");
        rentalSystem.addVideoTape(tape);
        
        // Execute test
        boolean result = rentalSystem.addVideoTapeRental("C002", "T002", currentDate);
        
        // Verify
        assertFalse("Rental should fail due to max rental limit", result);
    }

    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        LocalDate currentDate = LocalDate.parse("2025-01-05", formatter);
        
        // Create Customer C003 with 1 overdue rental
        Customer customer = new Customer("C003", "Customer Three");
        LocalDate rentalDate = LocalDate.parse("2024-12-20", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-02", formatter); // 3 days overdue on current date
        RentalTransaction rental = new RentalTransaction(
            "TR_C003_1",
            "C003",
            "T009",
            rentalDate,
            dueDate
        );
        customer.addRental(rental);
        rentalSystem.getRentals().add(rental);
        rentalSystem.addCustomer(customer);
        
        // Create available Tape T003
        VideoTape tape = new VideoTape("T003", "Test Tape 3");
        rentalSystem.addVideoTape(tape);
        
        // Execute test
        boolean result = rentalSystem.addVideoTapeRental("C003", "T003", currentDate);
        
        // Verify
        assertFalse("Rental should fail due to overdue fees", result);
    }

    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C004 with 0 rentals
        Customer customer = new Customer("C004", "Customer Four");
        rentalSystem.addCustomer(customer);
        
        // Create Customer C010 who has Tape T004 rented
        Customer otherCustomer = new Customer("C010", "Customer Ten");
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        RentalTransaction existingRental = new RentalTransaction(
            "TR_C010_1",
            "C010",
            "T004",
            rentalDate,
            dueDate
        );
        otherCustomer.addRental(existingRental);
        rentalSystem.getRentals().add(existingRental);
        rentalSystem.addCustomer(otherCustomer);
        
        // Create Tape T004 (already rented by C010)
        VideoTape tape = new VideoTape("T004", "Test Tape 4");
        rentalSystem.addVideoTape(tape);
        
        // Execute test
        boolean result = rentalSystem.addVideoTapeRental("C004", "T004", currentDate);
        
        // Verify
        assertFalse("Rental should fail due to tape being unavailable", result);
    }

    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer("C005", "Customer Five");
        
        // Add 19 active rentals
        for (int i = 1; i <= 19; i++) {
            LocalDate rentalDate = currentDate.minusDays(i);
            LocalDate dueDate = rentalDate.plusDays(7);
            RentalTransaction rental = new RentalTransaction(
                "TR_C005_" + i,
                "C005",
                "T00" + i,
                rentalDate,
                dueDate
            );
            customer.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        
        // Add 1 overdue rental (due date passed)
        LocalDate overdueRentalDate = LocalDate.parse("2024-12-20", formatter);
        LocalDate overdueDueDate = LocalDate.parse("2024-12-31", formatter); // Overdue on current date
        RentalTransaction overdueRental = new RentalTransaction(
            "TR_C005_20",
            "C005",
            "T020",
            overdueRentalDate,
            overdueDueDate
        );
        customer.addRental(overdueRental);
        rentalSystem.getRentals().add(overdueRental);
        rentalSystem.addCustomer(customer);
        
        // Create Customer C011 who has Tape T005 rented
        Customer otherCustomer = new Customer("C011", "Customer Eleven");
        LocalDate rentalDate = LocalDate.parse("2024-12-28", formatter);
        LocalDate dueDate = LocalDate.parse("2025-01-05", formatter);
        RentalTransaction existingRental = new RentalTransaction(
            "TR_C011_1",
            "C011",
            "T005",
            rentalDate,
            dueDate
        );
        otherCustomer.addRental(existingRental);
        rentalSystem.getRentals().add(existingRental);
        rentalSystem.addCustomer(otherCustomer);
        
        // Create Tape T005 (already rented by C011)
        VideoTape tape = new VideoTape("T005", "Test Tape 5");
        rentalSystem.addVideoTape(tape);
        
        // Execute test
        boolean result = rentalSystem.addVideoTapeRental("C005", "T005", currentDate);
        
        // Verify
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}