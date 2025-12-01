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
        // Setup Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        system.getCustomers().add(customer);
        
        // Create VideoTapes
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        system.getVideoTapes().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        system.getVideoTapes().add(tape2);
        
        // Create RentalTransaction with Rental 1
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        transaction.getRentals().add(rental1);
        
        // Create Rental 2
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        transaction.getRentals().add(rental2);
        
        customer.getRentalTransactions().add(transaction);
        system.getRentalTransactions().add(transaction);
        
        // Calculate total price with current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice();
        
        // Expected: Rental 1: 2 days (01-01 to 01-03) × $1 = $2 + $0 overdue = $2
        // Rental 2: 11 days (01-01 to 01-12) × $1 = $11 + $0 overdue = $11
        // Total = $13.00
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        system.getCustomers().add(customer);
        
        // Create VideoTape
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        system.getVideoTapes().add(tape);
        
        // Create RentalTransaction with Rental 1
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-12");
        transaction.getRentals().add(rental);
        
        customer.getRentalTransactions().add(transaction);
        system.getRentalTransactions().add(transaction);
        
        // Calculate total price with current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice();
        
        // Expected: 11 days (01-01 to 01-12) × $1 = $11 base fee
        // 7 days overdue (01-05 to 01-12) × $0.5 = $3.50 overdue fee
        // Total = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        system.getCustomers().add(customer);
        
        // Create VideoTapes
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T004");
        system.getVideoTapes().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T005");
        system.getVideoTapes().add(tape2);
        
        // Create RentalTransaction with Rental 1
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        transaction.getRentals().add(rental1);
        
        // Create Rental 2
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        transaction.getRentals().add(rental2);
        
        customer.getRentalTransactions().add(transaction);
        system.getRentalTransactions().add(transaction);
        
        // Calculate total price with current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice();
        
        // Expected: 
        // Rental 1: 8 days (01-01 to 01-09) × $1 = $8 base fee
        //          4 days overdue (01-05 to 01-09) × $0.5 = $2.00 overdue fee
        // Rental 2: 8 days (01-10 to 01-18) × $1 = $8 base fee
        //          3 days overdue (01-15 to 01-18) × $0.5 = $1.50 overdue fee
        // Total = $8 + $2 + $8 + $1.50 = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        system.getCustomers().add(customer);
        
        // Create VideoTapes
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T006");
        system.getVideoTapes().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T007");
        system.getVideoTapes().add(tape2);
        
        // Create RentalTransaction with Rental 1
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        transaction.getRentals().add(rental1);
        
        // Create Rental 2
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        transaction.getRentals().add(rental2);
        
        customer.getRentalTransactions().add(transaction);
        system.getRentalTransactions().add(transaction);
        
        // Calculate total price with current date "2025-01-20"
        double totalPrice = transaction.calculateTotalPrice();
        
        // Expected:
        // Rental 1: 6 days (01-01 to 01-07) × $1 = $6 base fee
        //          2 days overdue (01-05 to 01-07) × $0.5 = $1.00 overdue fee
        // Rental 2: 4 days (01-10 to 01-14) × $1 = $4 base fee
        //          0 days overdue (returned early)
        // Total = $6 + $1 + $4 = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        system.getCustomers().add(customer);
        
        // Create VideoTape
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T008");
        system.getVideoTapes().add(tape);
        
        // Create RentalTransaction with Rental 1
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned
        transaction.getRentals().add(rental);
        
        customer.getRentalTransactions().add(transaction);
        system.getRentalTransactions().add(transaction);
        
        // Calculate total price with current date "2025-01-10"
        double totalPrice = transaction.calculateTotalPrice();
        
        // Expected: 9 days (01-01 to 01-10) × $1 = $9 base fee
        //          5 days overdue (01-05 to 01-10) × $0.5 = $2.50 overdue fee
        // Total = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}