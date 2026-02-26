import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Tape tape;
    private Customer customer;
    private VideoRental rental;
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        RentalSystem.customers = new ArrayList<>();
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001"
        tape = new Tape();
        tape.setId("T001");
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Expected Output: True
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002"
        tape = new Tape();
        tape.setId("T002");
        
        // Create Customer C001
        customer = new Customer();
        customer.setId("C001");
        RentalSystem.customers.add(customer);
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        // return_date remains null (unreturned)
        customer.getRentals().add(rental);
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Expected Output: False
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003"
        tape = new Tape();
        tape.setId("T003");
        
        // Create Customer C002
        customer = new Customer();
        customer.setId("C002");
        RentalSystem.customers.add(customer);
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2024-12-30"));
        rental.setReturnDate(LocalDate.parse("2024-12-31"));
        customer.getRentals().add(rental);
        
        // Input: current_date="2025-01-01"
        LocalDate currentDate = LocalDate.parse("2025-01-01");
        
        // Expected Output: True
        assertTrue(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004"
        tape = new Tape();
        tape.setId("T004");
        
        // Create Customer C003
        customer = new Customer();
        customer.setId("C003");
        RentalSystem.customers.add(customer);
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null
        rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-01"));
        // return_date remains null (unreturned)
        customer.getRentals().add(rental);
        
        // Input: current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Expected Output: False
        assertFalse(tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005"
        tape = new Tape();
        tape.setId("T005");
        
        // Create Customer C004
        Customer customer1 = new Customer();
        customer1.setId("C004");
        RentalSystem.customers.add(customer1);
        
        // Create Customer C005
        Customer customer2 = new Customer();
        customer2.setId("C005");
        RentalSystem.customers.add(customer2);
        
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-01"));
        customer1.getRentals().add(rental1);
        
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        // return_date remains null
        customer2.getRentals().add(rental2);
        
        // Input: current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Expected Output: False (second rental is active and unreturned)
        assertFalse(tape.isAvailable(currentDate));
    }
}