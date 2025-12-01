import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C001");
        customer.setName("Customer C001");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Returned early, no overdue fee
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setTitle("Movie 1");
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        rental1.setRentalDays(4); // Jan 1 to Jan 5 = 4 days
        rental1.setBaseRentalFee(new BigDecimal("4.00"));
        rentals.add(rental1);
        
        // Rental 2: Returned early, no overdue fee
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setTitle("Movie 2");
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        rental2.setRentalDays(14); // Jan 1 to Jan 15 = 14 days
        rental2.setBaseRentalFee(new BigDecimal("14.00"));
        rentals.add(rental2);
        
        customer.setRentals(rentals);
        
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Test
        BigDecimal totalPrice = transaction.calculateTotalPrice("2025-01-20");
        
        // Verify
        assertEquals("Total price should be $13.00", new BigDecimal("13.00"), totalPrice);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C002");
        customer.setName("Customer C002");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: 7 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T003");
        tape1.setTitle("Movie 3");
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-12");
        rental1.setRentalDays(4); // Jan 1 to Jan 5 = 4 days
        rental1.setBaseRentalFee(new BigDecimal("4.00"));
        rentals.add(rental1);
        
        customer.setRentals(rentals);
        
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Test
        BigDecimal totalPrice = transaction.calculateTotalPrice("2025-01-20");
        
        // Verify
        assertEquals("Total price should be $14.50", new BigDecimal("14.50"), totalPrice);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C003");
        customer.setName("Customer C003");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: 4 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T004");
        tape1.setTitle("Movie 4");
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        rental1.setRentalDays(4); // Jan 1 to Jan 5 = 4 days
        rental1.setBaseRentalFee(new BigDecimal("4.00"));
        rentals.add(rental1);
        
        // Rental 2: 3 days overdue
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T005");
        tape2.setTitle("Movie 5");
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        rental2.setRentalDays(5); // Jan 10 to Jan 15 = 5 days
        rental2.setBaseRentalFee(new BigDecimal("5.00"));
        rentals.add(rental2);
        
        customer.setRentals(rentals);
        
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Test
        BigDecimal totalPrice = transaction.calculateTotalPrice("2025-01-20");
        
        // Verify
        assertEquals("Total price should be $19.50", new BigDecimal("19.50"), totalPrice);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C004");
        customer.setName("Customer C004");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: 2 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T006");
        tape1.setTitle("Movie 6");
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        rental1.setRentalDays(4); // Jan 1 to Jan 5 = 4 days
        rental1.setBaseRentalFee(new BigDecimal("4.00"));
        rentals.add(rental1);
        
        // Rental 2: On-time return
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T007");
        tape2.setTitle("Movie 7");
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        rental2.setRentalDays(5); // Jan 10 to Jan 15 = 5 days
        rental2.setBaseRentalFee(new BigDecimal("5.00"));
        rentals.add(rental2);
        
        customer.setRentals(rentals);
        
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Test
        BigDecimal totalPrice = transaction.calculateTotalPrice("2025-01-20");
        
        // Verify
        assertEquals("Total price should be $11.00", new BigDecimal("11.00"), totalPrice);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C006");
        customer.setName("Customer C006");
        
        List<Rental> rentals = new ArrayList<>();
        
        // Rental 1: Unreturned, 5 days overdue as of current date
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T008");
        tape1.setTitle("Movie 8");
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate(null); // Not returned
        rental1.setRentalDays(4); // Jan 1 to Jan 5 = 4 days
        rental1.setBaseRentalFee(new BigDecimal("4.00"));
        rentals.add(rental1);
        
        customer.setRentals(rentals);
        
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        
        // Test
        BigDecimal totalPrice = transaction.calculateTotalPrice("2025-01-10");
        
        // Verify
        assertEquals("Total price should be $11.50", new BigDecimal("11.50"), totalPrice);
    }
}