import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        customer = new Customer();
        transaction = new RentalTransaction();
        customer.setRentalTransactions(new ArrayList<>());
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        customer.setIdCard("C001");
        
        // Add Rental 1: Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03"
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        
        // Add Rental 2: Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12"
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Total price = 13.00
        double expectedTotal = 13.00;
        double actualTotal = transaction.calculateTotalPrice();
        
        assertEquals("Total price should be 13.00 for no overdue fees", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        customer.setIdCard("C002");
        
        // Add Rental 1: Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12"
        Rental rental1 = new Rental();
        rental1.setTapeId("T003");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        
        transaction.addRental(rental1);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Total price = 14.50
        double expectedTotal = 14.50;
        double actualTotal = transaction.calculateTotalPrice();
        
        assertEquals("Total price should be 14.50 for one overdue rental", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        customer.setIdCard("C003");
        
        // Add Rental 1: Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09"
        Rental rental1 = new Rental();
        rental1.setTapeId("T004");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        
        // Add Rental 2: Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18"
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Total price = 19.50
        double expectedTotal = 19.50;
        double actualTotal = transaction.calculateTotalPrice();
        
        assertEquals("Total price should be 19.50 for multiple overdue rentals", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        customer.setIdCard("C004");
        
        // Add Rental 1: Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07"
        Rental rental1 = new Rental();
        rental1.setTapeId("T006");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        
        // Add Rental 2: Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14"
        Rental rental2 = new Rental();
        rental2.setTapeId("T007");
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        
        transaction.addRental(rental1);
        transaction.addRental(rental2);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Total price = 11.00
        double expectedTotal = 11.00;
        double actualTotal = transaction.calculateTotalPrice();
        
        assertEquals("Total price should be 11.00 for mixed overdue and on-time rentals", expectedTotal, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        customer.setIdCard("C006");
        
        // Add Rental 1: Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental1 = new Rental();
        rental1.setTapeId("T008");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Unreturned tape
        
        transaction.addRental(rental1);
        customer.addRentalTransaction(transaction);
        
        // Expected Output: Total price = 11.50
        double expectedTotal = 11.50;
        double actualTotal = transaction.calculateTotalPrice();
        
        assertEquals("Total price should be 11.50 for unreturned tape with current date overdue", expectedTotal, actualTotal, 0.001);
    }
}