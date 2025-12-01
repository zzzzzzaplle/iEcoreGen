package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.VideorentalFactory;
import edu.videorental.VideorentalPackage;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    private VideorentalFactory factory;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        factory = VideorentalFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C001 with 5 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = factory.createVideoRental();
            Date rentalDate = dateFormat.parse("2025-01-0" + i + " 00:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L); // 7 days later
            rental.setDueDate(dueDate);
            rental.setReturnDate(null); // unreturned
            rental.setCustomer(customer);
            
            // Create a tape for each rental
            Tape tape = factory.createTape();
            tape.setId("T00" + (10 + i));
            rental.setTape(tape);
        }
        
        // Create available Tape T001 with no active rentals
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C002 with 20 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = factory.createVideoRental();
            Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L); // 7 days later
            rental.setDueDate(dueDate);
            rental.setReturnDate(null); // unreturned
            rental.setCustomer(customer);
            
            // Create a tape for each rental
            Tape tape = factory.createTape();
            tape.setId("T00" + (20 + i));
            rental.setTape(tape);
        }
        
        // Create available Tape T002
        Tape tape = factory.createTape();
        tape.setId("T002");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 active rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00");
        
        // Create Customer C003 with 1 active rental that is overdue
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        // Create overdue rental (due date was 2025-01-02, current date is 2025-01-05 - 3 days overdue)
        VideoRental rental = factory.createVideoRental();
        Date dueDate = dateFormat.parse("2025-01-02 00:00:00");
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // unreturned
        rental.setCustomer(customer);
        
        // Create tape for the overdue rental
        Tape existingTape = factory.createTape();
        existingTape.setId("T030");
        rental.setTape(existingTape);
        
        // Create available Tape T003
        Tape newTape = factory.createTape();
        newTape.setId("T003");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(newTape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C004 with 0 rentals
        Customer customer = factory.createCustomer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental by another customer
        Tape tape = factory.createTape();
        tape.setId("T004");
        
        // Create another customer who has rented this tape
        Customer otherCustomer = factory.createCustomer();
        otherCustomer.setId("C010");
        
        // Create active rental for the other customer
        VideoRental existingRental = factory.createVideoRental();
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        existingRental.setDueDate(dueDate);
        existingRental.setReturnDate(null); // unreturned
        existingRental.setCustomer(otherCustomer);
        existingRental.setTape(tape);
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable (already rented by another customer)", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = factory.createCustomer();
        customer.setId("C005");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = factory.createVideoRental();
            Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7 * 24 * 60 * 60 * 1000L); // 7 days later
            rental.setDueDate(dueDate);
            rental.setReturnDate(null); // unreturned
            rental.setCustomer(customer);
            
            // Create a tape for each rental
            Tape tape = factory.createTape();
            tape.setId("T00" + (50 + i));
            rental.setTape(tape);
        }
        
        // Add one overdue rental (due date was yesterday)
        VideoRental overdueRental = factory.createVideoRental();
        Date overdueDueDate = dateFormat.parse("2024-12-31 00:00:00");
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setReturnDate(null); // unreturned
        overdueRental.setCustomer(customer);
        
        Tape overdueTape = factory.createTape();
        overdueTape.setId("T071");
        overdueRental.setTape(overdueTape);
        
        // Create Tape T005 with active rental by another customer
        Tape tape = factory.createTape();
        tape.setId("T005");
        
        Customer otherCustomer = factory.createCustomer();
        otherCustomer.setId("C011");
        
        VideoRental existingRental = factory.createVideoRental();
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        existingRental.setDueDate(dueDate);
        existingRental.setReturnDate(null); // unreturned
        existingRental.setCustomer(otherCustomer);
        existingRental.setTape(tape);
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions fail (max rentals + overdue fees + tape unavailable)", result);
    }
}