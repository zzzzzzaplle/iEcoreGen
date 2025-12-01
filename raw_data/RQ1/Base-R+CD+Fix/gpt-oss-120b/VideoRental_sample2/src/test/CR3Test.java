import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Before
    public void setUp() {
        // Clear the rental store before each test to ensure isolation
        RentalStore.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> activeRentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i).plusDays(7));
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            Tape tape = new Tape();
            tape.setId("T" + String.format("%03d", 100 + i));
            rental.setTape(tape);
            
            activeRentals.add(rental);
            RentalStore.addRental(rental);
        }
        customer.setRentals(activeRentals);
        
        // Setup: Create available Tape T001 with no active rentals
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Input: C001 rents tape "T001" with current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: True
        assertTrue("Rental should be successful when all conditions are met", result);
        
        // Verify the rental was actually added
        assertEquals(6, customer.getRentals().size());
        assertTrue(RentalStore.getAllRentals().contains(customer.getRentals().get(5)));
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> activeRentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(LocalDate.parse("2025-01-01"));
            rental.setDueDate(LocalDate.parse("2025-01-01").plusDays(7));
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            Tape tape = new Tape();
            tape.setId("T" + String.format("%03d", 200 + i));
            rental.setTape(tape);
            
            activeRentals.add(rental);
            RentalStore.addRental(rental);
        }
        customer.setRentals(activeRentals);
        
        // Setup: Create available Tape T002
        Tape tape = new Tape();
        tape.setId("T002");
        
        // Input: C002 rents tape "T002" with current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has 20 active rentals", result);
        
        // Verify no new rental was added
        assertEquals(20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-05", return_date=null (3 days overdue)
        Customer customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> rentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-29"));
        overdueRental.setDueDate(LocalDate.parse("2025-01-05"));
        overdueRental.setReturnDate(null);
        overdueRental.setOwnedPastDueAmount(0.0);
        
        Tape overdueTape = new Tape();
        overdueTape.setId("T050");
        overdueRental.setTape(overdueTape);
        
        rentals.add(overdueRental);
        RentalStore.addRental(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create available Tape T003
        Tape tape = new Tape();
        tape.setId("T003");
        
        // Input: C003 rents tape "T003" with current_date="2025-01-08" (3 days overdue)
        LocalDate currentDate = LocalDate.parse("2025-01-08");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
        
        // Verify no new rental was added
        assertEquals(1, customer.getRentals().size());
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new ArrayList<>());
        
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        Tape tape = new Tape();
        tape.setId("T004");
        
        // Create another customer C010 who has rented T004
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C010");
        
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setRentalDate(LocalDate.parse("2024-12-29"));
        otherRental.setDueDate(LocalDate.parse("2025-01-05"));
        otherRental.setReturnDate(null);
        
        List<VideoRental> otherRentals = new ArrayList<>();
        otherRentals.add(otherRental);
        otherCustomer.setRentals(otherRentals);
        RentalStore.addRental(otherRental);
        
        // Input: C004 rents tape "T004" with current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
        
        // Verify no new rental was added
        assertEquals(0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            rental.setRentalDate(LocalDate.parse("2024-12-20"));
            rental.setDueDate(LocalDate.parse("2024-12-27"));
            rental.setReturnDate(null);
            rental.setOwnedPastDueAmount(0.0);
            
            Tape tape = new Tape();
            tape.setId("T" + String.format("%03d", 500 + i));
            rental.setTape(tape);
            
            rentals.add(rental);
            RentalStore.addRental(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = new VideoRental();
        overdueRental.setRentalDate(LocalDate.parse("2024-12-20"));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31"));
        overdueRental.setReturnDate(null);
        overdueRental.setOwnedPastDueAmount(0.0);
        
        Tape overdueTape = new Tape();
        overdueTape.setId("T999");
        overdueRental.setTape(overdueTape);
        
        rentals.add(overdueRental);
        RentalStore.addRental(overdueRental);
        customer.setRentals(rentals);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        Tape tape = new Tape();
        tape.setId("T005");
        
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C011");
        
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setRentalDate(LocalDate.parse("2024-12-29"));
        otherRental.setDueDate(LocalDate.parse("2025-01-05"));
        otherRental.setReturnDate(null);
        
        List<VideoRental> otherRentals = new ArrayList<>();
        otherRentals.add(otherRental);
        otherCustomer.setRentals(otherRentals);
        RentalStore.addRental(otherRental);
        
        // Input: C005 rents tape "T005" with current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Expected Output: False
        assertFalse("Rental should fail when all conditions fail", result);
        
        // Verify no new rental was added
        assertEquals(21, customer.getRentals().size());
    }
}