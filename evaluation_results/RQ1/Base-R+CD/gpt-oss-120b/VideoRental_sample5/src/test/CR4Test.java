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
        // Clear static ALL_RENTALS list before each test to ensure isolation
        // Note: This requires reflection or package-private access, but for test purposes we'll create fresh tapes
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer.setId("C001");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: due_date="2025-01-05", return_date="2025-01-03" (overdue fees=0)
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setTape(new Tape());
        
        // Create Rental 2: due_date="2025-01-15", return_date="2025-01-12" (overdue fees=0)
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental days = 2
        double result = transaction.calculateTotalPrice(2, currentDate);
        
        // Verify: Rental 1 price: 2 + 0 = 2, Rental 2 price: 2 + 0 = 2, total price = 4
        assertEquals(4.0, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer.setId("C002");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: due_date="2025-01-05", return_date="2025-01-12" (7 overdue days × $0.50 = $3.50)
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental days = 11
        double result = transaction.calculateTotalPrice(11, currentDate);
        
        // Verify: 3.50 + 11 = 14.5
        assertEquals(14.5, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer.setId("C003");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: due_date="2025-01-05", return_date="2025-01-09" (4 overdue days × $0.50 = $2.00)
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setTape(new Tape());
        
        // Create Rental 2: due_date="2025-01-15", return_date="2025-01-18" (3 overdue days × $0.50 = $1.50)
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental days = 8
        double result = transaction.calculateTotalPrice(8, currentDate);
        
        // Verify: 3.50 + 16 = 19.5
        assertEquals(19.5, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer.setId("C004");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: due_date="2025-01-05", return_date="2025-01-07" (2 overdue days × $0.50 = $1.00)
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setTape(new Tape());
        
        // Create Rental 2: due_date="2025-01-15", return_date="2025-01-14" (fees=0)
        VideoRental rental2 = new VideoRental();
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental days = 6 for rental1 and 5 for rental2
        // Note: The specification says rental 6 days and rental 5 days, so we use rentalDays = 6 for both
        double result = transaction.calculateTotalPrice(6, currentDate);
        
        // Verify: 1.00 + 11 = 12.0 (1.0 overdue + 6 base fee for rental1 + 5 base fee for rental2)
        assertEquals(12.0, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer.setId("C006");
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // Create Rental 1: due_date="2025-01-05", return_date=null (5 overdue days × $0.50 = $2.50)
        VideoRental rental1 = new VideoRental();
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not yet returned
        rental1.setTape(new Tape());
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price with rental days = 5
        double result = transaction.calculateTotalPrice(5, currentDate);
        
        // Verify: 2.50 + 5 = 7.5
        assertEquals(7.5, result, 0.001);
    }
}