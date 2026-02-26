import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private RentalTransaction transaction;
    private Customer customer;
    private List<Rental> rentals;
    
    @Before
    public void setUp() {
        transaction = new RentalTransaction();
        customer = new Customer();
        rentals = new ArrayList<>();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        customer.setId("C001");
        
        // Add Rental 1: Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03"
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        rentals.add(rental1);
        
        // Add Rental 2: Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12"
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        rentals.add(rental2);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        double result = transaction.calculateTotalPrice();
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        customer.setId("C002");
        
        // Add Rental 1: Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12"
        Rental rental1 = new Rental();
        rental1.setTapeId("T003");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        rentals.add(rental1);
        
        // Expected: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        double result = transaction.calculateTotalPrice();
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        customer.setId("C003");
        
        // Add Rental 1: Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09"
        Rental rental1 = new Rental();
        rental1.setTapeId("T004");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        rentals.add(rental1);
        
        // Add Rental 2: Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18"
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        rentals.add(rental2);
        
        // Expected: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        double result = transaction.calculateTotalPrice();
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        customer.setId("C004");
        
        // Add Rental 1: Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07"
        Rental rental1 = new Rental();
        rental1.setTapeId("T006");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        rentals.add(rental1);
        
        // Add Rental 2: Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14"
        Rental rental2 = new Rental();
        rental2.setTapeId("T007");
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        rentals.add(rental2);
        
        // Expected: Total price = (6+4 base) + 1 overdue = $11.00
        double result = transaction.calculateTotalPrice();
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        customer.setId("C006");
        
        // Add Rental 1: Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        Rental rental1 = new Rental();
        rental1.setTapeId("T008");
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null);
        rentals.add(rental1);
        
        // Expected: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        double result = transaction.calculateTotalPrice();
        assertEquals(11.50, result, 0.001);
    }
}