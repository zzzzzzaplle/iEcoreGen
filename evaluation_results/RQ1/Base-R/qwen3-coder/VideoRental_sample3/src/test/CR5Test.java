import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_noRentalsExist() {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setAccountId("C001");
        customer.setRentals(new ArrayList<>());
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Empty list, no active rentals
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase2_allTapesReturned() {
        // Setup: Create Customer C002 with returned rental
        Customer customer = new Customer();
        customer.setAccountId("C002");
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(LocalDate.of(2025, 1, 1));
        
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_oneUnreturnedTape() {
        // Setup: Create Customer C003 with one unreturned rental
        Customer customer = new Customer();
        customer.setAccountId("C003");
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental = new Rental();
        rental.setTapeId("T001");
        rental.setRentalDate(LocalDate.of(2025, 1, 1));
        rental.setDueDate(LocalDate.of(2025, 1, 5));
        rental.setReturnDate(null);
        
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: List containing T001
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertTrue("Should contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase4_mixedReturnedUnreturned() {
        // Setup: Create Customer C004 with mixed returned and unreturned rentals
        Customer customer = new Customer();
        customer.setAccountId("C004");
        List<Rental> rentals = new ArrayList<>();
        
        // Returned rental for T001
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 1));
        
        // Unreturned rental for T002
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null);
        
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: List containing T002 only
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly one tape", 1, result.size());
        assertTrue("Should contain T002", result.contains("T002"));
        assertFalse("Should not contain T001", result.contains("T001"));
    }
    
    @Test
    public void testCase5_multipleUnreturnedTapes() {
        // Setup: Create Customer C005 with multiple unreturned rentals
        Customer customer = new Customer();
        customer.setAccountId("C005");
        List<Rental> rentals = new ArrayList<>();
        
        // Unreturned rental for T001
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null);
        
        // Unreturned rental for T002
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 2));
        rental2.setDueDate(LocalDate.of(2025, 1, 6));
        rental2.setReturnDate(null);
        
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute: List unreturned tapes for customer
        List<String> result = rentalSystem.listUnreturnedTapes(customer);
        
        // Verify: List containing both T001 and T002
        assertNotNull("Result should not be null", result);
        assertEquals("Should contain exactly two tapes", 2, result.size());
        assertTrue("Should contain T001", result.contains("T001"));
        assertTrue("Should contain T002", result.contains("T002"));
    }
}