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
        // Clear the rental repository before each test to ensure isolation
        RentalRepository.getAllRentals().clear();
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        RentalRepository.getAllRentals().clear();
    }
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer("C001");
        
        // Execute the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: Empty list, no active rentals
        assertTrue("Result should be empty when customer has no rentals", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        // Setup:
        // 1. Create Customer C002
        Customer customer = new Customer("C002");
        
        // 2. Create Tape T001
        Tape tape = new Tape("T001", "Movie Title");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C002 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        customer.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Execute the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: Empty list, all rentals returned
        assertTrue("Result should be empty when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        // Setup:
        // 1. Create Customer C003
        Customer customer = new Customer("C003");
        
        // 2. Create Tape T001
        Tape tape = new Tape("T001", "Movie Title");
        
        // 3. Create VideoRental with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C003 and T001
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null);
        
        customer.getRentals().add(rental);
        RentalRepository.addRental(rental);
        
        // Execute the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T001
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T001", "T001", result.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        // Setup:
        // 1. Create Customer C004
        Customer customer = new Customer("C004");
        
        // 2. Create Tape T001 and T002
        Tape tape1 = new Tape("T001", "Movie Title 1");
        Tape tape2 = new Tape("T002", "Movie Title 2");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date="2025-01-01", associated with C004
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", formatter));
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C004
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Execute the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T002
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertEquals("Should contain T002", "T002", result.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        // Setup:
        // 1. Create Customer C005
        Customer customer = new Customer("C005");
        
        // 2. Create Tapes T001 and T002
        Tape tape1 = new Tape("T001", "Movie Title 1");
        Tape tape2 = new Tape("T002", "Movie Title 2");
        
        // 3. Create VideoRental for T001 with rental_date="2025-01-01", due_date="2025-01-05", 
        //    return_date=null, associated with C005
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setRentalStartDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null);
        
        // 4. Create VideoRental for T002 with rental_date="2025-01-02", due_date="2025-01-06", 
        //    return_date=null, associated with C005
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setRentalStartDate(LocalDate.parse("2025-01-02", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-06", formatter));
        rental2.setReturnDate(null);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        RentalRepository.addRental(rental1);
        RentalRepository.addRental(rental2);
        
        // Execute the method under test
        List<Tape> result = customer.getUnreturnedTapes();
        
        // Verify expected output: List containing T001 and T002
        assertEquals("Should contain exactly two tapes", 2, result.size());
        
        // Verify both tapes are present (order doesn't matter)
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : result) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            } else if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Should contain T001", foundT001);
        assertTrue("Should contain T002", foundT002);
    }
}