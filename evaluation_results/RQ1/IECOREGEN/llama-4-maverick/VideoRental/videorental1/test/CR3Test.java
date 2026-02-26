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

public class CR3Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SuccessfulRental() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        
        // Create Customer C001 with 5 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tape = factory.createTape();
            tape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2025-01-0" + i + " 10:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7L * 24 * 60 * 60 * 1000);
            
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setTape(tape);
            
            // Create transaction for each rental
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            tape.getRentals().add(rental);
        }
        
        // Create available Tape T001 with no active rentals
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when all conditions are met", result);
        assertEquals("Customer should have 6 rentals after successful rental", 6, customer.getRentals().size());
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        
        // Create Customer C002 with 20 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tempTape = factory.createTape();
            tempTape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2025-01-01 10:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7L * 24 * 60 * 60 * 1000);
            
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setTape(tempTape);
            
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            tempTape.getRentals().add(rental);
        }
        
        // Create available Tape T002
        Tape tape = factory.createTape();
        tape.setId("T002");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 active rentals", result);
        assertEquals("Customer should still have 20 rentals after failed rental", 20, customer.getRentals().size());
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-05 10:00:00");
        
        // Create Customer C003 with 1 active rental that is overdue
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        // Create overdue rental (due 3 days ago)
        VideoRental overdueRental = factory.createVideoRental();
        Tape tempTape = factory.createTape();
        tempTape.setId("TEMP001");
        
        Date rentalDate = dateFormat.parse("2024-12-20 10:00:00");
        Date dueDate = dateFormat.parse("2025-01-02 10:00:00"); // 3 days overdue from current date
        
        overdueRental.setDueDate(dueDate);
        overdueRental.setReturnDate(null);
        overdueRental.setTape(tempTape);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(customer);
        overdueRental.setTransaction(transaction);
        
        customer.getRentals().add(overdueRental);
        tempTape.getRentals().add(overdueRental);
        
        // Create available Tape T003
        Tape tape = factory.createTape();
        tape.setId("T003");
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
        assertEquals("Customer should still have 1 rental after failed rental", 1, customer.getRentals().size());
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        
        // Create Customer C004 with 0 rentals
        Customer customer = factory.createCustomer();
        customer.setId("C004");
        
        // Create Tape T004 with active rental by another customer
        Tape tape = factory.createTape();
        tape.setId("T004");
        
        // Create another customer who currently rents this tape
        Customer otherCustomer = factory.createCustomer();
        otherCustomer.setId("C010");
        
        VideoRental activeRental = factory.createVideoRental();
        Date rentalDate = dateFormat.parse("2024-12-29 10:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 10:00:00");
        
        activeRental.setDueDate(dueDate);
        activeRental.setReturnDate(null); // Tape is still rented
        activeRental.setTape(tape);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(otherCustomer);
        activeRental.setTransaction(transaction);
        
        otherCustomer.getRentals().add(activeRental);
        tape.getRentals().add(activeRental);
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
        assertEquals("Customer should have 0 rentals after failed rental", 0, customer.getRentals().size());
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 10:00:00");
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = factory.createCustomer();
        customer.setId("C005");
        
        // Create 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tempTape = factory.createTape();
            tempTape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2024-12-20 10:00:00");
            Date dueDate = new Date(rentalDate.getTime() + 7L * 24 * 60 * 60 * 1000);
            
            rental.setDueDate(dueDate);
            rental.setReturnDate(null);
            rental.setTape(tempTape);
            
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            tempTape.getRentals().add(rental);
        }
        
        // Create one overdue rental
        VideoRental overdueRental = factory.createVideoRental();
        Tape overdueTape = factory.createTape();
        overdueTape.setId("OVERDUE_TAPE");
        
        Date overdueRentalDate = dateFormat.parse("2024-12-20 10:00:00");
        Date overdueDueDate = dateFormat.parse("2024-12-31 10:00:00"); // Overdue by 1 day
        
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setReturnDate(null);
        overdueRental.setTape(overdueTape);
        
        RentalTransaction overdueTransaction = factory.createRentalTransaction();
        overdueTransaction.setRentalDate(overdueRentalDate);
        overdueTransaction.setCustomer(customer);
        overdueRental.setTransaction(overdueTransaction);
        
        customer.getRentals().add(overdueRental);
        overdueTape.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental by another customer
        Tape tape = factory.createTape();
        tape.setId("T005");
        
        Customer otherCustomer = factory.createCustomer();
        otherCustomer.setId("C011");
        
        VideoRental activeRental = factory.createVideoRental();
        Date otherRentalDate = dateFormat.parse("2024-12-29 10:00:00");
        Date otherDueDate = dateFormat.parse("2025-01-05 10:00:00");
        
        activeRental.setDueDate(otherDueDate);
        activeRental.setReturnDate(null);
        activeRental.setTape(tape);
        
        RentalTransaction otherTransaction = factory.createRentalTransaction();
        otherTransaction.setRentalDate(otherRentalDate);
        otherTransaction.setCustomer(otherCustomer);
        activeRental.setTransaction(otherTransaction);
        
        otherCustomer.getRentals().add(activeRental);
        tape.getRentals().add(activeRental);
        
        // Execute test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions fail", result);
        assertEquals("Customer should still have 20 rentals after failed rental", 20, customer.getRentals().size());
    }
}