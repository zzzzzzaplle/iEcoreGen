import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalTransaction transaction;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        transaction = new RentalTransaction();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Clear RentalStore between tests
        List<VideoRental> allRentals = RentalStore.getAllRentals();
        if (allRentals instanceof ArrayList) {
            ((ArrayList<VideoRental>) allRentals).clear();
        }
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Test Case 1: "No overdue fees"
        // Input: customer_id="C001", current date is "2025-01-20"
        
        // Setup Customer C001
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Setup Rental 1: Tape ID="T001", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-03"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Setup Rental 2: Tape ID="T002", rental_date="2025-01-01", due_date="2025-01-15", return_date="2025-01-12"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Calculate total price with current date "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Expected: Rental 1 price: 2 + 0 = 2, Rental 2 price: 11 + 0 = 11, total price = 13
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Test Case 2: "One overdue rental"
        // Input: customer_id="C002", current date is "2025-01-20"
        
        // Setup Customer C002
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Setup Rental 1: Tape ID="T003", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-12"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rental to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Calculate total price with current date "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Expected: Total price = 11 (base fee) + 3.50 (overdue) = $14.50
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Test Case 3: "Multiple overdue rentals"
        // Input: customer_id="C003", current_date="2025-01-20"
        
        // Setup Customer C003
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Setup Rental 1: Tape ID="T004", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-09"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Setup Rental 2: Tape ID="T005", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-18"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Calculate total price with current date "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Expected: Total price = 8+8 base fees + 2+1.5 overdue = $19.50
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Test Case 4: "Mixed overdue and on-time rentals"
        // Input: customer_id="C004", current_date="2025-01-20"
        
        // Setup Customer C004
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Setup Rental 1: Tape ID="T006", rental_date="2025-01-01", due_date="2025-01-05", return_date="2025-01-07"
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Setup Rental 2: Tape ID="T007", rental_date="2025-01-10", due_date="2025-01-15", return_date="2025-01-14"
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        // Add rentals to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Calculate total price with current date "2025-01-20"
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        double result = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Expected: Total price = (6+4 base) + 1 overdue = $11.00
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Test Case 5: "Unreturned tape with current date overdue"
        // Input: customer_id="C006", current_date="2025-01-10"
        
        // Setup Customer C006
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Setup Rental 1: Tape ID="T008", rental_date="2025-01-01", due_date="2025-01-05", return_date=null
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Unreturned tape
        
        // Add rental to transaction
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        
        // Calculate total price with current date "2025-01-10"
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        double result = transaction.calculateTotalPrice(LocalDate.parse("2025-01-01", formatter), currentDate);
        
        // Expected: Total price = 9 (base fee) + 2.50 (overdue) = $11.50
        assertEquals(11.50, result, 0.001);
    }
}