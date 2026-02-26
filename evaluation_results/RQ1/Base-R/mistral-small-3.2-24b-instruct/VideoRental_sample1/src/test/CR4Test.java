import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalService rentalService;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Test Case 1: "No overdue fees"
        // Input: customer_id="C001", current date is "2025-01-20"
        
        // Setup: Create Customer C001
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        // Setup: Add Rental 1 - returned early, overdue fee=0
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-03"));
        rental1.calculateOverdueFee();
        customer.addRental(rental1);
        
        // Setup: Add Rental 2 - returned early, overdue fee=0
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-12"));
        rental2.calculateOverdueFee();
        customer.addRental(rental2);
        
        // Execute: Calculate total price
        double totalPrice = rentalService.calculateTotalPriceForTransaction(customer);
        
        // Verify: Expected total price = 13.00
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Test Case 2: "One overdue rental"
        // Input: customer_id="C002", current date is "2025-01-20"
        
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        // Setup: Add Rental 1 - 7 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T003");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-12"));
        rental1.calculateOverdueFee();
        customer.addRental(rental1);
        
        // Execute: Calculate total price
        double totalPrice = rentalService.calculateTotalPriceForTransaction(customer);
        
        // Verify: Expected total price = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Test Case 3: "Multiple overdue rentals"
        // Input: customer_id="C003", current_date="2025-01-20"
        
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        // Setup: Add Rental 1 - 4 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T004");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-09"));
        rental1.calculateOverdueFee();
        customer.addRental(rental1);
        
        // Setup: Add Rental 2 - 3 days overdue
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T005");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-18"));
        rental2.calculateOverdueFee();
        customer.addRental(rental2);
        
        // Execute: Calculate total price
        double totalPrice = rentalService.calculateTotalPriceForTransaction(customer);
        
        // Verify: Expected total price = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Test Case 4: "Mixed overdue and on-time rentals"
        // Input: customer_id="C004", current_date="2025-01-20"
        
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        // Setup: Add Rental 1 - 2 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T006");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-07"));
        rental1.calculateOverdueFee();
        customer.addRental(rental1);
        
        // Setup: Add Rental 2 - on-time
        Rental rental2 = new Rental();
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T007");
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-14"));
        rental2.calculateOverdueFee();
        customer.addRental(rental2);
        
        // Execute: Calculate total price
        double totalPrice = rentalService.calculateTotalPriceForTransaction(customer);
        
        // Verify: Expected total price = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Test Case 5: "Unreturned tape with current date overdue"
        // Input: customer_id="C006", current_date="2025-01-10"
        
        // Setup: Create Customer C006
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        
        // Setup: Add Rental 1 - unreturned, 5 days overdue
        Rental rental1 = new Rental();
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T008");
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(null); // Unreturned
        rental1.calculateOverdueFee(); // Will calculate based on current date (2025-01-10)
        customer.addRental(rental1);
        
        // Execute: Calculate total price
        double totalPrice = rentalService.calculateTotalPriceForTransaction(customer);
        
        // Verify: Expected total price = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}