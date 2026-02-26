import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws Exception {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Call the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date set
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = dateFormat.parse("2025-01-05");
        Date returnDate = dateFormat.parse("2025-01-01");
        
        VideoRental rental = new VideoRental(tape, dueDate);
        rental.setReturnDate(returnDate);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Call the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null
        Date rentalDate = dateFormat.parse("2025-01-01");
        Date dueDate = dateFormat.parse("2025-01-05");
        
        VideoRental rental = new VideoRental(tape, dueDate);
        // return_date remains null by default
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Call the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Tape ID should be T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date set
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        Date returnDate1 = dateFormat.parse("2025-01-01");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        rental1.setReturnDate(returnDate1);
        
        // Create VideoRental for T002 with return_date=null
        Date rentalDate2 = dateFormat.parse("2025-01-02");
        Date dueDate2 = dateFormat.parse("2025-01-06");
        
        VideoRental rental2 = new VideoRental(tape2, dueDate2);
        // return_date remains null by default
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Call the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Tape ID should be T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Create Customer C005
        Customer customer = new Customer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Tape tape2 = new Tape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date dueDate1 = dateFormat.parse("2025-01-05");
        
        VideoRental rental1 = new VideoRental(tape1, dueDate1);
        // return_date remains null by default
        
        // Create VideoRental for T002 with return_date=null
        Date rentalDate2 = dateFormat.parse("2025-01-02");
        Date dueDate2 = dateFormat.parse("2025-01-06");
        
        VideoRental rental2 = new VideoRental(tape2, dueDate2);
        // return_date remains null by default
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Call the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        
        // Check that both T001 and T002 are in the result
        List<String> tapeIds = new ArrayList<>();
        for (Tape tape : result) {
            tapeIds.add(tape.getId());
        }
        
        assertTrue("Result should contain T001", tapeIds.contains("T001"));
        assertTrue("Result should contain T002", tapeIds.contains("T002"));
    }
}