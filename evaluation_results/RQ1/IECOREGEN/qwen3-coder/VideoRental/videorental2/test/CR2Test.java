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

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T002");
        
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-05 00:00:00");
        
        // Create rental with return_date = null (unreturned)
        VideoRental rental = VideorentalFactory.eINSTANCE.createVideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        tape.getRentals().add(rental);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape T002 should not be available when rented out with return_date null", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T003");
        
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-25 00:00:00");
        Date dueDate = dateFormat.parse("2024-12-30 00:00:00");
        Date returnDate = dateFormat.parse("2024-12-31 00:00:00");
        
        // Create rental with return_date set (returned)
        VideoRental rental = VideorentalFactory.eINSTANCE.createVideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate); // Returned
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        tape.getRentals().add(rental);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T004");
        
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        Date rentalDate = dateFormat.parse("2024-12-28 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Create rental with return_date = null (unreturned and overdue)
        VideoRental rental = VideorentalFactory.eINSTANCE.createVideoRental();
        rental.setDueDate(dueDate);
        rental.setReturnDate(null); // Unreturned and overdue
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setRentalDate(rentalDate);
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        customer.getTransactions().add(transaction);
        tape.getRentals().add(rental);
        
        // Test tape availability
        boolean result = tape.isAvailable(currentDate);
        
        // Verify
        assertFalse("Tape T004 should not be available when it has an overdue unreturned rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T005");
        
        Customer customer1 = VideorentalFactory.eINSTANCE.createCustomer();
        customer1.setId("C004");
        
        Customer customer2 = VideorentalFactory.eINSTANCE.createCustomer();
        customer2.setId("C005");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // First rental: returned
        Date rentalDate1 = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate1 = dateFormat.parse("2025-01-05 00:00:00");
        Date returnDate1 = dateFormat.parse("2025-01-01 00:00:00");
        
        VideoRental rental1 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental1.setDueDate(dueDate1);
        rental1.setReturnDate(returnDate1); // Returned
        rental1.setTape(tape);
        rental1.setCustomer(customer1);
        
        RentalTransaction transaction1 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction1.setRentalDate(rentalDate1);
        transaction1.setCustomer(customer1);
        transaction1.getRentals().add(rental1);
        rental1.setTransaction(transaction1);
        
        customer1.getRentals().add(rental1);
        customer1.getTransactions().add(transaction1);
        tape.getRentals().add(rental1);
        
        // Test availability after first rental (should be true since it was returned)
        boolean resultAfterFirstRental = tape.isAvailable(currentDate);
        assertTrue("Tape T005 should be available after first rental was returned", resultAfterFirstRental);
        
        // Second rental: unreturned
        Date rentalDate2 = dateFormat.parse("2025-01-06 00:00:00");
        Date dueDate2 = dateFormat.parse("2025-01-15 00:00:00");
        
        VideoRental rental2 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental2.setDueDate(dueDate2);
        rental2.setReturnDate(null); // Unreturned
        rental2.setTape(tape);
        rental2.setCustomer(customer2);
        
        RentalTransaction transaction2 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction2.setRentalDate(rentalDate2);
        transaction2.setCustomer(customer2);
        transaction2.getRentals().add(rental2);
        rental2.setTransaction(transaction2);
        
        customer2.getRentals().add(rental2);
        customer2.getTransactions().add(transaction2);
        tape.getRentals().add(rental2);
        
        // Test availability after second rental (should be false since currently rented out)
        boolean resultAfterSecondRental = tape.isAvailable(currentDate);
        assertFalse("Tape T005 should not be available when currently rented out with return_date null", resultAfterSecondRental);
    }
}