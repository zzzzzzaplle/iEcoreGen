import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Customer customer;
    private RentalTransaction transaction;
    
    @Before
    public void setUp() {
        // Clear static rentals list before each test to avoid interference
        VideoRental.getAllRentals().clear();
        customer = new Customer();
        transaction = new RentalTransaction();
        transaction.setCustomer(customer);
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup test data
        customer.setId("C001");
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-03"));
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-12"));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01"), currentDate);
        
        // Verify expected result
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup test data
        customer.setId("C002");
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Create Rental: 7 days overdue
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("T003");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(LocalDate.parse("2025-01-12"));
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01"), currentDate);
        
        // Verify expected result
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup test data
        customer.setId("C003");
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-09"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-18"));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01"), currentDate);
        
        // Verify expected result
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup test data
        customer.setId("C004");
        LocalDate currentDate = LocalDate.parse("2025-01-20");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-07"));
        
        // Create Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-14"));
        
        // Add rentals to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01"), currentDate);
        
        // Verify expected result
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup test data
        customer.setId("C006");
        LocalDate currentDate = LocalDate.parse("2025-01-10");
        
        // Create Rental: unreturned, 5 days overdue as of current date
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("T008");
        rental.setTape(tape);
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null); // Not returned
        
        // Add rental to customer and transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        transaction.setRentals(rentals);
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01"), currentDate);
        
        // Verify expected result
        assertEquals(11.50, totalPrice, 0.001);
    }
}