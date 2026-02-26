import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer.setId("C001");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: returned early
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        
        // Create Rental 2: returned early
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Verify result
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer.setId("C002");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: returned late (7 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Verify result
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer.setId("C003");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: returned late (4 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        
        // Create Rental 2: returned late (3 days overdue)
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Verify result
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer.setId("C004");
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        
        // Create Rental 1: returned late (2 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        
        // Create Rental 2: returned on time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Verify result
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer.setId("C006");
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        
        // Create Rental 1: unreturned (5 days overdue)
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Unreturned
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Verify result
        assertEquals(11.50, totalPrice, 0.001);
    }
}