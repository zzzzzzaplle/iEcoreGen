package edu.videorental.videorental2.test;

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
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C001 with 5 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tape = factory.createTape();
            tape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2025-01-0" + i + " 00:00:00");
            Date dueDate = new Date(rentalDate.getTime() + (7L * 24 * 60 * 60 * 1000));
            
            rental.setDueDate(dueDate);
            rental.setTape(tape);
            rental.setCustomer(customer);
            
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            transaction.getRentals().add(rental);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            customer.getTransactions().add(transaction);
            tape.getRentals().add(rental);
        }
        
        // Create available Tape T001 with no active rentals
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals, no past due, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C002 with 20 active rentals
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tempTape = factory.createTape();
            tempTape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
            Date dueDate = new Date(rentalDate.getTime() + (7L * 24 * 60 * 60 * 1000));
            
            rental.setDueDate(dueDate);
            rental.setTape(tempTape);
            rental.setCustomer(customer);
            
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            transaction.getRentals().add(rental);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            customer.getTransactions().add(transaction);
            tempTape.getRentals().add(rental);
        }
        
        // Create available Tape T002
        Tape tape = factory.createTape();
        tape.setId("T002");
        
        // Test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-05 00:00:00");
        
        // Create Customer C003 with 1 active rental that is overdue
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        // Create overdue rental (due date is 2025-01-02, current date is 2025-01-05 = 3 days overdue)
        VideoRental rental = factory.createVideoRental();
        Tape tempTape = factory.createTape();
        tempTape.setId("TEMP1");
        
        Date rentalDate = dateFormat.parse("2024-12-26 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-02 00:00:00"); // 3 days overdue from current date
        
        rental.setDueDate(dueDate);
        rental.setTape(tempTape);
        rental.setCustomer(customer);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        tempTape.getRentals().add(rental);
        
        // Create available Tape T003
        Tape tape = factory.createTape();
        tape.setId("T003");
        
        // Test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
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
        
        VideoRental existingRental = factory.createVideoRental();
        Date rentalDate = dateFormat.parse("2024-12-29 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        existingRental.setDueDate(dueDate);
        existingRental.setTape(tape);
        existingRental.setCustomer(otherCustomer);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(otherCustomer);
        transaction.getRentals().add(existingRental);
        existingRental.setTransaction(transaction);
        
        otherCustomer.getRentals().add(existingRental);
        otherCustomer.getTransactions().add(transaction);
        tape.getRentals().add(existingRental);
        
        // Test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() throws Exception {
        // Setup
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = factory.createCustomer();
        customer.setId("C005");
        
        // Create 20 active rentals
        for (int i = 1; i <= 20; i++) {
            VideoRental rental = factory.createVideoRental();
            Tape tempTape = factory.createTape();
            tempTape.setId("TEMP" + i);
            
            Date rentalDate = dateFormat.parse("2024-12-25 00:00:00");
            Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
            
            rental.setDueDate(dueDate);
            rental.setTape(tempTape);
            rental.setCustomer(customer);
            
            RentalTransaction transaction = factory.createRentalTransaction();
            transaction.setRentalDate(rentalDate);
            transaction.setCustomer(customer);
            transaction.getRentals().add(rental);
            rental.setTransaction(transaction);
            
            customer.getRentals().add(rental);
            customer.getTransactions().add(transaction);
            tempTape.getRentals().add(rental);
        }
        
        // Add one overdue rental
        VideoRental overdueRental = factory.createVideoRental();
        Tape overdueTape = factory.createTape();
        overdueTape.setId("OVERDUE");
        
        Date overdueDueDate = dateFormat.parse("2024-12-31 00:00:00"); // Overdue by 1 day
        
        overdueRental.setDueDate(overdueDueDate);
        overdueRental.setTape(overdueTape);
        overdueRental.setCustomer(customer);
        
        RentalTransaction overdueTransaction = factory.createRentalTransaction();
        overdueTransaction.setRentalDate(dateFormat.parse("2024-12-24 00:00:00"));
        overdueTransaction.setCustomer(customer);
        overdueTransaction.getRentals().add(overdueRental);
        overdueRental.setTransaction(overdueTransaction);
        
        customer.getRentals().add(overdueRental);
        customer.getTransactions().add(overdueTransaction);
        overdueTape.getRentals().add(overdueRental);
        
        // Create Tape T005 with active rental by another customer
        Tape tape = factory.createTape();
        tape.setId("T005");
        
        Customer otherCustomer = factory.createCustomer();
        otherCustomer.setId("C011");
        
        VideoRental existingRental = factory.createVideoRental();
        Date rentalDate = dateFormat.parse("2024-12-29 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        existingRental.setDueDate(dueDate);
        existingRental.setTape(tape);
        existingRental.setCustomer(otherCustomer);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(otherCustomer);
        transaction.getRentals().add(existingRental);
        existingRental.setTransaction(transaction);
        
        otherCustomer.getRentals().add(existingRental);
        otherCustomer.getTransactions().add(transaction);
        tape.getRentals().add(existingRental);
        
        // Test
        boolean result = customer.addVedioTapeRental(tape, currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
}