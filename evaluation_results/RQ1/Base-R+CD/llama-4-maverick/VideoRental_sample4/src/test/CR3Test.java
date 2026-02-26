import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private Customer customer;
    private Tape tape;
    private LocalDate currentDate;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        customer = new Customer();
        customer.setId("C001");
        
        // Create 5 active rentals (unreturned, due dates 7 days after rental)
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T" + String.format("%03d", i + 100));
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-0" + i, formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available tape T001
        tape = new Tape();
        tape.setId("T001");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        assertTrue("Rental should be successful when customer has <20 rentals, no past-due amount, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHasMaxRentals() {
        // Setup
        customer = new Customer();
        customer.setId("C002");
        
        // Create 20 active rentals (max limit)
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T" + String.format("%03d", i + 200));
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available tape T002
        tape = new Tape();
        tape.setId("T002");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        assertFalse("Rental should fail when customer has reached the maximum of 20 rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        customer = new Customer();
        customer.setId("C003");
        
        // Create 1 active rental with due_date="2025-01-05" (3 days overdue on current_date="2025-01-08")
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T300");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        // return_date remains null (unreturned)
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create available tape T003
        tape = new Tape();
        tape.setId("T003");
        
        currentDate = LocalDate.parse("2025-01-08", formatter); // 3 days overdue
        
        // Execute and Verify
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Note: The Tape class currently always returns true for isAvailable()
        // For this test, we need to modify the Tape class behavior to simulate unavailability
        // Since we cannot modify the original class, we'll create a subclass for testing
        
        // Setup
        customer = new Customer();
        customer.setId("C004");
        
        // Create customer with 0 rentals
        customer.setRentals(new ArrayList<>());
        
        // Create unavailable Tape T004 using a subclass
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Simulate unavailable tape
            }
        };
        tape.setId("T004");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        customer = new Customer();
        customer.setId("C005");
        
        // Create 20 active rentals (max limit) + one overdue rental
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T" + String.format("%03d", i + 500));
            rental.setTape(rentalTape);
            
            LocalDate rentalDate = LocalDate.parse("2025-01-01", formatter);
            rental.setDueDate(rentalDate.plusDays(7));
            rentals.add(rental);
        }
        
        // Add overdue rental (due_date="2024-12-31", return_date=null)
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T521");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter));
        rentals.add(overdueRental);
        
        customer.setRentals(rentals);
        
        // Create unavailable Tape T005 using a subclass
        tape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Simulate unavailable tape
            }
        };
        tape.setId("T005");
        
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Execute and Verify
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) are violated", result);
    }
}