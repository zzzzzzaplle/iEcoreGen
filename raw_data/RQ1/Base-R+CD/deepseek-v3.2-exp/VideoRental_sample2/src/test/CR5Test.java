import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.List;

public class CR5Test {
    
    private Customer customer;
    private Tape tape1;
    private Tape tape2;
    private VideoRental rental1;
    private VideoRental rental2;
    
    @Before
    public void setUp() {
        // Initialize common objects that may be used across tests
        customer = new Customer();
        tape1 = new Tape();
        tape2 = new Tape();
        rental1 = new VideoRental();
        rental2 = new VideoRental();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Arrange
        Customer customerC001 = new Customer();
        customerC001.setId("C001");
        customerC001.setRentals(new java.util.ArrayList<VideoRental>()); // Empty rentals list
        
        // Act
        List<Tape> result = customerC001.getUnreturnedTapes();
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup: Create Customer C002, Tape T001, VideoRental with return_date set
        
        // Arrange
        Customer customerC002 = new Customer();
        customerC002.setId("C002");
        
        Tape tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT001);
        rental.setDueDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));
        rental.setReturnDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0)); // Return date is set
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental);
        customerC002.setRentals(rentals);
        
        // Act
        List<Tape> result = customerC002.getUnreturnedTapes();
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup: Create Customer C003, Tape T001, VideoRental with return_date=null
        
        // Arrange
        Customer customerC003 = new Customer();
        customerC003.setId("C003");
        
        Tape tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        VideoRental rental = new VideoRental();
        rental.setTape(tapeT001);
        rental.setDueDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));
        rental.setReturnDate(null); // Not returned
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental);
        customerC003.setRentals(rentals);
        
        // Act
        List<Tape> result = customerC003.getUnreturnedTapes();
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup: Create Customer C004, Tapes T001 and T002, 
        //        VideoRental for T001 (returned), VideoRental for T002 (unreturned)
        
        // Arrange
        Customer customerC004 = new Customer();
        customerC004.setId("C004");
        
        Tape tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        Tape tapeT002 = new Tape();
        tapeT002.setId("T002");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tapeT001);
        rental1.setDueDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));
        rental1.setReturnDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0)); // Returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tapeT002);
        rental2.setDueDate(LocalDateTime.of(2025, 1, 6, 0, 0, 0));
        rental2.setReturnDate(null); // Not returned
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customerC004.setRentals(rentals);
        
        // Act
        List<Tape> result = customerC004.getUnreturnedTapes();
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup: Create Customer C005, Tapes T001 and T002,
        //        VideoRental for T001 (unreturned), VideoRental for T002 (unreturned)
        
        // Arrange
        Customer customerC005 = new Customer();
        customerC005.setId("C005");
        
        Tape tapeT001 = new Tape();
        tapeT001.setId("T001");
        
        Tape tapeT002 = new Tape();
        tapeT002.setId("T002");
        
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tapeT001);
        rental1.setDueDate(LocalDateTime.of(2025, 1, 5, 0, 0, 0));
        rental1.setReturnDate(null); // Not returned
        
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tapeT002);
        rental2.setDueDate(LocalDateTime.of(2025, 1, 6, 0, 0, 0));
        rental2.setReturnDate(null); // Not returned
        
        List<VideoRental> rentals = new java.util.ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customerC005.setRentals(rentals);
        
        // Act
        List<Tape> result = customerC005.getUnreturnedTapes();
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        
        // Verify both T001 and T002 are in the result
        boolean containsT001 = false;
        boolean containsT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                containsT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                containsT002 = true;
            }
        }
        assertTrue("Result should contain T001", containsT001);
        assertTrue("Result should contain T002", containsT002);
    }
}