package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.VideorentalFactory;
import edu.videorental.VideorentalPackage;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.eclipse.emf.common.util.EList;

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
        // Create Customer C001 with empty rentals list
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        // Get unreturned tapes
        EList<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, no active rentals
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase2_AllTapesReturned() throws Exception {
        // Create Customer C002
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        // Create Tape T001
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Create VideoRental with return date set
        VideoRental rental = factory.createVideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 10:00:00");
        Date returnDate = dateFormat.parse("2025-01-01 10:00:00");
        
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        // Add rental to customer and tape
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Get unreturned tapes
        EList<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: Empty list, all rentals returned
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertTrue("Unreturned tapes list should be empty when all tapes are returned", unreturnedTapes.isEmpty());
    }
    
    @Test
    public void testCase3_OneUnreturnedTape() throws Exception {
        // Create Customer C003
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        // Create Tape T001
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Create VideoRental with return_date=null (unreturned)
        VideoRental rental = factory.createVideoRental();
        Date rentalDate = dateFormat.parse("2025-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 10:00:00");
        
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Tape not returned
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        // Add rental to customer and tape
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Get unreturned tapes
        EList<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T001", "T001", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase4_MixedReturnedUnreturned() throws Exception {
        // Create Customer C004
        Customer customer = factory.createCustomer();
        customer.setId("C004");
        
        // Create Tapes T001 and T002
        Tape tape1 = factory.createTape();
        tape1.setId("T001");
        Tape tape2 = factory.createTape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return date set (returned)
        VideoRental rental1 = factory.createVideoRental();
        Date rentalDate1 = dateFormat.parse("2025-01-01 10:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 10:00:00");
        Date returnDate1 = dateFormat.parse("2025-01-01 10:00:00");
        
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1);
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = factory.createVideoRental();
        Date rentalDate2 = dateFormat.parse("2025-01-02 10:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-06 10:00:00");
        
        rental2.setDueDate(dueDate2);
        rental2.setReturnDate(null); // Tape not returned
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        
        // Add rentals to customer and tapes
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Get unreturned tapes
        EList<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T002 only
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly one unreturned tape", 1, unreturnedTapes.size());
        assertEquals("Unreturned tape should be T002", "T002", unreturnedTapes.get(0).getId());
    }
    
    @Test
    public void testCase5_MultipleUnreturnedTapes() throws Exception {
        // Create Customer C005
        Customer customer = factory.createCustomer();
        customer.setId("C005");
        
        // Create Tapes T001 and T002
        Tape tape1 = factory.createTape();
        tape1.setId("T001");
        Tape tape2 = factory.createTape();
        tape2.setId("T002");
        
        // Create VideoRental for T001 with return_date=null (unreturned)
        VideoRental rental1 = factory.createVideoRental();
        Date rentalDate1 = dateFormat.parse("2025-01-01 10:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 10:00:00");
        
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(null); // Tape not returned
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        
        // Create VideoRental for T002 with return_date=null (unreturned)
        VideoRental rental2 = factory.createVideoRental();
        Date rentalDate2 = dateFormat.parse("2025-01-02 10:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-06 10:00:00");
        
        rental2.setDueDate(dueDate2);
        rental2.setReturnDate(null); // Tape not returned
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        
        // Add rentals to customer and tapes
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Get unreturned tapes
        EList<Tape> unreturnedTapes = customer.getUnreturnedTapes();
        
        // Verify: List containing T001 and T002
        assertNotNull("Unreturned tapes list should not be null", unreturnedTapes);
        assertEquals("Should have exactly two unreturned tapes", 2, unreturnedTapes.size());
        
        // Check that both tapes are in the list
        boolean foundT001 = false;
        boolean foundT002 = false;
        for (Tape tape : unreturnedTapes) {
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