import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noOverdueFees() throws Exception {
        // Test Case 1: No overdue fees
        // Input: customer_id="C001", current date is "2025-01-20"
        
        // Setup Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create Rental 1: Tape T001, returned early (overdue fee=0)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03"));
        
        // Create Rental 2: Tape T002, returned early (overdue fee=0)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12"));
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_oneOverdueRental() throws Exception {
        // Test Case 2: One overdue rental
        // Input: customer_id="C002", current date is "2025-01-20"
        
        // Setup Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Rental 1: Tape T003, 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12")); // 7 days overdue
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        transaction.getRentals().add(rental1);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_multipleOverdueRentals() throws Exception {
        // Test Case 3: Multiple overdue rentals
        // Input: customer_id="C003", current_date="2025-01-20"
        
        // Setup Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Rental 1: Tape T004, 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09")); // 4 days overdue
        
        // Create Rental 2: Tape T005, 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18")); // 3 days overdue
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        transaction.getRentals().add(rental1);
        transaction.setRentalDate(dateFormat.parse("2025-01-10"));
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_mixedOverdueAndOnTimeRentals() throws Exception {
        // Test Case 4: Mixed overdue and on-time rentals
        // Input: customer_id="C004", current_date="2025-01-20"
        
        // Setup Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Rental 1: Tape T006, 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07")); // 2 days overdue
        
        // Create Rental 2: Tape T007, on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14")); // on-time
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        transaction.getRentals().add(rental1);
        transaction.setRentalDate(dateFormat.parse("2025-01-10"));
        transaction.getRentals().add(rental2);
        
        // Calculate total price with current date "2025-01-20"
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output: Total price = (6+4 base) + 1 overdue = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_unreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Test Case 5: Unreturned tape with current date overdue
        // Input: customer_id="C006", current_date="2025-01-10"
        
        // Setup Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create Rental 1: Tape T008, unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(null); // unreturned
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        transaction.getRentals().add(rental1);
        
        // Calculate total price with current date "2025-01-10" (5 days overdue)
        Date currentDate = dateFormat.parse("2025-01-10");
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected output: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}