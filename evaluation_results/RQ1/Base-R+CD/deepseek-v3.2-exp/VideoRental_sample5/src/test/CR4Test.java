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
        // Create customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create rentals list
        List<VideoRental> rentals = new ArrayList<>();
        
        // Rental 1: Tape T001, rented 2025-01-01, due 2025-01-05, returned 2025-01-03
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-03 00:00:00", formatter));
        rentals.add(rental1);
        
        // Rental 2: Tape T002, rented 2025-01-01, due 2025-01-15, returned 2025-01-12
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        rentals.add(rental2);
        
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Current date: 2025-01-20
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental 1: 2 days base fee ($2) + 0 overdue = $2
        // Rental 2: 11 days base fee ($11) + 0 overdue = $11
        // Total: $13.00
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Create customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create rentals list
        List<VideoRental> rentals = new ArrayList<>();
        
        // Rental 1: Tape T003, rented 2025-01-01, due 2025-01-05, returned 2025-01-12
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-12 00:00:00", formatter));
        rentals.add(rental1);
        
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Current date: 2025-01-20
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: Rental duration = 11 days ($11 base fee)
        // Overdue: 7 days × $0.50 = $3.50
        // Total: $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create rentals list
        List<VideoRental> rentals = new ArrayList<>();
        
        // Rental 1: Tape T004, rented 2025-01-01, due 2025-01-05, returned 2025-01-09
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-09 00:00:00", formatter));
        rentals.add(rental1);
        
        // Rental 2: Tape T005, rented 2025-01-10, due 2025-01-15, returned 2025-01-18
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-18 00:00:00", formatter));
        rentals.add(rental2);
        
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Current date: 2025-01-20
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: 
        // Rental 1: 8 days base fee ($8) + 4 days overdue ($2.00) = $10.00
        // Rental 2: 8 days base fee ($8) + 3 days overdue ($1.50) = $9.50
        // Total: $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Create customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create rentals list
        List<VideoRental> rentals = new ArrayList<>();
        
        // Rental 1: Tape T006, rented 2025-01-01, due 2025-01-05, returned 2025-01-07
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(LocalDateTime.parse("2025-01-07 00:00:00", formatter));
        rentals.add(rental1);
        
        // Rental 2: Tape T007, rented 2025-01-10, due 2025-01-15, returned 2025-01-14
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDateTime.parse("2025-01-15 00:00:00", formatter));
        rental2.setReturnDate(LocalDateTime.parse("2025-01-14 00:00:00", formatter));
        rentals.add(rental2);
        
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Current date: 2025-01-20
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-20 00:00:00", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: 
        // Rental 1: 6 days base fee ($6) + 2 days overdue ($1.00) = $7.00
        // Rental 2: 4 days base fee ($4) + 0 overdue = $4.00
        // Total: $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Create customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create RentalTransaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        
        // Create rentals list
        List<VideoRental> rentals = new ArrayList<>();
        
        // Rental 1: Tape T008, rented 2025-01-01, due 2025-01-05, not returned (returnDate = null)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDateTime.parse("2025-01-05 00:00:00", formatter));
        rental1.setReturnDate(null); // Unreturned tape
        rentals.add(rental1);
        
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDateTime.parse("2025-01-01 00:00:00", formatter));
        
        // Current date: 2025-01-10 (5 days overdue)
        LocalDateTime currentDate = LocalDateTime.parse("2025-01-10 00:00:00", formatter);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(transaction.getRentalDate(), currentDate);
        
        // Expected: 
        // Rental duration = 9 days ($9 base fee)
        // Overdue: 5 days × $0.50 = $2.50
        // Total: $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}