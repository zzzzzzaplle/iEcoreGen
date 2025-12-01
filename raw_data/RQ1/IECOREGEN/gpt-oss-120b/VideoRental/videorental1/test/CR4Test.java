package edu.videorental.videorental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.videorental.*;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private VideorentalFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = VideorentalFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: returned early, no overdue
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03 00:00:00"));
        
        // Create Rental 2: returned early, no overdue
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Create transaction and add rentals
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Set bidirectional references
        rental1.setTransaction(transaction);
        rental2.setTransaction(transaction);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(currentDate, transaction.getRentalDate());
        
        // Verify expected output: (2 + 0) + (11 + 0) = 13.00
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12 00:00:00"));
        
        // Create transaction and add rental
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.getRentals().add(rental1);
        
        // Set bidirectional reference
        rental1.setTransaction(transaction);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(currentDate, transaction.getRentalDate());
        
        // Verify expected output: 11 (base fee) + 3.50 (overdue) = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09 00:00:00"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18 00:00:00"));
        
        // Create transaction and add rentals
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Set bidirectional references
        rental1.setTransaction(transaction);
        rental2.setTransaction(transaction);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(currentDate, transaction.getRentalDate());
        
        // Verify expected output: (8+8 base fees) + (2+1.5 overdue) = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-20 00:00:00");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07 00:00:00"));
        
        // Create Rental 2: on-time
        VideoRental rental2 = factory.createVideoRental();
        rental2.setDueDate(dateFormat.parse("2025-01-15 00:00:00"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14 00:00:00"));
        
        // Create transaction and add rentals
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Set bidirectional references
        rental1.setTransaction(transaction);
        rental2.setTransaction(transaction);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(currentDate, transaction.getRentalDate());
        
        // Verify expected output: (6+4 base) + 1 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup
        Customer customer = factory.createCustomer();
        customer.setId("C006");
        
        Date currentDate = dateFormat.parse("2025-01-10 00:00:00");
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = factory.createVideoRental();
        rental1.setDueDate(dateFormat.parse("2025-01-05 00:00:00"));
        // returnDate is null (unreturned)
        
        // Create transaction and add rental
        RentalTransaction transaction = factory.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01 00:00:00"));
        transaction.getRentals().add(rental1);
        
        // Set bidirectional reference
        rental1.setTransaction(transaction);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(currentDate, transaction.getRentalDate());
        
        // Verify expected output: 9 (base fee) + 2.50 (overdue) = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}