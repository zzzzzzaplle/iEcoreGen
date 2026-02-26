import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private Customer customer;
    private VideoTape tape1;
    private VideoTape tape2;
    private Rental rental1;
    private Rental rental2;
    private VideoRentalSystem system;
    
    @Before
    public void setUp() {
        // Initialize common objects for tests
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Test Case 1: "No rentals exist"
        // Input: customer_id="C001"
        // Setup: Create Customer C001 with empty rentals list
        
        // Create customer with empty rentals list
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setIdCardNumber("ID001");
        customer.setName("Customer One");
        customer.setRentals(new ArrayList<Rental>());
        
        // Call method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify expected output: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Test Case 2: "All tapes returned"
        // Input: customer_id="C002"
        
        // Create customer
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setIdCardNumber("ID002");
        customer.setName("Customer Two");
        
        // Create tape
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Movie One");
        tape.setAvailable(true);
        
        // Create rental with return date (tape returned)
        Rental rental = new Rental();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01"); // Tape returned on same day
        rental.setRentalDays(4);
        
        // Add rental to customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Call method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify expected output: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty list", result.isEmpty());
        assertEquals("List size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Test Case 3: "One unreturned tape"
        // Input: customer_id="C003"
        
        // Create customer
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setIdCardNumber("ID003");
        customer.setName("Customer Three");
        
        // Create tape
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T001");
        tape.setTitle("Movie One");
        tape.setAvailable(true);
        
        // Create rental with no return date (tape not returned)
        Rental rental = new Rental();
        rental.setRentalId("R001");
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Tape not returned
        rental.setRentalDays(4);
        
        // Add rental to customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Call method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify expected output: List containing T001
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 1", 1, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertEquals("First element should be T001", "T001", result.get(0));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Test Case 4: "Mixed returned/unreturned"
        // Input: customer_id="C004"
        
        // Create customer
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        customer.setIdCardNumber("ID004");
        customer.setName("Customer Four");
        
        // Create tapes
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie One");
        tape1.setAvailable(true);
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie Two");
        tape2.setAvailable(true);
        
        // Create rental for T001 with return date (tape returned)
        Rental rental1 = new Rental();
        rental1.setRentalId("R001");
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01"); // Tape returned
        rental1.setRentalDays(4);
        
        // Create rental for T002 with no return date (tape not returned)
        Rental rental2 = new Rental();
        rental2.setRentalId("R002");
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape not returned
        rental2.setRentalDays(4);
        
        // Add rentals to customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Call method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify expected output: List containing T002
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 1", 1, result.size());
        assertTrue("List should contain T002", result.contains("T002"));
        assertFalse("List should not contain T001", result.contains("T001"));
        assertEquals("First element should be T002", "T002", result.get(0));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Test Case 5: "Multiple unreturned tapes"
        // Input: customer_id="C005"
        
        // Create customer
        Customer customer = new Customer();
        customer.setAccountNumber("C005");
        customer.setIdCardNumber("ID005");
        customer.setName("Customer Five");
        
        // Create tapes
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie One");
        tape1.setAvailable(true);
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie Two");
        tape2.setAvailable(true);
        
        // Create rental for T001 with no return date (tape not returned)
        Rental rental1 = new Rental();
        rental1.setRentalId("R001");
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Tape not returned
        rental1.setRentalDays(4);
        
        // Create rental for T002 with no return date (tape not returned)
        Rental rental2 = new Rental();
        rental2.setRentalId("R002");
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-02");
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null); // Tape not returned
        rental2.setRentalDays(4);
        
        // Add rentals to customer
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Call method under test
        List<String> result = customer.listUnreturnedTapes();
        
        // Verify expected output: List containing T001 and T002
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
        assertEquals("List size should be 2", 2, result.size());
        assertTrue("List should contain T001", result.contains("T001"));
        assertTrue("List should contain T002", result.contains("T002"));
        
        // Verify both tapes are in the list (order doesn't matter)
        assertTrue("List contains both expected tapes", 
                  result.contains("T001") && result.contains("T002"));
    }
}