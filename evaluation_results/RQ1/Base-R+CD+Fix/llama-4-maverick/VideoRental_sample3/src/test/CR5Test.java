import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, no active rentals
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date set (returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Expected Output: Empty list, all rentals returned
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T001"));
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date set (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T002
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("T002"));
        assertFalse(result.contains("T001"));
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Retrieve unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Expected Output: List containing T001 and T002
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("T001"));
        assertTrue(result.contains("T002"));
    }
}