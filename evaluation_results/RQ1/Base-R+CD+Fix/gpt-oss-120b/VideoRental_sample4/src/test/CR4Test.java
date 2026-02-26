import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear static rentals list before each test to ensure isolation
        clearAllRentals();
    }
    
    // Helper method to clear static rentals list
    private void clearAllRentals() {
        // Access the static field through reflection or create a reset method
        // Since we can't modify the original code, we'll work around it by being careful with test isolation
        // In a real scenario, we'd add a reset method to the Tape class
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer("C001");
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Create Rental 1: returned early, no overdue fee
        Tape tape1 = new Tape("T001", "Movie 1");
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-05", formatter),
            tape1
        );
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        customer.getRentals().add(rental1);
        Tape.registerRental(rental1);
        
        // Create Rental 2: returned early, no overdue fee
        Tape tape2 = new Tape("T002", "Movie 2");
        VideoRental rental2 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-15", formatter),
            tape2
        );
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        customer.getRentals().add(rental2);
        Tape.registerRental(rental2);
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction(currentDate, customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer("C002");
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Create Rental 1: 7 days overdue
        Tape tape1 = new Tape("T003", "Movie 3");
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-05", formatter),
            tape1
        );
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        customer.getRentals().add(rental1);
        Tape.registerRental(rental1);
        
        // Create transaction and add rental
        RentalTransaction transaction = new RentalTransaction(currentDate, customer);
        transaction.addRental(rental1);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer("C003");
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Create Rental 1: 4 days overdue
        Tape tape1 = new Tape("T004", "Movie 4");
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-05", formatter),
            tape1
        );
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        customer.getRentals().add(rental1);
        Tape.registerRental(rental1);
        
        // Create Rental 2: 3 days overdue
        Tape tape2 = new Tape("T005", "Movie 5");
        VideoRental rental2 = new VideoRental(
            LocalDate.parse("2025-01-10", formatter),
            LocalDate.parse("2025-01-15", formatter),
            tape2
        );
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        customer.getRentals().add(rental2);
        Tape.registerRental(rental2);
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction(currentDate, customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer("C004");
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Create Rental 1: 2 days overdue
        Tape tape1 = new Tape("T006", "Movie 6");
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-05", formatter),
            tape1
        );
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        customer.getRentals().add(rental1);
        Tape.registerRental(rental1);
        
        // Create Rental 2: on-time
        Tape tape2 = new Tape("T007", "Movie 7");
        VideoRental rental2 = new VideoRental(
            LocalDate.parse("2025-01-10", formatter),
            LocalDate.parse("2025-01-15", formatter),
            tape2
        );
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        customer.getRentals().add(rental2);
        Tape.registerRental(rental2);
        
        // Create transaction and add rentals
        RentalTransaction transaction = new RentalTransaction(currentDate, customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer("C006");
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Create Rental 1: unreturned, 5 days overdue
        Tape tape1 = new Tape("T008", "Movie 8");
        VideoRental rental1 = new VideoRental(
            LocalDate.parse("2025-01-01", formatter),
            LocalDate.parse("2025-01-05", formatter),
            tape1
        );
        // return_date remains null (unreturned)
        customer.getRentals().add(rental1);
        Tape.registerRental(rental1);
        
        // Create transaction and add rental
        RentalTransaction transaction = new RentalTransaction(currentDate, customer);
        transaction.addRental(rental1);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}