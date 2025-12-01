import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Input: customer_id="C001", current date is "2025-01-20"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Setup Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add Rental 1: Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-03 00:00:00", formatter));
        
        // Add Rental 2: Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Input: customer_id="C002", current date is "2025-01-20"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Setup Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add Rental 1: Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Input: customer_id="C003", current_date="2025-01-20"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Setup Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add Rental 1: Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-09 00:00:00", formatter));
        
        // Add Rental 2: Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-18 00:00:00", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Input: customer_id="C004", current_date="2025-01-20"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Setup Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add Rental 1: Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-07 00:00:00", formatter));
        
        // Add Rental 2: Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-14 00:00:00", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Total price = (6+4 base) + 1 overdue = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Input: customer_id="C006", current_date="2025-01-10"
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Setup Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Add Rental 1: Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null); // Unreturned tape
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}