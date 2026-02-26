package edu.videorental.videorental2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.RentalTransaction;
import edu.videorental.VideorentalFactory;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    private VideorentalFactory factory;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        factory = VideorentalFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() throws Exception {
        // Setup
        Customer c1 = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        RentalTransaction transaction = factory.createRentalTransaction();
        
        // Set dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00");
        Date returnDate = dateFormat.parse("2025-01-09 00:00:00");
        
        // Configure rental
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Configure transaction
        transaction.setRentalDate(rentalDate);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        // Add to customer
        c1.getRentals().add(rental);
        rental.setCustomer(c1);
        
        // Test
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Assert
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer c2 = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        RentalTransaction transaction = factory.createRentalTransaction();
        
        // Set dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00");
        
        // Configure rental - not returned (returnDate = null)
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Configure transaction
        transaction.setRentalDate(rentalDate);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        // Add to customer
        c2.getRentals().add(rental);
        rental.setCustomer(c2);
        
        // Test with current date 4 days after due date
        Date currentDate = dateFormat.parse("2025-01-12 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Assert - 4 days overdue (Jan 9, 10, 11, 12) = 4 * $0.50 = $2.00
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer c3 = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        RentalTransaction transaction = factory.createRentalTransaction();
        
        // Set dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-06 00:00:00");
        
        // Configure rental - returned on due date
        rental.setDueDate(dueDate);
        rental.setReturnDate(dueDate); // Returned exactly on due date
        
        // Configure transaction
        transaction.setRentalDate(rentalDate);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        // Add to customer
        c3.getRentals().add(rental);
        rental.setCustomer(c3);
        
        // Test
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Assert - returned on due date, no overdue
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer c4 = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        RentalTransaction transaction = factory.createRentalTransaction();
        
        // Set dates
        Date rentalDate = dateFormat.parse("2025-01-05 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Configure rental - not returned but not overdue yet
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Configure transaction
        transaction.setRentalDate(rentalDate);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        // Add to customer
        c4.getRentals().add(rental);
        rental.setCustomer(c4);
        
        // Test with current date before due date
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Assert - not overdue yet
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() throws Exception {
        // Setup
        Customer c5 = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        RentalTransaction transaction = factory.createRentalTransaction();
        
        // Set dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00");
        
        // Configure rental - not returned, 2 days overdue
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        
        // Configure transaction
        transaction.setRentalDate(rentalDate);
        transaction.getRentals().add(rental);
        rental.setTransaction(transaction);
        
        // Add to customer
        c5.getRentals().add(rental);
        rental.setCustomer(c5);
        
        // Test with current date 2 days after due date
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Assert - 2 days overdue (Jan 9, 10) = 2 * $0.50 = $1.00
        assertEquals(1.00, result, 0.001);
    }
}