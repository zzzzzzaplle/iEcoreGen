package edu.videorental.videorental3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.videorental.Customer;
import edu.videorental.VideoRental;
import edu.videorental.Tape;
import edu.videorental.RentalTransaction;
import edu.videorental.VideorentalFactory;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Setup
        Customer c1 = factory.createCustomer();
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        c1.getTransactions().add(transaction);
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-08 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-09 00:00:00"));
        rental.setTransaction(transaction);
        c1.getRentals().add(rental);
        
        // Test calculation with current date after return
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 1 day late * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = factory.createCustomer();
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        c2.getTransactions().add(transaction);
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-08 00:00:00"));
        // returnDate remains null (unreturned)
        rental.setTransaction(transaction);
        c2.getRentals().add(rental);
        
        // Test calculation with current date 4 days after due date
        Date currentDate = dateFormat.parse("2025-01-12 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 4 days overdue * $0.5 = $2.00
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = factory.createCustomer();
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        c3.getTransactions().add(transaction);
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-06 00:00:00"));
        rental.setReturnDate(dateFormat.parse("2025-01-06 00:00:00")); // Returned on due date
        rental.setTransaction(transaction);
        c3.getRentals().add(rental);
        
        // Test calculation
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: Returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = factory.createCustomer();
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-05 00:00:00"));
        c4.getTransactions().add(transaction);
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-10 00:00:00"));
        // returnDate remains null (unreturned)
        rental.setTransaction(transaction);
        c4.getRentals().add(rental);
        
        // Test calculation with current date before due date
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: Not overdue yet = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedTwoDaysLate() throws Exception {
        // Setup
        Customer c5 = factory.createCustomer();
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        c5.getTransactions().add(transaction);
        
        VideoRental rental = factory.createVideoRental();
        rental.setDueDate(dateFormat.parse("2025-01-08 00:00:00"));
        // returnDate remains null (unreturned)
        rental.setTransaction(transaction);
        c5.getRentals().add(rental);
        
        // Test calculation with current date 2 days after due date
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 2 days overdue * $0.5 = $1.00
        assertEquals(1.00, result, 0.001);
    }
}