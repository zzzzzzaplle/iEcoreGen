import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setIdCard("C001");
        
        // Create and setup Rental 1
        RentalTransaction transaction1 = new RentalTransaction();
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        transaction1.getRentals().add(rental1);
        
        // Create and setup Rental 2
        RentalTransaction transaction2 = new RentalTransaction();
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        transaction2.getRentals().add(rental2);
        
        // Add transactions to customer
        customer.getRentalTransactions().add(transaction1);
        customer.getRentalTransactions().add(transaction2);
        
        // Calculate total price
        double totalPrice1 = system.calculateTotalPrice(transaction1);
        double totalPrice2 = system.calculateTotalPrice(transaction2);
        double totalPrice = totalPrice1 + totalPrice2;
        
        // Verify results
        assertEquals(2.0, totalPrice1, 0.001);
        assertEquals(11.0, totalPrice2, 0.001);
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setIdCard("C002");
        
        // Create and setup Rental 1
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-12");
        transaction.getRentals().add(rental);
        
        // Add transaction to customer
        customer.getRentalTransactions().add(transaction);
        
        // Calculate total price
        double totalPrice = system.calculateTotalPrice(transaction);
        
        // Verify result
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setIdCard("C003");
        
        // Create and setup Rental 1
        RentalTransaction transaction1 = new RentalTransaction();
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T004");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        transaction1.getRentals().add(rental1);
        
        // Create and setup Rental 2
        RentalTransaction transaction2 = new RentalTransaction();
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T005");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        transaction2.getRentals().add(rental2);
        
        // Add transactions to customer
        customer.getRentalTransactions().add(transaction1);
        customer.getRentalTransactions().add(transaction2);
        
        // Calculate total prices and sum
        double totalPrice1 = system.calculateTotalPrice(transaction1);
        double totalPrice2 = system.calculateTotalPrice(transaction2);
        double totalPrice = totalPrice1 + totalPrice2;
        
        // Verify results
        assertEquals(10.0, totalPrice1, 0.001);
        assertEquals(9.5, totalPrice2, 0.001);
        assertEquals(19.5, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setIdCard("C004");
        
        // Create and setup Rental 1 (overdue)
        RentalTransaction transaction1 = new RentalTransaction();
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T006");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        transaction1.getRentals().add(rental1);
        
        // Create and setup Rental 2 (on-time)
        RentalTransaction transaction2 = new RentalTransaction();
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T007");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        transaction2.getRentals().add(rental2);
        
        // Add transactions to customer
        customer.getRentalTransactions().add(transaction1);
        customer.getRentalTransactions().add(transaction2);
        
        // Calculate total prices and sum
        double totalPrice1 = system.calculateTotalPrice(transaction1);
        double totalPrice2 = system.calculateTotalPrice(transaction2);
        double totalPrice = totalPrice1 + totalPrice2;
        
        // Verify results
        assertEquals(7.0, totalPrice1, 0.001);
        assertEquals(4.0, totalPrice2, 0.001);
        assertEquals(11.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setIdCard("C006");
        
        // Create and setup Rental 1 (unreturned, current date overdue)
        RentalTransaction transaction = new RentalTransaction();
        Rental rental = new Rental();
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T008");
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        transaction.getRentals().add(rental);
        
        // Add transaction to customer
        customer.getRentalTransactions().add(transaction);
        
        // Calculate total price (system uses current date for unreturned tapes)
        double totalPrice = system.calculateTotalPrice(transaction);
        
        // Verify result
        assertEquals(11.50, totalPrice, 0.001);
    }
}