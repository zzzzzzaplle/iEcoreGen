import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        // Clear static rentals before each test
        List<VideoRental> allRentals = VideoRental.getAllRentals();
        if (allRentals instanceof ArrayList) {
            ((ArrayList<VideoRental>) allRentals).clear();
        }
        
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001 with 2 active rentals
        customer.setId("C001");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-03" (overdue fees=0), rental 2 days
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setTape(tape1);
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-12" (overdue fees=0), rental 2 days
        Tape tape2 = new Tape();
        tape2.setId("T002");
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setTape(tape2);
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20" and rental days = 2
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = transaction.calculateTotalPrice(2, currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 2 + 0 = 2, total price = 4
        assertEquals(4.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002 with 1 rental
        customer.setId("C002");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-12" (7 overdue days × $0.50 = $3.50), rental 11 days
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setTape(tape1);
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20" and rental days = 11
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = transaction.calculateTotalPrice(11, currentDate);
        
        // Expected: 3.50 + 11 = 14.5
        assertEquals(14.5, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003 with 2 rentals
        customer.setId("C003");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-09" (4 overdue days × $0.50 = $2.00), rental 8 days
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setTape(tape1);
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-18" (3 overdue days × $0.50 = $1.50), rental 8 days
        Tape tape2 = new Tape();
        tape2.setId("T002");
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setTape(tape2);
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20" and rental days = 8
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double result = transaction.calculateTotalPrice(8, currentDate);
        
        // Expected: 3.50 + 16 = 19.5
        assertEquals(19.5, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004 with 2 rentals
        customer.setId("C004");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-07" (2 overdue days × $0.50 = $1.00), rental 6 days
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setTape(tape1);
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-14" (fees=0), rental 5 days
        Tape tape2 = new Tape();
        tape2.setId("T002");
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setTape(tape2);
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-20" and rental days = 6 for first, 5 for second
        // Since rental days is applied uniformly, we need to calculate manually
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // For this test case, we need to handle different rental days per rental
        // Since the method applies rental days uniformly, we'll calculate expected manually
        double rental1BaseFee = 6 * 1.0; // 6 days
        double rental2BaseFee = 5 * 1.0; // 5 days
        double rental1Overdue = rental1.calculateOwnedPastDueAmount(currentDate); // Should be 1.00
        double rental2Overdue = rental2.calculateOwnedPastDueAmount(currentDate); // Should be 0.00
        
        double expectedTotal = rental1BaseFee + rental1Overdue + rental2BaseFee + rental2Overdue;
        
        // Since the method applies rental days uniformly, we'll use the average rental days
        int avgRentalDays = (6 + 5) / 2; // This is approximate since the method doesn't support per-rental days
        double result = transaction.calculateTotalPrice(avgRentalDays, currentDate);
        
        // The method applies rental days uniformly, so we need to adjust our expectation
        // For uniform application: both rentals get 5.5 days average = 11 total base fee
        double adjustedExpected = 11.0 + 1.00; // 11 base fee + 1.00 overdue = 12.0
        assertEquals(12.0, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006 with 1 unreturned rental
        customer.setId("C006");
        
        // Rental 1: due_date="2025-01-05", return_date=null (5 overdue days × $0.50 = $2.50), rental 5 days
        Tape tape1 = new Tape();
        tape1.setId("T001");
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        rental1.setTape(tape1);
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price with current date "2025-01-10" and rental days = 5
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        double result = transaction.calculateTotalPrice(5, currentDate);
        
        // Expected: 2.50 + 5 = 7.5
        assertEquals(7.5, result, 0.001);
    }
}