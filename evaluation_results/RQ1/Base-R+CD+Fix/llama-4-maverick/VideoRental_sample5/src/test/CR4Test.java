import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalTransaction transaction;
    private Customer customer;
    
    @Before
    public void setUp() {
        transaction = new RentalTransaction();
        customer = new Customer();
        transaction.setCustomer(customer);
        // Clear static customers list before each test
        RentalSystem.customers = new ArrayList<>();
        RentalSystem.customers.add(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Test Case 1: "No overdue fees"
        // Input: customer_id="C001", current date is "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Setup Customer C001
        customer.setId("C001");
        
        // Add Rental 1: Tape T001, returned early (no overdue fee)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-03"));
        customer.getRentals().add(rental1);
        
        // Add Rental 2: Tape T002, returned early (no overdue fee)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-12"));
        customer.getRentals().add(rental2);
        
        // Set rental date for transaction
        transaction.setRentalDate(LocalDate.parse("2025-01-01"));
        transaction.getRentals().addAll(customer.getRentals());
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental 1: 2 days * $1 = $2 + $0 overdue = $2
        // Rental 2: 11 days * $1 = $11 + $0 overdue = $11
        // Total = $13.00
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Test Case 2: "One overdue rental"
        // Input: customer_id="C002", current date is "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Setup Customer C002
        customer.setId("C002");
        
        // Add Rental 1: Tape T003, returned late (7 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-12"));
        customer.getRentals().add(rental1);
        
        // Set rental date for transaction
        transaction.setRentalDate(LocalDate.parse("2025-01-01"));
        transaction.getRentals().addAll(customer.getRentals());
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Base fee: 11 days * $1 = $11
        // Overdue: 7 days * $0.5 = $3.50
        // Total = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Test Case 3: "Multiple overdue rentals"
        // Input: customer_id="C003", current_date="2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Setup Customer C003
        customer.setId("C003");
        
        // Add Rental 1: Tape T004, returned late (4 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-09"));
        customer.getRentals().add(rental1);
        
        // Add Rental 2: Tape T005, returned late (3 days overdue)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-18"));
        customer.getRentals().add(rental2);
        
        // Set rental dates for transactions
        transaction.setRentalDate(LocalDate.parse("2025-01-01"));
        transaction.getRentals().addAll(customer.getRentals());
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental 1: Base fee 8 days * $1 = $8, Overdue 4 days * $0.5 = $2.00
        // Rental 2: Base fee 8 days * $1 = $8, Overdue 3 days * $0.5 = $1.50
        // Total = $8 + $2 + $8 + $1.50 = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Test Case 4: "Mixed overdue and on-time rentals"
        // Input: customer_id="C004", current_date="2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Setup Customer C004
        customer.setId("C004");
        
        // Add Rental 1: Tape T006, returned late (2 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-07"));
        customer.getRentals().add(rental1);
        
        // Add Rental 2: Tape T007, returned on-time (no overdue)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-14"));
        customer.getRentals().add(rental2);
        
        // Set rental dates for transactions
        transaction.setRentalDate(LocalDate.parse("2025-01-01"));
        transaction.getRentals().addAll(customer.getRentals());
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental 1: Base fee 6 days * $1 = $6, Overdue 2 days * $0.5 = $1.00
        // Rental 2: Base fee 4 days * $1 = $4, Overdue 0 = $0
        // Total = $6 + $1 + $4 + $0 = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Test Case 5: "Unreturned tape with current date overdue"
        // Input: customer_id="C006", current_date="2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Setup Customer C006
        customer.setId("C006");
        
        // Add Rental 1: Tape T008, not returned (5 days overdue based on current date)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        
        // Set rental date for transaction
        transaction.setRentalDate(LocalDate.parse("2025-01-01"));
        transaction.getRentals().addAll(customer.getRentals());
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Base fee: 9 days * $1 = $9
        // Overdue: 5 days * $0.5 = $2.50
        // Total = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}