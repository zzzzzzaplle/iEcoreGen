import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Create Tape with id="T001"
        Tape tape = new Tape();
        tape.setId("T001");
        
        // No active rentals for T001
        // Current date: "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: True
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Create Tape with id="T002"
        Tape tape = new Tape();
        tape.setId("T002");
        
        // Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Current date: "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: False
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Create Tape with id="T003"
        Tape tape = new Tape();
        tape.setId("T003");
        
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30", formatter));
        rental.setReturnDate(LocalDate.parse("2024-12-31", formatter)); // Returned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Current date: "2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        
        // Expected Output: True
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Create Tape with id="T004"
        Tape tape = new Tape();
        tape.setId("T004");
        
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01", formatter));
        rental.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Current date: "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Expected Output: False
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Create Tape with id="T005"
        Tape tape = new Tape();
        tape.setId("T005");
        
        // Create Customer C004, C005
        Customer customer1 = new Customer();
        customer1.setId("C004");
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter)); // Returned
        
        List<VideoRental> rentals1 = new ArrayList<>();
        rentals1.add(rental1);
        customer1.setRentals(rentals1);
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals2 = new ArrayList<>();
        rentals2.add(rental2);
        customer2.setRentals(rentals2);
        
        // Current date: "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Expected Output: False (due to second unreturned rental)
        assertFalse(tape.isAvailable(currentDate));
    }
}