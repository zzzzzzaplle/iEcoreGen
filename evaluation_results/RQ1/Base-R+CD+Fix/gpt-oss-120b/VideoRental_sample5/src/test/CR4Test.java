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
        // Clear the rental repository before each test
        // Since ALL_RENTALS is private, we'll handle cleanup through test setup
        // by ensuring each test creates its own clean state
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        Customer customer = new Customer("C001");
        
        // Create Tape T001
        Tape tape1 = new Tape("T001", "Video Information 1");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        rental1.setOwnedPastDueAmount(0.0);
        
        // Create Tape T002
        Tape tape2 = new Tape("T002", "Video Information 2");
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental2.setOwnedPastDueAmount(0.0);
        
        // Add rentals to customer and repository
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected total price: 2 + 11 = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        Customer customer = new Customer("C002");
        
        // Create Tape T003
        Tape tape = new Tape("T003", "Video Information 3");
        
        // Create Rental 1: returned late with 7 days overdue
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental.setOwnedPastDueAmount(0.0);
        
        // Add rental to customer and repository
        customer.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.addRental(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected total price: 11 (base) + 3.50 (overdue) = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        Customer customer = new Customer("C003");
        
        // Create Tape T004
        Tape tape1 = new Tape("T004", "Video Information 4");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        rental1.setOwnedPastDueAmount(0.0);
        
        // Create Tape T005
        Tape tape2 = new Tape("T005", "Video Information 5");
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        rental2.setOwnedPastDueAmount(0.0);
        
        // Add rentals to customer and repository
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected total price: (8+8 base) + (2+1.5 overdue) = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        Customer customer = new Customer("C004");
        
        // Create Tape T006
        Tape tape1 = new Tape("T006", "Video Information 6");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        rental1.setOwnedPastDueAmount(0.0);
        
        // Create Tape T007
        Tape tape2 = new Tape("T007", "Video Information 7");
        
        // Create Rental 2: returned on time
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        rental2.setOwnedPastDueAmount(0.0);
        
        // Add rentals to customer and repository
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected total price: (6+4 base) + 1 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        Customer customer = new Customer("C006");
        
        // Create Tape T008
        Tape tape = new Tape("T008", "Video Information 8");
        
        // Create Rental 1: not returned, 5 days overdue as of current date
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Not returned
        rental.setOwnedPastDueAmount(0.0);
        
        // Add rental to customer and repository
        customer.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.addRental(rental);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected total price: 9 (base) + 2.50 (overdue) = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}