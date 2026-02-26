package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.RentalTransaction;
import edu.videorental.VideorentalFactory;
import edu.videorental.VideorentalPackage;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR5Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoRentalsExist() throws Exception {
        // Setup: Create Customer C001 with empty rentals list
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Setup: Create Customer C002
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = factory.createTape();
        tape.setId("T001");
        tape.setVideoInformation("Movie A");
        
        // Create VideoRental with return date
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setTape(tape);
        
        // Create RentalTransaction
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.setCustomer(customer);
        rental.setTransaction(transaction);
        
        // Associate rental with customer and tape
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty when all tapes are returned", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Setup: Create Customer C003
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = factory.createTape();
        tape.setId("T001");
        tape.setVideoInformation("Movie B");
        
        // Create VideoRental without return date (unreturned)
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Not returned
        rental.setTape(tape);
        
        // Create RentalTransaction
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.setCustomer(customer);
        rental.setTransaction(transaction);
        
        // Associate rental with customer and tape
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Setup: Create Customer C004
        Customer customer = factory.createCustomer();
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = factory.createTape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie C");
        
        Tape tape2 = factory.createTape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie D");
        
        // Create VideoRental for T001 with return date (returned)
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00")); // Returned
        rental1.setTape(tape1);
        
        // Create VideoRental for T002 without return date (unreturned)
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Not returned
        rental2.setTape(tape2);
        
        // Create RentalTransaction for both rentals
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.setCustomer(customer);
        rental1.setTransaction(transaction);
        rental2.setTransaction(transaction);
        
        // Associate rentals with customer and tapes
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Setup: Create Customer C005
        Customer customer = factory.createCustomer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = factory.createTape();
        tape1.setId("T001");
        tape1.setVideoInformation("Movie E");
        
        Tape tape2 = factory.createTape();
        tape2.setId("T002");
        tape2.setVideoInformation("Movie F");
        
        // Create VideoRental for T001 without return date (unreturned)
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(null); // Not returned
        rental1.setTape(tape1);
        
        // Create VideoRental for T002 without return date (unreturned)
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental2.setReturnDate(null); // Not returned
        rental2.setTape(tape2);
        
        // Create RentalTransaction for both rentals
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.setCustomer(customer);
        rental1.setTransaction(transaction);
        rental2.setTransaction(transaction);
        
        // Associate rentals with customer and tapes
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Execute: Get unreturned tapes
        List<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly two unreturned tapes", 2, unreturnedTapes.size());
        
        // Check that both tapes are in the list
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : unreturnedTapes) {
            if ("T001".equals(tape.getId())) {
                foundT001 = true;
            }
            if ("T002".equals(tape.getId())) {
                foundT002 = true;
            }
        }
        assertTrue("Should contain T001", foundT001);
        assertTrue("Should contain T002", foundT002);
    }
}