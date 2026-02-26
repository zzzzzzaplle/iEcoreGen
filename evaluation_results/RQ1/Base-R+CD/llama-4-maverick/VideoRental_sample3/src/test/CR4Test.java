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
        // Test Setup
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create Rental 1: returned early, no overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Create Rental 2: returned early, no overdue fee
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        transaction.setRentalDate(currentDate);
        
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify expected output
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Test Setup
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Rental 1: 7 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        transaction.setRentalDate(currentDate);
        
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify expected output
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Test Setup
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        transaction.setRentalDate(currentDate);
        
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify expected output
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Test Setup
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Create Rental 2: on-time return
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        transaction.setRentalDate(currentDate);
        
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify expected output
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Test Setup
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create Rental 1: unreturned, 5 days overdue based on current date
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentals(rentals);
        transaction.setCustomer(customer);
        transaction.setRentalDate(currentDate);
        
        double totalPrice = transaction.calculateTotalPrice(currentDate, currentDate);
        
        // Verify expected output
        assertEquals(11.50, totalPrice, 0.001);
    }
}