import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Rental 1: Tape T001, returned early
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Rental 2: Tape T002, returned early
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected output
        assertEquals("Total price should be 13.00", 13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Rental 1: Tape T003, 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected output
        assertEquals("Total price should be 14.50", 14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Rental 1: Tape T004, 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Rental 2: Tape T005, 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected output
        assertEquals("Total price should be 19.50", 19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Rental 1: Tape T006, 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Rental 2: Tape T007, on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected output
        assertEquals("Total price should be 11.00", 11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Rental 1: Tape T008, unreturned, 5 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Unreturned
        
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(
            LocalDate.parse("2025-01-01", formatter), 
            currentDate
        );
        
        // Verify expected output
        assertEquals("Total price should be 11.50", 11.50, totalPrice, 0.001);
    }
}