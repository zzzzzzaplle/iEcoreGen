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
        // Reset static rentals list before each test
        VideoRental.getRentals().clear();
    }
    
    @Test
    public void testCase1_successfulRental() {
        // Setup
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C001 with 5 active rentals
        customer = new Customer();
        customer.setId("C001");
        
        List<VideoRental> customerRentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (10 + i));
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            customerRentals.add(rental);
        }
        customer.setRentals(customerRentals);
        
        // Create available Tape T001 with no active rentals
        tape = new Tape();
        tape.setId("T001");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when all conditions are met", result);
    }
    
    @Test
    public void testCase2_customerHas20Rentals() {
        // Setup
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C002 with 20 active rentals
        customer = new Customer();
        customer.setId("C002");
        
        List<VideoRental> customerRentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T00" + (20 + i));
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            customerRentals.add(rental);
        }
        customer.setRentals(customerRentals);
        
        // Create available Tape T002
        tape = new Tape();
        tape.setId("T002");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has maximum rentals", result);
    }
    
    @Test
    public void testCase3_customerHasOverdueFees() {
        // Setup
        currentDate = LocalDate.parse("2025-01-05", formatter);
        
        // Create Customer C003 with 1 overdue rental
        customer = new Customer();
        customer.setId("C003");
        
        List<VideoRental> customerRentals = new ArrayList<>();
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T030");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", formatter)); // 3 days overdue
        customerRentals.add(overdueRental);
        customer.setRentals(customerRentals);
        
        // Create available Tape T003
        tape = new Tape();
        tape.setId("T003");
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_tapeIsUnavailable() {
        // Setup
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C004 with 0 rentals
        customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental by another customer
        tape = new Tape();
        tape.setId("T004");
        
        // Create another customer and rent the tape
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C010");
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        List<VideoRental> otherRentals = new ArrayList<>();
        otherRentals.add(otherRental);
        otherCustomer.setRentals(otherRentals);
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_allConditionsFail() {
        // Setup
        currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        customer = new Customer();
        customer.setId("C005");
        
        List<VideoRental> customerRentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental rental = new VideoRental();
            Tape rentalTape = new Tape();
            rentalTape.setId("T05" + (10 + i));
            rental.setTape(rentalTape);
            rental.setDueDate(currentDate.plusDays(7));
            customerRentals.add(rental);
        }
        
        // Add 1 overdue rental
        VideoRental overdueRental = new VideoRental();
        Tape overdueTape = new Tape();
        overdueTape.setId("T059");
        overdueRental.setTape(overdueTape);
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter)); // Overdue
        customerRentals.add(overdueRental);
        
        customer.setRentals(customerRentals);
        
        // Create Tape T005 with active rental by another customer
        tape = new Tape();
        tape.setId("T005");
        
        // Create another customer and rent the tape
        Customer otherCustomer = new Customer();
        otherCustomer.setId("C011");
        VideoRental otherRental = new VideoRental();
        otherRental.setTape(tape);
        otherRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        List<VideoRental> otherRentals = new ArrayList<>();
        otherRentals.add(otherRental);
        otherCustomer.setRentals(otherRentals);
        
        // Execute
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions fail", result);
    }
}