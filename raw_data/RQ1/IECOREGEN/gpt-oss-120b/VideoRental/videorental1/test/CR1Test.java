package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import edu.videorental.Customer;
import edu.videorental.Tape;
import edu.videorental.VideoRental;
import edu.videorental.VideorentalFactory;
import edu.videorental.VideorentalPackage;

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
        Customer customer = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        
        // Set rental dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00"); // 7 days later
        Date returnDate = dateFormat.parse("2025-01-09 00:00:00"); // 1 day late
        
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Link rental to customer
        customer.getRentals().add(rental);
        rental.setCustomer(customer);
        
        // Test calculation with current date (not used since return date is set)
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 1 day * $0.5 = $0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        
        // Set rental dates - due date is 2025-01-08
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00"); // 7 days later
        
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        // Link rental to customer
        customer.getRentals().add(rental);
        rental.setCustomer(customer);
        
        // Test calculation with current date 4 days after due date
        Date currentDate = dateFormat.parse("2025-01-12 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 4 days * $0.5 = $2.00
        assertEquals(2.00, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        
        // Set rental dates
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-06 00:00:00"); // 5 days later
        Date returnDate = dateFormat.parse("2025-01-06 00:00:00"); // returned on due date
        
        rental.setDueDate(dueDate);
        rental.setReturnDate(returnDate);
        
        // Link rental to customer
        customer.getRentals().add(rental);
        rental.setCustomer(customer);
        
        // Test calculation with current date
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: returned on due date = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        
        // Set rental dates - due date is 2025-01-10
        Date rentalDate = dateFormat.parse("2025-01-05 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-10 00:00:00"); // 5 days later
        
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        // Link rental to customer
        customer.getRentals().add(rental);
        rental.setCustomer(customer);
        
        // Test calculation with current date before due date
        Date currentDate = dateFormat.parse("2025-01-06 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: not overdue = $0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedTwoDaysLate() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        VideoRental rental = factory.createVideoRental();
        
        // Set rental dates - due date is 2025-01-08
        Date rentalDate = dateFormat.parse("2025-01-01 00:00:00");
        Date dueDate = dateFormat.parse("2025-01-08 00:00:00"); // 7 days later
        
        rental.setDueDate(dueDate);
        // returnDate remains null (unreturned)
        
        // Link rental to customer
        customer.getRentals().add(rental);
        rental.setCustomer(customer);
        
        // Test calculation with current date 2 days after due date
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        double result = rental.calculateOwnedPastDueAmount(currentDate);
        
        // Expected: 2 days * $0.5 = $1.00
        assertEquals(1.00, result, 0.001);
    }
}