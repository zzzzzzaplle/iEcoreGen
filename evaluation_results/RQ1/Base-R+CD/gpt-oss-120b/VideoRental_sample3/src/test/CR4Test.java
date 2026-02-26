import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        // Clear the rental repository before each test
        // Using reflection to reset the static list since RentalRepository doesn't provide a clear method
        try {
            java.lang.reflect.Field field = RentalRepository.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            List<VideoRental> rentals = (List<VideoRental>) field.get(null);
            rentals.clear();
        } catch (Exception e) {
            fail("Failed to clear rental repository: " + e.getMessage());
        }
        
        transaction = new RentalTransaction();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001 with 2 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-03" (overdue fees=0), rental 2 days
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setTape(new Tape());
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-12" (overdue fees=0), rental 2 days
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Input: current date is "2025-01-20", rental days = 2
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        int rentalDays = 2;
        
        // Expected Output: total price = 4.0
        double expectedTotal = 4.0;
        double actualTotal = transaction.calculateTotalPrice(rentalDays, currentDate);
        
        assertEquals("Total price should be 4.0 for 2 rentals with no overdue fees", 
                     expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002 with 1 rental
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-12" (7 overdue days × $0.50 = $3.50), rental 11 days
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Input: current date is "2025-01-20", rental days = 11
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        int rentalDays = 11;
        
        // Expected Output: 3.50 + 11 = 14.5
        double expectedTotal = 14.5;
        double actualTotal = transaction.calculateTotalPrice(rentalDays, currentDate);
        
        assertEquals("Total price should be 14.5 for one overdue rental", 
                     expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003 with 2 rentals
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-09" (4 overdue days × $0.50 = $2.00), rental 8 days
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setTape(new Tape());
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-18" (3 overdue days × $0.50 = $1.50), rental 8 days
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Input: current date is "2025-01-20", rental days = 8
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        int rentalDays = 8;
        
        // Expected Output: 3.50 + 16 = 19.5
        double expectedTotal = 19.5;
        double actualTotal = transaction.calculateTotalPrice(rentalDays, currentDate);
        
        assertEquals("Total price should be 19.5 for multiple overdue rentals", 
                     expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004 with 2 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Rental 1: due_date="2025-01-05", return_date="2025-01-07" (2 overdue days × $0.50 = $1.00), rental 6 days
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setTape(new Tape());
        
        // Rental 2: due_date="2025-01-15", return_date="2025-01-14" (fees=0), rental 5 days
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Input: current date is "2025-01-20", rental days = 6 for rental1, 5 for rental2
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        int rentalDays = 6; // Using the maximum rental days from the setup
        
        // Expected Output: 1.00 + 11 = 12.0
        double expectedTotal = 12.0;
        double actualTotal = transaction.calculateTotalPrice(rentalDays, currentDate);
        
        assertEquals("Total price should be 12.0 for mixed overdue and on-time rentals", 
                     expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006 with 1 unreturned rental
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Rental 1: due_date="2025-01-05", return_date=null (5 overdue days × $0.50 = $2.50), rental 5 days
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        rental1.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Input: current date is "2025-01-10", rental days = 5
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        int rentalDays = 5;
        
        // Expected Output: 2.50 + 5 = 7.5
        double expectedTotal = 7.5;
        double actualTotal = transaction.calculateTotalPrice(rentalDays, currentDate);
        
        assertEquals("Total price should be 7.5 for unreturned tape with overdue fees", 
                     expectedTotal, actualTotal, 0.001);
    }
}