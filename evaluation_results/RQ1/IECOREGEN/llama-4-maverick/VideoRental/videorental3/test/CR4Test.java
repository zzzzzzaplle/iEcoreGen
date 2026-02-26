package edu.videorental.videorental3.test;

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

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    private Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateStr, e);
        }
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C001");
        
        Tape tape1 = VideorentalFactory.eINSTANCE.createTape();
        tape1.setId("T001");
        Tape tape2 = VideorentalFactory.eINSTANCE.createTape();
        tape2.setId("T002");
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(parseDate("2025-01-01 00:00:00"));
        
        VideoRental rental1 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setTransaction(transaction);
        rental1.setDueDate(parseDate("2025-01-05 00:00:00"));
        rental1.setReturnDate(parseDate("2025-01-03 00:00:00"));
        
        VideoRental rental2 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setTransaction(transaction);
        rental2.setDueDate(parseDate("2025-01-15 00:00:00"));
        rental2.setReturnDate(parseDate("2025-01-12 00:00:00"));
        
        // Associate rentals with transaction, customer and tapes
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-01 00:00:00"));
        
        // Verify
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C002");
        
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T003");
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(parseDate("2025-01-01 00:00:00"));
        
        VideoRental rental = VideorentalFactory.eINSTANCE.createVideoRental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setTransaction(transaction);
        rental.setDueDate(parseDate("2025-01-05 00:00:00"));
        rental.setReturnDate(parseDate("2025-01-12 00:00:00"));
        
        // Associate rental with transaction, customer and tape
        transaction.getRentals().add(rental);
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-01 00:00:00"));
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C003");
        
        Tape tape1 = VideorentalFactory.eINSTANCE.createTape();
        tape1.setId("T004");
        Tape tape2 = VideorentalFactory.eINSTANCE.createTape();
        tape2.setId("T005");
        
        RentalTransaction transaction1 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction1.setCustomer(customer);
        transaction1.setRentalDate(parseDate("2025-01-01 00:00:00"));
        
        RentalTransaction transaction2 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction2.setCustomer(customer);
        transaction2.setRentalDate(parseDate("2025-01-10 00:00:00"));
        
        VideoRental rental1 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setTransaction(transaction1);
        rental1.setDueDate(parseDate("2025-01-05 00:00:00"));
        rental1.setReturnDate(parseDate("2025-01-09 00:00:00"));
        
        VideoRental rental2 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setTransaction(transaction2);
        rental2.setDueDate(parseDate("2025-01-15 00:00:00"));
        rental2.setReturnDate(parseDate("2025-01-18 00:00:00"));
        
        // Associate rentals with transactions, customer and tapes
        transaction1.getRentals().add(rental1);
        transaction2.getRentals().add(rental2);
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Execute - Test transaction1
        double totalPrice1 = transaction1.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-01 00:00:00"));
        
        // Execute - Test transaction2  
        double totalPrice2 = transaction2.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-10 00:00:00"));
        
        // Verify combined total
        assertEquals(19.50, totalPrice1 + totalPrice2, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C004");
        
        Tape tape1 = VideorentalFactory.eINSTANCE.createTape();
        tape1.setId("T006");
        Tape tape2 = VideorentalFactory.eINSTANCE.createTape();
        tape2.setId("T007");
        
        RentalTransaction transaction1 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction1.setCustomer(customer);
        transaction1.setRentalDate(parseDate("2025-01-01 00:00:00"));
        
        RentalTransaction transaction2 = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction2.setCustomer(customer);
        transaction2.setRentalDate(parseDate("2025-01-10 00:00:00"));
        
        VideoRental rental1 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setTransaction(transaction1);
        rental1.setDueDate(parseDate("2025-01-05 00:00:00"));
        rental1.setReturnDate(parseDate("2025-01-07 00:00:00"));
        
        VideoRental rental2 = VideorentalFactory.eINSTANCE.createVideoRental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setTransaction(transaction2);
        rental2.setDueDate(parseDate("2025-01-15 00:00:00"));
        rental2.setReturnDate(parseDate("2025-01-14 00:00:00"));
        
        // Associate rentals with transactions, customer and tapes
        transaction1.getRentals().add(rental1);
        transaction2.getRentals().add(rental2);
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        tape1.getRentals().add(rental1);
        tape2.getRentals().add(rental2);
        
        // Execute - Test transaction1
        double totalPrice1 = transaction1.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-01 00:00:00"));
        
        // Execute - Test transaction2
        double totalPrice2 = transaction2.calculateTotalPrice(parseDate("2025-01-20 00:00:00"), parseDate("2025-01-10 00:00:00"));
        
        // Verify combined total
        assertEquals(11.00, totalPrice1 + totalPrice2, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = VideorentalFactory.eINSTANCE.createCustomer();
        customer.setId("C006");
        
        Tape tape = VideorentalFactory.eINSTANCE.createTape();
        tape.setId("T008");
        
        RentalTransaction transaction = VideorentalFactory.eINSTANCE.createRentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(parseDate("2025-01-01 00:00:00"));
        
        VideoRental rental = VideorentalFactory.eINSTANCE.createVideoRental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setTransaction(transaction);
        rental.setDueDate(parseDate("2025-01-05 00:00:00"));
        // returnDate remains null (unreturned)
        
        // Associate rental with transaction, customer and tape
        transaction.getRentals().add(rental);
        customer.getRentals().add(rental);
        tape.getRentals().add(rental);
        
        // Execute
        double totalPrice = transaction.calculateTotalPrice(parseDate("2025-01-10 00:00:00"), parseDate("2025-01-01 00:00:00"));
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}