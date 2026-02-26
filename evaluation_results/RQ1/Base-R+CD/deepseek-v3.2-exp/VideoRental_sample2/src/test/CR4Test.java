import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transaction = new RentalTransaction();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer = new Customer();
        customer.setId("C001");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-03 00:00:00", formatter));
        
        // Create Rental 2: returned early, no overdue fee  
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Setup transaction
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Test calculation with current date
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        double actualTotal = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected total: Rental1 (2 days base) + Rental2 (11 days base) = 13.00
        assertEquals(13.00, actualTotal, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer = new Customer();
        customer.setId("C002");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Setup transaction
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Test calculation with current date
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        double actualTotal = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected total: 11 days base + 3.50 overdue = 14.50
        assertEquals(14.50, actualTotal, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer = new Customer();
        customer.setId("C003");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-09 00:00:00", formatter));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-18 00:00:00", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Setup transaction (note: rental2 starts at 2025-01-10)
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Test calculation with current date
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        double actualTotal = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected total: (8+8 base) + (2.00+1.50 overdue) = 19.50
        assertEquals(19.50, actualTotal, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer = new Customer();
        customer.setId("C004");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-07 00:00:00", formatter));
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-14 00:00:00", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Setup transaction (note: rental2 starts at 2025-01-10)
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Test calculation with current date
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        double actualTotal = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected total: (6+4 base) + 1.00 overdue = 11.00
        assertEquals(11.00, actualTotal, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer = new Customer();
        customer.setId("C006");
        
        // Create Rental 1: unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Setup transaction
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Test calculation with current date (5 days overdue from due date)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        double actualTotal = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Verify expected total: 9 days base + 2.50 overdue = 11.50
        assertEquals(11.50, actualTotal, 0.001);
    }
}