package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.VideorentalFactory;
import edu.videorental.VideorentalPackage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute & Verify
        assertTrue("Tape T001 should be available when no active rentals exist", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup
        Tape tape = factory.createTape();
        tape.setId("T002");
        
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null); // Unreturned
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute & Verify
        assertFalse("Tape T002 should be unavailable when actively rented", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup
        Tape tape = factory.createTape();
        tape.setId("T003");
        
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2024-12-30 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2024-12-31 00:00:00")); // Returned
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Execute & Verify
        assertTrue("Tape T003 should be available when previously rented but returned", 
                   tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup
        Tape tape = factory.createTape();
        tape.setId("T004");
        
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setReturnDate(null); // Unreturned and overdue
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Execute & Verify
        assertFalse("Tape T004 should be unavailable when overdue and unreturned", 
                    tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup
        Tape tape = factory.createTape();
        tape.setId("T005");
        
        Customer customer1 = factory.createCustomer();
        customer1.setId("C004");
        
        Customer customer2 = factory.createCustomer();
        customer2.setId("C005");
        
        // First rental - returned
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00")); // Returned early
        rental1.setTape(tape);
        rental1.setCustomer(customer1);
        
        customer1.getRentals().add(rental1);
        tape.getRentals().add(rental1);
        
        Date currentDateAfterFirstRental = dateFormat.parse("2025-01-02 00:00:00");
        
        // Verify first rental scenario
        assertTrue("Tape T005 should be available after first rental was returned", 
                   tape.isAvailable(currentDateAfterFirstRental));
        
        // Second rental - unreturned
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(null); // Unreturned
        rental2.setTape(tape);
        rental2.setCustomer(customer2);
        
        customer2.getRentals().add(rental2);
        tape.getRentals().add(rental2);
        
        Date currentDateAfterSecondRental = dateFormat.parse("2025-01-10 00:00:00");
        
        // Verify second rental scenario
        assertFalse("Tape T005 should be unavailable during second active rental", 
                    tape.isAvailable(currentDateAfterSecondRental));
    }
}