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
        // Setup for Test Case 1: Successful rental
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7)); // due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T001
        tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Movie T001");
        
        // Execute the rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify result
        assertTrue("Rental should be successful when customer has <20 rentals and no past-due amount", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup for Test Case 2: Customer has 20 rentals (max limit)
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7)); // due date 7 days after rental
            rentals.add(rental);
        }
        customer.setRentals(rentals);
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        tape.setVideoInformation("Movie T002");
        
        // Execute the rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when customer has 20 rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup for Test Case 3: Customer has overdue fees
        currentDate = LocalDate.parse("2025-01-05", formatter);
        
        // Create Customer C003 with 1 active rental that is overdue
        customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape tempTape = new Tape();
        tempTape.setId("TEMP001");
        overdueRental.setTape(tempTape);
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", formatter)); // due date 3 days ago
        overdueRental.setReturnDate(null); // not returned
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        tape.setVideoInformation("Movie T003");
        
        // Execute the rental
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup for Test Case 4: Tape is unavailable
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Create Tape T004 that is currently rented by another customer
        tape = new Tape();
        tape.setId("T004");
        tape.setVideoInformation("Movie T004");
        
        // Override isAvailable method to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T004");
        unavailableTape.setVideoInformation("Movie T004");
        
        // Execute the rental
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup for Test Case 5: All conditions fail
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        // Add 19 regular rentals
        for (int i = 0; i < 19; i++) {
            VideoRental rental = new VideoRental();
            Tape tempTape = new Tape();
            tempTape.setId("TEMP" + i);
            rental.setTape(tempTape);
            rental.setDueDate(currentDate.plusDays(7));
            rentals.add(rental);
        }
        // Add 1 overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("OVERDUE001");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter)); // overdue
        overdueRental.setReturnDate(null); // not returned
        rentals.add(overdueRental);
        customer.setRentals(rentals);
        
        // Create Tape T005 that is currently rented by another customer
        tape = new Tape();
        tape.setId("T005");
        tape.setVideoInformation("Movie T005");
        
        // Override isAvailable method to return false for this test
        Tape unavailableTape = new Tape() {
            @Override
            public boolean isAvailable(LocalDate currentDate) {
                return false; // Tape is unavailable
            }
        };
        unavailableTape.setId("T005");
        unavailableTape.setVideoInformation("Movie T005");
        
        // Execute the rental
        boolean result = customer.addVedioTapeRental(unavailableTape, currentDate);
        
        // Verify result
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape unavailable) fail", result);
    }
}