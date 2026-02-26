import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR5Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear RentalStore before each test to ensure isolation
        clearRentalStore();
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        clearRentalStore();
    }
    
    /**
     * Helper method to clear the RentalStore between tests
     */
    private void clearRentalStore() {
        // Use reflection to clear the ALL_RENTALS list
        try {
            java.lang.reflect.Field field = RentalStore.class.getDeclaredField("ALL_RENTALS");
            field.setAccessible(true);
            java.util.List<?> rentals = (java.util.List<?>) field.get(null);
            if (rentals instanceof java.util.ArrayList) {
                ((java.util.ArrayList<?>) rentals).clear();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear RentalStore", e);
        }
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Expected Output: Empty list, no active rentals
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Setup:
        // 1. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video 1");
        
        // 2. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C002 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        customer.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Expected Output: Empty list, all rentals returned
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Setup:
        // 1. Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        tape.setVideoInformation("Test Video 1");
        
        // 2. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C003 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        RentalStore.addRental(rental);
        
        // Expected Output: List containing T001
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Result should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Setup:
        // 1. Create Tape T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Test Video 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Test Video 2");
        
        // 2. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // 3. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C004
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalStore.addRental(rental1);
        RentalStore.addRental(rental2);
        
        // Expected Output: List containing T002
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly one tape", 1, result.size());
        assertEquals("Result should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        Customer customer = new Customer();
        customer.setId("C005");
        customer.setRentals(new java.util.ArrayList<VideoRental>());
        
        // Setup:
        // 1. Create Tapes T001 and T002
        Tape tape1 = new Tape();
        tape1.setId("T001");
        tape1.setVideoInformation("Test Video 1");
        
        Tape tape2 = new Tape();
        tape2.setId("T002");
        tape2.setVideoInformation("Test Video 2");
        
        // 2. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C005
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // 3. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C005
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalStore.addRental(rental1);
        RentalStore.addRental(rental2);
        
        // Expected Output: List containing T001 and T002
        List<Tape> result = customer.getUnreturnedTapes();
        assertNotNull("Result should not be null", result);
        assertEquals("Result should contain exactly two tapes", 2, result.size());
        
        // Check that both tapes are in the result
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Result should contain T001", foundT001);
        assertTrue("Result should contain T002", foundT002);
    }
}