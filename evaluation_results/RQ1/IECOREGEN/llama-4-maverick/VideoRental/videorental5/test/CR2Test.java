package edu.videorental.videorental5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.videorental.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR2Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup: Create Tape with id="T001", no active rentals
        Tape tape = factory.createTape();
        tape.setId("T001");
        
        // Input: current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup: Create Tape with id="T002", Customer C001 rents T002 (unreturned)
        Tape tape = factory.createTape();
        tape.setId("T002");
        
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        VideoRental rental = factory.createVideoRental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        // Set rental dates: rental date="2024-12-28", due_date="2025-01-05", return_date=null
        rental.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental.setReturnDate(null);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2024-12-28 00:00:00"));
        transaction.setCustomer(customer);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Input: current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: False
        assertFalse("Tape T002 should not be available (rented out)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup: Create Tape with id="T003", Customer C002 rents T003 and returns it
        Tape tape = factory.createTape();
        tape.setId("T003");
        
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        VideoRental rental = factory.createVideoRental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        // Set rental dates: rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        rental.setDueDate(dateFormat.parse("2024-12-30 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2024-12-31 00:00:00"));
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2024-12-25 00:00:00"));
        transaction.setCustomer(customer);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Input: current_date="2025-01-01"
        Date currentDate = dateFormat.parse("2025-01-01 00:00:00");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available (returned)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup: Create Tape with id="T004", Customer C003 rents T004 (overdue, unreturned)
        Tape tape = factory.createTape();
        tape.setId("T004");
        
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        VideoRental rental = factory.createVideoRental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        
        // Set rental dates: rental date="2024-12-28", due_date="2025-01-01", return_date=null
        rental.setDueDate(dateFormat.parse("2025-01-01 00:00:00"));
        rental.setReturnDate(null);
        
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2024-12-28 00:00:00"));
        transaction.setCustomer(customer);
        rental.setTransaction(transaction);
        
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Input: current_date="2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Expected Output: False
        assertFalse("Tape T004 should not be available (overdue rental)", tape.isAvailable(currentDate));
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup: Create Tape with id="T005", multiple customers rent and return
        Tape tape = factory.createTape();
        tape.setId("T005");
        
        // First rental: C004 rents T005 and returns early
        Customer customer1 = factory.createCustomer();
        customer1.setId("C004");
        
        VideoRental rental1 = factory.createVideoRental();
        rental1.setTape(tape);
        rental1.setCustomer(customer1);
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-01 00:00:00")); // Returned early
        
        RentalTransaction transaction1 = factory.createRentalTransaction();
        transaction1.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction1.setCustomer(customer1);
        rental1.setTransaction(transaction1);
        
        customer1.getRentals().add(rental1);
        tape.getRentals().add(rental1);
        
        // Input: current_date="2025-01-10"
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // After first rental: Tape should be available (returned)
        assertTrue("Tape T005 should be available after first rental return", tape.isAvailable(currentDate));
        
        // Second rental: C005 rents T005 (unreturned)
        Customer customer2 = factory.createCustomer();
        customer2.setId("C005");
        
        VideoRental rental2 = factory.createVideoRental();
        rental2.setTape(tape);
        rental2.setCustomer(customer2);
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(null); // Not returned
        
        RentalTransaction transaction2 = factory.createRentalTransaction();
        transaction2.setRentalDate(dateFormat.parse("2025-01-06 00:00:00"));
        transaction2.setCustomer(customer2);
        rental2.setTransaction(transaction2);
        
        customer2.getRentals().add(rental2);
        tape.getRentals().add(rental2);
        
        // After second rental: Tape should not be available (currently rented)
        assertFalse("Tape T005 should not be available during second rental", tape.isAvailable(currentDate));
    }
}