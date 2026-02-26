import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    @Test
    public void testCase1_NoRentalsExist() {
        // Create Customer C001 with empty rentals list
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify empty list is returned
        assertTrue("Expected empty list when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() {
        // Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with return date (returned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-01");
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify empty list is returned (all tapes returned)
        assertTrue("Expected empty list when all tapes are returned", result.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() {
        // Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = new Tape();
        tape.setId("T001");
        
        // Create VideoRental with null return date (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains T001
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T001 in unreturned list", result.contains("T001"));
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
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-01");
        
        // Create VideoRental for T002 with null return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains only T002
        assertEquals("Expected list size 1", 1, result.size());
        assertTrue("Expected T002 in unreturned list", result.contains("T002"));
        assertFalse("T001 should not be in unreturned list", result.contains("T001"));
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
        
        // Create VideoRental for T001 with null return date (unreturned)
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape1);
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        
        // Create VideoRental for T002 with null return date (unreturned)
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape2);
        rental2.setDueDate("2025-01-06");
        rental2.setReturnDate(null);
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Get unreturned tapes
        List<String> result = customer.getUnreturnedTapes();
        
        // Verify list contains both T001 and T002
        assertEquals("Expected list size 2", 2, result.size());
        assertTrue("Expected T001 in unreturned list", result.contains("T001"));
        assertTrue("Expected T002 in unreturned list", result.contains("T002"));
    }
}