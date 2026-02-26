import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalTransaction rentalTransaction;
    
    @Before
    public void setUp() {
        rentalTransaction = new RentalTransaction();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup: Create Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Setup: Add Rental 1 - returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        
        // Setup: Add Rental 2 - returned early, no overdue fee
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
        rentalTransaction.setRentals(rentals);
        rentalTransaction.setCustomer(customer);
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double totalPrice = rentalTransaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup: Create Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Setup: Add Rental 1 - 7 days overdue
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
        rentalTransaction.setRentals(rentals);
        rentalTransaction.setCustomer(customer);
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double totalPrice = rentalTransaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Expected: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup: Create Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Setup: Add Rental 1 - 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        
        // Setup: Add Rental 2 - 3 days overdue
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
        rentalTransaction.setRentals(rentals);
        rentalTransaction.setCustomer(customer);
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double totalPrice = rentalTransaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Expected: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup: Create Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Setup: Add Rental 1 - 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        
        // Setup: Add Rental 2 - on-time return
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
        rentalTransaction.setRentals(rentals);
        rentalTransaction.setCustomer(customer);
        
        // Calculate total price with current date 2025-01-20
        LocalDate currentDate = LocalDate.of(2025, 1, 20);
        double totalPrice = rentalTransaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Expected: Total price = (6+4 base) + 1 overdue = $11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup: Create Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Setup: Add Rental 1 - unreturned, 5 days overdue
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
        rentalTransaction.setRentals(rentals);
        rentalTransaction.setCustomer(customer);
        
        // Calculate total price with current date 2025-01-10
        LocalDate currentDate = LocalDate.of(2025, 1, 10);
        double totalPrice = rentalTransaction.calculateTotalPrice(LocalDate.of(2025, 1, 1), currentDate);
        
        // Expected: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}